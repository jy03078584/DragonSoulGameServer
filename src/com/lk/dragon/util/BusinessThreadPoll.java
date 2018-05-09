/** 
 *
 * @Title: BusinessThreadPoll.java 
 * @Package com.lk.dragon.util 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-6 上午9:39:47 
 * @version V1.0   
 */
package com.lk.dragon.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lk.dragon.db.domain.Role;
import com.lk.dragon.service.CacheService;
import com.lk.dragon.service.RoleInfoService;

/**
 * @Description:业务线程池 处理业务
 */
public class BusinessThreadPoll {

	/** 业务线程池对象 **/
	private final static ExecutorService bussThreadPoll = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

	/**
	 * 执行业务逻辑操作
	 * 
	 * @param bussTask
	 */
	public static void doBusinessOp(Runnable bussTask) {
		bussThreadPoll.execute(bussTask);
	}

	/**
	 * 角色非正常下线 更改角色状态
	 */
	public static void roleOffline(final long role_id) {

		 bussThreadPoll.execute(new Runnable() {
					Role role;
					@Override
					public void run() {
						role = new Role();
						role.setIs_online(Constants.ROLE_OFFLINE);
						role.setRole_id(role_id);
						role.setLast_logout_time("TEMP");
						//角色状态更改
						SpringBeanUtil.getBean(RoleInfoService.class).updateOnLineStatus(role);
						//连接缓冲池中移除连接
						CacheService.connCache.remove(role_id);
			}
		});
	}
}
