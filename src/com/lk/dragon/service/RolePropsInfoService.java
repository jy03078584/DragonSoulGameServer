/** 
 *
 * @Title: RolePropsInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: 角色-道具交互信息业务 
 * @author XiangMZh   
 * @date 2014-9-17 上午10:55:38 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IBuffDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.Equip;
import com.lk.dragon.db.domain.EquipGem;
import com.lk.dragon.db.domain.Gem;
import com.lk.dragon.db.domain.HeroEquip;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.RolePropsEquipPro;
import com.lk.dragon.server.domain.IncDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

/**
 * @Description:角色-道具交互信息业务
 */
@Service
public class RolePropsInfoService {

	@Autowired
	private IRolePropsDao rolePropsDao;

	@Autowired
	private IBuffDao buffDao;
	@Autowired
	private IRoleDao roleDao;

	/** 钻石道具ID **/
	public final static int PROPS_ID_DIAMOND = 276;
	/** 金币道具ID **/
	public final static int PROPS_ID_GOLD = 283;
	/** 木材道具ID **/
	public final static int PROPS_ID_WOOD = 284;
	/** 石料道具ID **/
	public final static int PROPS_ID_STONE = 285;
	/** 粮食道具ID **/
	public final static int PROPS_ID_FOOD = 286;
	/**新手邀请礼盒ID**/
	public final static int PROPS_ID_INVITEED = 273;
	
	/**城邦免战道具ID**/
	public final static int PROPS_ID_CITY_PROTECT = 246;
	

	
	
	public static final String PROPS_COUNT_ERROR_EN = "PROP COUNTS ERROR";
	public static final String PROPS_COUNT_ERROR = "所持物品数量有误";

	public RolePropsInfoService() {
	}

	/**
	 * 宝石合成升级
	 * 
	 * @param role_id
	 *            角色Id
	 * @param role_props_id
	 *            宝石在背包Id
	 * @param buff_type
	 *            宝石类型
	 * @param nextLev
	 *            新合成宝石等级
	 * @param is_success
	 *            合成是否成功 1:是 0:否
	 * @param use_gold
	 *            消费金币
     * @param assPropId
	 *            消费辅助道具ID
	 * @param assNum
	 *            消费的辅助道具数量
	 * @return 合成后宝石信息
	 */
	public Gem gemLevUp(long role_id, long role_props_id, int buff_type,
			int nextLev, int is_success, int use_gold,long assPropId,int assNum) throws Exception {
		Gem gem = null;
		

		int nowGold = roleDao.selectRolesByRoleId(role_id).getGold();// 获取角色当前金币
		int lastGold = nowGold - use_gold;
		if (lastGold < 0)
			return gem;
		
		

		// 更新背包原宝石数量
		RoleProps props = new RoleProps();
		props.setRole_props_id(role_props_id);
		props.setProps_count(rolePropsDao.getPropsCount(role_props_id) - 2);
		rolePropsDao.updatePropsCount(props);
		
		//更新背包辅助道具数量
		if(assPropId > 0 && assNum > 0){
			RoleProps props2 = new RoleProps();
			props2.setRole_props_id(assPropId);
			props2.setProps_count(rolePropsDao.getPropsCount(assPropId) - assNum);
			rolePropsDao.updatePropsCount(props2);
		}
		
		
		// 更新角色剩余金币
		Role role = new Role();
		role.setRole_id(role_id);
		role.setGold(lastGold);
		roleDao.updateRoleInfo(role);

		if (is_success == 1) {// 宝石合成成功
			String condition = "gem_equaitly = " + nextLev
					+ " AND buff_type = " + buff_type;

			// 生成合成后宝石
			gem = rolePropsDao.getGemPropsInfo(condition);
			// 新宝石入包裹
			long gem_role_props_id = rolePropsDao
					.callAddRoleProps(new RoleProps(role_id, gem.getProps_id(),
							1));
			gem.setRole_props_id(gem_role_props_id);
		}

		return gem;
	}

