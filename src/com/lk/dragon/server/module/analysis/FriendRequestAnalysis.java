/**
 *
 *
 * 文件名称： FriendAnalysisi.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:31:06
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.server.domain.FriendDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class FriendRequestAnalysis
{
    /******************** 请求字段 ****************************/
    private static final String FIREND_TYPE_KEY = "friendType";
    private static final String RELATION_ID_KEY = "relation_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String FRIEND_ROLE_ID_KEY = "friend_role_id";
    private static final String NAME_KEY =  "name";
    private static final String ENEMY_ID_KEY = "enemy_id";
    private static final String RELATION_TYPE_KEY = "relation_type";
    private static final String FRIEND_DEAL_TYPE = "deal_type";
    private static final String MAIL_ID_KEY = "mail_id";
    
    /******************** 响应 请求字段 *****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    //好友/仇人列表查询
    private static final String FRIEND_LEVEL_KEY = "level";
    private static final String FRIEND_SHT_ICO_KEY = "sht_ico";
    private static final String FRIEND_IS_ONLINE_KEY = "is_online";
    private static final String FRIEND_LIST_KEY = "roles";
    //好友申请
    private static final String APPLY_KEY = "applys";
    private static final String APPLY_NAME_KEY = "apply_name";
    private static final String APPLY_ID_KEY = "apply_id";
    private static final String APPLY_MAIL_ID_KEY = "apply_mail_id";
    //好友、仇人详细信息
    private static final String ROLE_RACE_KEY = "race";
    private static final String ROLE_DUKE_KEY = "duke";
    private static final String ROLE_SEX_KEY = "sex";
    private static final String ROLE_GOLD_KEY = "gold";
    private static final String ROLE_FOOD_KEY = "food";
    private static final String ROLE_WOOD_KEY = "wood";
    private static final String ROLE_STONE_KEY = "stone";  
    private static final String ROLE_CITY_NUM_KEY = "city_num";
    private static final String ROLE_EAT_NUM_KEY = "eat_num";
    
    
    /**
     * 将好友请求相关信息转化为好友实体
     * @param json
     * @return
     */
    public static FriendDomain getFriendInfo(String json)
    {
        //创建好友实体
        FriendDomain friendDomain = new FriendDomain();
        
        //获取data信息
        JSONObject friendData = JSONUtil.getData(json);
        
        //将信息分解到好友实体中
        friendDomain.setType(friendData.getInt(FIREND_TYPE_KEY));
        friendDomain.setId(friendData.has(ROLE_ID_KEY) ? friendData.getLong(ROLE_ID_KEY) : 0);
        friendDomain.setFriendId(friendData.has(FRIEND_ROLE_ID_KEY) ? friendData.getLong(FRIEND_ROLE_ID_KEY) : 0);
        friendDomain.setEnemyId(friendData.has(ENEMY_ID_KEY) ? friendData.getLong(ENEMY_ID_KEY) : 0);
//        friendDomain.setRelationType(friendData.has(RELATION_TYPE_KEY) ? friendData.getInt(RELATION_TYPE_KEY) : 0);
        friendDomain.setDealType(friendData.has(FRIEND_DEAL_TYPE) ? friendData.getInt(FRIEND_DEAL_TYPE) : 0);
        friendDomain.setMailId(friendData.has(MAIL_ID_KEY) ? friendData.getLong(MAIL_ID_KEY) : 0);
        friendDomain.setName(friendData.has(NAME_KEY) ? friendData.getString(NAME_KEY) : null);
        friendDomain.setRelation_id(friendData.has(RELATION_ID_KEY) ? friendData.getLong(RELATION_ID_KEY) : 0);
        return friendDomain;
    }
    
    /**
     * 获取角色列表响应字符串
     * @param list
     * @return
     */
    public static String getRelationListResponse(List<Role> list)
    {
        JSONObject relationObj = new JSONObject();
        relationObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //创建json数组对象
        JSONArray arr = new JSONArray();
        //判定list是否为空
        if (list != null && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                //获取Role
                Role r = list.get(i);
                //创建json对象
                JSONObject roleObj = new JSONObject();
                roleObj.put(ROLE_ID_KEY, r.getRole_id());
                roleObj.put(FRIEND_SHT_ICO_KEY, r.getSht_ico());
                roleObj.put(NAME_KEY, r.getRole_name());
                roleObj.put(FRIEND_LEVEL_KEY, r.getLev());
                roleObj.put(FRIEND_IS_ONLINE_KEY, r.getIs_online());
                roleObj.put(ROLE_RACE_KEY, r.getRace());
                roleObj.put(RELATION_TYPE_KEY, r.getRelation_type());
                
                arr.add(roleObj);
            }
        }
        
        relationObj.put(FRIEND_LIST_KEY, arr);
        
        return relationObj.toString();
    }
    
    public static String getOneRelation(Role r)
    {
        //创建json对象
        JSONObject roleObj = new JSONObject();
        if (r != null)
        {
            roleObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            roleObj.put(ROLE_ID_KEY, r.getRole_id());
            roleObj.put(FRIEND_SHT_ICO_KEY, r.getSht_ico());
            roleObj.put(NAME_KEY, r.getRole_name());
            roleObj.put(FRIEND_LEVEL_KEY, r.getLev());
            roleObj.put(FRIEND_IS_ONLINE_KEY, r.getIs_online());
            roleObj.put(ROLE_RACE_KEY, r.getRace());
            roleObj.put(RELATION_TYPE_KEY, r.getRelation_type());
        }
        else
        {
            roleObj.put(RESULT_KEY, Constants.REQUEST_ERROR);
            roleObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return roleObj.toString();
    }
    
    /**
     * 添加敌人响应字符串
     * @return
     */
    public static String addEnemyResponse(Map<String, Long> map)
    {
        JSONObject enemyObj = new JSONObject();
        
        long result = map.get(Constants.RESULT_KEY);
        
        if (result == Constants.CREATE_RELATION_SUCCESS)
        {
            //添加敌人成功
            enemyObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            enemyObj.put(ENEMY_ID_KEY, map.get(Constants.ID_KEY));
        }
        else if (result == Constants.TARGET_ALEARDY_FRIEND)
        {
            //目标已经存在好友列表中
            enemyObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            enemyObj.put(REASON_KEY, Constants.FRIEND_ALREADY_EXIT);
        }
        else if (result == Constants.TARGET_ALEARDY_ENEMY)
        {
            //目标已存在仇人列表中
            enemyObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            enemyObj.put(REASON_KEY, Constants.ENEMY_ALREADY_EXIT);
        }
        else
        {
            //添加仇人失败
            enemyObj.put(RESULT_KEY, Constants.REQUEST_ERROR);
            enemyObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return enemyObj.toString(); 
    }
    
    /**
     * 添加好友响应请求
     * @param map
     * @return
     */
    public static String addFriendResponse(Map<String, Long> map)
    {
        JSONObject friendObj = new JSONObject();
        
        long result = map.get(Constants.RESULT_KEY);
        
        if (result == Constants.SOURCE_ALEARDY_INENEMY)
        {
            //自己在对方的仇人列表中
            friendObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            friendObj.put(REASON_KEY, Constants.ENEMY_EXIT_OPPOSITE);
        }
        else if (result == Constants.TARGET_ALEARDY_FRIEND)
        {
            //已经存在好友列表中
            friendObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            friendObj.put(REASON_KEY, Constants.FRIEND_ALREADY_EXIT);
        }
        else if (result == Constants.TARGET_ALEARDY_ENEMY)
        {
            //添加仇人失败
            friendObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            friendObj.put(REASON_KEY, Constants.ENEMY_ALREADY_EXIT);
        }
        else if (result == Constants.ROLE_ADD_MYSELF)
        {
            //不能添加自己
            friendObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            friendObj.put(REASON_KEY, Constants.APPLY_MYSELF_FRIEND);
        }
        else if (result == Constants.ROLE_NOT_EXIT)
        {
            //不能添加自己
            friendObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            friendObj.put(REASON_KEY, Constants.APPLY_ROLE_NAME_NOT_EXIT);
        }
        
        
        return friendObj.toString(); 
    }
    
    /**
     * 获取好友申请邮件信息
     * @param mailList
     * @return
     */
    public static String getFriendMailResponse(List<Mail> mailList)
    {
        JSONObject relationObj = new JSONObject();
        relationObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //创建json数组对象
        JSONArray arr = new JSONArray();
        //判定list是否为空
        if (mailList != null && mailList.size() > 0)
        {
            for (int i = 0; i < mailList.size(); i++)
            {
                //获取Role
                Mail mail = mailList.get(i);
                //创建json对象
                JSONObject roleObj = new JSONObject();
                roleObj.put(APPLY_NAME_KEY, mail.getMail_from_name());
                roleObj.put(APPLY_ID_KEY, mail.getMail_from());
                roleObj.put(APPLY_MAIL_ID_KEY, mail.getMail_id());
                
                arr.add(roleObj);
            }
        }
        
        relationObj.put(APPLY_KEY, arr);
        
        return relationObj.toString();
    }
    
    /**
     * 获取好友详细信息
     * @param mailList
     * @return
     */
    public static String getFriendDetailInfo(Role r)
    {
        JSONObject roleObj = new JSONObject();
        
        if (r != null)
        {
            roleObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            roleObj.put(ROLE_ID_KEY, r.getRole_id());
            roleObj.put(FRIEND_SHT_ICO_KEY, r.getSht_ico());
            roleObj.put(NAME_KEY, r.getRole_name());
            roleObj.put(FRIEND_LEVEL_KEY, r.getLev());
            roleObj.put(ROLE_RACE_KEY, r.getRace());
            roleObj.put(ROLE_DUKE_KEY, r.getDuke());
            roleObj.put(ROLE_SEX_KEY, r.getSex());
            roleObj.put(ROLE_GOLD_KEY, r.getGold());
            roleObj.put(ROLE_FOOD_KEY, r.getFood());
            roleObj.put(ROLE_WOOD_KEY, r.getWood());
            roleObj.put(ROLE_STONE_KEY, r.getStone());
            roleObj.put(ROLE_CITY_NUM_KEY, r.getCity());
            roleObj.put(ROLE_EAT_NUM_KEY, r.getEat());
            roleObj.put("faction_name", r.getFaction() == null ? "" : r.getFaction().getFaction_name());
            
            JSONArray cityAJ = new JSONArray();
            if(r.getCitys()!= null && r.getCitys().size() > 0){
            	JSONObject cityJ ;
            	for (City city : r.getCitys()) {
					cityJ = new JSONObject();
					cityJ.put("name", city.getName());
					cityJ.put("x", city.getSite_x());
					cityJ.put("y", city.getSite_y());
					cityJ.put("race", city.getRace());
					
					cityAJ.add(cityJ);
				}
            }
            roleObj.put("citys", cityAJ);
        }
        else
        {
            roleObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            roleObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return roleObj.toString();
    } 
}
