/**
 *
 *
 * 文件名称： MainCityRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 下午5:07:26
 */
package com.lk.dragon.server.module.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Build;
import com.lk.dragon.db.domain.BuildCreate;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.server.domain.CityDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;

public class CityRequestAnalysis
{
    /***********************请求字符串**************************/
    /** 请求类型 **/
    private static final String REQUEST_TYPE_KEY = "requestType";
    /** 角色id **/
    private static final String ROLD_ID_KEY = "roleId";
    /** 种族信息 **/
    private static final String RACE_KEY = "race";
    /** 城市、建筑关联表id **/
    private static final String RELA_ID_KEY = "relaId";
    /** 坐标x **/
    private static final String X_KEY = "x";
    /** 坐标y **/
    private static final String Y_KEY = "y";
    /** 目标坐标x **/
    private static final String TARGET_X_KEY = "target_x";
    /** 目标坐标y **/
    private static final String TARGET_Y_KEY = "target_y";
    /** 城市名字 **/
    private static final String CITY_NAME_KEY = "cityName";
    /** 城邦仓库容量 **/
    private static final String CITY_STORE = "store";
    /** 传送门CD时间 **/
    private static final String TRANS_CD = "tra_cd";
    /** 城市类型 **/
    private static final String CITY_TYPE_KEY = "cityType";
    /** 建筑升级完成时间 **/
    private static final String BUILD_LEVEL_UP_TIME_KEY = "lev_up_time";
    /** 建筑等级 **/
    private static final String BUILD_LEV_KEY = "build_lev";
    private static final String ROLE_PROP_ID_KEY = "role_prop_id";
    private static final String PROTECT_TIME_KEY = "protect_time";
    private static final String PROP_IS_ENOUGH_KEY = "is_enough";
    private static final String DIAMOND_NUM_KEY = "diamond_num";
    
    

    private static final String ROLE_LEVEL_KEY = "level";
    private static final String ROLE_EXP_KEY = "exp";
    private static final String ROLE_UP_EXP_KEY = "up_exp";
    
    /******************响应字符串********************************/
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    //建造城镇
    private static final String CITY_ID_KEY = "city_id";
    
    //城市列表请求
    private static final String CITY_KEY = "cities";
    private static final String SITE_X_KEY = "site_x";
    private static final String SITE_Y_KEY = "site_y";
    private static final String HOME_KEY = "home";
    private static final String EAT_KEY = "eat";
    //产量
    private static final String YIELD_FOOD_KEY = "yield_food";
    private static final String YIELD_WOOD_KEY = "yield_wood";
    private static final String YIELD_STONE_KEY = "yield_stone";
    //角色总产量
    private static final String ROLE_FOOD_KEY = "role_food";
    private static final String ROLE_WOOD_KEY = "role_wood";
    private static final String ROLE_STONE_KEY = "role_stone";
    
    
    
    //城市建筑列表
    private static final String BUILD_KEY = "builds";
    private static final String MAX_LEVEL_KEY = "max_lev";
    private static final String BUILD_NAME_KEY = "build_name";
    private static final String BUILD_ICON_KEY = "build_icon";
    private static final String BUILD_ID_KEY = "build_id";
    private static final String BUILD_CURR_LEVEL_KEY = "build_curr_lev";
    private static final String BUILD_LEV_UP_KEY = "lev_up";
    private static final String BUILD_LEV_UP_T_KEY = "lev_up_t_key";
    private static final String BUILD_UPGRADE_LAST_TIME_KEY = "last_time";
    private static final String IS_FINISHED_KEY = "is_finish";
    private static final String BUILD_TYPE_KEY = "build_type";
    private static final String LOCATE_KEY = "locate";
    
