/**
 *
 *
 * 文件名称： GuildAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:33:03
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Faction;
import com.lk.dragon.db.domain.RoleFaction;
import com.lk.dragon.server.domain.GuildDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class GuildRequestAnalysis
{
    /******************** 请求字段键值 *******************/
    private static final String GUILD_TYPE_KEY = "guildType";
    private static final String GUILD_ID_KEY = "guild_id";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String ROLE_APPLY_ID_KEY = "apply_role_id";
    private static final String SHOT_ID_KEY = "shot_id";
    private static final String ROLE_GUILD_ID_KEY = "role_guild_id";
    private static final String GUILD_ANNOUNCEMENT_KEY = "guild_announcement";
    private static final String GUILD_ICON_KEY = "guild_icon";
    private static final String GUILD_JOB_NAME_KEY = "guild_job_name";
    private static final String GUILD_NAME_KEY = "guild_name";
    private static final String GOLD_NUM_KEY = "gold_num";
    private static final String ROLE_NAME_KEY = "role_name";
    private static final String APPLY_NAME_KEY = "apply_name";
    private static final String SHOT_NAME_KEY = "shot_name";
    private static final String AWARD_LEV_KEY = "award_lev";
    private static final String ROLE_AFTER_AWARD_KEY = "award_result";
    private static final String GUILD_POSITION_ID_KEY = "guild_position_id";
    private static final String OPERATOR_ID_KEY = "operator_id";
    
    /********************* 响应字符串 ****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    //公会列表信息
    private static final String GUILD_KEY = "guilds";
    private static final String ROLE_KEY = "roles";
    private static final String GUILD_MEMBER_KEY = "guildMembers";
    private static final String RECORD_KEY = "records";
    private static final String GUILD_LEV_KEY = "guild_lev";
    private static final String GUILD_ATTRIBUTE_KEY = "guild_attribute";
    private static final String GUILD_MEMBER_COUNT_KEY = "member_count";
    private static final String GUILD_MAX_MEMBER_COUNT_KEY = "member_max_count";
    private static final String GUILD_LEADER_NAME_KEY = "guild_leader_name";
    
    //公会成员列表
    private static final String RIGHT_LEV_KEY = "right_lev";
    private static final String ROLE_LEV_KEY = "role_lev";
    private static final String ROLE_RACE_KEY = "role_race";
    private static final String ROLE_IS_ONLINE_KEY = "is_online";
    private static final String ROLE_ICON_KEY = "icon";
    
    //公会日志
    private static final String GUILD_LOG_INFO_KEY = "log_info";
    
    //公会奖励
    private static final String GUILD_AWARD_KEY = "guild_award";
    
    //职位列表
    private static final String GUILD_POSITION_KEY = "positions";
    
    /**
     * 将json字符串转化到公会实体中
     * @param json
     * @return
     */
    public static GuildDomain getGuildInfo(String json)
    {
        //创建公会实体
        GuildDomain guildDomain = new GuildDomain();
        
        //获取data信息
        JSONObject guildData = JSONUtil.getData(json);
        
        //将信息分解到公会实体中
        guildDomain.setType(guildData.getInt(GUILD_TYPE_KEY));
        guildDomain.setGuildId(guildData.has(GUILD_ID_KEY) ? guildData.getLong(GUILD_ID_KEY) : 0);
        guildDomain.setRoleId(guildData.has(ROLE_ID_KEY) ? guildData.getLong(ROLE_ID_KEY) : 0);
        guildDomain.setRole_guild_id(guildData.has(ROLE_GUILD_ID_KEY) ? guildData.getLong(ROLE_GUILD_ID_KEY) : 0);
        guildDomain.setGuildAnnouncement(guildData.has(GUILD_ANNOUNCEMENT_KEY) ? guildData.getString(GUILD_ANNOUNCEMENT_KEY) : null);
        guildDomain.setGuildIcon(guildData.has(GUILD_ICON_KEY) ? guildData.getString(GUILD_ICON_KEY) : null);
        guildDomain.setGuildJobName(guildData.has(GUILD_JOB_NAME_KEY) ? guildData.getString(GUILD_JOB_NAME_KEY) : null);
        guildDomain.setGuildName(guildData.has(GUILD_NAME_KEY) ? guildData.getString(GUILD_NAME_KEY) : null);
        guildDomain.setGold_num(guildData.has(GOLD_NUM_KEY) ? guildData.getInt(GOLD_NUM_KEY) : 0);
        guildDomain.setApplyRoleId(guildData.has(ROLE_APPLY_ID_KEY) ? guildData.getLong(ROLE_APPLY_ID_KEY) : 0);
        guildDomain.setShotId(guildData.has(SHOT_ID_KEY) ? guildData.getLong(SHOT_ID_KEY) : 0);
        guildDomain.setRoleName(guildData.has(ROLE_NAME_KEY) ? guildData.getString(ROLE_NAME_KEY) : null);
        guildDomain.setApplyRoleName(guildData.has(APPLY_NAME_KEY) ? guildData.getString(APPLY_NAME_KEY) : null);
        guildDomain.setShotName(guildData.has(SHOT_NAME_KEY) ? guildData.getString(SHOT_NAME_KEY) : null);
        guildDomain.setAward_lev(guildData.has(AWARD_LEV_KEY) ? guildData.getInt(AWARD_LEV_KEY) : 0);
        guildDomain.setAfter_award_result(guildData.has(ROLE_AFTER_AWARD_KEY) ? guildData.getInt(ROLE_AFTER_AWARD_KEY) : 0);
        guildDomain.setPosition_id(guildData.has(GUILD_POSITION_ID_KEY) ? guildData.getLong(GUILD_POSITION_ID_KEY) : 0);        
        guildDomain.setOperator_id(guildData.has(OPERATOR_ID_KEY) ? guildData.getLong(OPERATOR_ID_KEY) : 0);        
        return guildDomain;
    }
    
    /**
     * 获取创建公会后的响应字符串
     * @param map
     * @return
     */
    public static String getCreateGuildResponse(Map<String,Long> map)
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
        
        if (result == Constants.FACTION_CREATE_SUCCESS)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(GUILD_ID_KEY, map.get(Constants.ID_KEY));
        }
        else if (result == Constants.FACTION_NAME_USED)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.GUILD_NAME_REPEAT);
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
     * 获取 公会列表信息响应字符串
     * type:
     *      1------公会列表
     *      2------申请加入公会列表
     *      3------玩家已申请的公会列表
     *      4------公会职位列表信息
     * @return
     */
    public static String getGuildListResponse(List<Faction> factionList, int type)
    {
        //公会列表信息json对象
        JSONObject guildListObj = new JSONObject();
        guildListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //json数组对象
        JSONArray arr = new JSONArray();
        if (factionList != null)
        {
            for (Faction faction: factionList)
            {
                JSONObject guildObj = new JSONObject();
                if (type == 1)
                {
                    guildObj.put(GUILD_ID_KEY, faction.getFaction_id());
                    guildObj.put(GUILD_NAME_KEY, faction.getFaction_name());
                    guildObj.put(GUILD_LEV_KEY, faction.getFaction_lev());
                    guildObj.put(GUILD_ICON_KEY, faction.getIcon());
                    guildObj.put(GUILD_ANNOUNCEMENT_KEY, faction.getFaction_public());
                    guildObj.put(GUILD_MEMBER_COUNT_KEY, faction.getMember_counts());
                    guildObj.put(GUILD_MAX_MEMBER_COUNT_KEY, faction.getMax_member_counts());
                    guildObj.put(GUILD_ATTRIBUTE_KEY, faction.getFaction_score());
                    guildObj.put(GUILD_LEADER_NAME_KEY, faction.getLeader_name());
                }
                else if (type == 2)
                {
                    guildObj.put(ROLE_ID_KEY, faction.getRole_id());
                    guildObj.put(ROLE_NAME_KEY, faction.getRole_name());
                    guildObj.put(ROLE_RACE_KEY, faction.getRole_race());
                    guildObj.put(ROLE_LEV_KEY, faction.getRole_lev());
                    guildObj.put(ROLE_ICON_KEY, faction.getRole_icon());
                }
                else if (type == 3)
                {
                    guildObj.put(GUILD_ID_KEY, faction.getFaction_id());
                    guildObj.put(GUILD_NAME_KEY, faction.getFaction_name());
                }
                else if (type == 4)
                {
                    guildObj.put(GUILD_POSITION_ID_KEY, faction.getFaction_position_id());
                    guildObj.put(GUILD_JOB_NAME_KEY, faction.getPosition_name());
                    guildObj.put(RIGHT_LEV_KEY, faction.getRight_id());
                }
                
                arr.add(guildObj);
            }
        }
                
        if (type == 3 || type == 1)
        {
            guildListObj.put(GUILD_KEY, arr);
        }
        else if (type == 2)
        {
            guildListObj.put(ROLE_KEY, arr);
        }
        else if (type == 4)
        {
            guildListObj.put(GUILD_POSITION_KEY, arr);
        }
        
        return guildListObj.toString();
    }
    
    /**
     * 获取公会成员列表响应字符串
     * @param roleList
     * @return
     */
    public static String getGuildMemberList(List<RoleFaction> roleList)
    {
        //公会列表信息json对象
        JSONObject guildMemberListObj = new JSONObject();
        guildMemberListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
       
        //json数组对象
        JSONArray arr = new JSONArray();
        if (roleList != null)
        {
            for (RoleFaction role: roleList)
            {
                JSONObject roleObj = new JSONObject();
                roleObj.put(ROLE_ID_KEY, role.getRole_id());
                roleObj.put(ROLE_GUILD_ID_KEY, role.getRole_faction_id());
                roleObj.put(ROLE_ID_KEY, role.getRole_id());
                roleObj.put(ROLE_NAME_KEY, role.getRole_name());
                roleObj.put(GUILD_JOB_NAME_KEY, role.getPosition_name());
                roleObj.put(GUILD_ATTRIBUTE_KEY, role.getContribution());
                roleObj.put(ROLE_LEV_KEY, role.getRole_lev());
                roleObj.put(ROLE_RACE_KEY, role.getRole_race());
                roleObj.put(ROLE_IS_ONLINE_KEY, role.getRole_status());
                roleObj.put(RIGHT_LEV_KEY, role.getRight_id());
                roleObj.put(ROLE_ICON_KEY, role.getRole_icon());
                
                arr.add(roleObj);
            }
        }
                
        guildMemberListObj.put(GUILD_MEMBER_KEY, arr);
        
        return guildMemberListObj.toString();
    }
    
    /**
     * 获取添加人员进入公会响应字符串
     * @param roleGuildId
     * @return
     */
    public static String getRoleGuildResponse(long roleGuildId)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (roleGuildId > 0)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(ROLE_GUILD_ID_KEY, roleGuildId);
        }
        else if (roleGuildId == -1)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.ROLE_HAS_GUILD);
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
     * 获取申请加入公会响应字符串
     * @param result
     * @return
     */
    public static String getApplyInGuildResponse(int result)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (result == 1)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        }
        else if (result == -1)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.ALREADY_APPLY_IN_GUILD);
        }
        else if (result == -2)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.ROLE_HAS_GUILD);
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
     * 查询个人公会相关信息的
     * @param faction
     * @return
     */
    public static String getMySelfInfo(Faction faction)
    {
        //公会列表信息json对象
        JSONObject infoObj = new JSONObject();
        
        if (faction == null)
        {
            infoObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
        }
        else
        {
            infoObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            //公会个人权限信息
            infoObj.put(RIGHT_LEV_KEY, faction.getRight_id());
            
            //公会相关信息
            infoObj.put(GUILD_ID_KEY, faction.getFaction_id());
            infoObj.put(GUILD_NAME_KEY, faction.getFaction_name());
            infoObj.put(GUILD_LEV_KEY, faction.getFaction_lev());
            infoObj.put(GUILD_ICON_KEY, faction.getIcon());
            infoObj.put(GUILD_ANNOUNCEMENT_KEY, faction.getFaction_public());
            infoObj.put(GUILD_MEMBER_COUNT_KEY, faction.getMember_counts());
            infoObj.put(GUILD_MAX_MEMBER_COUNT_KEY, faction.getMax_member_counts());
            infoObj.put(GUILD_ATTRIBUTE_KEY, faction.getFaction_score());
            
            //公会奖励领取信息
            infoObj.put(GUILD_AWARD_KEY, faction.getGet_reward_flag());
        }
        
        return infoObj.toString();
    }
    
    /**
     * 
     * @return
     */
    public static String guildRecordResponse(List<Faction> recordList)
    {
        //公会列表信息json对象
        JSONObject recordListObj = new JSONObject();
        recordListObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //json数组对象
        JSONArray arr = new JSONArray();
        if (recordList != null)
        {
            for (Faction record: recordList)
            {
                JSONObject recordObj = new JSONObject();
                recordObj.put(GUILD_LOG_INFO_KEY, record.getLog_info());
                
                arr.add(recordObj);
            }
        }
                
        recordListObj.put(RECORD_KEY, arr);
        
        return recordListObj.toString();
    }
    
    /**
     * 获取公会职位删除信息
     * @param flag
     * @return
     */
    public static String getDeleGuildPosition(int flag)
    {
        String response = "";
        if (flag == 1)
        {
            response = JSONUtil.getBooleanResponse(true);
        }
        else if (flag == 0)
        {
            response = JSONUtil.getWrongResponse(Constants.POSITION_IS_USED);
        }
        else if (flag == -1)
        {
            response = JSONUtil.getWrongResponse(Constants.NET_ERROR);
        }
        
        return response;
    }
    
    /**
     * 新增公会职位
     * @param id
     * @return
     */
    public static String addGuildPosition(long position_id)
    {
     // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (position_id > 0)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            resultObj.put(GUILD_POSITION_ID_KEY, position_id);
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
}
