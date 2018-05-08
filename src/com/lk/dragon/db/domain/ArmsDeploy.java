 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: ArmsDeploy.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-31 上午9:29:12 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:军队整备
 */
public class ArmsDeploy {


	//-------------------CONSCRIPT_WORK_TAB  征兵队列表
	private Long conscript_work_id;//关联id
	private Long city_id;//	城邦id
	private Integer arms_id;//兵种id
	private String end_time;//征兵结束时间
	private Integer extra_seconds;//额外秒数
	private Integer last_seconds;//完成征兵剩余秒数
	private Integer eat;//人中增涨量
	private Long city_build_id;//兵营ID

	//-------------------ARMS_TAB 军队总览表
	private Integer  arm_id ;//兵种id
	private Integer race;//种族
	private Integer grade;// 兵种阶级
	private String arm_name;//兵种名称
	private String arm_icon;//兵种图标
	private Integer hp;//生命值
	private Integer physic_attack;//物理攻击
	private Integer physic_defence;//物理防御
	private Integer magic_attack;//魔法攻击
	private Integer magic_defence;//魔法防御
	private Integer speed;//速度
	private Integer distance_attack;//攻击距离
	private Integer hire_privce;//招募价格
	private Integer hire_time;//招募时间(秒:s)
	private Integer exp;//击杀经验值 
	private Integer hire_build;//招募建筑类型
	private String arm_desc;//简介
	private Integer use_command;//消耗统帅值
	private Integer fc;//战斗力数值
	
	//-------------------CITY_ARMS_TAB 城市-军队表
	//private Long city_id;// n number(12)  y     城邦id
	//private Integer arm_id;//  n number(2) y     兵种id
	private Integer arm_count;// n number(5) y     兵量
	
	//英雄军队管理表
	private Long role_hero_id;  //角色英雄id
	private String hero_name;//英雄名
	private String hero_icon;//英雄头像
	private Long hero_arms_id;  //英雄角色关联id
	private Integer seq_number;     //序列号
	private Integer count;


	
	
	private Integer hero_arm_count;//英雄所带兵量

	
	
	private Integer isEnd;//重连后剩余招募时间
	private Integer offLineHiredCount;//下线期间招募士兵数
	private Integer offLineAddEat;//下线期间增长人口数
	private Integer creeps_count;//野怪数量
	
	public ArmsDeploy(){}
	
	
	
	
	
	public ArmsDeploy(int offLineHiredCount, int extra_seconds, Integer isEnd,Integer hire_time,Long city_id,Integer arms_id,Long city_build_id,Integer offLineAddEat) {
		this.offLineHiredCount = offLineHiredCount;
		this.extra_seconds = extra_seconds;
		this.isEnd = isEnd;
		this.hire_time = hire_time;
		this.city_id = city_id;
		this.arms_id = arms_id;
		this.city_build_id = city_build_id;
		this.offLineAddEat = offLineAddEat;
	}


	
	
	public String getHero_name() {
		return hero_name;
	}





	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}





	public String getHero_icon() {
		return hero_icon;
	}





	public void setHero_icon(String hero_icon) {
		this.hero_icon = hero_icon;
	}





	public Integer getFc() {
		return fc;
	}





	public void setFc(Integer fc) {
		this.fc = fc;
	}





	public Integer getUse_command() {
		return use_command;
	}





	public void setUse_command(Integer use_command) {
		this.use_command = use_command;
	}





	public String getArm_desc() {
		return arm_desc;
	}





	public void setArm_desc(String arm_desc) {
		this.arm_desc = arm_desc;
	}





	public Integer getHire_build() {
		return hire_build;
	}





	public void setHire_build(Integer hire_build) {
		this.hire_build = hire_build;
	}





	public Integer getExp() {
		return exp;
	}





	public void setExp(Integer exp) {
		this.exp = exp;
	}





	public Integer getCreeps_count() {
		return creeps_count;
	}





	public Long getRole_hero_id()
    {
        return role_hero_id;
    }





    public void setRole_hero_id(Long role_hero_id)
    {
        this.role_hero_id = role_hero_id;
    }





    public void setCreeps_count(Integer creeps_count) {
		this.creeps_count = creeps_count;
	}





	public Integer getOffLineAddEat() {
		return offLineAddEat;
	}





	public void setOffLineAddEat(Integer offLineAddEat) {
		this.offLineAddEat = offLineAddEat;
	}





	public Long getCity_build_id() {
		return city_build_id;
	}





	public void setCity_build_id(Long city_build_id) {
		this.city_build_id = city_build_id;
	}





	public Integer getEat() {
		return eat;
	}





	public void setEat(Integer eat) {
		this.eat = eat;
	}





	public Integer getOffLineHiredCount() {
		return offLineHiredCount;
	}





	public void setOffLineHiredCount(Integer offLineHiredCount) {
		this.offLineHiredCount = offLineHiredCount;
	}





	public Integer getIsEnd() {
		return isEnd;
	}





	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}





	public Integer getLast_seconds() {
		return last_seconds;
	}





	public void setLast_seconds(Integer last_seconds) {
		this.last_seconds = last_seconds;
	}





	public Long getConscript_work_id() {
		return conscript_work_id;
	}

	public void setConscript_work_id(Long conscript_work_id) {
		this.conscript_work_id = conscript_work_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Integer getArms_id() {
		return arms_id;
	}

	public void setArms_id(Integer arms_id) {
		this.arms_id = arms_id;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getExtra_seconds() {
		return extra_seconds;
	}

	public void setExtra_seconds(Integer extra_seconds) {
		this.extra_seconds = extra_seconds;
	}

	public Integer getArm_id() {
		return arm_id;
	}

	public void setArm_id(Integer arm_id) {
		this.arm_id = arm_id;
	}

	public Integer getRace() {
		return race;
	}

	public void setRace(Integer race) {
		this.race = race;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getArm_name() {
		return arm_name;
	}

	public void setArm_name(String arm_name) {
		this.arm_name = arm_name;
	}

	public String getArm_icon() {
		return arm_icon;
	}

	public void setArm_icon(String arm_icon) {
		this.arm_icon = arm_icon;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
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

	public Integer getHire_privce() {
		return hire_privce;
	}

	public void setHire_privce(Integer hire_privce) {
		this.hire_privce = hire_privce;
	}

	public Integer getHire_time() {
		return hire_time;
	}

	public void setHire_time(Integer hire_time) {
		this.hire_time = hire_time;
	}

	public Integer getArm_count() {
		return arm_count;
	}

	public void setArm_count(Integer arm_count) {
		this.arm_count = arm_count;
	}


	public Integer getHero_arm_count() {
		return hero_arm_count;
	}

	public void setHero_arm_count(Integer hero_arm_count) {
		this.hero_arm_count = hero_arm_count;
	}








    public Long getHero_arms_id()
    {
        return hero_arms_id;
    }





    public void setHero_arms_id(Long hero_arms_id)
    {
        this.hero_arms_id = hero_arms_id;
    }





    public Integer getCount()
    {
        return count;
    }





    public void setCount(Integer count)
    {
        this.count = count;
    }





    public Integer getSeq_number()
    {
        return seq_number;
    }





    public void setSeq_number(Integer seq_number)
    {
        this.seq_number = seq_number;
    }

	
}
