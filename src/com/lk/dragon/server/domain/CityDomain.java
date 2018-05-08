/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： MainCityDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午3:03:20
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class CityDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 城市类型(主城、分城) **/
    private int cityType;
    /** 城市名字 **/
    private String cityName;
    /** 种族 **/
    private int race;
    /** 角色id **/
    private long roldId;
    /** 城市、建筑关联id **/
    private long relaId;
    /** 城市id **/
    private long cityId;
    /** 建筑id **/
    private int buildId;
    /** 城市坐标x轴 **/
    private int x;
    /** 城市坐标y轴 **/
    private int y;
    /** 迁城目标点x **/
    private int targetX;
    /** 迁城目标点y **/
    private int targetY;
    /** 请求类型(修建城市、城市升级、城市建筑列表，修建建筑，升级建筑) **/
    private int type;
    /** 建筑升级完成时间 **/
    private int levelUpT;
    /** 建筑等级 **/
    private int buildLev;
    /** 建筑类型 **/
    private int buildType;
    /**建筑位置--针对外城建筑**/
    private int locate;
    /** 道具id **/
    private long rolePropId;
    /** 道具是否有剩余 **/
    private int is_enough;
    /** 保护时长 **/
    private int protectTime;
    /** 消耗钻石 **/
    private int diamondNum;
    /** 建筑升级消耗 **/
    private int wood;
    private int food;
    private int stone;
    private int gold;
    //人口
    private int eat;
    
    //资源点等级
    private int res_level;
    


    
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getRes_level() {
		return res_level;
	}
	public void setRes_level(int res_level) {
		this.res_level = res_level;
	}
	public int getLocate() {
		return locate;
	}
	public void setLocate(int locate) {
		this.locate = locate;
	}
	public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getCityType()
    {
        return cityType;
    }
    public void setCityType(int cityType)
    {
        this.cityType = cityType;
    }
    public int getRace()
    {
        return race;
    }
    public void setRace(int race)
    {
        this.race = race;
    }
    public long getRoldId()
    {
        return roldId;
    }
    public void setRoldId(long roldId)
    {
        this.roldId = roldId;
    }
    public long getRelaId()
    {
        return relaId;
    }
    public void setRelaId(long relaId)
    {
        this.relaId = relaId;
    }
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public String getCityName()
    {
        return cityName;
    }
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
    public long getCityId()
    {
        return cityId;
    }
    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }
    public int getLevelUpT()
    {
        return levelUpT;
    }
    public void setLevelUpT(int levelUpT)
    {
        this.levelUpT = levelUpT;
    }
    public int getBuildId()
    {
        return buildId;
    }
    public void setBuildId(int buildId)
    {
        this.buildId = buildId;
    }
    public int getTargetX()
    {
        return targetX;
    }
    public void setTargetX(int targetX)
    {
        this.targetX = targetX;
    }
    public int getTargetY()
    {
        return targetY;
    }
    public void setTargetY(int targetY)
    {
        this.targetY = targetY;
    }
    public int getBuildLev()
    {
        return buildLev;
    }
    public void setBuildLev(int buildLev)
    {
        this.buildLev = buildLev;
    }
    public int getBuildType()
    {
        return buildType;
    }
    public void setBuildType(int buildType)
    {
        this.buildType = buildType;
    }
    public long getRolePropId()
    {
        return rolePropId;
    }
    public void setRolePropId(long rolePropId)
    {
        this.rolePropId = rolePropId;
    }
    public int getIs_enough()
    {
        return is_enough;
    }
    public void setIs_enough(int is_enough)
    {
        this.is_enough = is_enough;
    }
    public int getProtectTime()
    {
        return protectTime;
    }
    public void setProtectTime(int protectTime)
    {
        this.protectTime = protectTime;
    }
    public int getDiamondNum()
    {
        return diamondNum;
    }
    public void setDiamondNum(int diamondNum)
    {
        this.diamondNum = diamondNum;
    }
    public int getWood()
    {
        return wood;
    }
    public void setWood(int wood)
    {
        this.wood = wood;
    }
    public int getFood()
    {
        return food;
    }
    public void setFood(int food)
    {
        this.food = food;
    }
    public int getStone()
    {
        return stone;
    }
    public void setStone(int stone)
    {
        this.stone = stone;
    }
    public int getGold()
    {
        return gold;
    }
    public void setGold(int gold)
    {
        this.gold = gold;
    }
    public int getEat()
    {
        return eat;
    }
    public void setEat(int eat)
    {
        this.eat = eat;
    }    
    
}
