 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Friend.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-19 下午4:22:34 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:好友关联表映射
 */
public class Relation {
	private Long relation_id;//关联id
	private Long role_left_id;//好友甲方
	private Long role_right_id;//好友乙方
	private Integer relation_type;//关系类型 1：好友 2：仇人
	
	public Relation(){}
	
	
	
	
	public Relation(Long role_left_id, Long role_right_id) {
		super();
		this.role_left_id = role_left_id;
		this.role_right_id = role_right_id;
	}




	public Relation(Long role_left_id, Long role_right_id, Integer relation_type) {
		super();
		this.role_left_id = role_left_id;
		this.role_right_id = role_right_id;
		this.relation_type = relation_type;
	}


	public Integer getRelation_type() {
		return relation_type;
	}

	public void setRelation_type(Integer relation_type) {
		this.relation_type = relation_type;
	}

	public Long getRelation_id() {
		return relation_id;
	}

	public void setRelation_id(Long relation_id) {
		this.relation_id = relation_id;
	}





	public Long getRole_left_id() {
		return role_left_id;
	}


	public void setRole_left_id(Long role_left_id) {
		this.role_left_id = role_left_id;
	}


	public Long getRole_right_id() {
		return role_right_id;
	}


	public void setRole_right_id(Long role_right_id) {
		this.role_right_id = role_right_id;
	}
	
	
}
