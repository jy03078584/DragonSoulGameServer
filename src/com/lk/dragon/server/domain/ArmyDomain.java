/**
 *
 *
 * 文件名称： ArmyDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-31 上午9:31:52
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;


import com.lk.dragon.db.domain.ArmsDeploy;

public class ArmyDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long role_id;
    /** 兵种id **/
    private int soldierId;
    /** 英雄-角色关联id **/
    private long roleHeroId;
    /** 城镇id **/
    private long city_id;
    /** 建筑id **/
    private long build_id;    
    /** 兵种招募数量 **/
    private int recruit_num;
    /** 种族 **/
    private int race;
    /** 军事建筑类型 **/
    private int hire_build;
    /** 兵种招募消耗时间(招募一个兵的消耗时间) **/
    private int recruit_time;
    /** 兵源变动记录 **/
    private String soldier_change;
    /** 英雄率领的部队迁移到目标城镇 **/
    private long target_city_id;
    /** 消耗金币数量 **/
    private int use_gold;
    /** 消耗点券、钻石数量 **/
    private int use_diamond;
    /** 招募一个兵的时间周期 **/
    private int one_time;
    /** 招募一队兵的时间总和 **/
    private int total_time;
    /** 兵种消耗的人口 **/
    private int eat;
    /** 军事系统模块请求类型 **/
    private int type;
    /** 被派驻的英雄id字符串 **/
    private List<Long> heroIds;
    
    //城镇所拥有的赋闲部队
    private List<ArmsDeploy> cityArmyList;
    //英雄所率领的部队
    private List<ArmsDeploy> heroArmyList;
    
    private String herosId;//随行英雄ID 以","分割
    private int trans_time;//部队转移消耗时间
    private int trans_food;//部队转移消耗粮草
    
    private int trans_level;//传送门等级
    
    //----------------战斗队列相关------------------
    private int tag_x;//目标X坐标
    private int tag_y;//目标Y坐标
    private long from_city_id;//出发城邦
    private long from_role_id;//攻防ID
    
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
    public long getFrom_role_id() {
		return from_role_id;
	}
	public void setFrom_role_id(long from_role_id) {
		this.from_role_id = from_role_id;
	}
	public long getFrom_city_id() {
		return from_city_id;
	}
	public void setFrom_city_id(long from_city_id) {
		this.from_city_id = from_city_id;
	}
	public int getTrans_level() {
		return trans_level;
	}
	public void setTrans_level(int trans_level) {
		this.trans_level = trans_level;
	}
	public String getHerosId() {
		return herosId;
	}
	public void setHerosId(String herosId) {
		this.herosId = herosId;
	}
	public int getTag_x() {
		return tag_x;
	}
	public void setTag_x(int tag_x) {
		this.tag_x = tag_x;
	}
	public int getTag_y() {
		return tag_y;
	}
	public void setTag_y(int tag_y) {
		this.tag_y = tag_y;
	}
	public int getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(int trans_time) {
		this.trans_time = trans_time;
	}
	public int getTrans_food() {
		return trans_food;
	}
	public void setTrans_food(int trans_food) {
		this.trans_food = trans_food;
	}
	public int getRace() {
		return race;
	}
	public void setRace(int race) {
		this.race = race;
	}
	public int getHire_build() {
		return hire_build;
	}
	public void setHire_build(int hire_build) {
		this.hire_build = hire_build;
	}
	public long getRole_id()
    {
        return role_id;
    }
    public void setRole_id(long role_id)
    {
        this.role_id = role_id;
    }
    public int getSoldierId()
    {
        return soldierId;
    }
    public void setSoldierId(int soldierId)
    {
        this.soldierId = soldierId;
    }
    public long getCity_id()
    {
        return city_id;
    }
    public void setCity_id(long city_id)
    {
        this.city_id = city_id;
    }
    public int getRecruit_num()
    {
        return recruit_num;
    }
    public void setRecruit_num(int recruit_num)
    {
        this.recruit_num = recruit_num;
    }
    public int getRecruit_time()
    {
        return recruit_time;
    }
    public void setRecruit_time(int recruit_time)
    {
        this.recruit_time = recruit_time;
    }
    public String getSoldier_change()
    {
        return soldier_change;
    }
    public void setSoldier_change(String soldier_change)
    {
        this.soldier_change = soldier_change;
    }
    public long getTarget_city_id()
    {
        return target_city_id;
    }
    public void setTarget_city_id(long target_city_id)
    {
        this.target_city_id = target_city_id;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getUse_gold()
    {
        return use_gold;
    }
    public void setUse_gold(int use_gold)
    {
        this.use_gold = use_gold;
    }
    public int getOne_time()
    {
        return one_time;
    }
    public void setOne_time(int one_time)
    {
        this.one_time = one_time;
    }
    public int getTotal_time()
    {
        return total_time;
    }
    public void setTotal_time(int total_time)
    {
        this.total_time = total_time;
    }
    public long getBuild_id()
    {
        return build_id;
    }
    public void setBuild_id(long build_id)
    {
        this.build_id = build_id;
    }
    public int getEat()
    {
        return eat;
    }
    public void setEat(int eat)
    {
        this.eat = eat;
    }
  
   
    public List<ArmsDeploy> getCityArmyList()
    {
        return cityArmyList;
    }
    public void setCityArmyList(List<ArmsDeploy> cityArmyList)
    {
        this.cityArmyList = cityArmyList;
    }
    public List<ArmsDeploy> getHeroArmyList()
    {
        return heroArmyList;
    }
    public void setHeroArmyList(List<ArmsDeploy> heroArmyList)
    {
        this.heroArmyList = heroArmyList;
    }
    public long getRoleHeroId()
    {
        return roleHeroId;
    }
    public void setRoleHeroId(long roleHeroId)
    {
        this.roleHeroId = roleHeroId;
    }
    public int getUse_diamond()
    {
        return use_diamond;
    }
    public void setUse_diamond(int use_diamond)
    {
        this.use_diamond = use_diamond;
    }
    public List<Long> getHeroIds()
    {
        return heroIds;
    }
    public void setHeroIds(List<Long> heroIds)
    {
        this.heroIds = heroIds;
    }
    
}
