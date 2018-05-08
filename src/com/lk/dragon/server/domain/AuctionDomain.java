/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： AuctionDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-27 下午2:44:58
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class AuctionDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 拍卖行记录id **/
    private long auctionId;
    /** 购买的物品id **/
    private int goodsId;
    /** 角色id **/
    private Long roleId;
    /** 拍卖人的id **/
    private long sellerId;
    /** 商品名称 **/
    private String goodsName;
    /** 商品类别 **/
    private Integer goodsType;
    /** 商品数量 **/
    private int goodsNum;
    /** 商品价格 **/
    private int goodsPrice;
    /** 道具中间表id **/
    private long rolePropId;
    /** 拍卖行模块请求类型（查询拍卖列表、购买物品） **/
    private int type;
    /** 商品子类型 **/
    private int sub_type;
    /** 起始索引值 **/
    private int b_ind;
    /** 结束所引值 **/
    private int e_ind;
    /** 排序列 **/
    private int order_key;
    /** 排序方式 **/
    private int de_as_key;
    /** 商品品质 **/
    private int goods_quality;
    /** 额外信息 **/
    private String extra_info;
    
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public String getExtra_info() {
		return extra_info;
	}
	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}
	public int getGoods_quality() {
		return goods_quality;
	}
	public void setGoods_quality(int goods_quality) {
		this.goods_quality = goods_quality;
	}
	public int getSub_type() {
		return sub_type;
	}
	public void setSub_type(int sub_type) {
		this.sub_type = sub_type;
	}
	public int getB_ind() {
		return b_ind;
	}
	public void setB_ind(int b_ind) {
		this.b_ind = b_ind;
	}
	public int getE_ind() {
		return e_ind;
	}
	public void setE_ind(int e_ind) {
		this.e_ind = e_ind;
	}
	public int getOrder_key() {
		return order_key;
	}
	public void setOrder_key(int order_key) {
		this.order_key = order_key;
	}
	public int getDe_as_key() {
		return de_as_key;
	}
	public void setDe_as_key(int de_as_key) {
		this.de_as_key = de_as_key;
	}
	public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getGoodsId()
    {
        return goodsId;
    }
    public void setGoodsId(int goodsId)
    {
        this.goodsId = goodsId;
    }
    public Long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }
    public String getGoodsName()
    {
        return goodsName;
    }
    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }
    public Integer getGoodsType()
    {
        return goodsType;
    }
    public void setGoodsType(Integer goodsType)
    {
        this.goodsType = goodsType;
    }
    public int getGoodsNum()
    {
        return goodsNum;
    }
    public void setGoodsNum(int goodsNum)
    {
        this.goodsNum = goodsNum;
    }
    public int getGoodsPrice()
    {
        return goodsPrice;
    }
    public void setGoodsPrice(int goodsPrice)
    {
        this.goodsPrice = goodsPrice;
    }
    public long getAuctionId()
    {
        return auctionId;
    }
    public void setAuctionId(long auctionId)
    {
        this.auctionId = auctionId;
    }
    public long getSellerId()
    {
        return sellerId;
    }
    public void setSellerId(long sellerId)
    {
        this.sellerId = sellerId;
    }
    public long getRolePropId()
    {
        return rolePropId;
    }
    public void setRolePropId(long rolePropId)
    {
        this.rolePropId = rolePropId;
    }
    
    
}
