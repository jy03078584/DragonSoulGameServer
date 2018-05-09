/**
 *
 *
 * 文件名称： DiamondRequesetAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午10:57:33
 */
package com.lk.dragon.server.module.analysis;

import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Gem;
import com.lk.dragon.server.domain.DiamondDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class DiamondRequesetAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String REQUEST_TYPE_KEY = "requestType";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String PROP_ID_KEY = "prop_id";
    private static final String ROLE_PROP_ID_KEY = "role_prop_id";
    private static final String DIAMOND_TYPE_KEY = "diamond_type";
    private static final String DIAMOND_LEV_KEY = "diamond_lev";
    private static final String DIAMOND_IS_SUCCESS_KEY = "is_success";
    private static final String USE_GOLD_KEY = "use_gold";
    private static final String PROP_IS_ENOUGH_KEY = "is_enough";
    
    /**辅助道具ID**/
    private static final String ASS_ROLE_PROP_ID = "ass_prop_id";
    /**辅助道具使用个数**/
    private static final String ASS_ROLE_PROP_NUM = "ass_prop_num";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    
    private static final String GEM_NAME_KEY = "gem_name";
    private static final String GEM_ICON_KEY = "gem_icon";
    private static final String PROP_TYPE_KEY = "prop_type";
    private static final String GEM_BUFF_KEY = "gem_buff";
    private static final String NEW_GEM_PROPS_ID_KEY = "gem_props_id";
    
    
    /**
     * 将上传的信息解析到英雄 实体中
     * @param info
     * @return
     */
    public static DiamondDomain getDiamondInfo(String info)
    {
        //创建英雄实体
        DiamondDomain diamondDomain = new DiamondDomain();
        
        //获取data信息
        JSONObject diamondData = JSONUtil.getData(info);
        
        //将信息分解到英雄实体中
        diamondDomain.setType(diamondData.getInt(REQUEST_TYPE_KEY));
        diamondDomain.setDiamondType(diamondData.has(DIAMOND_TYPE_KEY) ? diamondData.getInt(DIAMOND_TYPE_KEY) : 0);
        diamondDomain.setDiamondLev(diamondData.has(DIAMOND_LEV_KEY) ? diamondData.getInt(DIAMOND_LEV_KEY) : 0);
        diamondDomain.setDiamondId(diamondData.has(PROP_ID_KEY) ? diamondData.getInt(PROP_ID_KEY) : 0);
        diamondDomain.setRelaId(diamondData.has(ROLE_PROP_ID_KEY) ? diamondData.getLong(ROLE_PROP_ID_KEY) : 0);
        diamondDomain.setIsSuccess(diamondData.has(DIAMOND_IS_SUCCESS_KEY) ? diamondData.getInt(DIAMOND_IS_SUCCESS_KEY) : 0);
        diamondDomain.setUseGold(diamondData.has(USE_GOLD_KEY) ? diamondData.getInt(USE_GOLD_KEY) : 0);
        diamondDomain.setRoleId(diamondData.has(ROLE_ID_KEY) ? diamondData.getLong(ROLE_ID_KEY) : 0);
        diamondDomain.setIs_enough(diamondData.has(PROP_IS_ENOUGH_KEY) ? diamondData.getInt(PROP_IS_ENOUGH_KEY) : 0);
        diamondDomain.setAssRolePropsId(diamondData.has(ASS_ROLE_PROP_ID) ? diamondData.getLong(ASS_ROLE_PROP_ID) : 0);
        diamondDomain.setAssPropsCount(diamondData.has(ASS_ROLE_PROP_NUM) ? diamondData.getInt(ASS_ROLE_PROP_NUM) : 0);
        return diamondDomain;
    }
    
    /**
     * 获取相应 字符串
     * @param gem
     * @param isSuccess 针对宝石合成操作
     * @return
     */
    public static String getGemResponse(Gem gem,int isSuccess)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (gem == null)
        {
        	if(isSuccess == 0){
        		resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        	}else{
        		 //存放结果 
                resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
                resultObj.put(REASON_KEY, Constants.NET_ERROR);
        	}
           
        }
        else
        {
            //消耗结果信息
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(GEM_NAME_KEY, gem.getGem_name());
            resultObj.put(GEM_ICON_KEY, gem.getIcon());
            resultObj.put(GEM_BUFF_KEY, gem.getGem_buff());
            resultObj.put(NEW_GEM_PROPS_ID_KEY, gem.getRole_props_id());
        }
        
        return resultObj.toString();
    }
}
