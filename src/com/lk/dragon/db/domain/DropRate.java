 /** 
 *
 * @Title: DropRate.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:NPC掉落道具domain
 */
public class DropRate {
	private Integer npc_id;//NPC_ID
	private Integer random_beg;//暴率起始值	
	private Integer random_end;//暴率结束值
	private Integer random_max;//暴率总数值
	private Integer props_id;//掉落物品ID
	private Integer rate;//本次随机数
	
	
	public DropRate(Integer npc_id,Integer rate) {
		this.npc_id = npc_id;
		this.rate = rate;
	}
	
	public DropRate() {
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getNpc_id() {
		return npc_id;
	}
	public void setNpc_id(Integer npc_id) {
		this.npc_id = npc_id;
	}
	public Integer getRandom_beg() {
		return random_beg;
	}
	public void setRandom_beg(Integer random_beg) {
		this.random_beg = random_beg;
	}
	public Integer getRandom_end() {
		return random_end;
	}
	public void setRandom_end(Integer random_end) {
		this.random_end = random_end;
	}
	public Integer getRandom_max() {
		return random_max;
	}
	public void setRandom_max(Integer random_max) {
		this.random_max = random_max;
	}
	public Integer getProps_id() {
		return props_id;
	}
	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}
	
	
	

}
