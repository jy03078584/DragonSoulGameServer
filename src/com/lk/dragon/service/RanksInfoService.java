 /** 
 *
 * @Title: RanksInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-13 下午5:09:14 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IRanksDao;
import com.lk.dragon.db.domain.Ranks;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.SpringBeanUtil;

/**  
 * @Description:排行榜业务
 */

@Service
public class RanksInfoService {
	@Autowired
	private  IRanksDao ranksDao ;
	
	//---------------------------查询排行榜类型--------------------------------------//
	/**角色相关**/
	public static final int RANK_ROLE_INFO = 1;
	/**公会相关**/
	public static final int RANK_FACTION_INFO = 2;
	/**英雄相关**/
	public static final int RANK_HERO_INFO = 3;
//	1	140	sod.rank_tab	rank_type	0	综合战斗力排行榜	
//	2	141	sod.rank_tab	rank_type	1	财富排行榜	
//	3	142	sod.rank_tab	rank_type	2	兵力排行榜	
//	4	143	sod.rank_tab	rank_type	3	公会排行榜	

	
	public static final String RANK_ALL_KEY = "allRanks";
	public static final String RANK_MYSELF_KEY = "myselfRank";
	
	public RanksInfoService(){
	}
	

	/**
	 * 查询排行榜数据
	 * @param rank_type 排行榜类型
	 * @param obejct_id 己方ID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getRansInfo(int rank_type,long object_id) throws Exception{
		
		List<Ranks> rankList = null ;//全区排行
		Ranks myselfRank = null;//本人排行
		
		//获取RankCache数据
		List<Ranks> ranks =  (List<Ranks>) CacheService.rankCache.get(rank_type).getObjectValue();
		
		//缓存中无数据
		if(ranks == null || ranks.size() == 0){
			int select_type = 0;
			if(3 == rank_type){
				//查询公会排行 
				select_type = RANK_FACTION_INFO;
			}else if(0 == rank_type || 1 == rank_type || 2 == rank_type){//查询角色相关排行 
				select_type = RANK_ROLE_INFO;
			}
			HashMap<String,Integer> map = new HashMap<String,Integer>(); 
			//排行榜大类 1：角色相关 2：公会相关 3：英雄相关
			map.put("select_type", select_type);
			//排行榜子类型
			map.put("rank_type", rank_type);
			
			//查询全区排行数据
			rankList = ranksDao.getRansInfo(map);
			//数据放入缓存区
			if(rankList!= null)
				CacheService.rankCache.put(new Element(rank_type, rankList));
			//查询本人排行
			myselfRank = ranksDao.getRansInfoMySelf(new Ranks(rank_type, object_id));
		}else{
			//从缓存区中获取数据
			rankList = ranks;
			
			//查询本人排行
			for (Ranks myRank : ranks) {
				if(myRank.getObject_id() == object_id){
					myselfRank = myRank;
					break;
				}
			}
			if(myselfRank == null){
				//缓存中未查找到本人排名
				//查询本人排行
				myselfRank = ranksDao.getRansInfoMySelf(new Ranks(rank_type, object_id));
			}
		}
		
		//构建返回数据结构
		HashMap<String,Object> resMap = new HashMap<String,Object>(); 
		resMap.put(RANK_ALL_KEY, rankList);
		resMap.put(RANK_MYSELF_KEY, myselfRank);
		return resMap;
	}
	
	/**
	 * 获取自己排行
	 * @param rank_type 排行类型
	 * @param object_id 已方ID
	 * @return
	 * @throws Exception
	 */
	public Ranks getRansInfoMySelf(int rank_type,long object_id) throws Exception{
		Ranks myRank = null;
		myRank = ranksDao.getRansInfoMySelf(new Ranks(rank_type, object_id));
		return myRank;
	}
	
}
