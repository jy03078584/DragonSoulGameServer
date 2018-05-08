/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： HeroRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-11 下午4:01:20
 */
package com.lk.dragon.server.module.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Hero;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RolePropsEquipPro;
import com.lk.dragon.server.domain.HeroDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class HeroRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String HERO_TYPE_KEY = "heroType";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String HERO_ID_KEY = "hero_id";
    private static final String ROLE_PROP_ID_KEY = "role_prop_id";
    private static final String HERO_ADD_PROP_ID_KEY = "wear_prop_id";
    private static final String HERO_DELE_PROP_ID_KEY = "off_prop_id";
    private static final String ROLE_HERO_ID_KEY = "role_hero_id";
    private static final String CITY_ID_KEY = "city_id";
    private static final String GOLD_NUM_KEY = "gold_num";
    private static final String PROP_IS_ENOUGH_KEY =  "prop_is_enough";
    private static final String REPLY_QUANTITY_KEY = "reply_quantity";
    private static final String MEDICIEN_TYPE_KEY =  "medicine_type";
    private static final String REVIVE_TIME_KEY = "revive_time";
    private static final String HERO_CAN_ASSIGN_POINT = "can_assign_point";
    private static final String PHYSIC_KEY = "physic";
    private static final String MENTAL_KEY = "mental";
    private static final String POWER_KEY = "power";
    private static final String ENDURANCE_KEY = "endurance";
    private static final String AGILITY_KEY = "agility";
    private static final String DIAMON_KEY = "use_diamon";
    private static final String TRAIN_TIME = "train_time";
    private static final String PRE_EXP = "pre_exp";

    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    //英雄列表响应字段
    private static final String HEROS_KEY = "heros";
    private static final String HERO_RACE_KEY = "hero_race";
    private static final String PHYSIC_ATTACK_KEY = "physic_attack";
    private static final String PHYSIC_DEFENSE_KEY = "physic_defense";
    private static final String MAGIC_ATTACK_KEY = "magic_attack";
    private static final String MAGIC_DEFENSE_KEY = "magic_defense";
    private static final String HP_KEY = "hp";
    private static final String MP_KEY = "mp";
    private static final String MAX_HP_KEY = "max_hp";
    private static final String MAX_MP_KEY = "max_mp";
    private static final String SPEED_KEY = "speed";
    private static final String DISTANCE_ATTACK_KEY = "distance_attack";
    private static final String DISTANCE_MOVE_KEY = "distantce_move";
    private static final String ATTCK_SPEED_KEY = "attack_speed";
    private static final String QUALITY_KEY = "quality";
    private static final String HERO_NAME_KEY =  "hero_name";
    private static final String HERO_LEV_KEY = "hero_lev";
    private static final String HERO_EXP_KEY = "hero_exp";
    private static final String HERO_UP_EXP_KEY = "hero_up_exp";
    private static final String HERO_COMMAND_KEY = "hero_command";
    private static final String HERO_GENERAL_ID_KEY = "hero_general_id";
    private static final String HERO_ICON_KEY = "hero_icon";
    private static final String HERO_SHT_ICON_KEY = "sht_icon";
    
    private static final String HERO_STATUS_KEY = "hero_status";
    private static final String HERO_REVIVE_TIME_KEY = "revive_time";
    private static final String NOW_CITY_ID_KEY = "now_cityid";
    
    
    private static final String HERO_IS_HIRE_KEY = "is_hire";
    
    
    
    private static final String EQUIPS_KEY = "equips";
    private static final String EQUIP_PRO_ID_KEY = "role_prop_id";
    
    /** 已训练时间**/
    private static final String ALREADY_TRAIN = "alr_train";
    /** 训练获得经验**/
    private static final String ALREADY_TRAIN_EXP = "alr_train_exp";
    /** 剩余训练时间**/
    private static final String LAST_TRAIN = "last_train";
    /**
     * 将上传的信息解析到英雄 实体中
     * @param info
     * @return
     */
    public static HeroDomain getHeroInfo(String info)
    {
        //创建英雄实体
        HeroDomain heroDomain = new HeroDomain();
        
        //获取data信息
        JSONObject heroData = JSONUtil.getData(info);
        
        //将信息分解到英雄实体中
        heroDomain.setType(heroData.getInt(HERO_TYPE_KEY));
        heroDomain.setRoleId(heroData.has(ROLE_ID_KEY) ? heroData.getLong(ROLE_ID_KEY) : 0);
        heroDomain.setHeroId(heroData.has(HERO_ID_KEY) ? heroData.getInt(HERO_ID_KEY) : 0);
        heroDomain.setRolePropId(heroData.has(ROLE_PROP_ID_KEY) ? heroData.getLong(ROLE_PROP_ID_KEY) : 0);
        heroDomain.setHeroaddPropId(heroData.has(HERO_ADD_PROP_ID_KEY) ? heroData.getLong(HERO_ADD_PROP_ID_KEY) : 0);
        heroDomain.setHeroDelePropId(heroData.has(HERO_DELE_PROP_ID_KEY) ? heroData.getLong(HERO_DELE_PROP_ID_KEY) : 0);
        heroDomain.setRoleHeroId(heroData.has(ROLE_HERO_ID_KEY) ? heroData.getLong(ROLE_HERO_ID_KEY) : 0);
        heroDomain.setCity_id(heroData.has(CITY_ID_KEY) ? heroData.getLong(CITY_ID_KEY) : 0);
        heroDomain.setGoldNum(heroData.has(GOLD_NUM_KEY) ? heroData.getInt(GOLD_NUM_KEY) : 0);
        heroDomain.setProp_is_enough(heroData.has(PROP_IS_ENOUGH_KEY) ? heroData.getInt(PROP_IS_ENOUGH_KEY) : 0);
        heroDomain.setReply_quantity(heroData.has(REPLY_QUANTITY_KEY) ? heroData.getInt(REPLY_QUANTITY_KEY) : 0);
        heroDomain.setMedicine_type(heroData.has(MEDICIEN_TYPE_KEY) ? heroData.getInt(MEDICIEN_TYPE_KEY) : 0);
        heroDomain.setRevive_time(heroData.has(REVIVE_TIME_KEY) ? heroData.getInt(REVIVE_TIME_KEY) : 0);
        heroDomain.setAgility(heroData.has(AGILITY_KEY) ? heroData.getInt(AGILITY_KEY) : 15);
        heroDomain.setEndurance(heroData.has(ENDURANCE_KEY) ? heroData.getInt(ENDURANCE_KEY) : 15);
        heroDomain.setPhysique(heroData.has(PHYSIC_KEY) ? heroData.getInt(PHYSIC_KEY) : 15);
        heroDomain.setHero_power(heroData.has(POWER_KEY) ? heroData.getInt(POWER_KEY) : 15);
        heroDomain.setMentality(heroData.has(MENTAL_KEY) ? heroData.getInt(MENTAL_KEY) : 15);
        heroDomain.setUse_diamon(heroData.has(DIAMON_KEY) ? heroData.getInt(DIAMON_KEY) : 0);
        heroDomain.setHero_name(heroData.has(HERO_NAME_KEY) ? heroData.getString(HERO_NAME_KEY) : "");
        heroDomain.setTrain_time(heroData.has(TRAIN_TIME) ? heroData.getInt(TRAIN_TIME) : 0);
        heroDomain.setPre_exp(heroData.has(PRE_EXP) ? heroData.getInt(PRE_EXP) : 0);
        heroDomain.setHeroLev(heroData.has(HERO_LEV_KEY) ? heroData.getInt(HERO_LEV_KEY) : 1);
        heroDomain.setHeroIds(heroData.has("heroIds") ? heroData.getString("heroIds") : "");
        //仅当英雄加点时才会传送这些数据
        if (heroData.has(HERO_CAN_ASSIGN_POINT))
        {
            heroDomain.setCan_assign_point(heroData.getInt(HERO_CAN_ASSIGN_POINT));
        }

        return heroDomain;
    }
    
    /**
     * 获取英雄列表信息
     * @return
     */
    public static String getHeroListInfo(List<RoleHero> heroList)
    {
        //创建英雄列表json对象
        JSONObject heroListObj = new JSONObject();
        heroListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //英雄列表数组对象
        JSONArray arr = new JSONArray();
        if (heroList != null)
        {
            for (int i = 0; i < heroList.size(); i++)
            {
                //英雄对象
                RoleHero hero = heroList.get(i);
                JSONObject heroObj = new JSONObject();
                heroObj.put(HERO_NAME_KEY, hero.getHero_name());
                heroObj.put(ROLE_HERO_ID_KEY, hero.getRole_hero_id());
                heroObj.put(HERO_LEV_KEY, hero.getHero_lev());
                heroObj.put(HERO_RACE_KEY, hero.getRace());
                heroObj.put(QUALITY_KEY, hero.getQuality());
                heroObj.put(HERO_ICON_KEY, hero.getIcon());
                heroObj.put(HERO_SHT_ICON_KEY, hero.getSht_icon());
                heroObj.put(HP_KEY, hero.getHp());
                heroObj.put(MP_KEY, hero.getMp());
                heroObj.put(MAX_HP_KEY, hero.getHp_max_show());
                heroObj.put(MAX_MP_KEY, hero.getMp_max_show());
                heroObj.put(PHYSIC_KEY, hero.getPhysique());
                heroObj.put(MENTAL_KEY, hero.getMentality());
                heroObj.put(POWER_KEY, hero.getHero_power());
                heroObj.put(ENDURANCE_KEY, hero.getEndurance());
                heroObj.put(AGILITY_KEY, hero.getAgility());
                heroObj.put(PHYSIC_ATTACK_KEY, hero.getPhysic_attack_show());
                heroObj.put(PHYSIC_DEFENSE_KEY, hero.getPhysic_defence_show());
                heroObj.put(MAGIC_ATTACK_KEY, hero.getMagic_attack_show());
                heroObj.put(MAGIC_DEFENSE_KEY, hero.getMagic_defence_show());
                heroObj.put(SPEED_KEY, hero.getSpeed_inmap());
                heroObj.put(DISTANCE_ATTACK_KEY, hero.getDistance_attack_show());
                heroObj.put(DISTANCE_MOVE_KEY, hero.getDistance_move_show());
                heroObj.put(ATTCK_SPEED_KEY, hero.getSpeed_show());
                heroObj.put(HERO_CAN_ASSIGN_POINT, hero.getCan_assign_point());
                heroObj.put(HERO_STATUS_KEY, hero.getIs_free());
                heroObj.put(HERO_EXP_KEY, hero.getHero_exp());
                heroObj.put(HERO_UP_EXP_KEY, hero.getHero_up_exp());
                heroObj.put(CITY_ID_KEY, hero.getCity_id());
                heroObj.put(HERO_COMMAND_KEY, hero.getCommand());
                heroObj.put(NOW_CITY_ID_KEY, hero.getNow_cityid());
                
                heroObj.put(HERO_TYPE_KEY, hero.getHero_type());
                heroObj.put(ALREADY_TRAIN_EXP, hero.getTrain_exp());
                heroObj.put(LAST_TRAIN, hero.getTrain_last_time());
                int seconds = 0;
                if (hero.getRevLstSec() != null)
                {
                   seconds = hero.getRevLstSec() <= 0 ? 0 : hero.getRevLstSec();
                }
                
                heroObj.put(HERO_REVIVE_TIME_KEY, seconds);
                
                arr.add(heroObj);
            }
        }
        
        heroListObj.put(HEROS_KEY, arr);
        
        return heroListObj.toString();
    }
    
    /**
     * 查询可召唤英雄列表
     * @param heroList
     * @return
     */
    public static String getCityHeroInfo(List<Hero> heroList)
    {
        //创建英雄列表json对象
        JSONObject heroListObj = new JSONObject();
        heroListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //英雄列表数组对象
        JSONArray arr = new JSONArray();
        if (heroList != null)
        {
            for (int i = 0; i < heroList.size(); i++)
            {
                //英雄对象
                Hero hero = heroList.get(i);
                JSONObject heroObj = new JSONObject();
                
                heroObj.put(HERO_NAME_KEY, hero.getHero_name());
                heroObj.put(HERO_ICON_KEY, hero.getIcon());
                heroObj.put(HERO_SHT_ICON_KEY, hero.getSht_icon());
                heroObj.put(HERO_TYPE_KEY, hero.getHero_type());
                heroObj.put(HERO_IS_HIRE_KEY, hero.getIs_hired());
                heroObj.put(HERO_ID_KEY, hero.getHero_id());
                heroObj.put(QUALITY_KEY, hero.getQuality());
                heroObj.put(PHYSIC_KEY, hero.getPhysique());
                heroObj.put(MENTAL_KEY, hero.getMentality());
                heroObj.put(POWER_KEY, hero.getHero_power());
                heroObj.put(ENDURANCE_KEY, hero.getEndurance());
                heroObj.put(AGILITY_KEY, hero.getAgility());
                
                arr.add(heroObj);
            }
        }
        
        heroListObj.put(HEROS_KEY, arr);
        
        return heroListObj.toString();
    }
    
    /**
     * 招募英雄结果返回字符串
     * @param id
     * @return
     */
    public static String getHireHeroResponse(long id)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (id > 0)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(ROLE_HERO_ID_KEY, id);
        }
        else if(id == -2)
        {
        	//存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.HIRE_HERO_ALREADY_FLUSH);
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
     * 构建英雄详细信息JSON字符串
     * @param heros
     * @return
     */
    public static String getHeroListDetailInfo(List<RoleHero> heros){
    	
    	//英雄json对象
        JSONObject herosObj = new JSONObject();
        herosObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        JSONArray heroAJ = new JSONArray();
        
        if(heros != null && heros.size() > 0){
        	 JSONObject heroObj;
             for (RoleHero roleHero : heros) {
     			heroObj = new JSONObject();
     			constrHeroJson(heroObj, roleHero);
     			
     			heroAJ.add(heroObj);
     		}
        }
       
        herosObj.put("heros", heroAJ);
    	return herosObj.toString();
    }
    
    /**
     * 查询英雄的详细信息
     * @param obj
     * @param type 1--- 英雄详细信息
     * @return
     */
    public static String getHeroDetailInfo(RoleHero hero)
    {
        //英雄json对象
        JSONObject heroObj = new JSONObject();
        
        if (hero != null)
        {
        	heroObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            //写入该英雄的详细信息
        	constrHeroJson(heroObj,hero);
        }
        else 
        {
            heroObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            heroObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return heroObj.toString(); 
    }
    
    private static void constrHeroJson(JSONObject heroObj,RoleHero hero){
    	
    	//写入该英雄的详细信息
    	heroObj.put("role_hero_id", hero.getRole_hero_id());
        heroObj.put(PHYSIC_ATTACK_KEY, hero.getPhysic_attack_show());
        heroObj.put(PHYSIC_DEFENSE_KEY, hero.getPhysic_defence_show());
        heroObj.put(MAGIC_ATTACK_KEY, hero.getMagic_attack_show());
        heroObj.put(MAGIC_DEFENSE_KEY, hero.getMagic_defence_show());
        heroObj.put(DISTANCE_MOVE_KEY, hero.getDistance_move_show());
        heroObj.put(DISTANCE_ATTACK_KEY, hero.getDistance_attack_show());
        heroObj.put(MAX_HP_KEY, hero.getHp_max_show());
        heroObj.put(MAX_MP_KEY, hero.getMp_max_show());
        heroObj.put(HP_KEY, hero.getHp());
        heroObj.put(MP_KEY, hero.getMp());
        heroObj.put(ATTCK_SPEED_KEY, hero.getSpeed_show());
        heroObj.put(HERO_STATUS_KEY, hero.getIs_free());
        heroObj.put(CITY_ID_KEY, hero.getCity_id());
        heroObj.put(NOW_CITY_ID_KEY, hero.getNow_cityid());
        heroObj.put(PHYSIC_KEY, hero.getPhysique());
        heroObj.put(MENTAL_KEY, hero.getMentality());
        heroObj.put(POWER_KEY, hero.getHero_power());
        heroObj.put(ENDURANCE_KEY, hero.getEndurance());
        heroObj.put(AGILITY_KEY, hero.getAgility());
        heroObj.put(HERO_UP_EXP_KEY, hero.getHero_up_exp());
        heroObj.put(HERO_CAN_ASSIGN_POINT, hero.getCan_assign_point());
            
        heroObj.put(HERO_ICON_KEY, hero.getIcon());
        heroObj.put(SPEED_KEY, hero.getSpeed_inmap());
        heroObj.put(QUALITY_KEY, hero.getQuality());
        heroObj.put(HERO_NAME_KEY, hero.getHero_name());
        heroObj.put(HERO_LEV_KEY, hero.getHero_lev());
        heroObj.put(HERO_EXP_KEY, hero.getHero_exp());
        heroObj.put(HERO_COMMAND_KEY, hero.getCommand());
        heroObj.put(HERO_GENERAL_ID_KEY, hero.getGeneral_id());
        heroObj.put(HERO_SHT_ICON_KEY, hero.getSht_icon());
        heroObj.put("sex", hero.getSex());
        
    }
    
    /**
     * 查询英雄的详细信息
     * @param obj
     * @return
     */
    public static String baseHeroDetailResponse(Hero hero)
    {
        //英雄json对象
        JSONObject heroObj = new JSONObject();
        
        if (hero != null)
        {
            heroObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            
            //写入该英雄的详细信息
            heroObj.put(HERO_ICON_KEY, hero.getIcon());
            heroObj.put(HERO_SHT_ICON_KEY, hero.getSht_icon());
            heroObj.put(PHYSIC_KEY, hero.getPhysique());
            heroObj.put(MENTAL_KEY, hero.getMentality());
            heroObj.put(POWER_KEY, hero.getHero_power());
            heroObj.put(ENDURANCE_KEY, hero.getEndurance());
            heroObj.put(AGILITY_KEY, hero.getAgility());
            heroObj.put(HP_KEY, hero.getHp());
            heroObj.put(MP_KEY, hero.getMp());
            heroObj.put(PHYSIC_ATTACK_KEY, hero.getPhysic_attack());
            heroObj.put(PHYSIC_DEFENSE_KEY, hero.getPhysic_defence());
            heroObj.put(MAGIC_ATTACK_KEY, hero.getMagic_attack());
            heroObj.put(MAGIC_DEFENSE_KEY, hero.getMagic_defence());
            heroObj.put(SPEED_KEY, hero.getSpeed_inmap());
            heroObj.put(DISTANCE_MOVE_KEY, hero.getDistance_move());
            heroObj.put(ATTCK_SPEED_KEY, hero.getSpeed());
            heroObj.put(QUALITY_KEY, hero.getQuality());
            heroObj.put(HERO_NAME_KEY, hero.getHero_name());
            heroObj.put(DISTANCE_ATTACK_KEY, hero.getDistance_attack());
        }
        else 
        {
            heroObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            heroObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return heroObj.toString(); 
    }
    
    
  
    
    /**
     * 获取英雄已穿戴装备基本信息 
     * @param heroEquips
     * @return
     */
    public static String getHeroOnEquipBase(List<RolePropsEquipPro> heroEquips){
    	// 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        JSONArray arr = new JSONArray();
        if(heroEquips!=null){
        	for (RolePropsEquipPro object : heroEquips) {
        		 JSONObject equipObj = new JSONObject();
        		 equipObj.put(EQUIP_PRO_ID_KEY, object.getRole_props_id());
        		 arr.add(equipObj);
        	}
        	resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        	resultObj.put(EQUIPS_KEY, arr);
        }else{
        	resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
        	resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
}
