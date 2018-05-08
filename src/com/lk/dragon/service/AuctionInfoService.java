/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: AuctionInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 上午9:24:46 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IAuctionDao;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Auction;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.util.Constants;

/**
 * @Description:拍卖行信息业务层
 */
@Service
public class AuctionInfoService {
	
	@Autowired
	private  IAuctionDao auctionDao;
	@Autowired
	private  IRolePropsDao rolePropsDao;
	@Autowired
	private  IMailDao mailDao;
	@Autowired
	private  IRoleDao roleDao;
	
	@Autowired
	private SqlToolsService sqlToolsService;
	
	




	public AuctionInfoService() {
		super();
	}



	/**
	 * 查询指定商品列表
	 * @param props_name 玩家输入商品名称  可为null
	 * @param props_type 玩家选择商品类型  可为null
	 * @param sellerId   寄卖者角色Id 可为null
	 * @return
	 */
	public List<Auction> getAuctionList(String props_name,Integer props_type,Long sellerId,Integer sub_type,int b_ind,int e_ind,String order_key,String de_as_key,int quality){
		List<Auction> auctions = null;
		HashMap<String,Object> conditionMap = new HashMap<String,Object>();
		try {
			conditionMap.put("props_overdue_sql", 1);
			//--道具模糊名
			if(!props_name.trim().equals("") && props_name!=null)
				conditionMap.put("props_name_sql", props_name);
			//--道具类型
			if(props_type > 0){
				conditionMap.put("props_type_sql", props_type);
				if(sub_type > 0)
					conditionMap.put("sub_type", sub_type);
			}
			//--寄售人ID
				conditionMap.put("props_roleId_sql", sellerId);
			//--商品品质
			if(quality > 0 ){
				conditionMap.put("props_quality", quality);
			}
			//--排序列
			conditionMap.put("order_key", order_key);
			//--排序方式
			conditionMap.put("de_as_key",de_as_key);
			//分页起始索引
			conditionMap.put("end_index", e_ind);
			//分页结束索引
			conditionMap.put("beg_index", b_ind);
			auctions = auctionDao.getAllAuctionList(conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return auctions;
		
	}
	
	/**
	 * 查看自己寄售商品列表
	 * @param role_id
	 * @return
	 */
	public List<Auction> getAuctionListSelf(long role_id,int begin_index,int end_index){
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Auction> auctions = null;
		try {
			map.put("role_id", role_id);
			map.put("begin_index", begin_index);
			map.put("end_index", end_index);
			auctions = auctionDao.getAuctionListSelf(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auctions;
	}

	/**
	 * 寄售物品(新增数据)
	 * @param auction
	 * @return 
	 * @throws Exception 
	 */
	public HashMap<String,Long> consignAuctionProps(Auction auction) throws Exception{
		HashMap<String,Long> map = new HashMap<String,Long>();
		 map.put(Constants.RESULT_KEY, Constants.CONSIGN_FAIL);
		 map.put(Constants.ID_KEY, -1l);
			
			//获取售卖道具基本信息
			List<RoleProps> sellPropss =  rolePropsDao.getRolePropsInfo(" role_props_id = "+auction.getRole_props_id());
			
			if(sellPropss == null || sellPropss.size()==0)
				return map;
			
			RoleProps sellProps = sellPropss.get(0);
			//售卖人原持有道具数量
			int lastCount = sellProps.getProps_count();
			if(lastCount < auction.getProps_counts())
				 return map;
			//售卖道具类型
			int props_type = sellProps.getProps_type();
			//售卖道具ID
			int props_id = sellProps.getProps_id();
			
			auction.setSeller_id(sellProps.getRole_id());
			auction.setProps_type(props_type);
			auction.setProps_id(props_id);
			//售卖道具是否是装备
			if(props_type == Constants.PROP_EQUIP)
				auction.setExtra_info(rolePropsDao.getEquipProperty(auction.getRole_props_id()).getInc_property());
			
			List<DaoVo> daoVos = new ArrayList<DaoVo>();
			//更新寄卖人信息
			if(lastCount == auction.getProps_counts()){
				//rolePropsDao.deleteProps(auction.getRole_props_id());
				daoVos.add(new DaoVo("rolePropsMap.deleteProps", auction.getRole_props_id(),3));
			}else{
				
				daoVos.add(new DaoVo("rolePropsMap.updatePropsCount", new RoleProps(auction.getRole_props_id(), lastCount - auction.getProps_counts()),2));
				//rolePropsDao.updatePropsCount(new RoleProps(auction.getRole_props_id(), lastCount - auction.getProps_counts()));
			}
			
			//新增交易行数据
			long keyId = auctionDao.selectAutionKeyId();
			auction.setAuction_id(keyId);
			daoVos.add(new DaoVo("auctionMap.addAuction", auction,1));
			
			sqlToolsService.doSqlByBatch(daoVos);
			
			
			
			map.put(Constants.RESULT_KEY, Constants.CONSIGN_SUCCESS);
			map.put(Constants.ID_KEY, keyId);
			 
		return map;
	}
	
	
	
	
	/**
	 * 购买拍卖行物品 
	 * @param auction
	 * @return
	 * @throws Exception 
	 */
	public synchronized long buyAuctionProps(long auction_id,long buyer_id) throws Exception{
		long resFlag = -1;
			
			//查询购买商品信息
			Auction auction =auctionDao.selectAutionInfo(auction_id);
			if(auction == null)
				return -2;//商品已售出
			//商品价格
			int auctionPrice = auction.getPrivce();
			//买家原有金币数
			int buyerGold = roleDao.selectRolesByRoleId(buyer_id).getGold();
			//交易后买家财富
			int buyerLastGold = buyerGold - auctionPrice;
			//税金数量
			int rate_gold = Math.round(auctionPrice * Constants.GOODS_SELL_RATE);
			//扣除税金后 卖家获得金币数
			int sellerLastGold = roleDao.selectRolesByRoleId(auction.getSeller_id()).getGold() + auctionPrice - rate_gold;//交易后卖家财富
			
			if(buyerLastGold < 0 || sellerLastGold < 0)
				return resFlag;
			
			//-----------构造交易对象
			Role buyer = new Role();
			buyer.setRole_id(auction.getBuyerId());
			buyer.setGold(buyerLastGold);
			
			Role seller = new Role();
			seller.setRole_id(auction.getSeller_id());
			seller.setGold(sellerLastGold);
			//-----------构造买家-道具对象
			RoleProps roleProps = new RoleProps();
			roleProps.setRole_id(buyer_id);
			roleProps.setProps_count(auction.getProps_counts());
			roleProps.setProps_id(auction.getProps_id());
			roleProps.setExtra_info(auction.getExtra_info());
			
			

//			Map<String,Object> callMap = new HashMap<String,Object>();
//			List<DaoVo> daoVos = new ArrayList<DaoVo>();
//			callMap.put("role_id", buyer_id);
//			callMap.put("props_id", auction.getProps_id());
//			callMap.put("props_count", auction.getProps_counts());
//			callMap.put("extra_info", auction.getExtra_info());
//			
//			daoVos.add(new DaoVo("callAddRoleProps.callAddRoleProps", callMap,4));
			resFlag = rolePropsDao.callAddRoleProps(roleProps);//商品入库到买家
			
			
			System.out.println("privce:"+auctionPrice+" rate:"+rate_gold);
			roleDao.updateRoleInfo(buyer);//更新买家财富值
			//daoVos.add(new DaoVo("roleMap.updateRoleInfo", buyer,2));
			roleDao.updateRoleInfo(seller);//更新卖家财富值
			//daoVos.add(new DaoVo("roleMap.updateRoleInfo", seller,2));
			

			auctionDao.deleteAuction(auction);//删除交易行信息
			
			//发送邮件告知卖家 商品卖出信息
			String mailContent = getSellMail(auction.getProps_name(),auctionPrice , rate_gold);
			mailDao.addNewMail(new Mail(Constants.SYSTEM_MAIL_NAME, Constants.MAIL_SYSTEM_ID, auction.getSeller_id(), mailContent, Constants.MAIL_SYSTEM_TYPE, Constants.MAIL_NOT_HAS_ATTACH));//邮件告知卖家交易信息
			return resFlag;
	}
	
	/**
	 * 撤销寄售（删除数据 同时商品回归买家包裹）
	 * @param auction
	 * @return
	 * @throws Exception 
	 */
	public int cancelAuction(long auction_id) throws Exception{
		int resFlag = -1;
			//查询购买商品信息
			Auction auction =auctionDao.selectAutionInfo(auction_id);
			if(auction != null){
				RoleProps roleProps = new RoleProps();
				roleProps.setRole_id(auction.getSeller_id());
				roleProps.setProps_count(auction.getProps_counts());
				roleProps.setProps_id(auction.getProps_id());
				roleProps.setExtra_info(auction.getExtra_info());
				
				resFlag = rolePropsDao.callAddRoleProps(roleProps);//商品回归买家包裹
				auctionDao.deleteAuction(auction);//数据库中删除数据
			}
			
		return resFlag;
	}
	

	/**
     * 获取邮件提示内容
     * @return
     */
    private static String getSellMail(String propName, int totalPrice, int ratePrice)
    {
        return "您好，您的物品【" + propName + "】已经成功售出\r\n获得金币：" + totalPrice + "\r\n交易税：" + ratePrice+"\r\n售得金币已转入包裹";
    }
}
