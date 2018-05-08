 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Build.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午3:05:13 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:建筑表映射
 */
public class Build {
	private Long bulid_id;//建筑物id
	private String bulid_name;//建筑名称
	private String icon;//图标
	private Integer max_lev;//最大等级
	private Integer race;//种族
	private Integer type;//建筑种类 0:市政大厅
	
	
	public Build(){}

	

	
	
	public Integer getType() {
		return type;
	}





	public void setType(Integer type) {
		this.type = type;
	}





	public Long getBulid_id() {
		return bulid_id;
	}

	public void setBulid_id(Long bulid_id) {
		this.bulid_id = bulid_id;
	}

	public String getBulid_name() {
		return bulid_name;
	}

	public void setBulid_name(String bulid_name) {
		this.bulid_name = bulid_name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getMax_lev() {
		return max_lev;
	}

	public void setMax_lev(Integer max_lev) {
		this.max_lev = max_lev;
	}

	public Integer getRace() {
		return race;
	}

	public void setRace(Integer race) {
		this.race = race;
	}
	
	

}
