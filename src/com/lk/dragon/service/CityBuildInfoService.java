/** 
 *
 * @Title: CityBuildInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午4:26:06 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IBuffDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IMapDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.Build;
import com.lk.dragon.db.domain.BuildCreate;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;
import com.lk.dragon.db.domain.LkMap;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.util.Constants;

/**
 * @Description:城邦-建筑信息业务层
 */
@Service
public class CityBuildInfoService {

	@Autowired
	private ICityBuildDao cityBuildDao;
	@Autowired
	private IMapDao mapDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IBuffDao buffDao;
	@Autowired
	private IRolePropsDao rolePropsDao;

	public CityBuildInfoService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 出生新建城市
	 * 
	 * @param city
	 * @return KEY值 Constants.RESULT_KEY==========> 0：新建失败 1：新建成功 2：该坐标无法新建城邦
	 * @throws Exception
	 */
	public HashMap<String, Long> createNewCityByBirth(City city) throws Exception {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		resultMap.put(Constants.RESULT_KEY, Constants.CREATE_NEW_CITY_FAIL);
		resultMap.put(Constants.ID_KEY, -1l);

		// 新建城镇
		long cityId = cityBuildDao.createNewCity(city);

		// 构造城镇中心建筑对象
		// 查找该种族市政大厅ID
		int buildHomeId = cityBuildDao.findHomeBuildByRace(city.getRace());
		CityBuild homeBuild = new CityBuild();
		homeBuild.setCity_id(cityId);
		homeBuild.setBulid_id(buildHomeId);

		// 创建默认建筑:城镇中心
		cityBuildDao.createHomeBuild(homeBuild);

		// 更改世界地图上坐标元素 item = 1---------> 城镇
		mapDao.updateMapItem(new com.lk.dragon.db.domain.LkMap(
				city.getSite_x(), city.getSite_y(), 1, city.getRace()));

		resultMap.put(Constants.RESULT_KEY, Constants.CREATE_NEW_CITY_SUCCESS);
		resultMap.put(Constants.ID_KEY, cityId);

		return resultMap;
	}

	/**
	 * 建筑升级
	 * 
	 * @param rela_id
	 *            城邦-建筑 关联ID
	 * @param role_id
	 *            角色ID
	 * @param use_food
	 *            消耗食物
	 * @param use_wood
	 *            消耗木材
	 * @param use_stone
	 *            消耗石头
	 * @param use_gold
	 *            消耗金币
	 * @param levUpTime
	 *            升级时长
	 * @return
	 * @throws Exception
	 */
	public boolean buildLevUping(long rela_id, long role_id, int use_food,
			int use_wood, int use_stone, int use_gold, int levUpTime)
			throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rela_id", rela_id);
		map.put("levUpTime", levUpTime);

		map.put("operator", "-");
		map.put("food", use_food);
		map.put("wood", use_wood);
		map.put("stone", use_stone);
		map.put("gold", use_gold);
		map.put("role_id", role_id);
		roleDao.sumPluRoleInfo(map);// 消耗资源
		cityBuildDao.buildLevUping(map);// 设置建筑升级中

