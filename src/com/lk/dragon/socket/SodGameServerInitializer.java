 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: SodGameServerInitializer.java 
 * @Package com.lk.dragon.socket 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-12 上午8:37:46 
 * @version V1.0   
 */
package com.lk.dragon.socket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**  
 * @Description:
 */
public class SodGameServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
				//获取通道pipeline
				ChannelPipeline pipeline = ch.pipeline();
				/**----添加各层处理器-----**/
				//IdleStateHandler空闲处理器  处理心跳机制 
				pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
				/**添加数据编码/解码Hanlder**/
				//TCP组包 格式 :(包长度)+包内容
				//LengthFieldBasedFrameDecoder
				//pipeline.addLast(new LengthFieldPrepender(4));
				//pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
				//pipeline.addLast(new MyLengthPrendHandler());
				pipeline.addLast(new DelimiterBasedFrameDecoder(65536, Unpooled.wrappedBuffer("<msg>".getBytes())));
				pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
				/**添加服务器业务逻辑Handler**/
				pipeline.addLast("bussiness",new ServerBusinessHandler());
				
				
	}

}
