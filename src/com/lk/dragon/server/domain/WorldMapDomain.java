/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： WorldMapDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午3:13:42
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class WorldMapDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 世界坐标点X **/
    private int x;
    /** 世界坐标点Y **/
    private int y;
    /** 世界坐标点查询的x下限坐标 **/
    private int min_x;
    /** 世界坐标点查询的x上限坐标 **/
    private int max_x;
    /** 世界坐标点查询的y下限坐标 **/
    private int min_y;
    /** 世界坐标点查询的y上限坐标 **/
    private int max_y;
    /** 请求类型(出生点生成) **/
    private int type;
    /** 角色消耗钻石量 **/
    private int useDiamond;
    /** 角色消耗金币 **/
    private int useGold;
    /** 角色id **/
    private long role_id;
    private long city_id;
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public int getUseGold() {
		return useGold;
	}

	public void setUseGold(int useGold) {
		this.useGold = useGold;
	}

    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
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
    public int getMin_x()
    {
        return min_x;
    }
    public void setMin_x(int min_x)
    {
        this.min_x = min_x;
    }
    public int getMax_x()
    {
        return max_x;
    }
    public void setMax_x(int max_x)
    {
        this.max_x = max_x;
    }
    public int getMin_y()
    {
        return min_y;
    }
    public void setMin_y(int min_y)
    {
        this.min_y = min_y;
    }
    public int getMax_y()
    {
        return max_y;
    }
    public void setMax_y(int max_y)
    {
        this.max_y = max_y;
    }
    public int getUseDiamond()
    {
        return useDiamond;
    }
    public void setUseDiamond(int useDiamond)
    {
        this.useDiamond = useDiamond;
    }
    public long getRole_id()
    {
        return role_id;
    }
    public void setRole_id(long role_id)
    {
        this.role_id = role_id;
    }
	public long getCity_id() {
		return city_id;
	}
	public void setCity_id(long city_id) {
		this.city_id = city_id;
	}
    
}
