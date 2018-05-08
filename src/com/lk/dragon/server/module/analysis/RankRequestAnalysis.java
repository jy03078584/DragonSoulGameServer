/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： RankRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-13 上午10:59:04
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Ranks;
import com.lk.dragon.server.domain.RankDomain;
import com.lk.dragon.service.RanksInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class RankRequestAnalysis
{
    private static final String RANK_TYPE_KEY = "rank_type";
    private static final String OBJECT_ID_KEY = "object_id";
    private static final String OBJECT_NAME_KEY = "object_name";
    
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    
    //排行榜回写字段
    private static final String RANK_KEY = "ranks";
    private static final String RANK_ROWNUM_KEY = "rownum";
    private static final String RANK_DATA_KEY = "rank_data";
    
    //自己的排行榜信息
    private static final String MY_RANK_KEY = "my_rank";
    
    /**
     * 获取排行榜请求信息
     * @param info
     * @return
     */
    public static RankDomain getRankInfo(String info)
    {
        RankDomain rankDomain = new RankDomain();
        
        //获取data信息
        JSONObject rankData = JSONUtil.getData(info);
        
        rankDomain.setType(rankData.getInt(RANK_TYPE_KEY));
        rankDomain.setObject_id(rankData.has(OBJECT_ID_KEY) ? rankData.getLong(OBJECT_ID_KEY) : 0);
        
        return rankDomain;
    }
    
    /**
     * 获取排行榜详细信息
     * @param map
     * @return
     */
    public static final String getRankListResponse(Map<String,Object> map)
    {
        //获取排行列表
        @SuppressWarnings("unchecked")
        List<Ranks> ranks = (List<Ranks>) map.get(RanksInfoService.RANK_ALL_KEY);
        //获取玩家的排名信息
        Ranks myRank = (Ranks) map.get(RanksInfoService.RANK_MYSELF_KEY);
        
        //排行榜查询结果
        JSONObject rankObj = new JSONObject();
        
        if(ranks == null || ranks.size() == 0){
        	rankObj.put(RESULT_KEY, Constants.REQUEST_ERROR);
        	return rankObj.toString();
        }
        
        rankObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);//操作结果标志位
        
        //------------------------[构造整体排行数据]----------------------------//
        JSONArray ranksJArr = new JSONArray();//存放全区排行列表
        for (Ranks rank : ranks) {
			JSONObject rankJ = new JSONObject();//排行单条数据对象
			
			rankJ.put(RANK_TYPE_KEY, rank.getRank_type());//排行类型
			rankJ.put(OBJECT_NAME_KEY, rank.getObject_name());//对象名
			rankJ.put(RANK_ROWNUM_KEY, rank.getRank_number());//排行名次
			rankJ.put(RANK_DATA_KEY, rank.getRank_data());//实际数据
			
			ranksJArr.add(rankJ);//存入JSON数组
		}
        rankObj.put(RANK_KEY, ranksJArr);//整体排行存入总JSON对象中
        
      //------------------------[构造自己排行数据]----------------------------//
        if(myRank == null){
        	//角色未入榜  创建未入榜Rank对象
        	myRank = new Ranks();
        	myRank.setRank_number(-1l);
        	myRank.setRank_data(0);
        }
        //本人排行JSON对象
        JSONObject myRankJ = new JSONObject();
        
    	myRankJ.put(RANK_ROWNUM_KEY, myRank.getRank_number());
    	myRankJ.put(RANK_DATA_KEY, myRank.getRank_data());
    	
    	//本人排行存入总JSON对象
    	rankObj.put(MY_RANK_KEY, myRankJ);
    	
        //返回最终结果
        return rankObj.toString();
    }
    
}
