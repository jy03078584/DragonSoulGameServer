 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Hero.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-20 下午3:47:04 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:英雄表
 */
public class Hero {

	
	private Integer hero_id;//英雄原始ID
	private Integer physique;//英雄体质
	private Integer mentality;//英雄智力
	private Integer hero_power;//英雄力量
	private Integer endurance;//英雄耐力
	private Integer agility;//英雄敏捷
	private Integer physic_attack;//物理攻击
	private Integer physic_defence;//物理防御
	private Integer magic_attack;//魔法攻击
	private Integer magic_defence;//魔法防御
	private Integer hp;//生命值
	private Integer mp;//魔法值
	private Integer speed;//战斗速度
	private Integer distance_attack;//攻击距离
	private Integer distance_move;//移动距离
	private Integer quality;//英雄当前品质  1:白  2:蓝 3:紫  4:橙  5...
	private String hero_name;//英雄名
	private Integer speed_inmap;//影响世界地图中移动速度(坐骑)
	private Integer hero_type;//英雄类型
	private Integer race;//英雄种族
	private String icon;//英雄头像
	private String sht_icon;//英雄小头像
	private Integer hire_gold;//招募消耗金币
	private Integer sex;//性别
	private Long city_id;//城市ID
	private Integer is_hired;//是否已招募
	
	
	public Hero(){}

	
	
	public String getSht_icon() {
		return sht_icon;
	}



	public void setSht_icon(String sht_icon) {
		this.sht_icon = sht_icon;
	}



	public Integer getSex() {
		return sex;
	}



	public void setSex(Integer sex) {
		this.sex = sex;
	}



	public Long getCity_id() {
		return city_id;
	}



	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}



	public Integer getIs_hired() {
		return is_hired;
	}



	public void setIs_hired(Integer is_hired) {
		this.is_hired = is_hired;
	}



	public Integer getHire_gold() {
		return hire_gold;
	}



	public void setHire_gold(Integer hire_gold) {
		this.hire_gold = hire_gold;
	}



	public Integer getHero_id() {
		return hero_id;
	}

	public void setHero_id(Integer hero_id) {
		this.hero_id = hero_id;
	}

	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPhysique() {
		return physique;
	}

	public void setPhysique(Integer physique) {
		this.physique = physique;
	}

	public Integer getMentality() {
		return mentality;
	}

	public void setMentality(Integer mentality) {
		this.mentality = mentality;
	}

	public Integer getHero_power() {
		return hero_power;
	}

	public void setHero_power(Integer hero_power) {
		this.hero_power = hero_power;
	}

	public Integer getEndurance() {
		return endurance;
	}

	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}

	public Integer getAgility() {
		return agility;
	}

	public void setAgility(Integer agility) {
		this.agility = agility;
	}

	public Integer getPhysic_attack() {
		return physic_attack;
	}

	public void setPhysic_attack(Integer physic_attack) {
		this.physic_attack = physic_attack;
	}

	public Integer getPhysic_defence() {
		return physic_defence;
	}

	public void setPhysic_defence(Integer physic_defence) {
		this.physic_defence = physic_defence;
	}

	public Integer getMagic_attack() {
		return magic_attack;
	}

	public void setMagic_attack(Integer magic_attack) {
		this.magic_attack = magic_attack;
	}

	public Integer getMagic_defence() {
		return magic_defence;
	}

	public void setMagic_defence(Integer magic_defence) {
		this.magic_defence = magic_defence;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getMp() {
		return mp;
	}

	public void setMp(Integer mp) {
		this.mp = mp;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getDistance_attack() {
		return distance_attack;
	}

	public void setDistance_attack(Integer distance_attack) {
		this.distance_attack = distance_attack;
	}

	public Integer getDistance_move() {
		return distance_move;
	}

	public void setDistance_move(Integer distance_move) {
		this.distance_move = distance_move;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}


	public String getHero_name() {
		return hero_name;
	}



	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}



	public Integer getSpeed_inmap() {
		return speed_inmap;
	}

	public void setSpeed_inmap(Integer speed_inmap) {
		this.speed_inmap = speed_inmap;
	}

	public Integer getHero_type() {
		return hero_type;
	}

	public void setHero_type(Integer hero_type) {
		this.hero_type = hero_type;
	}

	public Integer getRace() {
		return race;
	}

	public void setRace(Integer race) {
		this.race = race;
	}
	
	
	
}
