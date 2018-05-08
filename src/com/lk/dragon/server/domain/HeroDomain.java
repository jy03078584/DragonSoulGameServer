/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： HeroDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-11 下午3:59:49
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class HeroDomain
{
    /** 连接 **/
	private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 英雄id **/
    private int heroId;
    /** 消耗钻石数 **/
    private int use_diamon;
    /** 道具id **/
    private long rolePropId;
    /** 英雄穿上的道具id**/
    private long heroaddPropId;
    /** 英雄脱下的道具id **/
    private long heroDelePropId;
    /** 角色-英雄id**/
    private long roleHeroId;
    /** 城市id **/
    private long city_id;
    /** 招募英雄消耗黄金数量 **/
    private int goldNum;
    /** 道具是否够用 **/
    private int prop_is_enough;
    /** 药品类型 **/
    private int medicine_type;
    /** 药品使用增益量 **/
    private int reply_quantity;
    /** 需要复活的英雄列表信息  **/
    private int revive_time;
    /** 英雄的属性信息以及可以分配加点信息 **/
    private Integer physique;//英雄体质
    private Integer mentality;//英雄智力
    private Integer hero_power;//英雄力量
    private Integer endurance;//英雄耐力
    private Integer agility;//英雄敏捷
    private int can_assign_point; //英雄可以分配的加点
    private Integer max_hp;//最大血量
    private Integer max_mp;//最大蓝量
    private Integer hp;//生命值
    private Integer mp;//魔法值
    /** 英雄模块请求类型(英雄列表查询、英雄召唤等请求) **/
    private int type;
    
    private String hero_name;//英雄名
    
    private Integer train_time;//训练时间
    private Integer pre_exp;//单位时间经验值
    
    private int heroLev;//英雄等级
    
    private String heroIds;
    
    
    
    public String getHeroIds() {
		return heroIds;
	}
	public void setHeroIds(String heroIds) {
		this.heroIds = heroIds;
	}
	public int getHeroLev() {
		return heroLev;
	}
	public void setHeroLev(int heroLev) {
		this.heroLev = heroLev;
	}
	public Integer getPre_exp() {
		return pre_exp;
	}
	public void setPre_exp(Integer pre_exp) {
		this.pre_exp = pre_exp;
	}
	public Integer getTrain_time() {
		return train_time;
	}
	public void setTrain_time(Integer train_time) {
		this.train_time = train_time;
	}
	public String getHero_name() {
		return hero_name;
	}
	public void setHero_name(String hero_name) {
		this.hero_name = hero_name;
	}
	public int getUse_diamon() {
		return use_diamon;
	}
	public void setUse_diamon(int use_diamon) {
		this.use_diamon = use_diamon;
	}
	
	
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public int getHeroId()
    {
        return heroId;
    }
    public void setHeroId(int heroId)
    {
        this.heroId = heroId;
    }

    public long getHeroaddPropId()
    {
        return heroaddPropId;
    }
    public void setHeroaddPropId(long heroaddPropId)
    {
        this.heroaddPropId = heroaddPropId;
    }
    public long getHeroDelePropId()
    {
        return heroDelePropId;
    }
    public void setHeroDelePropId(long heroDelePropId)
    {
        this.heroDelePropId = heroDelePropId;
    }
    public long getRoleHeroId()
    {
        return roleHeroId;
    }
    public void setRoleHeroId(long roleHeroId)
    {
        this.roleHeroId = roleHeroId;
    }
    public long getCity_id()
    {
        return city_id;
    }
    public void setCity_id(long city_id)
    {
        this.city_id = city_id;
    }

    public int getGoldNum()
    {
        return goldNum;
    }
    public void setGoldNum(int goldNum)
    {
        this.goldNum = goldNum;
    }
    public int getProp_is_enough()
    {
        return prop_is_enough;
    }
    public void setProp_is_enough(int prop_is_enough)
    {
        this.prop_is_enough = prop_is_enough;
    }
    public int getMedicine_type()
    {
        return medicine_type;
    }
    public void setMedicine_type(int medicine_type)
    {
        this.medicine_type = medicine_type;
    }
    public int getReply_quantity()
    {
        return reply_quantity;
    }
    public void setReply_quantity(int reply_quantity)
    {
        this.reply_quantity = reply_quantity;
    }
    public long getRolePropId()
    {
        return rolePropId;
    }
    public void setRolePropId(long rolePropId)
    {
        this.rolePropId = rolePropId;
    }
   
    public int getRevive_time()
    {
        return revive_time;
    }
    public void setRevive_time(int revive_time)
    {
        this.revive_time = revive_time;
    }
    public Integer getPhysique()
    {
        return physique;
    }
    public void setPhysique(Integer physique)
    {
        this.physique = physique;
    }
    public Integer getMentality()
    {
        return mentality;
    }
    public void setMentality(Integer mentality)
    {
        this.mentality = mentality;
    }
    public Integer getHero_power()
    {
        return hero_power;
    }
    public void setHero_power(Integer hero_power)
    {
        this.hero_power = hero_power;
    }
    public Integer getEndurance()
    {
        return endurance;
    }
    public void setEndurance(Integer endurance)
    {
        this.endurance = endurance;
    }
    public Integer getAgility()
    {
        return agility;
    }
    public void setAgility(Integer agility)
    {
        this.agility = agility;
    }
    public int getCan_assign_point()
    {
        return can_assign_point;
    }
    public void setCan_assign_point(int can_assign_point)
    {
        this.can_assign_point = can_assign_point;
    }
    public Integer getMax_hp()
    {
        return max_hp;
    }
    public void setMax_hp(Integer max_hp)
    {
        this.max_hp = max_hp;
    }
    public Integer getMax_mp()
    {
        return max_mp;
    }
    public void setMax_mp(Integer max_mp)
    {
        this.max_mp = max_mp;
    }
    public Integer getHp()
    {
        return hp;
    }
    public void setHp(Integer hp)
    {
        this.hp = hp;
    }
    public Integer getMp()
    {
        return mp;
    }
    public void setMp(Integer mp)
    {
        this.mp = mp;
    }    
    
}
