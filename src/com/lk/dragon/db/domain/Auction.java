 /** 
 *
 * @Title: Auction.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-23 下午4:08:55 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:拍卖行信息表映射
 */
public class Auction {
	
	private Long auction_id;//寄售商品ID
	private Long seller_id;//寄售人ID
	private String seller_name;//出售人名称
	private Integer props_id;//道具ID
	private Integer privce;//商品单价
	private Integer props_counts;//商品剩余个数：针对可叠合类道具
	private String overdue;//商品下架时间
	private Long role_props_id;//角色-道具ID
	private Integer quality;//商品品质
	private Integer buyCounts;//购买数量
	private Integer totalPrice;//商品总价
	private Long buyerId;//购买者Id
	private String props_name;//道具名称
	private String props_icon;//道具图标路径
	private String props_comment;//道具效果介绍
	private String sysdate_database;//数据库当前时间
	private Integer usePrice;//买家消费金额
	
	private Integer command_lev;//最低使用等级
	private String extra_info;//额外信息
	private Integer props_type;
	
	private Integer last_seconds;//商品到期剩余秒数
	public Auction(){}
	
	
	
	
	
	
	public Auction(Long auction_id, Long seller_id, Integer props_id,Integer props_counts, Integer buyCounts,Integer usePrice,Integer totalPrice, Long buyerId) {
		this.auction_id = auction_id;
		this.seller_id = seller_id;
		this.props_id = props_id;
		this.buyCounts = buyCounts;
		this.props_counts = props_counts;
		this.totalPrice = totalPrice;
		this.buyerId = buyerId;
		this.usePrice = usePrice;
	}






	public Integer getLast_seconds() {
		return last_seconds;
	}






	public void setLast_seconds(Integer last_seconds) {
		this.last_seconds = last_seconds;
	}






	public Integer getProps_type() {
		return props_type;
	}






	public void setProps_type(Integer props_type) {
		this.props_type = props_type;
	}






	public String getExtra_info() {
		return extra_info;
	}






	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}






	public Integer getCommand_lev() {
		return command_lev;
	}






	public void setCommand_lev(Integer command_lev) {
		this.command_lev = command_lev;
	}






	public Integer getQuality() {
		return quality;
	}






	public void setQuality(Integer quality) {
		this.quality = quality;
	}






	public Integer getUsePrice() {
		return usePrice;
	}






	public void setUsePrice(Integer usePrice) {
		this.usePrice = usePrice;
	}






	public Integer getBuyCounts() {
		return buyCounts;
	}






	public void setBuyCounts(Integer buyCounts) {
		this.buyCounts = buyCounts;
	}






	public Integer getTotalPrice() {
		return totalPrice;
	}




	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}




	public Long getBuyerId() {
		return buyerId;
	}




	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}




	public Long getRole_props_id() {
		return role_props_id;
	}




	public void setRole_props_id(Long role_props_id) {
		this.role_props_id = role_props_id;
	}




	public String getSysdate_database() {
		return sysdate_database;
	}




	public void setSysdate_database(String sysdate_database) {
		this.sysdate_database = sysdate_database;
	}




	public Auction(Long seller_id, Integer props_id, Integer privce,Integer props_counts) {
		super();
		this.seller_id = seller_id;
		this.props_id = props_id;
		this.privce = privce;
		this.props_counts = props_counts;
	}


	
	
	
	public String getOverdue() {
		return overdue;
	}


	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}


	public String getProps_comment() {
		return props_comment;
	}


	public void setProps_comment(String props_comment) {
		this.props_comment = props_comment;
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


	public Long getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Long auction_id) {
		this.auction_id = auction_id;
	}
	public Long getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(Long seller_id) {
		this.seller_id = seller_id;
	}
	public Integer getProps_id() {
		return props_id;
	}
	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}
	public Integer getPrivce() {
		return privce;
	}
	public void setPrivce(Integer privce) {
		this.privce = privce;
	}
	public Integer getProps_counts() {
		return props_counts;
	} 
	public void setProps_counts(Integer props_counts) {
		this.props_counts = props_counts;
	}






    public String getSeller_name()
    {
        return seller_name;
    }






    public void setSeller_name(String seller_name)
    {
        this.seller_name = seller_name;
    }
	
	
	

}
