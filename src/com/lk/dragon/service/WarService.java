/** 
 *
 * @Title: WarService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-6 下午9:16:05 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IToolsDao;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.socket.GameServer;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.SpringBeanUtil;

/**
 * @Description:处理战斗业务
 */
@Service
public  class WarService {

	
	
	public static Logger logger = Logger.getLogger(WarService.class);

	/** 执行DAO方法线程池 SingleThreadExecutor保证数据持久顺序性 **/
	private static ExecutorService daoExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService warProExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService warConExecutor = Executors.newSingleThreadExecutor();
	
	/** 缓存反射得到的所有DaoImpl方法 **/
	private static ConcurrentMap<String, Method> ReflectMethodsCacheMap = new ConcurrentHashMap<String, Method>();
	// 战斗对象缓冲队列
	public static final BlockingQueue<WarProduce> warQueue = new LinkedBlockingQueue<WarProduce>(1000);
	

	
	/** 模块运行状态标志 
	 * 0：未运行 
	 * 1：战斗队列生产线程已结束 
	 * 2：战斗队列生产/处理线程均结束
	 * 3： 战斗结果入库线程结束**/
	public static volatile int STATE = 0;

	/**
	 * 开启战斗处理模块线程
	 */
	public  void startWarProducePoll() {
		//系统运行标志位
		GameServer.READY_CLOSE_SYSTEM_FLAG = false;
		warProExecutor.execute(new WarProduceProducer());
		warConExecutor.execute(new WarProduceConsumer());
		//开启战斗轮询线程
		
		logger.info("[WarService]" + ">>>> START THE WarService---->"+ DateTimeUtil.getNowTimeByFormat());
	}

	/**
	 * 关闭战斗处理模块线程
	 * 
	 * @return 模块状态标志
	 */
	public   int shutDownWarProducePoll() {
		
		GameServer.READY_CLOSE_SYSTEM_FLAG = true;
		
		//等待生产-消费模型结束 6S缓冲时间
		int idleCount = 0;
		while(STATE != 1){
			if(3 == idleCount)
				break;
			idleCount += 1;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		
		System.out.println("-----------waiting do Executor begin--------");
		//等待战斗结果入库操作线程结束
		try {
			warProExecutor.shutdown();
			warConExecutor.shutdown();
			daoExecutor.shutdown();
			if(daoExecutor.awaitTermination(10,TimeUnit.SECONDS))
				daoExecutor.shutdownNow();
		} catch (Exception e) {
			daoExecutor.shutdownNow();
			e.printStackTrace();
		}
		System.out.println("-----------waiting do Executor end--------");
		return 3;
	}

	// 变更状态标志
	public  synchronized  void changeState(int state) {
		STATE = state;
	}



	/**
	 * 反射得到方法 并置入缓存中
	 * 
	 * @param o
	 * @param methodName
	 * @param param
	 * @return
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	private  Method getMethodReflect(final Object o, final String methodName,
			final Object param) throws NoSuchMethodException, Exception {
		Method method = null;
		try {
			Class paramClazz = param.getClass();
			// 转换基本类型的Class
			if (paramClazz == Integer.class) {
				paramClazz = int.class;
			} else if (paramClazz == Long.class) {
				paramClazz = long.class;
			}else if(paramClazz == HashMap.class){
				paramClazz = Map.class;
			}
			// 获取指定方法对象
			method = o.getClass().getDeclaredMethod(methodName, paramClazz);
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Object:"+o+"    ---->methodName:"+methodName+"  --->"+param);
			
		}

		return method;
	}

	/**
	 * 执行方法
	 * 
	 * @param daoObj
	 * @param methodName
	 * @param param
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private  void invokeDaoMethod(Object daoObj, String methodName, Object param) throws Exception{
		Method method = ReflectMethodsCacheMap.get(methodName);
		// 缓存中没有该方法
		if (method == null) {
			try {
				// 反射获取方法对象 并加入缓存Map
				method = getMethodReflect(daoObj, methodName, param);
				if (method != null) {
					ReflectMethodsCacheMap.put(methodName, method);
				}
			} catch (Exception e) {
				logger.info("[WarService]-invokeDaoMethod" + ">>>> getMethodException---->"
						+ e.getMessage());
				e.printStackTrace();
				return;
			}
		}
			// 执行方法
			method.invoke(daoObj, param);
		
	}

	/**
	 * 执行daoImpl方法
	 * 
	 * @param daoObj
	 * @param methodName
	 * @param param
	 */
	private  void doWarProducdHand(final Object daoObj, final String methodName,final Object param) {
		daoExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					invokeDaoMethod(daoObj, methodName, param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	@Autowired
	private IToolsDao toolsDao;
	/**
	 * 处理战斗过程产生的SQL
	 * @param daoVos
	 */
	public void doWarProduceDaoVosHand(List<DaoVo> daoVos){
		
//		daoExecutor.execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				toolsDao.doSqlBatch(daoVos);
//			}
//		});
		toolsDao.doSqlBatch(daoVos);
	}
	
	public static void main(String[] args) {
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		for (int i = 0; i < 10; i++) {
			if(i%2 == 0){
				daoVos.add(new DaoVo("mailMap.addNewMail", new Mail("【战报】", -20l, 92l, "warInfo"+i, 2, 0),1));
			}else{
				Role r = new Role();
				r.setRole_id(92l);
				r.setRole_name("smink"+i);
				daoVos.add(new DaoVo("roleMap.updateRoleInfo", r,2));
			}
		}
		WarService se = SpringBeanUtil.getBean(WarService.class);
		se.doWarProduceDaoVosHand(daoVos);
	}
}
