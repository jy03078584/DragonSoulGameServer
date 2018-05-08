 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: WarTeamVo.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-28 下午5:06:23 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:战斗队列信息VO
 */
public class WarTeamVo {
	
	private Long tag_role_id;//目标(守方)角色ID
	
	
	private int from_x;//攻方部队出发城市X坐标
	private int form_y;//攻方部队出发城市Y坐标
	
	
	
	public Long getTag_role_id() {
		return tag_role_id;
	}
	public void setTag_role_id(Long tag_role_id) {
		this.tag_role_id = tag_role_id;
	}
	
	public int getFrom_x() {
		return from_x;
	}
	public void setFrom_x(int from_x) {
		this.from_x = from_x;
	}
	public int getForm_y() {
		return form_y;
	}
	public void setForm_y(int form_y) {
		this.form_y = form_y;
	}
	
	
	/**
	 * @param tag_role_id
	 * @param tag_role_name 
	 * @param from_x 
	 * @param form_y 
	 */
	public WarTeamVo(Long tag_role_id,int from_x, int form_y) {
		this.tag_role_id = tag_role_id;
		this.from_x = from_x;
		this.form_y = form_y;
	}
	public WarTeamVo() {
		super();
	}
	
	
	
	
}
