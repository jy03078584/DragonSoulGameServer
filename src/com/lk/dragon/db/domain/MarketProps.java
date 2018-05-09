 /** 
 *
 * @Title: MarketProps.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-19 下午3:44:39 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:商城道具表映射
 */
public class MarketProps {
	private Integer market_props_id;//	商城道具ID
	private Integer props_id;//道具id
	private Integer privce_diamon;//道具单价（点卷）
	private Integer privce_gold;//道具单价（金币）
	private Integer market_weight;//道具权重
	
	private String props_name;//道具名称
	private String props_icon;//道具图标路径
	private String props_desc;//道具描述
	private Integer buy_counts;//购买数量
	private Long buyer_id;//购买者id
	private Integer use_gold;//购买消耗金币量
	private Integer use_diamon;//购买消耗点卷量
	
	private Integer props_type;//道具类型
	
	public MarketProps(){}

	
	
	 
	public Integer getProps_type() {
		return props_type;
	}




	public void setProps_type(Integer props_type) {
		this.props_type = props_type;
	}




	public Integer getBuy_counts() {
		return buy_counts;
	}




	public void setBuy_counts(Integer buy_counts) {
		this.buy_counts = buy_counts;
	}




	public Long getBuyer_id() {
		return buyer_id;
	}




	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
	}




	public Integer getUse_gold() {
		return use_gold;
	}




	public void setUse_gold(Integer use_gold) {
		this.use_gold = use_gold;
	}




	public Integer getUse_diamon() {
		return use_diamon;
	}




	public void setUse_diamon(Integer use_diamon) {
		this.use_diamon = use_diamon;
	}




	/**
	 * 构造函数 用于购买
	 * @param props_id 道具id
	 * @param buy_counts 购买数量
	 * @param buyer_id 购买者id
	 * @param use_gold 购买消耗金币量
	 * @param use_diamon 购买消耗点卷量
	 */
	public MarketProps(Integer props_id, Integer buy_counts, Long buyer_id,Integer use_gold, Integer use_diamon) {
		this.props_id = props_id;
		this.buy_counts = buy_counts;
		this.buyer_id = buyer_id;
		this.use_gold = use_gold;
		this.use_diamon = use_diamon;
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




	public Integer getMarket_props_id() {
		return market_props_id;
	}

	public void setMarket_props_id(Integer market_props_id) {
		this.market_props_id = market_props_id;
	}

	public Integer getProps_id() {
		return props_id;
	}

	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}

	public Integer getPrivce_diamon() {
		return privce_diamon;
	}

	public void setPrivce_diamon(Integer rivce_diamon) {
		this.privce_diamon = rivce_diamon;
	}

	public Integer getPrivce_gold() {
		return privce_gold;
	}

	public void setPrivce_gold(Integer privce_gold) {
		this.privce_gold = privce_gold;
	}

	public Integer getMarket_weight() {
		return market_weight;
	}

	public void setMarket_weight(Integer market_weight) {
		this.market_weight = market_weight;
	}




    public String getProps_desc()
    {
        return props_desc;
    }




    public void setProps_desc(String props_desc)
    {
        this.props_desc = props_desc;
    }
	
	

}
