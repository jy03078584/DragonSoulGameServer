 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: BuildCreate.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-14 下午6:29:14 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:建筑升级需要资源
 */
public class BuildCreate {
	
	private Long record_id;//记录id
	private Integer type;//建筑类型
	private Integer lev;//建造等级
	private Integer food;//消耗的食物
	private Integer wood;//消耗的木材
	private Integer stone;//消耗的石料
	private Integer gold;//消耗的黄金
	private Integer eat;//增加的人口
	private Integer create_t;//建造时长
	
	private Integer sumEat;//到当前等级 改建筑共提供人中数
	
	public BuildCreate() {}
	
	
	
	
	public Integer getSumEat() {
		return sumEat;
	}




	public void setSumEat(Integer sumEat) {
		this.sumEat = sumEat;
	}




	public Long getRecord_id() {
		return record_id;
	}
	public void setRecord_id(Long record_id) {
		this.record_id = record_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLev() {
		return lev;
	}
	public void setLev(Integer lev) {
		this.lev = lev;
	}
	public Integer getFood() {
		return food;
	}
	public void setFood(Integer food) {
		this.food = food;
	}
	public Integer getWood() {
		return wood;
	}
	public void setWood(Integer wood) {
		this.wood = wood;
	}
	public Integer getStone() {
		return stone;
	}
	public void setStone(Integer stone) {
		this.stone = stone;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public Integer getEat() {
		return eat;
	}
	public void setEat(Integer eat) {
		this.eat = eat;
	}
	public Integer getCreate_t() {
		return create_t;
	}
	public void setCreate_t(Integer create_t) {
		this.create_t = create_t;
	}

	
	

}
