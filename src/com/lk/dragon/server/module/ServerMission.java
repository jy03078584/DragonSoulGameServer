 /** 
 *
 * @Title: ServerMission.java 
 * @Package com.lk.dragon.server.module 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 下午1:35:22 
 * @version V1.0   
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Mission;
import com.lk.dragon.server.domain.MissionDomain;
import com.lk.dragon.server.module.analysis.MissionRequestAnalysis;
import com.lk.dragon.server.module.analysis.PropRequestAnalysis;
import com.lk.dragon.service.MissionInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

/**  
 * @Description:
 */
public class ServerMission {
	
	private MissionDomain missionDomain;
	
	private MissionInfoService missionInfoService;

	public ServerMission() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ServerMission(MissionDomain missionDomain){
		this.missionDomain = missionDomain;
		this.missionInfoService = SpringBeanUtil.getBean(MissionInfoService.class);
	}
	
	public void missionAnalysis(){
		switch (missionDomain.getType()) {
		case Constants.MISSION_INFO_SHOW:
			//获取任务信息
			getMissionDetailByRoleId();
			break;
		case Constants.MISSION_GET_NEW:
			//接取新任务
			getNewMission();
			break;
		case Constants.MISSION_FINISH:
			//任务完成
			missionFinish();
			break;
		case Constants.MISSION_REFRESH:
			//任务刷新:重置日常任务
			missionRefresh();
			break;
		case Constants.MISSION_LOSE:
			//放弃任务
			loseMission();
			break;
		case Constants.MISSION_ADDRATE:
			//更新任务进度
			missionAddRate();
			break;
		default:
			break;
		}
	}

	private void missionAddRate() {
		//构造Mission对象
		Mission mission = new Mission();
		mission.setRole_id(missionDomain.getRole_id());
		mission.setIncCount(missionDomain.getAddRate());
		mission.setCondition_id(missionDomain.getCondition_id());
		mission.setMission_id(missionDomain.getMisssion_id());
		boolean flag = false;
		//更新任务进度
		try {
			missionInfoService.updateRoleMission(mission);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回响应 客户端
		SocketUtil.responseClient(missionDomain.getCtx(), JSONUtil.getBooleanResponse(flag));
	}

	private void loseMission() {
		Mission mission = new Mission();
		mission.setRole_id(missionDomain.getRole_id());
		mission.setMission_id(missionDomain.getMisssion_id());
		boolean flag = false;
		try {
			missionInfoService.deleteRoleMission(mission);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回响应 客户端
		SocketUtil.responseClient(missionDomain.getCtx(), JSONUtil.getBooleanResponse(flag));
	}

	private void missionRefresh() {
		List<Mission>  newDayTask = null;
		JSONObject missObj = new JSONObject();
		try {
			newDayTask = missionInfoService.resetDayTask(missionDomain.getRole_id(), missionDomain.getMisssion_id(), missionDomain.getDiamonNum(),missionDomain.getRole_lev());
			missObj.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			missObj.put("new_mission", MissionRequestAnalysis.getMissionPropListResponse(newDayTask));
		} catch (Exception e) {
			missObj.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
			missObj.put("reason", Constants.NET_ERROR);
			e.printStackTrace();
		}
		SocketUtil.responseClient(missionDomain.getCtx(), missObj.toString());
	}

	private void missionFinish() {
		Mission mission = new Mission();
		mission.setRole_id(missionDomain.getRole_id());
		mission.setMission_id(missionDomain.getMisssion_id());
		mission.setExp(missionDomain.getExp());
		Map<String,Object> resMap = null;
		String res = "";
		try {
			resMap = missionInfoService.roleMissionFinish(mission);
			res = MissionRequestAnalysis.getMissionFinishResponse(resMap);
		} catch (Exception e) {
			JSONObject errJ = new JSONObject();
			if(e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)){
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
			}else{
				errJ.put(Constants.RESULT_KEY, Constants.NET_ERROR);
			}
			res = errJ.toString();
			e.printStackTrace();
		}
		System.out.println(res);
		SocketUtil.responseClient(missionDomain.getCtx(), res);
	}

	private void getNewMission() {
		// TODO Auto-generated method stub
		
	}

	private void getMissionDetailByRoleId() {
		Mission mission = new Mission();
		long roleId = missionDomain.getRole_id();
		int missionId = missionDomain.getMisssion_id();
		if(roleId > 0)
			mission.setRole_id(roleId);
		if(missionId > 0 )
			mission.setMission_id(missionId);
		JSONObject missObj = new JSONObject();
		try {
			List<Mission> missionList = missionInfoService.getMissionDetailByRoleId(mission);
			System.out.println("MISSION MAP after");
			missObj.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			missObj.put("mission", MissionRequestAnalysis.getMissionPropListResponse(missionList));
		} catch (Exception e) {
			missObj.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
			missObj.put("reason", Constants.NET_ERROR);
			e.printStackTrace();
		}
		SocketUtil.responseClient(missionDomain.getCtx(), missObj.toString());
	}
}
