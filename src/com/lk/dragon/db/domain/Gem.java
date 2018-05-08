 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Gem.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-16 上午10:15:43 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:宝石表映射
 */
public class Gem {

	private Integer gem_id;//宝石id
	private Integer props_id;//道具id
	private Integer gem_equaitly;//宝石等级
	private Integer gem_buff;//宝石增益数值
	private Integer buff_type;//影响属性类型
	
	
	private String gem_name;//宝石名字
	private String icon;//宝石图标
	private Long role_props_id;//宝石所在包裹ID
	public Gem() {
		super();
	}
	
	
	
	
	public Long getRole_props_id() {
		return role_props_id;
	}




	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}




	public String getGem_name() {
		return gem_name;
	}


	public void setGem_name(String gem_name) {
		this.gem_name = gem_name;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Gem(int gem_equaitly, int buff_type) {
		this.gem_equaitly = gem_equaitly;
		this.buff_type = buff_type;
	}
	public Integer getGem_id() {
		return gem_id;
	}
	public void setGem_id(Integer gem_id) {
		this.gem_id = gem_id;
	}
	public Integer getProps_id() {
		return props_id;
	}
	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}
	public Integer getGem_equaitly() {
		return gem_equaitly;
	}
	public void setGem_equaitly(Integer gem_equaitly) {
		this.gem_equaitly = gem_equaitly;
	}
	public Integer getGem_buff() {
		return gem_buff;
	}
	public void setGem_buff(Integer gem_buff) {
		this.gem_buff = gem_buff;
	}
	public Integer getBuff_type() {
		return buff_type;
	}
	public void setBuff_type(Integer buff_type) {
		this.buff_type = buff_type;
	}

	
	
}
