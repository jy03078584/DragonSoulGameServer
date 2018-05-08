 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IMissonDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 上午11:43:20 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.Mission;
import com.lk.dragon.db.domain.MissionCondition;
import com.lk.dragon.db.domain.RoleProps;

/**  
 * @Description:任务模块接口
 */
public interface IMissionDao {
	/**
	 * 查询当前角色正在执行的任务信息
	 * @param mission
	 * @return
	 * @throws Exception
	 */
	public List<Mission> getMissionDetailByRoleId(Mission mission)throws Exception;
	
	/**
	 * 查看任务完成条件
	 * @param mission
	 * @return
	 * @throws Exception
	 */
	public List<MissionCondition> getMissionCondition(Mission mission)throws Exception;
	
	/**
	 * 查询奖励信息
	 * @param mission_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> getMissionRewardInfo(int mission_id)throws Exception;
	/**
	 * 更新任务进度
	 * @param mission
	 * @return
	 * @throws Exception
	 */
	public int updateRoleMission(Mission mission)throws Exception;
	
	/**
	 * 删除任务
	 * @param mission
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleMission(Mission mission)throws Exception;
	
	/**
	 * 接取新任务
	 * @param mission
	 * @return
	 * @throws Exception
	 */
	public Object insertRoleMission(Mission mission)throws Exception;
	
	/**
	 * 随机选取一条日常任务
	 * @return
	 * @throws Exception
	 */
	public int getRandomDayTaskId(int role_lev)throws Exception;
	
	/**
	 * 查询后续任务ID
	 * @param messionId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getNextMissionId(int messionId)throws Exception;
}
