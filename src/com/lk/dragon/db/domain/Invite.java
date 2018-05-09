/**
 *
 *
 * 文件名称： Reward.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:23:23
 */
package com.lk.dragon.db.domain;

public class Invite
{
    private long invite_id;
    private long role_id;
    private int invite_code;
    private String reward;
    
    
    public Invite() {
		super();
	}
	public long getInvite_id()
    {
        return invite_id;
    }
    public void setInvite_id(long invite_id)
    {
        this.invite_id = invite_id;
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
    public String getReward()
    {
        return reward;
    }
    public void setReward(String reward)
    {
        this.reward = reward;
    }
    
    
}
