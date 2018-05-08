 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: CreepsDemon.java 
 * @Package com.lk.dragon.server.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-6 下午3:37:54 
 * @version V1.0   
 */
package com.lk.dragon.server.domain;

/**  
 * @Description:
 */
public class CreepsDemon {
	private Integer arm_id;//野怪种类ID
	private Integer creeps_count;//数量
	
	
	public CreepsDemon(){}


	
	
	public CreepsDemon(Integer arm_id, Integer creeps_count) {
		super();
		this.arm_id = arm_id;
		this.creeps_count = creeps_count;
	}




	public Integer getArm_id() {
		return arm_id;
	}


	public void setArm_id(Integer arm_id) {
		this.arm_id = arm_id;
	}


	public Integer getCreeps_count() {
		return creeps_count;
	}


	public void setCreeps_count(Integer creeps_count) {
		this.creeps_count = creeps_count;
	};
	
	
	
}
