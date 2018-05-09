 /** 
 *
 * @Title: Mission.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 上午10:51:40 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:系统任务模块
 */
public class Mission {
	/*-----------Mission_Tab 任务主表--------------------*/
	private Integer mission_id;//任务id
	private Integer head;//前置任务，若无则置为-1
	private Integer mission_type;//		基本类型，0-新手引导任务；1-支线任务；2-日常任务
	private String mission_name;//			任务名称
	private String mission_desc;//			任务描述
	private Integer minlev;//领取需要的最低等级
	private Integer maxlev;//领取限制的最高等级
	private Integer exp;//经验奖励
	
	
	
	/*-----------Role_Mession_Tab  角色任务进度表--------------------*/
	private Long role_id;//	角色id
	//mission_id	//n	number(12)	n			任务id
	private Integer curr;//	当前进度
	private Integer need;//需要进度
	private Integer condition_id;//任务条件id
	
	private int incCount;//进度增长量 
	//任务奖励信息
	private List<RoleProps> taskGifts;
	//任务完成条件                                               missionConditions
	private List<MissionCondition> missionConditions;

	public Mission() {
		super();
	}
	
	
	public Mission(Integer mission_id, Long role_id) {
		super();
		this.mission_id = mission_id;
		this.role_id = role_id;
	}


	
	public Integer getMission_type() {
		return mission_type;
	}


	public void setMission_type(Integer mission_type) {
		this.mission_type = mission_type;
	}


	public int getIncCount() {
		return incCount;
	}


	public void setIncCount(int incCount) {
		this.incCount = incCount;
	}


	public Integer getCondition_id() {
		return condition_id;
	}


	public void setCondition_id(Integer condition_id) {
		this.condition_id = condition_id;
	}


	public List<MissionCondition> getMissionConditions() {
		return missionConditions;
	}


	public void setMissionConditions(List<MissionCondition> missionConditions) {
		this.missionConditions = missionConditions;
	}


	public List<RoleProps> getTaskGifts() {
		return taskGifts;
	}


	public void setTaskGifts(List<RoleProps> taskGifts) {
		this.taskGifts = taskGifts;
	}

	public Long getRole_id() {
		return role_id;
	}


	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}


	public Integer getCurr() {
		return curr;
	}


	public void setCurr(Integer curr) {
		this.curr = curr;
	}


	public Integer getNeed() {
		return need;
	}


	public void setNeed(Integer need) {
		this.need = need;
	}


	public Integer getMission_id() {
		return mission_id;
	}
	public void setMission_id(Integer mission_id) {
		this.mission_id = mission_id;
	}
	public Integer getHead() {
		return head;
	}
	public void setHead(Integer head) {
		this.head = head;
	}

	public String getMission_name() {
		return mission_name;
	}
	public void setMission_name(String mission_name) {
		this.mission_name = mission_name;
	}
	public String getMission_desc() {
		return mission_desc;
	}
	public void setMission_desc(String mission_desc) {
		this.mission_desc = mission_desc;
	}
	public Integer getMinlev() {
		return minlev;
	}
	public void setMinlev(Integer minlev) {
		this.minlev = minlev;
	}
	public Integer getMaxlev() {
		return maxlev;
	}
	public void setMaxlev(Integer maxlev) {
		this.maxlev = maxlev;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}

	
	
}
