 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IRelationDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 下午2:34:18 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Relation;
import com.lk.dragon.db.domain.Role;

/**  
 * @Description:角色间社交关系接口
 */
public interface IRelationDao {
	
	
	/**
	 * 建立一条双向关系
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public long createRelationShip(Relation relation) throws Exception;
	
	/**
	 * 解除双方好友关系
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public int disarmFriendRelationShip(Relation relation) throws Exception;
	
	/**
	 * 删除仇人
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public int disarmEnemyRelationShip(Relation relation) throws Exception;
	
	
	/**
	 * 查看两角色间关系
	 * @param relation
	 * @return
	 * @throws Exception
	 */
	public Relation checkRelationShip(Relation relation) throws Exception;
	
	
	/**
	 * 获取指定社交列表
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRelationRoles(long role_id)throws Exception;
	

    /**
     * 获取单个关系信息
     * @param role_id
     * @return
     * @throws Exception
     */
    public Role getOneRelationRole(long relation_id)throws Exception;
	
	/**
	 * 判断是否在对方仇人列表中
	 * @param conditionMap
	 * @return
	 */
	public int checkIsEnemy(Map<String,Long> conditionMap)throws Exception;
	
	/**
	 * 根据角色id查询角色的详细信息
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public Role selectRelationRoleInfo(long role_id)throws Exception;
}
