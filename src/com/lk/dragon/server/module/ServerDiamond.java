/**
 *
 *
 * 文件名称： ServerDiamond.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午11:24:33
 */
package com.lk.dragon.server.module;


import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Gem;
import com.lk.dragon.server.domain.DiamondDomain;
import com.lk.dragon.server.module.analysis.DiamondRequesetAnalysis;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerDiamond
{
    /** 战斗数据实体 **/
    private DiamondDomain diamondDomain;
    private RolePropsInfoService rolePropsInfoService;
    public ServerDiamond(){}
    
    /**
     * 构造函数
     * @param battleDomain
     */
    public ServerDiamond(DiamondDomain diamondDomain)
    {
    	this.rolePropsInfoService = SpringBeanUtil.getBean(RolePropsInfoService.class);
        this.diamondDomain = diamondDomain;
    }
    
    /**
     * 战斗分析函数
     */
    public void diamondAnalysis()
    {
        switch (diamondDomain.getType())
        {
            case Constants.DIAMOND_UP_TYPE:
            {
                //宝石合成
                diamondSynthesis();
                break;
            }
            case Constants.DIAMOND_GET_TYPE:
            {
                //随机开启宝石袋获取宝石
                getDiamond();
                break;
            }
        }
    }
    
    /**
     * 宝石合成
     */
    private void diamondSynthesis()
    {
        //强化宝石详细信息
        long roleId = diamondDomain.getRoleId();
        //原宝石道具ID
        long rolePropId = diamondDomain.getRelaId();
        //宝石类型
        int type = diamondDomain.getDiamondType();
        //合成后宝石等级
        int lev = diamondDomain.getDiamondLev();
        //是否合成成功
        int isSuccess = diamondDomain.getIsSuccess();
        //本次合成消耗金币数
        int useGold = diamondDomain.getUseGold();
        //消耗的辅助道具ID
        long assRolePropId = diamondDomain.getAssRolePropsId();
        //消耗辅助道具数量
        int assRolePropNum = diamondDomain.getAssPropsCount();
        //强化宝石
        Gem gem = null;
        try {
			gem = rolePropsInfoService.gemLevUp(
			        roleId, rolePropId, type, lev, isSuccess, useGold,assRolePropId,assRolePropNum);
			
			//新增操作日志
            String detail = "成功强化宝石.";
            OperateLogUtil.insertOperateLog(diamondDomain.getRoleId(), detail, OperateLogUtil.DIAMOND_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			gem = null;
			e.printStackTrace();
			
			//新增操作日志
            String detail = "强化宝石失败.详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(diamondDomain.getRoleId(), detail, OperateLogUtil.DIAMOND_MODULE, OperateLogUtil.FAIL);

		}
        
        //获取相应字符串
        String diamondResponse = DiamondRequesetAnalysis.getGemResponse(gem,isSuccess);
        SocketUtil.responseClient(diamondDomain.getCtx(), diamondResponse);
    }
    
    /**
     * 开启宝石袋，随机获取一个宝石
     */
    private void getDiamond()
    {
        //开启宝石袋
        long roleId = diamondDomain.getRoleId();
        int diamondType = diamondDomain.getDiamondType();
        int diamondLev = diamondDomain.getDiamondLev();
        long rolePropId = diamondDomain.getRolePropId();
        int isEnough = diamondDomain.getIs_enough();
        Gem gem = null;
        String diamondResponse = "";
        try {
			gem =rolePropsInfoService.usePropsGetGem(
			        roleId, diamondLev, diamondType, rolePropId, isEnough);
			diamondResponse =   DiamondRequesetAnalysis.getGemResponse(gem,1);
			//新增操作日志
            String detail = "成功开启宝石袋";
            OperateLogUtil.insertOperateLog(diamondDomain.getRoleId(), detail, OperateLogUtil.DIAMOND_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)){
				JSONObject errJ = new JSONObject();
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
				diamondResponse = errJ.toString();
			}else{
				diamondResponse =   DiamondRequesetAnalysis.getGemResponse(null,1);
			}
			//新增操作日志
            String detail = "开启宝石袋失败.详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(diamondDomain.getRoleId(), detail, OperateLogUtil.DIAMOND_MODULE, OperateLogUtil.FAIL);

		}
        

        SocketUtil.responseClient(diamondDomain.getCtx(), diamondResponse);
    }
}
