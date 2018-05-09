 /** 
 *
 * @Title: IMapDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午6:03:37 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.LkMap;

/**  
 * @Description:世界地图接口
 */
public interface IMapDao {
	
	
	/**
	 * 获取地图剩余可用点
	 * @return
	 * @throws Exception
	 */
	public List<LkMap> findEnablePoint()throws Exception;
	
	/**
	 * 更改地图点元素
	 * @param lkMap
	 * @return
	 * @throws Exception
	 */
	public int updateMapItem(LkMap lkMap)throws Exception;
	
	/**
	 * 查看当前坐标点元素类型
	 * @param lkMap
	 * @return
	 * @throws Exception
	 */
	public int checkPointItem(LkMap lkMap)throws Exception;
}
