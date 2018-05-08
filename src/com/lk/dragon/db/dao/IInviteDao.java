/**
 * Copyright ? 2015，成都乐控
 * All Rights Reserved.
 * 文件名称： IInviteDao.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:55:18
 */
package com.lk.dragon.db.dao;

import java.util.List;

import com.lk.dragon.db.domain.Invite;
import com.lk.dragon.db.domain.InviteReward;
import com.lk.dragon.db.domain.InviteRole;
import com.lk.dragon.db.domain.RoleInvite;
import com.lk.dragon.db.domain.RoleProps;

public interface IInviteDao
{
    /**
     * 生成邀请码
     */
    public Long createInviteCode(Invite invite)throws Exception;
    
    /**
     * 验证邀请码是否重复、或者存在
     * @param code
     * @return
     * @throws Exception
     */
    public Integer getCode(String code) throws Exception;
    
    /**
     * 查询奖励
     * @param reward_id
     * @return
     * @throws Exception
     */
    public InviteReward getReward(int reward_id) throws Exception;
    
    /**
     * 查询奖励领取情况
     * @param role_id
     * @return
     * @throws Exception
     */
    public Invite getInvite(long role_id)throws Exception;
    
    /**
     * 查询已邀请人信息
     * @param role_id
     * @return
     * @throws Exception
     */
    public List<InviteRole> getInviteRoles(long role_id)throws Exception;
    
    /**
     * 检测是否账号被绑定
     * @param role_id
     * @return
     * @throws Exception
     */
    public Integer getIsBind(long role_id)throws Exception;
    
    /**
     * 根据邀请码，获取邀请人角色id的信息
     * @param code
     * @return
     * @throws Exception
     */
    public Long getInviteRoleId(String code)throws Exception;
    
    /**
     * 绑定邀请码
     * @param role_id  受邀请人id（新入住人id）
     * @param invite_id  邀请人id（发邀请码人的id）
     * @return
     */
    public Object bindInviteCode(InviteRole inviteRole)throws Exception;
    
    /**
     * 更新奖励领取资格
     * @param invite
     * @return
     * @throws Exception
     */
    public Integer updateCanGetReward(Invite invite)throws Exception;
    
    /**
     * 根据受邀请人id获取邀请人信息
     * @param role_id
     * @return
     * @throws Exception
     */
    public Long getInviteId(long role_id)throws Exception;
    
    /**
     * 获取受邀请人超过某个等级的人数
     * @param role_id
     * @param lev
     * @return
     * @throws Exception
     */
    public Integer getOverLevCount(long role_id, int lev)throws Exception;
    
    /**
     * 验证角色是否拥有邀请码
     * @param role_id
     * @return
     * @throws Exception
     */
    public Integer getInviteRoleNum(long role_id)throws Exception;
    
    
    /**
     * 查询邀请任务奖励详情
     * @return
     * @throws Exception
     */
    public List<InviteReward> getInviteRewardInfo()throws Exception;
    
    /**
     * 查询个人邀请奖励领取状况
     * @return
     * @throws Exception
     */
    public List<RoleInvite> getInviteRewardInfoMySelf(RoleInvite roleInvite)throws Exception;
    
    /**
     * 根据任务ID查询奖励详情
     * @param info_id
     * @return
     * @throws Exception
     */
    public List<RoleProps> getInviteRewardInfoById(int info_id)throws Exception;
    
    /**
     * 更改领取标志
     * @param roleInvite
     * @return
     * @throws Exception
     */
    public int updateInviteStatus(RoleInvite roleInvite)throws Exception;
}
