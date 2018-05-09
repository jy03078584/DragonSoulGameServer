 /** 
 *
 * @Title: Buff.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-2 下午12:09:19 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:游戏内BUFF
 */
public class Buff {
	
	//-------BUFF_TAB 
	private Integer buff_id;//	n	number(12)	y			buffid
	private Integer buff_type;//	n	number(1)	y			buff类型0：作用于角色，1：作用于城邦
	private String buff_name;//	n	varchar2(255)	y			名称，就用对应的道具名称
	private String buff_desc;//	n	varchar2(255)	y			描述，就用对应的道具描述
	private Integer buff_time;//	n	number(3)	y	1		作用时间，单位（天）
	private String buff_icon;
	//-------BUFF_USED_TAB
	//buff_id	n	number(12)	y			buffid
	private long target_id;//	n	number(12)	y			作用对象目标id，角色就用角色id，城邦就用城邦id
	private String last_time;//	n	varchar2(20)	y			到期时间
	
	//BUFF剩余时间
	private int lastSecond;
	
	
	public Buff() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Buff(Integer buff_id, long target_id) {
		super();
		this.buff_id = buff_id;
		this.target_id = target_id;
	}




	public String getBuff_icon() {
		return buff_icon;
	}




	public void setBuff_icon(String buff_icon) {
		this.buff_icon = buff_icon;
	}




	public Integer getBuff_type() {
		return buff_type;
	}




	public void setBuff_type(Integer buff_type) {
		this.buff_type = buff_type;
	}




	public int getLastSecond() {
		return lastSecond;
	}


	public void setLastSecond(int lastSecond) {
		this.lastSecond = lastSecond;
	}


	public Integer getBuff_id() {
		return buff_id;
	}
	public void setBuff_id(Integer buff_id) {
		this.buff_id = buff_id;
	}
	public String getBuff_name() {
		return buff_name;
	}
	public void setBuff_name(String buff_name) {
		this.buff_name = buff_name;
	}
	public String getBuff_desc() {
		return buff_desc;
	}
	public void setBuff_desc(String buff_desc) {
		this.buff_desc = buff_desc;
	}
	public Integer getBuff_time() {
		return buff_time;
	}
	public void setBuff_time(Integer buff_time) {
		this.buff_time = buff_time;
	}
	public long getTarget_id() {
		return target_id;
	}
	public void setTarget_id(long target_id) {
		this.target_id = target_id;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}

	
	
}
