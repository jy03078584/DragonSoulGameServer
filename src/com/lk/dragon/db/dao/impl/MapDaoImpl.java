 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: MapDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午6:04:10 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IMapDao;
import com.lk.dragon.db.domain.LkMap;

/**  
 * @Description:
 */
@Repository("mapDao")
public class MapDaoImpl extends BaseSqlMapDao implements IMapDao {



	@SuppressWarnings("unchecked")
	@Override
	public List<LkMap> findEnablePoint() throws Exception {
		return getSqlMapClientTemplate().queryForList("cityBuildMap.findEnablePoint");
	}

	@Override
	public int updateMapItem(LkMap lkMap) throws Exception {
		return getSqlMapClientTemplate().update("cityBuildMap.updateMapItem", lkMap);
	}

	@Override
	public int checkPointItem(LkMap lkMap) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("cityBuildMap.checkPointItem", lkMap);
	}

}
