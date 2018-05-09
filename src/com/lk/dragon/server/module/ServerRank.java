/**
 *
 *
 * 文件名称： ServerRank.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-13 上午11:06:33
 */
package com.lk.dragon.server.module;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.server.domain.RankDomain;
import com.lk.dragon.server.module.analysis.RankRequestAnalysis;
import com.lk.dragon.service.RanksInfoService;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerRank
{
    /** 战斗数据实体 **/
    private RankDomain rankDomain;
    
    @Autowired
    private RanksInfoService ranksInfoService;
    public ServerRank(){}
    
    /**
     * 构造函数
     * @param battleDomain
     */
    public ServerRank(RankDomain rankDomain)
    {
    	this.ranksInfoService = SpringBeanUtil.getBean(RanksInfoService.class);
        this.rankDomain = rankDomain;
    }
    
    /**
     * 战斗分析函数
     */
    public void rankAnalysis()
    {
    	//查询排行
    	getRankInfo();
      
        
    }

    /**
     * 查询排行信息
     */
	private void getRankInfo() {
		int rank_type = rankDomain.getType();//排行榜类型
		long object_id = rankDomain.getObject_id();//对象ID
		Map<String,Object> rankMap = null;
		
		try {
			//查询数据
			rankMap = ranksInfoService.getRansInfo(rank_type, object_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//响应客户端
		SocketUtil.responseClient(rankDomain.getCtx(), RankRequestAnalysis.getRankListResponse(rankMap));
	}
    
}
