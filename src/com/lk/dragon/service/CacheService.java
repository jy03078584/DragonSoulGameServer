/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: CacheService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-13 下午2:19:35 
 * @version V1.0   
 */
package com.lk.dragon.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Direction;
import net.sf.ehcache.search.Result;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IRanksDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.impl.RanksDaoImpl;
import com.lk.dragon.db.dao.impl.RoleDaoImpl;
import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.CityBuild;
import com.lk.dragon.db.domain.Ranks;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.server.domain.ConnDomain;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.SpringBeanUtil;

/**
 * @Description:缓存器
 * 
 */
@Service
public class CacheService {

	/*---------缓存器-----------*/
	private final static String ROLE_CACHE = "roleCache";
	private final static String BUILD_LEV_UP_CACHE = "buildLevUpCache";
	private final static String ARM_INFO_CACHE = "armInfoCache";
	private final static String RANK_INFO_CACHE = "rankInfoCache";
	private final static String WAR_TEAM_CACHE = "warTeamCache";
	// private final static String CONN_CHANNEL_CACHE = "connChannelCache";

	public static Logger logger = Logger.getLogger(CacheService.class);
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private ICityBuildDao cityBuildDao;
	@Autowired
	private IArmsDeployDao armsDeployDao;
	@Autowired
	private IRanksDao ranksDao;

	// private static ConcurrentMap<, V>

	private static CacheManager cacheManager = CacheManager
			.create("src/ehcache.xml");

	public static Cache roleCache = cacheManager.getCache(ROLE_CACHE);
	public static Cache buildCache = cacheManager.getCache(BUILD_LEV_UP_CACHE);
	public static Cache armsCache = cacheManager.getCache(ARM_INFO_CACHE);
	public static Cache rankCache = cacheManager.getCache(RANK_INFO_CACHE);
	public static Cache warTeamCache = cacheManager.getCache(WAR_TEAM_CACHE);
	public static ConcurrentMap<Long, ConnDomain> connCache = new ConcurrentHashMap<Long, ConnDomain>();
	public static Map<Integer, Integer> roleUpExpCache = new HashMap<Integer, Integer>();

	// public static Cache connChannelCache =
	// cacheManager.getCache(CONN_CHANNEL_CACHE);

	public void initCache() throws Exception {
		System.out.println("-------------[初始化 游戏各类缓存开始]----------------");
		// 初始化roleCache
		initRoleCache();
		//初始化角色所需升级经验
		initRoleUpExp();
		// 初始化buildCache
		initBuildLevUpCache();
		// 初始化armCache
		intiArmsCache();
		// 初始化排行榜
		initRankCache(ranksDao);
		//初始化战斗队列
		initWarTeamCache();
		System.out.println("-------------[初始化 游戏各类缓存结束]----------------");

	}

	public static void main(String[] args) {
		List<WarProduce> list = new ArrayList<WarProduce>();
		//list.add(null);
		System.out.println(list.size());
//		WarProduce produce;
//		Cache warTeamChache = CacheService.cacheManager
//				.getCache("warTeamCache");
//		for (int i = 0; i < 300; i++) {
//			produce = new WarProduce();
//			produce.setWar_team_id((long) i);
//			int j = 1;
//			if (i % 2 == 0)
//				j = 2;
//			produce.setWar_type(j);
//			produce.setTag_x(i);
//			produce.setTag_y(i);
//			String time = i < 10 ? "0" + i : i + "";
//			produce.setEnd_time("2015-06-21 23:29:" + time);
//			warTeamChache.put(new Element(produce.getWar_team_id(), produce));
//		}
//		WarProduce produce2 = (WarProduce)warTeamCache.get(9l).getObjectValue();
//		System.out.println(produce2.getEnd_time());
//		produce2.setEnd_time("xxxxxxxxxxxxxxxxxxx");
//		String s1 = ((WarProduce)warTeamCache.get(9l).getObjectValue()).getEnd_time();
//		WarProduce produce3 = new WarProduce();
//		produce3.setWar_team_id(9l);
//		produce3.setEnd_time("uuuuuuuuuuuuuuu");
//		produce3.setTag_x(55);
//		produce3.setTag_x(55);
//		
//		warTeamCache.put(new Element(9l, produce3));
//		System.out.println(s1);
//		produce3.setEnd_time("---------------");
//		String s2 = ((WarProduce)warTeamCache.get(9l).getObjectValue()).getEnd_time();
//		System.out.println(s2);
		
	}

	
	/**
	 * 初始化角色升级经验
	 */
	private void initRoleUpExp(){
		
		List<Role> roleUpExp = roleDao.getRoleUpExp();
		if(roleUpExp != null && roleUpExp.size() > 0 ){
			for (Role role : roleUpExp) {
				roleUpExpCache.put(role.getLev(), role.getUp_exp());
			}
		}
	}
	
	/**
	 * 初始化战斗队列缓存
	 */
	private void initWarTeamCache(){
		List<WarProduce> warProduces = armsDeployDao.initWarTeamCache();
		Element element;
		if(warProduces!= null && warProduces.size() > 0 ){
			for (WarProduce warProduce : warProduces) {
				element = new Element(warProduce.getWar_team_id(), warProduce);
				CacheService.warTeamCache.put(element);
			}
		}
	}
	
