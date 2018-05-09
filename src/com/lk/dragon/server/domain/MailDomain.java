/**
 *
 *
 * 文件名称： MailDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-25 下午2:24:08
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;


import com.lk.dragon.db.domain.Attachment;

public class MailDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 邮件id **/
    private long mailId;
    /** 发件人的id **/
    private long roleId;
    /** 收件人的id **/
    private long sendId;
    /** 收件人昵称 **/
    private String sendName;
    /** 邮件标题 **/
    private String title;
    /** 发送的内容 **/
    private String content;
    /** 附件 **/
    private List<Attachment> annexList;
    /** 邮件类型 **/
    private int mailType;
    /** 创建时间 **/
    private String time;
    /** 是否来自系统邮件 **/
    private int isSystemMail; 
    /** 上次轮询邮件的最大id值 **/
    private long LAST_MAX_MAIL_ID;
    /** 信件类型（普通文字邮件、物品邮件） **/
    private int type;
    
    
    /** 缓存relation_id **/
    private long relation_id;
    
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getRelation_id()
    {
        return relation_id;
    }
    public void setRelation_id(long relation_id)
    {
        this.relation_id = relation_id;
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
    
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public long getSendId()
    {
        return sendId;
    }
    public void setSendId(long sendId)
    {
        this.sendId = sendId;
    }

    public List<Attachment> getAnnexList()
    {
        return annexList;
    }
    public void setAnnexList(List<Attachment> annexList)
    {
        this.annexList = annexList;
    }
    public int getMailType()
    {
        return mailType;
    }
    public void setMailType(int mailType)
    {
        this.mailType = mailType;
    }
    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
   
    public int getIsSystemMail()
    {
        return isSystemMail;
    }
    public void setIsSystemMail(int isSystemMail)
    {
        this.isSystemMail = isSystemMail;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public long getMailId()
    {
        return mailId;
    }
    public void setMailId(long mailId)
    {
        this.mailId = mailId;
    }
    public long getLAST_MAX_MAIL_ID()
    {
        return LAST_MAX_MAIL_ID;
    }
    public void setLAST_MAX_MAIL_ID(long lAST_MAX_MAIL_ID)
    {
        LAST_MAX_MAIL_ID = lAST_MAX_MAIL_ID;
    }
    public String getSendName()
    {
        return sendName;
    }
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }
    
}
