/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerMall.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-26 上午11:35:57
 */
package com.lk.dragon.server.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.MarketProps;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.MallDomain;
import com.lk.dragon.server.module.analysis.MallRequestAnalysis;
import com.lk.dragon.service.MarketInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerMall
{
    /** 商城实体 **/
    private MallDomain mallDomain;

    @Autowired
    private MarketInfoService marketInfoService;
    @Autowired
    private SqlToolsService toolsService;
    public ServerMall()
    {
    }

    /**
     * 商城实体
     * 
     * @param mallDomain
     */
    public ServerMall(MallDomain mallDomain)
    {
    	this.marketInfoService = SpringBeanUtil.getBean(MarketInfoService.class);
    	this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
        this.mallDomain = mallDomain;
    }

    /**
     * 商城内部分析模块
     */
    public void mallAnalysis()
    {
        switch (mallDomain.getType())
        {
        case Constants.MALL_LIST_TYPE:
        {
            // 获取商城出售商城列表信息
            getMallList();
            break;
        }
        case Constants.MALL_BUY_TYPE:
        {
            // 购买商城物品请求
            buy();
            break;
        }
        case Constants.MALL_BACK_RES_TYPE:
        {
        	//玩家资源换取金币
        	backResource();
        	break;
        }
        }
    }

    /**
     * 资源换取金币
     */
    private void backResource() {
    	
    	//构建消耗资源Map
    	Map<String,Integer> resMap = new HashMap<String,Integer>();
    	resMap.put(MarketInfoService.FOOD_KEY, mallDomain.getFoodNum());
    	resMap.put(MarketInfoService.WOOD_KEY, mallDomain.getWoodNum());
    	resMap.put(MarketInfoService.STONE_KEY, mallDomain.getStoneNum());
    	
    	JSONObject resJ = new JSONObject();
    	//兑换金币
    	try {
    		
			int res = marketInfoService.backResource(resMap, mallDomain.getRoleId(), mallDomain.getGoldNum());
			if(res == 0){
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", "资源不足无法兑换,请检查后重新兑换!");
			}else{
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			resJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
		}
    	//向客户端反馈结果
    	SocketUtil.responseClient(mallDomain.getCtx(), resJ.toString());
	}

	/**
     * 获取商城列表
     */
    private void getMallList()
    {
        // 获取商品列表
        List<MarketProps> propList = marketInfoService.getMarketProps(
                mallDomain.getGoodsName(), mallDomain.getGoodsType());

        // 获取回写字符串
        String mallListResponseStr = MallRequestAnalysis
                .getMallPropListResponse(propList);
        System.out.println("RES_LENGTH:"+mallListResponseStr.length());
        SocketUtil.responseClient(mallDomain.getCtx(), mallListResponseStr);
    }

    /**
     * 购买商品
     */
    private void buy()
    {
        //商城商品购买实体
        MarketProps marketProps = new MarketProps();
        marketProps.setBuyer_id(mallDomain.getRoleId());
        marketProps.setBuy_counts(mallDomain.getBuyNum());
        marketProps.setProps_id(mallDomain.getPropId());
        marketProps.setUse_gold(mallDomain.getGoldNum());
        marketProps.setUse_diamon(mallDomain.getDimondNum());
        
        //购买商品
        long result = -1;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			result = marketInfoService.buyMarketProps(marketProps);
			title = "购买商品\r\n"+"购买人ID："+mallDomain.getRoleId()+"\r\n商品道具ID:"+mallDomain.getPropId()+"\r\n购买个数"+mallDomain.getBuyNum()+"\r\n消耗金币:"+mallDomain.getGoldNum()+"\r\n消耗钻石："+mallDomain.getDimondNum();
			logRes = Constants.LOG_RES_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
		toolsService.addNewLogInfo(new Tools(mallDomain.getRoleId(), "商城模块", title, logRes));
        int resKey = -1;
        //响应客户端
        if(result == Constants.BAGS_FULL || result > 0){//背包已满
        	resKey = Constants.REQUEST_SUCCESS;
        }
        SocketUtil.responseClient(mallDomain.getCtx(), JSONUtil.getNewPropsIdResponse(resKey, result));

    }
}
