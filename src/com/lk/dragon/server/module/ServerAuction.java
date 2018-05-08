/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerAuction.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-27 下午3:25:25
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;


import com.lk.dragon.db.domain.Auction;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.server.domain.AuctionDomain;
import com.lk.dragon.server.module.analysis.AuctionRequestAnalysis;
import com.lk.dragon.service.AuctionInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerAuction
{
    /** 拍卖行实体 **/
    private AuctionDomain auctionDomain;

    private AuctionInfoService auctionInfoService;


    public ServerAuction(){}
    /**
     * 
     * @param auctionDomain
     */
    public ServerAuction(AuctionDomain auctionDomain)
    {
        this.auctionDomain = auctionDomain;
        this.auctionInfoService = SpringBeanUtil.getBean(AuctionInfoService.class);
    }

    /**
     * 拍卖行内部分析模块
     */
    public void auctionAnalysis()
    {
        switch (auctionDomain.getType())
        {
        case Constants.AUCTION_LIST_TYPE:
        {
            // 获取拍卖行内部出售的商品列表
            getAuctionList();
            break;
        }
        case Constants.AUCTION_BUY_TYPE:
        {
            // 拍卖行购买商品请求
            buyGoods();
            break;
        }
        case Constants.AUCTION_SELL_TYPE:
        {
            // 拍卖行出售商品登记请求
            sellGoods();
            break;
        }
        case Constants.AUCTION_CANCEL_TYPE:
        {
            deleteGoods();
            break;
        }
        case Constants.AUCTION_LIST_MYSELF_TYPE:
        {
        	getAuctionListSelf();
        	break;
        }
        }
    }

    /**
     * 获取拍卖行商品列表
     */
    private void getAuctionList()
    {
        // 获取查询条件
        // 道具名称
        String propName = auctionDomain.getGoodsName();
        // 道具类型
        int propType = auctionDomain.getGoodsType();
        // 浏览人ID
        long lookerId = auctionDomain.getRoleId();
        int beg_index = auctionDomain.getB_ind();
        int end_index = auctionDomain.getE_ind();
        if(beg_index < 0 || end_index < 0)
        	SocketUtil.responseClient(auctionDomain.getCtx(), JSONUtil.getBooleanResponse(false));
        String des_asc = auctionDomain.getDe_as_key() == 0 ? "ASC" :"DESC";
        String order_key = " overdue";
        int subType = auctionDomain.getSub_type();
        int quality = auctionDomain.getGoods_quality();
        switch (auctionDomain.getOrder_key()) {
		case Constants.ORDER_BY_PRIVCE:
			order_key = " privce/props_counts";
			break;
		case Constants.ORDER_BY_PRO_NAME:
			order_key = " props_name";
			break;
		default:
			break;
		}
        // 获取拍卖行列表里的数据
        List<Auction> auctionList = auctionInfoService.getAuctionList(propName, propType, lookerId,subType,beg_index,end_index,order_key,des_asc,quality);

        // 获取响应字符串
        String auctionListResponse = AuctionRequestAnalysis.getAuctionListResonse(auctionList,false);
        SocketUtil.responseClient(auctionDomain.getCtx(), auctionListResponse);
    }

    
    /**
     * 查询自己寄售商品
     */
    private void getAuctionListSelf(){
    	long role_id = auctionDomain.getRoleId();
    	int begin_index = auctionDomain.getB_ind();
    	int end_index = auctionDomain.getE_ind();
    	if(role_id > 0){
    		List<Auction> auctionList = auctionInfoService.getAuctionListSelf(role_id,begin_index,end_index);
    		String response = AuctionRequestAnalysis.getAuctionListResonse(auctionList,true);
    		System.out.println(response);
    		SocketUtil.responseClient(auctionDomain.getCtx(),response);
    	}
    }
    
    /**
     * 玩家登记出售商品
     */
    private void sellGoods()
    {
        // 获取Auction实体对象
        Auction auction = new Auction();
        auction.setPrivce(auctionDomain.getGoodsPrice());
        auction.setProps_counts(auctionDomain.getGoodsNum());
        auction.setRole_props_id(auctionDomain.getRolePropId());
        // 将商品入库上架
        Map<String, Long> map = null;
		try {
			map = auctionInfoService.consignAuctionProps(auction);
			//新增操作日志
            String detail = "玩家寄售商品成功，道具id：" + auctionDomain.getRolePropId();
            OperateLogUtil.insertOperateLog(0l, detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "玩家寄售商品失败.详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(auctionDomain.getRoleId(), detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.FAIL);

		}
        
        // 获取结果处理响应字符串
        String responseStr = AuctionRequestAnalysis.getSellGoodsResponse(map);
        SocketUtil.responseClient(auctionDomain.getCtx(), responseStr);
    }

    /**
     * 玩家购买商品
     */
    private void buyGoods()
    {

        // 响应字符串
        int resKey = -1;
        long addPropsRes = 0;
        // 购买商品
        // 获取商品上架的数量（同步操作，必须保证购买时，剩余的商品足够）
        synchronized (this)
        {
        	try {
				addPropsRes  = auctionInfoService.buyAuctionProps(auctionDomain.getAuctionId(),auctionDomain.getRoleId());
				//新增操作日志
	            String detail = "玩家购买商品成功，购买到的道具id：" + addPropsRes;
	            OperateLogUtil.insertOperateLog(auctionDomain.getRoleId(), detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.SUCCESS);

        	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//新增操作日志
	            String detail = "玩家购买商品失败.详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
	            OperateLogUtil.insertOperateLog(auctionDomain.getRoleId(), detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.FAIL);

			}
        	//响应客户端
            if(addPropsRes == Constants.BAGS_FULL || addPropsRes > 0){
            	resKey = Constants.REQUEST_SUCCESS;
            }
        }

        // 将信息写回到客户端
        SocketUtil.responseClient(auctionDomain.getCtx(), JSONUtil.getNewPropsIdResponse(resKey, addPropsRes));
    }

    /**
     * 商品下架
     */
    private void deleteGoods()
    {
       
        int resKey =  -1;
        long result = -1;
        // 删除商品记录
         try {
			result = auctionInfoService.cancelAuction(auctionDomain.getAuctionId());
			if(result == Constants.BAGS_FULL || result > 0){//背包已满
				resKey = Constants.REQUEST_SUCCESS;
			}
			//新增操作日志
            String detail = "玩家下架商品成功，下架后的道具id：" + result;
            OperateLogUtil.insertOperateLog(0l, detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "玩家下架商品失败.详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(auctionDomain.getRoleId(), detail, OperateLogUtil.AUCTION_MODULE, OperateLogUtil.FAIL);

		}
        SocketUtil.responseClient(auctionDomain.getCtx(), JSONUtil.getNewPropsIdResponse(resKey, result));  
       }
}
