/**
 *
 *
 * 文件名称： FriendDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-26 上午11:45:01
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class FriendDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 请求添加邮件id **/
    private long mailId;
    /** 好友申请的请求人的id **/
    private long id;
    /** 被邀请人的id **/
    private long friendId;
    /** 被邀请人名字 **/
    private String name;
    /** 关系id **/
    private long relation_id;
    
    /** 仇人id **/
    private long enemyId;
    /** 关系类型 **/
    private int relationType;
    /** 请求后好友目标处理结果 **/
    private int dealType;
    /** 好友请求模块类型(好友列表查询、好友添加申请) **/
    private int type;
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public long getFriendId()
    {
        return friendId;
    }
    public void setFriendId(long friendId)
    {
        this.friendId = friendId;
    }
    public long getEnemyId()
    {
        return enemyId;
    }
    public void setEnemyId(long enemyId)
    {
        this.enemyId = enemyId;
    }
    public int getRelationType()
    {
        return relationType;
    }
    public void setRelationType(int relationType)
    {
        this.relationType = relationType;
    }
    public int getDealType()
    {
        return dealType;
    }
    public void setDealType(int dealType)
    {
        this.dealType = dealType;
    }
    public long getMailId()
    {
        return mailId;
    }
    public void setMailId(long mailId)
    {
        this.mailId = mailId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public long getRelation_id()
    {
        return relation_id;
    }
    public void setRelation_id(long relation_id)
    {
        this.relation_id = relation_id;
    }
    
}
