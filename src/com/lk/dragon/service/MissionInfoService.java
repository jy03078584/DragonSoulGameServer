/** 
 *
 * @Title: MissionInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 上午11:46:52 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IMissionDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Mission;
import com.lk.dragon.db.domain.MissionCondition;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;

/**
 * @Description:任务模块业务层
 */
@Service
public class MissionInfoService {

	@Autowired
	private RolePropsInfoService rolePropsInfoService;
	@Autowired
	private IMissionDao missionDao;

	@Autowired
	private IRolePropsDao rolePropsDao;

	@Autowired
	private IRoleDao roleDao;

	public static final String REWARD_KEY = "rewards";
	public static final String ROLE_INFO_KEY = "roleInfo";
	public static final String NEXT_MISSIONS_KEY = "nextMissions";

	/** 引导任务 **/
	public static final int MISSION_TYPE_GUIDE = 0;
	/** 直线任务 **/
	public static final int MISSION_TYPE_BRANCH = 1;
	/** 日常任务 **/
	public static final int MISSION_TYPE_DAILY = 2;

	/** 里程碑式任务条件 **/
	public static final int CONDITION_TYPE_MAINPROCESS = 0;
	/** 建造式任务条件 **/
	public static final int CONDITION_TYPE_CONSTRUCT = 1;
	/** 招募式任务条件 **/
	public static final int CONDITION_TYPE_HIRE = 2;
	/** 击杀式任务条件 **/
	public static final int CONDITION_TYPE_KILL = 3;
	/** 收集式任务条件 **/
	public static final int CONDITION_TYPE_COLLECT = 4;

	/**
	 * 接取新任务
	 * 
	 * @param role_id
	 * @param mission_id
	 * @throws Exception
	 */
	public List<Mission> insertRoleMission(long role_id, int mission_id)
			throws Exception {
		Mission mission = new Mission(mission_id, role_id);
		// 插入数据
		missionDao.insertRoleMission(mission);

		// 返回该任务详细信息
		return missionDao.getMissionDetailByRoleId(mission);
	}

	/**
	 * 重置日常任务
	 * 
	 * @param role_id
	 * @param mission_id
	 *            :原任务ID
	 * @param use_diamon
	 * @return
	 * @throws Exception
	 */
	public List<Mission> resetDayTask(long role_id, int mission_id,int use_diamon,int role_lev) throws Exception {
		Mission srcMission = new Mission();
		srcMission.setRole_id(role_id);
		srcMission.setMission_id(mission_id);
		// 消耗钻石
		if (use_diamon > 0) {
			Role r = roleDao.selectRolesByRoleId(role_id);
			int lastDiamon = r.getDiamon() - use_diamon;
			if (lastDiamon < 0)
				throw new RuntimeException(
						RolePropsInfoService.PROPS_COUNT_ERROR_EN);
			roleDao.updateRoleInfo(r);
		}
		// 删除原任务
		missionDao.deleteRoleMission(srcMission);

		// 随机获取日常任务ID
		int newMissionId = missionDao.getRandomDayTaskId(role_lev);

		// 任务绑定角色
		return insertRoleMission(role_id, newMissionId);
	}

	/**
	 * 查询当前角色正在执行的任务列表
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<Mission> getMissionDetailByRoleId(Mission mission)
			throws Exception {
		return missionDao.getMissionDetailByRoleId(mission);
	}

	/**
	 * 删除任务:任务完成/放弃任务
	 * 
	 * @param mission
	 *            #mission_id# #role_id#
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleMission(Mission mission) throws Exception {
		return missionDao.deleteRoleMission(mission);
	}

	/**
	 * 更新任务进度
	 * 
	 * @param mission
	 *            ： #incCount# #mission_id# #role_id# #condition_id#
	 * @return
	 * @throws Exception
	 */
	public int updateRoleMission(Mission mission) throws Exception {
		return missionDao.updateRoleMission(mission);
	}

