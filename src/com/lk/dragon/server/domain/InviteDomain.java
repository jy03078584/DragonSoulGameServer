/**
 * Copyright ? 2015，成都乐控
 * All Rights Reserved.
 * 文件名称： RewardDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午3:59:07
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class InviteDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long role_id;
    /** 邀请码 **/
    private int invite_code;
    /** 奖励领取的序列号 **/
    private int reward_index;
    /** 请求类型 **/
    private int type;
    /**任务ID**/
    private int invite_info_id;
    
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getInvite_info_id() {
		return invite_info_id;
	}
	public void setInvite_info_id(int invite_info_id) {
		this.invite_info_id = invite_info_id;
	}
	public long getRole_id()
    {
        return role_id;
    }
    public void setRole_id(long role_id)
    {
        this.role_id = role_id;
    }
    public int getInvite_code()
    {
        return invite_code;
    }
    public void setInvite_code(int invite_code)
    {
        this.invite_code = invite_code;
    }
    public int getReward_index()
    {
        return reward_index;
    }
    public void setReward_index(int reward_index)
    {
        this.reward_index = reward_index;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
}
