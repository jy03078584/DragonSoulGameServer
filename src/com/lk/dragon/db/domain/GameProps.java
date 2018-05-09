 /** 
 *
 * @Title: GameProps.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: 道具表domain映射 
 * @author XiangMZh   
 * @date 2014-9-16 上午10:57:20 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:道具表domain映射
 */
public class GameProps {
	private Integer props_id;//	道具ID
	private String props_name;// 道具名称
	private String props_icon;// 道具图标路径
	private String props_comment;//道具效果简介
	private Integer props_type;//道具类型 
	private Integer props_type_show;//界面展示类型

	/**
	 * 无参构造
	 */
	public GameProps(){}
	




	public Integer getProps_type_show() {
		return props_type_show;
	}




	public void setProps_type_show(Integer props_type_show) {
		this.props_type_show = props_type_show;
	}




	public String getProps_comment() {
		return props_comment;
	}

	public void setProps_comment(String props_comment) {
		this.props_comment = props_comment;
	}

	public Integer getProps_id() {
		return props_id;
	}
	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}
	public String getProps_name() {
		return props_name;
	}
	public void setProps_name(String props_name) {
		this.props_name = props_name;
	}
	public String getProps_icon() {
		return props_icon;
	}
	public void setProps_icon(String props_icon) {
		this.props_icon = props_icon;
	}
	public Integer getProps_type() {
		return props_type;
	}
	public void setProps_type(Integer props_type) {
		this.props_type = props_type;
	}

	
	
}
