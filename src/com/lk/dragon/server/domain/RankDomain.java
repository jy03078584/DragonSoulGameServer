/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： RankDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-13 上午11:01:40
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class RankDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /**对象ID**/
    private long object_id;
    /** 排行榜请求类型 **/
    private int type;

    
    
    
    
    
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getObject_id() {
		return object_id;
	}
	public void setObject_id(long object_id) {
		this.object_id = object_id;
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
    
    
}
