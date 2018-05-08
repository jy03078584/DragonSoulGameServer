 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IFactionDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-24 下午3:40:20 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Faction;
import com.lk.dragon.db.domain.RoleFaction;

/**  
 * @Description:盟会接口
 */
public interface IFactionDao {
	
	/**
	 * 创建帮会
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public long createFaction(Faction faction)throws Exception;
	
	
	/**
	 * 获取Sequence
	 */
	public long getFactionsApplyKeyId();
	/**
	 * 玩家入会
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public long addRoleFaction(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询帮会人员列表
	 * @param faction_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleFaction> getFactionRoles(String condition)throws Exception;
	
	
	/**
	 * 查询帮会列表
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Faction> getFactionsByCondition(String condition)throws Exception;
	
	/**
	 * 申请加入帮会
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object applyUnionFaction(Map<String,Long> map)throws Exception;
	
	/**
	 * 查询帮会申请列表信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Faction> selectApplyFactionInfo(Map<String,Long> map)throws Exception;
	
	/**
	 * 删除申请表信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int deleteApplyFactionInfo(String condition)throws Exception;
	
	/**
	 * 查询个人的帮会权限
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public Faction selectFactionRight(long role_id)throws Exception;
	
	/**
	 * 帮会-帮众 解除联系
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleFaction(long role_id)throws Exception;
	
	
	/**
	 * 修改帮会基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateFactionInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 修改帮会个人信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateRoleFactionInfo(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 新增公会职位
	 */
	public long addGuildPosition(Map<String,Object> map) throws Exception;
	
	/**
	 * 编辑公会职位信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int editGuildPosition(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除公会职位
	 * @param position_id
	 * @return
	 * @throws Exception
	 */
	public int deleGuildPosition(long position_id) throws Exception;
	
	/**
	 * 查询角色帮会权限
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int selectRoleFactionRight(long role_id)throws Exception;
	
	/**
	 * 删除帮会-职位数据
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int deleteFactionPosition(long role_id)throws Exception;
	
	/**
	 * 修改管理称号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateFactionPosition(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增帮会事件记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public long addFactionLog(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 查询帮会记录
	 * @param faction_id
	 * @return
	 * @throws Exception
	 */
	public List<Faction> selectFactionLog(long faction_id)throws Exception;
	
	/**
	 * 解散帮会
	 * @param faction_id
	 * @return
	 * @throws Exception
	 */
	public int deleteFaction(long faction_id)throws Exception;

	
	
	/**
	 * 判断是否是同帮会成员
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int checkIsSameFaction(String condition)throws Exception;

	
	/**
	 * 获取当前职位使用情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Faction getPositionUseInfo(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询当前公会职位列表信息
	 * @param faction_id
	 * @return
	 * @throws Exception
	 */
	public List<Faction> getPositionList(long faction_id)throws Exception;
	
	/**
	 * 检查角色是否已有帮会
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public int checkAlreadyFaction(long role_id)throws Exception;

}
