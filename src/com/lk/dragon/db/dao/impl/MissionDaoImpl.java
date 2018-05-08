 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: MissionDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 上午11:44:52 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.IMissionDao;
import com.lk.dragon.db.domain.Mission;
import com.lk.dragon.db.domain.MissionCondition;
import com.lk.dragon.db.domain.RoleProps;

/**  
 * @Description:
 */
@Repository("missionDao")
public class MissionDaoImpl extends BaseSqlMapDao implements IMissionDao {

	@Override
	public List<Mission> getMissionDetailByRoleId(Mission mission)throws Exception {
		return getSqlMapClientTemplate().queryForList("missionMap.getMissionDetailByRoleId", mission);
	}

	@Override
	public int updateRoleMission(Mission mission) throws Exception {
		return getSqlMapClientTemplate().update("missionMap.updateRoleMission", mission);
	}

	@Override
	public int deleteRoleMission(Mission mission) throws Exception {
		return getSqlMapClientTemplate().delete("missionMap.deleteRoleMission",mission);
	}

	@Override
	public List<RoleProps> getMissionRewardInfo(int mission_id)throws Exception {
		return getSqlMapClientTemplate().queryForList("missionMap.getMissionRewardInfo", mission_id);
	}

	@Override
	public Object insertRoleMission(Mission mission) throws Exception {
		return getSqlMapClientTemplate().insert("missionMap.insertRoleMission", mission);
	}

	@Override
	public int getRandomDayTaskId(int role_lev) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("missionMap.getRandomDayTaskId",role_lev);
	}

	@Override
	public List<Integer> getNextMissionId(int messionId) throws Exception {
		return getSqlMapClientTemplate().queryForList("missionMap.getNextMissionId", messionId);
	}

	@Override
	public List<MissionCondition> getMissionCondition(Mission mission)throws Exception {
		return getSqlMapClientTemplate().queryForList("missionMap.getMissionCondition", mission);
	}

}
