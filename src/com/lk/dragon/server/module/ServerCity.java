/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerMainCity.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午2:47:34
 */
package com.lk.dragon.server.module;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Build;
import com.lk.dragon.db.domain.BuildCreate;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;
import com.lk.dragon.db.domain.LkMap;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.server.domain.CityDomain;
import com.lk.dragon.server.module.analysis.CityRequestAnalysis;
import com.lk.dragon.server.module.analysis.HeroRequestAnalysis;
import com.lk.dragon.service.CacheService;
import com.lk.dragon.service.CityBuildInfoService;
import com.lk.dragon.service.RoleHeroInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerCity {
	/** 主城相关请求实体 **/
	private CityDomain cityDomain;

	private CityBuildInfoService cityBuildInfoService;
	private RoleHeroInfoService roleHeroInfoService;
	/** 缓存正在处理的坐标点 **/
	private static List<Point> pointArr = new ArrayList<Point>();

	public ServerCity() {
	}

	/**
	 * 构造函数
	 */
	public ServerCity(CityDomain cityDomain) {
		this.cityDomain = cityDomain;
		this.cityBuildInfoService = SpringBeanUtil
				.getBean(CityBuildInfoService.class);
		this.roleHeroInfoService = SpringBeanUtil
				.getBean(RoleHeroInfoService.class);
	}

	/**
	 * 主城相关请求分析
	 */
	public void cityAnalysis() {
		switch (cityDomain.getType()) {
		case Constants.CITY_HAVE_BUILD_LIST_TYPE: {
			// 主城已经建造的建筑列表获取
			getBuildList();
			break;
		}
		case Constants.CITY_BUILD_BUILD_TYPE: {
			// 主城建造
			buildArch();
			break;
		}
		case Constants.CITY_BUILD_UPGRADE_TYPE: {
			// 主城建筑升级
			upgradeArch();
			break;
		}
		case Constants.CITY_ESTABLISH_TYPE: {
			// 修建城市请求
			buildCityResponse();
			break;
		}
		case Constants.CITY_CAN_BUILD_TYPE: {
			// 可建造建筑列表
			canBuildList();
			break;
		}
		case Constants.CITY_LIST_TYPE: {
			// 城市列表id
			getCityList();
			break;
		}
		case Constants.CITY_RENAME_TYPE: {
			// 城市重命名
			cityRename();
			break;
		}
		case Constants.CITY_MOVE_TYPE: {
			// 城市迁移
			cityMove();
			break;
		}
		case Constants.CITY_MOVE_RANDOM_TYPE: {
			// 城市迁移到 随机点请求
			cityMoveRandom();
			break;
		}
		case Constants.CITY_UPGRADE_CANCEL_TYPE: {
			// 建筑升级取消
			cancelUpgrade();
			break;
		}
		// case Constants.CITY_BUILD_UPGRADE_RATE_TYPE:
		// {
		// //建筑升级情况查询请求
		// buildUpgradeRate();
		// break;
		// }
		case Constants.CITY_BUILD_UPGRADE_FINISH_TYPE: {
			// 建筑升级完成请求
			buildUpgradeFinished();
			break;
		}
		case Constants.CITY_BUILD_DELETE_TYPE: {
			// 拆除建筑
			deleteBuild();
			break;
		}
		case Constants.CITY_BUILD_UPGRADE_USE_TYPE: {
			// 查询建筑升级消耗
			getBuildUpgradeUse();
			break;
		}
		case Constants.CITY_PROTECT_TYPE: {
			// 城镇保护道具
			protectCity();
			break;
		}
		case Constants.CITY_BUILD_UPGRADE_ACCE_TYPE: {
			// 城镇建筑加速升级
			buildUpgradeAcce();
			break;
		}
		case Constants.CITY_HAVE_HERO_TYPE: {
			// 查询该城镇拥有的英雄信息
			cityHaveHero();
			break;
		}
		case Constants.CITY_CLEAN_TRANS_CD_TYPE: {
			// 清除传送门CD
			cleanTransCD();
			break;
		}
		case Constants.GET_RESOURCE_YIELD: {
			// 获取资源建筑对应等级的产量
			getResourceYield();
			break;
		}
		}
	}

	/**
	 * 获取资源建筑对应等级的产量
	 */
	private void getResourceYield() {
		JSONObject resYieldJ = new JSONObject();
		int res_lev = cityDomain.getRes_level();
		int build_type = cityDomain.getBuildType();
		// if (build_type != 17 && 18 != build_type && 19 != build_type) {
		// resYieldJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
		// // 返回响应信息给客户端
		// SocketUtil.responseClient(cityDomain.getCtx(), resYieldJ.toString());
		// return;
		// }

		// 根据客户端请求查询对应建筑的产量
		int yield = 0;
		try {
			yield = ((CityBuild) CacheService.buildCache.get(
					build_type + "" + res_lev).getObjectValue()).getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 构造JSON字符串
		if (yield == 0) {
			resYieldJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
		} else {
			resYieldJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			resYieldJ.put("res_yield", yield);
		}
		// 返回响应信息给客户端
		SocketUtil.responseClient(cityDomain.getCtx(), resYieldJ.toString());
	}

	/**
	 * 清除传送门CD
	 */
	private void cleanTransCD() {
		boolean flag = false;
		try {
			flag = cityBuildInfoService.usePropsClearTransCD(
					cityDomain.getCityId(), cityDomain.getRoldId(),
					cityDomain.getDiamondNum());
			// 新增操作日志
			String detail = "清除传送门CD成功.";
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 新增操作日志
			String detail = "清除传送门CD失败.详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

		}
		SocketUtil.responseClient(cityDomain.getCtx(),
				JSONUtil.getBooleanResponse(flag));
	}

	/**
	 * 修建城市
	 */
	public void buildCity() {
		// 判定建造城镇类型
		if (cityDomain.getCityType() == Constants.MAIN_CITY) {
			// 建造的主城
			City city = new City();

			synchronized (this) {
				while (true) {
					String siteStr = cityBuildInfoService.getEnableRadomPoint();
					JSONObject siteObj = JSONObject.fromObject(siteStr);
					int x = siteObj.getInt(Constants.X_KEY);
					int y = siteObj.getInt(Constants.Y_KEY);

					boolean flag = isPointUsed(x, y);

					// 如果已经在处理中则需要重新随点,否则就在该点上建造
					if (!flag) {
						city.setSite_x(x);
						city.setSite_y(y);
						break;
					}
				}
			}

			// 在该点建造主城
			city.setName(Constants.CITY_DEFAULT_NAME);
			city.setHome(Constants.MAIN_CITY);
			city.setRace(cityDomain.getRace());
			city.setRole_id(cityDomain.getRoldId());
			city.setEat(0);

			// 建造城市
			try {
				cityBuildInfoService.createNewCityByBirth(city);
				// 新增操作日志
				String detail = "成功建造主城.";
				OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
						OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

			} catch (Exception e) {
				e.printStackTrace();
				// 新增操作日志
				String detail = "建造主城失败，详细信息："
						+ OperateLogUtil.getExceptionStackInfo(e);
				OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
						OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

			}

			// 移除缓存点
			Point point = new Point(city.getSite_x(), city.getSite_y());
			pointArr.remove(point);
		} else if (cityDomain.getCityType() == Constants.VICE_CITY) {
			String responseStr = "";

			// 检测该点是否在缓存中
			if (isPointUsed(cityDomain.getX(), cityDomain.getY())) {
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(Constants.RESULT_KEY, Constants.CREATE_CITY_HAS_APPLYED);

				// 获取响应字符串
				responseStr = CityRequestAnalysis.getBuildCityResponseStr(map);

				// 新增操作日志
				String detail = "建造要塞失败，该点已被占用";
				OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
						OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

			} else {
				// 根据上传的信息建造分城
				City city = new City();
				String cityName = cityDomain.getCityName();
				city.setName(cityName);
				if (cityName == null || cityName.trim().length() == 0) {
					city.setName(Constants.CITY_DEFAULT_NAME);
				}
				city.setHome(Constants.VICE_CITY);
				city.setRace(cityDomain.getRace());
				city.setSite_x(cityDomain.getX());
				city.setSite_y(cityDomain.getY());
				city.setRole_id(cityDomain.getRoldId());
				city.setEat(0);

				// 建造城市
				Map<String, Long> map = null;
				try {
					map = cityBuildInfoService.createNewCityByBirth(city);

					// 新增操作日志
					String detail = "成功建造要塞.";
					OperateLogUtil.insertOperateLog(cityDomain.getRoldId(),
							detail, OperateLogUtil.CITY_MODULE,
							OperateLogUtil.SUCCESS);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					// 新增操作日志
					String detail = "建造要塞失败,详细信息："
							+ OperateLogUtil.getExceptionStackInfo(e);
					OperateLogUtil.insertOperateLog(cityDomain.getRoldId(),
							detail, OperateLogUtil.CITY_MODULE,
							OperateLogUtil.FAIL);

				}

				// 将该点移除缓存
				Point point = new Point(cityDomain.getX(), cityDomain.getY());
				pointArr.remove(point);

				// 获取响应字符串
				responseStr = CityRequestAnalysis.getBuildCityResponseStr(map);

			}

			// 将信息回写到客户端
			SocketUtil.responseClient(cityDomain.getCtx(), responseStr);
		}
	}

	/**
	 * 修建请求
	 */
	private void buildArch() {
		// 根据客户端 上传的建筑数据，将建筑入库
		CityBuild cityBuild = new CityBuild();
		cityBuild.setBulid_id(cityDomain.getBuildId());
		cityBuild.setCity_id(cityDomain.getCityId());
		cityBuild.setLocate(cityDomain.getLocate());
		long role_id = cityDomain.getRoldId();
		int food = cityDomain.getFood();
		int wood = cityDomain.getWood();
		int stone = cityDomain.getStone();
		int gold = cityDomain.getGold();
		int time = cityDomain.getLevelUpT();

		// 建造
		long buildId = -1;
		try {
			buildId = cityBuildInfoService.createNewBuild(cityBuild, role_id,
					food, wood, stone, gold, time);
			// 新增操作日志
			String detail = "建造建筑请求成功处理.城镇id:" + cityDomain.getCityId()
					+ "，建筑id:" + cityDomain.getBuildId();
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 新增操作日志
			String detail = "建造建筑失败，详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

		}

		// 获取响应字符串
		String buildResponse = CityRequestAnalysis.getBuildResponse(buildId);

		// 将处理结果返回
		SocketUtil.responseClient(cityDomain.getCtx(), buildResponse);
	}

	/**
	 * 升级建筑请求
	 */
	private void upgradeArch() {
		// 根据建筑数据，以及升级级数，将数据更新到数据库
		long realId = cityDomain.getRelaId();
		long roleId = cityDomain.getRoldId();
		int time = cityDomain.getLevelUpT();
		int food = cityDomain.getFood();
		int wood = cityDomain.getWood();
		int stone = cityDomain.getStone();
		int gold = cityDomain.getGold();

		boolean result = false;
		try {
			result = cityBuildInfoService.buildLevUping(realId, roleId, food,
					wood, stone, gold, time);
			// 新增操作日志
			String detail = "升级建筑请求成功处理.建筑id:" + cityDomain.getBuildId()
					+ "，当前等级：" + cityDomain.getBuildLev();
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			// 新增操作日志
			String detail = "升级建筑失败，详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

		}
		String upgradeResponse = JSONUtil.getBooleanResponse(result);

		// 将处理结果返回
		SocketUtil.responseClient(cityDomain.getCtx(), upgradeResponse);
	}

	/**
	 * 获取主城的建筑列表
	 */
	private void getBuildList() {
		// 查询建造的建筑列表
		long cityId = cityDomain.getCityId();
		long relaId = cityDomain.getRelaId();

		// 建筑列表集合
		List<CityBuild> cityBuildList = null;

		if (0 != relaId) {
			// 查询指定建筑
			cityBuildList = cityBuildInfoService.getAlerdyBuilded("rela_id",
					relaId);
		} else if (0 != cityId) {
			// 查询城邦中所有建筑
			cityBuildList = cityBuildInfoService.getAlerdyBuilded("city_id",
					cityId);
		}

		// 获取列表的响应字符串
		String responseStr = CityRequestAnalysis
				.getCityBuildResponse(cityBuildList);
		System.out.println(responseStr);

		// 将列表写回
		SocketUtil.responseClient(cityDomain.getCtx(), responseStr);
	}

	/**
	 * 建造城市请求
	 */
	private void buildCityResponse() {
		buildCity();

	}

	/**
	 * 城市列表信息
	 */
	private void canBuildList() {
		// 获取种族信息
		int race = cityDomain.getRace();
		List<Build> buildList = cityBuildInfoService.getBuildsByRace(race);

		// 获取响应字符串
		String buildResponse = CityRequestAnalysis.getBuildResponse(buildList);
		SocketUtil.responseClient(cityDomain.getCtx(), buildResponse);
	}

	/**
	 * 获取城市列表
	 */
	private void getCityList() {
		// 获取roleId
		long roleId = cityDomain.getRoldId();
		// 城邦ID
		long cityId = cityDomain.getCityId();
		// 城邦列表信息集合对象
		List<City> cityList = null;
		try {
			if (0 == cityId) {
				cityList = cityBuildInfoService.getCitysByRoleId(roleId);
			} else {
				cityList = cityBuildInfoService.getCityInfo(" where t.city_id = " + cityId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SocketUtil.responseClient(cityDomain.getCtx(), JSONUtil.getBooleanResponse(false));
			return;
		}

		// 判定城镇列表是否为空
		if (cityList == null || cityList.size() == 0) {
			// 如果城镇列表为空，则说明建角色时创建主城失败，则需要重新创建
			cityDomain.setCityType(Constants.MAIN_CITY);
			buildCity();

			// 重新查询城市列表
			try {
				cityList = cityBuildInfoService.getCitysByRoleId(roleId);
			} catch (Exception e) {
				e.printStackTrace();
				SocketUtil.responseClient(cityDomain.getCtx(), JSONUtil.getBooleanResponse(false));
				return;
			}
		}

		// 获取城邦BUFF信息
		cityList = cityBuildInfoService.getCityBuffs(cityList);
		// 获取响应字符串
		String responseStr = CityRequestAnalysis.getCityListResponse(cityList);

		SocketUtil.responseClient(cityDomain.getCtx(), responseStr);
	}

	/**
	 * 城市迁移
	 */
	private void cityMove() {
		// 创建城市实体
		City city = new City();
		city.setCity_id(cityDomain.getCityId());
		city.setSite_x(cityDomain.getTargetX());
		city.setSite_y(cityDomain.getTargetY());
		// 构建迁城后 原始坐标状态
		LkMap fromPoint = new LkMap(cityDomain.getX(), cityDomain.getY(),
				Constants.MAP_EMPTY_POINT, 0);
		// 构建迁城后 新坐标状态
		LkMap toPoint = new LkMap(cityDomain.getTargetX(),
				cityDomain.getTargetY(), Constants.MAP_CITY_POINT,
				cityDomain.getRace());

		// 迁移城市
		Map<String, Long> map = null;
		String moveResponse = "";
		try {
			map = cityBuildInfoService.moveCity(fromPoint, toPoint, city,
					cityDomain.getRolePropId());
			// 获取响应字符串
			moveResponse = CityRequestAnalysis.getCityMoveResponse(map, city);
			// 新增操作日志
			String detail = "城镇迁移成功，城镇id：" + cityDomain.getCityId()
					+ "，迁移后坐标：（" + toPoint + "）";
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 新增操作日志
			String detail = "城镇迁移失败，详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);
			if (e.getMessage()
					.equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)) {
				JSONObject errJ = new JSONObject();
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
				moveResponse = errJ.toString();
			} else {
				map.put(Constants.RESULT_KEY, Constants.MOVE_CITY_FAIL);
				map.put(Constants.ID_KEY, -1l);
				moveResponse = CityRequestAnalysis.getCityMoveResponse(map,
						city);
			}

		}

		SocketUtil.responseClient(cityDomain.getCtx(), moveResponse);
	}

	/**
	 * 城市迁移到随机地点
	 */
	private void cityMoveRandom() {
		// 创建城市实体
		City city = new City();

		// 产生随机点
		while (true) {
			String siteStr = cityBuildInfoService.getEnableRadomPoint();
			JSONObject siteObj = JSONObject.fromObject(siteStr);
			int x = siteObj.getInt(Constants.X_KEY);
			int y = siteObj.getInt(Constants.Y_KEY);

			boolean flag = isPointUsed(x, y);

			// 如果已经在处理中则需要重新随点,否则就在该点上建造
			if (!flag) {
				city.setSite_x(x);
				city.setSite_y(y);
				break;
			}
		}

		// 将城市迁移到指定地点
		city.setCity_id(cityDomain.getCityId());
		// 构建迁城后 原始坐标状态
		LkMap fromPoint = new LkMap(cityDomain.getX(), cityDomain.getY(),
				Constants.MAP_EMPTY_POINT, 0);
		// 构建迁城后 新坐标状态
		LkMap toPoint = new LkMap(city.getSite_x(), city.getSite_y(),
				Constants.MAP_CITY_POINT, cityDomain.getRace());

		// 迁移城市
		Map<String, Long> map = null;
		String moveResponse = "";
		try {
			map = cityBuildInfoService.moveCity(fromPoint, toPoint, city,
					cityDomain.getRolePropId());
			// 移除缓存点
			Point point = new Point(city.getSite_x(), city.getSite_y());
			pointArr.remove(point);
			moveResponse = CityRequestAnalysis.getCityMoveResponse(map, city);
			// 新增操作日志
			String detail = "城镇迁移成功，城镇id：" + cityDomain.getCityId()
					+ "，迁移后坐标：（" + toPoint + "）";
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 新增操作日志
			String detail = "城镇迁移失败，详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);
			if (e.getMessage()
					.equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)) {
				JSONObject errJ = new JSONObject();
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
				moveResponse = errJ.toString();
			} else {
				map.put(Constants.RESULT_KEY, Constants.MOVE_CITY_FAIL);
				map.put(Constants.ID_KEY, -1l);
				moveResponse = CityRequestAnalysis.getCityMoveResponse(map,
						city);
			}

		}
		SocketUtil.responseClient(cityDomain.getCtx(), moveResponse);
	}

	/**
	 * 城市重命名
	 */
	private void cityRename() {
		// 创建城市实体
		City city = new City();
		city.setCity_id(cityDomain.getCityId());
		city.setName(cityDomain.getCityName());

		// 修改城市名字
		boolean result = cityBuildInfoService.updateCityName(city);

		// 获取响应字符串
		String renameResponse = JSONUtil.getBooleanResponse(result);
		SocketUtil.responseClient(cityDomain.getCtx(), renameResponse);

		// 新增操作日志
		String detail = "城镇更名成功，城镇id：" + cityDomain.getCityId() + "，更改后城镇名："
				+ cityDomain.getCityName();
		OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
				OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

	}

	/******************************** 内部使用 **********************************/
	/**
	 * 判定该地图点是否已经被申请占用了
	 * 
	 * @return
	 */
	private synchronized boolean isPointUsed(int x, int y) {
		// 判定该点是否已经在缓存中了
		for (int i = 0; i < pointArr.size(); i++) {
			Point point = pointArr.get(i);
			if (point.x == x && point.y == y) {
				return true;
			}
		}

		// 将该点加入申请缓存中
		Point point = new Point(x, y);
		pointArr.add(point);

		return false;
	}

	/**
	 * 建筑升级完成请求
	 */
	private void buildUpgradeFinished() {
		// 建筑对象
		CityBuild cityBuild = new CityBuild();
		cityBuild.setRela_id(cityDomain.getRelaId());
		cityBuild.setCurr_lev(cityDomain.getBuildLev());
		long roleId = cityDomain.getRoldId();

		// 建筑升级调用
		String res = "";
		String detail = "";
		int opFlag = 1;
		try {
			res = cityBuildInfoService.buildLevUpEnd(cityBuild, roleId);
			detail = "建筑升级完成，建筑id：" + cityDomain.getRelaId() + "，升级后建筑等级："
					+ cityDomain.getBuildLev();
			opFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			res = JSONUtil.getBooleanResponse(false);
			detail = "建筑升级失败，建筑id：" + cityDomain.getRelaId() + "，升级后建筑等级："
					+ cityDomain.getBuildLev();
			opFlag = 0;
		}

		// 新增操作日志
		OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
				OperateLogUtil.CITY_MODULE, opFlag);
		// 获取响应字符串
		SocketUtil.responseClient(cityDomain.getCtx(), res);
	}

	/**
	 * 建筑建造/升级取消功能
	 */
	private void cancelUpgrade() {
		// 建筑取消升级请求
		long rela_id = cityDomain.getRelaId();
		long role_id = cityDomain.getRoldId();
		int curr_lev = cityDomain.getBuildLev();// 当前建筑等级
		int food = cityDomain.getFood();
		int wood = cityDomain.getWood();
		int stone = cityDomain.getStone();
		int gold = cityDomain.getGold();
		boolean result = false;
		try {
			result = cityBuildInfoService.cancelLevUp(role_id, rela_id,
					curr_lev, food, wood, stone, gold);
			// 新增操作日志
			String detail = "取消建筑升级/建造请求成功，建筑id：" + rela_id;
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			// 新增操作日志
			String detail = "取消建筑升级/建造请求失败，详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

		}

		// 响应客户端取消升级请求
		SocketUtil.responseClient(cityDomain.getCtx(),
				JSONUtil.getBooleanResponse(result));

	}

	// /**
	// * 建筑升级进度查询请求
	// */
	// private void buildUpgradeRate()
	// {
	// //查询建筑升级情况
	// long buildId = cityDomain.getRelaId();
	// CityBuild cityBuild = cityBuildInfoService.getLevUpInfo(buildId);
	//
	// //转换时间
	// SimpleDateFormat formatDate = new
	// SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	// Date finishedTime = null;
	// Date now = null;
	// try
	// {
	// finishedTime = formatDate.parse(cityBuild.getLev_up_t());
	// now = formatDate.parse(cityBuild.getNow_db());
	// }
	// catch (ParseException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// //响应字符串
	// String rateResponse = "";
	//
	// if (now.before(finishedTime))
	// {
	// //计算时间差返回
	// long secTime = (finishedTime.getTime() - now.getTime()) / 1000;
	// CityBuild build = new CityBuild();
	// build.setLev_up_t(secTime + "");
	// rateResponse = CityRequestAnalysis.getUpgradeRate(Constants.RATE_DOING,
	// build);
	// }
	// else
	// {
	// //更新数据库的等级
	// CityBuild build = new CityBuild();
	// build.setRela_id(cityDomain.getRelaId());
	// int lev = cityBuild.getCurr_lev() + 1;
	// build.setCurr_lev(lev);
	//
	// //
	//
	// //建筑升级调用
	// boolean result = cityBuildInfoService.buildLevUpEnd(build);
	// if (result)
	// {
	// rateResponse =
	// CityRequestAnalysis.getUpgradeRate(Constants.RATE_GET_SUCCESS, build);
	// }
	// else
	// {
	// rateResponse = CityRequestAnalysis.getUpgradeRate(Constants.RATE_WRONG,
	// build);
	// }
	// }
	//
	// //将响应字符串写回客户端
	// SocketUtil.responseClient(cityDomain.getCtx(), rateResponse);
	// }

	/**
	 * 拆除建筑请求
	 */
	private void deleteBuild() {
		// 城市建筑关联id
		long relaId = cityDomain.getRelaId();
		// 城邦ID
		long cityId = cityDomain.getCityId();
		// 调用删除函数 获取最新资源产量
		City yieldInfo = cityBuildInfoService.destoryBuild(relaId, cityId);

		// 调用回调函数
		SocketUtil.responseClient(cityDomain.getCtx(),
				CityRequestAnalysis.getNewYieldDelete(yieldInfo));
		// 新增操作日志
		String detail = "成功拆除建筑";
		OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
				OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

	}

	/**
	 * 查询建筑升级消耗
	 */
	private void getBuildUpgradeUse() {
		// 查询消耗情况
		BuildCreate use = cityBuildInfoService.buildLevupUse(
				cityDomain.getBuildType(), cityDomain.getBuildLev());

		// 将消耗情况写回客户端
		String useResponse = CityRequestAnalysis.getUseResponse(use);
		SocketUtil.responseClient(cityDomain.getCtx(), useResponse);
	}

	/**
	 * 城镇保护
	 */
	private void protectCity() {
		// 保护城镇
		long cityId = cityDomain.getCityId();
		long rolePropId = cityDomain.getRolePropId();
		int protectTime = cityDomain.getProtectTime();
		boolean result = false;
		try {
			result = cityBuildInfoService.usePropsProtectCity(cityId,
					rolePropId, protectTime);
			// 新增操作日志
			String detail = "城镇使用保护道具成功，城镇id:" + cityId;
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e.getMessage()
					.equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)) {
				JSONObject errJ = new JSONObject();
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
				// 相应客户端
				SocketUtil.responseClient(cityDomain.getCtx(), errJ.toString());
				return;
			}
			// 新增操作日志
			String detail = "城镇使用保护道具失败,详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
					OperateLogUtil.CITY_MODULE, OperateLogUtil.FAIL);

		}

		// 相应客户端
		SocketUtil.responseClient(cityDomain.getCtx(),
				JSONUtil.getBooleanResponse(result));
	}

	/**
	 * 建筑加速升级
	 */
	private void buildUpgradeAcce() {
		// 加速升级
		long cityBuildId = cityDomain.getRelaId();
		long roleId = cityDomain.getRoldId();
		int diamondNum = cityDomain.getDiamondNum();
		int nextLev = cityDomain.getBuildLev();

		String res = "";
		String detail = "";
		int opFlag = 1;
		try {
			res = cityBuildInfoService.usePropsLevUpEnd(cityBuildId, roleId,
					nextLev, diamondNum);
			detail = "建筑升级完成，建筑id：" + cityDomain.getRelaId() + "，升级后建筑等级："
					+ cityDomain.getBuildLev();
			opFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
			res = JSONUtil.getBooleanResponse(false);
			detail = "建筑升级失败，建筑id：" + cityDomain.getRelaId() + "，升级后建筑等级："
					+ cityDomain.getBuildLev();
			opFlag = 0;
		}

		// 新增操作日志
		OperateLogUtil.insertOperateLog(cityDomain.getRoldId(), detail,
				OperateLogUtil.CITY_MODULE, opFlag);
		// 获取响应字符串
		SocketUtil.responseClient(cityDomain.getCtx(), res);
	}

	/**
	 * 城镇中驻扎的英雄列表（包括空闲英雄、出去狩猎等活动英雄）
	 */
	private void cityHaveHero() {
		// 查询城镇中驻扎的英雄
		long cityId = cityDomain.getCityId();
		List<RoleHero> heroList = roleHeroInfoService.getHerosByCityId(cityId);

		// 将英雄列表的概述信息返回
		String heroListResponse = HeroRequestAnalysis.getHeroListInfo(heroList);
		SocketUtil.responseClient(cityDomain.getCtx(), heroListResponse);
	}
}
