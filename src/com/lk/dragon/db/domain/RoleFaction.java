 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: RoleFaction.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-24 下午4:31:17 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:角色-帮会表映射
 */
public class RoleFaction {

	private Long role_faction_id;//	n	number(12)	y			关联id
	private Long role_id;//	n	number(12)	y			角色id
	private Long faction_id;//	n	number(12)	y			盟会id
	private Long faction_position;//	n	number(1)	y	-1		盟会职位
	private Integer contribution;//	n	number(5)	y	1		帮会贡献度
	private String alreadyGetReward;//已领取奖励
	private Integer role_race;   //角色种族
	private Integer role_lev;    //角色等级
	private Integer role_status; //角色是否在线
	
	
	
	private String role_name;//角色名
	private String role_icon;
	private String faction_name;//帮会名称
	private String icon;//帮会图标
	private String faction_lev;//帮会等级
	private String position_name;//职位称号
	private Integer right_id;//权利等级
	
	
	
	
	
	
	public String getAlreadyGetReward() {
		return alreadyGetReward;
	}
	public void setAlreadyGetReward(String alreadyGetReward) {
		this.alreadyGetReward = alreadyGetReward;
	}
	public String getRole_icon() {
		return role_icon;
	}
	public void setRole_icon(String role_icon) {
		this.role_icon = role_icon;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Long getRole_faction_id() {
		return role_faction_id;
	}
	public void setRole_faction_id(Long role_faction_id) {
		this.role_faction_id = role_faction_id;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Long getFaction_id() {
		return faction_id;
	}
	public void setFaction_id(Long faction_id) {
		this.faction_id = faction_id;
	}
	public Long getFaction_position() {
		return faction_position;
	}
	public void setFaction_position(Long faction_position) {
		this.faction_position = faction_position;
	}
	public Integer getContribution() {
		return contribution;
	}
	public void setContribution(Integer contribution) {
		this.contribution = contribution;
	}
	public String getFaction_name() {
		return faction_name;
	}
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFaction_lev() {
		return faction_lev;
	}
	public void setFaction_lev(String faction_lev) {
		this.faction_lev = faction_lev;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public Integer getRight_id() {
		return right_id;
	}
	public void setRight_id(Integer right_id) {
		this.right_id = right_id;
	}
	public RoleFaction() {
		super();
	}
    public Integer getRole_race()
    {
        return role_race;
    }
    public void setRole_race(Integer role_race)
    {
        this.role_race = role_race;
    }
    public Integer getRole_lev()
    {
        return role_lev;
    }
    public void setRole_lev(Integer role_lev)
    {
        this.role_lev = role_lev;
    }
    public Integer getRole_status()
    {
        return role_status;
    }
    public void setRole_status(Integer role_status)
    {
        this.role_status = role_status;
    }
	
	
	

}
