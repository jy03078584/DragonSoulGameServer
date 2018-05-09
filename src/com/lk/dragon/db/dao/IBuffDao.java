 /** 
 *
 * @Title: IBuffDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-2 下午12:13:46 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.Buff;

/**  
 * @Description:系统各类BUFF接口
 */
public interface IBuffDao {
	
	/**
	 * 获得BUFF
	 * @param buff
	 * @return
	 * @throws RuntimeException
	 */
	public Object insertNewBuff(Buff buff)throws Exception;
	
	/**
	 * BUFF结束
	 * @param buff
	 * @return
	 * @throws Exception
	 */
	public int deleteBuff(Buff buff)throws Exception;
	
	/**
	 * 查看目标对象BUFF信息
	 * @param buff
	 * @return
	 * @throws Exception
	 */
	public List<Buff> getBuffInfo(Buff buff)throws Exception;
	
	/**
	 * 检查目标对象是否处于指定BUFF效果内
	 * @param buff
	 * @return
	 * @throws Exception
	 */
	public int checkTagInBuff(Buff buff)throws Exception;
}
