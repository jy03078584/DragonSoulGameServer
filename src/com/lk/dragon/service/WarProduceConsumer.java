/** 
 *
 * @Title: WarProduceConsumer.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-16 下午3:37:04 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.List;


import com.lk.dragon.db.dao.impl.ArmsDeployDaoImpl;
import com.lk.dragon.db.dao.impl.RolePropsDaoImpl;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.util.SpringBeanUtil;

/**
 * @Description:战斗队列消费者线程
 */
public class WarProduceConsumer implements Runnable {

	//private WarProduceInfoService warProduceInfoService;
	private WarProduceInfoServiceBatch warProduceInfoServiceBatch;
	private WarService warService;

	public WarProduceConsumer(){
		this.warProduceInfoServiceBatch = SpringBeanUtil.getBean(WarProduceInfoServiceBatch.class);
		this.warService = SpringBeanUtil.getBean(WarService.class);
	}
	
	@Override
	public void run() {
		WarProduce warProduce = null;
		while (true) {
			// 从缓存区中获取战斗对象
			// 处理战斗过程
			List<DaoVo> vos;
			try {
				warProduce = WarService.warQueue.take();
				vos = warProduceInfoServiceBatch.pollWarTeamInfo(warProduce);
			} catch (Exception e) {
				/*----------处理异常队列-------------*/
				vos = new ArrayList<DaoVo>();
				//删除队列 英雄复位
				String condition = " t.is_free = 1,t.now_cityid = t.city_id where t.role_hero_id in (select t1.role_hero_id from sod.team_hero_tab t1 where t1.team_id = "+warProduce.getWar_team_id()+")";
				vos.add(new DaoVo("rolePropsMap.updateHerosStatus", condition, 2));
				vos.add(new DaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(), 3));
				//缓存删除
				CacheService.warTeamCache.remove(warProduce.getWar_team_id());
				WarService.logger.info("[WarService-WarProduceConsumer]"+ ">>>> Exception---->ROLEID:" + warProduce.getRole_id()+"--->STATUS:"+warProduce.getStatus()+"---->WARTYPE:"+warProduce.getWar_type());
				WarService.logger.error("WarProduceConsumer-Exception", e);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

			warService.doWarProduceDaoVosHand(vos);
		}
		//变更状态标识
		//warService.changeState(2);
	}

}
