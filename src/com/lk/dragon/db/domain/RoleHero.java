 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RoleHero.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-17 上午9:41:23 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:Role_HERO_TAB映射
 */
public class RoleHero {

	private Long role_hero_id;//角色-英雄关联id
	private Long role_id;//角色id
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
	private Integer hero_lev;//英雄等级
	private Integer hero_exp;//英雄当前经验
	private Integer hero_up_exp;//英雄所需升级经验
	private Integer is_free;//英雄当前是否空闲 1:是  0:否  3：死亡
	private Integer command;//英雄统帅值(最大统兵量)
	private Integer general_id;//英雄段位id
	private Integer rank_score;//英雄竞技场积分
	private Integer max_hp;//最大血量
	private Integer max_mp;//最大蓝量
	private Long city_id;//英雄所属城邦
	private String revive_time;//复活完成时间
	private Integer revLstSec;//复活剩余时间
	private Integer can_assign_point; //英雄可以分配的加点
	private Long now_cityid;//英雄当前所在城邦
	
	//系统当前时间
	private String now_db;
	
	private Integer hero_type;//英雄类型
	private Integer race;//英雄种族
	private String icon;//英雄图片
	private String sht_icon;//英雄小头像图标
	private Integer sex;//性别
	private String city_name;//城邦名称
	private Integer hero_arm_count;//英雄所带兵量
	
	
	//-------------------HERO_ARMS_TAB  英雄-军队表
	private List<ArmsDeploy> heroArmys; 
	
	private Integer physic_attack_show;//界面展示物理攻击
	private Integer physic_defence_show;//界面展示物理防御
	private Integer magic_attack_show;//界面展示魔法攻击
	private Integer magic_defence_show;//界面展示魔法防御
	private Integer hp_max_show;//界面展示生命值
	private Integer mp_max_show;//界面展示魔法值
	private Integer speed_show;//界面展示攻击速度
	private Integer distance_attack_show;//界面展示攻击距离
	private Integer distance_move_show;//界面展示移动距离
	
	//-----------------HERO_TRAIN_TAB 英雄训练室表
	private String begin_time;//训练开始时间
	private String end_time;//训练结束时间
	private Integer train_time;//训练时间
	private Integer already_train;//已训练时间(s)
	private Integer pre_exp;//单位时间获取经验数
	private Integer use_gold_dimaon;//消耗资源数
	private Integer train_exp;//已获得经验
	private Integer train_last_time;//剩余训练时间
	public RoleHero() {
	}



	
	public Long getNow_cityid() {
		return now_cityid;
	}




	public void setNow_cityid(Long now_cityid) {
		this.now_cityid = now_cityid;
	}




	public void setCan_assign_point(Integer can_assign_point) {
		this.can_assign_point = can_assign_point;
	}




	public Integer getRevLstSec() {

		return revLstSec;
	}


	public void setRevLstSec(Integer revLstSec) {
		this.revLstSec = revLstSec;
	}




	public Integer getCan_assign_point() {
		return can_assign_point;
	}





	public Integer getTrain_last_time() {
		return train_last_time;
	}



	public void setTrain_last_time(Integer train_last_time) {
		this.train_last_time = train_last_time;
	}



	public Integer getTrain_exp() {
		return train_exp;
	}



	public void setTrain_exp(Integer train_exp) {
		this.train_exp = train_exp;
	}



	public Integer getUse_gold_dimaon() {
		return use_gold_dimaon;
	}



	public void setUse_gold_dimaon(Integer use_gold_dimaon) {
		this.use_gold_dimaon = use_gold_dimaon;
	}



	public Integer getAlready_train() {
		return already_train;
	}



	public void setAlready_train(Integer already_train) {
		this.already_train = already_train;
	}



	public Integer getTrain_time() {
		return train_time;
	}



	public void setTrain_time(Integer train_time) {
		this.train_time = train_time;
	}



