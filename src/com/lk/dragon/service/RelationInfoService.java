/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RelationInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-22 下午3:27:17 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IRelationDao;
import com.lk.dragon.db.domain.Relation;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.util.Constants;

/**
 * @Description:角色间社交关系业务
 */
@Service
public class RelationInfoService {

	@Autowired
	private  IRelationDao relationDao;

	public RelationInfoService(){
	}
	
	/**
	 * 获取指定社交列表 好友/仇人列表
	 * 
	 * @param relation
	 *            获取好友列表:relation_type = 1 获取仇人列表:relation_type = 2
	 * @return
	 */
	public List<Role> getRelationRolesList(long role_id) {

		List<Role> roles = null;
		try {
			roles = relationDao.getRelationRoles(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	/**
	 * 检查当前与对方的社交关系 0：尚未建立关系 1：好友 2：仇人 -1:查询错误
	 * 
	 * @param relation
	 * @return
	 */
	private int checkRelationShip(Relation relation) {
		int relationType = 0;
		Relation relationTemp;
		try {
			relationTemp = relationDao.checkRelationShip(relation);
			if (relationTemp != null) {
				relationType = relationTemp.getRelation_type();
			} else {
				relationType = 0;
			}
		} catch (Exception e) {
			relationType = -1;
			e.printStackTrace();
		}

		return relationType;
	}

	/**
	 * 判断自己是否在对方仇人列表
	 * @param relation
	 * @return
	 */
	private int checkIsEnemy(Relation relation) {
		int isEnemy = -1;
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put("target_id", relation.getRole_right_id());
		map.put("my_id", relation.getRole_left_id());
		try {
			isEnemy =  relationDao.checkIsEnemy(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isEnemy;
	}

	/**
	 * 判断双方是否具有成为好友条件
	 * 
	 * @param relation
	 * @return 1:可以成为好友 2:对方已在好友列表 3：对方已在仇人列表  4:自己已在对方仇人列表
	 */
	public HashMap<String, Long> canCreateFriend(Relation relation) {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		if (checkIsEnemy(relation) > 0) {
			resultMap.put(Constants.RESULT_KEY,Constants.SOURCE_ALEARDY_INENEMY);
			return resultMap;
		}
		int type = checkRelationShip(relation);
		switch (type) {
		case 0:
			resultMap.put(Constants.RESULT_KEY, Constants.CAN_BE_FRIEND);
			break;
		case 1:
			resultMap.put(Constants.RESULT_KEY, Constants.TARGET_ALEARDY_FRIEND);
			break;
		case 2:
			resultMap.put(Constants.RESULT_KEY, Constants.TARGET_ALEARDY_ENEMY);
			break;
		default:
			break;
		}
		return resultMap;
	}

	
	
	/**
	 * 新增一对好友 双向
	 * 
	 * @param relation
	 *            包含roleLeftId和roleRightId的Relation对象
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, Long> createFriend(Relation relation) throws Exception {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		// 定义关系：好友
		relation.setRelation_type(1);
		try{

			Relation relationTemp = new Relation(relation.getRole_right_id(),relation.getRole_left_id(), relation.getRelation_type());
			long leftId = relationDao.createRelationShip(relation);
			long rightId = relationDao.createRelationShip(relationTemp);


			if (leftId > 0 && rightId > 0) {
				resultMap.put(Constants.RESULT_KEY,Constants.CREATE_RELATION_SUCCESS);
				resultMap.put(Constants.ID_KEY, leftId);
			}else{
				resultMap.put(Constants.RESULT_KEY,Constants.CREATE_RELATION_FAIL);
				resultMap.put(Constants.ID_KEY, -1l);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();//抛出运行时异常 事务回滚
		}
		return resultMap;
	}

	/**
	 * 新增仇人 单向 包含roleLeftId和roleRightId的Relation对象
	 * 
	 * @param relation
	 * @return
	 */
	public HashMap<String, Long> createEnemy(Relation relation) {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		int type = checkRelationShip(relation);
		System.out.println(type);
		switch (type) {
		case 0:
			// 定义关系：仇人
			relation.setRelation_type(2);
			try {
				long enemyId = relationDao.createRelationShip(relation);
				if (enemyId > 0) {
					resultMap.put(Constants.RESULT_KEY,Constants.CREATE_RELATION_SUCCESS);
					resultMap.put(Constants.ID_KEY, enemyId);
				}

			} catch (Exception e) {
				resultMap.put(Constants.RESULT_KEY,Constants.CREATE_RELATION_FAIL);
				resultMap.put(Constants.ID_KEY, -1l);
				e.printStackTrace();
			}
			break;
		case -1:
			resultMap.put(Constants.RESULT_KEY, Constants.CREATE_RELATION_FAIL);
			resultMap.put(Constants.ID_KEY, -1l);
			break;
		case 1:
			resultMap.put(Constants.RESULT_KEY, Constants.TARGET_ALEARDY_FRIEND);
			resultMap.put(Constants.ID_KEY, -1l);
			break;
		case 2:
			resultMap.put(Constants.RESULT_KEY, Constants.TARGET_ALEARDY_ENEMY);
			resultMap.put(Constants.ID_KEY, -1l);
			break;
		default:
			break;
		}

		return resultMap;
	}

	/**
	 * 解除好友关系 双向
	 * 
	 * @param relation
	 * @return
	 */
	public boolean disarmFriendRelationShip(Relation relation) {
		boolean flag = false;

		try {
			if (relationDao.disarmFriendRelationShip(relation) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除仇人 单向
	 * 
	 * @param relation
	 * @return
	 */
	public boolean disarmEnemyRelationShip(Relation relation) {
		boolean flag = false;
		try {
			if (relationDao.disarmEnemyRelationShip(relation) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据角色id查询角色的详细信息
	 * @param role_id
	 * @return
	 */
	public Role selectRelationRoleInfo(long role_id)
	{
	    Role role = null;
	    try 
	    {
            role = relationDao.selectRelationRoleInfo(role_id);
        }
	    catch (Exception e)
	    {
            e.printStackTrace();
        }
	    
        return role;
	}
	

    /**
     * 根据relation_id查询角色的详细信息
     * @param role_id
     * @return
     */
    public Role getRoleByRelationId(long relation_id)
    {
        Role role = null;
        try 
        {
            role = relationDao.getOneRelationRole(relation_id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return role;
    }
}
