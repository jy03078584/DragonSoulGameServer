/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： MallAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:30:56
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.MarketProps;
import com.lk.dragon.server.domain.MallDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class MallRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String MALL_TYPE_KEY = "mallType";
    private static final String GOODS_NAME_KEY = "goods_name";
    private static final String GOODS_TYPE_KEY = "goods_type";
    private static final String ROLE_ID_KEY =  "role_id";
    private static final String BUY_NUM_KEY = "buy_num";
    private static final String PROP_ID_KEY = "prop_id";
    private static final String GOLD_NUM_KEY = "gold_num";
    private static final String DIMOND_NUM_KEY = "dimond_num";
    private static final String FOOD_NUM_KEY = "food_num";
    private static final String WOOD_NUM_KEY = "wood_num";
    private static final String STONE_NUM_KEY = "stone_num";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    //商城物品列表响应字段
    private static final String MALL_PROP_KEY = "mall_props";
    private static final String MALL_PROP_ID_KEY = "mall_prop_id";
    private static final String PRIVCE_DIAMON_KEY = "privce_diamon";
    private static final String PRIVCE_GOLD_KEY = "privce_gold";
    private static final String PROPS_NAME_KEY = "props_name";
    private static final String PROPS_ICON_KEY = "props_icon";
    private static final String PROPS_DESC_KEY = "desc";
    private static final String PROPS_TYPE_KEY = "props_type";
    
    /**
     * 将请求信息转化为商城实体信息
     * @param json
     * @return
     */
    public static MallDomain getMallInfo(String json)
    {
        //创建商城实体
        MallDomain mallDomain = new MallDomain();
        
        // 获取请求数据
        JSONObject mallData = JSONUtil.getData(json);;
        //将信息分解到商城实体中
        mallDomain.setType(mallData.getInt(MALL_TYPE_KEY));
        mallDomain.setGoodsName(mallData.has(GOODS_NAME_KEY) ? mallData.getString(GOODS_NAME_KEY) : null);
        mallDomain.setGoodsType(mallData.has(GOODS_TYPE_KEY) ? mallData.getInt(GOODS_TYPE_KEY) : null);
        mallDomain.setRoleId(mallData.has(ROLE_ID_KEY) ? mallData.getLong(ROLE_ID_KEY) : 0);
        mallDomain.setBuyNum(mallData.has(BUY_NUM_KEY) ? mallData.getInt(BUY_NUM_KEY) : 0);
        mallDomain.setPropId(mallData.has(PROP_ID_KEY) ? mallData.getInt(PROP_ID_KEY) : 0);
        mallDomain.setGoldNum(mallData.has(GOLD_NUM_KEY) ? mallData.getInt(GOLD_NUM_KEY) : 0);
        mallDomain.setDimondNum(mallData.has(DIMOND_NUM_KEY) ? mallData.getInt(DIMOND_NUM_KEY) : 0);
        
        mallDomain.setFoodNum(mallData.has(FOOD_NUM_KEY) ? mallData.getInt(FOOD_NUM_KEY) : 0);
        mallDomain.setWoodNum(mallData.has(WOOD_NUM_KEY) ? mallData.getInt(WOOD_NUM_KEY) : 0);
        mallDomain.setStoneNum(mallData.has(STONE_NUM_KEY) ? mallData.getInt(STONE_NUM_KEY) : 0);
        return mallDomain;
    }
    
    /**
     * 获取商城道具列表响应字符串
     * @return
     */
    public static String getMallPropListResponse(List<MarketProps> mallList)
    {
      //拍卖行列表查询结果
        JSONObject mallObj = new JSONObject();
        mallObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //将商品list转换到json数组中
        JSONArray arr = new JSONArray();
        if (mallObj != null && mallObj.size() > 0)
        {
            for (int i = 0; i < mallList.size(); i++)
            {
                MarketProps marketProps = mallList.get(i);
                JSONObject obj = new JSONObject();
                obj.put(MALL_PROP_ID_KEY, marketProps.getMarket_props_id());
                obj.put(PROP_ID_KEY, marketProps.getProps_id());
                obj.put(PRIVCE_DIAMON_KEY, marketProps.getPrivce_diamon());
                obj.put(PRIVCE_GOLD_KEY, marketProps.getPrivce_gold());
                obj.put(PROPS_NAME_KEY, marketProps.getProps_name());
                obj.put(PROPS_ICON_KEY, marketProps.getProps_icon());
                obj.put(PROPS_DESC_KEY, marketProps.getProps_desc());
                obj.put(PROPS_TYPE_KEY, marketProps.getProps_type());
                arr.add(obj);
            }
        }
        
        mallObj.put(MALL_PROP_KEY, arr);
        
        return mallObj.toString();
    }
}