	/**
	 * 任务完成
	 * 
	 * @param mission
	 *            ：roleId missionId exp
	 * @throws Exception
	 */
	public HashMap<String, Object> roleMissionFinish(Mission mission)
			throws Exception {
		List<RoleProps> missionRewads = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 查看该任务条件类型
		List<MissionCondition> conditions = missionDao
				.getMissionCondition(mission);

		// 查询角色基本信息
		Role r = roleDao.selectRolesByRoleId(mission.getRole_id());
		r.setRole_id(mission.getRole_id());
		for (MissionCondition missionCondition : conditions) {
			// 搜集类任务
			if (missionCondition.getCondition_type() == CONDITION_TYPE_COLLECT) {
				// 消耗道具ID
				int prop_id = Integer.parseInt(missionCondition
						.getCondition_key());
				// 消耗数量
				int count = missionCondition.getCondition_val();

				// 针对各类道具分别处理
				switch (prop_id) {
				case RolePropsInfoService.PROPS_ID_FOOD:
					int lastFood = r.getFood() - count;
					if (lastFood < 0)
						throw new RuntimeException(
								RolePropsInfoService.PROPS_COUNT_ERROR_EN);
					r.setFood(lastFood);
					roleDao.updateRoleInfo(r);
					break;
				case RolePropsInfoService.PROPS_ID_WOOD:
					int lastWood = r.getWood() - count;
					if (lastWood < 0)
						throw new RuntimeException(
								RolePropsInfoService.PROPS_COUNT_ERROR_EN);
					r.setWood(lastWood);
					roleDao.updateRoleInfo(r);
					break;
				case RolePropsInfoService.PROPS_ID_STONE:
					int lastStone = r.getStone() - count;
					if (lastStone < 0)
						throw new RuntimeException(
								RolePropsInfoService.PROPS_COUNT_ERROR_EN);
					r.setStone(lastStone);
					roleDao.updateRoleInfo(r);
					break;
				case RolePropsInfoService.PROPS_ID_GOLD:
					int lastGold = r.getGold() - count;
					if (lastGold < 0)
						throw new RuntimeException(
								RolePropsInfoService.PROPS_COUNT_ERROR_EN);
					r.setGold(lastGold);
					roleDao.updateRoleInfo(r);
					break;
				default:
					map.put("role_id", mission.getRole_id());
					map.put("props_id", prop_id);
					List<RoleProps> useProps = rolePropsDao
							.getPropsByPropsId(map);
					if (useProps != null && useProps.size() > 0) {
						RoleProps rp = useProps.get(0);
						if (rp.getProps_count() - count < 0)
							throw new RuntimeException(
									RolePropsInfoService.PROPS_COUNT_ERROR_EN);
						rp.setProps_count(rp.getProps_count() - count);
						rolePropsDao.updatePropsCount(rp);
					}
					break;
				}

			}
		}
		// 删除角色任务记录
		missionDao.deleteRoleMission(mission);

		
		//角色完成任务后角色经验值
		int nowExp = r.getExp() + mission.getExp();
		//达到升级条件 角色升级
		if(r.getUp_exp() <= nowExp){
			r.setExp(nowExp - r.getUp_exp());
			int nextLev = r.getLev() + 1;
			r.setLev(nextLev);
			r.setUp_exp(CacheService.roleUpExpCache.get(nextLev));
		}else{
			r.setExp(nowExp);
		}
		
		
		// 经验增加
		roleDao.updateRoleInfo(r);

		// 领取奖励
		missionRewads = missionDao.getMissionRewardInfo(mission.getMission_id());
		// 奖励进入玩家包裹
		if (missionRewads != null && missionRewads.size() > 0) {
			missionRewads = rolePropsInfoService.anayAddRoleProps(missionRewads, mission.getRole_id());
		}

		// 领取后续任务
		// 查询后续任务ID
		List<Integer> nextMissionId = missionDao.getNextMissionId(mission.getMission_id());

		// 存放后续任务
		List<List<Mission>> nextMissions = new ArrayList<List<Mission>>();
		if (nextMissionId != null && nextMissionId.size() > 0) {
			// 该任务有后续任务
			for (Integer integer : nextMissionId) {
				List<Mission> newMission = insertRoleMission(
						mission.getRole_id(), integer);
				nextMissions.add(newMission);
			}
		}

		HashMap<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(REWARD_KEY, missionRewads);
		resMap.put(NEXT_MISSIONS_KEY, nextMissions);
		resMap.put(ROLE_INFO_KEY, r);
		return resMap;
	}
}
