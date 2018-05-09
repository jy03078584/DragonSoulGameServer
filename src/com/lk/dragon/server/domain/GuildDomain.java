/**
 *
 *
 * 文件名称： GuildDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-27 下午2:45:15
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class GuildDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 角色名称 **/
    private String roleName;
    /** 申请人id **/
    private long applyRoleId;
    /** 申请人姓名 **/
    private String applyRoleName;
    /** 被踢人id **/
    private long shotId;
    /** 被踢人姓名 **/
    private String shotName;
    /** 公会id **/
    private long guildId;
    /** 公会公告 **/
    private String guildAnnouncement;
    /** 公会名称 **/
    private String guildName;
    /** 公会图标 **/
    private String guildIcon;
    /** 公会职位名称 **/
    private String guildJobName;
    /** 角色-公会id **/
    private long role_guild_id;
    /** 创建公会消耗资金 **/
    private int gold_num;
    /** 公会奖励等级 **/
    private int award_lev;
    /** 公会奖励领取后结果 **/
    private int after_award_result;
    /** 公会职位列表id **/
    private long position_id;
    /** 公会请求类型(创建公会、更新公会、删除公会、获取公会列表、加入公会、退出公会)  **/
    private int type;

    
    /**操作人ID**/
    private long operator_id;

    

    public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
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

    public long getGuildId()
    {
        return guildId;
    }

    public void setGuildId(long guildId)
    {
        this.guildId = guildId;
    }

    public String getGuildAnnouncement()
    {
        return guildAnnouncement;
    }

    public void setGuildAnnouncement(String guildAnnouncement)
    {
        this.guildAnnouncement = guildAnnouncement;
    }

    public String getGuildIcon()
    {
        return guildIcon;
    }

    public void setGuildIcon(String guildIcon)
    {
        this.guildIcon = guildIcon;
    }

    public String getGuildJobName()
    {
        return guildJobName;
    }

    public void setGuildJobName(String guildJobName)
    {
        this.guildJobName = guildJobName;
    }

    public long getRole_guild_id()
    {
        return role_guild_id;
    }

    public void setRole_guild_id(long role_guild_id)
    {
        this.role_guild_id = role_guild_id;
    }

    public String getGuildName()
    {
        return guildName;
    }

    public void setGuildName(String guildName)
    {
        this.guildName = guildName;
    }

    public int getGold_num()
    {
        return gold_num;
    }

    public void setGold_num(int gold_num)
    {
        this.gold_num = gold_num;
    }

    public long getApplyRoleId()
    {
        return applyRoleId;
    }

    public void setApplyRoleId(long applyRoleId)
    {
        this.applyRoleId = applyRoleId;
    }

    public long getShotId()
    {
        return shotId;
    }

    public void setShotId(long shotId)
    {
        this.shotId = shotId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getApplyRoleName()
    {
        return applyRoleName;
    }

    public void setApplyRoleName(String applyRoleName)
    {
        this.applyRoleName = applyRoleName;
    }

    public String getShotName()
    {
        return shotName;
    }

    public void setShotName(String shotName)
    {
        this.shotName = shotName;
    }

    public int getAward_lev()
    {
        return award_lev;
    }

    public void setAward_lev(int award_lev)
    {
        this.award_lev = award_lev;
    }

    public int getAfter_award_result()
    {
        return after_award_result;
    }

    public void setAfter_award_result(int after_award_result)
    {
        this.after_award_result = after_award_result;
    }

    public long getPosition_id()
    {
        return position_id;
    }

    public void setPosition_id(long position_id)
    {
        this.position_id = position_id;
    }
    
    
}
