 /** 
 *
 * @Title: MarketInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 上午11:58:39 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IMarketPropsDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.MarketProps;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;

/**  
 * @Description:商城信息业务层
 */
@Service
public class MarketInfoService {
	@Autowired
	private  IMarketPropsDao marketPropsDao;
	@Autowired
	private  IRolePropsDao rolePropsDao;
	@Autowired
	private  IRoleDao roleDao ;
	
	public static final String FOOD_KEY = "food";
	public static final String WOOD_KEY = "wood";
	public static final String STONE_KEY = "stone";
	
	public MarketInfoService(){
	}
	
	/**
	 * 获取商城列表
	 * @param props_name 商城道具名称 可为null
	 * @param props_type 商城道具类型 可为null
	 * @return
	 */
	public List<MarketProps> getMarketProps(String props_name,Integer props_type){
		List<MarketProps> marketProps = null;
		HashMap<String,Object> conditionMap = new HashMap<String, Object>();
		if(props_name != null)
			conditionMap.put("props_name_sql", props_name.trim());
		if(props_type != null)
			conditionMap.put("props_type_sql", props_type);
		try {
			marketProps = marketPropsDao.getAllMarketPropsList(conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return marketProps;
	}

	
	/**
	 * 购买商城物品
	 * @param marketProps  可使用已有构造函数构造 
	 * @return TRUE:购买成功  FALSE：购买失败
	 * @throws Exception 
	 */
	public int buyMarketProps(MarketProps marketProps) throws Exception{
		int resFlag = -1;
		
		//构造买家-道具对象
		RoleProps roleProps = new RoleProps();
		roleProps.setProps_count(marketProps.getBuy_counts());
		roleProps.setRole_id(marketProps.getBuyer_id());
		roleProps.setProps_id(marketProps.getProps_id());
		
		//构造付款后买家对象
		Role role = new Role();
			Role baseRole = roleDao.selectRolesByRoleId(marketProps.getBuyer_id());
			role.setDiamon(baseRole.getDiamon() - marketProps.getUse_diamon());
			role.setGold(baseRole.getGold() - marketProps.getUse_gold());
			role.setRole_id(marketProps.getBuyer_id());
			//物品进入买家包裹
			resFlag = rolePropsDao.callAddRoleProps(roleProps);
			//更新玩家财富
			roleDao.updateRoleInfo(role);
			
			
		
		return resFlag;
	}
	
	/**
	 * 玩家销售资源 换取金币
	 * @param backMap
	 * @param role_id
	 * @return
	 * @throws Exception 
	 */
	public int backResource(Map<String,Integer> backMap,long role_id,int gold) throws Exception{
		
		//查询角色当前资源
		Role roleRes = roleDao.selectRolesByRoleId(role_id);
		
		//角色当前剩余资源
		int src_food = roleRes.getFood();
		int src_wood = roleRes.getWood();
		int src_stone = roleRes.getStone();
		int src_gold = roleRes.getGold();
		
		//本次兑换使用资源数
		int use_food = backMap.get(FOOD_KEY);
		int use_wood = backMap.get(WOOD_KEY);
		int use_stone = backMap.get(STONE_KEY);
		
		//交易后资源数
		int last_food = src_food - use_food;
		int last_wood = src_wood - use_wood;
		int last_stone = src_stone - use_stone;
		int last_gold = src_gold + gold;
		//资源不足
		if(last_food < 0 || last_wood < 0 || last_stone < 0 )
			return 0;
		
		//更新角色资源
		roleRes.setRole_id(role_id);
		roleRes.setFood(last_food);
		roleRes.setWood(last_wood);
		roleRes.setStone(last_stone);
		roleRes.setGold(last_gold);
		
		return roleDao.updateRoleInfo(roleRes);
		
	}
	
	
}
