 /** 
 *
 * @Title: IRanksDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-13 下午4:39:50 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Ranks;

/**  
 * @Description:排行榜接口
 */
public interface IRanksDao {

	/**
	 * 查询排行榜数据
	 * @param rank_type
	 * @return
	 */
	public List<Ranks> getRansInfo(Map<String,Integer> rankMap)throws Exception;
	
	/**
	 * 查询本人排行
	 * @param ranks
	 * @return
	 * @throws Exception
	 */
	public Ranks getRansInfoMySelf(Ranks ranks)throws Exception;
	
}
