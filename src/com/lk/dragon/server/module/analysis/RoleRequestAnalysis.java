/**
 *
 *
 * 文件名称： RoleAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:33:27
 */
package com.lk.dragon.server.module.analysis;

import java.text.ParseException;
import java.util.Map;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;

import com.lk.dragon.db.domain.Role;
import com.lk.dragon.server.domain.CityDomain;
import com.lk.dragon.server.domain.RoleDomain;
import com.lk.dragon.server.module.ServerCity;
import com.lk.dragon.service.FactionInfoService;
import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class RoleRequestAnalysis
{

	private static RoleInfoService roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);; 
	
	private static FactionInfoService factionInfoService = SpringBeanUtil.getBean(FactionInfoService.class);
    //角色请求相关字段 
    private static final String USER_ID_KEY = "user_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String ROLE_NAME_KEY = "role_name";
    private static final String ROLE_TYPE_KEY = "roleType";
    private static final String ROLE_RACE_KEY = "race";
    private static final String ROLE_ICON_KEY = "icon";
    private static final String GUILD_ID_KEY = "guild_id";
    private static final String ROLE_PROPS_ID_KEY = "rolePropId";
    
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    
    //判定是否登录字段
    private static final String ROLE_DUKE_KEY = "duke";
    private static final String ROLE_LEVEL_KEY = "level";
    private static final String ROLE_EXP_KEY = "exp";
    private static final String ROLE_UP_EXP_KEY = "up_exp";
    private static final String ROLE_SEX_KEY = "sex";
    private static final String ROLE_GOLD_KEY = "gold";
    private static final String ROLE_SHT_ICO_KEY = "sht_ico";
    private static final String ROLE_FOOD_KEY = "food";
    private static final String ROLE_WOOD_KEY = "wood";
    private static final String ROLE_STONE_KEY = "stone";
    private static final String ROLE_YIELD_FOOD_KEY = "yield_food";   
    private static final String ROLE_YIELD_WOOD_KEY = "yield_wood";   
    private static final String ROLE_YIELD_STONE_KEY = "yield_stone";    
    private static final String ROLE_CITY_NUM_KEY = "city_num";
    private static final String ROLE_DIAMON_NUM_KEY = "diamon_num";
    private static final String ROLE_EAT_NUM_KEY = "eat_num";
    private static final String ROLE_BAG_NUM_KEY = "bag_num";
    private static final String ROLE_BATTLE_COUNT_KEY = "battle_count";//当日剩余征战次数
    private static final String ROLE_FIRST_LOGIN_KEY = "first_login";//当日首次登陆
    private static final String ROLE_MARK_COUNT_KEY = "mark_count";//签到次数
    

    private static final String CHEST_KEY = "chest";//奖励
    private static final String LOCAL_KEY = "local";//领取
    
    //角色请求相关响应字段
    private static final String ROLE_LIST_KEY = "roles";
    
    
    
   
    /**
     * 获取角色请求相关的信息
     * @param json
     * @return
     */
    public static RoleDomain getRoleInfo(String json)
    {
    	System.out.println(json);
        //获取data信息
        JSONObject roleData = JSONUtil.getData(json);
        //创建聊天实体
        RoleDomain roleDomain = new RoleDomain();
        roleDomain.setType(roleData.getInt(ROLE_TYPE_KEY));
        //判定是否有该键值传递过来，然后才会解析
        roleDomain.setUser_id(roleData.has(USER_ID_KEY) ? roleData.getLong(USER_ID_KEY) : 0);
        roleDomain.setRole_id(roleData.has(ROLE_ID_KEY) ? roleData.getLong(ROLE_ID_KEY) : 0);
        roleDomain.setRolePropId(roleData.has(ROLE_PROPS_ID_KEY) ? roleData.getLong(ROLE_PROPS_ID_KEY) : 0);
        roleDomain.setGuildId(roleData.has(GUILD_ID_KEY) ? roleData.getLong(GUILD_ID_KEY) : 0);
        roleDomain.setRole_name(roleData.has(ROLE_NAME_KEY) ? roleData.getString(ROLE_NAME_KEY) : null);
        roleDomain.setRace(roleData.has(ROLE_RACE_KEY) ? roleData.getInt(ROLE_RACE_KEY) : 0);
        roleDomain.setIcon(roleData.has(ROLE_ICON_KEY) ? roleData.getString(ROLE_ICON_KEY) : null);
        roleDomain.setSex(roleData.has(ROLE_SEX_KEY) ? roleData.getInt(ROLE_SEX_KEY) : 0);
        roleDomain.setShortIcon(roleData.has(ROLE_SHT_ICO_KEY) ? roleData.getString(ROLE_SHT_ICO_KEY) : null);
        roleDomain.setLocal(roleData.has(LOCAL_KEY) ?  roleData.getInt(LOCAL_KEY) : -1);
        return roleDomain;
    }
    
    /** =======================【响应信息json字符串构成】============================= **/
    /**
     * 获取角色模块相关回复信息
     * @param result 处理结果
     * @param requestType 请求类型，用于区分是那种类型请求
     * @return
     */
    public static String getRoleResponseInfo(long result, int requestType)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (requestType == Constants.ROLE_DELETE_TYPE)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, result);
            
            if (result == Constants.REQUEST_FAIL)
            {
                resultObj.put(REASON_KEY, Constants.NET_ERROR);
            }
        }
        else if (requestType == Constants.ROLE_UPDATE_TYPE)
        {
            //修改角色
            if (result == Constants.CREATE_ROLE_NAMEREPEAT)
            {
                //用户名重复
                resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
                resultObj.put(REASON_KEY, Constants.ROLE_NAME_REPEAT);
            }
            else if (result == Constants.CREATE_ROLE_SUCCESS)
            {
                resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            }
            else if (result == Constants.CREATE_ROLE_FAIL)
            {
                resultObj.put(RESULT_KEY, Constants.REQUEST_ERROR);
                resultObj.put(REASON_KEY, Constants.NET_ERROR);
            }
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取列表信息
     * @param result
     * @param roleId
     * @return
     */
    public static String getAddRoleResponseInfo(Map<String, Long> map, long user_id)
    {
//    	roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);
//    	factionInfoService = SpringBeanUtil.getBean(FactionInfoService.class);
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        long result = map.get(Constants.RESULT_KEY);
        long roleId = map.get(Constants.ID_KEY);
        
        //新增角色
        //判定状态
        if (result == Constants.CREATE_ROLE_NAMEREPEAT)
        {
            //角色名重复
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.ROLE_NAME_REPEAT);
        }
        else if (result == Constants.CREATE_ROLE_NUM_MAX)
        {
            //角色用户数已达上限
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.ROLE_NUM_MAX);
        }
        else if (result == Constants.CREATE_ROLE_FAIL)
        {
            //添加角色异常
            resultObj.put(RESULT_KEY, Constants.REQUEST_ERROR);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        else if (result == Constants.CREATE_ROLE_SUCCESS)
        {
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
           Role role =  roleInfoService.findRolesByUserId(user_id);
            //获取角色列表信息
            String roleArrStr = getRoleListResponseInfo(role);
            
            //随机地点建造一座主城
            CityDomain cityDomain = new CityDomain();
            cityDomain.setRace(role.getRace());
            cityDomain.setRoldId(roleId);
            cityDomain.setCityType(Constants.MAIN_CITY);
            
            new ServerCity(cityDomain).buildCity();
            return roleArrStr;
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取角色列表信息
     * @param user_id
     * @return
     */
   /* public static String getRoleListResponseInfo(Role role)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        //获取角色列表信息
        JSONArray roleArr = getRoleList(user_id);
        resultObj.put(ROLE_LIST_KEY, roleArr);
        
        return resultObj.toString();
    }*/
    
    /**
     * 获取角色列表信息
     * @param user_id　用户id
     * @return
     */
    public static String getRoleListResponseInfo(Role r)
    {
    	JSONObject resObj = null;
    	try {
            //创建json对象
            resObj = new JSONObject();
            
    		 //查询用户列表信息
             JSONArray arr = new JSONArray();
            // Role role = new Role();
             
             if (r != null)
             {
                 resObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
                 //创建json对象
                 JSONObject roleObj = new JSONObject();
                 
                 roleObj.put(USER_ID_KEY, r.getUser_id());
                 roleObj.put(ROLE_ID_KEY, r.getRole_id());
                 roleObj.put(ROLE_RACE_KEY, r.getRace());
                 roleObj.put(ROLE_ICON_KEY, r.getIcon());
                 roleObj.put(ROLE_SHT_ICO_KEY, r.getSht_ico());
                 roleObj.put(ROLE_NAME_KEY, r.getRole_name());
                 roleObj.put(ROLE_DUKE_KEY, r.getDuke());
                 roleObj.put(ROLE_LEVEL_KEY, r.getLev());
                 roleObj.put(ROLE_EXP_KEY, r.getExp());
                 roleObj.put(ROLE_UP_EXP_KEY, r.getUp_exp());
                 roleObj.put(ROLE_SEX_KEY, r.getSex());
                 roleObj.put(ROLE_GOLD_KEY, r.getGold());
                 roleObj.put(ROLE_FOOD_KEY, r.getFood());
                 roleObj.put(ROLE_WOOD_KEY, r.getWood());
                 roleObj.put(ROLE_STONE_KEY, r.getStone());
                 roleObj.put(ROLE_YIELD_FOOD_KEY, r.getYield_food());
                 roleObj.put(ROLE_YIELD_WOOD_KEY, r.getYield_wood());
                 roleObj.put(ROLE_YIELD_STONE_KEY, r.getYield_stone());
                 roleObj.put(ROLE_CITY_NUM_KEY, r.getCity());
                 roleObj.put(ROLE_DIAMON_NUM_KEY, r.getDiamon());
                 roleObj.put(ROLE_EAT_NUM_KEY, r.getEat());
                 roleObj.put(ROLE_BAG_NUM_KEY, r.getBags());
                 roleObj.put(GUILD_ID_KEY, r.getFactionid());
                 
                 roleObj.put(ROLE_BATTLE_COUNT_KEY, r.getBattle_count());
                 
                 roleObj.put("buffs", PropRequestAnalysis.getBuffInfoResponse(r.getBuffs()));
                 //是否是当日首次登陆游戏
                 int isFirstLogin = 0;
                 int days = 0;
                 //已签到次数(含今天)
                 int mark_count = r.getSum_login_count();
                 try {
                    days    = DateTimeUtil.compareDayWithNow(r.getLast_login_time());
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
                 if( days > 0){
                     mark_count += days;
                     isFirstLogin = 1; 
                     //*---------处理是否增加帮会贡献度----------*//
                     factionInfoService.addGuildScore(1, r.getRole_id());
                 }
                 roleObj.put(ROLE_FIRST_LOGIN_KEY, isFirstLogin);
                 roleObj.put(ROLE_MARK_COUNT_KEY, mark_count);
                 arr.add(roleObj);
              }
             
             resObj.put(ROLE_LIST_KEY, arr);
                    
            
            
		} catch (Exception e) {
			e.printStackTrace();
		}
       return resObj.toString();
    }
    
    public static String getChestResponse(String chests)
    {
        JSONObject o = new JSONObject();
        o.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        o.put(CHEST_KEY, chests);
        
        return o.toString();
    }
}
