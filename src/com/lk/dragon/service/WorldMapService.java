/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： WorldMapService.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-12-15 下午3:42:31
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
import com.lk.dragon.db.dao.IWorldMapDao;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;

@Service
public class WorldMapService {
	@Autowired
	private IWorldMapDao worldMapDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IArmsDeployDao armsDeployDao;
	@Autowired
	private ICityBuildDao cityBuildDao;
	@Autowired
	private WarDeployInfoService warDeployInfoService;

	public WorldMapService() {
	}

	/**
	 * 查询世界地图某个范围内的点信息
	 * 
	 * @param worldMap
	 * @return
	 */
	public List<WorldMap> getWorldMapPoints(WorldMap worldMap) {
		List<WorldMap> worldMapList = null;

		try {
			worldMapList = worldMapDao.getWorldMapPoint(worldMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return worldMapList;
	}

	/**
	 * 根据坐标点查询城镇信息
	 * 
	 * @param city
	 * @return
	 */
	public City getCityInfo(City city) {
		City c = null;

		try {
			c = worldMapDao.getCityInfo(city);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return c;
	}

	/**
	 * 查询资源点信息
	 * 
	 * @param conditon
	 * @return
	 */
	public List<WildSrc> getWildSrcInfo(String condition) {
		List<WildSrc> wildSrc = null;
		try {
			wildSrc = worldMapDao.getWildSrcInfo(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wildSrc;
	}

	/**
	 * 查询野外随即点野怪信息
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public WorldMap getRandomWildInfo(int x, int y) {
		WorldMap wildArmInfo = null;
		HashMap<String, Integer> point;
		try {
			point = new HashMap<String, Integer>();
			point.put("site_x", x);
			point.put("site_y", y);
			wildArmInfo = worldMapDao.getRandomWildInfo(point);
			wildArmInfo.setWildArms(armsDeployDao.getArmInfoByArmId(wildArmInfo.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wildArmInfo;
	}

	/**
	 * 变更资源点信息
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public boolean updateWildSrcInfo(WildSrc src) throws Exception{
		boolean flag = false;
		if(worldMapDao.updateWildSrcInfo(src) > 0 )
			flag = true;
		
		return flag;
	}
	
	/**
	 * 放弃资源点
	 * @param src
	 * @throws Exception 
	 */
	public HashMap<String,Object> giveUpWildSrc(int x,int y,long role_id) throws Exception{
		HashMap<String,Object> resMap  = new HashMap<String,Object>();
		//查询该资源点驻扎队列
		WildSrc widsrc = worldMapDao.getWildSrcInfo(" where t.tag_x = "+x+" and t.tag_y = "+y).get(0);
		if(role_id != widsrc.getOwner_id()){
			resMap.put("result", -1);//资源点不属于该roleId
			return resMap;
		}
		
		resMap.put("result",1);//
		HashMap<Long,Long> teamMap  = new HashMap<Long,Long>();
		//该资源点有驻扎部队
		if(widsrc.getArm_info()!=null && widsrc.getArm_info().length() > 0 ){
			//获取所有部队队列ID
			List<Long> teamIds = armsDeployDao.selectDistintTeamId(" t.role_hero_id in ("+widsrc.getArm_info()+")");
			long backTime = -1;
			//驻扎部队开始返回
			for (Long long1 : teamIds) {
				backTime = warDeployInfoService.callBackWarTeam(long1);
				teamMap.put(long1, backTime);
			}
		}
		resMap.put("teamMap", teamMap);
		
		widsrc.setOwner_id(-10l);
		widsrc.setOwner_type(1);
		//资源点状态变更
		updateWildSrcInfo(widsrc);
		
		//获取角色资源
		Role r = roleDao.selectRolesByRoleId(role_id);
		int plSrcCount = widsrc.getSrc_leve() == 1 ? 400 : 1200;
		
		// 1:食物资源点  2:木材资源点  3:石头资源点
		switch (widsrc.getSrc_type()) {
		case 1:
			r.setYield_food((r.getYield_food() - plSrcCount) < 0 ? 0 : (r.getYield_food() - plSrcCount));
			break;
		case 2:
			r.setYield_wood((r.getYield_wood() - plSrcCount) < 0 ? 0 : (r.getYield_wood() - plSrcCount));
			break;
		case 3:
			r.setYield_stone((r.getYield_stone() - plSrcCount) < 0 ? 0 : (r.getYield_stone() - plSrcCount));
			break;
		default:
			break;
		}
		//角色资源更新 
		roleDao.updateRoleInfo(r);
		
		resMap.put("yield", r);
		return resMap;
	}
	/**
	 * 角色使用钻石查看信息
	 * 
	 * @return 城镇的增值信息 map key val armys 军队信息 cityInfo 城镇信息
	 */
	public HashMap<String, Object> useDiamond(long role_id, int diamond,
			long city_id, int gold) throws Exception {
		Role baseRole = roleDao.selectRolesByRoleId(role_id);
		baseRole.setDiamon(baseRole.getDiamon() - diamond);
		baseRole.setGold(baseRole.getGold() - gold);
		baseRole.setRole_id(role_id);

		// 更新玩家财富
		roleDao.updateRoleInfo(baseRole);

		// 角色所有城邦建筑总人口
		int sumCityEats = cityBuildDao.getCityEatByRoleId(role_id);

		// 查询城镇的增值信息
		String condition = "where t.now_cityid = " + city_id;
		List<RoleHero> armyList = armsDeployDao.getSrcHerosInfoByCondition(condition);

		// 查询城镇产量信息
		condition = "where t.city_id = " + city_id;
		City city = cityBuildDao.getCityInfo(condition).get(0);

		// 资源占比
		double per = (double) city.getEat() / sumCityEats;
		city.setCheck_food((int) Math.ceil(baseRole.getFood() * per));
		city.setCheck_wood((int) Math.ceil(baseRole.getWood() * per));
		city.setCheck_stone((int) Math.ceil(baseRole.getStone() * per));

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("armys", armyList);
		map.put("cityInfo", city);

		return map;
	}
}
