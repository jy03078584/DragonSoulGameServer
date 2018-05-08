 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Role.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-4 上午9:41:50 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:游戏角色映射
 */
public class Role {
	private Long role_id;
	private Long user_id;	
	private Integer race;	
	private String role_name;
	private Integer duke;
	private Integer lev;
	private Integer exp;
	private Integer up_exp;
	private Integer sex;
	private String icon;
	private String sht_ico;
	private Integer gold;
	private Integer food;
	private Integer wood;
	private Integer stone;
	private Integer yield_food;
	private Integer yield_wood;
	private Integer yield_stone;
	private Integer diamon;//拥有点卷数量
	private Integer city;//拥有城邦数量
	private Integer is_online;//角色是否在线
	private Integer eat;//人口
	private Integer bags;//背包数量
	private Integer battle_count;//当日剩余征战次数
	
	private Integer sum_login_count;//累积登陆次数
	private String  last_login_time;//上次登陆时间
	private String  last_logout_time;//上次下线时间
	
	//private String now_db;//数据库当前时间
	
	private Long factionid;//角色帮会ID
	private int isExit;//角色名是否存在
	
	private String chests;
	
	private List<Buff> buffs;//buff信息
	private int buff_type;
	private List<City> citys;//城邦里欸包
	private Faction faction;
	/** 关系类型（用于好友模块） **/
	private int relation_type;
	
	public Role(){}

	


	public Faction getFaction() {
		return faction;
	}




	public void setFaction(Faction faction) {
		this.faction = faction;
	}




	public List<City> getCitys() {
		return citys;
	}




	public void setCitys(List<City> citys) {
		this.citys = citys;
	}




	public List<Buff> getBuffs() {
		return buffs;
	}




	public int getBuff_type() {
		return buff_type;
	}




	public void setBuff_type(int buff_type) {
		this.buff_type = buff_type;
	}




	public void setBuffs(List<Buff> buffs) {
		this.buffs = buffs;
	}




	public Long getFactionid() {
		return factionid;
	}

	public void setFactionid(Long factionid) {
		this.factionid = factionid;
	}



	public Integer getBattle_count() {
		return battle_count;
	}



	public void setBattle_count(Integer battle_count) {
		this.battle_count = battle_count;
	}



	public Role(Long user_id, String role_name,Integer race, Integer sex, String icon,String sht_ico) {
		this.user_id = user_id;
		this.role_name = role_name;
		this.race = race;
		this.sex = sex;
		this.icon = icon;
		this.sht_ico = sht_ico;
	}

	
	
	
	 
	public Integer getSum_login_count() {
		return sum_login_count;
	}



	public void setSum_login_count(Integer sum_login_count) {
		this.sum_login_count = sum_login_count;
	}



	public String getLast_login_time() {
		return last_login_time;
	}



	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}



	public String getLast_logout_time() {
		return last_logout_time;
	}



	public void setLast_logout_time(String last_logout_time) {
		this.last_logout_time = last_logout_time;
	}



	public Integer getBags() {
		return bags;
	}



	public void setBags(Integer bags) {
		this.bags = bags;
	}



	public Integer getEat() {
		return eat;
	}



	public void setEat(Integer eat) {
		this.eat = eat;
	}



	public Integer getIs_online() {
		return is_online;
	}



	public void setIs_online(Integer is_online) {
		this.is_online = is_online;
	}



	public String getSht_ico() {
		return sht_ico;
	}



	public void setSht_ico(String sht_ico) {
		this.sht_ico = sht_ico;
	}


	
	public Integer getDiamon() {
		return diamon;
	}



	public void setDiamon(Integer diamon) {
		this.diamon = diamon;
	}



	public Integer getCity() {
		return city;
	}



	public void setCity(Integer city) {
		this.city = city;
	}



	public int getIsExit(){
		return isExit;
	}
	public void setIsExit(int isExit){
		this.isExit = isExit;
	}
	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Integer getRace() {
		return race;
	}

	public void setRace(Integer race) {
		this.race = race;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public Integer getDuke() {
		return duke;
	}

	public void setDuke(Integer duke) {
		this.duke = duke;
	}

	

	public Integer getLev() {
		return lev;
	}



	public void setLev(Integer lev) {
		this.lev = lev;
	}



	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getUp_exp() {
		return up_exp;
	}

	public void setUp_exp(Integer up_exp) {
		this.up_exp = up_exp;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getFood() {
		return food;
	}

	public void setFood(Integer food) {
		this.food = food;
	}

	public Integer getWood() {
		return wood;
	}

	public void setWood(Integer wood) {
		this.wood = wood;
	}

	public Integer getStone() {
		return stone;
	}

	public void setStone(Integer stone) {
		this.stone = stone;
	}

	public Integer getYield_food() {
		return yield_food;
	}

	public void setYield_food(Integer yield_food) {
		this.yield_food = yield_food;
	}

	public Integer getYield_wood() {
		return yield_wood;
	}

	public void setYield_wood(Integer yield_wood) {
		this.yield_wood = yield_wood;
	}

	public Integer getYield_stone() {
		return yield_stone;
	}

	public void setYield_stone(Integer yield_stone) {
		this.yield_stone = yield_stone;
	}



    public int getRelation_type()
    {
        return relation_type;
    }



    public void setRelation_type(int relation_type)
    {
        this.relation_type = relation_type;
    }





    public String getChests()
    {
        return chests;
    }





    public void setChests(String chests)
    {
        this.chests = chests;
    }

}