    //建筑升级消耗
    private static final String FOOD_KEY = "food";
    private static final String WOOD_KEY = "wood";
    private static final String STONE_KEY = "stone";
    private static final String GOLD_KEY = "gold";
    //资源建筑等级
    private static final String RES_LEVEL = "res_level"; 
    /**
     * 获取主城相关请求信息
     * @param info
     * @return
     */
    public static CityDomain getMainCityInfo(String info)
    {
        /** 主城相关请求实体 **/
        CityDomain cityDomain = new CityDomain();
        
        //获取请求分析的json实体
        JSONObject cityData = JSONUtil.getData(info);
        cityDomain.setType(cityData.getInt(REQUEST_TYPE_KEY));
        //设置默认值为操作分城
        cityDomain.setRoldId(cityData.has(ROLD_ID_KEY) ? cityData.getLong(ROLD_ID_KEY) : 0);
        cityDomain.setRace(cityData.has(RACE_KEY) ? cityData.getInt(RACE_KEY) : 0);
        cityDomain.setRelaId(cityData.has(RELA_ID_KEY) ? cityData.getLong(RELA_ID_KEY) : 0);
        cityDomain.setX(cityData.has(X_KEY) ? cityData.getInt(X_KEY) : 0);
        cityDomain.setY(cityData.has(Y_KEY) ? cityData.getInt(Y_KEY) : 0);
        cityDomain.setCityType(cityData.has(CITY_TYPE_KEY) ? cityData.getInt(CITY_TYPE_KEY) : Constants.VICE_CITY);
        cityDomain.setCityName(cityData.has(CITY_NAME_KEY) ? cityData.getString(CITY_NAME_KEY) : null);
        cityDomain.setCityId(cityData.has(CITY_ID_KEY) ? cityData.getLong(CITY_ID_KEY) : 0);
        cityDomain.setLevelUpT(cityData.has(BUILD_LEVEL_UP_TIME_KEY) ? cityData.getInt(BUILD_LEVEL_UP_TIME_KEY) : 0);
        cityDomain.setBuildId(cityData.has(BUILD_ID_KEY) ? cityData.getInt(BUILD_ID_KEY) : 0);
        cityDomain.setTargetX(cityData.has(TARGET_X_KEY) ? cityData.getInt(TARGET_X_KEY) : 0);
        cityDomain.setTargetY(cityData.has(TARGET_Y_KEY) ? cityData.getInt(TARGET_Y_KEY) : 0);
        cityDomain.setBuildLev(cityData.has(BUILD_LEV_KEY) ? cityData.getInt(BUILD_LEV_KEY) : 0);
        cityDomain.setBuildType(cityData.has(BUILD_TYPE_KEY) ? cityData.getInt(BUILD_TYPE_KEY) : -1);
        cityDomain.setIs_enough(cityData.has(PROP_IS_ENOUGH_KEY) ? cityData.getInt(PROP_IS_ENOUGH_KEY) : 0);
        cityDomain.setProtectTime(cityData.has(PROTECT_TIME_KEY) ? cityData.getInt(PROTECT_TIME_KEY) : 0);
        cityDomain.setRolePropId(cityData.has(ROLE_PROP_ID_KEY) ? cityData.getLong(ROLE_PROP_ID_KEY) : 0);
        cityDomain.setDiamondNum(cityData.has(DIAMOND_NUM_KEY) ? cityData.getInt(DIAMOND_NUM_KEY) : 0);
        cityDomain.setFood(cityData.has(FOOD_KEY) ? cityData.getInt(FOOD_KEY) : 0);
        cityDomain.setWood(cityData.has(WOOD_KEY) ? cityData.getInt(WOOD_KEY) : 0);
        cityDomain.setStone(cityData.has(STONE_KEY) ? cityData.getInt(STONE_KEY) : 0);
        cityDomain.setGold(cityData.has(GOLD_KEY) ? cityData.getInt(GOLD_KEY) : 0);
        cityDomain.setEat(cityData.has(EAT_KEY) ? cityData.getInt(EAT_KEY) : 0);
        cityDomain.setLocate(cityData.has(LOCATE_KEY)?cityData.getInt(LOCATE_KEY) : 0);
        cityDomain.setRes_level(cityData.has(RES_LEVEL)?cityData.getInt(RES_LEVEL) : 1);
        return cityDomain;
    }
    
