/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ConnDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-12-10 下午5:27:00
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class ConnDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 公会id **/
    private long guildId;
    /** 链接是否超时标志位 **/
    private int timeoutFlag;
    
    
    
    
    
    public ConnDomain(ChannelHandlerContext ctx, long roleId) {
		super();
		this.ctx = ctx;
		this.roleId = roleId;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getTimeoutFlag() {
		return timeoutFlag;
	}
	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public long getGuildId()
    {
        return guildId;
    }
    public void setGuildId(long guildId)
    {
        this.guildId = guildId;
    }
    
    
}