	/**
	 * 销毁 丢弃道具
	 * 
	 * @param props
	 * @return
	 */
	public boolean destoryProps(RoleProps props, long roleId, int gold)
			throws Exception {
		boolean flag = true;
		// 删除数据
		rolePropsDao.deleteProps(props.getRole_props_id());
		rolePropsDao.deleteEquipValue(props.getRole_props_id());

		// 返回回收价格
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("gold", gold);
		map.put("role_id", roleId);
		map.put("operator", "+");
		roleDao.sumPluRoleInfo(map);

		return flag;
	}

	/**
	 * 部分物品使用后绑定
	 * 
	 * @param role_props_id
	 * @return
	 */
	public boolean bindProps(long role_props_id) {
		boolean flag = false;
		try {
			if (rolePropsDao.bindProps(role_props_id) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取道具详细信息
	 * 
	 * @param role_props_id
	 * @return
	 * @throws Exception
	 */
	public RoleProps selectRolePropsDetailById(Map<String, Object> map)
			throws Exception {
		RoleProps props = null;

		props = rolePropsDao.selectRolePropsDetailById(map);

		return props;
	}

	/**
	 * 查询角色道具列表
	 * 
	 * @param role_id
	 * @return
	 */
	public List<RoleProps> selectRolePropsByCondition(long role_id) {
		List<RoleProps> roleProps = null;

		try {
			roleProps = rolePropsDao.selectRolePropsByCondition(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleProps;
	}

	/**
	 * 装备穿卸:修改穿戴标志位与绑定标志位
	 * 
	 * @param is_equiped
	 *            1:装备 0:卸下
	 * @param role_props_id
	 *            道具所在包裹ID
	 * @return
	 * @throws Exception
	 */
	private boolean onOffEquip(int is_equiped, long role_props_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		map.put("is_equiped", is_equiped);
		map.put("role_props_id", role_props_id);

		try {
			if (rolePropsDao.onOffEquip(map) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查看英雄已穿戴装备基本信息
	 * 
	 * @param role_hero_id
	 * @return
	 */
	public List<RolePropsEquipPro> getHeroOnEquipBase(long role_hero_id) {
		List<RolePropsEquipPro> heroEquips = null;

		try {
			heroEquips = rolePropsDao.getHeroOnEquipBase(role_hero_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heroEquips;
	}

	/**
	 * 获取装备属性(含已嵌套宝石)
	 * 
	 * @param role_props_id
	 * @return
	 */
	private RolePropsEquipPro getEquipProperty(long role_props_id) {
		RolePropsEquipPro propsEquipPro = null;

		try {
			propsEquipPro = rolePropsDao.getEquipProperty(role_props_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propsEquipPro;
	}

	/**
	 * 英雄穿卸装备
	 * 
	 * @param role_hero_id
	 *            角色-英雄ID
	 * @param role_props_id
	 *            装备道具ID
	 * @param operFlag
	 *            穿戴标志 1:穿 0:取
	 * @return
	 */
	public boolean onOffHeroEquip(long role_hero_id, long role_props_id,
			int operFlag) {
		boolean flag = false;
		HashMap<String, Object> incProMap = new HashMap<String, Object>();

		RolePropsEquipPro equipPro = getEquipProperty(role_props_id);
		String inc_property = equipPro.getInc_property();
		incProMap = analysisEquipProperty(JSONUtil.getIncObj(inc_property));

		try {

			if (operFlag == 1) {// 穿
				incProMap.put("operator", "+");
				incProMap.put("role_hero_id", role_hero_id);
				rolePropsDao.heroUseEquip(new HeroEquip(role_hero_id,
						role_props_id));
				onOffEquip(1, role_props_id);
				rolePropsDao.bindProps(role_props_id);
			} else {// 取
				incProMap.put("operator", "-");
				incProMap.put("role_hero_id", role_hero_id);
				rolePropsDao.heroOffEquip(role_props_id);
				onOffEquip(0, role_props_id);
			}
			rolePropsDao.updateHeroProperty(incProMap);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 装备镶嵌宝石
	 * 
	 * @param role_props_id
	 *            装备所在包裹ID
	 * @param gem_role_props_id
	 *            宝石所在包裹ID
	 * @return resultMap: RESULT_KEY=1镶嵌成功 RESULT_KEY=-1镶嵌失败 ID_KEY:装备-宝石关联ID
	 */
	public HashMap<String, Long> equipOnGem(long role_props_id,
			long gem_role_props_id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> mapCount = new HashMap<String, Object>();
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		resultMap.put(Constants.RESULT_KEY, -1l);
		resultMap.put(Constants.ID_KEY, -1l);

		map.put("role_props_id", role_props_id);
		String condition = "props_id = (SELECT t2.props_id FROM role_props_tab t2 WHERE t2.role_props_id = "
				+ gem_role_props_id + ")";
		// 装备原有属性
		RolePropsEquipPro equipPro = getEquipProperty(role_props_id);
		String inc_property = equipPro.getInc_property();
		List<IncDomain> incDomainsSrc = JSONUtil.getIncObj(inc_property);
		Gem gem = rolePropsDao.getGemInfo(condition).get(0);
		int gem_type = gem.getBuff_type();
		int gem_value = gem.getGem_buff();
		int gem_props_id = gem.getProps_id();
		// 原属性中是否已有该类型增益效果
		boolean alreadyHas = false;

		for (IncDomain incDomain : incDomainsSrc) {
			if (incDomain.getIncType() == gem_type) {
				incDomain.setValue(incDomain.getValue() + gem_value);
				alreadyHas = true;
				break;
			}
		}
		// 新增类型的增益效果
		if (!alreadyHas)
			incDomainsSrc.add(new IncDomain(gem_type, gem_value));

		// 转换为最新的属性json字符串
		String incStrNew = JSONUtil.getIncStr(incDomainsSrc);
		map.put("inc_property", incStrNew);
		mapCount.put("operator", "-");
		mapCount.put("props_count_vo", 1);
		mapCount.put("role_props_id", gem_role_props_id);
		rolePropsDao.plSubPropsCount(mapCount);
		long idKey = rolePropsDao.addEquipGem(new EquipGem(role_props_id,
				gem_props_id));
		rolePropsDao.updateEquipProperty(map);
		resultMap.put(Constants.RESULT_KEY, 1l);
		resultMap.put(Constants.ID_KEY, idKey);
		return resultMap;
	}

	/**
	 * 装备卸下宝石
	 * 
	 * @param role_props_id
	 *            装备所在包裹ID
	 * @param role_id
	 *            角色ID
	 * @param use_gold
	 *            消耗金币
	 * @param rela_id
	 *            装备-宝石关联ID
	 * @return
	 */
	public int equipOffGem(long role_props_id, long role_id, int use_diamond,
			long rela_id) throws Exception {
		int resFlag = 1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role_props_id", role_props_id);
		String condition = "props_id = (SELECT t2.props_id FROM equip_gem_tab t2 WHERE t2.rela_id ="
				+ rela_id + ")";

		// 装备原有属性
		RolePropsEquipPro equipPro = getEquipProperty(role_props_id);
		String inc_property = equipPro.getInc_property();
		List<IncDomain> incDomainsSrc = JSONUtil.getIncObj(inc_property);
		Gem gem = rolePropsDao.getGemInfo(condition).get(0);
		int gem_type = gem.getBuff_type();
		int gem_value = gem.getGem_buff();
		int gem_props_id = gem.getProps_id();

		for (IncDomain incDomain : incDomainsSrc) {
			if (incDomain.getIncType() == gem_type) {
				// 取下该宝石后 装备该增益类型的剩余数值
				int lastValue = incDomain.getValue() - gem_value;
				if (lastValue > 0) {
					// 取下宝石后 该装备仍然有该类型的增益
					incDomain.setValue(lastValue);
				} else {
					// 取下宝石后 该装备不再有该类型的增益
					incDomainsSrc.remove(incDomain);
				}
				break;
			}

		}
		String incStrNew = JSONUtil.getIncStr(incDomainsSrc);
		map.put("inc_property", incStrNew);
		Role role = new Role();
		role.setRole_id(role_id);
		role.setDiamon(roleDao.selectRolesByRoleId(role_id).getDiamon()
				- use_diamond);
		condition = " t.rela_id = " + rela_id;
		roleDao.updateRoleInfo(role);// 消耗金币
		resFlag = rolePropsDao.callAddRoleProps(new RoleProps(role_id,gem_props_id, 1));// 退还宝石
		rolePropsDao.updateEquipProperty(map);// 卸下宝石后更新装备属性
		rolePropsDao.deleteEquipGem(condition);// 删除装备-宝石中间表数据
		return resFlag;
	}

	/**
	 * 使用宝石袋随机宝石
	 * 
	 * @param role_id
	 *            角色ID
	 * @param gem_level
	 *            宝石等级
	 * @param gem_type
	 *            宝石类型
	 * @param role_props_id
	 *            宝石袋ID
	 * @param is_enough
	 *            宝石袋是否还有剩余
	 * @return
	 */
	public Gem usePropsGetGem(long role_id, int gem_level, int gem_type,
			long role_props_id, int is_enough) throws Exception {
		Gem gem = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String condition = "gem_equaitly = " + gem_level + " AND buff_type = "
				+ gem_type;
		gem = rolePropsDao.getGemPropsInfo(condition);
		long newGemId = rolePropsDao.callAddRoleProps(new RoleProps(role_id,
				gem.getProps_id(), 1));
		gem.setRole_props_id(newGemId);
		if (is_enough == 0) {
			rolePropsDao.deleteProps(role_props_id);// 删除道具
		} else {
			map.put("operator", "-");
			map.put("props_count_vo", 1);
			map.put("role_props_id", role_props_id);
			rolePropsDao.plSubPropsCount(map);// 更新道具数量
		}
		return gem;
	}

	

	/**
	 * 英雄进入训练室
	 * 
	 * @param roleHero
	 * @return
	 */
	public boolean addHeroTraiin(RoleHero roleHero, int type) throws Exception {
		boolean flag = true;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("operator", "-");
		map.put("role_id", roleHero.getRole_id());
		flag = rolePropsDao.addHeroTrain(roleHero);

		map.put("role_hero_id", roleHero.getRole_hero_id());
		map.put("is_free", 6);// 更改英雄状态
		rolePropsDao.updateHeroProperty(map);
		if (type == 1) {// 方式金币
			map.put("gold", roleHero.getUse_gold_dimaon());
		} else {// 钻石方式
			map.put("diamon", roleHero.getUse_gold_dimaon());
		}
		// 消耗资源
		roleDao.sumPluRoleInfo(map);
		return flag;
	}

	/**
	 * 查看当前城邦 训练室中英雄信息
	 * 
	 * @param city_id
	 * @return
	 */
	public List<RoleHero> getHeroTrainInfo(String city_id) {
		List<RoleHero> heros = null;
		try {
			heros = rolePropsDao.getHeroTrainInfo(" t.city_id=" + city_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heros;
	}

	/**
	 * 中断训练
	 * 
	 * @return
	 */
	public List<RoleHero> cancelHeroTrain(long roleHeroId, long city_id)
			throws Exception {
		List<RoleHero> cancelHeros = null;
		String condition = "t.city_id = " + city_id;
		if (roleHeroId > 0)//
			condition += " and t.role_hero_id = " + roleHeroId;
		cancelHeros = rolePropsDao.getHeroTrainInfo(condition);
		rolePropsDao.cancelHeroTrain(condition);
		for (RoleHero roleHero : cancelHeros) {
			double exp = roleHero.getAlready_train()
					* ((double) roleHero.getPre_exp() / 3600);
			roleHero.setTrain_exp((int) Math.floor(exp));
		}
		rolePropsDao.updateHerosStatus(" t.is_free = 1 where " + condition
				+ " and t.is_free = 6");
		return cancelHeros;

	}

	/**
	 * 根据id查询道具信息
	 * 
	 * @param rolePropId
	 */
	public RoleProps getRolePropsById(long rolePropId) {
		try {
			String condition = " role_props_id = " + rolePropId;
			return rolePropsDao.getRolePropsInfo(condition).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取系统奖励
	 * 
	 * @param props_id
	 * @return
	 */
	public String getSysReward(int props_id) {
		String info = "";
		try {
			info = rolePropsDao.getSysReward(props_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 开启宝箱
	 * 
	 * @param reward_id
	 *            礼包ID
	 * @param isRandom
	 *            是否是随机礼包
	 * @param randCnt
	 *            礼包随机次数
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> doSysRewardDetail(int reward_id, int isRandom,
			long role_id, int randCnt, long role_props_id) throws Exception {
		List<RoleProps> rewards = new ArrayList<RoleProps>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("reward_id", reward_id);

		// 随机道具礼包
		if (isRandom == 1) {
			for (int i = 0; i < randCnt; i++) {
				// 多次随机礼包
				int rateRandom = new Random().nextInt(100);// 0-99
				map.put("rand", rateRandom);
				rewards.add(rolePropsDao.getSysRewardDetail(map).get(0));
			}

		} else {// 固定道具礼包
			map.put("rand", 0);
			// 获得对应奖励物品
			rewards = rolePropsDao.getSysRewardDetail(map);
		}

//		// 处理资源类奖励Map对象
//		HashMap<String, Object> map2 = new HashMap<String, Object>();
//		map2.put("operator", "+");// $operator$ #diamon#
//		map2.put("role_id", role_id);
//
//		// 道具进入玩家包裹
//		for (RoleProps roleProps : rewards) {
//
//			int propId = roleProps.getProps_id();
//
//			// 定义资源类道具rolePropId:-100 钻石 -101木材 -102粮食 -103石料 -104金币
//			switch (propId) {
//			case PROPS_ID_DIAMOND:
//				// 钻石奖励
//				map2.put("diamon", roleProps.getProps_count());
//				roleProps.setRole_props_id(-100l);
//				roleDao.sumPluRoleInfo(map2);
//				break;
//			case PROPS_ID_FOOD:
//				// 粮食奖励
//				map2.put("food", roleProps.getProps_count());
//				roleProps.setRole_props_id(-102l);
//				roleDao.sumPluRoleInfo(map2);
//				break;
//			case PROPS_ID_WOOD:
//				// 木材奖励
//				map2.put("wood", roleProps.getProps_count());
//				roleProps.setRole_props_id(-101l);
//				roleDao.sumPluRoleInfo(map2);
//				break;
//			case PROPS_ID_GOLD:
//				// 金币奖励
//				map2.put("gold", roleProps.getProps_count());
//				roleProps.setRole_props_id(-104l);
//				roleDao.sumPluRoleInfo(map2);
//				break;
//			case PROPS_ID_STONE:
//				// 石料奖励
//				map2.put("stone", roleProps.getProps_count());
//				roleProps.setRole_props_id(-103l);
//				roleDao.sumPluRoleInfo(map2);
//				break;
//			default:
//				// 非资源类奖励 进入玩家包裹
//				roleProps.setRole_id(role_id);
//				if(roleProps.getProps_type() == Constants.PROP_EQUIP)
//					roleProps.setExtra_info(makeEquipRandProperty(roleProps.getProps_id()));
//				int rolePropId = rolePropsDao.callAddRoleProps(roleProps);
//				if (rolePropId <= 0 && rolePropId != -10) {
//					throw new RuntimeException();
//				}
//				roleProps.setRole_props_id((long) rolePropId);
//				break;
//			}
//
//		}

		rewards = anayAddRoleProps(rewards, role_id);
		// 是否是打开包裹中的礼包
		if (role_props_id > 0) {
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put("operator", "-");
			map2.put("props_count_vo", 1);
			map2.put("role_props_id", role_props_id);// 礼包数量减1 减为0后由 触发器删掉对应数据
			rolePropsDao.plSubPropsCount(map2);
				
		}
		return rewards;
	}

	/**
	 * 对奖励进行分类处理
	 * @param rolePropsList
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> anayAddRoleProps(List<RoleProps> rolePropsList,long role_id) throws Exception{
		// 处理资源类奖励Map对象
				HashMap<String, Object> map2 = new HashMap<String, Object>();
				map2.put("operator", "+");// $operator$ #diamon#
				map2.put("role_id", role_id);

				// 道具进入玩家包裹
				for (RoleProps roleProps : rolePropsList) {

					int propId = roleProps.getProps_id();

					// 定义资源类道具rolePropId:-100 钻石 -101木材 -102粮食 -103石料 -104金币
					switch (propId) {
					case PROPS_ID_DIAMOND:
						// 钻石奖励
						map2.put("diamon", roleProps.getProps_count());
						roleProps.setRole_props_id(-100l);
						roleDao.sumPluRoleInfo(map2);
						break;
					case PROPS_ID_FOOD:
						// 粮食奖励
						map2.put("food", roleProps.getProps_count());
						roleProps.setRole_props_id(-102l);
						roleDao.sumPluRoleInfo(map2);
						break;
					case PROPS_ID_WOOD:
						// 木材奖励
						map2.put("wood", roleProps.getProps_count());
						roleProps.setRole_props_id(-101l);
						roleDao.sumPluRoleInfo(map2);
						break;
					case PROPS_ID_GOLD:
						// 金币奖励
						map2.put("gold", roleProps.getProps_count());
						roleProps.setRole_props_id(-104l);
						roleDao.sumPluRoleInfo(map2);
						break;
					case PROPS_ID_STONE:
						// 石料奖励
						map2.put("stone", roleProps.getProps_count());
						roleProps.setRole_props_id(-103l);
						roleDao.sumPluRoleInfo(map2);
						break;
					default:
						// 非资源类奖励 进入玩家包裹
						roleProps.setRole_id(role_id);
						if(roleProps.getProps_type() == Constants.PROP_EQUIP)
							roleProps.setExtra_info(makeEquipRandProperty(roleProps.getProps_id()));
						int rolePropId = rolePropsDao.callAddRoleProps(roleProps);
						if (rolePropId <= 0 && rolePropId != -10) {
							throw new RuntimeException();
						}
						roleProps.setRole_props_id((long) rolePropId);
						break;
					}

				}
			return rolePropsList;
	}
	
	/**
	 * 构造装备随机属性
	 * 
	 * @param props_id
	 * @return
	 */
	public String makeEquipRandProperty(int props_id) {

		String equip_pro = "";
		// 获取该装备基础属性
		try {
			Equip dropEquip = rolePropsDao.getEquipBaseInfo(props_id);
			// 坐骑类装备无随机属性
			if (dropEquip.getEquip_location() != Constants.EQUIP_LOCATION_MOUNT) {

				int dropEquipQuality = dropEquip.getQuality();// 装备品质

				// 非白色品质装备 随机属性
				if (dropEquipQuality > 0) {

					String dropEquipBaseProperty = dropEquip.getInc_property();// 装备原始属性
					int dropEquipLevel = dropEquip.getCommand_lev();// 装备所需等级

					List<IncDomain> incList = JSONUtil
							.getIncObj(dropEquipBaseProperty);// 属性转化为java对象
					int time = dropEquipQuality < 4 ? dropEquipQuality : 3;// 根据品质决定装备随机属性条数最大3条随机属性

					// 随机增幅基础属性 1-5
					for (int j = 0; j < time; j++) {
						// 随机选取增益类型
						int buffType = new Random().nextInt(5) + 1;
						// 随机选取增益数值
						int randomMin = (int) Math
								.ceil((double) (dropEquipQuality * dropEquipLevel)
										/ (3 + dropEquipQuality));
						int buffValue = new Random().nextInt((int) Math
								.ceil(0.5 * randomMin)) + randomMin;
						// 构建本轮随机属性对象
						IncDomain domain = new IncDomain(buffType, buffValue);
						// 加入属性列表
						incList.add(domain);
					}
					// 随机结束 IncDomain对象转为属性字符串
					equip_pro = JSONUtil.getIncStr(incList);
				} else {
					// 白色品质装备无随机属性
					equip_pro = dropEquip.getInc_property();
				}
			}// --非坐骑类装备
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return equip_pro;
	}

	/**
	 * 解析装备属性
	 * 
	 * @param incDomains
	 * @return
	 */
	public HashMap<String, Object> analysisEquipProperty(List<IncDomain> incDomains) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (IncDomain incDomain : incDomains) {
			switch (incDomain.getIncType()) {
			case 1:
				map.put(Constants.MENTALITY, incDomain.getValue());
				break;
			case 2:
				map.put(Constants.HERO_POWER, incDomain.getValue());
				break;
			case 3:
				map.put(Constants.ENDURANCE, incDomain.getValue());
				break;
			case 4:
				map.put(Constants.AGILITY, incDomain.getValue());
				break;
			case 5:
				map.put(Constants.PHYSIQUE, incDomain.getValue());
				break;
			case 6:
				map.put(Constants.PHYSIC_ATTACK, incDomain.getValue());
				break;
			case 8:
				map.put(Constants.PHYSIC_DEFENCE, incDomain.getValue());
				break;
			case 7:
				map.put(Constants.MAGIC_ATTACK, incDomain.getValue());
				break;
			case 9:
				map.put(Constants.MAGIC_DEFENCE, incDomain.getValue());
				break;
			case 10:
				map.put(Constants.MAX_HP, incDomain.getValue());
				break;
			case 11:
				map.put(Constants.SPEED, incDomain.getValue());
				break;
			case 12:
				map.put(Constants.DISTANCE_ATTACK, incDomain.getValue());
				break;
			case 13:
				map.put(Constants.DISTANCE_MOVE, incDomain.getValue());
				break;
			case 14:
				map.put(Constants.MAX_MP, incDomain.getValue());
				break;
			case 15:
				map.put(Constants.SPEED_INMAP, incDomain.getValue());
				break;
			default:
				break;
			}
		}

		return map;
	}

	//================================【系统BUFF模块】=========================
	/**
	 * 使用道具获得BUFF
	 * @param tagId
	 * @param buff_id
	 * @param role_props_id
	 * @return 剩余秒数
	 * @throws Exception
	 */
	public HashMap<String,Object> insertNewBuff(long tagId,int buff_id,long role_props_id) throws Exception{
		HashMap<String,Object> resMap = new HashMap<String, Object>();
		Buff buff = new Buff(buff_id, tagId);
		//判断角色是否已有该BUFF
		if(buffDao.checkTagInBuff(buff) > 0 ){
			resMap.put("result", -2);
			return resMap;
		}
		//获取消耗道具数量
		Integer count = rolePropsDao.getPropsCount(role_props_id);
		if(count == null || (count - 1) < 0){
			resMap.put("result", -1);
			return resMap;
		}
		//更新道具数量
		rolePropsDao.updatePropsCount(new RoleProps(role_props_id, count - 1));
		//获得BUFF效果
		buffDao.insertNewBuff(buff);
		resMap.put("result", 1);
		resMap.put("data", getBuffInfo(buff).get(0));
		return resMap;
	}
	
	/**
	 * 在线期间:BUFF时间结束
	 * @param tagId
	 * @param buff_id
	 * @throws Exception 
	 */
	public void buffFinish(long tagId,int buff_id) throws Exception{
		buffDao.deleteBuff(new Buff(buff_id, tagId));
	}
	
	/**
	 * 查询目标BUFF信息
	 * @param buff--> buff_typ或 buff_id
	 * @return
	 * @throws Exception
	 */
	public List<Buff> getBuffInfo(Buff buff) throws Exception{
		return buffDao.getBuffInfo(buff);
	}
}
