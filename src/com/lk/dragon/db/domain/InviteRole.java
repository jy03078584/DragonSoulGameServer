/**
 *
 *
 * 文件名称： InviteRole.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:26:35
 */
package com.lk.dragon.db.domain;

public class InviteRole
{
    private long invite_id;            //邀请人id
    private long invite_role_id;       //被邀请人id（发邀请人id）
    private long role_id;
    private String role_name;
    private int lev;
    private int sex;
    private String icon;
    
    
    
    public InviteRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InviteRole(long invite_id, long invite_role_id) {
		super();
		this.invite_id = invite_id;
		this.invite_role_id = invite_role_id;
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
    public int getLev()
    {
        return lev;
    }
    public void setLev(int lev)
    {
        this.lev = lev;
    }
    public int getSex()
    {
        return sex;
    }
    public void setSex(int sex)
    {
        this.sex = sex;
    }
    public String getIcon()
    {
        return icon;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    public long getInvite_id()
    {
        return invite_id;
    }
    public void setInvite_id(long invite_id)
    {
        this.invite_id = invite_id;
    }
    public long getInvite_role_id()
    {
        return invite_role_id;
    }
    public void setInvite_role_id(long invite_role_id)
    {
        this.invite_role_id = invite_role_id;
    }
    
}
