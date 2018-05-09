/**
 *
 *
 * 文件名称： InviteService.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-6-23 下午4:58:47
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IInviteDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Invite;
import com.lk.dragon.db.domain.InviteReward;
import com.lk.dragon.db.domain.InviteRole;
import com.lk.dragon.db.domain.RoleInvite;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.util.SerialNumberUtil;

@Service
public class InviteService
{
    @Autowired
    private IInviteDao inviteDao;
    @Autowired
    private IRolePropsDao rolePropsDao;
    @Autowired
    private IRoleDao roleDao;
    
  //存储系统奖励信息
	public static List<InviteReward>  rewardAll = null;
	
    public static final String ALL_KEY = "all";
    public static final String MY_KEY = "my";
    
    public static final String GET_RES_KEY = "get_res";
    public static final String GET_PROPS_KEY = "get_props";
    public InviteService()
    {
        super();
    }
    
//    /**
//     * 生成邀请码
//     * @param invite
//     * @return
//     */
//    public Invite createInvideCode(Invite invite) throws Exception
//    {
//        //生成邀请码
//        String code = SerialNumberUtil.toSerialNumber(invite.getRole_id());
//        
//        //检测是否重复，如果重复则需要再次生成
//        while (true)
//        {
//            if (inviteDao.getCode(code) < 1)
//            {
//                break;
//            }
//            
//            //如果重复了重新生成
//            code = SerialNumberUtil.toSerialNumber(invite.getRole_id());
//        }
//        
//        invite.setInvite_code(code);
//        
//        //入库 
//        inviteDao.createInviteCode(invite);
//        
//        return getInviteInfo(invite.getRole_id());
//    }
    
    
    /**
     * 查询各绑定奖励详细信息及本人领奖详情 
     * @param role_id
     * @return
     */
    public HashMap<String,List> getInviteRewardInfo(long role_id)throws Exception
    {
    	HashMap<String,List> resMap = new HashMap<String,List>();
    	if(rewardAll == null)
    		//查询系统全部级别的绑定奖励信息
        	rewardAll =  inviteDao.getInviteRewardInfo();
    	
    	//存储个人邀请奖励详情
    	List<RoleInvite> rewardMySelf = null;
    	//查询个人奖励信息
    	RoleInvite roleInvite = new RoleInvite();
    	roleInvite.setRole_id(role_id);
    	rewardMySelf = inviteDao.getInviteRewardInfoMySelf(roleInvite);
    	
    	resMap.put(ALL_KEY, rewardAll);
    	resMap.put(MY_KEY, rewardMySelf);
    	return resMap;
    }
    
    /**
     * 查询奖励详细信息
     * @param reward_id
     * @return
     */
    public InviteReward getReward(int reward_id) throws Exception
    {
        return inviteDao.getReward(reward_id);
    }
    
    /**
     * 判定角色是否拥有邀请码
     * @param role_id
     * @return
     * @throws Exception
     */
    public int getRoleHasCode(long role_id) throws Exception
    {
        return inviteDao.getInviteRoleNum(role_id);
    }
    
    /**
     * 查询个人邀请人员信息
     * @param role_id
     * @return
     */
    public List<InviteRole> getInviteRole(long role_id) throws Exception
    {
        return inviteDao.getInviteRoles(role_id);
    }
    
    /**
     * 绑定邀请码 
     * 0--邀请码不存在
     * 2--已经绑定过邀请码
     * 1--绑定成功
     * @return
     */
    public int bindCode(Invite invite) throws Exception
    {
        //检测邀请码是否存在
        if (roleDao.selectRolesByRoleId(invite.getInvite_code()) == null)
        	return 0;
        
        //检测该角色是否已经绑定过
        if (inviteDao.getIsBind(invite.getRole_id()) >= 1)
        	return 2;
        

        //绑定邀请人与被邀请人
        inviteDao.bindInviteCode(new InviteRole(invite.getInvite_code(),invite.getRole_id()));
        
        //发送新手礼包包裹
        return rolePropsDao.callAddRoleProps(new RoleProps(invite.getRole_id(), RolePropsInfoService.PROPS_ID_INVITEED, 1));
    }
    
    /**
     * 领取奖励
     * 0--奖励已被领取
     * 2--未达到领取资格
     * 1--领取成功
     * @return
     */
    public HashMap<String,Object> doGetInviteReward(long role_id, int invite_info_id) throws Exception
    {
    	HashMap<String,Object> resMap = new HashMap<String,Object>();
    	
    	//查看领取状态
    	List<RoleInvite> myInvites = inviteDao.getInviteRewardInfoMySelf(new RoleInvite(role_id,invite_info_id));
    	if(myInvites == null || myInvites.size() <= 0 ){
    		resMap.put(GET_RES_KEY, 2);
    		return resMap;
    	}
    	if(myInvites.get(0).getIs_get() == 1){
    		resMap.put(GET_RES_KEY, 0);
    		return resMap;
    	}
    	//修改领取状态
    	inviteDao.updateInviteStatus(new RoleInvite(role_id, invite_info_id));
    	
    	//查看奖励信息
    	List<RoleProps> rewards= inviteDao.getInviteRewardInfoById(invite_info_id);
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	for (RoleProps roleProps : rewards) {
    		roleProps.setRole_id(role_id);
    		//钻石单独处理
            if (roleProps.getProps_id() == RolePropsInfoService.PROPS_ID_DIAMOND)
            {
                map.put("role_id", role_id);
                map.put("operator", "+");
                map.put("diamon", roleProps.getProps_count());
                roleDao.sumPluRoleInfo(map);
                roleProps.setRole_props_id(-100l);
            }else{
            	//奖励物品进入包裹
        		int res = rolePropsDao.callAddRoleProps(roleProps);
        		if(res == -1)
        			throw new RuntimeException();
        		
        		roleProps.setRole_props_id((long)res);
            }
    		
		}
    	resMap.put(GET_RES_KEY, 1);
    	resMap.put(GET_PROPS_KEY, rewards);

        return resMap;
    }
    
    /**
     * 邀请人数达到领奖标准判定
     * @throws Exception
     */
    public void canGetRewardByRoleNum(long role_id) throws Exception
    {
        //获取个人邀请信息
        Invite invite = inviteDao.getInvite(role_id);
        //获取奖励详细信息
        String reward = invite.getReward();
        //获取受邀请人数量
        List<InviteRole> inviteRoles = inviteDao.getInviteRoles(role_id);
        int count = inviteRoles.size();
        JSONArray rewardArr = JSONArray.fromObject(reward);
        JSONObject o = null;
        if (count >= 10)
        {
            o = rewardArr.getJSONObject(4);
        }
        else if (count >= 7)
        {
            o = rewardArr.getJSONObject(3);
        }
        else if (count >= 5)
        {
            o = rewardArr.getJSONObject(2);
        }
        else if (count >= 3)
        {
            o = rewardArr.getJSONObject(1);
        }
        else if (count >= 1)
        {
            o = rewardArr.getJSONObject(0);
        }
        
        if (o != null && o.getInt("reward_type") == -1)
        {
            o.put("reward_type", 0);
        }
        
        //入库
        invite.setReward(rewardArr.toString());
        inviteDao.updateCanGetReward(invite);
    }
}
    
