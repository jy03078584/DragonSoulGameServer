 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: AuctionDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-23 下午4:15:06 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IAuctionDao;
import com.lk.dragon.db.domain.Auction;

/**  
 * @Description:拍卖行接口实现
 */
@Repository("auctionDao")
public class AuctionDaoImpl extends BaseSqlMapDao implements IAuctionDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<Auction> getAllAuctionList(Map<String, Object> conditionMap)throws Exception {
		return getSqlMapClientTemplate().queryForList("auctionMap.getAllAuctionList", conditionMap);
	}

	@Override
	public int deleteAuction(Auction auction) throws Exception {
		return getSqlMapClientTemplate().delete("auctionMap.deleteAuction", auction);
	}



	@Override
	public int updateAuctionCounts(Auction auction) throws Exception {
		return getSqlMapClientTemplate().update("auctionMap.updateAuctionCounts", auction);
	}

	@Override
	public int checkConsignCounts(long seller_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("auctionMap.checkConsignCounts", seller_id);
	}

	@Override
	public List<Auction> getAuctionListSelf(Map<String,Object> map) throws Exception {
		return getSqlMapClientTemplate().queryForList("auctionMap.getAuctionListSelf", map);
	}

	@Override
	public Auction selectAutionInfo(long auction_id) throws Exception {
		return (Auction) getSqlMapClientTemplate().queryForObject("auctionMap.selectAutionInfo",auction_id);
	}

	@Override
	public Long selectAutionKeyId() {
		return (Long) getSqlMapClientTemplate().queryForObject("auctionMap.selectAutionKeyId");
	}

	@Override
	public Object addAuction(Auction auction) throws Exception {
		return getSqlMapClientTemplate().insert("auctionMap.addAuction", auction);
	}

}