    /**
     * 获取相应字符串
     * @return
     */
    public static String getBuildCityResponseStr(Map<String,Long> map)
    {
    	// 构造登录结果
    	JSONObject resultObj = new JSONObject();
    	if(map == null){
    		//存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
            return resultObj.toString();
    	}
    	//获取结果
        long result = map.get(Constants.RESULT_KEY);
        
        if (result == Constants.CREATE_NEW_CITY_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(CITY_ID_KEY, map.get(Constants.ID_KEY));
        }
        else if (result == Constants.CREATE_CITY_HAS_APPLYED)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.MAP_POINT_HAS_APPLYED);
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取城市列表响应字符串
     * @param cityList
     * @return
     */
    public static String getCityListResponse(List<City> cityList)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        resultObj.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
        JSONArray arr = new JSONArray();
        for (int i = 0; i < cityList.size(); i++)
        {
            JSONObject cityObj = new JSONObject();
            City city = cityList.get(i);
            cityObj.put(CITY_ID_KEY, city.getCity_id());
            cityObj.put(SITE_X_KEY, city.getSite_x());
            cityObj.put(SITE_Y_KEY, city.getSite_y());
            cityObj.put(HOME_KEY, city.getHome());
            cityObj.put(RACE_KEY, city.getRace());
            cityObj.put(EAT_KEY, city.getEat());
            cityObj.put(CITY_NAME_KEY, city.getName());
            cityObj.put(CITY_STORE, city.getStore());
            cityObj.put(TRANS_CD, city.getTrans_cd()<=0 ? 0 : city.getTrans_cd());
            cityObj.put(YIELD_FOOD_KEY, city.getYield_food());
            cityObj.put(YIELD_WOOD_KEY, city.getYield_wood());
            cityObj.put(YIELD_STONE_KEY, city.getYield_stone());
            cityObj.put("loyal", city.getLoyal());
            cityObj.put("buffs", PropRequestAnalysis.getBuffInfoResponse(city.getBuffs()));
            arr.add(cityObj);
        }
        
        resultObj.put(CITY_KEY, arr);
        
