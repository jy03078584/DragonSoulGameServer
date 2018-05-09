 /** 
 *
 * @Title: RoleDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: 游戏角色实现类
 * @author XiangMZh   
 * @date 2014-9-4 上午10:03:38 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.service.CacheService;

/**  
 * @Description:游戏角色实现类
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseSqlMapDao implements IRoleDao {


	public RoleDaoImpl(){};
	
	@Override
	public Role selectRoles(long user_id)throws Exception {
		Role role;

		Results results	 = CacheService.roleCache.createQuery().addCriteria(CacheService.roleCache.getSearchAttribute("user_id")
											  .eq(user_id))
											  .includeValues()
											  .execute();
		List<Result> roleInCache = results.all();
		
		try {
			if(roleInCache!=null && roleInCache.size() > 0){
				role = (Role) roleInCache.get(0).getValue();
			}else{
				role = (Role) getSqlMapClientTemplate().queryForObject("roleMap.selectRolesByUserId", user_id);
				CacheService.roleCache.put(new Element(role.getRole_id(), role));
			}
		} finally{
			results.discard();
		}
		return role;
	}

	@Override
	public int deleteRole(Role role)throws Exception {
		return getSqlMapClientTemplate().delete("roleMap.deleteRole", role);
	}

	@Override
	public int updateRoleInfo(Role role) throws Exception{
		return getSqlMapClientTemplate().update("roleMap.updateRoleInfo", role);
	}

	@Override
	public int updateRoleName(Role role)throws Exception {
		return getSqlMapClientTemplate().update("roleMap.updateRoleName", role);
	}


	@Override
	public long createRole(Role role)throws Exception {
		return (Long) getSqlMapClientTemplate().insert("roleMap.addRole", role);
	}

	@Override
	public Role selectRoleByRoleName(Role role) throws Exception {
		return (Role) getSqlMapClientTemplate().queryForObject("roleMap.selectRolesByRoleName", role);
	}

	@Override
	public int checkRolesCountByUserId(Role role) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("roleMap.checkRolesCountByUserId", role);
	}

	@Override
	public int updateRoleIcon(Role role) throws Exception {
		return getSqlMapClientTemplate().update("roleMap.changeRoleIcon", role);
	}

	@Override
	public List<Role> refreshRoleCache() throws Exception {
		return getSqlMapClientTemplate().queryForList("roleMap.refreshRoleCache");
	}

	@Override
	public int updateOnLineStatus(Role role) throws Exception {
		return getSqlMapClientTemplate().update("roleMap.updateOnLineStatus", role);
	}

	@Override
	public Object selectRoleIdByName(String role_name) throws Exception {
		return getSqlMapClientTemplate().queryForObject("roleMap.selectRoleIdByName", role_name);
	}

	@Override
	public Role selectRolesByRoleId(long role_id) throws Exception {

		Role role;
		Element roleInCache = CacheService.roleCache.get(role_id);
		//先查缓存是否保存该角色
		if(roleInCache == null){
			role = (Role) getSqlMapClientTemplate().queryForObject("roleMap.selectRolesByRoleId", role_id);
			if(role == null)
				return null;
			//存入缓存
			CacheService.roleCache.put(new Element(role.getRole_id(), role));
		}else{
			//从缓存中拿去数据
			role = (Role) roleInCache.getObjectValue();
		}
		return role;
	}

	@Override
	public int sumPluRoleInfo(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("roleMap.sumPluRoleInfo", map);
	}

	@Override
	public Role selectRoleOnLineStatus(long role_id) throws Exception {
		return (Role) getSqlMapClientTemplate().queryForObject("roleMap.selectRoleOnLineStatus", role_id);
	}
    @Override
    public int resetRoleStatus() throws Exception
    {
        return getSqlMapClientTemplate().update("roleMap.resetRoleOnlineStatus");
    }
    @Override
    public Role selectRoleChest(long role_id) throws Exception
    {
        return (Role) getSqlMapClientTemplate().queryForObject("roleMap.selectChest", role_id);
    }
    @Override
    public int updateRoleChest(Role role) throws Exception
    {
        return getSqlMapClientTemplate().update("roleMap.updateChest", role);
    }
	@Override
	public List<Role> initRoleCache() throws Exception {
		return getSqlMapClientTemplate().queryForList("roleMap.initRoleCache");
	}

	@Override
	public List<Role> getRoleUpExp() {
		return getSqlMapClientTemplate().queryForList("roleMap.getRoleUpExp");
	}


}