		flag = true;
		return flag;
	}

	/**
	 * 建筑升级完成 新建建筑(建筑升级) 城邦与角色人口均增加
	 * 
	 * @param cityBuild
	 *            城邦-建筑关联ID 属性：rela_id关联ID　　curr_lev：　升级完成后建筑等级
	 * @param role_id
	 *            角色ID
	 * @param city_id
	 *            城邦ID
	 * @param add_eat
	 *            此次升级后新增人口数
	 * @return
	 * @throws Exception 
	 */
	public String buildLevUpEnd(CityBuild cityBuild, long role_id) throws Exception {
//		Role role = null;
//		try {
//
//			cityBuildDao.buildLevUpEnd(cityBuild);// 调整升级标志位
//			// 查询角色信息
//			role = roleDao.selectRolesByRoleId(role_id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return role;
		return cityBuildDao.callBuildUpFinishFun(role_id, cityBuild.getRela_id(), cityBuild.getCurr_lev(), 0);
	}

	/**
	 * 使用道具 建筑升级完成
	 * 
	 * @param rela_id
	 *            城邦-建筑关联ID
	 * @param role_id
	 *            角色ID
	 * @param upEndLevel
	 *            升级后建筑等级
	 * @param use_diamon
	 *            消耗钻石数
	 * @return
	 * @throws Exception
	 */
	public String usePropsLevUpEnd(long rela_id, long role_id,int upEndLevel, int use_diamon) throws Exception {

//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("operator", "+");
//		map.put("eat", add_eat);
//		map.put("city_id", city_id);
//		CityBuild cityBuild = new CityBuild();
//		cityBuild.setRela_id(rela_id);
//		cityBuild.setCurr_lev(upEndLevel);
//
//		Role role = roleDao.selectRolesByRoleId(role_id);
//		role.setRole_id(role_id);
//		role.setDiamon(role.getDiamon() - use_diamon);
//		
//		//SQL
//		roleDao.updateRoleInfo(role);// 消耗钻石
//		cityBuildDao.buildLevUpEnd(cityBuild);// 调整升级标志位
		return cityBuildDao.callBuildUpFinishFun(role_id, rela_id, upEndLevel, use_diamon);
	}

	/**
	 * 获取建筑升级信息
	 * 
	 * @param rela_id
	 * @return
	 */
	public CityBuild getLevUpInfo(long rela_id) {
		CityBuild upBuild = null;
		try {
			upBuild = cityBuildDao.getLevUpInfo(rela_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return upBuild;
	}

	/**
	 * 查询角色已拥有的城邦列表
	 * 
	 * @param role_id
	 * @return
	 * @throws Exception 
	 */
	public List<City> getCitysByRoleId(long role_id) throws Exception {
		return cityBuildDao.getCitysByRoleId(role_id);
	}

	/**
	 * 查询城邦信息
	 * 
	 * @param condition
	 * @return
	 * @throws Exception 
	 */
	public List<City> getCityInfo(String condition) throws Exception {
		return cityBuildDao.getCityInfo(condition);
	}

	/**
	 * 查询城邦当前ＢＵＦＦ信息
	 * 
	 * @param citys
	 * @return
	 */
	public List<City> getCityBuffs(List<City> citys) {

		try {
			Buff buff = new Buff();
			for (City city : citys) {
				buff.setTarget_id(city.getCity_id());
				buff.setBuff_type(Constants.BUFF_TAG_TYPE_CITY);
				city.setBuffs(buffDao.getBuffInfo(buff));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citys;
	}

	/**
	 * 根据城镇id查询城镇相关产量
	 * 
	 * @param cityId
	 * @return
	 */
	public City getCityByCityId(long cityId) {
		City city = null;
		try {
			city = cityBuildDao.getCityYieldReinInfoByCityId(cityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return city;
	}

	/**
	 * 根据种族获取该种族所有建筑物
	 * 
	 * @param race
	 * @return
	 */
	public List<Build> getBuildsByRace(int race) {
		List<Build> builds = null;
		try {
			builds = cityBuildDao.getBuildsByRace(race);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builds;
	}

	/**
	 * 获取地图可用随机点
	 * 
	 * @return
	 */
	public String getEnableRadomPoint() {
		String point = "";
		try {
			point = cityBuildDao.getEnableRadomPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return point;

	}

	/**
	 * 获取地图剩余可用点---暂不使用
	 * 
	 * @return
	 */
	private List<com.lk.dragon.db.domain.LkMap> findEnablePoint() {
		List<com.lk.dragon.db.domain.LkMap> maps = null;
		try {
			maps = mapDao.findEnablePoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

	/**
	 * 更改地图元素
	 * 
	 * @param lkMap
	 *            包含x,y坐标的Map对象 item--->0:草原 1：城镇
	 * @return
	 */
	public boolean updateMapItem(com.lk.dragon.db.domain.LkMap lkMap) {
		boolean flag = false;
		try {
			if (mapDao.updateMapItem(lkMap) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查看当前坐标点元素类型
	 * 
	 * @param lkMap
	 * @return 0:草原 1：城镇
	 */
	public int checkPointItem(com.lk.dragon.db.domain.LkMap lkMap) {
		int itemType = 0;
		try {
			itemType = mapDao.checkPointItem(lkMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemType;
	}

	/**
	 * 获取指定城市中所有已建建筑
	 * 
	 * @param isOutCity
	 *            是否查看的是外城建筑
	 * @return
	 */
	public List<CityBuild> getAlerdyBuilded(String key, long value) {
		List<CityBuild> cityBuilds = null;
		HashMap<String, Long> map;
		try {
			map = new HashMap<String, Long>();
			map.put(key, value);
			cityBuilds = cityBuildDao.getAlerdyBuilded(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityBuilds;
	}

	/**
	 * 新建建筑 //暂弃 新建==建筑升级0--->1
	 * 
	 * @param cityBuild
	 *            城邦-建筑对象 属性：city_id build_id
	 * @param role_id
	 *            角色ID
	 * @param use_food
	 *            消耗食物
	 * @param use_wood
	 *            消耗木材
	 * @param use_stone
	 *            消耗石头
	 * @param use_gold
	 *            消耗金币
	 * @param create_time
	 *            建造时长
	 * @return
	 * @throws Exception
	 */
	public long createNewBuild(CityBuild cityBuild, long role_id, int use_food,
			int use_wood, int use_stone, int use_gold, int create_time)
			throws Exception {
		long keyId = -1l;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("operator", "-");
		map.put("food", use_food);
		map.put("wood", use_wood);
		map.put("stone", use_stone);
		map.put("gold", use_gold);
		map.put("role_id", role_id);
		cityBuild.setCreate_time(create_time);
		keyId = cityBuildDao.createNewBuild(cityBuild);
		roleDao.sumPluRoleInfo(map);

		return keyId;
	}

	/**
	 * 更改城邦名称
	 * 
	 * @param city
	 * @return
	 */
	public boolean updateCityName(City city) {
		boolean flag = false;
		try {
			if (cityBuildDao.updateCityName(city) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 使用道具清除城邦传送门CD
	 * 
	 * @param city_id
	 * @param role_id
	 * @param use_diamon
	 * @return
	 * @throws Exception
	 */
	public boolean usePropsClearTransCD(long city_id, long role_id,
			int use_diamon) throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("condition", " trans_cd = null");
		map.put("city_id", city_id);
		// 清除传送门CD
		cityBuildDao.updateCityInfo(map);
		map.clear();
		map.put("operator", "-");
		map.put("role_id", role_id);
		map.put("diamon", use_diamon);
		// 消耗角色钻石
		roleDao.sumPluRoleInfo(map);
		flag = true;
		return flag;
	}

	/**
	 * 使用道具保护一定时间类城邦无法被攻击
	 * 
	 * @param city_id
	 *            城邦ID
	 * @param role_props_id
	 *            道具ID
	 * @param protect_hour
	 *            保护持续时间 (小时)
	 * @param is_enough
	 *            道具是否还有剩余 1:是 0:否
	 * @return
	 * @throws Exception
	 */
	public boolean usePropsProtectCity(long city_id, long role_props_id,
			int protect_hour) throws Exception {
		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> mapCount = new HashMap<String, Object>();
		String condition = "protect_time = to_char(sysdate+numtodsinterval("
				+ protect_hour + ",'hour'),'yyyy-mm-dd hh24:mi:ss')";
		map.put("condition", condition);
		map.put("city_id", city_id);

		cityBuildDao.updateCityInfo(map);// 设置城邦被保护

		mapCount.put("operator", "-");
		mapCount.put("props_count_vo", 1);
		mapCount.put("role_props_id", role_props_id);
		if (rolePropsDao.plSubPropsCount(mapCount) > 0)
			;// 更新道具数量
		flag = true;

		return flag;

	}

	/**
	 * 迁城操作
	 * 
	 * @param fromPoint
	 *            城镇原始点 具有x,y坐标且item=0
	 * @param toPoint
	 *            目标坐标点 具有x,y坐标且item=1
	 * @param city
	 *            新城邦对象 具有city_id,x,y坐标
	 * @param rolePropsId
	 *            消耗道具ID
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Long> moveCity(LkMap fromPoint, LkMap toPoint,
			City city, long rolePropsId) throws Exception {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		if (checkPointItem(toPoint) != 0) {
			resultMap.put(Constants.RESULT_KEY, Constants.MOVE_CITY_UNAVAILABLE);
			resultMap.put(Constants.ID_KEY, -1l);

			return resultMap;
		}

		int toId = mapDao.updateMapItem(toPoint);// 更改目标点元素类型
		int newId = cityBuildDao.updateCityPoint(city);// 更改新城邦坐标
		int fromId = mapDao.updateMapItem(fromPoint);// 更改原始点坐标类型

		// 更新消耗品数量
		HashMap<String, Object> propsMap = new HashMap<String, Object>();
		propsMap.put("operator", "-");
		propsMap.put("props_count_vo", 1);
		propsMap.put("role_props_id", rolePropsId);
		int res = rolePropsDao.plSubPropsCount(propsMap);
		if (toId > 0 && newId > 0 && fromId > 0 && res > 0) {
			resultMap.put(Constants.RESULT_KEY, Constants.MOVE_CITY_SUCCESS);
			resultMap.put(Constants.ID_KEY, 1l);
		} else {
			throw new RuntimeException();
		}
		return resultMap;

	}

	/**
	 * 取消建筑升级
	 * 
	 * @param role_id
	 *            角色ID
	 * @param rela_id
	 *            城邦-建筑关联ID
	 * @param back_food
	 *            返还事物
	 * @param back_wood
	 *            返还木材
	 * @param back_stone
	 *            返还石料
	 * @param back_gold
	 *            返还金币
	 * @return
	 * @throws Exception
	 */
	public boolean cancelLevUp(long role_id, long rela_id, int curr_lev,
			int back_food, int back_wood, int back_stone, int back_gold)
			throws Exception {
		boolean flag = false;
		CityBuild cityBuild = new CityBuild();
		cityBuild.setRela_id(rela_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role_id", role_id);
		map.put("operator", "+");
		map.put("food", back_food);
		map.put("gold", back_gold);
		map.put("stone", back_stone);
		map.put("wood", back_wood);
		if (curr_lev == 0) {
			// 当前取消升级建筑:0删除对应数据
			cityBuildDao.destoryBuild(rela_id);
		} else {
			// 非0级建筑只取消升级 不删除建筑
			cityBuildDao.buildLevUpEnd(cityBuild);// 停止升级
		}
		// 返还部分升级资源
		roleDao.sumPluRoleInfo(map);
		flag = true;
		return flag;
	}

	/**
	 * 拆毁建筑 :暂定只能拆除外城建筑
	 * 
	 * @param rela_id
	 * @return TRUE:拆除成功 FALSE：拆除失败
	 */
	public City destoryBuild(long rela_id, long cityId) {
		boolean flag = false;
		City cityYildsInfo = null;
		try {

			// 删除建筑信息 人口与产量变更 ORACLE触发器处理
			cityBuildDao.destoryBuild(rela_id);

			// 删除后获取最新产量数据
			cityYildsInfo = cityBuildDao.getCityYieldReinInfoByCityId(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityYildsInfo;
	}

	/**
	 * 查询建筑升级消耗信息
	 * 
	 * @param type
	 *            建筑类型
	 * @param leve
	 *            升级后建筑等级
	 * @return
	 */
	public BuildCreate buildLevupUse(int type, int lev) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		BuildCreate buildCr = null;
		map.put("type", type);
		map.put("lev", lev);
		try {

			buildCr = cityBuildDao.buildLevupUse(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buildCr;
	}

	/**
	 * 获取资源对应等级的产量
	 * 
	 * @param level
	 * @return
	 */
	public List<CityBuild> getResouceYield() {
		List<CityBuild> resYield = null;
		try {
			resYield = cityBuildDao.getResourceYieldOut();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resYield;
	}
}
