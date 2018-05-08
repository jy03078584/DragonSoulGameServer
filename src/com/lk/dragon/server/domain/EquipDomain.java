/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： EquipDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午10:25:36
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;



public class EquipDomain
{
   
	/** 连接 **/
    private ChannelHandlerContext ctx;
    /** 装备id **/
    private int equipId;
    /** 宝石id **/
    private int diamondId;
    /** 装备-宝石关联表id **/
    private long relaId;
    /** 角色id **/
    private long roleId;
    /** 消耗金币数量 **/
    private int goldNum;
    /** 装备请求类型 **/
    private int type;
    private int diamondNum;
    
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getEquipId()
    {
        return equipId;
    }
    public void setEquipId(int equipId)
    {
        this.equipId = equipId;
    }
    public int getDiamondId()
    {
        return diamondId;
    }
    public void setDiamondId(int diamondId)
    {
        this.diamondId = diamondId;
    }
    public long getRelaId()
    {
        return relaId;
    }
    public void setRelaId(long relaId)
    {
        this.relaId = relaId;
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
    public int getGoldNum()
    {
        return goldNum;
    }
    public void setGoldNum(int goldNum)
    {
        this.goldNum = goldNum;
    }
    public int getDiamondNum()
    {
        return diamondNum;
    }
    public void setDiamondNum(int diamondNum)
    {
        this.diamondNum = diamondNum;
    }
    
}
