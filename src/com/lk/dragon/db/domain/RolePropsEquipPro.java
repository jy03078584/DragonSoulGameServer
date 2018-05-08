 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RolePropsEquipPro.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-17 上午11:34:27 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:ROLE_PROPS_EQUPROPETY_TAB映射 保存装备当前属性(含已嵌套宝石)
 */
public class RolePropsEquipPro {

	private Long role_props_id;	//n	number(12)	y			角色-道具id
	private String inc_property;//	n	varchar2(255)	y			装备当前属性效果(含宝石) json字符串
	
	private Integer equip_location;//装备部位
	private Integer quality;//装备品质
	private Integer command_lev	;//最低要求等级
	private String props_name;//	n	varchar2(255)	y			装备名称
	private String props_comment;//	n	varchar2(500)	y			装备描述
	private String props_icon;//	n	varchar2(255)	y			装备图标
	private String gems;//装备里嵌套宝石信息
	public RolePropsEquipPro() {
	}
	
	
	public String getGems() {
		return gems;
	}


	public void setGems(String gems) {
		this.gems = gems;
	}


	public Integer getEquip_location() {
		return equip_location;
	}


	public void setEquip_location(Integer equip_location) {
		this.equip_location = equip_location;
	}


	public Integer getQuality() {
		return quality;
	}


	public void setQuality(Integer quality) {
		this.quality = quality;
	}


	public Integer getCommand_lev() {
		return command_lev;
	}


	public void setCommand_lev(Integer command_lev) {
		this.command_lev = command_lev;
	}


	public String getProps_name() {
		return props_name;
	}


	public void setProps_name(String props_name) {
		this.props_name = props_name;
	}


	public String getProps_comment() {
		return props_comment;
	}


	public void setProps_comment(String props_comment) {
		this.props_comment = props_comment;
	}


	public String getProps_icon() {
		return props_icon;
	}


	public void setProps_icon(String props_icon) {
		this.props_icon = props_icon;
	}


	public Long getRole_props_id() {
		return role_props_id;
	}
	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}


	public String getInc_property() {
		return inc_property;
	}


	public void setInc_property(String inc_property) {
		this.inc_property = inc_property;
	}

	
}
