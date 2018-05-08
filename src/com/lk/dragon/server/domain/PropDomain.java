/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： PropDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午3:03:10
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class PropDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 道具id **/
    private int propId;
    /** 关联表id **/
    private long relationId;    
    /** 使用数量（拆分数量） **/
    private int useNum;
    /** 道具数量 **/
    private int propNum;
    /** 请求类型 **/
    private int type;
    
    //道具使用
    private int propType;  //使用的道具类型
    
    //喝药
    private long roleHeroId;  //使用对象
    private long rolePropId;  //使用道具
    private int value;       //带来的增益值
    
    
    private int isRandom;//是否是随机奖励
    private int randCnt;//随机次数
    
    private int gold;//金币量
    
    //---------------buff
    private int buff_id;
    private long buffKeyId;
    private int buffType;
    
    
    
    public int getBuffType() {
		return buffType;
	}
	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	public int getBuff_id() {
		return buff_id;
	}
	public void setBuff_id(int buff_id) {
		this.buff_id = buff_id;
	}
	
	public long getBuffKeyId() {
		return buffKeyId;
	}
	public void setBuffKeyId(long buffKeyId) {
		this.buffKeyId = buffKeyId;
	}

    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getRandCnt() {
		return randCnt;
	}
	public void setRandCnt(int randCnt) {
		this.randCnt = randCnt;
	}
	public int getIsRandom() {
		return isRandom;
	}
	public void setIsRandom(int isRandom) {
		this.isRandom = isRandom;
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
    public int getPropId()
    {
        return propId;
    }
    public void setPropId(int propId)
    {
        this.propId = propId;
    }
    public int getUseNum()
    {
        return useNum;
    }
    public void setUseNum(int useNum)
    {
        this.useNum = useNum;
    }
    public long getRelationId()
    {
        return relationId;
    }
    public void setRelationId(long relationId)
    {
        this.relationId = relationId;
    }
    public int getPropNum()
    {
        return propNum;
    }
    public void setPropNum(int propNum)
    {
        this.propNum = propNum;
    }
    public int getPropType()
    {
        return propType;
    }
    public void setPropType(int propType)
    {
        this.propType = propType;
    }
    public long getRoleHeroId()
    {
        return roleHeroId;
    }
    public void setRoleHeroId(long roleHeroId)
    {
        this.roleHeroId = roleHeroId;
    }
    public long getRolePropId()
    {
        return rolePropId;
    }
    public void setRolePropId(long rolePropId)
    {
        this.rolePropId = rolePropId;
    }
    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
	@Override
	public String toString() {
		return "PropDomain [ctx=" + ctx + ", roleId=" + roleId + ", propId="
				+ propId + ", relationId=" + relationId + ", useNum=" + useNum
				+ ", propNum=" + propNum + ", type=" + type + ", propType="
				+ propType + ", roleHeroId=" + roleHeroId + ", rolePropId="
				+ rolePropId + ", value=" + value + "]";
	}
    
}
