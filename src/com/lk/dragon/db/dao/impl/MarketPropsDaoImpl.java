 /** 
 *
 * @Title: MarketPropsDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 上午10:48:16 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IMarketPropsDao;
import com.lk.dragon.db.domain.MarketProps;

/**  
 * @Description:
 */
@Repository("marketPropsDao")
public class MarketPropsDaoImpl extends BaseSqlMapDao implements IMarketPropsDao {



	@SuppressWarnings("unchecked")
	@Override
	public List<MarketProps> getAllMarketPropsList(Map<String, Object> conditionMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("marketPropsMap.getAllMarketPropsList", conditionMap);
	}

}
