package com.lk.dragon.db.domain;

import java.util.List;

public class ReinForceArm {
	private Long toCityId;// 目标城邦ID	
	private String toCityName;//	目标城邦名
	private Long fromCityId;//	出发城邦ID
	private String fromCityName;	//出发城邦名
	private Long role_hero_id;//	英雄ID
	private String hero_name;//	英雄名
	private String sht_icon;//	头像
	private Integer command;//	统率值
	private List<ArmsDeploy> heroArms;//统帅部队信息
	public Long getToCityId() {
		return toCityId;
	}
	public void setToCityId(Long toCityId) {
		this.toCityId = toCityId;
	}
	public String getToCityName() {
		return toCityName;
	}
	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}
	public Long getFromCityId() {
		return fromCityId;
	}
	public void setFromCityId(Long fromCityId) {
		this.fromCityId = fromCityId;
	}
	public String getFromCityName() {
		return fromCityName;
	}
	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}
	public Long getRole_hero_id() {
		return role_hero_id;
	}
	public void setRole_hero_id(Long role_hero_id) {
		this.role_hero_id = role_hero_id;
	}
	public String getHero_name() {
		return hero_name;
	}
	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}
	public String getSht_icon() {
		return sht_icon;
	}
	public void setSht_icon(String sht_icon) {
		this.sht_icon = sht_icon;
	}
	public Integer getCommand() {
		return command;
	}
	public void setCommand(Integer command) {
		this.command = command;
	}
	public List<ArmsDeploy> getHeroArms() {
		return heroArms;
	}
	public void setHeroArms(List<ArmsDeploy> heroArms) {
		this.heroArms = heroArms;
	}
	
	
	
}
