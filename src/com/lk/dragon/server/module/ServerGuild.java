/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerGuild.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-27 下午3:20:25
 */
package com.lk.dragon.server.module;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Faction;
import com.lk.dragon.db.domain.RoleFaction;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.GuildDomain;
import com.lk.dragon.server.module.analysis.GuildRequestAnalysis;
import com.lk.dragon.service.FactionInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.ReadProperties;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerGuild
{
    /** 缓存正在处理的加入公会申请的申请人id **/
    private static List<Long> applyRoleId = new ArrayList<Long>();
    
    @Autowired
    private FactionInfoService factionInfoService;
    @Autowired
    private SqlToolsService toolsService;
    /** 公会实体 **/
    private GuildDomain guildDomain;
    
    public ServerGuild(){}
    
    /**
     * 构造方法
     * @param guildDomain
     */
    public ServerGuild(GuildDomain guildDomain)
    {
    	this.factionInfoService = SpringBeanUtil.getBean(FactionInfoService.class);
        this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
    	this.guildDomain = guildDomain;
    }
    
    /**
     * 公会系统内部分析模块
     */
    public void guildAnalysis()
    {
        switch (guildDomain.getType())
        {
            case Constants.GUILD_LIST_TYPE:
            {
                //获取 公会列表信息
                getGuildList();
                break;
            }
            case Constants.GUILD_CREATE_TYPE:
            {
                //创建公会
                createGuild();
                break;
            }
            case Constants.GUILD_JOIN_TYPE:
            {
                //申请加入加入公会
                joinGuild();
                break;
            }
            case Constants.GUILD_QUIT_TYPE:
            {
                //退出公会
                quitGuild();
                break;
            }
            case Constants.GUILD_ALLOW_TYPE:
            {
                //允许玩家加入公会请求
                allowIn();
                break;
            }
            case Constants.GUILD_UPDATE_TYPE:
            {
                //更新公会信息、如公会名等
                updateGuild();
                break;
            }
            case Constants.GUILD_APPOINT_TYPE:
            {
                //会长任命副会长职务
                appointChairman();
                break;
            }
            case Constants.GUILD_EDIT_VICE_CHAIRMAN_TYPE:
            {
                //会长编辑管理层职位职称
                updatePosition();
                break;
            }
            case Constants.GUILD_DELE_APPOINT_TYPE:
            {
                //会长取消管理层职务
                deleChairman();
                break;
            }
            case Constants.GUILD_ADD_POSITION_TYPE:
            {
                //新增公会职位
                addPosition();
                break;
            }
            case Constants.GUILD_DELE_POSITION_TYPE:
            {
                //删除公会职位 
                delePosition();
                break;
            }
            case Constants.GUILD_REPLACE_CHAIRMAN_TYPE:
            {
                //会长将会长职务让给他人
                presidentReplace();
                break;
            }
            case Constants.GUILD_CHAIRMAN_DELE_TYPE:
            {
                //会长、副会长踢出人员
                deleMember();
                break;
            }
            case Constants.GUILD_MEMBER_GET_REWARD_TYPE:
            {
                //会员领取等级奖励
                memberGetReward();
                break;
            }
            case Constants.GUILD_RECORD_INFO_TYPE:
            {
                //查看 公会的记录信息
                getGuildRecord();
                break;
            }
            case Constants.GUILD_APPLY_LIST_TYPE:
            {
                //申请加入公会人员列表信息查询
                guildApplyList();
                break;
            }
            case Constants.GUILD_CARBON_LIST_TYPE:
            {
                //公会副本列表查询功能
                guildCarbonList();
                break;
            }
            case Constants.GUILD_MEMBER_LIST_TYPE:
            {
                //查询公会现有成员列表功能
                getMemberList();
                break;
            }
            case Constants.GUILD_MEMBER_INFO_TYPE:
            {
                //查询公会中个人的相关信息
                getMyselfInfo();
                break;
            }
            case Constants.GUILD_ROLE_HAS_APPLY_TYPE:
            {
                //查询角色已经申请加入的公会列表
                getRoleHasApplyGuild();
                break;
            }
            case Constants.GUILD_REFUSE_APPLY_TYPE:
            {
                //拒绝玩家加入公会
                refuseApply();
                break;
            }
            case Constants.GUILD_DELE_TYPE:
            {
                //解散公会
                deleGuild();
                break;
            }
            case Constants.GUILD_GET_POSITION_LIST_TYPE:
            {
                //查询公会职位列表
                getGuildPosition();
                break;
            }
            case Constants.GUILD_MEMBER_SINGLE_TYPE:
            {
                //查询单个成员信息
                getMemberSingle();
                break;
            }
        }
    }
    
    
    /**
     * 查询单个成员信息
     */
    private void getMemberSingle() {
    	//关联ＩＤ
		long role_guild_id = guildDomain.getRole_guild_id();
		  List<RoleFaction> roleList = factionInfoService.getFactionRoles(role_guild_id,2);
	        
	        //获取响应字符串
	        String memberListResponse = GuildRequestAnalysis.getGuildMemberList(roleList);
	        SocketUtil.responseClient(guildDomain.getCtx(), memberListResponse);
	}

	/**
     * 创建公会
     */
    private void createGuild()
    {
        //创建公会请求处理
        long roleId = guildDomain.getRoleId();
        String guildName = guildDomain.getGuildName();
        String guildIcon = guildDomain.getGuildIcon();
        int goldNum = guildDomain.getGold_num();
        
        Map<String, Long> map = null;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			//执行创建公会
			map = factionInfoService.createFaction(roleId, guildName, guildIcon, goldNum);
			title = "请求创建公会\r\n公会名:"+guildName+";icon:"+guildIcon;
			logRes = Constants.LOG_RES_SUCESS;
		} catch (Exception e) {
			title = OperateLogUtil.getExceptionStackInfo(e);
			e.printStackTrace();
		}
		//插入日志
		toolsService.addNewLogInfo(new Tools(roleId, "公会模块", title, logRes));
		//玩家请求正常
        //获取响应字符串
        String createGuildResponse = GuildRequestAnalysis.getCreateGuildResponse(map);
        SocketUtil.responseClient(guildDomain.getCtx(), createGuildResponse);
    }
    
    /**
     * 获取公会列表信息
     */
    private void getGuildList()
    {
        //查询公会列表
        String guildName = guildDomain.getGuildName();
        if (guildName == null)
        {
            guildName = "";
        }
        List<Faction> guildList = factionInfoService.findFactionByName(guildName);
        
        //获取响应字符串
        SocketUtil.responseClient(guildDomain.getCtx(),
                GuildRequestAnalysis.getGuildListResponse(guildList, 1));
    }
    
    /**
     * 允许会员加入公会
     */
    private void allowIn()
    {
        //当前处理人id
        long roleId = guildDomain.getRoleId();
        //判定当前处理人是否有权限做该项操作
        if (checkRoleRight(roleId, guildDomain.getCtx()))
        {
            //申请人id
            long applyId = guildDomain.getApplyRoleId();
            //检测该角色id是否在处理中
            String roleGuildResponse = "";
            boolean isDeal = checkRoleIsAllow(applyId);
            String applyName = guildDomain.getApplyRoleName();
            
            //加入id到缓存中
            applyRoleId.add(applyId);
            
            if (isDeal)
            {
                roleGuildResponse = JSONUtil.getWrongResponse(Constants.ROLE_HAS_GUILD);
            }
            else
            {
                //允许会员加入公会操作
                long guildId = guildDomain.getGuildId();
                long roleGuildId = -2;
                int logRes = Constants.LOG_RES_FAIL;
                String title = "";
				try {
					roleGuildId = factionInfoService.addRoleFaction(guildId, applyId, applyName);
					logRes =Constants.LOG_RES_SUCESS;
					title = "允许玩家入会申请\r\n申请人ID："+applyId+"/r/n申请人角色名："+applyName+"\r\n";
				} catch (Exception e) {
					e.printStackTrace();
					title = OperateLogUtil.getExceptionStackInfo(e);
					
				}
                //插入日志
				toolsService.addNewLogInfo(new Tools(roleId, "公会模块",title, logRes));
                //响应字符串
                roleGuildResponse = GuildRequestAnalysis.getRoleGuildResponse(roleGuildId);    
            }
            
            //将id从缓存中移除
            applyRoleId.remove(applyId);
            
            SocketUtil.responseClient(guildDomain.getCtx(), roleGuildResponse);
        }
    }
    
    /**
     * 更新公会信息
     */
    private void updateGuild()
    {
        //修改公会信息
        long guildId = guildDomain.getGuildId();
        long roleId = guildDomain.getRoleId();
        String guildAnnou = guildDomain.getGuildAnnouncement();
        
        boolean result = false;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			result = factionInfoService.updateFactionPublic(guildId, guildAnnou);
			logRes = Constants.LOG_RES_SUCESS;
			title = "更新公会信息\r\n公会ID："+guildId+"/r/n修改内容："+guildAnnou+"\r\n";
		} catch (Exception e) {
			title = OperateLogUtil.getExceptionStackInfo(e);
			e.printStackTrace();
		}
        
        //插入日志
		toolsService.addNewLogInfo(new Tools(roleId, "公会模块",title, logRes));
        
        //响应客户端
        SocketUtil.responseClient(guildDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 申请加入加入公会
     */
    private void joinGuild()
    {
        //玩家申请加入公会
        long roleId = guildDomain.getRoleId();
        long guildId = guildDomain.getGuildId();
        int result = 0;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
        try {
			result = factionInfoService.applyUnionFaction(roleId, guildId);
			logRes = Constants.LOG_RES_SUCESS;
			title = "申请加入工会\r\n公会ID："+guildId;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
        
        //插入日志
		toolsService.addNewLogInfo(new Tools(roleId, "公会模块",title, logRes));
        //响应客户端
        SocketUtil.responseClient(guildDomain.getCtx(),
                GuildRequestAnalysis.getApplyInGuildResponse(result));
    }
    
    /**
     * 管理拒绝玩家申请
     */
    private void refuseApply()
    {
        //拒绝玩家加入申请
        long role_id = guildDomain.getRoleId();
        long apply_id = guildDomain.getApplyRoleId();
        long guild_id = guildDomain.getGuildId();
        boolean result = false;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			result = factionInfoService.refuseRoleApplyFaction(apply_id, guild_id);
			title = "拒绝玩家的申请/r/n申请人ID："+apply_id+"/r/n公会ID："+guild_id;
			logRes = Constants.LOG_RES_SUCESS;
			System.out.println("res1:"+result);
		} catch (Exception e) {
			e.printStackTrace();
			title =OperateLogUtil.getExceptionStackInfo(e);
		}
        
        //插入日志
		toolsService.addNewLogInfo(new Tools(role_id, "公会模块",title, logRes));
        System.out.println("res2:"+result);
        //响应客户端
        SocketUtil.responseClient(guildDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 退出公会
     */
    private void quitGuild()
    {
        //玩家主动退出公会
        long role_id = guildDomain.getRoleId();
        boolean isMana = checkRoleRight(role_id, null);
        long guildId = guildDomain.getGuildId();
        String name = guildDomain.getRoleName();
        boolean result = false;
        int logRes =Constants.LOG_RES_FAIL;
		String title = "";
        try {
			result = factionInfoService.quitFaction(role_id, name, guildId, isMana);
			logRes = Constants.LOG_RES_SUCESS;
			title = "退出公会\r\n退出角色名："+name+"/r/n公会ID："+guildId;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
		//插入日志
		toolsService.addNewLogInfo(new Tools(role_id, "公会模块",title, logRes));
		        
        //响应字符串
        SocketUtil.responseClient(guildDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 任命副会长
     */
    private void appointChairman()
    {
        updatePosition(1);
    }
    
    /**
     * 取消副会长任命
     */
    private void deleChairman()
    {
        updatePosition(2);
    }
    
    /**
     * 会长将会长职务让出
     */
    private void presidentReplace()
    {
        //会长职务移交操作
        long guildId = guildDomain.getGuildId();
        long roleId = guildDomain.getRoleId();
        long applyId = guildDomain.getApplyRoleId();
        String applyName = guildDomain.getApplyRoleName();
        String roleName = guildDomain.getRoleName();
        
        boolean result = false;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "";
		try {
			result = factionInfoService.turnKingToOther(
			        guildId, applyId, applyName, roleId, roleName);
			logRes = Constants.LOG_RES_SUCESS;
			title = "会长转让\r\n角色:"+applyName+"请求将会长职位移交给:"+roleName;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
		//插入日志
		toolsService.addNewLogInfo(new Tools(applyId, "公会模块",title , logRes));
        //响应客户端
        SocketUtil.responseClient(guildDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 会长、副会长踢人
     */
    private void deleMember()
    {
        //判定管理是否够权限踢人
        long roleId = guildDomain.getRoleId();
        String roleName = guildDomain.getRoleName();
        long shotId = guildDomain.getShotId();
        String shotName = guildDomain.getShotName();
        long guildId = guildDomain.getGuildId();
        
        if (checkRoleCanShotOff(roleId, shotId, guildDomain.getCtx()))
        {
            //踢人出公会
            boolean result = false;
            int logRes = Constants.LOG_RES_FAIL;
            String title = "";
			try {
				result = factionInfoService.shotOffFaction(guildId, roleId, roleName, shotId, shotName);
				logRes = Constants.LOG_RES_SUCESS;
				title = "管理踢出会员\r\n管理角色ID："+roleId+";角色名:"+roleName+"\r\n被踢会员ID:"+shotId+";角色名:"+roleName;
			} catch (Exception e) {
				e.printStackTrace();
				title = OperateLogUtil.getExceptionStackInfo(e);
			}
			//插入日志
			toolsService.addNewLogInfo(new Tools(roleId, "公会模块",title , logRes));
            //响应客户端
            SocketUtil.responseClient(guildDomain.getCtx(), 
                    JSONUtil.getBooleanResponse(result));
        }
    }
    
    /**
     * 会员领取公会活跃度奖励
     */
    private void memberGetReward()
    {
        //会员领取奖励物品
        long roleId = guildDomain.getRoleId();
        int awardLev = guildDomain.getAward_lev();
        int awardResult = guildDomain.getAfter_award_result();
        
        //获取奖励信息
        String awardInfo = ReadProperties
                .getProperties("level" + awardLev, "guild_award.properties");
        String[] infos = awardInfo.split(";");
        //金币奖励
        int goldNum = Integer.parseInt(infos[0]);
        //钻石奖励
        int diamondNum = Integer.parseInt(infos[1]);
        //宝石袋奖励
        int diamondBagNum = Integer.parseInt(infos[2]);
        
        long result = -1;
        String title = "";
        int logRes = Constants.LOG_RES_FAIL;
		try {
			result = factionInfoService.getFactionReward(
			        roleId, awardResult, goldNum, diamondNum, diamondBagNum);
			title = "会员领取公会贡献度奖励\r\n领取人ID："+roleId;
			logRes = Constants.LOG_RES_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
        int resKey = -1;
        //响应客户端
        if(result == -10 || result > 0){//背包已满
        	resKey = Constants.REQUEST_SUCCESS;
        }
		//插入日志
		toolsService.addNewLogInfo(new Tools(roleId, "公会模块", title, logRes));

        SocketUtil.responseClient(guildDomain.getCtx(), JSONUtil.getNewPropsIdResponse(resKey, result));
    }
    
    /**
     * 查看公会操作记录等信息
     */
    private void getGuildRecord()
    {
        //公会会员查询公会操作记录信息
        long guildId = guildDomain.getGuildId();
        List<Faction> recordList = factionInfoService.selectFactionLog(guildId);
        
        //获取相应字符串
        SocketUtil.responseClient(guildDomain.getCtx(),
                GuildRequestAnalysis.guildRecordResponse(recordList));
    }
    
    /**
     * 公会加入申请列表查询
     */
    private void guildApplyList()
    {
        //查询申请加入公会的人员列表
        long guildId = guildDomain.getGuildId();
        long roleId = guildDomain.getRoleId();
        //判定角色权限是否足够
        if (checkRoleRight(roleId, guildDomain.getCtx()))
        {
            List<Faction> roleList = 
                    factionInfoService.selectApplyFactionInfo(2, guildId);
            
            //获取相应字符串
            SocketUtil.responseClient(guildDomain.getCtx(), 
                    GuildRequestAnalysis.getGuildListResponse(roleList, 2));
        }
    }
    
    /**
     * 公会副本列表查询
     */
    private void guildCarbonList()
    {
        
    }
    
    /**
     * 查询公会现有成员
     * 
     */
    private void getMemberList()
    {
        //查询成员列表
        long guildId = guildDomain.getGuildId();
        List<RoleFaction> roleList = factionInfoService.getFactionRoles(guildId,1);
        
        //获取响应字符串
        String memberListResponse = GuildRequestAnalysis.getGuildMemberList(roleList);
        SocketUtil.responseClient(guildDomain.getCtx(), memberListResponse);
    }
    
    /**
     * 查询公会中个人相关信息
     */
    private void getMyselfInfo()
    {
        //查询个人公会信息
        long roleId = guildDomain.getRoleId();
        Faction faction = factionInfoService.selectFactionRight(roleId);
        
        //获取相应字符串
        SocketUtil.responseClient(guildDomain.getCtx(),
                GuildRequestAnalysis.getMySelfInfo(faction));
    }
    
    /**
     * 查询角色已经申请的公会列表
     */
    private void getRoleHasApplyGuild()
    {
        //查询该角色已申请的公会列表
        long roleId = guildDomain.getRoleId();
        List<Faction> list = factionInfoService.selectApplyFactionInfo(1, roleId);
        
        //获取响应字符串
        SocketUtil.responseClient(guildDomain.getCtx(), 
                GuildRequestAnalysis.getGuildListResponse(list, 3));
    }
    
    
    
    /**
     * 解散公会
     */
    private void deleGuild()
    {
        //解散公会
        long guildId = guildDomain.getGuildId();
        long operId = guildDomain.getOperator_id();
        boolean result = false;
        String title = "";
        int logRes = Constants.LOG_RES_FAIL;
		try {
			result = factionInfoService.deleteFaction(guildId);
			title = "解散公会\r\n"+"操作人ＩＤ："+operId+"\r\n公会ID："+guildId;
			logRes = Constants.LOG_RES_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
        
		toolsService.addNewLogInfo(new Tools(operId, "公会模块", title, logRes));
        //解散公会请求响应客户端
        SocketUtil.responseClient(guildDomain.getCtx(), 
                JSONUtil.getBooleanResponse(result));
    }
    
    
    
//    /*********************** 内部使用  移至FactionInfoService.java******************************/
//    /**
//     * 公会签到、公会副本后，更新公会的荣誉度接口
//     * @param score
//     * @param roleId
//     */
//    public void addGuildScore(int score, int roleId)
//    {
//        //查询玩家所在公会的信息
//        Faction guild = factionInfoService.selectFactionRight(roleId);
//        //如果玩家在公会中则会进行升级判断
//        if (guild != null)
//        {
//            //获取当前的荣誉度
//            int factionScore = guild.getFaction_score();
//            //荣誉度增加
//            factionScore += score;
//            //获取当前等级
//            int lev = guild.getFaction_lev();
//            //当前等级的最大成员容量
//            int nextLevMembers = guild.getMax_member_counts();
//            //获取升级到下一等级的经验值
//            String nextLevInfo = ReadProperties
//                    .getProperties("level" + (lev + 1), "guild_lev_up.properties");
//            String[] infos = nextLevInfo.split(";");
//            int nextLevScore = Integer.parseInt(infos[0]);
//            
//            //如果加分后达到升级条件，则进行升级操作
//            if (factionScore >= nextLevScore)
//            {
//                //等级+1
//                lev++;
//                //查找最大公会成员数量
//                nextLevMembers = Integer.parseInt(infos[1]);
//            }
//            
//            //更新公会相关信息
//            factionInfoService.factionLevUp(guild.getFaction_id(),
//                    lev, nextLevMembers, factionScore, roleId, score);
//        }
//    }
    
    /**
     * 检测角色id是否正在被拉入工会
     * @param roleId
     * @return
     */
    private boolean checkRoleIsAllow(long roleId)
    {
        for (long checkId : applyRoleId)
        {
            if (checkId == roleId)
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 判定角色权限信息
     * @param roleId
     * @return
     */
    private boolean checkRoleRight(long roleId, ChannelHandlerContext ctx)
    {
        int right = factionInfoService.selectRoleFactionRight(roleId);
        
        if (right == 3)
        {
            if (ctx != null)
            {
                //普通帮众没有权限
                //写回权限不够的信息
                SocketUtil.responseClient(ctx, 
                        JSONUtil.getWrongResponse(Constants.ROLE_CAN_NOT_DO));
            }
            
            return false;
        }
        
        return true;
    }
    
    /**
     * 判定是否够权限将人提出
     * @param roleId
     * @param shotId
     * @param nbc
     * @return
     */
    private boolean checkRoleCanShotOff(long roleId, long shotId, ChannelHandlerContext ctx)
    {
        //获取权限
        int roleRight = factionInfoService.selectRoleFactionRight(roleId);
        int shotRight = factionInfoService.selectRoleFactionRight(shotId);
        
        //判定踢人者权限是否比被踢人的高
        if (roleRight == 3 || (roleRight == 2 && shotRight == 2)
                || shotRight == 1)
        {
            SocketUtil.responseClient(ctx, 
                    JSONUtil.getWrongResponse(Constants.ROLE_CAN_NOT_DO));
            return false;
        }
        
        return true;
    }
    
    /**
     * 新增公会职位
     */
   private void addPosition()
   {
       /** 新增公会职位 **/
       String jobName = guildDomain.getGuildJobName();
       long guildId = guildDomain.getGuildId(); 
       long operId = guildDomain.getOperator_id();
       //公会职位
       long positionId = -1;
       
       String title = "";
       int logRes = Constants.LOG_RES_FAIL;
	try {
		positionId = factionInfoService.addGuildPosition(jobName, guildId);
		title = "新增公会职位\r\n"+"操作人ＩＤ："+operId+"\r\n新职位："+jobName;
		logRes = Constants.LOG_RES_SUCESS;
	} catch (Exception e) {
		e.printStackTrace();
	}
	toolsService.addNewLogInfo(new Tools(operId, "公会模块", title, logRes));
       //回写客户端
       SocketUtil.responseClient(guildDomain.getCtx(), 
               GuildRequestAnalysis.addGuildPosition(positionId));
   }
   
   /**
    * 修改职位名称
    */
   private void updatePosition()
   {
       /** 新增公会职位 **/
       String jobName = guildDomain.getGuildJobName();
       long positionId = guildDomain.getPosition_id();
       long operId = guildDomain.getOperator_id();
       //公会职位
       boolean result  = false;
       String title = "";
       int logRes = Constants.LOG_RES_FAIL;
	try {
		result = factionInfoService.editGuildPosition(positionId, jobName);
		title = "修改公会职位\r\n"+"操作人ＩＤ："+operId+"\r\n新职位："+jobName;
		logRes = Constants.LOG_RES_SUCESS;
	} catch (Exception e) {
		e.printStackTrace();
		title = OperateLogUtil.getExceptionStackInfo(e);
	}
	toolsService.addNewLogInfo(new Tools(operId, "公会模块", title, logRes));
       //响应客户端
       SocketUtil.responseClient(guildDomain.getCtx(),
               JSONUtil.getBooleanResponse(result));
   }
   
  /**
   * 删除公会职位
   */
   private void delePosition()
   {
       long positionId = guildDomain.getPosition_id();
       long guildId = guildDomain.getGuildId(); 
       long operatoId = guildDomain.getOperator_id();
       //删除职位列表
       int result = -1;
       String title = "";
       int logRes = Constants.LOG_RES_FAIL;
	try {
		result = factionInfoService.deleGuildPosition(positionId, guildId);
		title = "删除公会职位\r\n"+"操作人ID："+operatoId+"\r\n帮会ID："+guildId+"\r\n被删除职位ID:"+positionId;
		logRes = Constants.LOG_RES_SUCESS;
	} catch (Exception e) {
		e.printStackTrace();
		title = OperateLogUtil.getExceptionStackInfo(e);
	}
    //插入日志
	toolsService.addNewLogInfo(new Tools(operatoId, "公会模块", title, logRes));
       //响应客户端
       SocketUtil.responseClient(guildDomain.getCtx(),
               GuildRequestAnalysis.getDeleGuildPosition(result));
   }

   /**
    * 查询公户职位列表
    */
   private void getGuildPosition()
   {
       long guildId = guildDomain.getGuildId(); 
       
       //获取职位列表信息
       List<Faction> positionList = factionInfoService.getPositionList(guildId);
       
       //响应客户端
       SocketUtil.responseClient(guildDomain.getCtx(), 
               GuildRequestAnalysis.getGuildListResponse(positionList, 4));
   }
   
   /**
    * 更新公会成员职位
    * @param flag
    */
   private void updatePosition(int flag)
   {
       //会长更改副会长职务
       long roleId = guildDomain.getRoleId();
       String jobName = guildDomain.getGuildJobName();
       long positionId = guildDomain.getPosition_id();
       String roleName = guildDomain.getRoleName();
       long faction_id = guildDomain.getGuildId();
       long operatorId = guildDomain.getOperator_id();
       boolean result = false;
       int logRes = 0;
       String title = "";
	try {
		result = factionInfoService.
		           upRolePosition(roleId, roleName, faction_id, positionId, jobName, flag);
		if(result)
			logRes = 1;
		title = "职位变更\r\n角色ID:"+operatorId+"请求变更角色ID:"+roleId+"的帮会职位";
	} catch (Exception e) {
		e.printStackTrace();
		title = OperateLogUtil.getExceptionStackInfo(e);
	}
       //插入日志
	toolsService.addNewLogInfo(new Tools(operatorId, "公会模块", title, logRes));
       //响应客户端
       SocketUtil.responseClient(guildDomain.getCtx(), 
               JSONUtil.getBooleanResponse(result));
   }
   
}
