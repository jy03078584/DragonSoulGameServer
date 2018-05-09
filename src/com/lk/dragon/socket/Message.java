/**
 *
 *
 * 文件名称： Message.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-11-5 下午5:48:47
 */
package com.lk.dragon.socket;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;


/**
 * 客户端发送到服务端的消息实体对象
 *
 *
 * 文件名称： Message.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-11-5 下午5:49:01
 */
public class Message
{
    /** 消息实体 **/
    private String message;
    /** 发送时间 **/
    private Date time;
    /** 头部标志位  **/
    private String headFlag;
    /** 尾部标志位 **/
    private String endFlag;
    /** 连接信息 **/
    private ChannelHandlerContext ctx;
    
  
	public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public String getHeadFlag()
    {
        return headFlag;
    }
    public void setHeadFlag(String headFlag)
    {
        this.headFlag = headFlag;
    }
    public String getEndFlag()
    {
        return endFlag;
    }
    public void setEndFlag(String endFlag)
    {
        this.endFlag = endFlag;
    }
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

    
}
