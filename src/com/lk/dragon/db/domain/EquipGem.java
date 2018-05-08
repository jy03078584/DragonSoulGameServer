 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: EquipGem.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-19 下午9:49:15 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:装备-宝石表映射
 */
public class EquipGem {

	private Long rela_id;//关联ID
	private Long role_props_id;//装备所在包裹ID
	private Integer props_id;//宝石道具ID
	
	public EquipGem() {}

	
	
	public EquipGem(Long role_props_id, Integer props_id) {
		super();
		this.role_props_id = role_props_id;
		this.props_id = props_id;
	}



	public Long getRela_id() {
		return rela_id;
	}

	public void setRela_id(Long rela_id) {
		this.rela_id = rela_id;
	}

	public Long getRole_props_id() {
		return role_props_id;
	}

	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}

	public Integer getProps_id() {
		return props_id;
	}

	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}
	
	
	
	
	
}
