 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RelationDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 下午2:57:44 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lk.dragon.db.dao.IRelationDao;
import com.lk.dragon.db.domain.Relation;
import com.lk.dragon.db.domain.Role;

/**  
 * @Description:
 */
@Repository("relationDao")
public class RelationDaoImpl extends BaseSqlMapDao implements IRelationDao {


	@Override
	public long createRelationShip(Relation relation) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("relationMap.createRelationShip", relation);
	}


	@Override
	public Relation checkRelationShip(Relation relation) throws Exception {
		return (Relation) getSqlMapClientTemplate().queryForObject("relationMap.checkRelationShip", relation);
	}

	@Override
	public int disarmFriendRelationShip(Relation relation) throws Exception {
		return getSqlMapClientTemplate().delete("relationMap.disarmFriendRelationShip", relation);
	}

	@Override
	public int disarmEnemyRelationShip(Relation relation) throws Exception {
		return getSqlMapClientTemplate().delete("relationMap.disarmEnemyRelationShip", relation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRelationRoles(long role_id) throws Exception {
		return getSqlMapClientTemplate().queryForList("relationMap.getRelationRoles", role_id);
	}

    @Override
    public Role getOneRelationRole(long relation_id) throws Exception {
        return (Role) getSqlMapClientTemplate().queryForObject("relationMap.getOneRelationRole", relation_id);
    }
    

	@Override
	public int checkIsEnemy(Map<String,Long> conditionMap) throws Exception{
		return (Integer) getSqlMapClientTemplate().queryForObject("relationMap.checkIsEnemy", conditionMap);
	}

    @Override
    public Role selectRelationRoleInfo(long role_id) throws Exception
    {
        return (Role) getSqlMapClientTemplate().queryForObject("relationMap.selectRelationRoleInfo", role_id);
    }

}
