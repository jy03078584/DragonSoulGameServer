/**
 *
 *
 * 文件名称： ServerProp.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-17 下午2:49:26
 */
package com.lk.dragon.server.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.PropDomain;
import com.lk.dragon.server.module.analysis.PropRequestAnalysis;
import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerProp
{
    /** 道具请求实体 **/
    private PropDomain propDomain;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private RolePropsInfoService rolePropsInfoService;
    @Autowired
    private SqlToolsService toolsService;
    public ServerProp(){}
    
    /**
     * 构造函数
     */
    public ServerProp(PropDomain propDomain)
    {
    	this.roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);
    	this.rolePropsInfoService = SpringBeanUtil.getBean(RolePropsInfoService.class);
    	this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
        this.propDomain =  propDomain;
    }
    
    /**
     * 内部分析函数
     */
    public void propAnalysis()
    {
        switch (propDomain.getType())
        {
            case Constants.PROP_LIST_TYPE:
            {
                //获取道具列表基本信息
                getPropList();
                break;
            }
            case Constants.PROP_LIST_DETAIL_TYPE:
            {
            	//获取道具列表详细信息
            	getPropDetailList();
            	break;
            }
            case Constants.PROP_DISCARD_TYPE:
            {
                //丢弃道具
                discardProp();
                break;
            }
            case Constants.PROP_GET_BUFF_TYPE:
            {
                //使用道具获取BUFF
                getBuff();
                break;
            }
            case Constants.PROP_GRID_TYPE:
            {
                //购买包裹格子
                buyPropGrid();
                break;
            }
            case Constants.PROP_GET_SYSREWARD:
            {
                //获取系统奖励信息
                getSysReward();
                break;
            }
            case Constants.PROP_DO_SYSREWARD:
            {
                //开启统奖励
                doSysReward();
                break;
            }
            case Constants.BUFF_INFO_TYPE:
            {
                //查看目标对象BUFF
                getBuffInfo();
                break;
            }
            case Constants.BUFF_FINISH_TYPE:
            {
                //BUFF时间结束
                buffFinish();
                break;
            }
        }
    }
    
    /**
     * BUFF结束
     */
    private void buffFinish() {
    	boolean flag = false;
		try {
			rolePropsInfoService.buffFinish(propDomain.getBuffKeyId(), propDomain.getBuff_id());
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		SocketUtil.responseClient(propDomain.getCtx(), JSONUtil.getBooleanResponse(flag));
	}

    /**
     * 获取BUFF信息
     */
	private void getBuffInfo() {
		//构造BUff参数
		Buff buff = new Buff();
		buff.setTarget_id(propDomain.getBuffKeyId());
		if(propDomain.getBuff_id() > 0 )
			buff.setBuff_id(propDomain.getBuff_id());
		if(propDomain.getBuffType() > 0 )
			buff.setBuff_type(propDomain.getBuffType());
		String res;
		try {
			JSONObject resultObj = new JSONObject();
			resultObj.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			//获取对应的全部buff
			JSONArray buffAJ = PropRequestAnalysis.getBuffInfoResponse(rolePropsInfoService.getBuffInfo(buff));
			resultObj.put("buffs", buffAJ);
			res = resultObj.toString();
		} catch (Exception e) {
			res = JSONUtil.getBooleanResponse(false);
			e.printStackTrace();
		}
		//返回响应信息
		SocketUtil.responseClient(propDomain.getCtx(), res);
	}

	/**
     * 开启系统奖励
     */
    private void doSysReward() {
    	//开启宝箱 获得物品 存入包裹
    	String res = JSONUtil.getBooleanResponse(false);
		
		try {
			List<RoleProps> rewards = rolePropsInfoService.doSysRewardDetail(propDomain.getPropId(), propDomain.getIsRandom(), propDomain.getRoleId(),propDomain.getRandCnt(),propDomain.getRolePropId());
			res = PropRequestAnalysis.getRewardPropResponse(rewards);
		} catch (Exception e) {
			if(e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)){
				JSONObject errJ = new JSONObject();
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
				res = errJ.toString();
			}
			e.printStackTrace();
		}
		//返回获得物品相关信息 响应客户端
		SocketUtil.responseClient(propDomain.getCtx(), res);
	}

	/**
     * 获取系统奖励
     */
    private void getSysReward() {
    	String rewardInfo = rolePropsInfoService.getSysReward(propDomain.getPropId());
    	JSONObject rewardJ = new JSONObject();
    	rewardJ.put(Constants.RESULT_KEY, 1);
    	rewardJ.put("reward", rewardInfo);
    	SocketUtil.responseClient(propDomain.getCtx(), rewardJ.toString());
	}

	/**
     * 获取道具详细信息
     */
    private void getPropDetailList() {
		//获取关联ID
    	long role_prop_id = propDomain.getRolePropId();
    	//获取道具类型
    	int prop_type = propDomain.getPropType();
    	Map<String,Object> conditonMap = new HashMap<String,Object>();
    	conditonMap.put("role_props_id", role_prop_id);
    	
    	if(prop_type == Constants.PROP_EQUIP){
    		//装备类道具
    		conditonMap.put("isEquip", true);
    	}else if(prop_type == Constants.PROP_GEM){
    		//宝石类道具
    		conditonMap.put("isGem", true);
    	}else if (prop_type == Constants.PROP_REWARD){
    		//礼包类
    		conditonMap.put("isReward", true);
    	}

    	//查询当前角色所拥有的道具列表
        RoleProps roleProps = null;
		try {
			roleProps = rolePropsInfoService.selectRolePropsDetailById(conditonMap);
		} catch (Exception e) {
			e.printStackTrace();
			toolsService.addNewLogInfo(new Tools(0l, "道具模块", OperateLogUtil.getExceptionStackInfo(e), 0));
		}
        
        //获取响应字符串
        
        String propListResponseStr = PropRequestAnalysis.getPropDetailResponse(roleProps,prop_type);
        //System.out.println(propListResponseStr);
       SocketUtil.responseClient(propDomain.getCtx(), propListResponseStr);
	}
    

    
	/**
     * 使用道具获得BUFF
     */
    private void getBuff()
    {
    	JSONObject resJ = new JSONObject();
    	try {
    		//使用道具获取BUFF
			Map<String,Object> resMap = rolePropsInfoService.insertNewBuff(propDomain.getBuffKeyId(), propDomain.getBuff_id(), propDomain.getRolePropId());
			int resKey = (Integer) resMap.get("result");
			//解析结果
			if(resKey == 1){
				//Success
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
				resJ.put("buff_info", PropRequestAnalysis.getBuffInfoResponse((Buff) resMap.get("data")));
			}else if(resKey == -1){
				//道具不足
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
			}else{
				//已处于该BUFF效果时间内
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", "该BUFF无法叠加");
			}
    	} catch (Exception e) {
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
				resJ.put("reason", Constants.NET_ERROR);
			e.printStackTrace();
		}
    	SocketUtil.responseClient(propDomain.getCtx(), resJ.toString());
    }
    
    /**
     * 获取道具列表基本信息
     */
    private void getPropList()
    {
        //获取角色id
        long roleId = propDomain.getRoleId();
        
        //查询当前角色所拥有的道具列表
        List<RoleProps> propList = rolePropsInfoService.selectRolePropsByCondition(roleId);
        
        //获取响应字符串
        String propListResponseStr = PropRequestAnalysis.getPropListResponse(propList);
        SocketUtil.responseClient(propDomain.getCtx(), propListResponseStr);
    }
    
    /**
     * 丢弃道具
     */
    private void discardProp()
    {
        //道具实体
        RoleProps roleProp = new RoleProps();
        roleProp.setRole_props_id(propDomain.getRelationId());
        
        //删除道具
        boolean result = false;
        try {
			result = rolePropsInfoService.destoryProps(roleProp,propDomain.getRoleId(),propDomain.getGold());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //获取响应字符串
        String discardPropResponse = JSONUtil.getBooleanResponse(result);
        SocketUtil.responseClient(propDomain.getCtx(), discardPropResponse);
    }
    
    /**
     * 购买道具包裹格子
     */
    private void buyPropGrid()
    {
        //购买包裹袋子
        long roleId = propDomain.getRoleId();
        Map<String, Integer> map = null;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			map = roleInfoService.usePropsAddBags(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
        toolsService.addNewLogInfo(new Tools(roleId, "道具模块", title, logRes));
        //获取相应字符串
        SocketUtil.responseClient(
                propDomain.getCtx(), PropRequestAnalysis.addBagResponse(map));
    }
}
