/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: BattleItem.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-7 上午10:20:27 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

public class WildSrc {

	//该domain执行操作在world_mapping.xml
	
	
	private String role_name;
	private Integer tag_x;//	
	private Integer tag_y;//	
	private String arm_info;//	
	private Integer owner_type;//	
	private Long owner_id;//	
	private Integer src_type;//
	private Integer src_leve;//	
	
	
	
	
	public WildSrc() {
		super();
	}
	public WildSrc(Integer tag_x, Integer tag_y, String arm_info,
			Integer owner_type, Long owner_id) {
		super();
		this.tag_x = tag_x;
		this.tag_y = tag_y;
		this.arm_info = arm_info;
		this.owner_type = owner_type;
		this.owner_id = owner_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Integer getTag_x() {
		return tag_x;
	}
	public void setTag_x(Integer tag_x) {
		this.tag_x = tag_x;
	}
	public Integer getTag_y() {
		return tag_y;
	}
	public void setTag_y(Integer tag_y) {
		this.tag_y = tag_y;
	}
	public String getArm_info() {
		return arm_info;
	}
	public void setArm_info(String arm_info) {
		this.arm_info = arm_info;
	}
	public Integer getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(Integer owner_type) {
		this.owner_type = owner_type;
	}
	public Long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}
	public Integer getSrc_type() {
		return src_type;
	}
	public void setSrc_type(Integer src_type) {
		this.src_type = src_type;
	}
	public Integer getSrc_leve() {
		return src_leve;
	}
	public void setSrc_leve(Integer src_leve) {
		this.src_leve = src_leve;
	}

	
	
}
