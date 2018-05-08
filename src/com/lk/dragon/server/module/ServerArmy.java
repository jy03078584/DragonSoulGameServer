/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerArmy.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-31 上午10:13:35
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;



import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.server.domain.ArmyDomain;
import com.lk.dragon.server.module.analysis.ArmyRequestAnalysis;
import com.lk.dragon.service.RoleHeroInfoService;
import com.lk.dragon.service.WarDeployInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerArmy
{
    /** 军事系统模块实体 **/
    private ArmyDomain armyDomain;
    
    private RoleHeroInfoService roleHeroInfoService;
    private WarDeployInfoService warDeployInfoService;
    public ServerArmy(){}
    
    /**
     * 构造函数
     * @param battleDomain
     */
    public ServerArmy(ArmyDomain armyDomain)
    {
        this.armyDomain = armyDomain;
        this.roleHeroInfoService = SpringBeanUtil.getBean(RoleHeroInfoService.class);
        this.warDeployInfoService = SpringBeanUtil.getBean(WarDeployInfoService.class);
    }
    
    /**
     * 战斗分析函数
     */
    public void armyAnalysis()
    {
        switch (armyDomain.getType())
        {
            case Constants.ARMY_RECRUIT_TYPE:
            {
                //兵源招募请求
                armyRecruit();
                break;
            }
            case Constants.PRODUCT_ARMY_NORMAL_TYPE:
            {
                //正常产兵
                productNormal();
                break;
            }
            case Constants.PRODUCT_ARMY_SPECIAL_TYPE:
            {
                //非正常产兵
                productSpecial();
                break;
            }
            case Constants.ARMY_RECRUIT_CANCEL_TYPE:
            {
                //兵源招募取消请求
                armyRecruitCancel();
                break;
            }
            case Constants.ARMY_RECRUIT_ACCE_TYPE:
            {
                //兵源招募加速请求
                armyRecruitAcce();
                break;
            }
            case Constants.ARMY_CAN_ALLOT_TYPE:
            {
                //兵源可分配列表查询
                armyCanAllot();
                break;
            }
            case Constants.HERO_EDIT_ARMY_TYPE:
            {
                //为英雄分配部队请求
                heroEditArmy();
                break;
            }
            case Constants.HERO_ARMY_LIST_TYPE:
            {
                //英雄部队总览表查询
                getHerosArmyList();
                break;
            }
            case Constants.CITY_ARMY_TYPE:
            {
                //城镇部队防守信息
                getCityArmy();
                break;
            }
            case Constants.HERO_TP_TYPE:
            {
                //英雄传送到其他城镇的功能(传送门)
                heroTransByTp();
                break;
            }
            case Constants.ARM_BUILD_INFO:
            {
            	//查看军事建筑兵种信息
            	getArmsBuildInfo();
            	break;
            }
            case Constants.HERO_ARM_INFO:
            {
            	//查看指定英雄军事信息
            	getHeroArmyByHeroId();
            	break;
            }
            case Constants.HERO_TRANS_INFO:
            {
            	//派遣部队到已方其他城邦(非传送门)
            	heroTans();
            	break;
            }
            case Constants.ARM_REINFORCE_INFO:
            {
            	//查看当前城邦增援信息
            	getReinforceInfo();
            	break;
            }
        }
    }
    

	/**
     * 查看当前城邦增援信息
     */
    private void getReinforceInfo() {

    	SocketUtil.responseClient(armyDomain.getCtx(), ArmyRequestAnalysis.getReinforceArmyListResponse(warDeployInfoService.selectCityReinforceInfo(armyDomain.getRole_id())));
	}

	/**
     * 派遣部队正常运输到已方其他城邦
     */
    private void heroTans() {
    	long teamId = 0;
    	String resStr = "";
    	try {
			Map<String,Long> resMap = warDeployInfoService.createWarTeam(armyDomain.getFrom_city_id(), armyDomain.getFrom_role_id(),Constants.TEAM_STATUS_REINFORCE,armyDomain.getTrans_time(), "", armyDomain.getTag_x(), armyDomain.getTag_x(), armyDomain.getTrans_food(), null, Constants.WAR_TYPE_RESIDSELF, null, armyDomain.getHerosId());
			teamId = resMap.get(WarDeployInfoService.WAR_TEAM_ID_KEY);
			long tagCityId = resMap.get(WarDeployInfoService.TAG_CITY_ID_KEY);
			
			JSONObject jsonObject = new JSONObject();
			if(teamId > 0){
	    		jsonObject.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
	    		jsonObject.put("team_id", teamId);
	    		jsonObject.put("tag_city_id", tagCityId);
	    		resStr = jsonObject.toString();
	    	}else if (teamId == -10l) {
				// 队列中有英雄状态不符合出征条件
	    		jsonObject.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
	    		jsonObject.put("reason", "队列中存在状态不符的英雄,请重建队列");
				
				resStr = jsonObject.toString();
			}else{
				resStr = JSONUtil.getBooleanResponse(false);
	    	}
			//返回信息
			SocketUtil.responseClient(armyDomain.getCtx(), resStr);
			//新增操作日志
            String detail = "成功派遣部队到其他城邦，目标城镇id:" + armyDomain.getFrom_city_id();
            OperateLogUtil.insertOperateLog(armyDomain.getFrom_role_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

    	} catch (Exception e) {
			e.printStackTrace();
			//新增操作日志
            String detail = "派遣部队到其他城邦失败，详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getFrom_role_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
    	

    }

	/**
     * /查看军事建筑兵种信息
     */
    private void getArmsBuildInfo() {
		int race = armyDomain.getRace();
		int build_type = armyDomain.getHire_build();
		ArmsDeploy armsDeploy = warDeployInfoService.getArmsBuildInfo(race, build_type);
		if(armsDeploy == null)
			SocketUtil.responseClient(armyDomain.getCtx(), JSONUtil.getBooleanResponse(false));
		SocketUtil.responseClient(armyDomain.getCtx(), ArmyRequestAnalysis.getArmsDetailInfo(armsDeploy));
	}
    
    
    
	/**
     * 招募兵源请求
     */
    private void armyRecruit()
    {
        //创建招募兵源队列
        long city_id = armyDomain.getCity_id();
        int soldierId = armyDomain.getSoldierId();
        int totalTime = armyDomain.getTotal_time();
        int oneTime = armyDomain.getOne_time();
        int goldNum = armyDomain.getUse_gold();
        long buildId = armyDomain.getBuild_id();
        long roleId = armyDomain.getRole_id();
        int eat = armyDomain.getEat();
        boolean result = false;
        try {
			result = warDeployInfoService.createConscriptTeam(
			        city_id, soldierId, totalTime, oneTime, goldNum, buildId, roleId, eat);
			//新增操作日志
            String detail = "成功创建招募兵源队列，城镇id:" + city_id + ",兵种id:" + soldierId;
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "创建招募兵源队列失败，详细信息:" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getFrom_role_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        //响应客户端
        SocketUtil.responseClient(armyDomain.getCtx(),
                    JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 正常产兵：一个周期生产一个兵
     */
    private void productNormal()
    {
        //正常周期内产兵
        int eat = armyDomain.getEat();
        long city_id = armyDomain.getCity_id();
        int soldierId = armyDomain.getSoldierId();
        long roleId = armyDomain.getRole_id();
        int oneTime = armyDomain.getOne_time();
        boolean result = false;
        try {
			result = warDeployInfoService.productArmNormal(
			        city_id, roleId, soldierId, oneTime, eat);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "产兵异常，详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        //响应客户端
        SocketUtil.responseClient(armyDomain.getCtx(),
                    JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 非正常产兵：下线重连后，多个兵一起生产请求
     */
    private void productSpecial()
    {
        //下线重连时，查询产兵队列
        long roleId = armyDomain.getRole_id();
        List<ArmsDeploy> armyList = null;
        try {
			armyList = warDeployInfoService.productArmbreakPoint(roleId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "断线重连后产兵异常，详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        
        //获取响应客户端字符串
        String productResponse = ArmyRequestAnalysis.getArmyListResponse(armyList, 1);
       SocketUtil.responseClient(armyDomain.getCtx(), productResponse);
    }

    /**
     * 部队招募取消请求
     */
    private void armyRecruitCancel()
    {
        //募兵取消请求
        long roleId = armyDomain.getRole_id();
        long cityId = armyDomain.getCity_id();
        int armyId = armyDomain.getSoldierId();
        int gold = armyDomain.getUse_gold();
        boolean result = false;
        try {
			result = warDeployInfoService.cancelConscriptTeam(roleId, gold, cityId, armyId);
			//新增操作日志
            String detail = "取消募兵队列成功";
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "取消募兵队列异常，详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        //响应客户端请求
        SocketUtil.responseClient(armyDomain.getCtx(), 
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 部队招募加速请求
     */
    private void armyRecruitAcce()
    {
        //募兵加速请求
        long roleId = armyDomain.getRole_id();
        long cityId = armyDomain.getCity_id();
        int armyId = armyDomain.getSoldierId();
        int useDiamond = armyDomain.getUse_diamond();
        int armyCount = armyDomain.getRecruit_num();
        int eat = armyDomain.getEat();
        boolean result = false;
        try {
			result = warDeployInfoService.
			        usePropsHiredArms(roleId, useDiamond, cityId, armyId, eat, armyCount);
		
			//新增操作日志
            String detail = "募兵加速请求成功";
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "募兵加速请求异常，详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        
        //响应客户端请求
        SocketUtil.responseClient(armyDomain.getCtx(), 
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 当前可分配兵源列表查询
     */
    private void armyCanAllot()
    {
        //查看城镇的可分配兵源信息
        long city_id = armyDomain.getCity_id();
        List<ArmsDeploy> armyList = 
                warDeployInfoService.selectCityArmsInfo(city_id);
        
        //获取响应字符串
        String canAllotArmyResponse = 
                ArmyRequestAnalysis.getArmyListResponse(armyList, 2);
        SocketUtil.responseClient(armyDomain.getCtx(), canAllotArmyResponse);
    }
    
    /**
     * 英雄所率领部队信息列表查询
     */
    private void getHerosArmyList()
    {
        //查询英雄已经分配所得的兵源
       long role_id = armyDomain.getRole_id();
        
        List<RoleHero> heroList = 
        		warDeployInfoService.selectHeroArmsStatus(role_id);
        
        //获取响应字符串
        String heroArmyResponse =
                ArmyRequestAnalysis.getHeroArmyListResponse(heroList);
        SocketUtil.responseClient(armyDomain.getCtx(), heroArmyResponse);
    }
    
    /**
     * 获取指定英雄当前军事信息
     */
    private void getHeroArmyByHeroId(){
    	//英雄ID
    	long roleHeroId = armyDomain.getRoleHeroId();
    	
    	List<ArmsDeploy> heroArms = warDeployInfoService.selectHeroArmsDetailByHeroId(roleHeroId);
    	

    		//获取响应字符串
    		String heroArmsResponse = ArmyRequestAnalysis.getHeroArmyResponseByHeroId(heroArms);
    		SocketUtil.responseClient(armyDomain.getCtx(), heroArmsResponse);
    	
    	
    }

    /**
     * 修改英雄带兵量请求
     */
    private void heroEditArmy()
    {
        //同步英雄带兵、城镇佣兵数据
        long cityId = armyDomain.getCity_id();
        //英雄相关信息
        RoleHero roleHero = new RoleHero();
        roleHero.setRole_hero_id(armyDomain.getRoleHeroId());

        //城镇部队相关信息
        List<ArmsDeploy> cityArms = armyDomain.getCityArmyList();
        List<ArmsDeploy> heroArms = armyDomain.getHeroArmyList();
        
        boolean result = false;
        try {
			result = warDeployInfoService.
			        adjustHeroArms(cityArms, cityId, heroArms);
			//新增操作日志
            String detail = "英雄带兵量修改成功，英雄id:" + armyDomain.getRoleHeroId();
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//新增操作日志
            String detail = "英雄带兵量修改失败，详细信息:" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        
        //响应客户端
        SocketUtil.responseClient(armyDomain.getCtx(), 
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 城镇兵力部署情况查询请求
     */
    private void getCityArmy()
    {
         
    }
    
    /**
     * 英雄派驻到其他城镇
     */
    private void heroTransByTp()
    {
    	try {
    		List<Long> heroIdList = armyDomain.getHeroIds();
    		long cityId = armyDomain.getCity_id();
    		int result = roleHeroInfoService.transportArmsBetCitys(heroIdList, cityId,armyDomain.getFrom_city_id(),armyDomain.getTrans_level());
    		if(result>0){
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("result", 1);
    			jsonObject.put("trans_cd", result);
    			//响应客户端
    			SocketUtil.responseClient(armyDomain.getCtx(), jsonObject.toString());
    			
    			//新增操作日志
                String detail = "英雄传送到指定城镇成功，英雄id:" + armyDomain.getHeroIds() + ",目标城镇id:" + cityId;
                OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.SUCCESS);

    		}else{
    			SocketUtil.responseClient(armyDomain.getCtx(), 
    					JSONUtil.getBooleanResponse(false));
    			
    			//新增操作日志
                String detail = "英雄传送到指定城镇失败";
                OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

    		}
		} catch (Exception e) {
			e.printStackTrace();
			//新增操作日志
            String detail = "英雄传送到指定城镇失败, 详细信息：" + OperateLogUtil.getExceptionStackInfo(e);
            OperateLogUtil.insertOperateLog(armyDomain.getRole_id(), detail, OperateLogUtil.ARMY_MODULE, OperateLogUtil.FAIL);

		}
        //英雄派驻功能
    }
    
    
    
}
