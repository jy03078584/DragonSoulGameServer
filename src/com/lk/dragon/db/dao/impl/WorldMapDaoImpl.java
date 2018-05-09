/**
 *
 *
 * 文件名称： WorldMapDaoImpl.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-12-15 下午3:39:04
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IWorldMapDao;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;

@Repository("worldMapDao")
public class WorldMapDaoImpl extends BaseSqlMapDao implements IWorldMapDao
{


    @SuppressWarnings("unchecked")
    @Override
    public List<WorldMap> getWorldMapPoint(WorldMap worldMap) throws Exception
    {
        return getSqlMapClientTemplate().queryForList("worldMap.getWorldMapPoints", worldMap);
    }

    @Override
    public City getCityInfo(City city) throws Exception
    {
        return (City) getSqlMapClientTemplate().queryForObject("worldMap.selectCityInfo", city);
    }

	@Override
	public List<WildSrc> getWildSrcInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().queryForList("worldMap.getWildSrcInfo",condition);
	}

	@Override
	public WorldMap getRandomWildInfo(Map<String, Integer> point)throws Exception {
		return (WorldMap) getSqlMapClientTemplate().queryForObject("worldMap.getRandomWildInfo", point);
	}

	@Override
	public int updateWildSrcInfo(WildSrc wildSrc) throws Exception {
		return getSqlMapClientTemplate().update("worldMap.updateWildSrcInfo", wildSrc);
	}
    
    

}
