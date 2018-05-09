/**
 *
 *
 * 文件名称： BattleDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-28 上午10:15:54
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class BattleDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 角色id **/
    private long roleId;
    /** 城镇id **/
    private long cityId;
    /** 战斗请求模块类型 **/
    private int type;
    
    /**战争模式**/
    private int war_type;
    /**目标x**/
    private int tag_x;
    /**目标Y**/
    private int tag_y;
    private String tag_name;//目标名
    private int use_time;//到达目标消耗时间
    private long tag_role_id;//目标ID
    private int from_x;//队列出发X坐标
    private int from_y;//队列出发Y坐标
    private String herosId;//队列所辖英雄ID 以','分割
    private int use_food;//消耗粮食
    
    private int creeps_type;//野外狩猎类型 1:隐形地形  2随机地形
    private int distance;//目标距离
    private int sum_command;//部队总统帅值
    
    //出征队列ID
    private long war_team_id;
    
    
    
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public long getWar_team_id() {
		return war_team_id;
	}
	public void setWar_team_id(long war_team_id) {
		this.war_team_id = war_team_id;
	}
	public int getSum_command() {
		return sum_command;
	}
	public void setSum_command(int sum_command) {
		this.sum_command = sum_command;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getCreeps_type() {
		return creeps_type;
	}
	public void setCreeps_type(int creeps_type) {
		this.creeps_type = creeps_type;
	}
	public int getUse_food() {
		return use_food;
	}
	public void setUse_food(int use_food) {
		this.use_food = use_food;
	}
	public String getHerosId() {
		return herosId;
	}
	public void setHerosId(String herosId) {
		this.herosId = herosId;
	}
	public int getFrom_x() {
		return from_x;
	}
	public void setFrom_x(int from_x) {
		this.from_x = from_x;
	}
	public int getFrom_y() {
		return from_y;
	}
	public void setFrom_y(int from_y) {
		this.from_y = from_y;
	}
	public long getTag_role_id() {
		return tag_role_id;
	}
	public void setTag_role_id(long tag_role_id) {
		this.tag_role_id = tag_role_id;
	}
	public int getWar_type() {
		return war_type;
	}
	public void setWar_type(int war_type) {
		this.war_type = war_type;
	}
	public int getTag_x() {
		return tag_x;
	}
	public void setTag_x(int tag_x) {
		this.tag_x = tag_x;
	}
	public int getTag_y() {
		return tag_y;
	}
	public void setTag_y(int tag_y) {
		this.tag_y = tag_y;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public int getUse_time() {
		return use_time;
	}
	public void setUse_time(int use_time) {
		this.use_time = use_time;
	}
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public long getCityId()
    {
        return cityId;
    }
    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    
    
}
