 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: GameServer.java 
 * @Package com.lk.dragon.socket 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-12 上午8:35:30 
 * @version V1.0   
 */
package com.lk.dragon.socket;

import javax.swing.Spring;
import javax.swing.SwingWorker;

import com.lk.dragon.service.CacheService;
import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.service.WarService;
import com.lk.dragon.util.ShowLoadingDialog;
import com.lk.dragon.util.SpringBeanUtil;
import com.lk.dragon.view.ServerHandlerView;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**  
 * @Description:
 */
public class GameServer {
	/**PORT:端口号**/
	private final int port;
	
	private ChannelFuture cf;
	
	private RoleInfoService roleInfoService;
	private CacheService cacheService;
	private WarService warService;
	private SqlToolsService toolsService;
	
	
	/** 关闭标志 **/
	public static volatile boolean READY_CLOSE_SYSTEM_FLAG = false;

	public GameServer(int port){
		this.roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);
		this.cacheService = SpringBeanUtil.getBean(CacheService.class);
		this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
		this.warService =SpringBeanUtil.getBean(WarService.class);
		this.port = port;
	}
	
	public  void run(){
		//Boss线程池  只用于分发连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//工作线程池
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap sbt = new ServerBootstrap();
			sbt.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.option(ChannelOption.SO_BACKLOG, 150)
			.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new SodGameServerInitializer());
			
             try {
            	 //绑定端口 启动服务器
				cf = sbt.bind(port).sync(); 
				//初始化系统部分操作线程
			    startWorkThread();           
				//阻塞 直到channel关闭
				cf.channel().closeFuture().sync();
			} catch (Exception e) {
				e.printStackTrace();
				//Log记录
			}
		}finally{
			// 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
		}
	}
	
	/**
	 * 关闭服务器
	 */
	public void closeServer(){
		if(cf!=null)
			cf.channel().close();
	}
	
	/**
	 * 重启服务器
	 */
	public void restartServer(){
		closeServer();
		run();
	}
	
	
	private void startWorkThread() throws Exception{
        //重置当前服务器所有角色离线
		roleInfoService.resetRoleOnlineStatus();
		//各类缓存初始化
		cacheService.initCache();
		//战斗处理线程开启
		warService.startWarProducePoll();
		//构建日志SQL集合
		toolsService.startSqlTake();
		
	}
	
	public static void main(String[] args) {
		try {
			new GameServer(9010).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭服务器 弹出等待loading
	 */
	public  void closingWarService(final boolean exitWin){
		ShowLoadingDialog.getDialogInstance().setVisible(true);
		new SwingWorker<Boolean, Void>(){
			@Override
			protected Boolean doInBackground() throws Exception {
				int idleCount = 1;
				toolsService.shutdownSqlTake();
				while(warService.shutDownWarProducePoll() != 3){
					
					if(idleCount == 3)
						break;
					idleCount++;
					Thread.sleep(3000);
				}
				return true;
			}

			@Override
			protected void done() {
				ShowLoadingDialog.getDialogInstance().dispose();
				if(exitWin)
					System.exit(0);
			}
		}.execute();
	}
}
