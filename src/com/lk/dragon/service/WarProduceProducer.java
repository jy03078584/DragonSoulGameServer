 /** 
 *
 * @Title: WarProduceProducer.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-16 下午3:28:32 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Direction;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.impl.ArmsDeployDaoImpl;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.socket.GameServer;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.SpringBeanUtil;

/**  
 * @Description:战斗队列生产者线程
 */
public class WarProduceProducer implements Runnable {
	
	private IArmsDeployDao armsDeployDao;
	private WarService warService;
	/** 最后一次轮训到队列的结束时间 **/
	private static String LAST_END_TIME = "";
	
	/**用于查询缓存对象的属性**/
	private static final Attribute<String> endTime =  CacheService.warTeamCache.getSearchAttribute("end_time");
	private static final Attribute<Integer> warType =  CacheService.warTeamCache.getSearchAttribute("war_type");
	
	public WarProduceProducer(){
		armsDeployDao = SpringBeanUtil.getBean(ArmsDeployDaoImpl.class);
		warService = SpringBeanUtil.getBean(WarService.class);
	}
	
	
	/**
	 * 避免重复选取同一队列 该操作只在单线程操作 如需多线程 需对LAST_END_TIME加锁等操作
	 */
	@Override
	public void run() {
		List<WarProduce> warTeams;
		while (!GameServer.READY_CLOSE_SYSTEM_FLAG) {
			try {
				//System.out.println("================轮询缓存===============");
				// 战斗对象集合
				warTeams = getWarTeamsFromCache();
				if (warTeams != null && warTeams.size() > 0) {
					for (WarProduce warProduce : warTeams) {
						// 战斗对象放入缓存队列
						WarService.warQueue.put(warProduce);
					}
				}else{
					//本轮未取得数据 休息3S后继续
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		warService.changeState(1);
		// 准备关闭生产线程
		WarService.logger.info("[WarService]" + ">>>> Thread warProThd END---->"+ DateTimeUtil.getNowTimeByFormat());
	}

	/**
	 * 从warTeamCache中获取 满足处理条件的队列
	 */
	private List<WarProduce> getWarTeamsFromCache(){
		//从队列中选取 endtime<now && endtime>lastEndTime的队列 并顺排序
		 Results results = CacheService.warTeamCache.createQuery()
										.addCriteria(endTime.le(DateTimeUtil.getNowTimeByFormat())
										.and(endTime.gt(LAST_END_TIME)))
										.includeValues()
										.addOrderBy(endTime, Direction.ASCENDING)
										.execute();
		try{
			
			 if(results == null || results.size() == 0)
				 return null;
			 
			 //转WarProduce集合
			 List<Result> resList = results.all();
			 List<WarProduce> warList = new ArrayList<WarProduce>();
			 for (Result result : resList) {
				 System.out.println(((WarProduce)result.getValue()).getWar_team_id());
				warList.add((WarProduce)result.getValue());
			}
			 //更新轮询到的最后一个队列的结束时间
			 LAST_END_TIME = warList.get(warList.size() - 1).getEnd_time();
			 
			 return warList;
		}finally{
			results.discard();
		}
	
	}
	
//--------------------------------------------------V1.0 轮询数据库获取方式获取满足条件队列------------------------//
//	@Override
//	public void run() {
//		List<WarProduce> warTeams;
//		while (!GameServer.READY_CLOSE_SYSTEM_FLAG) {
//			try {
//				// 战斗对象集合
//				warTeams = armsDeployDao.selectArriveTagWarTeam2(LAST_END_TIME);
//				if (warTeams != null && warTeams.size() > 0) {
//					// 重置最后一次轮询到队列结束时间
//					LAST_END_TIME = warTeams.get(warTeams.size() - 1).getEnd_time();
//					for (WarProduce warProduce : warTeams) {
//						// 战斗对象放入缓存队列
//						WarService.warQueue.put(warProduce);
//					}
//				}else{
//					Thread.sleep(30000);
//				}
//			} catch (Exception e) {
//				try {
//					// 线程休眠1S 防止死循环
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//				e.printStackTrace();
//			}
//		}
//		warService.changeState(1);
//		// 准备关闭生产线程
//		WarService.logger.info("[WarService]" + ">>>> Thread warProThd END---->"+ DateTimeUtil.getNowTimeByFormat());
//	}

}
