 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: DropRateUtil.java 
 * @Package com.lk.dragon.util 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-4-8 上午10:45:30 
 * @version V1.0   
 */
package com.lk.dragon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.util.SpringBeanUtil;

/**  
 * @Description:游戏物品掉落
 */
@Service
public class DropRateService {
	
	@Autowired
	private IRolePropsDao rolePropsDao;
	
	public DropRateService(){}

	/**
	 * 获取NPC掉落概率基数
	 * @param npcid
	 * @return
	 */
	public int getNpcDropRateMax(int npcid){
		int rateMax = 100;
		try {
			rateMax = rolePropsDao.getNpcDropRateMax(npcid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rateMax;
	}
	

}
