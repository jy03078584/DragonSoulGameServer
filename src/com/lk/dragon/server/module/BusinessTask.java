 /** 
 *
 * @Title: BusinessTask.java 
 * @Package com.lk.dragon.server.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-6 上午9:49:08 
 * @version V1.0   
 */
package com.lk.dragon.server.module;

import com.lk.dragon.server.analysis.ServerAnalysis;

/**  
 * @Description:系统业务Task
 */
public class BusinessTask implements Runnable {

	private ServerAnalysis serverAnalysis;
	
	
	public BusinessTask(ServerAnalysis serverAnalysis) {
		super();
		this.serverAnalysis = serverAnalysis;
	}


	@Override
	public void run() {
		//解析并处理对应业务
		this.serverAnalysis.analysisRequest();
	}


	public ServerAnalysis getServerAnalysis() {
		return serverAnalysis;
	}


	public void setServerAnalysis(ServerAnalysis serverAnalysis) {
		this.serverAnalysis = serverAnalysis;
	}

	
}
