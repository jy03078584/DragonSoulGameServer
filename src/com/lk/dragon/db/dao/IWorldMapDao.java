/**
 *
 *
 * 文件名称： IWorldMapDao.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-12-15 下午3:37:47
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;

public interface IWorldMapDao
{
    /**
     * 查询一定范围的世界地图坐标点
     * @param worldMap
     * @return
     */
    public List<WorldMap> getWorldMapPoint(WorldMap worldMap)throws Exception;
    
    
    /**
     * 查询当前坐标点城镇信息
     * @param city
     * @return
     * @throws Exception
     */
    public City getCityInfo(City city) throws Exception;
    
    /**
     * 查询资源点信息
     * @param condition
     * @return
     * @throws Exception
     */
    public List<WildSrc> getWildSrcInfo(String condition) throws Exception;
    
    /**
     * 获取随机野怪信息
     * @param point
     * @return
     * @throws Exception
     */
    public WorldMap getRandomWildInfo(Map<String,Integer> point)throws Exception;
    
    /**
     * 变更资源点信息
     * @param wildSrc
     * @return
     * @throws Exception
     */
    public int updateWildSrcInfo(WildSrc wildSrc)throws Exception;
}
