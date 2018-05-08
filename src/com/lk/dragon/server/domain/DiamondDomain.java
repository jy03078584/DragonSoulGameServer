/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： DiamondDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午10:25:49
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class DiamondDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 宝石道具id **/
    private int diamondId;
    /** 宝石道具-角色关联id **/
    private long relaId;
    /** 宝石类型 **/
    private int diamondType;
    /** 宝石等级 **/
    private int diamondLev;
    /** 是否成功 **/
    private int isSuccess;
    /** 消耗金币 **/
    private int useGold;
    /** 装备请求类型 **/
    private int type;
    /** 道具id **/
    private long rolePropId;
    /** 道具是否剩余 **/
    private int is_enough;
    
    /**辅助道具ID：增加合成概率的辅助道具等**/
    private long assRolePropsId;
    /**辅助道具使用个数*/
    private int  assPropsCount;
    

    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getAssRolePropsId() {
		return assRolePropsId;
	}
	public void setAssRolePropsId(long assRolePropsId) {
		this.assRolePropsId = assRolePropsId;
	}
	public int getAssPropsCount() {
		return assPropsCount;
	}
	public void setAssPropsCount(int assPropsCount) {
		this.assPropsCount = assPropsCount;
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
    public int getDiamondType()
    {
        return diamondType;
    }
    public void setDiamondType(int diamondType)
    {
        this.diamondType = diamondType;
    }
    public int getDiamondLev()
    {
        return diamondLev;
    }
    public void setDiamondLev(int diamondLev)
    {
        this.diamondLev = diamondLev;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getIsSuccess()
    {
        return isSuccess;
    }
    public void setIsSuccess(int isSuccess)
    {
        this.isSuccess = isSuccess;
    }
    public int getUseGold()
    {
        return useGold;
    }
    public void setUseGold(int useGold)
    {
        this.useGold = useGold;
    }
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
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
    
    
}
