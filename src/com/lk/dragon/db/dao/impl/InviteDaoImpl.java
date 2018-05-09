/**
 *
 *
 * 文件名称： InviteDaoImpl.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:56:17
 */
package com.lk.dragon.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.IInviteDao;
import com.lk.dragon.db.domain.Invite;
import com.lk.dragon.db.domain.InviteReward;
import com.lk.dragon.db.domain.InviteRole;
import com.lk.dragon.db.domain.RoleInvite;
import com.lk.dragon.db.domain.RoleProps;

@Repository("inviteDao")
public class InviteDaoImpl extends BaseSqlMapDao implements IInviteDao
{

    @Override
    public Long createInviteCode(Invite invite)throws Exception
    {
        return (Long) getSqlMapClientTemplate().insert("inviteMap.createInviteCode", invite);
    }

    @Override
    public Integer getCode(String code) throws Exception
    {
        return (Integer) getSqlMapClientTemplate().queryForObject("inviteMap.getCode", code);
    }

    @Override
    public InviteReward getReward(int reward_id) throws Exception
    {
        return (InviteReward) getSqlMapClientTemplate().queryForObject("inviteMap.getReward", reward_id);
    }

    @Override
    public Invite getInvite(long role_id) throws Exception
    {
        return (Invite) getSqlMapClientTemplate().queryForObject("inviteMap.getInvite", role_id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InviteRole> getInviteRoles(long role_id) throws Exception
    {
        return (List<InviteRole>) getSqlMapClientTemplate().queryForList("inviteMap.getInviteRole", role_id);
    }

    @Override
    public Integer getIsBind(long role_id) throws Exception
    {
        return (Integer) getSqlMapClientTemplate().queryForObject("inviteMap.getIsBind", role_id);
    }

    @Override
    public Long getInviteRoleId(String code) throws Exception
    {
        return (Long) getSqlMapClientTemplate().queryForObject("inviteMap.getRoleIdByCode", code);
    }

    @Override
    public Object bindInviteCode(InviteRole inviteRole) throws Exception
    {
        return getSqlMapClientTemplate().insert("inviteMap.createBind", inviteRole);
    }

    @Override
    public Integer updateCanGetReward(Invite invite) throws Exception
    {
        return (Integer) getSqlMapClientTemplate().update("inviteMap.updateRewardCanGet", invite);
    }

    @Override
    public Long getInviteId(long role_id) throws Exception
    {
        return (Long) getSqlMapClientTemplate().queryForObject("inviteMap.getInviteId", role_id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Integer getOverLevCount(long role_id, int lev) throws Exception
    {
        Map map = new HashMap();
        map.put("role_id", role_id);
        map.put("lev", lev);

        return (Integer) getSqlMapClientTemplate().queryForObject("inviteMap.getOverLevCount", map);
    }

    @Override
    public Integer getInviteRoleNum(long role_id) throws Exception
    {
        return (Integer) getSqlMapClientTemplate().queryForObject("inviteMap.getRoleHasCode", role_id);
    }

	@Override
	public List<InviteReward> getInviteRewardInfo() throws Exception {
		return getSqlMapClientTemplate().queryForList("inviteMap.getInviteRewardInfo");
	}

	@Override
	public List<RoleInvite> getInviteRewardInfoMySelf(RoleInvite roleInvite) throws Exception {
		return getSqlMapClientTemplate().queryForList("inviteMap.getInviteRewardInfoMySelf", roleInvite);
	}

	@Override
	public List<RoleProps> getInviteRewardInfoById(int info_id)throws Exception {
		return getSqlMapClientTemplate().queryForList("inviteMap.getInviteRewardInfoById", info_id);
	}

	@Override
	public int updateInviteStatus(RoleInvite roleInvite) throws Exception {
		return getSqlMapClientTemplate().update("inviteMap.updateInviteStatus", roleInvite);
	}
}
