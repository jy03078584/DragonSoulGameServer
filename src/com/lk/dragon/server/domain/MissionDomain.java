 /** 
 *
 * @Title: MissionDomain.java 
 * @Package com.lk.dragon.server.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-6-27 下午1:35:54 
 * @version V1.0   
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


/**  
 * @Description:Mission交互实体
 */
public class MissionDomain {
	private long role_id;
	private int misssion_id;
	private int exp;
	private int addRate;//任务完成数
	private int condition_id;//任务条件ID
	
	private int role_lev;//角色等级
	
	/** 连接 **/
    private ChannelHandlerContext ctx;
    private int type;
    /**消耗钻石**/
    private int diamonNum;
    
    
    
    
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getRole_lev() {
		return role_lev;
	}
	public void setRole_lev(int role_lev) {
		this.role_lev = role_lev;
	}
	public int getDiamonNum() {
		return diamonNum;
	}
	public void setDiamonNum(int diamonNum) {
		this.diamonNum = diamonNum;
	}
	public int getCondition_id() {
		return condition_id;
	}
	public void setCondition_id(int condition_id) {
		this.condition_id = condition_id;
	}
	public int getAddRate() {
		return addRate;
	}
	public void setAddRate(int addRate) {
		this.addRate = addRate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public int getMisssion_id() {
		return misssion_id;
	}
	public void setMisssion_id(int misssion_id) {
		this.misssion_id = misssion_id;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
    
    
}
