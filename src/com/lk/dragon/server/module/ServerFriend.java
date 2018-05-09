/**
 *
 *
 * 文件名称： ServerFriend.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-26 上午11:36:22
 */
package com.lk.dragon.server.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Relation;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.FriendDomain;
import com.lk.dragon.server.domain.MailDomain;
import com.lk.dragon.server.module.analysis.FriendRequestAnalysis;
import com.lk.dragon.service.MailInfoService;
import com.lk.dragon.service.RelationInfoService;
import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SMSUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerFriend
{
    /** 好友实体 **/
    private FriendDomain friendDomain;
    
    @Autowired
    private MailInfoService mailInfoService;
    @Autowired
    private RelationInfoService relationInfoService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private SqlToolsService toolsService;
    public ServerFriend(){}
    
    /**
     * 构造函数
     * @param friendDomain
     */
    public ServerFriend(FriendDomain friendDomain)
    {
    	this.mailInfoService = SpringBeanUtil.getBean(MailInfoService.class);
    	this.relationInfoService = SpringBeanUtil.getBean(RelationInfoService.class);
    	this.roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);
        this.friendDomain = friendDomain;
    }
    
    /**
     * 好友模块内部分析模块
     */
    public void FriendAnalysis()
    {
        switch (friendDomain.getType())
        {
            case Constants.FRIEND_ADD_TYPE:
            {
                //添加好友请求
                addFriend();
                break;
            }
            case Constants.FRIEND_LIST_TYPE:
            {
                //查询好友列表请求
                getFriendList();
                break;
            }
            case Constants.FRIEND_RESPONSE_ADD_TYPE:
            {
                //玩家响应被添加好友请求处理结果
                dealAddFriend();
                break;
            }
            case Constants.FRIEND_DELETE_TYPE:
            {
                //删除好友
                deleteFriend();
                break;
            }
            case Constants.FRIEND_DELETE_ENEMY_TYPE:
            {
                //删除仇人
                deleteEnemy();
                break;
            }
            case Constants.FRIEND_ADD_ENEMY_TYPE:
            {
                //添加仇人
                addEnemy();
                break;
            }
            case Constants.FRIEND_REQUEST_LIST_TYPE:
            {
                //获取好友请求信息查询
                getAddFriendRequest();
                break;
            }
            case Constants.FRIEND_DETAIL_INFO_TYPE:
            {
                //查询好友、仇人详细信息
                getDetailInfo();
                break;
            }
            case Constants.FRIEND_ONE_ROLE_TYPE:
            {
                //查询单个关系
                getOneRelation();
                break;
            }
        }
    }
    
    /**
     * 获取好友列表
     */
    public void getFriendList()
    {
        //查询列表信息
        List<Role> list = relationInfoService.getRelationRolesList(friendDomain.getId());
        
        //获取响应字符串
        String relationResponse = FriendRequestAnalysis.getRelationListResponse(list);
        SocketUtil.responseClient(friendDomain.getCtx(), relationResponse);
    }
    
    /**
     * 添加好友信息
     */
    private void addFriend()
    {
        //获取当前角色名的id信息
        long friend_id = roleInfoService.selectRoleIdByName(friendDomain.getName());
        friendDomain.setFriendId(friend_id);
        if (friend_id == -1)
        {
            //角色名不存在
            Map<String, Long> map = new HashMap<String, Long>();
            map.put(Constants.RESULT_KEY, Constants.ROLE_NOT_EXIT);
            //获取响应字符串
            String addFriendResponse = FriendRequestAnalysis.addFriendResponse(map);
            SocketUtil.responseClient(friendDomain.getCtx(), addFriendResponse);
            
            //新增操作日志
            String detail = "新增好友失败，角色：" + friendDomain.getName() + "不存在.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.FAIL);

            return;
        }
        else if (friend_id == friendDomain.getId())
        {
            //自己添加自己
            Map<String, Long> map = new HashMap<String, Long>();
            map.put(Constants.RESULT_KEY, Constants.ROLE_ADD_MYSELF);
            //获取响应字符串
            String addFriendResponse = FriendRequestAnalysis.addFriendResponse(map);
            SocketUtil.responseClient(friendDomain.getCtx(), addFriendResponse);
            
            //新增操作日志
            String detail = "新增好友失败，添加对象为角色本人.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.FAIL);

            return;
        }
        
        //创建relation实体
        Relation relation = new Relation();
        relation.setRole_left_id(friendDomain.getId());
        relation.setRole_right_id(friend_id);
        relation.setRelation_type(Constants.FRIEND);
        
        //判定是否满足加好友条件
        Map<String, Long> map = relationInfoService.canCreateFriend(relation);
        
        //如果 满足则向被添加对象发送邮件
        if (map.get(Constants.RESULT_KEY) == Constants.CAN_BE_FRIEND)
        {
            //判定请求是否已经发送过
            Mail mail = new Mail();
            mail.setMail_to(friend_id);
            mail.setMail_from(friendDomain.getId());
            Map<String,Long> mailMap = mailInfoService.checkIsSendFriendApply(mail);
            if (mailMap.size() > 0)
            {

                //新增操作日志
                String detail = "成功添加角色："+ friendDomain.getName() +"为好友.";
                OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.SUCCESS);

                if (mailMap.get(Constants.RESULT_KEY) == Constants.APPLY_FRIEND_EACH)
                {
                    //将双方加为好友
                    friendDomain.setMailId(mailMap.get(Constants.ID_KEY));
                    friendDomain.setDealType(Constants.FRIEND_ACCEPT);
                    dealAddFriend();
                    return;
                }
                else if (mailMap.get(Constants.RESULT_KEY) == Constants.APPLY_FRIEND_ALREADY)
                {
                    SocketUtil.responseClient(friendDomain.getCtx(), JSONUtil.getOpSuccess());
                    return;
                }
            }
            
            //向被添加方发送邮件
            MailDomain mailDomain = new MailDomain();
            mailDomain.setRoleId(friendDomain.getId());
            mailDomain.setSendName(friendDomain.getName());
            mailDomain.setTitle(Constants.FRIEND_MAIL_NAME);
            mailDomain.setMailType(Constants.MAIL_FRIEND_TYPE);
            
            //添加邮件
            new ServerMail(mailDomain).sendMailInter();
            SocketUtil.responseClient(friendDomain.getCtx(), JSONUtil.getOpSuccess());
            
            //新增操作日志
            String detail = "成功添加角色："+ friendDomain.getName() +"为好友.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.SUCCESS);

        }
        else
        {
            //如果不满足，立即向客户端响应不满足原因
            //获取响应字符串
            String addFriendResponse = FriendRequestAnalysis.addFriendResponse(map);
            SocketUtil.responseClient(friendDomain.getCtx(), addFriendResponse);
            
            //新增操作日志
            String detail = "添加好友失败，角色："+ friendDomain.getName() +"不满足添加好友条件.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.FAIL);

        }
    }
    
    /**
     * 添加敌人
     */
    private void addEnemy()
    {
        //获取当前角色名的id信息
        long enemy_id = roleInfoService.selectRoleIdByName(friendDomain.getName());
        if (enemy_id == -1)
        {
            //角色名不存在
            Map<String, Long> map = new HashMap<String, Long>();
            map.put(Constants.RESULT_KEY, Constants.ROLE_NOT_EXIT);
            //获取响应字符串
            String addFriendResponse = FriendRequestAnalysis.addFriendResponse(map);
            SocketUtil.responseClient(friendDomain.getCtx(), addFriendResponse);
            //新增操作日志
            String detail = "新增敌人失败，角色：" + friendDomain.getName() + "不存在.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.FAIL);

            return;
        }
        else if (enemy_id == friendDomain.getId())
        {
            //自己添加自己
            Map<String, Long> map = new HashMap<String, Long>();
            map.put(Constants.RESULT_KEY, Constants.ROLE_ADD_MYSELF);
            //获取响应字符串
            String addFriendResponse = FriendRequestAnalysis.addFriendResponse(map);
            SocketUtil.responseClient(friendDomain.getCtx(), addFriendResponse);

            //新增操作日志
            String detail = "新增敌人失败，添加对象为角色本人.";
            OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.FAIL);

            return;
        }
        
        //创建relation实体
        Relation relation = new Relation();
        relation.setRole_left_id(friendDomain.getId());
        relation.setRole_right_id(enemy_id);
        relation.setRelation_type(Constants.ENEMY);
        
        //添加敌人
        Map<String, Long> map = relationInfoService.createEnemy(relation);
        
        //获取响应字符串
        String addEnemyResponse = FriendRequestAnalysis.addEnemyResponse(map);
        SocketUtil.responseClient(friendDomain.getCtx(), addEnemyResponse);
        //新增操作日志
        String detail = "成功添加角色："+ friendDomain.getName() +"为敌人.";
        OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.SUCCESS);

    }
    
    /**
     * 添加好友响应处理结果
     */
    private void dealAddFriend()
    {
        mailInfoService.deleteMail(friendDomain.getMailId());
        //根据类型返回结果
        if (friendDomain.getDealType() == Constants.FRIEND_ACCEPT)
        {
            //接受好友添加请求
            //创建好友实体
            Relation relation = new Relation();
            relation.setRelation_type(Constants.FRIEND);
            relation.setRole_left_id(friendDomain.getId());
            relation.setRole_right_id(friendDomain.getFriendId());
            
            //将好友添加到好友列表
            //向请求方发送系统邮件（如果成功发送，失败将错误信息存放到日志表中）
            try
            {
               relationInfoService.createFriend(relation);
              
            }
            catch (Exception ex)
            {
                //添加错误操作到日志表中
            	toolsService.addNewLogInfo(new Tools(0l, "道具模块", OperateLogUtil.getExceptionStackInfo(ex), 0));
            	 SocketUtil.responseClient(friendDomain.getCtx(), JSONUtil.getBooleanResponse(false));
            }
        }
        SocketUtil.responseClient(friendDomain.getCtx(), JSONUtil.getBooleanResponse(true));
//        //删除请求邮件
//        MailDomain mailDomain = new MailDomain();
//        mailDomain.setCtx(friendDomain.getCtx());
//        mailDomain.setMailId(friendDomain.getMailId());
//        mailDomain.setType(Constants.MAIL_DELETE_TYPE);
//        mailDomain.setRelation_id((Long) map.get(Constants.ID_KEY));
//        new ServerMail(mailDomain).mailAnalysis();
    }
      
    /**
     * 删除好友
     */
    private void deleteFriend()
    {
        //创建relation实体
        Relation relation = new Relation();
        relation.setRole_left_id(friendDomain.getId());
        relation.setRole_right_id(friendDomain.getFriendId());
        
        //添加敌人
        boolean result = relationInfoService.disarmFriendRelationShip(relation);
        
        //获取响应字符串
        String addEnemyResponse = JSONUtil.getBooleanResponse(result);
        SocketUtil.responseClient(friendDomain.getCtx(), addEnemyResponse);
        
        //新增操作日志
        String detail = "成功删除好友.好友id：" + friendDomain.getFriendId();
        OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.SUCCESS);

    }
    
    /**
     * 删除敌人
     */
    private void deleteEnemy()
    {
        //创建relation实体
        Relation relation = new Relation();
        relation.setRole_left_id(friendDomain.getId());
        relation.setRole_right_id(friendDomain.getEnemyId());
        
        //添加敌人
        boolean result = relationInfoService.disarmEnemyRelationShip(relation);
        
        //获取响应字符串
        String addEnemyResponse = JSONUtil.getBooleanResponse(result);
        SocketUtil.responseClient(friendDomain.getCtx(), addEnemyResponse);
        //新增操作日志
        String detail = "成功删除敌人.敌人id：" + friendDomain.getEnemyId();
        OperateLogUtil.insertOperateLog(friendDomain.getId(), detail, OperateLogUtil.FRIEND_MODULE, OperateLogUtil.SUCCESS);

    }
    
    /**
     * 获取好友请求信息查询
     */
    private void getAddFriendRequest()
    {
        //查询当前角色的被申请添加好友请求
        List<Mail> friendListMail = mailInfoService.getFriendMails(friendDomain.getId());
        
        //获取相应字符串
        String friendMailResponse = FriendRequestAnalysis.getFriendMailResponse(friendListMail);
        SocketUtil.responseClient(friendDomain.getCtx(), friendMailResponse);
    }
    
    /**
     * 查询角色详细信息
     */
    private void getDetailInfo()
    {
        //查询详细信息
        Role role = relationInfoService.selectRelationRoleInfo(friendDomain.getId());
        
        //将信息写回客户端
        String detailInfo = FriendRequestAnalysis.getFriendDetailInfo(role);
        SocketUtil.responseClient(friendDomain.getCtx(), detailInfo);
    }
    
    /**
     * 查询单个关系信息
     */
    private void getOneRelation()
    {
        //查询列表信息
        Role role = relationInfoService.getRoleByRelationId(friendDomain.getRelation_id());
        
        //获取响应字符串
        String relationResponse = FriendRequestAnalysis.getOneRelation(role);
        SocketUtil.responseClient(friendDomain.getCtx(), relationResponse);
    }
}
