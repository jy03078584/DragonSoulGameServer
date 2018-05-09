 /** 
 *
 * @Title: RoleInvite.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-25 上午11:58:08 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:
 */
public class RoleInvite {
	private Long role_id;//角色ID
	private Integer invite_info_id;//任务ID
	private Integer is_get;//是否已领取
	
	
	
	
	public RoleInvite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RoleInvite(Long role_id, Integer invite_info_id) {
		super();
		this.role_id = role_id;
		this.invite_info_id = invite_info_id;
	}

	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Integer getInvite_info_id() {
		return invite_info_id;
	}
	public void setInvite_info_id(Integer invite_info_id) {
		this.invite_info_id = invite_info_id;
	}
	public Integer getIs_get() {
		return is_get;
	}
	public void setIs_get(Integer is_get) {
		this.is_get = is_get;
	}
	
	
}
