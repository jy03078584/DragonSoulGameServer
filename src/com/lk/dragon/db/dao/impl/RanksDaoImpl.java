 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RanksDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-13 下午4:40:36 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.IRanksDao;
import com.lk.dragon.db.domain.Ranks;

/**  
 * @Description:
 */
@Repository("ranksDao")
public class RanksDaoImpl extends BaseSqlMapDao implements IRanksDao {



	@Override
	public List<Ranks> getRansInfo(Map<String,Integer> rankMap)throws Exception {
		return getSqlMapClientTemplate().queryForList("ranksMap.getRansInfo", rankMap);
	}

	@Override
	public Ranks getRansInfoMySelf(Ranks ranks) throws Exception {
		return (Ranks) getSqlMapClientTemplate().queryForObject("ranksMap.getRansInfoMySelf", ranks);
	}


}