//    /**
//     * 根据被邀请人等级达到领奖标准判定
//     * @param role_id
//     * @throws Exception
//     */
//    public void canGetRewardByRoleLev(long role_id, int lev) throws Exception
//    {
//        //获取邀请人的id
//        long invite_id = inviteDao.getInviteId(role_id);
//        
//        if (lev == 30)
//        {
//            levReward(invite_id, 30, 5);
//        }
//        else if (lev == 50)
//        {
//            levReward(invite_id, 50, 8);
//        }
//    }
//    
//    /**
//     * 根据角色邀请人等级人数更新奖励领取资格
//     * @param role_id
//     * @param lev
//     * @throws Exception
//     */
//    private void levReward(long role_id, int lev, int index) throws Exception
//    {
//        //获取个人邀请信息
//        Invite invite = inviteDao.getInvite(role_id);
//        //获取奖励详细信息
//        String reward = invite.getReward();
//        //达到等级的数量
//        int count = inviteDao.getOverLevCount(role_id, lev);
//        JSONArray rewardArr = JSONArray.fromObject(reward);
//        JSONObject o = null;
//        if (count >= 3)
//        {
//            o = rewardArr.getJSONObject(index + 2);
//        }
//        else if (count >= 2)
//        {
//            o = rewardArr.getJSONObject(index + 1);
//        }
//        else if(count >= 1)
//        {
//            o = rewardArr.getJSONObject(index);
//        }
//
//        if (o.getInt("reward_type") == -1)
//        {
//            o.put("reward_type", 0);
//        }
//        //入库
//        invite.setInvite_code(rewardArr.toString());
//        inviteDao.updateCanGetReward(invite);
//    }
//}
