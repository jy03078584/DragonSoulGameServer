 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Faction.java 
 * @Package com.lk.dragon.db.domain 
 * @Description:
 * @author XiangMZh   
 * @date 2014-10-24 下午3:35:29 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;


/**  
 * @Description:帮会表映射
 */
public class Faction {

//--------------------------Faction_TAB 帮会表	
	
	private Long faction_id;//	n	number(10)	y			帮会id
	private String faction_name;//	n	varchar2(255)	y			帮会名称
	private Integer faction_lev;//帮会等级
	private Integer member_counts;//帮会现已容纳人数
	private Integer max_member_counts;//	帮会当前等级容纳最大人数
	private Integer faction_score;//	帮会繁荣度
	private String faction_public;//帮会公告
	private String icon;//帮会图标
	
	
	
//--------------------------Faction_Apply_TAB 帮会申请表
	
	private long rela_id;//关联Id
	private long role_id;//申请人ID
	private String role_name; //角色名称
	private int role_race;//角色种族
	private int role_lev;//角色等级
	private String role_icon;//角色头像
	
//-------------------------Faction_position_tab 帮会-称谓表
	private Integer right_id;//权限ID
	private Long faction_position_id;//帮会-称号关联ID
	private String position_name;//职位称号

//------------------------Faction_log_tab 帮会-日志表
	private Long faction_log_id;// 关联id
	//private Long faction_id;//  n number(12)  y     帮会id
	private String log_info;//  n varchar2(1000)  y     日志信息
	private String log_time;//  n varchar2(255) y     操作时间
	
//------------------------Role_faction_tab 帮会人员表
	private Integer get_reward_flag;//已领取的帮会奖励

	private String leader_name;//帮主名
	

	private Integer position_use_count;
	
	
	
	
	public String getRole_icon() {
		return role_icon;
	}








	public void setRole_icon(String role_icon) {
		this.role_icon = role_icon;
	}








	public Integer getPosition_use_count()
    {
        return position_use_count;
    }








    public void setPosition_use_count(Integer position_use_count)
    {
        this.position_use_count = position_use_count;
    }








    public Faction() {
	}
	
	
	





	public String getLeader_name() {
		return leader_name;
	}








	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}








	public Integer getGet_reward_flag() {
		return get_reward_flag;
	}








	public void setGet_reward_flag(Integer get_reward_flag) {
		this.get_reward_flag = get_reward_flag;
	}








	public Long getFaction_log_id() {
		return faction_log_id;
	}





	public void setFaction_log_id(Long faction_log_id) {
		this.faction_log_id = faction_log_id;
	}







	public String getLog_info() {
		return log_info;
	}





	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}





	public String getLog_time() {
		return log_time;
	}





	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}





	public Integer getRight_id() {
		return right_id;
	}





	public void setRight_id(Integer right_id) {
		this.right_id = right_id;
	}





	public Long getFaction_position_id() {
		return faction_position_id;
	}





	public void setFaction_position_id(Long faction_position_id) {
		this.faction_position_id = faction_position_id;
	}





	public String getPosition_name() {
		return position_name;
	}





	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}





	public String getRole_name() {
		return role_name;
	}




	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}




	public Faction(String faction_name, String icon) {
		super();
		this.faction_name = faction_name;
		this.icon = icon;
	}



	
	
	public long getRela_id() {
		return rela_id;
	}



	public void setRela_id(long rela_id) {
		this.rela_id = rela_id;
	}



	public long getRole_id() {
		return role_id;
	}



	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public Long getFaction_id() {
		return faction_id;
	}
	public void setFaction_id(Long faction_id) {
		this.faction_id = faction_id;
	}
	public String getFaction_name() {
		return faction_name;
	}
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	public Integer getFaction_lev() {
		return faction_lev;
	}
	public void setFaction_lev(Integer faction_lev) {
		this.faction_lev = faction_lev;
	}
	public Integer getMember_counts() {
		return member_counts;
	}
	public void setMember_counts(Integer member_counts) {
		this.member_counts = member_counts;
	}
	public Integer getMax_member_counts() {
		return max_member_counts;
	}
	public void setMax_member_counts(Integer max_member_counts) {
		this.max_member_counts = max_member_counts;
	}
	public Integer getFaction_score() {
		return faction_score;
	}
	public void setFaction_score(Integer faction_score) {
		this.faction_score = faction_score;
	}
	public String getFaction_public() {
		return faction_public;
	}
	public void setFaction_public(String faction_public) {
		this.faction_public = faction_public;
	}








    public int getRole_race()
    {
        return role_race;
    }








    public void setRole_race(int role_race)
    {
        this.role_race = role_race;
    }








    public int getRole_lev()
    {
        return role_lev;
    }








    public void setRole_lev(int role_lev)
    {
        this.role_lev = role_lev;
    }
	
	
	

}
