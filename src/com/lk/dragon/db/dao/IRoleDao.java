 /** 
 *
 * @Title: IRole.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-4 上午9:58:28 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Role;

/**  
 * @Description:游戏角色信息
 */
public interface IRoleDao {
	
	
	/**
	 * 查询游戏角色
	 * @param role
	 * @return
	 */
	public Role selectRoles(long user_id) throws Exception;
	
	public Role selectRoleByRoleName(Role role) throws Exception;
	/**
	 * 新建游戏角色
	 * @param role
	 * @return
	 */
	public long createRole(Role role)throws Exception;
	
	/**
	 * 删除游戏角色
	 * @param role
	 * @return 
	 */
	public int deleteRole(Role role)throws Exception;
	
	/**
	 * 更新游戏角色数据
	 * @param role
	 * @return
	 */
	public int updateRoleInfo(Role role)throws Exception;
	
	public List<Role> getRoleUpExp();
	
	/**
	 * 更改角色名
	 * @param role
	 * @return
	 */
	public int updateRoleName(Role role)throws Exception;
	
	/**
	 * 检测当前用户已创建角色数
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int checkRolesCountByUserId(Role role) throws Exception;
	
	/**
	 * 修改头像
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int updateRoleIcon(Role role) throws Exception;
	
	public int resetRoleStatus() throws Exception;
	
	/**
	 * 当前在线玩家 更新角色缓存
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public List<Role> refreshRoleCache() throws Exception;
	
	/**
	 * 初始化角色缓存
	 * @return
	 * @throws Exception
	 */
	public List<Role> initRoleCache()throws Exception;
	/**
	 * 更改角色在线状态
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int updateOnLineStatus(Role role)throws Exception;
	
	/**
	 * 根据角色名查询角色ID
	 * @param role_name
	 * @return
	 * @throws Exception
	 */
	public Object selectRoleIdByName(String role_name)throws Exception;
	
	/**
	 * 根据角色ID查询资源信息
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public Role selectRolesByRoleId(long role_id)throws Exception;
	
	
	/**
	 * 角色资源增减
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int sumPluRoleInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 获取角色登录状态相关信息
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public Role selectRoleOnLineStatus(long role_id)throws Exception;
	
	public Role selectRoleChest(long role_id)throws Exception;
	public int updateRoleChest(Role role)throws Exception;
	
}
