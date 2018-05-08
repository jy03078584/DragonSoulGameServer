/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： MallAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:30:56
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.dao.impl.MissionDaoImpl;
import com.lk.dragon.db.domain.Mission;
import com.lk.dragon.db.domain.MissionCondition;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.server.domain.MissionDomain;
import com.lk.dragon.service.MissionInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class MissionRequestAnalysis {
	/******************** 请求字段键值 *******************/
	private static final String MISSION_ID_KEY = "mission_id";
	private static final String ROLE_ID_KEY = "role_id";
	private static final String EXP_KEY = "exp";
	private static final String DIAMON_KEY = "diamon";
	private static final String MISSION_TYPE_KEY = "missionType";
	// 任务增长进度
	private static final String INC_RATE_KEY = "inc_rate";
	private static final String CONDITION_ID_KEY = "condition_id";
	private static final String ROLE_LEV_KEY = "role_lev";

	/********************* 响应字符串 ****************************/
	// 响应的关键字段
	// private static final String RESULT_KEY = "result";
	// //商城物品列表响应字段
	// private static final String MALL_PROP_KEY = "mall_props";
	// private static final String MALL_PROP_ID_KEY = "mall_prop_id";
	// private static final String PRIVCE_DIAMON_KEY = "privce_diamon";
	// private static final String PRIVCE_GOLD_KEY = "privce_gold";
	// private static final String PROPS_NAME_KEY = "props_name";
	// private static final String PROPS_ICON_KEY = "props_icon";
	// private static final String PROPS_DESC_KEY = "desc";
	// private static final String PROPS_TYPE_KEY = "props_type";

	/**
	 * 将请求信息转化为商城实体信息
	 * 
	 * @param json
	 * @return
	 */
	public static MissionDomain getMissionInfo(String json) {
		MissionDomain missionDomain = new MissionDomain();

		// 获取请求数据
		JSONObject missionData = JSONUtil.getData(json);
		
		missionDomain.setType(missionData.getInt(MISSION_TYPE_KEY));
		missionDomain.setRole_id(missionData.has(ROLE_ID_KEY) ? missionData
				.getLong(ROLE_ID_KEY) : 0);
		missionDomain
				.setMisssion_id(missionData.has(MISSION_ID_KEY) ? missionData
						.getInt(MISSION_ID_KEY) : 0);
		missionDomain.setExp(missionData.has(EXP_KEY) ? missionData
				.getInt(EXP_KEY) : 0);
		missionDomain.setAddRate(missionData.has(INC_RATE_KEY) ? missionData
				.getInt(INC_RATE_KEY) : 0);
		missionDomain
				.setCondition_id(missionData.has(CONDITION_ID_KEY) ? missionData
						.getInt(CONDITION_ID_KEY) : 0);
		missionDomain.setDiamonNum(missionData.has(DIAMON_KEY) ? missionData.getInt(DIAMON_KEY) : 0);
		missionDomain.setRole_lev(missionData.has(ROLE_LEV_KEY) ? missionData.getInt(ROLE_LEV_KEY) : 0);
		return missionDomain;
	}

	/**
	 * 完成任务后响应字符串
	 * 
	 * @return
	 */
	public static String getMissionFinishResponse(Map<String, Object> map) {
		// 
		JSONObject missObj = new JSONObject();
		missObj.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);

		//-----------------------------获取本次任务奖励后角色经验变动
		Role role = (Role) map.get(MissionInfoService.ROLE_INFO_KEY);
		missObj.put("exp", role.getExp());
		missObj.put("up_exp", role.getUp_exp());
		missObj.put("lev", role.getLev());
		
		//-----------------------------获取本次任务奖励
		List<RoleProps> rewards = (List<RoleProps>) map.get(MissionInfoService.REWARD_KEY);
		JSONArray rewardJA = new JSONArray();

		// 遍历集合
		if (rewards != null && rewards.size() > 0) {
			for (RoleProps prop : rewards) {
				// 单个道具对象
				JSONObject propObj = new JSONObject();
				propObj.put("role_prop_id", prop.getRole_props_id());
				propObj.put("prop_id", prop.getProps_id());
				propObj.put("prop_name", prop.getProps_name());
				propObj.put("prop_icon", prop.getProps_icon());
				propObj.put("prop_count", prop.getProps_count());
				propObj.put("prop_type", prop.getProps_type());
				// 存入JSON数组
				rewardJA.add(propObj);
			}
		}
		missObj.put("get_rewards", rewardJA);
		
		//-----------------------------获取后续任务
		List<List<Mission>> nextList = (List<List<Mission>>) map.get(MissionInfoService.NEXT_MISSIONS_KEY);
		JSONArray newMissionAJ = new JSONArray();
		if (nextList.size() > 0) {
			newMissionAJ = conNextMissionJson(nextList);
		}

		missObj.put("new_mission", newMissionAJ);
		
		return missObj.toString();
	}

	/**
	 * 返回新任务
	 * @param missionList
	 * @return
	 */
	public static JSONArray getMissionPropListResponse(List<Mission> missionList){
		JSONArray arr = new JSONArray();
		if (missionList != null && missionList.size() > 0) {
			for (Mission mission : missionList) {
				arr.add(conMissionJsonObj(mission));
			}
		}
		return arr;
	}
	
	/**
	 * 后续任务列表
	 * @param nextMissionList
	 * @return
	 */
	private static JSONArray conNextMissionJson(List<List<Mission>> nextMissionList){
		JSONArray arr = new JSONArray();
		for (List<Mission> list : nextMissionList) {
			for (Mission nextMission : list) {
				arr.add(conMissionJsonObj(nextMission));
			}
		}
		
		return arr;
	}
	
	/**
	 * 解析Mission对象为JSON对象
	 * @param mission
	 * @return
	 */
	private static JSONObject conMissionJsonObj(Mission mission){
				JSONObject missionJ = new JSONObject();
				// 任务基本信息
				missionJ.put(MISSION_ID_KEY, mission.getMission_id());
				missionJ.put("mission_type", mission.getMission_type());
				missionJ.put("exp", mission.getExp());
				missionJ.put("mission_name", mission.getMission_name());
				missionJ.put("mission_desc", mission.getMission_desc());

				// 任务条件
				JSONArray conditionAJ = new JSONArray();
				List<MissionCondition> conditionList = mission
						.getMissionConditions();
				if (conditionList != null && conditionList.size() > 0) {
					for (MissionCondition condition : conditionList) {
						JSONObject condJ = new JSONObject();
						condJ.put("condition_id", condition.getCondition_id());
						condJ.put("condition_key",condition.getCondition_key());
						condJ.put("condition_type",condition.getCondition_type());
						condJ.put("condition_val",condition.getCondition_val());
						condJ.put("condition_desc",condition.getCondition_desc());
						condJ.put("curr", condition.getCurr());
						//condJ.put("need", mission.getNeed());
						conditionAJ.add(condJ);
					}
				}
				missionJ.put("conditions", conditionAJ);

				// 任务奖励
				JSONArray rewardAJ = new JSONArray();
				List<RoleProps> rewardList = mission.getTaskGifts();
				if (rewardList != null && rewardList.size() > 0) {
					for (RoleProps roleProps : rewardList) {
						JSONObject propJ = new JSONObject();
						propJ.put("prop_id", roleProps.getProps_id());
						propJ.put("prop_icon",
								roleProps.getProps_icon());
						propJ.put("prop_name",
								roleProps.getProps_name());
						propJ.put("prop_count",
								roleProps.getProps_count());
						rewardAJ.add(propJ);
					}
				}
				missionJ.put("props", rewardAJ);
				
				return missionJ;
	}
}