	public String getBegin_time() {
		return begin_time;
	}



	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}



	public String getEnd_time() {
		return end_time;
	}



	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}



	public Integer getPre_exp() {
		return pre_exp;
	}



	public void setPre_exp(Integer pre_exp) {
		this.pre_exp = pre_exp;
	}



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




	public List<ArmsDeploy> getHeroArmys()
    {
        return heroArmys;
    }



    public void setHeroArmys(List<ArmsDeploy> heroArmys)
    {
        this.heroArmys = heroArmys;
    }


    public String getRevive_time() {
		return revive_time;
	}



	public void setRevive_time(String revive_time) {
		this.revive_time = revive_time;
	}

	public Integer getHero_arm_count() {
		return hero_arm_count;
	}


	public void setHero_arm_count(Integer hero_arm_count) {
		this.hero_arm_count = hero_arm_count;
	}



	public String getCity_name() {
		return city_name;
	}








	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}



	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}


	public Integer getMax_hp() {
		return max_hp;
	}



	public void setMax_hp(Integer max_hp) {
		this.max_hp = max_hp;
	}



	public Integer getMax_mp() {
		return max_mp;
	}



	public void setMax_mp(Integer max_mp) {
		this.max_mp = max_mp;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public Integer getHero_id() {
		return hero_id;
	}



	public void setHero_id(Integer hero_id) {
		this.hero_id = hero_id;
	}



	public Integer getRace() {
		return race;
	}



	public void setRace(Integer race) {
		this.race = race;
	}



	public Integer getPhysic_attack_show() {
		return physic_attack_show;
	}



	public void setPhysic_attack_show(Integer physic_attack_show) {
		this.physic_attack_show = physic_attack_show;
	}



	public Integer getPhysic_defence_show() {
		return physic_defence_show;
	}



	public void setPhysic_defence_show(Integer physic_defence_show) {
		this.physic_defence_show = physic_defence_show;
	}



	public Integer getMagic_attack_show() {
		return magic_attack_show;
	}



	public void setMagic_attack_show(Integer magic_attack_show) {
		this.magic_attack_show = magic_attack_show;
	}



	public Integer getMagic_defence_show() {
		return magic_defence_show;
	}



	public void setMagic_defence_show(Integer magic_defence_show) {
		this.magic_defence_show = magic_defence_show;
	}




	public Integer getHp_max_show() {
		return hp_max_show;
	}


	public void setHp_max_show(Integer hp_max_show) {
		this.hp_max_show = hp_max_show;
	}


	public Integer getMp_max_show() {
		return mp_max_show;
	}


	public void setMp_max_show(Integer mp_max_show) {
		this.mp_max_show = mp_max_show;
	}


	public Integer getSpeed_show() {
		return speed_show;
	}



	public void setSpeed_show(Integer speed_show) {
		this.speed_show = speed_show;
	}



	public Integer getDistance_attack_show() {
		return distance_attack_show;
	}



	public void setDistance_attack_show(Integer distance_attack_show) {
		this.distance_attack_show = distance_attack_show;
	}



	public Integer getDistance_move_show() {
		return distance_move_show;
	}



	public void setDistance_move_show(Integer distance_move_show) {
		this.distance_move_show = distance_move_show;
	}





	public Integer getHero_type() {
		return hero_type;
	}



	public void setHero_type(Integer hero_type) {
		this.hero_type = hero_type;
	}



	public Integer getSpeed_inmap() {
		return speed_inmap;
	}



	public void setSpeed_inmap(Integer speed_inmap) {
		this.speed_inmap = speed_inmap;
	}



	public Long getRole_hero_id() {
		return role_hero_id;
	}


	public void setRole_hero_id(Long role_hero_id) {
		this.role_hero_id = role_hero_id;
	}


	public Long getRole_id() {
		return role_id;
	}


	public void setRole_id(Long role_id) {
		this.role_id = role_id;
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








	public Integer getHero_lev() {
		return hero_lev;
	}


	public void setHero_lev(Integer hero_lev) {
		this.hero_lev = hero_lev;
	}


	public Integer getHero_exp() {
		return hero_exp;
	}


	public void setHero_exp(Integer hero_exp) {
		this.hero_exp = hero_exp;
	}


	public Integer getHero_up_exp() {
		return hero_up_exp;
	}


	public void setHero_up_exp(Integer hero_up_exp) {
		this.hero_up_exp = hero_up_exp;
	}


	public Integer getIs_free() {
		return is_free;
	}


	public void setIs_free(Integer is_free) {
		this.is_free = is_free;
	}


	public Integer getCommand() {
		return command;
	}


	public void setCommand(Integer command) {
		this.command = command;
	}


	public Integer getGeneral_id() {
		return general_id;
	}


	public void setGeneral_id(Integer general_id) {
		this.general_id = general_id;
	}


	public Integer getRank_score() {
		return rank_score;
	}


	public void setRank_score(Integer rank_score) {
		this.rank_score = rank_score;
	}










    public String getNow_db()
    {
        return now_db;
    }










    public void setNow_db(String now_db)
    {
        this.now_db = now_db;
    }










 
	
	

}
