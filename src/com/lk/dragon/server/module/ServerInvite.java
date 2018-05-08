/**
 * Copyright ? 2015，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerReward.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午3:58:22
 */
package com.lk.dragon.server.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Invite;
import com.lk.dragon.db.domain.InviteRole;
import com.lk.dragon.server.domain.InviteDomain;
import com.lk.dragon.server.module.analysis.InviteRequestAnalysis;
import com.lk.dragon.service.InviteService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerInvite
{
    /** 邀请实体 **/
    private InviteDomain rewardDomain;
    
    @Autowired
    private InviteService inviteService;
    public ServerInvite(){}
    
    /**
     * 构造函数
     * @param friendDomain
     */
    public ServerInvite(InviteDomain rewardDomain)
    {
        this.inviteService = SpringBeanUtil.getBean(InviteService.class);
        this.rewardDomain = rewardDomain;
    }
    
    /**
     * 好友模块内部分析模块
     */
    public void RewardAnalysis()
    {
        switch (rewardDomain.getType())
        {
            case Constants.REWARD_BUND_TYPE:
            {
                //绑定
                bindCode();
                break;
            }
            case Constants.REWARD_INFO_TYPE:
            {
                //获取奖励详情
                getInviteRewardInfo();
                break;
            }
            case Constants.REWARD_RECEIVE_TYPE:
            {
                //领取奖励
                getReward();
                break;
            }
            case Constants.REWARD_ROLE_TYPE:
            {
                //邀请人查询
                getInviteRoles();
                break;
            }
        }
    }
    
//    /**
//     * 生成邀请码
//     */
//    private void createInviteCode()
//    {
//        Invite invite = new Invite();
//        invite.setRole_id(rewardDomain.getRole_id());
//        
//
//        String response = "";
//        try
//        {
//            //判定用户是否已经拥有邀请码
//            if (inviteService.getRoleHasCode(rewardDomain.getRole_id()) >= 1)
//            {
//                JSONObject o = new JSONObject();
//                o.put("result", Constants.REQUEST_FAIL);
//                o.put("reason", "角色已经拥有邀请码，请勿重复生成");
//                response = o.toString();
//            }
//            else
//            {
//                invite = inviteService.createInvideCode(invite);
//                //获取响应字符串
//                response = InviteRequestAnalysis.getInviteInfo(invite);
//            }
//        } 
//        catch (Exception e)
//        {
//            response = JSONUtil.getBooleanResponse(false);
//            e.printStackTrace();
//        }
//        
//        SocketUtil.responseClient(rewardDomain.getCtx(), response);
//    }
    
    /**
     * 绑定邀请码
     */
    private void bindCode()
    {
        Invite invite = new Invite();
        invite.setRole_id(rewardDomain.getRole_id());//被邀请
        invite.setInvite_code(rewardDomain.getInvite_code());//邀请人ID
        String response = "";
        try
        {
            int result = inviteService.bindCode(invite);
            response = InviteRequestAnalysis.getBindInfo(result);
            
        } 
        catch (Exception e)
        {
            response = JSONUtil.getBooleanResponse(false);
            e.printStackTrace();
        }
        SocketUtil.responseClient(rewardDomain.getCtx(), response);
    }
    
    /**
     * 领取奖励
     */
    private void getReward()
    {
        String response = "";
        Map<String,Object> resMap;
        try
        {
          resMap = inviteService.doGetInviteReward(rewardDomain.getRole_id(), rewardDomain.getInvite_info_id());
            response = InviteRequestAnalysis.getRewardInfo(resMap);
        } 
        catch (Exception e)
        {
            response = JSONUtil.getBooleanResponse(false);
            e.printStackTrace();
        }
        
        SocketUtil.responseClient(rewardDomain.getCtx(), response);
    }
    
    /**
     * 查看奖励详情
     */
    private void getInviteRewardInfo()
    {
        String response = "";
        try
        {
            Map<String,List> inviteListMap = inviteService.getInviteRewardInfo(rewardDomain.getRole_id());
            response = InviteRequestAnalysis.getInviteInfo(inviteListMap);
        }
        catch (Exception e)
        {
            response = JSONUtil.getBooleanResponse(false);
            e.printStackTrace();
        }
        
        SocketUtil.responseClient(rewardDomain.getCtx(), response);
    }
    
    private void getInviteRoles()
    {
        String response = "";
        try
        {
            List<InviteRole> roles = inviteService.getInviteRole(rewardDomain.getRole_id());
            response = InviteRequestAnalysis.getInviteRolesInfo(roles);
        } 
        catch (Exception e)
        {
            response = JSONUtil.getBooleanResponse(false);
            e.printStackTrace();
        }
        
        SocketUtil.responseClient(rewardDomain.getCtx(), response);
    }
}
