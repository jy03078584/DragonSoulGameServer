 /** 
 *
 * @Title: RolePropsDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-15 上午11:39:30 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.DropRate;
import com.lk.dragon.db.domain.Equip;
import com.lk.dragon.db.domain.EquipGem;
import com.lk.dragon.db.domain.Gem;
import com.lk.dragon.db.domain.Hero;
import com.lk.dragon.db.domain.HeroEquip;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.RolePropsEquipPro;
import com.lk.dragon.service.RolePropsInfoService;

/**  
 * @Description:
 */
@Repository("rolePropsDao")
public class RolePropsDaoImpl extends BaseSqlMapDao implements IRolePropsDao {

	@Override
	public long addRoleProps(RoleProps props) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("rolePropsMap.addRoleProps", props);
	}

	@Override
	public RoleProps checkPropsEnabled(long rolePropsId) throws Exception {
		return (RoleProps) getSqlMapClientTemplate().queryForObject("rolePropsMap.checkPropsEnabled", rolePropsId);
	}

	@Override
	public int useProps(RoleProps props) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updateRemainCount", props);
	}

	@Override
	public int updatePropsCount(RoleProps props) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updatePropsCount", props);
	}

	@Override
	public RoleProps checkHasEnabledProps(RoleProps props) throws Exception {
		return (RoleProps)getSqlMapClientTemplate().queryForObject("rolePropsMap.checkHasEnabledProps", props);
	}

	@Override
	public int deleteProps(long role_props_id) throws Exception {
		return getSqlMapClientTemplate().delete("rolePropsMap.deleteProps",role_props_id);
	}

	@Override
	public int getPropsCount(long role_props_id) throws Exception {
		Object countO = getSqlMapClientTemplate().queryForObject("rolePropsMap.getPropsCount", role_props_id) ;
		return countO == null ? 0 : (Integer)countO;
	}

	@Override
	public int bindProps(long role_props_id) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.bindProps", role_props_id);
	}

	@Override
	public int callAddRoleProps(RoleProps roleProps) throws Exception {
		int resInt = 0;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("role_id", roleProps.getRole_id());
		paramMap.put("props_id", roleProps.getProps_id());
		paramMap.put("props_count", roleProps.getProps_count());
		String exInfo = roleProps.getExtra_info() == null ? "" : roleProps.getExtra_info();
		paramMap.put("extra_info",exInfo );
		getSqlMapClientTemplate().queryForObject("rolePropsMap.callAddRoleProps", paramMap);
		resInt = (Integer) paramMap.get("res");
		return resInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleProps> selectRolePropsByCondition(long role_id)throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.selectRolePropsByRoleId", role_id);
	}

	@Override
	public Gem getGemByLevType(Gem gem) throws Exception {
		return (Gem) getSqlMapClientTemplate().queryForObject("rolePropsMap.getGemByLevType", gem);
	}

	@Override
	public int onOffEquip(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.onOffEquip", map);
	}

	@Override
	public long heroUseEquip(HeroEquip heroEquip) throws Exception {
		getSqlMapClientTemplate().insert("rolePropsMap.heroUseEquip", heroEquip);
		return 1l ;
	}

	@Override
	public RolePropsEquipPro getEquipProperty(long role_props_id) {
		return (RolePropsEquipPro) getSqlMapClientTemplate().queryForObject("rolePropsMap.getEquipProperty", role_props_id);
	}

	@Override
	public int heroOffEquip(long role_props_id) throws Exception {
		return getSqlMapClientTemplate().delete("rolePropsMap.heroOffEquip", role_props_id);
	}

	@Override
	public int updateHeroProperty(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updateHeroProperty", map);
	}

	@Override
	public int deleteEquipValue(long role_props_id) throws Exception {
		return getSqlMapClientTemplate().delete("rolePropsMap.deleteEquipValue", role_props_id);
	}

	@Override
	public long hireNewHero(Map<String,Object> map) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("rolePropsMap.hireNewHero", map);
	}

	@Override
	public List<Gem> getGemInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getGemInfo", condition);
	}

	@Override
	public int updateEquipProperty(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updateEquipProperty", map);
	}

	@Override
	public int plSubPropsCount(Map<String, Object> map) throws Exception {
		
		//检验物品数量是否合法
		int srcCount = getPropsCount((Long) map.get("role_props_id"));
		if(map.get("operator").equals("-")){
			if(srcCount - (Integer)map.get("props_count_vo") < 0)
				throw new RuntimeException(RolePropsInfoService.PROPS_COUNT_ERROR_EN);
		}
		return getSqlMapClientTemplate().update("rolePropsMap.plSubPropsCount", map);
	}

	@Override
	public long addEquipGem(EquipGem equipGem) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("rolePropsMap.addEquipGem", equipGem);
	}

	@Override
	public int deleteEquipGem(String condition) throws Exception {
		return getSqlMapClientTemplate().delete("rolePropsMap.deleteEquipGem", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleProps> getRolePropsInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getRolePropsInfo", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleHero> getHeroPropertyByCondition(String condition)throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getHeroPropertyByCondition", condition);
	}

	@Override
	public List<Hero> getHeroCanHired(long city_id) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getHeroCanHired", city_id);
	}

	@Override
	public Hero getHeroBaseProperties(String condition) throws Exception {
		return (Hero) getSqlMapClientTemplate().queryForObject("rolePropsMap.getHeroBaseProperties", condition);
	}

	@Override
	public int updateHireFlag(long hero_id) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updateHireFlag", hero_id);
	}

	@Override
	public int callFlushHiredHeros(long city_id) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("p_city_id", city_id);
		map.put("p_flag", 1);
		map.put("p_race", -1);
		getSqlMapClientTemplate().queryForObject("rolePropsMap.callFlushHiredHeros", map);
		return 1;
	}

	@Override
	public Gem getGemPropsInfo(String condition) throws Exception {
		return (Gem) getSqlMapClientTemplate().queryForObject("rolePropsMap.getGemPropsInfo", condition);
	}

	@Override
	public int deleteRoleHero(String condition) throws Exception {
		return getSqlMapClientTemplate().delete("rolePropsMap.deleteRoleHero", condition);
	}

	@Override
	public int updateHerosStatus(String condition) throws Exception {
		return getSqlMapClientTemplate().update("rolePropsMap.updateHerosStatus", condition);
	}

	@Override
	public Hero checkHeroBasePro(long hero_id) throws Exception {
		return (Hero) getSqlMapClientTemplate().queryForObject("rolePropsMap.checkHeroBasePro", hero_id);
	}

	
	@Override
	public List<RolePropsEquipPro> getHeroOnEquipBase(long role_hero_id) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getHeroOnEquipBase", role_hero_id);
	}

	@Override
	public RoleProps selectRolePropsDetailById(Map<String, Object> conditonMap)
			throws Exception {
		return (RoleProps) getSqlMapClientTemplate().queryForObject("rolePropsMap.selectRolePropsDetailById", conditonMap);
	}

	@Override
	public boolean addHeroTrain(RoleHero roleHero) throws Exception {
		boolean flag = false;
		try {
			getSqlMapClientTemplate().insert("rolePropsMap.addHeroTrain", roleHero);
			flag = true;
		} catch (Exception e) {
			throw new RuntimeException("Enter HeroTrain Error");
		}
		return flag;
	}

	@Override
	public List<RoleHero> getHeroTrainInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getHeroTrainInfo", condition);
	}

	@Override
	public boolean cancelHeroTrain(String condtion) throws Exception {
		boolean flag = false;
		if(getSqlMapClientTemplate().delete("rolePropsMap.cancelHeroTrain",condtion) > 0)
			flag = true;
		return flag;
	}

	@Override
	public int getNpcDropRateMax(int npcId) throws Exception {
		Object rateMaxO = 	getSqlMapClientTemplate().queryForObject("rolePropsMap.getNpcDropRateMax", npcId);
		int rateMax = 0; 
		if(rateMaxO != null)
			rateMax = (Integer) rateMaxO;
		return rateMax ;
	}

	@Override
	public RoleProps getNpcDropInfo(DropRate dropRate) throws Exception {
		return (RoleProps) getSqlMapClientTemplate().queryForObject("rolePropsMap.getNpcDropInfo", dropRate);
	}

	@Override
	public Equip getEquipBaseInfo(int props_id) throws Exception {
		return (Equip) getSqlMapClientTemplate().queryForObject("rolePropsMap.getEquipBaseInfo", props_id);
	}

	@Override
	public List<RoleHero> checkHerosStatus(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.checkHerosStatus", condition);
	}

	@Override
	public String getSysReward(int props_id) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("rolePropsMap.getSysReward", props_id);
	}

	@Override
	public List<RoleProps> getSysRewardDetail(Map<String, Integer> map)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getSysRewardDetail", map);
	}

	@Override
	public RoleHero getHeroPropertyByRoleHeroId(long role_hero_id)throws Exception {
		return (RoleHero) getSqlMapClientTemplate().queryForObject("rolePropsMap.getHeroPropertyByRoleHeroId", role_hero_id);
	}

	@Override
	public List<RoleProps> getPropsByPropsId(Map<String, Object> map)throws Exception {
		return getSqlMapClientTemplate().queryForList("rolePropsMap.getPropsByPropsId", map);
	}



}
