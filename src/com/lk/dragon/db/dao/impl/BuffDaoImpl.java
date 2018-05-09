 /** 
 *
 * @Title: BuffDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-2 下午12:14:38 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.IBuffDao;
import com.lk.dragon.db.domain.Buff;

/**  
 * @Description:
 */
@Repository("buffDao")
public class BuffDaoImpl extends BaseSqlMapDao implements IBuffDao{

	@Override
	public Object insertNewBuff(Buff buff) throws Exception {
		return getSqlMapClientTemplate().insert("buffMap.insertNewBuff", buff);
	}

	@Override
	public int deleteBuff(Buff buff) throws Exception {
		return getSqlMapClientTemplate().delete("buffMap.deleteBuff", buff);
	}

	@Override
	public List<Buff> getBuffInfo(Buff buff) throws Exception {
		return  getSqlMapClientTemplate().queryForList("buffMap.getBuffInfo", buff);
	}

	@Override
	public int checkTagInBuff(Buff buff) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("buffMap.checkTagInBuff", buff);
	}
	
}
