/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerHero.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-11 下午4:01:07
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Hero;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RolePropsEquipPro;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.HeroDomain;
import com.lk.dragon.server.module.analysis.HeroRequestAnalysis;
import com.lk.dragon.service.RoleHeroInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerHero
{
    /** 战斗数据实体 **/
    private HeroDomain heroDomain;
    @Autowired
    private RoleHeroInfoService roleHeroInfoService;
    @Autowired
    private RolePropsInfoService rolePropsInfoService;
    @Autowired
    private SqlToolsService toolsService;
    public ServerHero(){}
    
    /**
     * 构造函数
     * @param battleDomain
     */
    public ServerHero(HeroDomain heroDomain)
    {
        this.heroDomain = heroDomain;
        this.roleHeroInfoService = SpringBeanUtil.getBean(RoleHeroInfoService.class);
        this.rolePropsInfoService = SpringBeanUtil.getBean(RolePropsInfoService.class);
        this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
    }
    
    /**
     * 战斗分析函数
     */
    public void heroAnalysis()
    {
        switch (heroDomain.getType())
        {
            case Constants.CAN_CALL_HERO_LIST_TYPE:
            {
                //英雄列表查询（可召唤英雄列表）
                canCallHeroList();
                break;
            }
            case Constants.HAVE_HERO_LIST_TYPE:
            {
                //英雄列表查询（角色拥有的英雄列表）
                haveHeroList();
                break;
            }
            case Constants.CALL_HERO_TYPE:
            {
                //召唤英雄
                callHero();
                break;
            }
            case Constants.HERO_DELETE_TYPE:
            {
                //解雇英雄
                deleteHero();
                break;
            }
            case Constants.HERO_EQUIP_TYPE:
            {
                //英雄 穿戴装备
                equipHero();
                break;
            }
            case Constants.HERO_REMOVE_EQUIP_TYPE:
            {
                //英雄卸下装备
                removeHeroEquip();
                break;
            }
            case Constants.HERO_REPLACE_EQUIP_TYPE:
            {
                //英雄换装
                replaceHeroEquip();
                break;
            }
            case Constants.HERO_DETAIL_INFO_TYPE:
            {
                //获取英雄的详细信息
                getHeroListDetailInfo();
                break;
            }
            case Constants.HERO_CALL_REFRESH_TYPE:
            {
                //刷新可招募的英雄
                refreshCanCallHero();
                break;
            }
            case Constants.CALL_HERO_DETAIL_INFO_TYPE:
            {
                //可招募英雄的详细信息查询
                canCallHeroDetailInfo();
                break;
            }
            case Constants.HERO_USE_MEDICINE_TYPE:
            {
                //英雄使用药品
                heroUseMedicine();
                break;
            }
            case Constants.HERO_RELIVE_TYPE:
            {
                //英雄复活请求
                heroRevive();
                break;
            }
            case Constants.HERO_UPGRADE_TYPE:
            {
                //英雄升级请求
                heroUpgrade();
                break;
            }
            case Constants.HERO_ASSIGN_TYPE:
            {
                //英雄分配加点
                heroAssign();
                break;
            }
            case Constants.HERO_EQUIP_BASE_INFO_TYPE:
            {
            	//英雄已穿戴装备基本信息
            	getHeroEquipBase();
            	break;
            }
            case Constants.HERO_CHANGE_NAME_TYPE:
            {
            	//英雄改名
            	changeHeroName();
            	break;
            }
            case Constants.HERO_BEGIN_TRAIN:
            {
            	//英雄训练
            	heroTrain();
            	break;
            }
            case Constants.CITY_TRAIN_INFO:
            {
            	//当前城邦训练室信息
            	getHeroTrainInfo();
            	break;
            }
            case Constants.CANCEL_TRAIN:
            {
            	//中断训练
            	cancelHeroTrain();
            	break;
            }
            case Constants.HERO_REVIVE_FINISH:
            {
            	//英雄复活完成
            	heroReviFinish();
            	break;
            }
        }
    }
    

    /**
     * 英雄复活完成
     */
    private void heroReviFinish() {
    	boolean flag = false;
    	try {
			roleHeroInfoService.heroReviFinish(heroDomain.getRoleHeroId());
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	SocketUtil.responseClient(heroDomain.getCtx(), JSONUtil.getBooleanResponse(flag));
	}

	/**
     * 终止英雄训练
     */
    private void cancelHeroTrain() {
    	List<RoleHero> heros = null;
    	long roleId= heroDomain.getRoleId();
    	int logRes = Constants.LOG_RES_FAIL;
    	
    	String title = "";
    	 try {
			heros = rolePropsInfoService.cancelHeroTrain(heroDomain.getRoleHeroId(), heroDomain.getCity_id());
			title = "终止英雄训练\r\n"+"操作人ID："+roleId+"\r\n被终止城邦ID:"+heroDomain.getCity_id();
			logRes = Constants.LOG_RES_SUCESS;
    	 } catch (Exception e) {
			heros = null;
			title = OperateLogUtil.getExceptionStackInfo(e);
			e.printStackTrace();
		}
    	toolsService.addNewLogInfo(new Tools(roleId, "英雄模块", title, logRes));
    	String responseStr = HeroRequestAnalysis.getHeroListInfo(heros);
    	SocketUtil.responseClient(heroDomain.getCtx(), responseStr);
    }

	/**
     * 英雄改名
     */
    private void changeHeroName() {
    	boolean flag = roleHeroInfoService.changeHeroName(heroDomain.getHero_name(),heroDomain.getRoleHeroId());
    	SocketUtil.responseClient(heroDomain.getCtx(), JSONUtil.getBooleanResponse(flag));
	}

	/**
     * 英雄已穿戴装备基本信息
     */
    private void getHeroEquipBase() {
    	long role_hero_id = heroDomain.getRoleHeroId();
    	List<RolePropsEquipPro> heroEquips = rolePropsInfoService.getHeroOnEquipBase(role_hero_id);
    	String equipListResponse = HeroRequestAnalysis.getHeroOnEquipBase(heroEquips);
    	SocketUtil.responseClient(heroDomain.getCtx(), equipListResponse);
	}
    
	/**
     * 英雄列表查询（可召唤英雄列表）
     */
    private void canCallHeroList()
    {
    	try {
    		long city_id = heroDomain.getCity_id();
    		//根据该城镇的id查出随机英雄列表
    		List<Hero> heroList = roleHeroInfoService.getHeroCanHired(city_id);
    		
    		//将英雄列表返回客户端
    		String heroListResponse = HeroRequestAnalysis.getCityHeroInfo(heroList);
    		SocketUtil.responseClient(heroDomain.getCtx(), heroListResponse);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 英雄列表查询（角色拥有的英雄列表）
     */
    private void haveHeroList()
    {
        //根据客户端传送的角色id，查询该角色用户的英雄列表信息
        long role_id = heroDomain.getRoleId();
        List<RoleHero> heroList = roleHeroInfoService.getHerosByRoleId(role_id);
        
        //将英雄列表的概述信息返回
        String heroListResponse = HeroRequestAnalysis.getHeroListInfo(heroList);
        SocketUtil.responseClient(heroDomain.getCtx(), heroListResponse);
    }
    
    /**
     * 召唤英雄
     */
    private void callHero()
    {
        //将酒馆中展示的英雄绑定到角色中
        long roleId = heroDomain.getRoleId();
        int heroId = heroDomain.getHeroId();
        int goldNum = heroDomain.getGoldNum();
        int physique = heroDomain.getPhysique();
        int mentality = heroDomain.getMentality();
        int power = heroDomain.getHero_power();
        int endurance = heroDomain.getEndurance();
        int agility = heroDomain.getAgility();


        long roleHeroId = 0l;
        
        int logRes = Constants.LOG_RES_FAIL;
    	
    	String title = "";
        try {
			roleHeroId = roleHeroInfoService.hireNewHero(roleId,heroId,physique,mentality,power,endurance,agility,goldNum);
			title = "招募英雄\r\n"+"操作人ID："+roleId+"\r\n英雄原始ID:"+heroId;
			logRes = Constants.LOG_RES_SUCESS;
        } catch (Exception e) {
			roleHeroId = -1l;
			title = OperateLogUtil.getExceptionStackInfo(e);
			e.printStackTrace();
		}
    	toolsService.addNewLogInfo(new Tools(roleId, "英雄模块", title, logRes));

        //响应客户端
        String hireHeroResponse = HeroRequestAnalysis.getHireHeroResponse(roleHeroId);
        SocketUtil.responseClient(heroDomain.getCtx(), hireHeroResponse);
    }
    
    /**
     * 解雇英雄
     */
    private void deleteHero()
    {
        //接收角色-英雄关联表id
        //将角色-英雄关联表相关数据删掉
        long roleHeroId = heroDomain.getRoleHeroId();
        long roleId = heroDomain.getRoleId();
        int result = -1;
        
        int logRes = Constants.LOG_RES_FAIL;
    	JSONObject resJ = new JSONObject();
    	String title = "";
		try {
			result = roleHeroInfoService.fireHero(roleHeroId);
			if(result == 1){
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
				title = "解雇英雄\r\n操作人ID："+roleId+"\r\n被解雇英雄ID:"+roleHeroId;
				logRes = Constants.LOG_RES_SUCESS;
				
			}else{
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", "不能解雇携带部队或穿戴装备的英雄");
			}
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
			resJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
		}
		toolsService.addNewLogInfo(new Tools(roleId, "英雄模块", title, logRes));
        //响应客户端 处理结果
        SocketUtil.responseClient(heroDomain.getCtx(), resJ.toString());
    }
    
    /**
     * 英雄穿戴装备
     */
    private void equipHero()
    {
        //角色-道具id
        long rolePropId = heroDomain.getHeroaddPropId();
        
        //角色-英雄id
        long roleHeroId = heroDomain.getRoleHeroId();
        //穿戴装备 
        boolean result = rolePropsInfoService.onOffHeroEquip(
                roleHeroId, rolePropId, 1);
        
        if(result)
        {
        	//重新获取英雄详细信息
        	getHeroDetailInfo();
        }
        else{
        	//回写处理信息
            SocketUtil.responseClient(
                    heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
            return ;
        }
        
//        //回写处理信息
//        SocketUtil.responseClient(
//                heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 卸下英雄装备
     */
    private void removeHeroEquip()
    {
        //角色-道具id
        long rolePropId = heroDomain.getHeroDelePropId();
        //角色-英雄id
        long roleHeroId = heroDomain.getRoleHeroId();
        //穿戴装备 
        boolean result = rolePropsInfoService.onOffHeroEquip(
                roleHeroId, rolePropId, 0);
        
        if(result)
        {
        	//重新获取英雄详细信息
        	getHeroDetailInfo();
        }
        else{
        	//回写处理信息
            SocketUtil.responseClient(
                    heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
            return ;
        }
        
        
//        //回写处理信息
//        SocketUtil.responseClient(
//                heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 英雄换装
     */
    private void replaceHeroEquip()
    {
        /********     英雄脱下装备                   ********/
        //角色-英雄id
        long roleHeroId = heroDomain.getRoleHeroId();
        
        //角色-道具id
        long roleOffPropId = heroDomain.getHeroDelePropId();
        //卸下装备 
        boolean result = rolePropsInfoService.onOffHeroEquip(
                roleHeroId, roleOffPropId, 0);
        
        if (!result)
        {
            //回写处理信息
            SocketUtil.responseClient(
                    heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
            return ;
        }

        /********     如果脱下装备成功，则英雄穿上装备                   ********/
        //角色-英雄id
        long roleWearPropId = heroDomain.getHeroaddPropId();
        //穿戴装备 
        result = rolePropsInfoService.onOffHeroEquip(
                roleHeroId, roleWearPropId, 1);
        
        
        if (!result)
        {
            //回写处理信息
            SocketUtil.responseClient(
                    heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
            return ;
        }
        
//        //回写处理信息
//        SocketUtil.responseClient(
//                heroDomain.getCtx(), JSONUtil.getBooleanResponse(result));
        
        
      //重新获取英雄详细信息
        getHeroDetailInfo();
    }
    
    /**
     * 获取指定英雄的详细信息
     */
    private void getHeroDetailInfo()
    {
        //查询英雄的详细信息
        long roleHeroId = heroDomain.getRoleHeroId();
        RoleHero hero = roleHeroInfoService.getHeroPropertyById(roleHeroId);
        
        //获取响应字符串
        String heroInfoResponse = HeroRequestAnalysis.getHeroDetailInfo(hero);
        SocketUtil.responseClient(heroDomain.getCtx(), heroInfoResponse);
    }
    
    
    /**
     * 获取英雄列表详细信息
     */
    private void getHeroListDetailInfo()
    {
        //查询英雄的详细信息
        List<RoleHero> heros = null;
		try {
			heros = roleHeroInfoService.getHeroPropertyList(heroDomain.getHeroIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
        //获取响应字符串
        String heroInfoResponse = HeroRequestAnalysis.getHeroListDetailInfo(heros);
        SocketUtil.responseClient(heroDomain.getCtx(), heroInfoResponse);
    }
    
    /**
     * 刷新可招募的英雄
     */
    private void refreshCanCallHero()
    {
        //刷新英雄
        long city_id = heroDomain.getCity_id();
        long role_id = heroDomain.getRoleId();
        int use_diamon = heroDomain.getUse_diamon();
        List<Hero> heroList = null;
        
        try {
			heroList = roleHeroInfoService.usePropsFlushHeros(
			        city_id, role_id, use_diamon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //获取相应字符串
        String heroListResponse = HeroRequestAnalysis.getCityHeroInfo(heroList);
        SocketUtil.responseClient(heroDomain.getCtx(), heroListResponse);
    }
    
    /**
     * 可招募英雄的向信息查询
     */
    private void canCallHeroDetailInfo()
    {
        //查询该英雄的详细信息
        int heroId = heroDomain.getHeroId();
        Hero hero = roleHeroInfoService.getHeroBaseProperties(heroId);
        
        //获取响应字符串
        String heroDetailInfo = HeroRequestAnalysis.baseHeroDetailResponse(hero);
        SocketUtil.responseClient(heroDomain.getCtx(), heroDetailInfo);
    }
    
    /**
     * 英雄喝药
     */
    private void heroUseMedicine()
    {
        //某个 英雄使用增益药品
        long roleHeroId = heroDomain.getRoleHeroId();
        long rolePropId = heroDomain.getRolePropId();
        int replyQuantity = heroDomain.getReply_quantity();
        int medicineType = heroDomain.getMedicine_type();
        JSONObject resJ = new JSONObject();
        try {
			roleHeroInfoService.usePropsAddHpMp(
			        roleHeroId, rolePropId, replyQuantity, medicineType);
			resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
		} catch (Exception e) {
			if(e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)){
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
			}else{
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
			}
			e.printStackTrace();
		}
        
        //获取相应字符串
        SocketUtil.responseClient(
                heroDomain.getCtx(), resJ.toString());
    }
    
    //英雄进入训练室
    private void heroTrain(){
    	int type =1;
    	RoleHero  roleHero = new RoleHero();
    	
    	if(heroDomain.getGoldNum() == 0){
    		type =2 ;
    		roleHero.setUse_gold_dimaon(heroDomain.getUse_diamon());
    	}else{
    		type = 1;
    		roleHero.setUse_gold_dimaon(heroDomain.getGoldNum());
    	}
    	roleHero.setRole_hero_id(heroDomain.getRoleHeroId());
    	roleHero.setTrain_time(heroDomain.getTrain_time() * 60 * 60);
    	roleHero.setPre_exp(heroDomain.getPre_exp());
    	roleHero.setCity_id(heroDomain.getCity_id());
    	roleHero.setRole_id(heroDomain.getRoleId());
    	boolean res = false;
    	try {
			res = rolePropsInfoService.addHeroTraiin(roleHero, type);
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
    	SocketUtil.responseClient(heroDomain.getCtx(), JSONUtil.getBooleanResponse(res));
    }
    
    //查看当前城邦训练室内英雄
    private void getHeroTrainInfo(){
    	
    	String res = HeroRequestAnalysis.getHeroListInfo(rolePropsInfoService.getHeroTrainInfo(heroDomain.getCity_id()+""));
    	SocketUtil.responseClient(heroDomain.getCtx(), res);
    }

    
 
    
    /**
     * 英雄复活请求
     */
    private void heroRevive()
    {
        //获取需要复活的英雄信息
        long roleHeroId = heroDomain.getRoleHeroId();
        long roleId = heroDomain.getRoleId();
        int heroLev= heroDomain.getHeroLev();
        //复活英雄  
        JSONObject errJ = new JSONObject();
		try {
			Map<String,Integer> resMap = roleHeroInfoService.heroRevive(roleId,roleHeroId,heroLev);
			errJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			errJ.put(RoleHeroInfoService.HERO_REV_TIME, resMap.get(RoleHeroInfoService.HERO_REV_TIME));
			errJ.put(RoleHeroInfoService.HERO_REV_GOLD, resMap.get(RoleHeroInfoService.HERO_REV_GOLD));
		} catch (Exception e) {
			//捕获指定特殊异常
			if(e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)){
				errJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				errJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
			}else{
				errJ.put(Constants.RESULT_KEY, Constants.NET_ERROR);
			}
			e.printStackTrace();
		}
        
        //响应客户端
        SocketUtil.responseClient(heroDomain.getCtx(),errJ.toString());
    }
    
    
    /**
     * 英雄升级请求
     */
    private void heroUpgrade()
    {
        //获取英雄的当前信息
        long role_hero_id = heroDomain.getRoleHeroId();
        RoleHero roleHero = roleHeroInfoService.getHeroPropertyById(role_hero_id);
        //获取英雄的当前生命、魔法值
        int max_hp = roleHero.getHp_max_show();
        int max_mp = roleHero.getMp_max_show();
        int hp = roleHero.getHp();
        int mp = roleHero.getMp();
        //为英雄添加增加的属性值
        roleHero.setRole_hero_id(role_hero_id);
        
        int oldLev = roleHero.getHero_lev();
        roleHero.setHero_lev(oldLev + 1);
        roleHero.setAgility(roleHero.getAgility() + Constants.ADD_POINT);
        roleHero.setPhysique(roleHero.getPhysique() + Constants.ADD_POINT);
        roleHero.setMentality(roleHero.getMentality() + Constants.ADD_POINT);
        roleHero.setHero_power(roleHero.getHero_power() + Constants.ADD_POINT);
        roleHero.setEndurance(roleHero.getEndurance() + Constants.ADD_POINT);
        roleHero.setCan_assign_point(roleHero.getCan_assign_point() + Constants.CAN_ASSIGN_POINT);
        
        //当前等级段 统帅值增幅
        int commandAdd = oldLev/10 == 0 ? 5 : (oldLev/10) * 10;
        roleHero.setCommand(roleHero.getCommand() + commandAdd);
        
        //将信息入库
        //增加生命值、魔法值
        roleHero = RoleHeroInfoService.calculateHeroPropertyShow(roleHero);
        roleHero.setHp(hp + (roleHero.getHp_max_show() - max_hp));
        roleHero.setMp(mp + (roleHero.getMp_max_show() - max_mp));
        Map<String,Integer> resMap = roleHeroInfoService.roleHeroUpgrade(roleHero, 1);
        
        //获取响应字符串
        String responseStr = "";
        if (resMap.get("next_exp") == -1)
        {
            responseStr = JSONUtil.getWrongResponse(Constants.NET_ERROR);
        }
        else
        {
            //获取加点后显示信息
            roleHero.setHero_up_exp(resMap.get("next_exp"));
            roleHero.setHero_exp(resMap.get("last_exp"));
            //回写英雄的详细信息
            responseStr = HeroRequestAnalysis.getHeroDetailInfo(roleHero);
        }
       SocketUtil.responseClient(heroDomain.getCtx(), responseStr);
    }
    
    /**
     * 英雄分配加点请求
     */
    private void heroAssign()
    {
        //获取英雄加点后属性值信息
        RoleHero roleHero = roleHeroInfoService
                .getHeroPropertyById(heroDomain.getRoleHeroId());
        roleHero.setRole_hero_id(heroDomain.getRoleHeroId());
        roleHero.setAgility(heroDomain.getAgility());
        roleHero.setMentality(heroDomain.getMentality());
        roleHero.setPhysique(heroDomain.getPhysique());
        roleHero.setEndurance(heroDomain.getEndurance());
        roleHero.setHero_power(heroDomain.getHero_power());
        roleHero.setCan_assign_point(heroDomain.getCan_assign_point());
        //缓存当前生命值信息
        int max_hp = roleHero.getHp_max_show();
        int max_mp = roleHero.getMp_max_show();
        int hp = roleHero.getHp();
        int mp = roleHero.getMp();
        
        //将信息入库
        roleHero = RoleHeroInfoService.calculateHeroPropertyShow(roleHero);
        roleHero.setHp(hp + (roleHero.getHp_max_show() - max_hp));
        roleHero.setMp(mp + (roleHero.getMp_max_show() - max_mp));
        Map<String,Integer> resMap = roleHeroInfoService.roleHeroUpgrade(roleHero, 2);
        String responseStr = "";
        if (resMap.get("next_exp") == -1)
        {
            responseStr = JSONUtil.getWrongResponse(Constants.NET_ERROR);
        }
        else
        {
            //获取加点后显示信息
            roleHero = RoleHeroInfoService.calculateHeroPropertyShow(roleHero);
            //回写英雄的详细信息
            responseStr = HeroRequestAnalysis.getHeroDetailInfo(roleHero);
        }
        SocketUtil.responseClient(heroDomain.getCtx(), responseStr);
    }    
}
