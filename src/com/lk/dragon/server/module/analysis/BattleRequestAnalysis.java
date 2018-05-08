/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： BattleAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:32:51
 */
package com.lk.dragon.server.module.analysis;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.server.domain.BattleDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;

public class BattleRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String BATTLE_TYPE_KEY = "battleType";
    private static final String CITY_ID_KEY = "city_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String WAR_TEAM_ID_KEY = "war_team_id";
    
    
    private static final String WAR_TYPE_KEY = "war_type";
    private static final String TAG_X_KEY = "x";
    private static final String TAG_Y_KEY = "y";
    private static final String FROM_X_KEY = "fr_x";//队列出发X坐标
    private static final String FROM_Y_KEY = "fr_y";//队列出发Y坐标
    private static final String TAG_NAME_KEY = "tag_name";
    private static final String TAG_ROLE_ID_KEY = "tag_role_id";
    private static final String USE_TIME_KEY = "use_time";
    private static final String USE_FOOD_KEY = "use_food";
    private static final String HEROS_ID_KEY = "herosId";
    private static final String CREEPS_KEY = "creeps_type";
    private static final String DISTANCE_KEY = "distance";
    private static final String SUM_COMMAND_KEY = "sum_command";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    /** 队列是否还存在 **/
    private static final String TEAM_LIVE = "team_live";
    
    //可出征英雄列表信息
    private static final String HERO_KEY = "heros";
    private static final String HERO_ID_KEY = "hero_id";
    private static final String HERO_NAME_KEY = "hero_name";
    private static final String HERO_SOLDIER_NUM_KEY = "hero_soldier_num";
    
    /**
     * 获取战斗请求数据
     * @param json
     * @return
     */
    public static BattleDomain getBattleInfo(String json)
    {
        //创建公会实体
        BattleDomain battleDomain = new BattleDomain();
        
        //获取data信息
        JSONObject battleData = JSONUtil.getData(json);
        
        //将信息分解到公会实体中
        battleDomain.setType(battleData.getInt(BATTLE_TYPE_KEY));
        battleDomain.setWar_team_id(battleData.has(WAR_TEAM_ID_KEY) ? battleData.getLong(WAR_TEAM_ID_KEY) : 0);
        battleDomain.setRoleId(battleData.has(ROLE_ID_KEY) ? battleData.getLong(ROLE_ID_KEY) : 0);
        battleDomain.setCityId(battleData.has(CITY_ID_KEY) ? battleData.getLong(CITY_ID_KEY) : 0);
        
        battleDomain.setWar_type(battleData.has(WAR_TYPE_KEY) ? battleData.getInt(WAR_TYPE_KEY) : 0);
        battleDomain.setTag_x(battleData.has(TAG_X_KEY) ? battleData.getInt(TAG_X_KEY) : 0);
        battleDomain.setTag_y(battleData.has(TAG_Y_KEY) ? battleData.getInt(TAG_Y_KEY) : 0);
        battleDomain.setTag_name(battleData.has(TAG_NAME_KEY) ? battleData.getString(TAG_NAME_KEY) : "");
        battleDomain.setTag_role_id(battleData.has(TAG_ROLE_ID_KEY) ? battleData.getLong(TAG_ROLE_ID_KEY) : 0);
        battleDomain.setUse_time(battleData.has(USE_TIME_KEY) ? battleData.getInt(USE_TIME_KEY) : 0);
        battleDomain.setFrom_x(battleData.has(FROM_X_KEY) ? battleData.getInt(FROM_X_KEY) : 0);
        battleDomain.setFrom_y(battleData.has(FROM_Y_KEY) ? battleData.getInt(FROM_Y_KEY) : 0);
        battleDomain.setHerosId(battleData.has(HEROS_ID_KEY) ? battleData.getString(HEROS_ID_KEY) : "");
        battleDomain.setUse_food(battleData.has(USE_FOOD_KEY) ? battleData.getInt(USE_FOOD_KEY) : 0);
        battleDomain.setCreeps_type(battleData.has(CREEPS_KEY) ? battleData.getInt(CREEPS_KEY) : 1);
        battleDomain.setDistance(battleData.has(DISTANCE_KEY) ? battleData.getInt(DISTANCE_KEY) : 1);
        battleDomain.setSum_command(battleData.has(SUM_COMMAND_KEY) ? battleData.getInt(SUM_COMMAND_KEY) : 1);
        

        return battleDomain;
    }
    
    /**
     * 队列详细信息
     * @param warProduces
     * @return
     */
    public static String selectWarTeamDetailInfo(List<WarProduce> warProduces){
    	JSONObject warTeamJ = new JSONObject();
    	warTeamJ.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
    	if(warProduces == null || warProduces.size() == 0){
    		warTeamJ.put(TEAM_LIVE, 0);
    		return warTeamJ.toString();
    	}
    	warTeamJ.put(TEAM_LIVE, 1);
    	
    	JSONArray heroAmrsJA = new JSONArray();
    	for (WarProduce warProduce : warProduces) {
    		
			JSONObject warProduceJ = new JSONObject();//总JSON描述
			
			//队列级别层JSON描述
			warProduceJ.put(WAR_TEAM_ID_KEY, warProduce.getWar_team_id());//队列ID
			warProduceJ.put(CITY_ID_KEY, warProduce.getCity_id());//出发城邦ID
			warProduceJ.put("status", warProduce.getStatus());//队列当前状态
			long lastSecond = 0;
			try {
				lastSecond = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(warProduce.getEnd_time()), new Date(), 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			warProduceJ.put("last_time", lastSecond < 0 ? 0 : lastSecond);//当前状态剩余时间
			warProduceJ.put("tag_name", warProduce.getTag_name());//目标名
			warProduceJ.put(TAG_X_KEY, warProduce.getTag_x());//目标X坐标
			warProduceJ.put(TAG_Y_KEY, warProduce.getTag_y());//目标Y坐标
			warProduceJ.put(USE_TIME_KEY, warProduce.getUse_time());//当前任务耗时
			warProduceJ.put(WAR_TYPE_KEY, warProduce.getWar_type());//队列征战模式
			
			//英雄级别层JSON描述
			List<RoleHero> heros = warProduce.getHeros();//队列中英雄集合
			JSONArray herosArray = new JSONArray();
			if(heros != null && heros.size() > 0){
				for (RoleHero hero : heros) {
					JSONObject heroJ = new JSONObject();
					heroJ.put("heroId", hero.getRole_hero_id());//英雄ID
					heroJ.put("hp", hero.getHp());//英雄HP
					heroJ.put("mp", hero.getMp());//英雄MP
					heroJ.put("hero_exp", hero.getHero_exp());//英雄经验值
					//部队级别层JSON描述
					List<ArmsDeploy> arms = hero.getHeroArmys();//英雄携带部队信息
					JSONArray armsArray = new JSONArray();
					if(arms != null && arms.size() > 0){
						for (ArmsDeploy arm : arms) {
							JSONObject armJ = new JSONObject();
							armJ.put("armId", arm.getArm_id());//部队种类
							armJ.put("seq", arm.getSeq_number());//部队位置序号
							armJ.put("count", arm.getCount());//部队数量
							
							armsArray.add(armJ);
						}
					}
					heroJ.put("arms", armsArray);
					
					
					herosArray.add(heroJ);
				}
			}
			warProduceJ.put("heros", herosArray);
			heroAmrsJA.add(warProduceJ);
		}
    	warTeamJ.put("teams", heroAmrsJA);
    	return warTeamJ.toString();
    	
    }
    
    public static String getCurrentEnemyTeamResponse(List<WarProduce> teams){
    	JSONObject resJ = new JSONObject();
    	resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
    	
    	JSONArray teamAJ = new JSONArray();
    	if(teams != null && teams.size() > 0){
    		JSONObject teamJ;
    		for (WarProduce warProduce : teams) {
				teamJ = new JSONObject();
				teamJ.put("teamId", warProduce.getWar_team_id());
				teamJ.put("war_type", warProduce.getWar_type());
				teamJ.put("last_time", warProduce.getLastTime());
				teamJ.put("tag_x", warProduce.getTag_x());
				teamJ.put("tag_y", warProduce.getTag_y());
				if(warProduce.getTag_city_id() != null)
					teamJ.put("tag_city_id", warProduce.getTag_city_id());
				
				teamAJ.add(teamJ);
			}
    	}
    	
    	resJ.put("teams", teamAJ);
    	return resJ.toString();
    }
    
    /**
     * 某城镇可以派出的英雄列表信息 
     * @param heroList
     * @return
     */
    @SuppressWarnings("unused")
	public static String canBattleHeroResponse(List<Object> heroList)
    {
        //英雄列表json对象
        JSONObject heroListObj = new JSONObject();
        heroListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //创建英雄列表的数组对象
        JSONArray arr = new JSONArray();
        if (heroList != null)
        {
            for (Object obj : heroList)
            {
                JSONObject heroObj = new JSONObject();
                //将信息设置到对象中
                heroObj.put(HERO_ID_KEY, "");
                heroObj.put(HERO_NAME_KEY, "");
                heroObj.put(HERO_SOLDIER_NUM_KEY, "");
                
                arr.add(heroObj);
            }
        }
        
        //将数组放入结果字符串中
        heroListObj.put(HERO_KEY, arr);
        
        return heroListObj.toString();
    }
}
