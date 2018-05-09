 /** 
 *
 * @Title: HeroEquip.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-17 上午9:48:51 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:
 */
public class HeroEquip {
	
	private Long rela_id;//关联ID
	private Long role_hero_id;//角色英雄ID
	private Long role_props_id;//装备所在包裹ID
	
	
	public HeroEquip() {
	}

	





	public HeroEquip(Long role_hero_id, Long role_props_id) {
		super();
		this.role_hero_id = role_hero_id;
		this.role_props_id = role_props_id;
	}





	public Long getRela_id() {
		return rela_id;
	}

	public void setRela_id(Long rela_id) {
		this.rela_id = rela_id;
	}

	public Long getRole_hero_id() {
		return role_hero_id;
	}

	public void setRole_hero_id(Long role_hero_id) {
		this.role_hero_id = role_hero_id;
	}

	public Long getRole_props_id() {
		return role_props_id;
	}

	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}

}
