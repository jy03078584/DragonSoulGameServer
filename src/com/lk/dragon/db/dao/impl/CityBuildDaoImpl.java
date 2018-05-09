 /** 
 *
 * @Title: CityBuildDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午4:06:28 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.domain.Build;
import com.lk.dragon.db.domain.BuildCreate;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;

/**  
 * @Description:
 */
@Repository("cityBuildDao")
public class CityBuildDaoImpl extends BaseSqlMapDao implements ICityBuildDao {

	@Override
	public Long createNewCity(City city) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("cityBuildMap.createNewCity", city);
	}

	@Override
	public Long createNewBuild(CityBuild cityBuild) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("cityBuildMap.createNewBuild", cityBuild);
	}

	@Override
	public int findHomeBuildByRace(int race ) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("cityBuildMap.findHomeBuildByRace", race);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Build> getBuildsByRace(int race) throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.getBuildsByRace", race);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCitysByRoleId(long roleId) throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.getCitysByRoleId", roleId);
	}
	
	public City getCityYieldReinInfoByCityId(long cityId) throws Exception
	{
	    return (City)getSqlMapClientTemplate().queryForList("cityBuildMap.getCityYieldReinInfoByCityId", cityId).get(0);
	}

	@Override
	public int buildLevUping(Map<String,Object> map) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.buildLevUping", map);
	}

	@Override
	public String getEnableRadomPoint() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("cityBuildMap.getEnableRadomPoint");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityBuild> getAlerdyBuilded(Map<String,Long> map) throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.getAlerdyBuilded", map);
	}

	@Override
	public int updateCityName(City city) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.updateCityName", city);
	}

	@Override
	public int updateCityPoint(City city) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.updateCityPoint", city);
	}

	@Override
	public int buildLevUpEnd(CityBuild cityBuild) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.buildLevUpEnd", cityBuild);
	}

	@Override
	public CityBuild getLevUpInfo(long rela_id) throws Exception {
		return (CityBuild) getSqlMapClientTemplate().queryForObject("cityBuildMap.getLevUpInfo", rela_id);
	}

	@Override
	public int destoryBuild(long rela_id) throws Exception {
		return getSqlMapClientTemplate().delete("cityBuildMap.destoryBuild", rela_id);
	}

	@Override
	public BuildCreate buildLevupUse(Map<String, Integer> conditionMap)throws Exception {
		return (BuildCreate) getSqlMapClientTemplate().queryForObject("cityBuildMap.buildLevupUse", conditionMap);
	}

	@Override
	public int updateCityEat(Map<String,Object> map) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.updateCityEat", map);
	}

	@Override
	public long selectCityIdByRelaId(long rela_id) throws Exception {
		return (Long) getSqlMapClientTemplate().queryForObject("cityBuildMap.selectCityIdByRelaId", rela_id);
	}

	@Override
	public int updateCityInfo(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.updateCityInfo", map);
	}

	@Override
	public List<CityBuild> selectBuildInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.selectBuildInfo", condition);
	}

	@Override
	public List<City> getCityInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.getCityInfo", condition);
	}

	@Override
	public City getCitysInfoByCondition(String condition) throws Exception {
		return (City) getSqlMapClientTemplate().queryForObject("cityBuildMap.getCitysInfoByCondition", condition);
	}

	@Override
	public int selectAddEatByRealId(long rela_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("cityBuildMa.selectBuildCreateByIdp", rela_id);
	}

	@Override
	public List<CityBuild> getResourceYieldOut() throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.getResourceYieldOut");
	}

	@Override
	public long createHomeBuild(CityBuild cityBuild) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("cityBuildMap.createHomeBuild", cityBuild);
	}

	@Override
	public int getCityEatByRoleId(long roleId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("cityBuildMap.getCityEatByRoleId", roleId);
	}

	@Override
	public String callBuildUpFinishFun(long role_id, long rela_id, int lev,int diamon) throws Exception {
		Map<String,Object> callMap = new HashMap<String,Object>();
		String result = "";
		callMap.put("p_role_id", role_id);
		callMap.put("p_rela_id", rela_id);
		callMap.put("p_lev", lev);
		callMap.put("p_diamon", diamon);
		getSqlMapClientTemplate().queryForObject("cityBuildMap.callBuildUpFinishFun", callMap);
		//System.out.println("======================"+(Integer)callMap.get("res")+"==================");
		result = (String) callMap.get("res");
		return result == null || result.equals("") ? "{\"result\":-1}" : result;
	}
	
}
