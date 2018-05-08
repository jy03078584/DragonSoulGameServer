 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Equip.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-16 上午9:39:12 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:EQUIP_TAB表映射
 */
public class Equip {


	private Integer props_id;//道具id
	private Integer equip_location;//装备部位
	private String inc_property;//装备属性
	private Integer quality;//装备品质
	private Integer command_lev	;//最低要求等级
	private String props_name;//	n	varchar2(255)	y			装备名称
	private String props_comment;//	n	varchar2(500)	y			装备描述
	private String props_icon;//	n	varchar2(255)	y			装备图标

	
	public Equip() {}




	
	
	public String getInc_property() {
		return inc_property;
	}






	public void setInc_property(String inc_property) {
		this.inc_property = inc_property;
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






	public Integer getProps_id() {
		return props_id;
	}


	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
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
	
	

}
