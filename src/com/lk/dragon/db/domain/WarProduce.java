 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: WarProduce.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-6 上午10:44:57 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:战斗过程
 */
public class WarProduce {
	
	//----------------WAR_TEAM_TAB 战斗队列表
	
	private Long war_team_id;//	n	number(12)	y			id
	private Long city_id;//	n	number(12)	y			所属城邦id
	private Long role_id;//	n	number(12)	y			角色id
	private Long faction_id;//所属帮会ID
	private Integer status;//	n	number(1)	y			队列状态   1:出征中 2:返回中  3:覆灭
	private String end_time;//	n	varchar2(255)	y			该状态结束时间点
	private String tag_name;//目标名 城邦名/野怪名
	private Integer tag_x;//目标X坐标
	private Integer tag_y;//目标Y坐标
	private String tag_arms;//野怪兵力
	private Integer use_time;//单程消耗秒数
	private Integer war_type;//出征类型 
	private Long tag_city_id;//目标城邦ID
	
	private String last_end_time;//上次轮训最后队列结束时间
	
	//-------------team_hero_tab
	private List<RoleHero> heros;
	private Long team_id;//队列ID
	private long lastTime;//结束剩余秒
	private Long role_hero_id;//英雄ID
	private Integer herosCount;//英雄个数

	
	
	//-------------wild_src_tab
	// tag_x tag_y
	private String arm_info;//	n	varchar2(255)	y			占据该坐标的军事信息
	private Integer owner_type;//	n	number(1)	y			占据者类型  0:狩猎野怪 1:资源点野怪   2:玩家部队
	private Long owner_id;//	n	number(12)	y			占据者所属id  -10系统  
	private Integer src_type;//	n	number(1)	y			野外点类型  0:非资源点 1:食物资源点  2:木材资源点  3:石头资源点
	private Integer src_leve;//资源点等级  1：初级 2：高级
	
	
	//------------wild_hurt_tab
	//tag_x	n	number(12)	y			x坐标
	//tag_y	n	number(12)	y			y坐标
	private Integer hurt_type;//	n	number(1)	y			狩猎类型  1:隐形  2:可见
	private String creeps;//	n	varchar2(200)	y			野怪种类


	public WarProduce(){}

	
	
	public WarProduce(Long war_team_id, Long city_id, Long role_id,
			Integer status, String end_time, String tag_name, Integer tag_x,
			Integer tag_y, String tag_arms, Integer use_time, Integer war_type) {
		this.war_team_id = war_team_id;
		this.city_id = city_id;
		this.role_id = role_id;
		this.status = status;
		this.end_time = end_time;
		this.tag_name = tag_name;
		this.tag_x = tag_x;
		this.tag_y = tag_y;
		this.tag_arms = tag_arms;
		this.use_time = use_time;
		this.war_type = war_type;
	}



	public WarProduce(Long war_team_id, Integer status, String end_time) {
		super();
		this.war_team_id = war_team_id;
		this.status = status;
		this.end_time = end_time;
	}



	
	
	public Long getTag_city_id() {
		return tag_city_id;
	}



	public void setTag_city_id(Long tag_city_id) {
		this.tag_city_id = tag_city_id;
	}



	public long getLastTime() {
		return lastTime;
	}



	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}



	public String getLast_end_time() {
		return last_end_time;
	}



	public void setLast_end_time(String last_end_time) {
		this.last_end_time = last_end_time;
	}



	public List<RoleHero> getHeros() {
		return heros;
	}

	public void setHeros(List<RoleHero> heros) {
		this.heros = heros;
	}



	public Integer getSrc_leve() {
		return src_leve;
	}
	public void setSrc_leve(Integer src_leve) {
		this.src_leve = src_leve;
	}

	public Integer getHurt_type() {
		return hurt_type;
	}

	public void setHurt_type(Integer hurt_type) {
		this.hurt_type = hurt_type;
	}

	public String getCreeps() {
		return creeps;
	}







	public void setCreeps(String creeps) {
		this.creeps = creeps;
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







	public Integer getHerosCount() {
		return herosCount;
	}







	public void setHerosCount(Integer herosCount) {
		this.herosCount = herosCount;
	}







	public Long getTeam_id() {
		return team_id;
	}







	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}







	public Long getRole_hero_id() {
		return role_hero_id;
	}







	public void setRole_hero_id(Long role_hero_id) {
		this.role_hero_id = role_hero_id;
	}







	public Long getFaction_id() {
		return faction_id;
	}







	public void setFaction_id(Long faction_id) {
		this.faction_id = faction_id;
	}







	public Integer getWar_type() {
		return war_type;
	}




	public void setWar_type(Integer war_type) {
		this.war_type = war_type;
	}




	public Integer getUse_time() {
		return use_time;
	}




	public void setUse_time(Integer use_time) {
		this.use_time = use_time;
	}




	public Long getWar_team_id() {
		return war_team_id;
	}


	public void setWar_team_id(Long war_team_id) {
		this.war_team_id = war_team_id;
	}


	public Long getCity_id() {
		return city_id;
	}


	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}


	public Long getRole_id() {
		return role_id;
	}


	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getEnd_time() {
		return end_time;
	}


	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}


	public String getTag_name() {
		return tag_name;
	}


	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
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


	public String getTag_arms() {
		return tag_arms;
	}


	public void setTag_arms(String tag_arms) {
		this.tag_arms = tag_arms;
	}



	@Override
	public String toString() {
		return "WarProduce [war_team_id=" + war_team_id + ", city_id="
				+ city_id + ", role_id=" + role_id + ", status=" + status
				+ ", end_time=" + end_time + ", tag_x=" + tag_x + ", tag_y="
				+ tag_y + ", war_type=" + war_type + "]";
	};
	
	
	

}
