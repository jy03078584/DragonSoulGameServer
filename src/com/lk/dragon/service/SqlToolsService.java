/** 
 *
 * @Title: ToolsService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-4-28 下午12:00:53 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IToolsDao;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.socket.GameServer;

/**
 * @Description:ToolsService
 */ 
@Service
public class SqlToolsService {

	/** 执行SQL线程数 **/
	private final static int sqlTakeThreadCount = 2;
	/** SQL队列 **/
	public  final static BlockingQueue<DaoVo> sqlQueue = new LinkedBlockingQueue<DaoVo>(500);
	private static ExecutorService sqlTakeExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService sqlBatchExecuExecutor = Executors.newFixedThreadPool(sqlTakeThreadCount);

	private Logger logger = Logger.getLogger(SqlToolsService.class);

	/**
	 * 角色详细操作 插入日志表
	 * 
	 * @param tool
	 * @return
	 * @throws Exception
	 */
	public void addNewLogInfo(Tools tool) {

		// 获取当前日份
		 Calendar cal=Calendar.getInstance();
		 int today = cal.get(Calendar.DAY_OF_MONTH);
		 //构建分区表 分区键
		 String patition_key = today < 10 ? "0"+today : today+"";
		 tool.setPartitionkey(patition_key);
			 // 构建DaoVo对象 放入SQL队列
			 try {
				sqlQueue.put(new DaoVo("toolsMap.addNewLogInfo", tool,1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 
		
	}

	@Autowired
	private IToolsDao toolsDao;

	/**
	 * 批量执行SQL
	 * 
	 * @param daoVo
	 */
	public void doSqlByBatch(final List<DaoVo> daoVos) {
		sqlBatchExecuExecutor.execute(new Runnable() {
			@Override
			public void run() {
				toolsDao.doSqlBatch(daoVos);
			}
		});
	}

	/**
	 * 获取队列中SQL语句 构建List<DaoVo>
	 */
	private void pollSqlsBatch() {
		sqlTakeExecutor.execute(new Runnable() {
			List<DaoVo> daoList;
			boolean newListFlag = false;

			@Override
			public void run() {
				try {
					daoList = new ArrayList<DaoVo>();
					while (true) {
						if (GameServer.READY_CLOSE_SYSTEM_FLAG == true) {
							// 准备关闭服务器 执行集合中剩余SQL
							if (daoList != null && daoList.size() > 0)
								doSqlByBatch(daoList);
							break;
						}
						if (newListFlag){
							daoList = new ArrayList<DaoVo>();
							newListFlag = false;
						}
						
						//DaoVo daoVo = sqlQueue.take();
						DaoVo daoVo = sqlQueue.poll(15, TimeUnit.SECONDS);
						if(daoVo == null){
							//15s内未取得新的SQL
							if(daoList.size() > 0){
								doSqlByBatch(daoList);
								newListFlag = true;
							}
						}else{
							daoList.add(daoVo);
							if (daoList.size() > 5) {
								// SQL满5条 批量执行
								doSqlByBatch(daoList);
								newListFlag = true;
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 队列中取出日志SQL 构建List<DaoVo>
	public void startSqlTake() {
		pollSqlsBatch();
	}

	// 关闭SqlTool
	public void shutdownSqlTake() {
		sqlBatchExecuExecutor.shutdown();
		try {
			//10S延迟时间
			sqlBatchExecuExecutor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			sqlBatchExecuExecutor.shutdownNow();
			e1.printStackTrace();
		}
		sqlTakeExecutor.shutdown();

	}
}
