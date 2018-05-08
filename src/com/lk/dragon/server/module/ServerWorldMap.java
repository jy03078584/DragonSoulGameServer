/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerWorldMap.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午3:13:08
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;
import com.lk.dragon.server.domain.WorldMapDomain;
import com.lk.dragon.server.module.analysis.WorldMapRequestAnalysis;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.service.WorldMapService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerWorldMap
{
    /** 世界地图相关请求实体 **/
    private WorldMapDomain worldMapDomain;
    
    @Autowired
    private WorldMapService worldMapService;
    
    @Autowired
    private SqlToolsService toolsService;
    public ServerWorldMap(){}
    
    /**
     * 构造函数
     * @param worldMapDomain
     */
    public ServerWorldMap(WorldMapDomain worldMapDomain)
    {
    	this.worldMapService = SpringBeanUtil.getBean(WorldMapService.class);
    	this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
        this.worldMapDomain =  worldMapDomain;
    }
    
    /**
     * 世界地图内部分析函数
     */
    public void worldMapAnalysis()
    {
        switch (worldMapDomain.getType())
        {
            case Constants.WORLD_MAP_BORTH_TYPE:
            {
                //出生点生成请求
                worldBorth();
                break;
            }
            case Constants.WORLD_MAP_DETAIL_TYPE:
            {
                //获取世界地图的某部分详细信息
                getWorldMapDetail();
                break;
            }
            case Constants.MAP_CITY_INFO_TYPE:
            {
                //根据坐标点获取城镇信息
                getCityInfo();
                break;
            }
            case Constants.MAP_ROLE_USE_TYPE:
            {
                //玩家使用钻石
                roleUseDiamond();
                break;
            }
            case Constants.WILD_SRC_SINGL_INFO_TYPE:
            {
            	//查询单目标资源点信息
            	selectWildSrcInfoSingle();
                break;
            }
            case Constants.WILD_SRC_MULTI_INFO_TYPE:
            {
            	//查询多目标资源点信息
            	selectWildSrcInfoMulti();
            	break;
            }
            case Constants.RANDOM_CREEPS_INFO_TYPE:
            {
            	//查询随机点野怪信息
            	selectRandomCreepsInfo();
            	break;
            }
            case Constants.GIVEUP_WILD_SRC_INFO:
            {
            	//舍弃资源点
            	giveUpWildSrc();
            	break;
            }
        }
    }
    
    

    /**
     * 放弃资源点
     */
    @SuppressWarnings("unchecked")
	private void giveUpWildSrc() {
    	String res;
    	JSONObject resJ;
		try {
			Map<String,Object> resMap = worldMapService.giveUpWildSrc(worldMapDomain.getX(), worldMapDomain.getY(),worldMapDomain.getRole_id()); 
			if((Integer)resMap.get("result") == -1){
				resJ = new JSONObject();
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", "该资源点已不属于您");
				res = resJ.toString();
			}else{
				res = WorldMapRequestAnalysis.giveUpWildSrcRes((Map<Long,Integer>)resMap.get("teamMap"),(Role)resMap.get("yield"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res = JSONUtil.getBooleanResponse(false);
		}
		SocketUtil.responseClient(worldMapDomain.getCtx(), res);
	}

	/**
     * 查询随机点野怪信息
     */
    private void selectRandomCreepsInfo() {
    	int x = worldMapDomain.getX();
    	int y = worldMapDomain.getY();
    	WorldMap creeps = worldMapService.getRandomWildInfo(x, y);
    	SocketUtil.responseClient(worldMapDomain.getCtx(), WorldMapRequestAnalysis.getRandomWildInfo(creeps));
    	
	}

	/**
     * 查询多目标资源点信息
     */
    private void selectWildSrcInfoMulti() {
    	long role_id = worldMapDomain.getRole_id();
    	String condition = " WHERE t2.role_id = "+role_id;
    	List<WildSrc> wildSrc =worldMapService.getWildSrcInfo(condition);
    	SocketUtil.responseClient(worldMapDomain.getCtx(), WorldMapRequestAnalysis.getWildSrcInfo(wildSrc, 2));
	}

	/**
     * 查询目标资源点信息
     */
    private void selectWildSrcInfoSingle() {
    	int site_x = worldMapDomain.getX();//X坐标
    	int site_y = worldMapDomain.getY();//Y坐标
    	String condition = " WHERE t.tag_x = "+site_x+" AND t.tag_y = "+site_y;
    	List<WildSrc> wildSrc =worldMapService.getWildSrcInfo(condition);
    	if(wildSrc == null || wildSrc.size() <= 0)
    		SocketUtil.responseClient(worldMapDomain.getCtx(), JSONUtil.getBooleanResponse(false));
    	SocketUtil.responseClient(worldMapDomain.getCtx(), WorldMapRequestAnalysis.getWildSrcInfo(wildSrc, 1));
    }

	/**
     * 玩家在世界地图出生点生成
     */
    private void worldBorth()
    {

    }
    
    /**
     * 获取地图某部分的详细信息
     */
    private void getWorldMapDetail()
    {
        //获取坐标范围
        WorldMap worldMap = new WorldMap();
        worldMap.setMin_x(worldMapDomain.getMin_x());
        worldMap.setMax_x(worldMapDomain.getMax_x());
        worldMap.setMin_y(worldMapDomain.getMin_y());
        worldMap.setMax_y(worldMapDomain.getMax_y());
        
        //获取这些点坐标的详细信息
        List<WorldMap> worldMapList = worldMapService.getWorldMapPoints(worldMap);
        
        //将这些详细信息写回到客户端
        SocketUtil.responseClient(worldMapDomain.getCtx(), 
                WorldMapRequestAnalysis.getWorldMapPoint(worldMapList));
    }
    
    /**
     * 根据城镇坐标获取其信息
     */
    private void getCityInfo()
    {
        City city = new City();
        city.setSite_x(worldMapDomain.getX());
        city.setSite_y(worldMapDomain.getY());
        
        city = worldMapService.getCityInfo(city);
        
        //相应客户端
        SocketUtil.responseClient(worldMapDomain.getCtx(),
                WorldMapRequestAnalysis.getCityInfo(city));
    }
    
    /**
     * 侦查对方城邦信息
     */
    private void roleUseDiamond()
    {
        //扣除钻石
        Map<String, Object> map = null;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
        try {
			map = worldMapService.useDiamond(
			        worldMapDomain.getRole_id(), worldMapDomain.getUseDiamond(), worldMapDomain.getCity_id(),worldMapDomain.getUseGold());
			
			logRes = Constants.LOG_RES_SUCESS;
			title = "侦查城邦信息\r\n操作人ID:"+worldMapDomain.getRole_id()+"\r\n被侦查城邦ID："+worldMapDomain.getCity_id()+"\r\n消耗钻石："+worldMapDomain.getUseDiamond();
			//查询城镇基本信息
			SocketUtil.responseClient(worldMapDomain.getCtx(), WorldMapRequestAnalysis.getDettctInfo(map));
		} catch (Exception e) {
			title = OperateLogUtil.getExceptionStackInfo(e);
			e.printStackTrace();
		}
      toolsService.addNewLogInfo(new Tools(worldMapDomain.getRole_id(), "世界地图模块", title, logRes));
    }
}
