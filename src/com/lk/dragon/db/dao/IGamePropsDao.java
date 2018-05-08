 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IGamePropsDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: 道具Dao 接口 
 * @author XiangMZh   
 * @date 2014-9-16 上午11:08:51 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.GameProps;

/**  
 * @Description:道具Dao 接口
 */
public interface IGamePropsDao {
	
	/**
	 * 获取当前可购买的道具
	 * @param gameProps
	 * @return
	 * @throws Exception
	 */
	public List<GameProps> getAllPropsList(GameProps gameProps)throws Exception;
	
	/**
	 * 查看道具基本信息
	 * @param props_id
	 * @return
	 * @throws Exception
	 */
	public GameProps getPropsInfo(long props_id)throws Exception;
}
