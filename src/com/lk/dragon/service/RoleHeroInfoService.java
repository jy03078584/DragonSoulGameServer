/** 
 *
 * @Title: RoleHeroInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-18 上午11:54:35 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Hero;
import com.lk.dragon.db.domain.HeroEquip;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RolePropsEquipPro;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.FileUtil;
import com.lk.dragon.util.ReadProperties;

/**
 * @Description:
 */
@Service
public class RoleHeroInfoService {

	@Autowired
	private IRolePropsDao rolePropsDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private ICityBuildDao cityBuildDao;
	
	@Autowired
	private IArmsDeployDao armsDeployDao;

	public static final String HERO_REV_TIME = "revSecond";
	public static final String HERO_REV_GOLD = "revGold";

	public RoleHeroInfoService() {
	}

	/**
	 * 确定招募英雄
	 * 
	 * @param role_id
	 *            角色ID
	 * @param hero_id
	 *            英雄ID
	 * @param physique
	 *            体质
	 * @param mentality
	 *            智力
	 * @param hero_power
	 *            力量
	 * @param endurance
	 *            耐力
	 * @param agility
	 *            敏捷
	 * @param hire_gold
	 *            招募金币
	 * @return -1:网络错误 -2：该位置的英雄已刷新 >0：新招募英雄role_hero_id
	 */
	public long hireNewHero(long role_id, int hero_id, int physique,
			int mentality, int hero_power, int endurance, int agility,
			int hire_gold) throws Exception {

		long role_hero_id = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("role_id", role_id);
		map.put("operator", "-");
		map.put("gold", hire_gold);
		map.put("hero_id", hero_id);

		// 该ID在当前数据库中信息
		Hero heroDb = rolePropsDao.checkHeroBasePro(hero_id);
		// 判断英雄是否已刷新
		if (heroDb.getPhysique() != physique
				|| heroDb.getMentality() != mentality
				|| heroDb.getHero_power() != hero_power
				|| heroDb.getEndurance() != endurance
				|| heroDb.getAgility() != agility) {
			return -2l;// 该位置的英雄已刷新
		}

		role_hero_id = rolePropsDao.hireNewHero(map);// 角色-英雄表新增数据
		rolePropsDao.updateHireFlag(hero_id);// 修改招募英雄 已被招募标志
		roleDao.sumPluRoleInfo(map);// 消耗招募金币
		return role_hero_id;
	}

	/**
	 * 使用道具刷新可招募英雄
	 * 
	 * @param city_id
	 *            城市ID
	 * @param role_id
	 *            角色ID
	 * @param use_diamon
	 *            消耗钻石数
	 * @return
	 */
	public List<Hero> usePropsFlushHeros(long city_id, long role_id,
			int use_diamon) throws Exception {
		HashMap<String, Object> map;
		List<Hero> heros = null;

		rolePropsDao.callFlushHiredHeros(city_id);// 重新刷新该城市可招募列表
		heros = rolePropsDao.getHeroCanHired(city_id);// 获取城市可招募英雄

		// 消耗钻石
		map = new HashMap<String, Object>();

		map.put("operator", "-");
		map.put("diamon", use_diamon);
		map.put("role_id", role_id);
		roleDao.sumPluRoleInfo(map);

		return heros;
	}

	/**
	 * 英雄使用药品 回复血/蓝
	 * 
	 * @param role_hero_id
	 *            英雄ID
	 * @param role_props_id
	 *            药品所在包裹ID
	 * @param addNumber
	 *            药品效果 增益量
	 * @param h_m
	 *            药品类型 1：血药 2:魔法药
	 * @param has_enough
	 *            道具是否还有剩余 1:是 0:否
	 * @return
	 */
	public boolean usePropsAddHpMp(long role_hero_id, long role_props_id,
			int addNumber, int h_m) throws Exception {
		boolean flag = true;
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// String hero_properties = "hp,mp,max_hp,max_mp";
		String condition = " role_hero_id = " + role_hero_id;
		// conditionMap.put("hero_properties", hero_properties);
		conditionMap.put("condition", condition);

		RoleHero hero = rolePropsDao.getHeroPropertyByCondition(condition).get(0);
		conditionMap.clear();

		conditionMap.put("operator", "+");
		conditionMap.put("role_hero_id", role_hero_id);
		if (1 == h_m) {// 血药
			if ((addNumber + hero.getHp()) >= hero.getMax_hp()) {
				conditionMap.put("hp", hero.getMax_hp() - hero.getHp());

			} else {
				conditionMap.put("hp", addNumber);
			}
		} else {// 魔法药
			if ((addNumber + hero.getMp()) >= hero.getMax_mp()) {
				conditionMap.put("mp", hero.getMax_mp() - hero.getMp());

			} else {
				conditionMap.put("mp", addNumber);
			}
			throw new RuntimeException();// 抛出运行时异常 事务回滚
		}

		rolePropsDao.updateHeroProperty(conditionMap);// 更新英雄属性
		map.put("operator", "-");
		map.put("props_count_vo", 1);
		map.put("role_props_id", role_props_id);
		rolePropsDao.plSubPropsCount(map);// 更新道具数量

		return flag;
	}

