/**
 *
 *
 * 文件名称： EquipRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午10:57:47
 */
package com.lk.dragon.server.module.analysis;

import java.util.Map;

import net.sf.json.JSONObject;

import com.lk.dragon.server.domain.EquipDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class EquipRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String REQUEST_TYPE_KEY = "requestType";
    private static final String EQUIP_ID_KEY = "equip_id";
    private static final String DIAMOND_ID_KEY = "diamond_id";
    private static final String RELA_ID_KEY = "rela_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String GOLD_NUM_KEY = "gold_num";
    private static final String DIAMOND_NUM_KEY = "diamond";

    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    
    /**
     * 将上传的信息解析到英雄 实体中
     * @param info
     * @return
     */
    public static EquipDomain getEquipInfo(String info)
    {
        //创建英雄实体
        EquipDomain equipDomain = new EquipDomain();
        
        //获取data信息
        JSONObject equipData = JSONUtil.getData(info);
        
        //将信息分解到英雄实体中
        equipDomain.setType(equipData.getInt(REQUEST_TYPE_KEY));
        equipDomain.setEquipId(equipData.has(EQUIP_ID_KEY) ? equipData.getInt(EQUIP_ID_KEY) : 0);
        equipDomain.setDiamondId(equipData.has(DIAMOND_ID_KEY) ? equipData.getInt(DIAMOND_ID_KEY) : 0);
        equipDomain.setRelaId(equipData.has(RELA_ID_KEY) ? equipData.getLong(RELA_ID_KEY) : 0);
        equipDomain.setRoleId(equipData.has(ROLE_ID_KEY) ? equipData.getLong(ROLE_ID_KEY) : 0);
        equipDomain.setGoldNum(equipData.has(GOLD_NUM_KEY) ? equipData.getInt(GOLD_NUM_KEY) : 0);
        equipDomain.setDiamondNum(equipData.has(DIAMOND_NUM_KEY) ? equipData.getInt(DIAMOND_NUM_KEY) : 100);
        
        
        return equipDomain;
    }
    
    /**
     * 装备宝石响应字符串
     * @param map
     * @return
     */
    public static String getEquipDiamondResponse(Map<String,Long> map)
    {
        //获取结果
        long result = map.get(Constants.RESULT_KEY);
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (result == Constants.REQUEST_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(RELA_ID_KEY, map.get(Constants.ID_KEY));
        }
        else if (result == Constants.REQUEST_ERROR)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
}
