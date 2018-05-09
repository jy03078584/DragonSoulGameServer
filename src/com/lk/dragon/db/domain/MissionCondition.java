 /** 
 *
 * @Title: MissionCondition.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 上午11:19:17 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:任务条件
 */
public class MissionCondition {
	/*-----------Mission_Condition_Tab  任务完成条件表--------------------*/
	private Integer mission_id;//任务id
	private String condition_key;//条件键
	private Integer condition_val;//条件值 
	private String condition_desc;//条件描述
	private Integer condition_id;//	条件id
	private Integer condition_type;//任务类型
	private Integer curr;//
	public MissionCondition() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public Integer getCondition_type() {
		return condition_type;
	}



	public void setCondition_type(Integer condition_type) {
		this.condition_type = condition_type;
	}



	public Integer getCurr() {
		return curr;
	}



	public void setCurr(Integer curr) {
		this.curr = curr;
	}



	public Integer getMission_id() {
		return mission_id;
	}
	public void setMission_id(Integer mission_id) {
		this.mission_id = mission_id;
	}
	public String getCondition_key() {
		return condition_key;
	}
	public void setCondition_key(String condition_key) {
		this.condition_key = condition_key;
	}
	public Integer getCondition_val() {
		return condition_val;
	}
	public void setCondition_val(Integer condition_val) {
		this.condition_val = condition_val;
	}
	public String getCondition_desc() {
		return condition_desc;
	}
	public void setCondition_desc(String condition_desc) {
		this.condition_desc = condition_desc;
	}
	public Integer getCondition_id() {
		return condition_id;
	}
	public void setCondition_id(Integer condition_id) {
		this.condition_id = condition_id;
	}
	
	
}
