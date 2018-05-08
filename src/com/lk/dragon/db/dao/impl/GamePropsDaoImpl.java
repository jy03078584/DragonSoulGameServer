 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: GamePropsDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-16 上午11:10:13 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IGamePropsDao;
import com.lk.dragon.db.domain.GameProps;

/**  
 * @Description:
 */
@Repository("gamePropsDao")
public class GamePropsDaoImpl extends BaseSqlMapDao implements IGamePropsDao {



	@SuppressWarnings("unchecked")
	@Override
	public List<GameProps> getAllPropsList(GameProps gameProps)throws Exception {
		return getSqlMapClientTemplate().queryForList("gamePropsMap.getAllPropsList", gameProps);
	}

	@Override
	public GameProps getPropsInfo(long props_id) throws Exception {
		return (GameProps) getSqlMapClientTemplate().queryForObject("gamePropsMap.getPropsInfo",props_id);
	}

}
