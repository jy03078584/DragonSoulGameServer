/**
 * Copyright ? 2015，成都乐控
 * All Rights Reserved.
 * 文件名称： InviteReward.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:27:30
 */
package com.lk.dragon.db.domain;

import java.util.List;

public class InviteReward
{
	//邀请任务ID
	private Integer info_id;
   //邀请任务介绍
	private String  info_desc;
	//奖励集合
	private List<RoleProps> propList;
	public InviteReward() {
		super();
	}
	
	
	public Integer getInfo_id() {
		return info_id;
	}


	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}


	public String getInfo_desc() {
		return info_desc;
	}


	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}


	public List<RoleProps> getPropList() {
		return propList;
	}


	public void setPropList(List<RoleProps> propList) {
		this.propList = propList;
	}



	
	
}
