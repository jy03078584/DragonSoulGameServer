 /** 
 *
 * @Title: IMarketPropsDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 上午10:46:59 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.MarketProps;

/**  
 * @Description:商城道具列表接口
 */
public interface IMarketPropsDao {
	
	/**
	 * 获取游戏商场道具列表
	 * @param conditionMap 查询条件     
	 * 		  key值:props_name_sql--->按名称查询道具  为空时查询全部道具
	 * 		  key值:props_type_sql--->按类型查询道具  为空时查询全部道具
	 * @return
	 * @throws Exception
	 */
	public List<MarketProps> getAllMarketPropsList(Map<String,Object> conditionMap) throws Exception;
}
