 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Ranks.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-13 下午3:36:10 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:Rank排行表映射
 */
public class Ranks {

	private Long rank_id;//排行表id
	private Integer rank_type;//排行类型
	private Integer rank_data;//数据
	private Long object_id;//类型对象id
	private Long rank_number;//排名序号
	
	private Long role_id;//角色ID
	private String role_name;//角色名称
	private String hero_name;//英雄名称
	private String faction_name;//帮派名称
	
	private String object_name;//对象名
	
	
	
	public Ranks() {
	}
	
	
	public Ranks(Integer rank_type, Long object_id) {
		super();
		this.rank_type = rank_type;
		this.object_id = object_id;
	}


	public String getObject_name() {
		return object_name;
	}


	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}


	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Long getRank_id() {
		return rank_id;
	}
	public void setRank_id(Long rank_id) {
		this.rank_id = rank_id;
	}
	public Integer getRank_type() {
		return rank_type;
	}
	public void setRank_type(Integer rank_type) {
		this.rank_type = rank_type;
	}
	public Integer getRank_data() {
		return rank_data;
	}
	public void setRank_data(Integer rank_data) {
		this.rank_data = rank_data;
	}
	public Long getObject_id() {
		return object_id;
	}
	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getHero_name() {
		return hero_name;
	}
	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}
	public String getFaction_name() {
		return faction_name;
	}
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	public Long getRank_number() {
		return rank_number;
	}
	public void setRank_number(Long rank_number) {
		this.rank_number = rank_number;
	}
	
	
	

}
