/**
 *
 *
 * 文件名称： WorldMapRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 下午4:57:51
 */
package com.lk.dragon.server.module.analysis;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.dao.impl.ArmsDeployDaoImpl;
import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;
import com.lk.dragon.server.domain.WorldMapDomain;
import com.lk.dragon.service.WarProduceInfoServiceBatch;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class WorldMapRequestAnalysis
{
    /** 坐标点id **/
    private static final String ID_KEY = "id";
    /** 世界坐标点X值 **/
    private static final String X_KEY = "x";
    /** 世界坐标点Y值 **/
    private static final String Y_KEY = "y";
    /** 世界坐标点查询下限X值 **/
    private static final String MIN_X_KEY = "min_x";
    /** 世界坐标点查询下限Y值 **/
    private static final String MIN_Y_KEY = "min_y";
    /** 世界坐标点查询上限X值 **/
    private static final String MAX_X_KEY = "max_x";
    /** 世界坐标点查询上限Y值 **/
    private static final String MAX_Y_KEY = "max_y";
    /** 世界地图请求类型 **/
    private static final String REQUEST_TYPE = "worldMap";
    /** 坐标点的类型 **/
    private static final String POINT_TYPE = "type";
    /** 坐标点的子类型 **/
    private static final String POINT_SUB_TYPE = "sub_type";
    /** 坐标点 **/
    private static final String POINT_KEY = "points";
    /** 钻石数量 **/
    private static final String USE_DIAMOND_KEY = "diamond";
    /** 金币数量 **/
    private static final String USE_GOLD_KEY = "gold";
    private static final String CITY_ID_KEY = "city_id";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    
    //城镇信息
    private static final String CITY_NAME_KEY = "city_name";
    private static final String CITY_LOYAL_KEY = "city_loyal";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String ROLE_NAME_KEY = "role_name";
    private static final String RACE_KEY = "race";
    private static final String ROLE_LEV_KEY = "role_lev";
    private static final String ROLE_ICON_KEY = "icon";
    private static final String YIELD_FOOD_KEY = "yield_food";   
    private static final String YIELD_WOOD_KEY = "yield_wood";   
    private static final String YIELD_STONE_KEY = "yield_stone";  
    private static final String EAT_KEY = "eat";   
    private static final String FACTION_NAME_KEY = "faction_name";  
    //资源点信息
    private static final String WILD_SRC_ARMINFO = "arm_info"; //资源点当前驻军情况
    private static final String WILD_SRC_OWNER_TYPE = "owner_type"; //资源点当前所属类型 1系统NPC 2玩家
    private static final String WILD_SRC_TYPE = "src_type";//资源点类型 
    private static final String WILD_SRC_LEVEL = "src_leve";//资源点等级 
    
    
    //野外随机点野怪信息
    private static final String CREEPS_ICON_KEY = "icon";
	private static final String CREEPS_NAME_KEY = "name";
	private static final String CREEPS_DESC_KEY = "desc";
	private static final String CREEPS_ID_KEY = "army_id";
	private static final String CREEPS_GRADE_KEY = "grade";
	private static final String CREEPS_EXP_KEY = "exp";
	private static final String CREEPS_FAIGHT_CAP_KEY = "fc";
	private static final String CREEPS_HP_KEY = "hp";
	private static final String CREEPS_PHY_ATTACK_KEY = "phy_att";
	private static final String CREEPS_PHY_DEFENCE_KEY = "phy_def";
	private static final String CREEPS_MEG_ATTACK_KEY = "meg_att";
	private static final String CREEPS_MEG_DEFENCE_KEY = "meg_def";
	private static final String CREEPS_SPEED_KEY = "speed";
	private static final String CREEPS_DISTANCE_ATTACK_KEY = "distance_att";
	
	//城镇守城信息
	private static final String ARMY_KEY = "armys";
	private static final String ROLE_HERO_ID_KEY = "role_hero_id";
	private static final String HERO_NAME_KEY = "hero_name";
	private static final String HERO_COMMAND_KEY = "hero_command";
	private static final String HERO_ICON_KEY = "hero_icon";
	private static final String HERO_IS_FREE_KEY = "is_free";
	private static final String ARMY_NUM_KEY = "num";
	private static final String HERO_ARMY_KEY = "hero_armys";
	private static final String SEQ_NUMBER_KEY = "seq_number";
	private static final String ARMY_ICON_KEY = "icon";
	
    /**
     * 获取世界地图相关请求
     * @param json
     * @return
     */
    public static WorldMapDomain getWorldMapInfo(String json)
    {
        //世界地图相关请求
        WorldMapDomain worldMapDomain = new WorldMapDomain();
        
        //获取 请求json数据
        JSONObject worldMapData = JSONUtil.getData(json);
        
        //获取 请求中相关数据
        worldMapDomain.setType(worldMapData.getInt(REQUEST_TYPE));
        worldMapDomain.setX(worldMapData.has(X_KEY) ? worldMapData.getInt(X_KEY) : -1);
        worldMapDomain.setY(worldMapData.has(Y_KEY) ? worldMapData.getInt(Y_KEY) : -1);
        worldMapDomain.setMin_x(worldMapData.has(MIN_X_KEY) ? worldMapData.getInt(MIN_X_KEY) : -1);
        worldMapDomain.setMin_y(worldMapData.has(MIN_Y_KEY) ? worldMapData.getInt(MIN_Y_KEY) : -1);
        worldMapDomain.setMax_x(worldMapData.has(MAX_X_KEY) ? worldMapData.getInt(MAX_X_KEY) : -1);
        worldMapDomain.setMax_y(worldMapData.has(MAX_Y_KEY) ? worldMapData.getInt(MAX_Y_KEY) : -1);
        worldMapDomain.setUseDiamond(worldMapData.has(USE_DIAMOND_KEY) ? worldMapData.getInt(USE_DIAMOND_KEY) : 0);
        worldMapDomain.setUseDiamond(worldMapData.has(USE_GOLD_KEY) ? worldMapData.getInt(USE_GOLD_KEY) : 0);
        worldMapDomain.setRole_id(worldMapData.has(ROLE_ID_KEY) ? worldMapData.getLong(ROLE_ID_KEY) : 0);
        worldMapDomain.setCity_id(worldMapData.has(CITY_ID_KEY) ? worldMapData.getLong(CITY_ID_KEY) : 0);
        return worldMapDomain;
    }
    
   /**
    * 世界地图部分点信息
    * @param worldMapList
    * @return
    */
    public static String getWorldMapPoint(List<WorldMap> worldMapList)
    {
        //世界地图点坐标对象
        JSONObject mapObj = new JSONObject();
        JSONArray mapArr = new JSONArray();
        
        if (worldMapList != null)
        {
            for (WorldMap worldMap : worldMapList)
            {
                JSONObject point = new JSONObject();
                point.put(ID_KEY, worldMap.getMap_id());
                point.put(X_KEY, worldMap.getSite_x());
                point.put(Y_KEY, worldMap.getSite_y());
                point.put(POINT_TYPE, worldMap.getItem());
                point.put(POINT_SUB_TYPE, worldMap.getType());
                point.put("ownerId", worldMap.getOwnerId());
                point.put("ownerName", worldMap.getOwnerName());
                mapArr.add(point);
            }
        }
        
        mapObj.put(POINT_KEY, mapArr);
        
        return mapObj.toString();
    }
    
    /**
     * 获取城镇信息
     * @param city
     * @return
     */
    public static String getCityInfo(City city)
    {
        JSONObject cObj = new JSONObject();
        if (city == null)
        {
            cObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
        }
        else
        {
            cObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            cObj.put(CITY_ID_KEY, city.getCity_id());
            cObj.put(CITY_NAME_KEY, city.getName());
            cObj.put(CITY_LOYAL_KEY, city.getLoyal());
            cObj.put(ROLE_ID_KEY, city.getRole_id());
            cObj.put(ROLE_NAME_KEY, city.getRole_name());
            cObj.put(ROLE_LEV_KEY, city.getRole_lev());
            cObj.put(RACE_KEY, city.getRace());
            cObj.put(ROLE_ICON_KEY, city.getRole_icon());
            cObj.put(YIELD_FOOD_KEY, city.getYield_food());
            cObj.put(YIELD_STONE_KEY, city.getYield_stone());
            cObj.put(YIELD_WOOD_KEY, city.getYield_wood());
            cObj.put(EAT_KEY, city.getEat());
            cObj.put(FACTION_NAME_KEY, city.getFaction_name());
            cObj.put("home", city.getHome());
        }

        return cObj.toString();
    }
    
    /**
     * 获取侦察信息
     * @param map
     * @return
     */
    public static String getDettctInfo(Map<String, Object> map)
    {
    	JSONObject cObj = new JSONObject();
    	//城镇信息
    	City city = (City) map.get("cityInfo");
		cObj.put(CITY_LOYAL_KEY, city.getLoyal());
	    cObj.put(YIELD_FOOD_KEY, city.getCheck_food());
	    cObj.put(YIELD_STONE_KEY, city.getCheck_stone());
	    cObj.put(YIELD_WOOD_KEY, city.getCheck_wood());
	    
	    //军队信息
	    @SuppressWarnings("unchecked")
		List<RoleHero> heroList = (List<RoleHero>) map.get("armys");
		JSONArray arr = new JSONArray();
	    if (heroList != null) {
			for (RoleHero roleHero : heroList) {
				JSONObject heroArmsObj = new JSONObject();
				heroArmsObj.put(ROLE_HERO_ID_KEY, roleHero.getRole_hero_id());
				heroArmsObj.put(HERO_NAME_KEY, roleHero.getHero_name());
				heroArmsObj.put(HERO_COMMAND_KEY, roleHero.getCommand());
				heroArmsObj.put(HERO_ICON_KEY, roleHero.getSht_icon());
				heroArmsObj.put(ARMY_NUM_KEY, roleHero.getHero_arm_count());
				heroArmsObj.put(CITY_NAME_KEY, roleHero.getCity_name());
				heroArmsObj.put(HERO_IS_FREE_KEY, roleHero.getIs_free());
				
				// 每个英雄所拥有的部队信息
				JSONArray heroHasArmyArr = new JSONArray();
				List<ArmsDeploy> armsList = roleHero.getHeroArmys();
				if (armsList != null && armsList.size() > 0) {
					for (ArmsDeploy arms : armsList) {
						JSONObject army = new JSONObject();
						army.put(ARMY_NUM_KEY, arms.getCount());
						army.put(SEQ_NUMBER_KEY, arms.getSeq_number());
						army.put(ARMY_ICON_KEY, arms.getArm_icon());
						heroHasArmyArr.add(army);
					}
				}
				heroArmsObj.put(HERO_ARMY_KEY, heroHasArmyArr);

				// 英雄信息
				arr.add(heroArmsObj);
			}
		}
	    
	    cObj.put(ARMY_KEY, arr);
	    
    	return cObj.toString();
    }
    
    /**
     * 查询目标资源点信息
     * @param tagWildSrcs
     * @param type :查询类型
     * @return
     */
    public static String getWildSrcInfo(List<WildSrc> wildSrcs,int type){
    	JSONObject wildSrcJ ;
    	wildSrcJ = new JSONObject();
    	String result = "";
    	if(type == 1){//查询目标资源点信息
    		wildSrcJ.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    		WildSrc tagWildSrc = wildSrcs.get(0);
    		wildSrcJ.put(ROLE_NAME_KEY,tagWildSrc.getRole_name());
    		wildSrcJ.put(ROLE_ID_KEY,tagWildSrc.getOwner_id());
    		wildSrcJ.put(WILD_SRC_OWNER_TYPE,tagWildSrc.getOwner_type());
    		wildSrcJ.put(WILD_SRC_TYPE, tagWildSrc.getSrc_type());
    		wildSrcJ.put(WILD_SRC_LEVEL,tagWildSrc.getSrc_leve());
    		wildSrcJ.put("inc_value", tagWildSrc.getSrc_leve() == 1 ? 400 : 1200);
    		if(tagWildSrc.getOwner_type() == 2){//资源点被玩家占居
    			wildSrcJ.put(WILD_SRC_ARMINFO, getHerosArmJson(tagWildSrc));
    		}
    		result = wildSrcJ.toString();
    	}else{//查询多个资源点信息
    		
    		JSONArray srcArr = new JSONArray();
    		JSONObject resJson = new JSONObject();
    		resJson.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    		if(wildSrcs != null && wildSrcs.size() > 0){
    			for (WildSrc wildSrc : wildSrcs) {
    				wildSrcJ = new JSONObject();
    				wildSrcJ.put(ROLE_NAME_KEY,wildSrc.getRole_name());
    				wildSrcJ.put(ROLE_ID_KEY,wildSrc.getOwner_id());
    				wildSrcJ.put(X_KEY,wildSrc.getTag_x());
    				wildSrcJ.put(Y_KEY,wildSrc.getTag_y());
    				int srcLev = wildSrc.getSrc_leve();
    				wildSrcJ.put(WILD_SRC_LEVEL,srcLev);
    				wildSrcJ.put("inc_value", srcLev == 1 ? 400 : 1200);
    				wildSrcJ.put(WILD_SRC_TYPE,wildSrc.getSrc_type());
    				wildSrcJ.put(WILD_SRC_OWNER_TYPE,wildSrc.getOwner_type());
    				if(wildSrc.getOwner_type() == 2){//资源点被玩家占居
    	    			wildSrcJ.put(WILD_SRC_ARMINFO, getHerosArmJson(wildSrc));
    	    		}
    				srcArr.add(wildSrcJ);
    			}
    		}
    		resJson.put("wildSrcs", srcArr);
    		result = resJson.toString();
    	}
    	return result;
    }
    
    /**
     * 构造英雄携带部队信息
     * @param tagWildSrc
     * @return
     */
    private static JSONArray getHerosArmJson(WildSrc tagWildSrc){
    	JSONArray herosArray = new JSONArray();
		if(tagWildSrc.getArm_info()!=null && !tagWildSrc.getArm_info().trim().equals("")){
			try {
				
				List<RoleHero> heros = SpringBeanUtil.getBean(ArmsDeployDaoImpl.class).getSrcHerosInfoByCondition(" WHERE t.role_hero_id in ("+tagWildSrc.getArm_info()+")");
				
				for (RoleHero hero : heros) {
					JSONObject heroJ = new JSONObject();
					//英雄所率部队信息
					heroJ.put("heroId", hero.getRole_hero_id());//英雄ID
					heroJ.put("heroName", hero.getHero_name());//英雄名
					heroJ.put("hero_icon", hero.getSht_icon());//英雄头像
					//部队级别层JSON描述
					List<ArmsDeploy> arms = hero.getHeroArmys();//英雄携带部队信息
					JSONArray armsArray = new JSONArray();
					if(arms != null && arms.size() > 0){
						for (ArmsDeploy arm : arms) {
							JSONObject armJ = new JSONObject();
							armJ.put("armId", arm.getArm_id());//部队种类
							armJ.put("seq", arm.getSeq_number());//部队位置序号
							armJ.put("count", arm.getCount());//部队数量
							armJ.put("arm_icon", arm.getArm_icon());//部队图标
							armsArray.add(armJ);
						}
					}
					heroJ.put("arms", armsArray);
					herosArray.add(heroJ);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return herosArray;
    }
    
    /**
     * 获取随机点野怪信息
     * @param creeps
     * @return
     */
    public static String getRandomWildInfo(WorldMap creeps){
    	JSONObject creepsJ = new JSONObject();
    	creepsJ.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    	if(creeps != null){
    		if(creeps.getItem() != 3){//客户端发送的坐标点非野怪区域
    			creepsJ.put(RESULT_KEY, Constants.REQUEST_ERROR);
    			creepsJ.put("error_info", "The Point Item Error");
    		}else{
    			ArmsDeploy creepsArm = creeps.getWildArms();
    			creepsJ.put("map_id", creeps.getMap_id());
    			creepsJ.put(CREEPS_ID_KEY, creepsArm.getArm_id());
    			creepsJ.put(CREEPS_NAME_KEY, creepsArm.getArm_name());
    			creepsJ.put(CREEPS_ICON_KEY, creepsArm.getArm_icon());
    			creepsJ.put(CREEPS_DESC_KEY, creepsArm.getArm_desc());
    			creepsJ.put(CREEPS_EXP_KEY, creepsArm.getExp());
    			creepsJ.put(CREEPS_FAIGHT_CAP_KEY, creepsArm.getFc());
    			creepsJ.put(CREEPS_GRADE_KEY, creepsArm.getGrade());

    			creepsJ.put(CREEPS_HP_KEY, creepsArm.getHp());
    			creepsJ.put(CREEPS_PHY_ATTACK_KEY, creepsArm.getPhysic_attack());
    			creepsJ.put(CREEPS_PHY_DEFENCE_KEY, creepsArm.getPhysic_defence());
    			creepsJ.put(CREEPS_MEG_ATTACK_KEY, creepsArm.getMagic_attack());
    			creepsJ.put(CREEPS_MEG_DEFENCE_KEY, creepsArm.getMagic_defence());
    			creepsJ.put(CREEPS_SPEED_KEY, creepsArm.getSpeed());
    			creepsJ.put(CREEPS_DISTANCE_ATTACK_KEY, creepsArm.getDistance_attack());
    		}
    	}
    	return creepsJ.toString();
    }
    
    /**
     * 放弃城邦 驻扎部队返回
     * @param map
     * @return
     */
    public static String giveUpWildSrcRes(Map<Long,Integer> map,Role r){
    	JSONObject resJ = new JSONObject();
    	resJ.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    	JSONArray teamAJ = new JSONArray();
    	//是否存在驻扎部队
    	if(map.size() > 0 ){
    		Set<Long> teamIds = map.keySet();
    		for (Iterator<Long> it = teamIds.iterator(); it.hasNext();) {
				JSONObject teamJ = new JSONObject();
				teamJ.put("team_id", it);
				teamJ.put("back_time", map.get(it));
				
				teamAJ.add(teamJ);
			}
    	}
    	
    	resJ.put("back_teams", teamAJ);
    	//最新产量
    	resJ.put("yield_food", r.getYield_food());
    	resJ.put("yield_wood", r.getYield_wood());
    	resJ.put("yield_stone", r.getYield_stone());
    	return resJ.toString();
    }
}   