	/**
	 * 英雄名字更改
	 * 
	 * @param name
	 * @return
	 */
	public boolean changeHeroName(String name, long role_hero_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		map.put("hero_name", name);
		map.put("role_hero_id", role_hero_id);
		try {
			rolePropsDao.updateHeroProperty(map);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查看该城市能招募英雄列表
	 * 
	 * @param city_id
	 * @return
	 */
	public List<Hero> getHeroCanHired(long city_id) {
		List<Hero> cityHeros = null;
		try {
			cityHeros = rolePropsDao.getHeroCanHired(city_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cityHeros;
	}

	/**
	 * 获取招募英雄详细属性
	 * 
	 * @param hero_id
	 *            英雄ID
	 * @return
	 */
	public Hero getHeroBaseProperties(int hero_id) {
		Hero hero = null;
		String condition = "t.hero_id = " + hero_id;
		try {
			hero = rolePropsDao.getHeroBaseProperties(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hero;
	}

	/**
	 * 查询角色拥有英雄列表
	 * 
	 * @param role_id
	 *            角色ID
	 * @return
	 */
	public List<RoleHero> getHerosByRoleId(long role_id) {
		List<RoleHero> heros = null;
		// String hero_properties =HERO_PROPER_FIELS +
		// ",to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') AS now_db";
		String condition = " role_id = " + role_id;
		try {
			heros = rolePropsDao.getHeroPropertyByCondition(condition);
			if (heros != null && heros.size() > 0) {
				// 转化主副属性
				for (RoleHero roleHero : heros) {
					calculateHeroPropertyShow(roleHero);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return heros;

	}

	/**
	 * 查询驻守城邦的英雄列表
	 * 
	 * @param city_id
	 *            城邦ID
	 * @return
	 */
	public List<RoleHero> getHerosByCityId(long city_id) {
		List<RoleHero> heros = null;
		// String hero_properties = HERO_PROPER_FIELS;
		String condition = " t.now_cityid = " + city_id + " and t.is_free <> "
				+ Constants.HERO_STATUS_QUARTER;
		try {
			heros = rolePropsDao.getHeroPropertyByCondition(condition);
			if (heros != null && heros.size() != 0) {
				// 转化主副属性
				for (RoleHero roleHero : heros) {
					calculateHeroPropertyShow(roleHero);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heros;
	}

	/**
	 * 流放英雄
	 * 
	 * @param role_hero_id
	 *            角色-英雄ID
	 * @return -1  配备有部队或携带装备的英雄无法解雇
	 * @throws Exception
	 */
	public int fireHero(long role_hero_id) throws Exception {
		//判断英雄当前是否配备部队
		boolean hasArms =  armsDeployDao.checkHeroHasArmsNow(role_hero_id) == 0 ? false : true;
		if(hasArms)
			return -1;
		//判断英雄是否穿戴装备
		List<RolePropsEquipPro> equips = rolePropsDao.getHeroOnEquipBase(role_hero_id);
		if(equips.size() > 0)
			return -1;
		String condition = " role_hero_id = " + role_hero_id;
		rolePropsDao.deleteRoleHero(condition);
		return 1;
	}

	/**
	 * 查询英雄属性
	 * 
	 * @param role_hero_id
	 *            角色-英雄ID
	 * @return
	 */
	public RoleHero getHeroPropertyById(long role_hero_id) {
		RoleHero roleHero = null;
		// String hero_properties =HERO_PROPER_FIELS +
		// ",to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') AS now_db";
		String condition = " t.role_hero_id = " + role_hero_id;
		try {
			roleHero = calculateHeroPropertyShow(rolePropsDao.getHeroPropertyByCondition(condition).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roleHero;
	}

	public List<RoleHero> getHeroPropertyList(String roleHeroIds) throws Exception{
		
		List<RoleHero> heros = rolePropsDao.getHeroPropertyByCondition(" t.role_hero_id in("+roleHeroIds+")");
		if(heros != null && heros.size() >0){
			for (RoleHero roleHero : heros) {
				calculateHeroPropertyShow(roleHero);
			}
		}
		return heros;
	}
	
	/**
	 * 英雄主副属性兑换
	 * 
	 * @param roleHero
	 * @return
	 */
	public static RoleHero calculateHeroPropertyShow(RoleHero roleHero) {
		int phy = roleHero.getPhysique();// 体质
		int ment = roleHero.getMentality();// 智力
		int porwer = roleHero.getHero_power();// 力量
		int endur = roleHero.getEndurance();// 耐力
		int agility = roleHero.getAgility();// 敏捷

		// 计算物理攻击
		roleHero.setPhysic_attack_show(porwer * 3 + roleHero.getPhysic_attack()
				+ agility);
		// 计算物理防御
		roleHero.setPhysic_defence_show(endur * 3 + phy
				+ roleHero.getPhysic_defence());
		// 计算魔法攻击
		roleHero.setMagic_attack_show(ment * 3 + roleHero.getMagic_attack());
		// 计算魔法防御
		roleHero.setMagic_defence_show(ment * 3 + roleHero.getMagic_defence());
		// 计算总血量
		roleHero.setHp_max_show(porwer + endur * 5 + phy * 8
				+ roleHero.getMax_hp() + ment);
		// 计算总魔法量
		roleHero.setMp_max_show(ment + phy + roleHero.getMax_mp());
		// 计算攻击速度
		roleHero.setSpeed_show(agility + roleHero.getSpeed());
		// 计算攻击距离
		roleHero.setDistance_attack_show(roleHero.getDistance_attack());
		// 计算战斗中移动距离
		roleHero.setDistance_move_show(roleHero.getDistance_move());

		// 计算当前血量 确保在特定操作后 血量数据符合逻辑(e.g 换装操作)
		roleHero.setHp(roleHero.getHp_max_show() >= roleHero.getHp() ? roleHero
				.getHp() : roleHero.getHp_max_show());
		// 计算当前魔法量 确保在特定操作后 血量数据符合逻辑(e.g 换装操作)
		roleHero.setMp(roleHero.getMp_max_show() >= roleHero.getMp() ? roleHero
				.getMp() : roleHero.getMp_max_show());

		return roleHero;
	}

	/**
	 * 派遣英雄-部队 传送门
	 * 
	 * @param role_hero_id
	 *            派遣的英雄集合ID
	 * @param to_city_id
	 *            目的城市ID
	 * @return
	 */
	public int transportArmsBetCitys(List<Long> role_hero_id, long to_city_id,
			long from_city_id, int transLevel) {
		int trans_cd = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city_id", to_city_id);
		map.put("now_cityid", to_city_id);
		try {
			for (Long hero_id : role_hero_id) {
				map.put("role_hero_id", hero_id);

				rolePropsDao.updateHeroProperty(map);

			}
			String trasCdStr = FileUtil.getValue(System.getProperty("user.dir")
					+ "\\properties\\props_diamon.properties", "tans_leve"
					+ transLevel);
			trans_cd = Integer.parseInt(trasCdStr) * 3600;
			map.put("city_id", from_city_id);
			map.put("condition", " trans_cd = to_char(sysdate+numtodsinterval("
					+ trans_cd + ",'second'),'yyyy-mm-dd hh24:mi:ss')");

			cityBuildDao.updateCityInfo(map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();// 抛出运行时异常 事务回滚
		}
		return trans_cd;
	}

	/**
	 * 英雄经验增加接口
	 * 
	 * @param roleHeroId
	 *            英雄的角色-英雄关联id
	 * @param addExp
	 *            单个英雄升级获取的经验
	 * @return
	 */
	public boolean heroAddExp(List<Long> roleHeroId, int addExp) {
		try {
			for (long id : roleHeroId) {
				// 查询当前英雄的经验值和等级
				// String hero_properties = "hero_exp";
				String condition = " role_hero_id = " + id;

				RoleHero roleHero = rolePropsDao.getHeroPropertyByCondition(condition).get(0);

				int currExp = roleHero.getHero_exp();
				currExp += addExp;

				// 更新英雄信息
				String upCondition = "t.hero_exp=" + currExp
						+ " where t.role_hero_id=" + id;

				rolePropsDao.updateHerosStatus(upCondition);
			}
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	/**
	 * 英雄升级/加点接口程序
	 * 
	 * @param roleHero
	 *            英雄对象 需要包含以下字段 next_lev 英雄升级后的下一等级（加点不需要） role_hero_id
	 *            角色-英雄关联id 以及英雄的各个属性值（升级/加点过后的）
	 * @param type
	 *            1-----升级 2--------加点
	 * @return
	 */
	public HashMap<String, Integer> roleHeroUpgrade(RoleHero roleHero, int type) {
		HashMap<String, Integer> resMap = new HashMap<String, Integer>();
		// 下一等级所需的经验值
		int next_exp = 0;
		// 升级后剩余经验
		int last_exp = 0;

		// 英雄升级(添加各个属性值)
		String upCondition = "t.physique=" + (roleHero.getPhysique())
				+ ",t.mentality=" + (roleHero.getMentality())
				+ ",t.hero_power=" + (roleHero.getHero_power())
				+ ",t.endurance=" + (roleHero.getEndurance()) + ",t.agility="
				+ (roleHero.getAgility()) + ",t.CAN_ASSIGN_POINT="
				+ (roleHero.getCan_assign_point()) + ",t.hp="
				+ (roleHero.getHp()) + ",t.mp=" + (roleHero.getMp());

		if (type == 1) {
			// 获取英雄下一等级所需经验值
			next_exp = Integer.parseInt(ReadProperties.getProperties("level"+ (roleHero.getHero_lev()), "hero_up_lev.properties"));

			last_exp = roleHero.getHero_exp()
					- Integer.parseInt(ReadProperties.getProperties("level"
							+ (roleHero.getHero_lev() - 1),
							"hero_up_lev.properties"));
			upCondition += (",t.hero_lev=" + roleHero.getHero_lev()
					+ ",t.hero_up_exp=" + next_exp + ",t.hero_exp = "
					+ last_exp + ",t.command = " + roleHero.getCommand());

		}

		upCondition += (" where t.role_hero_id=" + roleHero.getRole_hero_id());

		try {
			rolePropsDao.updateHerosStatus(upCondition);
		} catch (Exception ex) {
			ex.printStackTrace();
			resMap.put("next_exp", -1);
			return resMap;
		}
		resMap.put("next_exp", next_exp);
		resMap.put("last_exp", last_exp);
		return resMap;
	}

	/**
	 * 英雄复活接口程序
	 * 
	 * @param roleHeroId
	 * @param is_free
	 * @param revive_time
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Integer> heroRevive(long role_id, long roleHeroId, int heroLev)
			throws Exception {

		HashMap<String, Integer> heroReMap = heroRevieTimeExpress(heroLev);
		// 消耗金币
		Role r = roleDao.selectRolesByRoleId(role_id);
		if (r != null) {
			int lastGold = r.getGold() - heroReMap.get(HERO_REV_GOLD);
			if (lastGold < 0)
				throw new RuntimeException(
						RolePropsInfoService.PROPS_COUNT_ERROR_EN);

			r.setRole_id(role_id);
			r.setGold(lastGold);
			roleDao.updateRoleInfo(r);
		}
		
		// 参数
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role_hero_id", roleHeroId);
		map.put("is_free", Constants.HERO_STATUS_REVIVE);
		map.put("revive_time", heroReMap.get(HERO_REV_TIME));
		// 更新当前英雄的数据
		rolePropsDao.updateHeroProperty(map);

		return heroReMap;
	}

	
	/**
	 * 线上英雄复活完成 线下部分由数据库JOB实现
	 * @param role_hero_id
	 * @throws Exception 
	 */
	public void heroReviFinish(long role_hero_id) throws Exception{
		//获取该英雄当前属性
		RoleHero rh = rolePropsDao.getHeroPropertyByRoleHeroId(role_hero_id);
		if(rh == null)
			throw new RuntimeException();
		calculateHeroPropertyShow(rh);
		String condition = " t.is_free = 1 ,t.hp = "+rh.getHp_max_show()+",t.mp = "+rh.getMp_max_show()+", t.revive_time = '' WHERE t.role_hero_id = "+role_hero_id;
		rolePropsDao.updateHerosStatus(condition);
	}
	/**
	 * 英雄复活时间公式
	 * 
	 * @param heroLev
	 * @return
	 */
	public static HashMap<String, Integer> heroRevieTimeExpress(int heroLev) {
		HashMap<String, Integer> resMap = new HashMap<String, Integer>();
		// 英雄复活时间=25+英雄等级×（英雄等级+1）×5(秒)
		// 消耗的金币数量=50+10×英雄等级×（英雄等级+1）

		resMap.put(HERO_REV_TIME, 25 + 5 * heroLev * (heroLev + 1));
		resMap.put(HERO_REV_GOLD, 50 + 10 * heroLev * (heroLev + 1));

		return resMap;
	}
}
