/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: FactionInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: 帮会模块
 * @author XiangMZh   
 * @date 2014-10-24 下午4:05:40 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IFactionDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Faction;
import com.lk.dragon.db.domain.RoleFaction;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.ReadProperties;

/**
 * @Description:帮会模块业务层
 */
@Service
public class FactionInfoService {
	@Autowired
	private IFactionDao factionDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IRolePropsDao propsDao;

	public FactionInfoService() {
		super();
	}

	/**
	 * 新建帮会
	 * 
	 * @param role_id
	 *            创建人
	 * @param faction_name
	 *            帮会名称
	 * @param icon
	 *            帮会图标
	 * @param use_gold
	 *            消耗金币
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Long> createFaction(long role_id, String faction_name,
			String icon, int use_gold) throws Exception {
		long faction_id = 0;

		String condition = "WHERE t.faction_name = '" + faction_name + "'";
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> mapRole = new HashMap<String, Object>();
		HashMap<String, Long> mapRes = new HashMap<String, Long>();
		mapRole.put("role_id", role_id);
		mapRole.put("operator", "-");
		mapRole.put("gold", use_gold);
		if (factionDao.getFactionsByCondition(condition) != null
				&& factionDao.getFactionsByCondition(condition).size() != 0) {
			mapRes.put(Constants.RESULT_KEY, Constants.FACTION_NAME_USED);
			mapRes.put(Constants.ID_KEY, -1l);
			return mapRes;
		}

		faction_id = factionDao.createFaction(new Faction(faction_name, icon));
		map.put("role_id", role_id);
		map.put("faction_id", faction_id);
		map.put("faction_position", -1);// 帮主职位
		factionDao.addRoleFaction(map);
		roleDao.sumPluRoleInfo(mapRole);// 消耗金币

		mapRes.put(Constants.RESULT_KEY, Constants.FACTION_CREATE_SUCCESS);
		mapRes.put(Constants.ID_KEY, faction_id);
		return mapRes;
	}

	/**
	 * 玩家申请加入帮会
	 * 
	 * @param role_id
	 * @param faction_id
	 * @return -1:已在该帮会申请列表中 0:网络错误申请失败 1:申请成功
	 * @throws Exception
	 */
	public int applyUnionFaction(long role_id, long faction_id)
			throws Exception {
		HashMap<String, Long> map = new HashMap<String, Long>();
		int res = 0;
		map.put("role_id", role_id);
		map.put("faction_id", faction_id);
		List<Faction> factions = factionDao.selectApplyFactionInfo(map);
		if (factions != null && factions.size() != 0)
			return -1;// 已在对方申请列表中
		if (factionDao.checkAlreadyFaction(role_id) > 0)
		{
		    return -2;//判定该角色是否已在 公会中
		}
		
		long resI = factionDao.getFactionsApplyKeyId();
		map.put("rela_id", resI);
		//放入SQL队列
		SqlToolsService.sqlQueue.put(new DaoVo("factionMap.applyUnionFaction", map, 1));
		if (resI > 0)
			res = 1;

		return res;
	}

