/**
 * Copyright ? 2015，成都乐控
 * All Rights Reserved.
 * 文件名称： RewardRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午3:58:44
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Invite;
import com.lk.dragon.db.domain.InviteReward;
import com.lk.dragon.db.domain.InviteRole;
import com.lk.dragon.db.domain.RoleInvite;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.server.domain.InviteDomain;
import com.lk.dragon.service.InviteService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class InviteRequestAnalysis
{
    /******************** 请求字段 ****************************/
    private static final String REWARD_TYPE_KEY = "invite_type";
    private static final String REWARD_CODE_KEY = "code";
    private static final String ROLE_ID_KEY = "role_id";
    private static final String INDEX_KEY = "index";
    /******************** 响应 请求字段 *****************************/
    // 响应的关键字段
    private static final String ALL_INVITE_INFO = "all_info";
    private static final String MY_INVITE_INFO = "my_info";
    private static final String PROPS_ICON = "props_icon";
    private static final String PROPS_ID = "props_id";
    private static final String PROPS_COUNT = "props_count";
    private static final String PROP_TYPE_KEY = "props_type";
    private static final String PROPS = "props";
    private static final String IS_GET = "is_get";
    private static final String INVITE_IFON_ID = "info_id";
    private static final String INVITE_IFON_DES = "info_desc";
    
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    private static final String REWARD_KEY = "rewards";
    private static final String ROLE_NAME_KEY = "role_name";
    private static final String ROLE_ICON_KEY = "icon";
    private static final String ROLE_SEX_KEY = "sex";
    private static final String ROLE_LEVEL_KEY = "level";
    
    
    /**
     * 将好友请求相关信息转化为好友实体
     * @param json
     * @return
     */
    public static InviteDomain getFriendInfo(String json)
    {
        //创建好友实体
        InviteDomain rewardDomain = new InviteDomain();
        
        //获取data信息
        JSONObject inviteData = JSONUtil.getData(json);
        
        //将信息分解到好友实体中
        rewardDomain.setType(inviteData.getInt(REWARD_TYPE_KEY));
        rewardDomain.setReward_index(inviteData.has(INDEX_KEY) ? inviteData.getInt(INDEX_KEY) : 0);
        rewardDomain.setInvite_code(inviteData.has(REWARD_CODE_KEY) ? inviteData.getInt(REWARD_CODE_KEY) : 0);
        rewardDomain.setRole_id(inviteData.has(ROLE_ID_KEY) ? inviteData.getLong(ROLE_ID_KEY) : 0);
        rewardDomain.setInvite_info_id(inviteData.has(INVITE_IFON_ID) ? inviteData.getInt(INVITE_IFON_ID) : 0);
        return rewardDomain;
    }
    
    /**
     * 展示邀请奖励领取情况
     * @param invite
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String getInviteInfo(Map<String,List> map)
    {
        //总JSON对象
        JSONObject inviteObj = new JSONObject();
        inviteObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        //取得奖励集合对象List
        List<InviteReward> allReList = map.get(InviteService.ALL_KEY);
        List<RoleInvite> myReList = map.get(InviteService.MY_KEY);
        
      //-------------------------处理系统全部奖励详情---------------------------  
        //系统所有邀请奖励信息JSON数组
        JSONArray allRewadJA = new JSONArray();
        if(allReList != null && allReList.size() > 0){
        	//构造JSON元素
        	for (InviteReward allInvite : allReList) {
        		JSONObject allJ = new JSONObject();
        		allJ.put(INVITE_IFON_ID, allInvite.getInfo_id());
        		allJ.put(INVITE_IFON_DES, allInvite.getInfo_desc());
        		
        		//奖励道具集合
        		List<RoleProps> rewardInfoList = allInvite.getPropList();
        		JSONArray rewadPropsJA = new JSONArray();
        		for (RoleProps roleProps : rewardInfoList) {
					JSONObject propJ = new JSONObject();
					propJ.put(PROPS_ID, roleProps.getProps_id());
					propJ.put(PROPS_ICON,roleProps.getProps_icon());
					propJ.put(PROPS_COUNT, roleProps.getProps_count());
					rewadPropsJA.add(propJ);
				}
        		allJ.put(PROPS, rewadPropsJA);
        		
        		//存入JSONARRAY
        		allRewadJA.add(allJ);
			}
        	
        }
        //系统全部奖励信息存入总JSON对象
        inviteObj.put(ALL_INVITE_INFO, allRewadJA);
        
      //-------------------------处理个人奖励详情---------------------------  
        //个人邀请奖励信息JSON数组
        JSONArray myRewadJA = new JSONArray();
        if(myReList != null && myReList.size() > 0){
        	for (RoleInvite myInvite : myReList) {
				JSONObject myJ = new JSONObject();
				myJ.put(INVITE_IFON_ID, myInvite.getInvite_info_id());
				myJ.put(IS_GET, myInvite.getIs_get());
				
				//存入JSONARRAY
				myRewadJA.add(myJ);
			}
        }
        //系统全部奖励信息存入总JSON对象
        inviteObj.put(MY_INVITE_INFO, myRewadJA);
        
        return inviteObj.toString();
    }
    
    /**
     * 绑定邀请码结果
     * @param result
     * @return
     */
    public static String getBindInfo(int result)
    {
        JSONObject inviteObj = new JSONObject();
        inviteObj.put(RESULT_KEY, Constants.NET_ERROR);
        if (result == 0)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            inviteObj.put(REASON_KEY, "邀请码输入错误");
        }
        else if (result == 2)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            inviteObj.put(REASON_KEY, "您已经绑定过邀请码，不能重复绑定");
        }
        else if (result > 0)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            inviteObj.put("role_props_id", result);
            inviteObj.put("props_name", "新手邀请礼包");
        }else if(result == -10)
        {
        	inviteObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        	inviteObj.put(REASON_KEY, "您包裹已满,新手礼包将以邮件发送给您");
        }
        
        return inviteObj.toString();
    }
    
    @SuppressWarnings("unchecked")
	public static String getRewardInfo(Map<String,Object> map)
    {
        JSONObject inviteObj = new JSONObject();
        int resKey = (Integer)map.get(InviteService.GET_RES_KEY);
        if (resKey == 0)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            inviteObj.put(REASON_KEY, "奖励已被领取");
        }
        else if (resKey == 2)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            inviteObj.put(REASON_KEY, "未达到领取奖励资格");
        }
        else if (resKey == 1)
        {
            inviteObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            List<RoleProps> rewards = (List<RoleProps>) map.get(InviteService.GET_PROPS_KEY);
            JSONArray rewardAJ =new JSONArray();
            for (RoleProps roleProps : rewards) {
				JSONObject propJ = new JSONObject();
				propJ.put(PROPS_ID, roleProps.getProps_id());
				propJ.put("role_prop_id", roleProps.getRole_props_id());
				propJ.put(PROP_TYPE_KEY, roleProps.getProps_type());
				propJ.put(PROPS_COUNT, roleProps.getProps_count());
				
				rewardAJ.add(propJ);
			}
            inviteObj.put("props", rewardAJ);
        }
        
        return inviteObj.toString();
    }
    
    /**
     * 获取已邀请角色信息
     * @param roles
     * @return
     */
    public static String getInviteRolesInfo(List<InviteRole> roles)
    {
        JSONObject inviteObj = new JSONObject();
        inviteObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        JSONArray array = new JSONArray();
        for (InviteRole role : roles)
        {
            JSONObject o = new JSONObject();
            o.put(ROLE_NAME_KEY, role.getRole_name());
            o.put(ROLE_ICON_KEY, role.getIcon());
            o.put(ROLE_ID_KEY, role.getRole_id());
            o.put(ROLE_SEX_KEY, role.getSex());
            o.put(ROLE_LEVEL_KEY, role.getLev());
            array.add(o);
        }
        
        inviteObj.put("roles", array);
        return inviteObj.toString();
    }
}
