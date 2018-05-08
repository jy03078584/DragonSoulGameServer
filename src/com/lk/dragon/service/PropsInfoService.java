 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: PropsInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-16 下午2:45:18 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IGamePropsDao;
import com.lk.dragon.db.domain.GameProps;

/**  
 * @Description:游戏道具信息相关业务
 */
@Service
public class PropsInfoService {
	@Autowired
	private IGamePropsDao gamePropsDao ;
	private List<GameProps> props;
	
	public PropsInfoService(){
	}
	
	/**
	 * 
	 * @param gameProps 查询条件对象可按道具ID/道具类型查询指定道具列表  为null时查询全部道具
	 * @return
	 */
	public List<GameProps> getProps(GameProps gameProps){
		props = null;
		try {
			props = gamePropsDao.getAllPropsList(gameProps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return props;
	}

}
