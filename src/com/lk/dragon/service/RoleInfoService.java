/** 
 *
 * @Title: RoleInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: 角色基本信息业务层
 * @author XiangMZh   
 * @date 2014-9-5 下午12:02:24 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IBuffDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.FileUtil;
import com.lk.dragon.util.ReadProperties;

/**
 * @Description:角色基本信息业务层
 */
@Service
public class RoleInfoService {

	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IRolePropsDao rolePropsDao;
	@Autowired
	private IBuffDao buffDao;
	@Autowired
	private ICityBuildDao cityBuildDao;
	@Autowired
	private RolePropsInfoService rolePropsInfoService;

	public RoleInfoService() {

	}
	
	public static final String TAXATION_DAILY_KEY = "taxation";
	public static final String DIAMOND_DAILY_KEY = "diamond";
	

	/** 角色名被占用 **/
	public static final int ROLE_NAME_USED = 2;

	// 角色签到宝箱对应ID
	public static final int SIGN_LOCAL1_ID = 277;
	public static final int SIGN_LOCAL2_ID = 278;
	public static final int SIGN_LOCAL3_ID = 279;
	public static final int SIGN_LOCAL4_ID = 280;
	public static final int SIGN_LOCAL5_ID = 281;
	public static HashMap<String, Integer> SIGN_PROPID_MAP = new HashMap<String, Integer>();
	static {
		SIGN_PROPID_MAP.put("1", SIGN_LOCAL1_ID);
		SIGN_PROPID_MAP.put("2", SIGN_LOCAL2_ID);
		SIGN_PROPID_MAP.put("3", SIGN_LOCAL3_ID);
		SIGN_PROPID_MAP.put("4", SIGN_LOCAL4_ID);
		SIGN_PROPID_MAP.put("5", SIGN_LOCAL5_ID);

	}

