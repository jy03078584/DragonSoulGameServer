/**
 *
 *
 * 文件名称： RoleDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-5 上午9:59:00
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class RoleDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 用户id **/
    private long user_id;
    /** 角色id **/
    private long role_id;
    /** 角色名 **/
    private String role_name;
    /** 性别 **/
    private int sex;
    /** 角色种族 **/
    private int race;
    /** 角色头像 **/
    private String icon;
    /** 角色小头像 **/
    private String shortIcon;
    /** 公会id **/
    private long guildId;
    /** 请求类型(检测是否重复登录、新建角色、修改角色、删除角色) **/
    private int type;
    /** 领取 **
     */
    private int local;
    
    /**使用的道具ID**/
    private long rolePropId;
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getRolePropId() {
		return rolePropId;
	}
	public void setRolePropId(long rolePropId) {
		this.rolePropId = rolePropId;
	}
	public long getUser_id()
    {
        return user_id;
    }
    public void setUser_id(long user_id)
    {
        this.user_id = user_id;
    }
    public long getRole_id()
    {
        return role_id;
    }
    public void setRole_id(long role_id)
    {
        this.role_id = role_id;
    }
    public String getRole_name()
    {
        return role_name;
    }
    public void setRole_name(String role_name)
    {
        this.role_name = role_name;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getRace()
    {
        return race;
    }
    public void setRace(int race)
    {
        this.race = race;
    }
    public String getIcon()
    {
        return icon;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    public int getSex()
    {
        return sex;
    }
    public void setSex(int sex)
    {
        this.sex = sex;
    }
    public String getShortIcon()
    {
        return shortIcon;
    }
    public void setShortIcon(String shortIcon)
    {
        this.shortIcon = shortIcon;
    }
    public long getGuildId()
    {
        return guildId;
    }
    public void setGuildId(long guildId)
    {
        this.guildId = guildId;
    }
    public int getLocal()
    {
        return local;
    }
    public void setLocal(int local)
    {
        this.local = local;
    }
    
}
