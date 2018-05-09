 /** 
 *
 * @Title: IFactionDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-24 下午3:40:20 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Tools;

/**  
 * @Description:系统工具接口
 */
public interface IToolsDao {
	
	
	/**
	 * 批量处理SQL：DaoVo需指定 对应ibatis配置namespace.id及param  
	 * @param daoVos
	 */
	public void doSqlBatch(List<DaoVo> daoVos);
}
