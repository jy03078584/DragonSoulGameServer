/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: ArmsDeployDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-31 上午9:44:04 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.ReinForceArm;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.service.CacheService;
import com.lk.dragon.util.DateTimeUtil;

/**
 * @Description:
 */
@Repository("armsDeployDao")
public class ArmsDeployDaoImpl extends BaseSqlMapDao implements IArmsDeployDao {

	@Override
	public long createConscriptTeam(Map<String, Object> map) throws Exception {
		return (Long) getSqlMapClientTemplate().insert(
				"warMap.createConscriptTeam", map);
	}

	@Override//long city_id, long arms_id, int arms_count
	public int callProductArms(ArmsDeploy armsDeploy)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int res = -1;
		map.put("city_id_param", armsDeploy.getCity_id());
		map.put("arm_id_param", armsDeploy.getArms_id());
		map.put("arm_count_param", armsDeploy.getArm_count());

		getSqlMapClientTemplate().queryForObject("warMap.callProductArms", map);
		res = (Integer) map.get("res");
		return res;
	}

	@Override
	public int updateConScriptTime(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("warMap.updateConScriptTime",
				map);
	}

	@Override
	public int deleteConScript(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().delete("warMap.deleteConScript", map);
	}

	@Override
	public int findConScriptIsEnd(String condition) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"warMap.findConScriptIsEnd", condition);
	}

	@Override
	public List<ArmsDeploy> selectConScriptByRoleId(long role_id)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectConScriptByRoleId", role_id);
	}

	@Override
	public List<RoleHero> selectHeroArmsStatus(String condition)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectHeroArmsStatus", condition);
	}

	/**
	 * 查询英雄所率领部队的详细信息
	 * 
	 * @param roleHeroId
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectHeroArmsDetail(Long roleHeroId)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectHeroArmsDetail", roleHeroId);
	}

	@Override
	public List<ArmsDeploy> selectCityArmsInfo(String condition)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectCityArmsInfo", condition);
	}

	@Override
	public int updateHeroArmsStatus(ArmsDeploy heroArm) throws Exception {
		return getSqlMapClientTemplate().update("warMap.updateHeroArmsStatus",
				heroArm);
	}

	@Override
	public int deleteCityArms(long city_id) throws Exception {
		return getSqlMapClientTemplate().delete("warMap.deleteCityArms",
				city_id);
	}

	@Override
	public Object insertCityArms(ArmsDeploy armsDeploy) throws Exception {
		return getSqlMapClientTemplate().insert("warMap.insertCityArms",
				armsDeploy);
	}

	@Override
	public Object createWarTeam(WarProduce  warProduce) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("warMap.createWarTeam",warProduce);
	}

	@Override
	public List<WarProduce> selectArriveTagWarTeam() throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectArriveTagWarTeam");
	}

	@Override
	public int updateWarTeamInfo(WarProduce warProduce) throws Exception {
		return getSqlMapClientTemplate()
				.update("warMap.updateWarTeamInfo", warProduce);
	}

	@Override
	public int deleteWarTeamInfo(long war_team_id) throws Exception {
		return getSqlMapClientTemplate().delete("warMap.deleteWarTeamInfo",
				war_team_id);
	}

	@Override
	public List<ArmsDeploy> selectHeroArmsInfoProduce(long role_hero_id)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectHeroArmsInfoProduce", role_hero_id);
	}

	@Override
	public int updateHeroArms(String condition) throws Exception {
		return getSqlMapClientTemplate().update("warMap.updateHeroArms",
				condition);
	}

	@Override
	public List<ArmsDeploy> selectHeroArmsInfoWarEnd(long role_hero_id)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectHeroArmsInfoWarEnd", role_hero_id);
	}

	@Override
	public List<ArmsDeploy> selectCityArmsInfoProduce(long city_id)
			throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectCityArmsInfoProduce", city_id);
	}

	@Override
	public int updateCityArmsWarEnd(String condition) throws Exception {
		return getSqlMapClientTemplate().update("warMap.updateCityArmsWarEnd",
				condition);
	}

	@Override
	public List<WarProduce> selectHerosInTeam(String condition)throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectHerosInTeam", condition);
	}

	@Override
	public int deleteTeamHeroInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().delete("warMap.deleteTeamHeroInfo",
				condition);
	}

	@Override
	public int callCheckTeamHero(String dead_teams_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int res = -1;
		map.put("dead_teams_id", dead_teams_id);

		getSqlMapClientTemplate().queryForObject("warMap.callCheckTeamHero",
				map);
		res = (Integer) map.get("res");
		return res;
	}

	@Override
	public List<ArmsDeploy> selectArmsDetailInfo(String condition)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectArmsDetailInfo", condition);
	}

	@Override
	public List<WarProduce> selectWildSrcInfo(String conditon) throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectWildSrcInfo", conditon);
	}

	@Override
	public int updateWildSrcOwner(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("warMap.updateWildSrcOwner",map);
	}

	@Override
	public WarProduce selectWarTeamById(long war_team_id)throws Exception {
		Element element = CacheService.warTeamCache.get(war_team_id);
		WarProduce warProduce;
		if(element != null){
			warProduce = (WarProduce)element.getObjectValue();
			//队列处于驻扎状态 移除缓存
			if(warProduce.getStatus()>3)
				CacheService.warTeamCache.remove(war_team_id);
		}else{
			warProduce = (WarProduce) getSqlMapClientTemplate().queryForObject("warMap.selectWarTeamById", war_team_id);
				if(warProduce != null && warProduce.getStatus() < 3){
					CacheService.warTeamCache.put(new Element(warProduce.getWar_team_id(), warProduce));
				}
		}
		
		return warProduce;
	}

	@Override
	public String getArriveTimeDb(int use_time) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject(
				"warMap.getArriveTimeDb", use_time);
	}

	@Override
	public Object insertTeamHero(Map<String, Long> map) throws Exception {
		return (Object) getSqlMapClientTemplate().insert(
				"warMap.insertTeamHero", map);
	}

	@Override
	public ArmsDeploy getArmsBuildInfo(Map<String, Object> map)
			throws Exception {
		ArmsDeploy arm;
		Results results = CacheService.armsCache
				.createQuery()
				.addCriteria(CacheService.armsCache
								.getSearchAttribute("race")
								.eq(map.get("race"))
								.and(CacheService.armsCache.getSearchAttribute("hire_build").eq(map.get("hire_build"))))
				.includeValues().execute();
		List<Result> armInCache = results.all();

		try {
			if (armInCache != null && armInCache.size() > 0) {
				arm = (ArmsDeploy) armInCache.get(0).getValue();
			} else {
				arm = (ArmsDeploy) getSqlMapClientTemplate().queryForObject("roleMap.selectRolesByUserId", map);
				CacheService.armsCache.put(new Element(arm.getArm_id(), arm));
			}
		} finally {
			results.discard();
		}
		return arm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> selectDistintTeamId(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList(
				"warMap.selectDistintTeamId", condition);
	}

	@Override
	public List<WarProduce> selectWarTeamDetailInfo(String conditionId)throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectWarTeamDetailInfo", conditionId);
	}

	@Override
	public List<RoleHero> getSrcHerosInfoByCondition(String condition)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.getSrcHerosInfoByCondition", condition);
	}

	@Override
	public List<ArmsDeploy> selectWildHurtRandomInfo(
			Map<String, Integer> conditionMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectWildHurtRandomInfo", conditionMap);
	}

	@Override
	public int selectTotalCommand(long team_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"warMap.selectTotalCommand", team_id);
	}

	@Override
	public List<WarProduce> selectArriveTagWarTeam2(String value)throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectArriveTagWarTeam2", value);
	}

	@Override
	public List<ArmsDeploy> getAllArmsInfo() {
		return (List<ArmsDeploy>) getSqlMapClientTemplate().queryForList("warMap.getAllArmsInfo");
	}

	@Override
	public ArmsDeploy getArmInfoByArmId(int arm_id) {
		ArmsDeploy arm;
		Element armElement = CacheService.armsCache.get(arm_id);
		if(armElement != null){
			arm =  (ArmsDeploy) armElement.getObjectValue();
		}else{
			arm = (ArmsDeploy) getSqlMapClientTemplate().queryForObject("warMap.getArmInfoByArmId",arm_id);
			CacheService.armsCache.put(new Element(arm.getArm_id(), arm));
		}
		
		return arm;
	}

	@Override
	public List<ReinForceArm> selectReinForceArm(long role_id) throws Exception {
		return getSqlMapClientTemplate().queryForList("warMap.selectReinForceArm", role_id);
	}

	@Override
	public Long getWarTeamSeq() throws Exception {
		return (Long) getSqlMapClientTemplate().queryForObject("warMap.getWarTeamSeq");
	}

	@Override
	public List<WarProduce> initWarTeamCache() {
		return getSqlMapClientTemplate().queryForList("warMap.initWarTeamCache");
	}

	@Override
	public List<RoleHero> getHerosInfoByCondition(String condition) {
		return getSqlMapClientTemplate().queryForList("warMap.getHerosInfoByCondition", condition);
	}

	@Override
	public List<WarProduce> getCurrentEnemyTeam(long roleId) {
		return getSqlMapClientTemplate().queryForList("warMap.getCurrentEnemyTeam", roleId);
	}

	@Override
	public int checkHeroHasArmsNow(long roleHeroId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("warMap.checkHeroHasArmsNow", roleHeroId);
	}

}
