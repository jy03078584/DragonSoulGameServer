 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RoleProps.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: 用户-道具中间表映射
 * @author XiangMZh   
 * @date 2014-9-15 上午10:28:44 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;


/**  
 * @Description:用户-道具中间表映射
 */
public class RoleProps {

	private Long role_props_id;
	private Long role_id;	//角色ID
	private Integer props_id;//	道具ID
	private Integer props_count;//道具存有数量
	private Integer is_bind;//道具是否已绑定  0：否  1:是
	private Integer is_equiped;//是否已经装备
	
	private Integer equip_location;      //装备位置
	private String inc_property;    //影响属性
	private Integer quality;              //品质
	private Integer command_lev;            //最低装备等级
	
	private Integer props_type;//道具种类
	private Integer isExist;//数据库中有无数据
	private String props_name;// 道具名称
	private String props_icon;// 道具图标路径
	private String props_comment;//道具效果简介
	private Integer props_duration;//道具效果持续时间
	private String gems;//装备类道具镶嵌的宝石信息
	private Integer props_repurchase;//道具回收价格
	
	private Integer gem_equaitly;//宝石等级
	private Integer buff_value;//宝石BUFF数值
	private Integer buff_type;//宝石类型
	
	private String extra_info;//额外信息
	
	private int is_random;//礼包类型:是否是随机礼包
	private int rand_cnt;//随机礼包 随机次数
	
	
	public RoleProps(){}

	public int getIs_random() {
		return is_random;
	}








	public void setIs_random(int is_random) {
		this.is_random = is_random;
	}








	public int getRand_cnt() {
		return rand_cnt;
	}








	public void setRand_cnt(int rand_cnt) {
		this.rand_cnt = rand_cnt;
	}





	public String getExtra_info() {
		return extra_info;
	}






	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}






	public Integer getGem_equaitly() {
		return gem_equaitly;
	}






	public void setGem_equaitly(Integer gem_equaitly) {
		this.gem_equaitly = gem_equaitly;
	}






	public Integer getBuff_value() {
		return buff_value;
	}






	public void setBuff_value(Integer buff_value) {
		this.buff_value = buff_value;
	}






	public Integer getBuff_type() {
		return buff_type;
	}






	public void setBuff_type(Integer buff_type) {
		this.buff_type = buff_type;
	}






	public Integer getProps_repurchase() {
		return props_repurchase;
	}






	public void setProps_repurchase(Integer props_repurchase) {
		this.props_repurchase = props_repurchase;
	}






	public String getGems() {
		return gems;
	}






	public void setGems(String gems) {
		this.gems = gems;
	}






	public Integer getEquip_location() {
		return equip_location;
	}






	public void setEquip_location(Integer equip_location) {
		this.equip_location = equip_location;
	}








	public String getInc_property() {
		return inc_property;
	}






	public void setInc_property(String inc_property) {
		this.inc_property = inc_property;
	}






	public Integer getQuality() {
		return quality;
	}






	public void setQuality(Integer quality) {
		this.quality = quality;
	}






	public Integer getCommand_lev() {
		return command_lev;
	}






	public void setCommand_lev(Integer command_lev) {
		this.command_lev = command_lev;
	}






	public RoleProps(Long role_id, Integer props_id, Integer props_count) {
		super();
		this.role_id = role_id;
		this.props_id = props_id;
		this.props_count = props_count;
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

	public String getProps_comment() {
		return props_comment;
	}

	public void setProps_comment(String props_comment) {
		this.props_comment = props_comment;
	}

	public Integer getProps_duration() {
		return props_duration;
	}

	public void setProps_duration(Integer props_duration) {
		this.props_duration = props_duration;
	}


	public Integer getIs_equiped() {
		return is_equiped;
	}






	public void setIs_equiped(Integer is_equiped) {
		this.is_equiped = is_equiped;
	}






	public RoleProps(Long role_props_id, Integer props_count) {
		super();
		this.role_props_id = role_props_id;
		this.props_count = props_count;
	}

	public Integer getIs_bind() {
		return is_bind;
	}

	public void setIs_bind(Integer is_bind) {
		this.is_bind = is_bind;
	}


	public Integer getProps_type() {
		return props_type;
	}

	public void setProps_type(Integer props_type) {
		this.props_type = props_type;
	}
	public Integer getProps_count() {
		return props_count;
	}

	public void setProps_count(Integer props_count) {
		this.props_count = props_count;
	}

	public Integer getIsExist() {
		return isExist;
	}

	public void setIsExist(Integer isExist) {
		this.isExist = isExist;
	}

	public Long getRole_props_id() {
		return role_props_id;
	}
	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Integer getProps_id() {
		return props_id;
	}
	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}

	
}
