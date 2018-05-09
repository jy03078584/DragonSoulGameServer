/** 
 *
 * @Title: BattleItem.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-7 上午10:20:27 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**
 * @Description:战斗元素
 */
public class BattleItem implements Cloneable {

	private Long key_id;// ID
	private String name;// 元素名
	private Integer type;// 元素类型 1:英雄 2：士兵 3城防 4预备部队 5野外
	private double physic_attack;// 物理攻击
	private Integer physic_defence;// 物理防御
	private double magic_attack;// 魔法攻击
	private Integer magic_defence;// 魔法防御
	private Integer hp;// 生命值
	private Integer mp;// 魔法值
	private Integer speed;// 战斗速度
	private Integer item_count;// 元素数量
	private String attact_defen_flag;// 攻守标志 1：攻方：D 2：守方：A

	private Integer itemSumHp;// 该元素总生命值
	private Integer attack_type;// 此次攻击方式
	private Integer item_count_now;// 该回合后剩余数量
	private String leaderName;// 部队将领名
	private Integer exp;// 部队经验值
	private String icon;// 元素图标

	private Integer max_hp;//英雄最大生命值
	
	private int armPreLastCount;//预备部队剩余数量
	private int isfource;//是否是增援部队
	private String fourceName;//增援角色名
	
	public BattleItem() {
		super();
	}

	

	public BattleItem clone() {
		BattleItem battleItem = null;
		try {
			battleItem = (BattleItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return battleItem;
	}

	
	
	
	public Integer getMax_hp() {
		return max_hp;
	}

	public void setMax_hp(Integer max_hp) {
		this.max_hp = max_hp;
	}

	@Override
	public boolean equals(Object obj) {
		BattleItem battleItem = (BattleItem) obj;
		if(this.type == battleItem.getType() && this.key_id == battleItem.getKey_id())
			return true;
		if (this == obj)
			return true;
		if (null == obj)
			return false;
		if (getClass() != obj.getClass()) 
			return false;
		
		return false;

	}

	
	
	public int getArmPreLastCount() {
		return armPreLastCount;
	}

	public void setArmPreLastCount(int armPreLastCount) {
		this.armPreLastCount = armPreLastCount;
	}

	public int getIsfource() {
		return isfource;
	}

	public void setIsfource(int isfource) {
		this.isfource = isfource;
	}

	public String getFourceName() {
		return fourceName;
	}

	public void setFourceName(String fourceName) {
		this.fourceName = fourceName;
	}
	public BattleItem(Long key_id, String name, Integer type,
			double physic_attack, Integer physic_defence,
			double magic_attack, Integer magic_defence, Integer hp,
			Integer mp, Integer speed, Integer item_count,
			String attact_defen_flag, Integer itemSumHp, Integer item_count_now) {
		super();
		this.key_id = key_id;
		this.name = name;
		this.type = type;
		this.physic_attack = physic_attack;
		this.physic_defence = physic_defence;
		this.magic_attack = magic_attack;
		this.magic_defence = magic_defence;
		this.hp = hp;
		this.mp = mp;
		this.speed = speed;
		this.item_count = item_count;
		this.attact_defen_flag = attact_defen_flag;
		this.itemSumHp = itemSumHp;
		this.item_count_now = item_count_now;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public BattleItem(Long key_id, Integer speed) {
		super();
		this.key_id = key_id;
		this.speed = speed;
	}

	public Integer getItem_count_now() {
		return item_count_now;
	}

	public void setItem_count_now(Integer item_count_now) {
		this.item_count_now = item_count_now;
	}

	public Integer getItemSumHp() {
		return itemSumHp;
	}

	public void setItemSumHp(Integer itemSumHp) {
		this.itemSumHp = itemSumHp;
	}

	public Integer getAttack_type() {
		return attack_type;
	}

	public void setAttack_type(Integer attack_type) {
		this.attack_type = attack_type;
	}

	public String getAttact_defen_flag() {
		return attact_defen_flag;
	}

	public void setAttact_defen_flag(String attact_defen_flag) {
		this.attact_defen_flag = attact_defen_flag;
	}

	public Integer getItem_count() {
		return item_count;
	}

	public void setItem_count(Integer item_count) {
		this.item_count = item_count;
	}

	public Long getKey_id() {
		return key_id;
	}

	public void setKey_id(Long key_id) {
		this.key_id = key_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getPhysic_attack() {
		return physic_attack;
	}

	public void setPhysic_attack(double physic_attack) {
		this.physic_attack = physic_attack;
	}

	public Integer getPhysic_defence() {
		return physic_defence;
	}

	public void setPhysic_defence(Integer physic_defence) {
		this.physic_defence = physic_defence;
	}

	public double getMagic_attack() {
		return magic_attack;
	}

	public void setMagic_attack(double magic_attack) {
		this.magic_attack = magic_attack;
	}

	public Integer getMagic_defence() {
		return magic_defence;
	}

	public void setMagic_defence(Integer magic_defence) {
		this.magic_defence = magic_defence;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getMp() {
		return mp;
	}

	public void setMp(Integer mp) {
		this.mp = mp;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

}