	/**
	 * 查询帮会申请列表信息
	 * 
	 * @param flag
	 *            条件标志 1：role_id 2:faction_id
	 * @param conditionId
	 *            条件ID role_id-->查看角色已申请帮会 faction_id-->查看申请加入该帮会的玩家
	 * @return
	 */
	public List<Faction> selectApplyFactionInfo(int flag, long conditionId) {
		List<Faction> applys = null;
		HashMap<String, Long> map = new HashMap<String, Long>();
		if (flag == 1) {
			map.put("role_id", conditionId);
		} else {
			map.put("faction_id", conditionId);
		}
		try {
			applys = factionDao.selectApplyFactionInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applys;
	}

	/**
	 * 批准玩家入会
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param role_id
	 *            入会玩家ID
	 * @param role_name
	 *            玩家角色名
	 * @return -1:玩家已拥有帮会 0:网络错误 >0:操作成功 角色-帮会关联ID
	 * @throws Exception
	 */
	public long addRoleFaction(long faction_id, long role_id, String role_name)
			throws Exception {
		long role_faction_id = 0;

		if (factionDao.checkAlreadyFaction(role_id) > 0)
			return -1l;// 玩家已拥有帮会

		HashMap<String, Object> map = new HashMap<String, Object>();
		String condition = "WHERE t.role_id = " + role_id;
		map.put("role_id", role_id);
		map.put("faction_id", faction_id);
		map.put("faction_position", 0);// 普通会员
		map.put("operator", "+");
		map.put("member_counts", "1");
		map.put("log_info", " " + role_name + "加入了帮会");

		role_faction_id = factionDao.addRoleFaction(map);// 玩家进入该帮会成员列表
		factionDao.deleteApplyFactionInfo(condition);// 删除玩家在帮会申请列表中信息
		factionDao.updateFactionInfo(map);// 人数+1
		factionDao.addFactionLog(map);// 公会记录

		return role_faction_id;
	}

	/**
	 * 拒绝玩家申请
	 * 
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public boolean refuseRoleApplyFaction(long role_id, long guild_id)
			throws Exception {
		String condition = " WHERE t.role_id = " + role_id
				+ " and t.faction_id=" + guild_id;
//		if (factionDao.deleteApplyFactionInfo(condition) > 0)
//			flag = true;
		SqlToolsService.sqlQueue.put(new DaoVo("factionMap.deleteApplyFactionInfo", condition, 3));
		return true;
	}

	/**
	 * 查询帮会成员列表
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param type 查询类型 1全帮成员  2单个成员
	 * @return
	 */
	public List<RoleFaction> getFactionRoles(long keyId,int type) {
		List<RoleFaction> roleFactions = null;
		String condition = "";
		if(1 == type){
			condition = "where t.faction_id ="+keyId;
		}else{
			condition = "where t.role_faction_id = "+keyId;
		}
		try {
			roleFactions = factionDao.getFactionRoles(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roleFactions;
	}

	/**
	 * 按条件查询帮会列表
	 * 
	 * @param conditionFlag
	 *            1:按帮会等级 2：按帮会繁荣度 3：按帮会现役人数
	 * @return
	 */
	public List<Faction> getFactionsByCondition(int conditionFlag) {
		List<Faction> factions = null;
		String condition = "ORDER BY t.faction_lev DESC";
		switch (conditionFlag) {
		case 1:
			condition = "ORDER BY t.faction_lev DESC";
			break;
		case 2:
			condition = "ORDER BY t.faction_score DESC";
			break;
		case 3:
			condition = "ORDER BY t.member_counts DESC";
			break;
		}
		try {
			factions = factionDao.getFactionsByCondition(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factions;
	}

	/**
	 * 根据名字查找帮会
	 * 
	 * @param faction_name
	 * @return
	 */
	public List<Faction> findFactionByName(String faction_name) {
		String condition = "WHERE t.faction_name like '%" + faction_name
				+ "%' ORDER BY t.faction_lev";
		List<Faction> factions = null;
		try {
			factions = factionDao.getFactionsByCondition(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return factions;
	}

	/**
	 * 加载玩家帮会界面 + 玩家权限 + 玩家已领取的奖励等级列表
	 * 
	 * @param role_id
	 * @return -1：查询错误 1：帮主权限 2：管理层权限 3：普通帮众权限
	 */
	public Faction selectFactionRight(long role_id) {
		Faction faction = null;
		try {
			faction = factionDao.selectFactionRight(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return faction;
	}

	/**
	 * 领取帮会奖励
	 * 
	 * @param role_id
	 *            角色ID
	 * @param rewardLev
	 *            最新已领取奖励等级标志
	 * @param goldCount
	 *            奖励金币量
	 * @param diamonCount
	 *            奖励钻石量
	 * @param gemBagsCount
	 *            奖励宝石袋个数
	 * @return
	 * @throws Exception
	 */
	public long getFactionReward(long role_id, Integer rewardLev,
			int goldCount, int diamonCount, int gemBagsCount) throws Exception {
		long resFlag = -1;
		HashMap<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("gold", goldCount);
		roleMap.put("diamon", diamonCount);
		roleMap.put("role_id", role_id);
		roleMap.put("operator", "+");
		roleMap.put("get_reward_flag", rewardLev);

		roleDao.sumPluRoleInfo(roleMap);
		factionDao.updateRoleFactionInfo(roleMap);
		resFlag = propsDao.callAddRoleProps(new RoleProps(role_id, 24,
				gemBagsCount));// 宝石袋进入玩家包裹

		return resFlag;
	}

	/**
	 * 查询玩家当前权限
	 * 
	 * @param role_id
	 * @return -1查询失败 1：帮主 2：副帮主 3：帮众
	 */
	public int selectRoleFactionRight(long role_id) {
		try {
			return factionDao.selectRoleFactionRight(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 玩家退出帮会
	 * 
	 * @param role_id
	 *            玩家ID
	 * @param role_name
	 *            角色名
	 * @param faction_id
	 *            帮会ID
	 * @param isManager
	 *            是否是管理
	 * @return
	 * @throws Exception
	 */
	public boolean quitFaction(long role_id, String role_name, long faction_id,
			boolean isManager) throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		factionDao.deleteRoleFaction(role_id);
		if (isManager) {
			// 删除faction_position_tab数据
			factionDao.deleteFactionPosition(role_id);
		}
		map.put("operator", "-");
		map.put("member_counts", "1");
		map.put("faction_id", faction_id);
		map.put("log_info", " " + role_name + "退出了帮会");

		factionDao.updateFactionInfo(map);// 修改帮会人数

		// 工会记录
		factionDao.addFactionLog(map);

		flag = true;

		return flag;
	}

	/**
	 * 管理剔除帮众
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param manager_role_id
	 *            管理ID
	 * @param manager_name
	 *            管理角色名
	 * @param role_id
	 *            帮众ID
	 * @param role_id
	 *            被踢角色名
	 * @return
	 * @throws Exception
	 */
	public boolean shotOffFaction(long faction_id, long manager_role_id,
			String manager_name, long role_id, String role_name)
			throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();

		factionDao.deleteRoleFaction(role_id);
		// 修改帮会人数
		map.put("operator", "-");
		map.put("member_counts", "1");
		map.put("faction_id", faction_id);
		map.put("log_info", " " + role_name + "被" + manager_name + "踢出了帮会");
		int upRes = factionDao.updateFactionInfo(map);// 修改帮会人数)

		// 工会记录
		long adres = factionDao.addFactionLog(map);
		
		if(upRes > 0 && adres > 0)
			flag = true;
		return flag;
	}

	/**
	 * 修改帮会公告
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param faction_public
	 *            公告内容
	 * @return
	 * @throws Exception
	 */
	public boolean updateFactionPublic(long faction_id, String faction_public)
			throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("faction_id", faction_id);
		map.put("faction_public", faction_public);
		int res = factionDao.updateFactionInfo(map);
		if (res > 0)
			flag = true;

		return flag;
	}

	/**
	 * 帮会升级
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param next_lev
	 *            　升级后等级
	 * @param max_member_counts
	 *            　升级后最大人员数
	 * @param faction_score
	 *            　帮会繁荣度
	 * @param role_id
	 *            　角色ID
	 * @param role_counribution_incNumber
	 *            　角色贡献度增量
	 * @return
	 * @throws Exception
	 */
	public boolean factionLevUp(long faction_id, int next_lev,
			int max_member_counts, int faction_score, long role_id,
			int role_counribution_incNumber) throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("faction_id", faction_id);
		map.put("faction_lev", next_lev);
		map.put("max_member_counts", max_member_counts);
		map.put("faction_score", faction_score);

		map.put("role_id", role_id);
		map.put("contribution", role_counribution_incNumber);
		map.put("operator", "+");
		// 帮会升级
		factionDao.updateFactionInfo(map);
		// 个人贡献度增加
		factionDao.updateRoleFactionInfo(map);

		flag = true;

		return flag;
	}

	/**
	 * 提升帮众职位
	 * 
	 * @param role_id
	 *            被提升角色ID
	 * @param role_name
	 *            被提升角色名
	 * @param faction_id
	 *            帮会ID
	 * @param position_name
	 *            职位名
	 * @param upOrdown
	 *            职位升降标志 1:升 2：降
	 * @return
	 * @throws Exception
	 */
	public boolean upRolePosition(long role_id, String role_name,
			long faction_id, long faction_position_id, String position_name,
			int upOrdown) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;

		map.put("role_id", role_id);

		if (1 == upOrdown) {
			map.put("faction_id", faction_id);
			map.put("faction_position", faction_position_id);
			factionDao.updateRoleFactionInfo(map);

			map.put("log_info", " " + role_name + "被提升为" + position_name);
			factionDao.addFactionLog(map);

		} else {// 降为普通帮众
			map.put("faction_position", 0);
			factionDao.updateRoleFactionInfo(map);
		}

		flag = true;
		return flag;
	}

	/**
	 * 修改管理职位名称
	 * 
	 * @param position_name
	 *            职位名称
	 * @param role_id
	 *            被修改角色ID
	 * @return
	 */
	public boolean updateFactionPosition(String position_name, long role_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role_id", role_id);
		map.put("position_name", position_name);
		try {
			factionDao.updateFactionPosition(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 移交帮主
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @param now_king_id
	 *            现任帮主ID
	 * @param now_king_name
	 *            现任帮主角色名
	 * @param after_king_id
	 *            移交后帮主ID
	 * @param after_king_name
	 *            移交后帮主角色名
	 * @return
	 * @throws Exception
	 */
	public boolean turnKingToOther(long faction_id, long now_king_id,
			String now_king_name, long after_king_id, String after_king_name)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> mapAfter = new HashMap<String, Object>();
		boolean flag = false;
		map.put("role_id", now_king_id);
		map.put("faction_position", 0);

		mapAfter.put("role_id", after_king_id);
		mapAfter.put("faction_position", -1);

		factionDao.updateRoleFactionInfo(map);// 原帮主降为普通帮众

		factionDao.updateRoleFactionInfo(mapAfter);// 新帮主
		map.put("faction_id", faction_id);
		map.put("log_info", " " + now_king_name + "将帮主移交给了" + after_king_name);
		factionDao.addFactionLog(map);// 插入记录

		flag = true;
		return flag;
	}

	/**
	 * 查询帮会事件记录
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @return Faction对象 log_info属性
	 */
	public List<Faction> selectFactionLog(long faction_id) {
		List<Faction> factions = null;
		try {
			factions = factionDao.selectFactionLog(faction_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return factions;
	}

	/**
	 * 解散帮会
	 * 
	 * @param faction_id
	 *            帮会ID
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFaction(long faction_id) throws Exception {
		boolean flag = false;

		int res = factionDao.deleteFaction(faction_id);
		if (res > 0)
			flag = true;
		return flag;
	}

	/**
	 * 新增公会职位
	 * 
	 * @param position_name
	 * @param guild_id
	 * @return
	 * @throws Exception
	 */
	public long addGuildPosition(String position_name, long guild_id)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("faction_id", guild_id);
		map.put("position_name", position_name);

		long faction_position_id = -1;

		faction_position_id = factionDao.addGuildPosition(map);

		return faction_position_id;
	}

	/**
	 * 获取公会职位列表信息
	 * 
	 * @param guild_id
	 * @return
	 */
	public List<Faction> getPositionList(long guild_id) {
		List<Faction> positionList = null;
		// 公会职位
		try {
			positionList = factionDao.getPositionList(guild_id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return positionList;
	}

	/**
	 * 修改职位称呼
	 * 
	 * @param position_id
	 * @param positionName
	 * @return
	 * @throws Exception
	 */
	public boolean editGuildPosition(long position_id, String positionName)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("faction_position_id", position_id);
		map.put("position_name", positionName);

		int resI = factionDao.editGuildPosition(map);
		if (resI > 0)
			return true;

		return false;
	}

	/**
	 * 删除职位
	 * 
	 * @param position_id
	 * @return -1 网络异常 0 公会成员列表中有使用到该职位 1成功
	 * @throws Exception
	 */
	public int deleGuildPosition(long position_id, long guildId)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("faction_position_id", position_id);
		map.put("faction_id", guildId);

		// 判定公会角色是否挂有该职位，如果有，禁止删除
		if (factionDao.getPositionUseInfo(map).getPosition_use_count() > 0) {
			return 0;
		}

		factionDao.deleGuildPosition(position_id);

		return 1;
	}

	/*********************** 内部使用 ******************************/
	/**
	 * 公会签到、公会副本后，更新公会的荣誉度接口
	 * 
	 * @param score
	 * @param roleId
	 * @throws Exception
	 */
	public void addGuildScore(int score, long roleId) throws Exception {
		// 查询玩家所在公会的信息
		Faction guild = factionDao.selectFactionRight(roleId);
		// 如果玩家在公会中则会进行升级判断
		if (guild != null) {
			// 获取当前的荣誉度
			int factionScore = guild.getFaction_score();
			// 荣誉度增加
			factionScore += score;
			// 获取当前等级
			int lev = guild.getFaction_lev();
			// 当前等级的最大成员容量
			int nextLevMembers = guild.getMax_member_counts();
			// 获取升级到下一等级的经验值
			String nextLevInfo = ReadProperties.getProperties("level"
					+ (lev + 1), "guild_lev_up.properties");
			String[] infos = nextLevInfo.split(";");
			int nextLevScore = Integer.parseInt(infos[0]);

			// 如果加分后达到升级条件，则进行升级操作
			if (factionScore >= nextLevScore) {
				// 等级+1
				lev++;
				// 查找最大公会成员数量
				nextLevMembers = Integer.parseInt(infos[1]);
			}
			factionLevUp(guild.getFaction_id(), lev, nextLevMembers,
					factionScore, roleId, score);
		}
	}

}
