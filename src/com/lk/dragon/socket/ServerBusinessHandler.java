 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: ServerBusinessHandler.java 
 * @Package com.lk.dragon.socket 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-12 上午8:45:39 
 * @version V1.0   
 */
package com.lk.dragon.socket;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.apache.log4j.Logger;

import com.lk.dragon.server.analysis.ServerAnalysis;
import com.lk.dragon.server.module.BusinessTask;
import com.lk.dragon.service.CacheService;
import com.lk.dragon.util.BusinessThreadPoll;
import com.lk.dragon.util.SpringBeanUtil;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**  
 * @Description:服务端业务Handler
 */
public class ServerBusinessHandler extends SimpleChannelInboundHandler<String> {

	private  int idleCount = 1;
	private static  Logger logger = Logger.getLogger(ServerBusinessHandler.class); 
	@Override
	public void messageReceived(ChannelHandlerContext ctx, String msg)throws Exception {
		//客户端有信息发送 读超时重置
		this.idleCount = 0;
		//处理请求
		//System.out.println(Thread.currentThread().getName()+">>>>>1"+msg);
		System.out.println(msg);
		//解析分发业务
		BusinessThreadPoll.doBusinessOp(new BusinessTask(new ServerAnalysis(ctx, msg)));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		logger.info("Unexpected exception ServerBUssinessLogicHandler:",cause);
		ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
		
		/**==========心跳机制处理=======**/
		if(evt instanceof IdleStateEvent){
			
			IdleStateEvent e = (IdleStateEvent) evt;
			if(e.state() == IdleState.READER_IDLE){//读超时
				idleCount++;
				//客户端连续3次触发服务器Reader_idle 则服务端可判断该Client已断开 
				if(idleCount >= 3){
					ctx.channel().close();
					roleOffLineLooseConnect(ctx);
				}
			}
		}
		
		
		/**======其他触发事件处理======**/
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress sa = (InetSocketAddress) ctx.channel().remoteAddress();
		System.out.println("client address:"+sa);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress sa = (InetSocketAddress) ctx.channel().remoteAddress();
		roleOffLineLooseConnect(ctx);
			
		System.out.println(">>>>>"+sa+" disconnected");
	}

	
	/**
	 * 连接异常断开后 从连接缓存中获取角色ID 角色下线 
	 * @param ctx
	 */
	private void roleOffLineLooseConnect(ChannelHandlerContext ctx){
		long key = CacheService.getConnMapKey(ctx);
		if(key > 0){
			System.out.println("CONN :"+key);
			BusinessThreadPoll.roleOffline(key);
		}
	}

}
