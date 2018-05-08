/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： SocketUtil.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-5 下午4:47:53
 */
package com.lk.dragon.util;

import io.netty.channel.ChannelHandlerContext;

public class SocketUtil {
	/**
	 * 将信息回写给客户端
	 * 
	 * @param nbc
	 * @param responseStr
	 */
	public static void responseClient(ChannelHandlerContext ctx,
			String responseStr) {
		// 将信息写回客户端
		ctx.writeAndFlush(ConvertComminuEdian.constrInfoToClient(responseStr.getBytes().length, responseStr));
	}
}
