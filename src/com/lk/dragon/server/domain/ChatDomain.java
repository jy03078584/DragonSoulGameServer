/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ChatDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-1 上午10:25:41
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class ChatDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 角色名 **/
    private String roleName;
    /** 公会id **/
    private long guildId;
    /** 聊天内容 **/
    private String content;
    /** 聊天的角色id **/
    private long chatRoleId;
    /** 公会id **/
    private long chatGuildId;
    /** 聊天类型(公会聊天，公共聊天， 私聊，建立连接，断开连接) **/
    private int type;
    
    
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public long getChatRoleId()
    {
        return chatRoleId;
    }
    public void setChatRoleId(long chatRoleId)
    {
        this.chatRoleId = chatRoleId;
    }
    public long getGuildId()
    {
        return guildId;
    }
    public void setGuildId(long guildId)
    {
        this.guildId = guildId;
    }
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getChatGuildId()
    {
        return chatGuildId;
    }
    public void setChatGuildId(long chatGuildId)
    {
        this.chatGuildId = chatGuildId;
    }
    public String getRoleName()
    {
        return roleName;
    }
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    
}
