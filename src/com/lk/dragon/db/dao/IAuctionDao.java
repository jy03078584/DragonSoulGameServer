 /** 
 *
 * @Title: IAuctionDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-23 下午4:14:14 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Auction;

/**  
 * @Description:拍卖行接口
 */
public interface IAuctionDao {

	/**
	 * 查询拍卖行列表
	 * @param conditionMap 分类查询
	 * @return
	 * @throws Exception
	 */
	public List<Auction> getAllAuctionList(Map<String,Object> conditionMap) throws Exception;
	 
	
	/**
	 * 获取Auction Sequence
	 * @return
	 */
	public Long selectAutionKeyId();

	/**
	 * 撤销寄售或商品售罄
	 * @param auction
	 * @return
	 * @throws Exception
	 */
	public int deleteAuction(Auction auction) throws Exception;
	
	
	/**
	 * 寄售(新增)拍卖行商品
	 * @param auction
	 * @return
	 * @throws Exception
	 */
	public Object addAuction(Auction auction) throws Exception;
	
	
	/**
	 * 购买拍卖行商品：针对可叠合类商品 修改数量
	 * @param auction
	 * @return
	 * @throws Exception
	 */
	public int updateAuctionCounts(Auction auction)throws Exception;
	
	/**
	 * 查看商品基本信息
	 * @param auction
	 * @return
	 * @throws Exception
	 */        
	public Auction selectAutionInfo(long auction_id)throws Exception;
	
	/**
	 * 查看当前角色寄售量
	 * @param sellerId
	 * @return
	 * @throws Exception
	 */
	public int checkConsignCounts(long seller_id)throws Exception;
	
	/**
	 * 查看自己寄售商品列表
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Auction> getAuctionListSelf(Map<String,Object> map)throws Exception;
}
