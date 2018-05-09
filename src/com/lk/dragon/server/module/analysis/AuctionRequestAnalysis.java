/**
 *
 *
 * 文件名称： AuctionAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:32:40
 */
package com.lk.dragon.server.module.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Auction;
import com.lk.dragon.server.domain.AuctionDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class AuctionRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String AUCTION_TYPE_KEY = "auctionType";
    private static final String AUCTION_ID_KEY = "auction_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String PROP_ID_KEY = "prop_id";
    private static final String ROLE_PROP_ID_KEY = "role_prop_id";
    private static final String GOODS_NAME_KEY = "goods_name";
    private static final String GOODS_TYPE_KEY = "goods_type";
    private static final String GOODS_PRICE_KEY = "goods_price";
    private static final String SELLER_ID_KEY = "seller_id";
    private static final String GOODS_SUB_TYPE_KEY = "sub_type";
    private static final String BEGIN_INDEX_KEY = "begin_index";
    private static final String END_INDEX_KEY = "end_index";
    private static final String ORDER_BY_KEY = "order_key";
    private static final String DE_AS_KEY = "de_as_key";
    private static final String GOODS_LAST_SECONDS = "last_time";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    //拍卖行物品列表响应字段
    private static final String GOODSES_KEY = "goods";
    private static final String GOODS_ICON_KEY = "icon";
    private static final String GOODS_COMMENT_KEY = "comment";
    private static final String GOODS_NUM_KEY = "num";
    private static final String GOODS_IS_TIME_OUT_KEY = "time_out";
    private static final String SELLER_NAME_KEY = "sell_name";
    private static final String GOODS_QUALITY_KEY = "quality";
    private static final String GOODS_COMMAND_LEV_KEY = "lev";
    private static final String GOODS_EXTRA_INFO_KEY = "extra";
    
    /**
     * 将json字符串转化为拍卖行实体
     * @param json
     * @return
     */
    public static AuctionDomain getAuctionInfo(String json)
    {
        //创建拍卖行实体
        AuctionDomain auctionDomain = new AuctionDomain();
        
        //获取data信息
        JSONObject auctionData = JSONUtil.getData(json);
        
        //将信息分解到拍卖行实体中
        auctionDomain.setType(auctionData.getInt(AUCTION_TYPE_KEY));
        auctionDomain.setAuctionId(auctionData.has(AUCTION_ID_KEY) ? auctionData.getLong(AUCTION_ID_KEY) : 0);
        auctionDomain.setGoodsId(auctionData.has(PROP_ID_KEY) ? auctionData.getInt(PROP_ID_KEY) : 0);
        auctionDomain.setGoodsType(auctionData.has(GOODS_TYPE_KEY) ? auctionData.getInt(GOODS_TYPE_KEY) : -1);
        auctionDomain.setGoodsName(auctionData.has(GOODS_NAME_KEY) ? auctionData.getString(GOODS_NAME_KEY) : "");
        auctionDomain.setRoleId(auctionData.has(ROLE_ID_KEY) ? auctionData.getLong(ROLE_ID_KEY) : 0l);
        auctionDomain.setGoodsNum(auctionData.has(GOODS_NUM_KEY) ? auctionData.getInt(GOODS_NUM_KEY) : 0);
        auctionDomain.setGoodsPrice(auctionData.has(GOODS_PRICE_KEY) ? auctionData.getInt(GOODS_PRICE_KEY) : 0);
        auctionDomain.setRolePropId(auctionData.has(ROLE_PROP_ID_KEY) ? auctionData.getLong(ROLE_PROP_ID_KEY) : 0);
        auctionDomain.setSellerId(auctionData.has(SELLER_ID_KEY) ? auctionData.getLong(SELLER_ID_KEY) : -1);
        auctionDomain.setSub_type(auctionData.has(GOODS_SUB_TYPE_KEY) ? auctionData.getInt(GOODS_SUB_TYPE_KEY) : -1);
        auctionDomain.setB_ind(auctionData.has(BEGIN_INDEX_KEY) ? auctionData.getInt(BEGIN_INDEX_KEY) : -1);
        auctionDomain.setE_ind(auctionData.has(END_INDEX_KEY) ? auctionData.getInt(END_INDEX_KEY) : -1);
        auctionDomain.setOrder_key(auctionData.has(ORDER_BY_KEY) ? auctionData.getInt(ORDER_BY_KEY) : 1);
        auctionDomain.setDe_as_key(auctionData.has(DE_AS_KEY) ? auctionData.getInt(DE_AS_KEY) : 0);
        auctionDomain.setGoods_quality(auctionData.has(GOODS_QUALITY_KEY) ? auctionData.getInt(GOODS_QUALITY_KEY) : -1);
        auctionDomain.setExtra_info(auctionData.has(GOODS_EXTRA_INFO_KEY) ? auctionData.getString(GOODS_EXTRA_INFO_KEY) : "");
        
        return auctionDomain;
    }
    
    /**
     * 获取
     * @param auctionList
     * @return
     */
    public static String getAuctionListResonse(List<Auction> auctionList,boolean getTimeOut)
    {
        //拍卖行列表查询结果
        JSONObject auctionObj = new JSONObject();
        auctionObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //将商品list转换到json数组中
        JSONArray arr = new JSONArray();
        if (auctionList != null && auctionList.size() > 0)
        {
            for (int i = 0; i < auctionList.size(); i++)
            {
                Auction auction = auctionList.get(i);
                JSONObject obj = new JSONObject();
                obj.put(AUCTION_ID_KEY, auction.getAuction_id());
                obj.put(GOODS_NAME_KEY, auction.getProps_name());
                obj.put(GOODS_ICON_KEY, auction.getProps_icon());
                obj.put(GOODS_NUM_KEY, auction.getProps_counts());
                obj.put(GOODS_PRICE_KEY, auction.getPrivce());
                obj.put(GOODS_COMMENT_KEY, auction.getProps_comment());
                obj.put(SELLER_NAME_KEY, auction.getSeller_name());
                obj.put(GOODS_QUALITY_KEY, auction.getQuality());
                obj.put(GOODS_COMMAND_LEV_KEY, auction.getCommand_lev());
                obj.put(GOODS_EXTRA_INFO_KEY, auction.getExtra_info());
                obj.put(GOODS_TYPE_KEY, auction.getProps_type());
                
                if(getTimeOut){//是否需要获取到期标志
                	try
                	{
                		obj.put(GOODS_LAST_SECONDS, auction.getLast_seconds()<= 0 ? 0 : auction.getLast_seconds());
                		//判定货物是否已经到期
//                		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                		Date currTime = null;
//                		Date endTime = null;
//                		currTime = formatDate.parse(auction.getSysdate_database());
//                		endTime = formatDate.parse(auction.getOverdue());
//                		if (currTime.after(endTime))
//                		{
//                			obj.put(GOODS_IS_TIME_OUT_KEY, Constants.GOODS_TIME_OUT);
//                		}
//                		else
//                		{
//                			obj.put(GOODS_IS_TIME_OUT_KEY, Constants.GOODS_NOT_TIME_OUT);
//                		}
                	} 
                	catch (Exception e)
                	{
                		e.printStackTrace();
                	}
                }
                
                
                arr.add(obj);
            }
        }
        
        auctionObj.put(GOODSES_KEY, arr);
        
        return auctionObj.toString();
    }
    
    /**
     * 获取物品上架响应 字符串
     * @param map
     * @return
     */
    public static String getSellGoodsResponse(Map<String,Long> map)
    {
    	JSONObject resultObj = new JSONObject();
    	if(map == null){
    		resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
            return resultObj.toString();
    	}
        //获取结果
        long result = map.get(Constants.RESULT_KEY);
        // 构造登录结果
        
        if (result == Constants.CONSIGN_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(AUCTION_ID_KEY, map.get(Constants.ID_KEY));
        }else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    
}
