/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-21 下午4:54:46
 */
package com.lk.dragon.server.analysis;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import com.lk.dragon.server.domain.ArmyDomain;
import com.lk.dragon.server.domain.AuctionDomain;
import com.lk.dragon.server.domain.BattleDomain;
import com.lk.dragon.server.domain.ChatDomain;
import com.lk.dragon.server.domain.CityDomain;
import com.lk.dragon.server.domain.ConnDomain;
import com.lk.dragon.server.domain.DiamondDomain;
import com.lk.dragon.server.domain.EquipDomain;
import com.lk.dragon.server.domain.FriendDomain;
import com.lk.dragon.server.domain.GuildDomain;
import com.lk.dragon.server.domain.HeroDomain;
import com.lk.dragon.server.domain.MailDomain;
import com.lk.dragon.server.domain.MallDomain;
import com.lk.dragon.server.domain.MissionDomain;
import com.lk.dragon.server.domain.PropDomain;
import com.lk.dragon.server.domain.RankDomain;
import com.lk.dragon.server.domain.InviteDomain;
import com.lk.dragon.server.domain.RoleDomain;
import com.lk.dragon.server.domain.WorldMapDomain;
import com.lk.dragon.server.module.ServerArmy;
import com.lk.dragon.server.module.ServerAuction;
import com.lk.dragon.server.module.ServerBattle;
import com.lk.dragon.server.module.ServerCity;
import com.lk.dragon.server.module.ServerDiamond;
import com.lk.dragon.server.module.ServerEquip;
import com.lk.dragon.server.module.ServerFriend;
import com.lk.dragon.server.module.ServerGuild;
import com.lk.dragon.server.module.ServerHero;
import com.lk.dragon.server.module.ServerMail;
import com.lk.dragon.server.module.ServerMall;
import com.lk.dragon.server.module.ServerMission;
import com.lk.dragon.server.module.ServerProp;
import com.lk.dragon.server.module.ServerRank;
import com.lk.dragon.server.module.ServerInvite;
import com.lk.dragon.server.module.ServerRole;
import com.lk.dragon.server.module.ServerWorldMap;
import com.lk.dragon.server.module.analysis.ArmyRequestAnalysis;
import com.lk.dragon.server.module.analysis.AuctionRequestAnalysis;
import com.lk.dragon.server.module.analysis.BattleRequestAnalysis;
import com.lk.dragon.server.module.analysis.ChatRequestAnalysis;
import com.lk.dragon.server.module.analysis.CityRequestAnalysis;
import com.lk.dragon.server.module.analysis.DiamondRequesetAnalysis;
import com.lk.dragon.server.module.analysis.EquipRequestAnalysis;
import com.lk.dragon.server.module.analysis.FriendRequestAnalysis;
import com.lk.dragon.server.module.analysis.GuildRequestAnalysis;
import com.lk.dragon.server.module.analysis.HeroRequestAnalysis;
import com.lk.dragon.server.module.analysis.MailRequestAnalysis;
import com.lk.dragon.server.module.analysis.MallRequestAnalysis;
import com.lk.dragon.server.module.analysis.MissionRequestAnalysis;
import com.lk.dragon.server.module.analysis.PropRequestAnalysis;
import com.lk.dragon.server.module.analysis.RankRequestAnalysis;
import com.lk.dragon.server.module.analysis.InviteRequestAnalysis;
import com.lk.dragon.server.module.analysis.RoleRequestAnalysis;
import com.lk.dragon.server.module.analysis.WorldMapRequestAnalysis;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class ServerAnalysis {
	/** 连接信息 **/
	private ChannelHandlerContext ctx;
	/** 请求信息 **/
	private String info;

	public ServerAnalysis() {
	}

	/**
	 * 构造函数
	 * 
	 * @param ndc
	 * @param info
	 */
	public ServerAnalysis(ChannelHandlerContext ctx, String info) {
		this.ctx = ctx;
		this.info = info;
	}

	/**
	 * 根据请求信息，将信息处理分发到各个的模块
	 */
	public void analysisRequest() {
		if(info.equals(""))
			return;
		// 解析请求类型
		int requestType = JSONUtil.getDealType(info);
		switch (requestType) {
		case Constants.MAIL_TYPE: {
			// 邮件处理模块
			// 获取邮件实体
			MailDomain mailDomain = MailRequestAnalysis.getMailInfo(info);
			mailDomain.setCtx(ctx);

			// 将请求下发到邮件模块进行处理
			new ServerMail(mailDomain).mailAnalysis();

			break;
		}
		case Constants.MALL_TYPE: {
			// 商城处理模块
			// 获取商城信息实体
			MallDomain mallDomain = MallRequestAnalysis.getMallInfo(info);
			mallDomain.setCtx(ctx);

			// 将商城请求下发到商城模块进行处理
			new ServerMall(mallDomain).mallAnalysis();
			break;
		}
		case Constants.GUILD_TYPE: {
			// 公会处理模块
			// 获取公会信息实体
			GuildDomain guildDomain = GuildRequestAnalysis.getGuildInfo(info);
			guildDomain.setCtx(ctx);

			// 将公会请求下发到公会模块进行处理
			new ServerGuild(guildDomain).guildAnalysis();
			break;
		}
		case Constants.FRIEND_TYPE: {
			// 好友处理模块
			// 获取好友信息实体
			FriendDomain friendDomain = FriendRequestAnalysis
					.getFriendInfo(info);
			friendDomain.setCtx(ctx);

			// 将好友请求下发到好友模块进行处理
			new ServerFriend(friendDomain).FriendAnalysis();
			break;
		}
		case Constants.BATTLE_TYPE: {
			// 战斗处理模块
			// 获取战斗请求实体
			BattleDomain battleDomain = BattleRequestAnalysis
					.getBattleInfo(info);
			battleDomain.setCtx(ctx);

			// 将战斗请求下发到战斗模块进行处理
			new ServerBattle(battleDomain).battleAnalysis();
			break;
		}
		case Constants.AUCTION_TYPE: {
			// 拍卖行处理模块
			// 获取拍卖行实体
			AuctionDomain auctionDomain = AuctionRequestAnalysis
					.getAuctionInfo(info);
			auctionDomain.setCtx(ctx);

			// 将拍卖行请求下发到拍卖行模块进行处理
			new ServerAuction(auctionDomain).auctionAnalysis();
			break;
		}
		case Constants.ROLE_TYPE: {
			// 如果是角色相关请求
			// 获取角色请求domain
			RoleDomain roleDomain = RoleRequestAnalysis.getRoleInfo(info);
			roleDomain.setCtx(ctx);

			// 将请求下发到角色请求处理模块进行相关处理
			new ServerRole(roleDomain).roleAnalysis();
			break;
		}
		// case Constants.CHAT_TYPE:
		// {
		// //聊天请求
		// //获取聊天实体信息
		// ChatDomain chatDomain = ChatRequestAnalysis.getChatContent(info);
		// chatDomain.setCtx(ctx);
		//
		// //将其下发到聊天模块进行处理
		// new ServerChat(chatDomain).chatAnalysis();
		// break;
		// }
		case Constants.CITY_TYPE: {
			// 主城相关请求
			CityDomain cityDomain = CityRequestAnalysis.getMainCityInfo(info);
			cityDomain.setCtx(ctx);

			new ServerCity(cityDomain).cityAnalysis();
			break;
		}
		case Constants.PROP_TYPE: {
			// 道具相关请求
			PropDomain propDomain = PropRequestAnalysis.getPropInfo(info);
			propDomain.setCtx(ctx);

			new ServerProp(propDomain).propAnalysis();
			break;
		}
		case Constants.WORLD_MAP_TYPE: {
			// 世界地图请求
			WorldMapDomain worldMapDomain = WorldMapRequestAnalysis
					.getWorldMapInfo(info);
			worldMapDomain.setCtx(ctx);

			new ServerWorldMap(worldMapDomain).worldMapAnalysis();
			break;
		}
		case Constants.HERO_TYPE: {
			// 英雄相关请求
			HeroDomain heroDomain = HeroRequestAnalysis.getHeroInfo(info);
			heroDomain.setCtx(ctx);
			new ServerHero(heroDomain).heroAnalysis();
			break;
		}
		case Constants.RANK_TYPE: {
			// 排行榜相关请求
			RankDomain rankDomain = RankRequestAnalysis.getRankInfo(info);
			rankDomain.setCtx(ctx);

			new ServerRank(rankDomain).rankAnalysis();
			break;
		}
		case Constants.EQUIP_TYPE: {
			// 装备-宝石模块
			EquipDomain equipDomain = EquipRequestAnalysis.getEquipInfo(info);
			equipDomain.setCtx(ctx);

			new ServerEquip(equipDomain).analysis();
			break;
		}
		case Constants.DIAMOND_TYPE: {
			// 宝石升级模块
			DiamondDomain diamondDomain = DiamondRequesetAnalysis
					.getDiamondInfo(info);
			diamondDomain.setCtx(ctx);

			new ServerDiamond(diamondDomain).diamondAnalysis();
			break;
		}
		case Constants.ARMY_TYPE: {
			// 军事系统请求
			ArmyDomain armyDomain = ArmyRequestAnalysis.getArmyInfo(info);
			armyDomain.setCtx(ctx);

			new ServerArmy(armyDomain).armyAnalysis();
			break;
		}
		case Constants.REWARD_TYPE: {
			// 奖励系统
			InviteDomain rewardDomain = InviteRequestAnalysis
					.getFriendInfo(info);
			rewardDomain.setCtx(ctx);

			new ServerInvite(rewardDomain).RewardAnalysis();
			break;
		}
		case Constants.MISSION_TYPE: {
			// 奖励系统
			MissionDomain missionDomain = MissionRequestAnalysis
					.getMissionInfo(info);
			missionDomain.setCtx(ctx);

			new ServerMission(missionDomain).missionAnalysis();
			break;
		}
		default:
			// 将请求错误的信息写回，并关闭连接
			ctx.writeAndFlush("THE REQUEST CODE ERROR");
			break;
		}
	}
}
