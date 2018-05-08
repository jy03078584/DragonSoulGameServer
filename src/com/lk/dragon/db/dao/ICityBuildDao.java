 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: ICityBuildDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午3:03:50 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Build;
import com.lk.dragon.db.domain.BuildCreate;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;

/**  
 * @Description:城市-建筑关联接口
 */
public interface ICityBuildDao {

	/**
	 * 新建城镇
	 * @param city
	 * @return
	 * @throws Exception
	 */
	public Long createNewCity(City city)throws Exception;
	
	/**
	 * 建筑升级完成Oracle函数
	 * @return
	 * @throws Exception
	 */
	public String callBuildUpFinishFun(long role_id,long rela_id,int lev,int diamon)throws Exception;
	
	/**
	 * 新建建筑
	 * @param cityBuild
	 * @return
	 * @throws Exception
	 */
	public Long createNewBuild(CityBuild cityBuild)throws Exception;
	
	
	/**
	 * 新建特殊建筑:城镇中心
	 * @param cityBuild
	 * @return
	 * @throws Exception
	 */
	public long createHomeBuild(CityBuild cityBuild)throws Exception;
	/**
	 * 查询当前种族城镇大厅ID
	 * @return
	 */
	public int findHomeBuildByRace(int race) throws Exception;
	
	
	/**
	 * 根据种族查询该种族建筑
	 * @param race
	 * @return
	 * @throws Exception
	 */
	public List<Build> getBuildsByRace(int race)throws Exception;
	
	/**
	 * 查询角色已拥有的城邦列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<City> getCitysByRoleId(long roleId)throws Exception;
	
	
	/**
	 * 查询角色所有城邦建筑(除军队)总人口
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public int getCityEatByRoleId(long roleId)throws Exception;
	/**
	 * 根据城镇id查询城镇产量信息
	 * @param cityId
	 * @return
	 * @throws Exception
	 */
	public City getCityYieldReinInfoByCityId(long cityId) throws Exception;
	
	
	/**
	 * 建筑升级
	 * @param cityBuild
	 * @return
	 * @throws Exception
	 */
	public int buildLevUping(Map<String,Object> map)throws Exception;
	
	/**
	 * 建筑升级完成
	 * @param cityBuild
	 * @return
	 * @throws Exception
	 */
	public int buildLevUpEnd(CityBuild cityBuild)throws Exception;
	
	
	/**
	 * 查询建筑升级信息
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public CityBuild getLevUpInfo(long rela_id)throws Exception;
	
	/**
	 * 获取地图可用随机点
	 * @return
	 * @throws Exception
	 */
	public String getEnableRadomPoint()throws Exception;
	
	
	/**
	 * 获取指定城市中所有已建建筑
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<CityBuild> getAlerdyBuilded(Map<String,Long> map)throws Exception;
	
	
	/**
	 * 更改城邦名称
	 * @param city
	 * @return
	 * @throws Exception
	 */
	public int updateCityName(City city)throws Exception;
	
	/**
	 * 查询城邦Id
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public long selectCityIdByRelaId(long rela_id)throws Exception;
	
	/**
	 * 修改城邦人口
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateCityEat(Map<String,Object> map)throws Exception;
	
	/**
	 * 更改城邦坐标
	 * @param city
	 * @return
	 * @throws Exception
	 */
	public int updateCityPoint(City city)throws Exception;
	
	/**
	 * 拆除建筑
	 * @param rela_id
	 * @return
	 * @throws Exception
	 */
	public int destoryBuild(long rela_id)throws Exception;
	
	/**
	 * 某类建筑升级消耗相关
	 * @param conditionMap
	 * @return
	 * @throws Exception
	 */
	public BuildCreate buildLevupUse(Map<String,Integer> conditionMap)throws Exception;
	
	
	
	/**
	 * 修改城邦信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateCityInfo(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 获取指定建筑信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<CityBuild> selectBuildInfo(String condition)throws Exception;
	
	
	/**
	 * 查看城邦基础信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<City> getCityInfo(String condition)throws Exception;
	
	/**
	 * 查询特定城邦信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public City getCitysInfoByCondition(String condition)throws Exception;
	
	/**
	 * 获取指定建筑共提供人口数
	 * @param rela_id
	 * @return
	 * @throws Excepiton
	 */
	public int selectAddEatByRealId(long rela_id)throws Exception;
	
	/**
	 * 获取外城建筑对应等级的产量
	 * @return
	 * @throws Exception
	 */
	public List<CityBuild> getResourceYieldOut()throws Exception;
}