        return resultObj.toString();
    }
    
    /**
     * 获取建筑列表响应字符串
     * @param list
     * @return
     */
    public static String getCityBuildResponse(List<CityBuild> list)
    {
     // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        JSONArray arr = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject cityObj = new JSONObject();
            CityBuild cityBuild = list.get(i);
            cityObj.put(RELA_ID_KEY, cityBuild.getRela_id());
            cityObj.put(CITY_ID_KEY, cityBuild.getCity_id());
            cityObj.put(BUILD_ID_KEY, cityBuild.getBulid_id());
            cityObj.put(BUILD_CURR_LEVEL_KEY, cityBuild.getCurr_lev());
            cityObj.put(BUILD_LEV_UP_KEY, cityBuild.getLevup());
            cityObj.put(BUILD_LEV_UP_T_KEY, cityBuild.getLev_up_t());
            //cityObj.put(BUILD_ICON_KEY, cityBuild.getBulidIcon());
            cityObj.put(BUILD_NAME_KEY, cityBuild.getBulidName());
            cityObj.put(MAX_LEVEL_KEY, cityBuild.getBulidMaxLev());
            cityObj.put(BUILD_TYPE_KEY, cityBuild.getType());
            cityObj.put(LOCATE_KEY, cityBuild.getLocate());
            long finishedSecond =  0;
            if (cityBuild.getLev_up_t() != null)
            {
                //转换时间
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date finishedTime = null;
                Date now = null;
                try
                {
                    finishedTime = formatDate.parse(cityBuild.getLev_up_t());
                    now = formatDate.parse(cityBuild.getNow_db());
                } 
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                //计算时间差返回
                finishedSecond = (finishedTime.getTime() - now.getTime()) / 1000;
            }
            cityObj.put(BUILD_UPGRADE_LAST_TIME_KEY, finishedSecond);
            
            arr.add(cityObj);
        }
        
        resultObj.put(BUILD_KEY, arr);
        
        return resultObj.toString();
    }
    
    /**
     * 获取建筑列表响应字符串
     * @param list
     * @return
     */
    public static String getBuildResponse(List<Build> list)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        JSONArray arr = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject buildObj = new JSONObject();
            Build build = list.get(i);
            buildObj.put(BUILD_ID_KEY, build.getBulid_id());
           // buildObj.put(BUILD_ICON_KEY, build.getIcon());
            buildObj.put(BUILD_NAME_KEY, build.getBulid_name());
            buildObj.put(MAX_LEVEL_KEY, build.getMax_lev());
            buildObj.put(BUILD_TYPE_KEY, build.getType());
            
            arr.add(buildObj);
        }
        
        resultObj.put(BUILD_KEY, arr);
        
        return resultObj.toString();
    }
    
    /**
     * 获取建造建筑响应字符串
     * @param buildId
     * @return
     */
    public static String getBuildResponse(long buildId)
    {
     // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (buildId != -1)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(BUILD_ID_KEY, buildId);
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取迁城请求响应字符串
     * @param map
     * @return
     */
    public static String getCityMoveResponse(Map<String, Long> map, City city)
    {
    	// 构造登录结果
    	JSONObject resultObj = new JSONObject();
    	if(map == null){
    		//存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
            return resultObj.toString();
    	}
    	//获取结果
        long result = map.get(Constants.RESULT_KEY);
        
        if (result == Constants.MOVE_CITY_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(TARGET_X_KEY, city.getSite_x());
            resultObj.put(TARGET_Y_KEY, city.getSite_y());
        }
        else if (result == Constants.MOVE_CITY_UNAVAILABLE)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.MAP_POINT_HAS_APPLYED);
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取城市的升级 情况信息
     * 1---升级完成
     * 2---升级中
     * 3---查询失败
     * @return
     */
    public static String getUpgradeRate(int result, CityBuild cityBuild)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (result == Constants.RATE_GET_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(IS_FINISHED_KEY, 1);
            resultObj.put(BUILD_LEV_KEY, cityBuild.getCurr_lev());
        }
        else if(result == Constants.RATE_DOING)
        {
            //返回剩余时间
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(IS_FINISHED_KEY, 0);
            resultObj.put(BUILD_UPGRADE_LAST_TIME_KEY, Integer.parseInt(cityBuild.getLev_up_t()));
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    /**
     * 获取建筑消耗情况
     * @param use
     * @return
     */
    public static String getUseResponse(BuildCreate use)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (use == null)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        else
        {
            //消耗结果信息
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(FOOD_KEY, use.getFood());
            resultObj.put(WOOD_KEY, use.getWood());
            resultObj.put(STONE_KEY, use.getStone());
            resultObj.put(GOLD_KEY, use.getGold());
            resultObj.put(EAT_KEY, use.getEat());
            resultObj.put(BUILD_LEVEL_UP_TIME_KEY, use.getCreate_t());
        }
        
        return resultObj.toString();
    }
    /**
     * 建筑拆除后最新产量
     * @param role
     * @return
     */
    public static String getNewYieldDelete(City city)
    {
    	JSONObject yildObj = new JSONObject();
    	if (city != null)
    	{
            //创建json对象
    		yildObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    		yildObj.put(YIELD_FOOD_KEY, city.getYield_food());
    		yildObj.put(YIELD_WOOD_KEY, city.getYield_wood());
    		yildObj.put(YIELD_STONE_KEY, city.getYield_stone());
    		yildObj.put(ROLE_FOOD_KEY, city.getRole_food());
    		yildObj.put(ROLE_WOOD_KEY, city.getRole_wood());
    		yildObj.put(ROLE_STONE_KEY, city.getRole_stone());
            yildObj.put("role_eat", city.getRole_eat());
    	}
    	else
    	{
    		 //存放结果 
    		yildObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
    		yildObj.put(REASON_KEY, Constants.NET_ERROR);
    	}
    	return yildObj.toString();
    }
    /**
     * 获取升级完成角色基本信息
     * @param role
     * @return
     */
    public static String getUpgradeResponseInfo(Role r)
    {
    	if (r != null)
    	{
            //创建json对象
            JSONObject roleObj = new JSONObject();
            roleObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            roleObj.put(ROLE_LEVEL_KEY, r.getLev());
            roleObj.put(ROLE_EXP_KEY, r.getExp());
            roleObj.put(ROLE_UP_EXP_KEY, r.getUp_exp());
            
            return roleObj.toString();
    	}
    	else
    	{
    		return JSONUtil.getBooleanResponse(false);
    	}
    }
}