	/**
	 * 新建游戏角色
	 * 
	 * @param role
	 *            包含role_name的Role对象
	 * @return 新建结果码
	 */
	public HashMap<String, Long> createRole(Role role) {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		resultMap.put(Constants.RESULT_KEY, Constants.CREATE_ROLE_FAIL);
		resultMap.put(Constants.ID_KEY, -1l);
		if (role != null) {
			try {
				if (roleDao.checkRolesCountByUserId(role) >= Constants.ROLES_MAX) {// 可创角色数已满
					resultMap.put(Constants.RESULT_KEY,
							Constants.CREATE_ROLE_NUM_MAX);
					resultMap.put(Constants.ID_KEY, -1l);
					return resultMap;
				}
				Role tempRole = roleDao.selectRoleByRoleName(role);
				if (tempRole != null) {
					resultMap.put(Constants.RESULT_KEY,
							Constants.CREATE_ROLE_NAMEREPEAT);
					resultMap.put(Constants.ID_KEY, -1l);
				} else {
					long roleId = roleDao.createRole(role);
					resultMap.put(Constants.RESULT_KEY,Constants.CREATE_ROLE_SUCCESS);
					resultMap.put(Constants.ID_KEY, roleId);
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return resultMap;
	}

	/**
	 * 查询当前服务器已创建角色
	 * 
	 * @param role
	 *            包含user_id的Role对象
	 * @return
	 */
	public Role findRolesByUserId(long user_id) {
		Role roleRes = null;
		try {
				roleRes = roleDao.selectRoles(user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleRes;
	}

	/**
	 * 修改角色在线状态 属性is_online 1:在线状态 0:离线状态
	 * 
	 * @param role
	 * @return
	 */
	public boolean updateOnLineStatus(Role role) {
		boolean flag = false;
		try {
			if (roleDao.updateOnLineStatus(role) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取角色登录状态相关信息
	 * 
	 * @param role_id
	 * @return
	 */
	public Role getRoleOnLineStatus(long role_id) {
		Role role = null;
		try {
			role = roleDao.selectRoleOnLineStatus(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return role;
	}

	/**
	 * 修改角色名 
	 * 
	 * @param role
	 * @return map
	 * @throws Exception
	 */
	public int changeRoleName(Role role, long rolePropId) throws Exception {
		int res = -1;
		
		//消耗更名道具
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("operator", "-");
		map.put("props_count_vo", 1);
		map.put("role_props_id", rolePropId);
		rolePropsDao.plSubPropsCount(map);
		
		//更改合法的角色名
		if (roleDao.selectRoleByRoleName(role) != null) {
			res = ROLE_NAME_USED;
		} else {
			if (roleDao.updateRoleName(role) > 0)
				res = 1;
		}
		System.out.println("role change anme2");
		return res;
	}

	/**
	 * 修改用户头像 ：各尺寸头像 需先RolePropsInfoService.checkPropsEnabled()判定此次使用的道具是否存在
	 * 
	 * @param role
	 * @return map
	 */
	public HashMap<String, Long> changeRoleIcon(Role role) {
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put(Constants.RESULT_KEY, Constants.UPDATE_FAIL);
		try {
			if (role != null) {
				if (roleDao.updateRoleIcon(role) > 0)
					map.put(Constants.RESULT_KEY, Constants.UPDATE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 更新角色游戏数据
	 * 
	 * @param role
	 * @return true:更改角色信息成功 false:更改角色信息失败
	 */
	public boolean updateRoleInfo(Role role) {
		boolean tag = false;
		try {
			if (roleDao.updateRoleInfo(role) > 0)
				tag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 删除角色信息
	 * 
	 * @param role
	 * @return true:删除成功 false:删除失败
	 * @throws Exception
	 */
	public boolean deleteRole(Role role) throws Exception {
		boolean tag = false;
		/*************************
		 * 此处为后期同一事务中删除角色其他相关信息代码段
		 ********************************************/
		if (roleDao.deleteRole(role) > 0)
			tag = true;
		return tag;
	}

	

	/**
	 * 根据角色名查询角色Id
	 * 
	 * @param role_name
	 * @return
	 */
	public long selectRoleIdByName(String role_name) {
		Object role_id;
		try {
			role_id = roleDao.selectRoleIdByName(role_name);
			if (role_id != null)
				return (Long) role_id;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1l;
	}

	/**
	 * 使用钻石购买包裹
	 * 
	 * @param role_id
	 *            角色ID
	 * @return KEY: Constants.USE_DIAMON消耗钻石数 Constants.ADD_BAG_COUNT增加包裹个数
	 * @throws Exception
	 */
	public HashMap<String, Integer> usePropsAddBags(long role_id) throws Exception {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String filePath = System.getProperty("user.dir")
				+ "\\properties\\props_diamon.properties";
		int use_diamon = Integer.parseInt(FileUtil.getValue(filePath,
				"bag_use_diamon"));// 单次购买消耗钻石量
		int add_bag_count = Integer.parseInt(FileUtil.getValue(filePath,
				"bag_add_count"));// 单次购买增加格子数
		int bags_max = Integer.parseInt(FileUtil.getValue(filePath, "bag_max"));// 背包最大容量

		Role role = roleDao.selectRolesByRoleId(role_id);
		role.setRole_id(role_id);
		role.setDiamon(role.getDiamon() - use_diamon);
		if ((role.getBags() + add_bag_count) >= bags_max) {
			role.setBags(bags_max);
			map.put(Constants.ADD_BAG_COUNT, bags_max - role.getBags());
		} else {
			role.setBags(role.getBags() + add_bag_count);
			map.put(Constants.ADD_BAG_COUNT, add_bag_count);
		}
		map.put(Constants.USE_DIAMON, use_diamon);
		roleDao.updateRoleInfo(role);
		return map;
	}

	/**
	 * 角色升级接口
	 * 
	 * @param roleId
	 *            当前角色id
	 * @param exp
	 *            当前角色获取经验值
	 * @return
	 */
	public boolean roleUpgrade(long roleId, int exp) {
		boolean flag = false;
		try {
			// 查询当前角色相关信息
			Role role = roleDao.selectRolesByRoleId(roleId);
			int lev = role.getLev();
			int currExp = role.getExp();
			currExp += exp;

			// 获取下一级所需经验值
			int next_exp = Integer.parseInt(ReadProperties.getProperties(
					"level" + (lev + 1), "role_lev_up.properties"));

			if (currExp >= next_exp) {
				lev++;
			}

			// 更新角色信息
			Role updateRole = new Role();
			updateRole.setRole_id(roleId);
			updateRole.setExp(currExp);
			updateRole.setLev(lev);
			roleDao.updateRoleInfo(updateRole);

			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flag;
	}

	/**
	 * 重置角色在线状态
	 */
	public void resetRoleOnlineStatus() {
		try {
			roleDao.resetRoleStatus();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 角色开启签到宝箱
	 * 
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> doRoleChestGetReward(long role_id, int local)
			throws Exception {
		// 对应宝箱ID
		int reward_id = SIGN_LOCAL1_ID;
		List<RoleProps> rwardsList = null;

		// 获取该宝箱的道具ID
		reward_id = SIGN_PROPID_MAP.get(local + "");

		// 执行开启宝箱操作 奖励进入包裹
		rwardsList = rolePropsInfoService.doSysRewardDetail(reward_id, 0,
				role_id, 0, -1);

		// 查看该角色签到情况
		String chests = roleDao.selectRoleChest(role_id).getChests();

		// 更新领取后签到数据
		JSONArray array = JSONArray.fromObject(chests);
		Role role = new Role();
		int localItem = 1;
		JSONObject o;
		for (int i = 0; i < array.size(); i++) {
			 o = array.getJSONObject(i);
			localItem = o.getInt("loca");
			if(localItem == local){
				o.put("flag", 1);
				//判断是否为最大签到奖励
				if(localItem == 5){
					//开启并记录新一轮签到
					role.setSum_login_count(1);
					//开启新一轮奖励
					refreshChests(array);
				}
				break;
			}
		}
		// 构造角色最新签到信息
		chests = array.toString();

		// 更新角色签到数据
		role.setChests(chests);
		role.setRole_id(role_id);
		roleDao.updateRoleChest(role);

		return rwardsList;
	}

	/**
	 * 每日首登奖励
	 * @param role_id
	 * @throws Exception 
	 */
	public Map<String,Object> rewardLoginDaily(long role_id) throws Exception{
		Map<String,Object> loginMap = new HashMap<String, Object>();
		int eatCity = cityBuildDao.getCityEatByRoleId(role_id);
		//根据城邦总人口获取本日税收
		int taxationDaily = eatCity * 5;
		//更新角色数据
		loginMap.put("operator", "+");
		loginMap.put("diamon", 5);
		loginMap.put("gold", taxationDaily);
		loginMap.put("role_id", role_id);
		roleDao.sumPluRoleInfo(loginMap);
		
		
		loginMap.put(TAXATION_DAILY_KEY, taxationDaily);
		loginMap.put(DIAMOND_DAILY_KEY, 5);
		
		return loginMap;
	}
	
	/**
	 * 重置签到奖励(其中上轮已达条件但未领取的不变)
	 * flag=0表示当前可以领取 -1表示条件尚未满足 1表示已经领取
	 * @param jsonArray
	 */
	private void refreshChests(JSONArray chestAJ){
		JSONObject jsonObject;
		for (int i = 0; i < chestAJ.size(); i++) {
			jsonObject = chestAJ.getJSONObject(i);
			int flag = jsonObject.getInt("flag");
			if(flag != 0 )
				jsonObject.put("flag", -1);
				
		}
	}
	
	/**
	 * 查看签到宝箱奖励
	 * 
	 * @param role_id
	 * @return
	 */
	public String getRoleChest(long role_id) {
		try {
			return roleDao.selectRoleChest(role_id).getChests();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public boolean updateRoleChest(Role role) {
		try {
			roleDao.updateRoleChest(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