	/**
	 * 新建队列/队列状态变更 
	 * 更新缓存
	 */
	public void putNewWarTeamToCache(WarProduce warProduce){
		//获取指定Key的write锁
		CacheService.warTeamCache.acquireWriteLockOnKey(warProduce.getWar_team_id());  
		   try { 
			  //更新缓存
			   CacheService.warTeamCache.put(new Element(warProduce.getWar_team_id(),warProduce));  
		  } finally {
			  //释放write锁
			  CacheService.warTeamCache.releaseWriteLockOnKey(warProduce.getWar_team_id());
		     //logger.info("[CacheService putNewWarTeamToCache]>>>>" + warProduce.toString());
		 }  

	}
	
	/**
	 * 从缓存中移除无效队列
	 * @param keyId
	 */
	public void removeWarTeamFromCache(long keyId){
		CacheService.warTeamCache.remove(keyId);
	}
	
	/**
	 * 获取连接KEY
	 * 
	 * @param ctx
	 * @return
	 */
	public static long getConnMapKey(ChannelHandlerContext ctx) {

		Iterator<Entry<Long, ConnDomain>> iterator = connCache.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<Long, ConnDomain> entry = iterator.next();
			if (entry.getValue().getCtx() == ctx) {
				return entry.getKey();
			}
		}

		return -1l;
	}

	/**
	 * 初始化角色缓存
	 * 
	 * @throws Exception
	 */
	private void initRoleCache() throws Exception {
		// 查询最近下线的前700名玩家信息缓存
		putRoleToCache(roleDao.initRoleCache());
	}

	// 更新角色缓存 6分钟执行一次
	@Scheduled(cron = "0 6/4 * * * *")
	private void refreshRoleCache() {
		try {
			System.out.println(Thread.currentThread().getName() + "---->更新角色缓存");
			roleCache.removeAll();
			putRoleToCache(SpringBeanUtil.getBean(RoleDaoImpl.class)
					.refreshRoleCache());
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	// 角色List放入缓存
	private void putRoleToCache(List<Role> roles) {
		Element element;
		for (Role role : roles) {

			element = new Element(role.getRole_id(), role);
			roleCache.put(element);
		}
	}

	/**
	 * 初始化各类建筑升级后增益数值
	 * 
	 * @throws Exception
	 */
	private void initBuildLevUpCache() throws Exception {
		List<CityBuild> builds = cityBuildDao.getResourceYieldOut();
		Element element;
		for (CityBuild cityBuild : builds) {
			element = new Element(cityBuild.getBuild_type() + ""
					+ cityBuild.getLev(), cityBuild);
			buildCache.put(element);
		}
	}

	/**
	 * 初始化兵种信息
	 */
	private void intiArmsCache() {
		List<ArmsDeploy> arms = armsDeployDao.getAllArmsInfo();
		Element element;
		for (ArmsDeploy arm : arms) {
			element = new Element(arm.getArm_id(), arm);
			armsCache.put(element);
		}
	}

	/**
	 * 初始化排行榜
	 * 
	 * rank_type 0 综合战斗力排行榜 rank_type 1 财富排行榜 rank_type 2 兵力排行榜 rank_type 3
	 * 公会排行榜
	 * 
	 * @throws Exception
	 */
	private void initRankCache(IRanksDao ranksDao) throws Exception {
		Map<String, Integer> rankMap = new HashMap<String, Integer>();
		rankMap.put("select_type", 1);// 角色类排行
		rankMap.put("rank_type", 0);
		// 综合排行榜
		List<Ranks> complexRanks = ranksDao.getRansInfo(rankMap);
		// 财富排行榜
		rankMap.put("rank_type", 1);
		List<Ranks> wealthRanks = ranksDao.getRansInfo(rankMap);
		// 兵力排行榜
		rankMap.put("rank_type", 2);
		List<Ranks> armsRanks = ranksDao.getRansInfo(rankMap);

		// 工会排行榜
		rankMap.put("select_type", 2);// 公会类排行
		rankMap.put("rank_type", 3);
		List<Ranks> gulidRanks = ranksDao.getRansInfo(rankMap);

		// 以Rank_typ为KEY 存入rankCache
		rankCache.put(new Element(0, complexRanks));
		rankCache.put(new Element(1, wealthRanks));
		rankCache.put(new Element(2, armsRanks));
		rankCache.put(new Element(3, gulidRanks));
	}

	// 每天凌晨1点更新排行榜
	@Scheduled(cron = "0 0 1 * * *")
	private void refreshRankCache() {
		try {
			logger.info("[CacheService refreshRankCache]>>>>更新排行榜缓存");
			cacheManager.getCache(RANK_INFO_CACHE).removeAll();
			initRankCache(SpringBeanUtil.getBean(RanksDaoImpl.class));
		} catch (Exception e) {
			logger.info("[CacheService refreshRankCache]>>>>" + e.getMessage());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
