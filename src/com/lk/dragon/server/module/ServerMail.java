/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerMail.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-22 上午11:32:16
 */
package com.lk.dragon.server.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.MailDomain;
import com.lk.dragon.server.module.analysis.MailRequestAnalysis;
import com.lk.dragon.service.MailInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerMail
{
    /** 邮件实体 **/
    private MailDomain mailDomain;

    @Autowired
    private MailInfoService mailInfoService;
    @Autowired
    private RolePropsInfoService rolePropsInfoService;
    @Autowired
    private SqlToolsService toolsService;
    
    public static final String RESULT_KEY = "result";
    public static final String REASON_KEY = "reason";
    public ServerMail()
    {
    }

    /**
     * 构造函数
     * 
     * @param mailDomain
     */
    public ServerMail(MailDomain mailDomain)
    {
    	this.mailInfoService = SpringBeanUtil.getBean(MailInfoService.class);
    	this.rolePropsInfoService = SpringBeanUtil.getBean(RolePropsInfoService.class);
    	 this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
        this.mailDomain = mailDomain;
    }

    /**
     * 邮件模块内部分析
     */
    public void mailAnalysis()
    {
        switch (mailDomain.getType())
        {
            case Constants.MAIL_LIST_TYPE:
            {
                // 获取邮件列表
                getMailList();
                break;
            }
            case Constants.MAIL_READ_TYPE:
            {
                // 阅读邮件
                readMail();
                break;
            }
            case Constants.MAIL_SEND_TYPE:
            {
                // 发送邮件
                sendMail();
                break;
            }
            case Constants.MAIL_DELETE_TYPE:
            {
                deleteMail();
                break;
            }
            case Constants.MAIL_DELETE_ALL_TYPE:
            {
                //删除某个类型的全部邮件 
                deleteAllmail();
                break;
            }
            case Constants.MAIL_MARK_ALL_TYPE:
            {
                //标记某个类型邮件为已读
                markAllMail();
                break;
            }
            case Constants.MAIL_RECEIVE_ATTACH_TYPE:
            {
                //接收邮件附件
                mailRecevieAttach();
                break;
            }
        }
    }
    
    /**
     * 对外调用发送 邮件接口
     * @return
     */
    public int sendMailInter()
    {
          // 获取邮件实体
        Mail mail = new Mail();
        mail.setMail_from(mailDomain.getRoleId());
        mail.setMail_to_name(mailDomain.getSendName());
        mail.setMail_type(mailDomain.getMailType());
        mail.setMail_title(mailDomain.getTitle());
        mail.setMail_content(mailDomain.getContent());
        mail.setHas_attachments(Constants.MAIL_NOT_HAS_ATTACH);
        if (mailDomain.getAnnexList() != null
                && mailDomain.getAnnexList().size() > 0)
        {
            mail.setHas_attachments(Constants.MAIL_IS_HAS_ATTACH);
            mail.setAttachments(mailDomain.getAnnexList());
        }

        // 发送邮件
        Map<String, Long> map = new HashMap<String,Long>();
        map.put(Constants.RESULT_KEY, -1l);
		try {
			map = mailInfoService.addNewMailByService(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return map.get(Constants.RESULT_KEY).intValue();
    }

    /**
     * 邮件列表查询
     */
    private void getMailList()
    {
        //查询邮件列表
        List<Mail> mailList = mailInfoService.showMailsByCondition(
                mailDomain.getRoleId(), mailDomain.getLAST_MAX_MAIL_ID());
        
        //获取相应字符串
        String mailListResponse = MailRequestAnalysis.getMailListResponse(mailList);
        SocketUtil.responseClient(mailDomain.getCtx(), mailListResponse);
    }

    /**
     * 发送邮件请求
     */
    private void sendMail()
    {
        int result = sendMailInter();

        // 获取发送邮件响应字符串
        String sendMailResponse = "";
        if (result == -2)
        {
            sendMailResponse = JSONUtil.getWrongResponse(Constants.MAIL_SEND_ROLE_NOT_EXIT);
        }
        else if (result == -1)
        {
            sendMailResponse = JSONUtil.getWrongResponse(Constants.NET_ERROR);
        }
        else if (result == 1)
        {
            sendMailResponse = JSONUtil.getBooleanResponse(true);
        }

        SocketUtil.responseClient(mailDomain.getCtx(), sendMailResponse);
    }

    /**
     * 读取邮件
     */
    private void readMail()
    {
        // 读取邮件的详细信息
        Mail mail = mailInfoService.showMailDetailsById(mailDomain
                .getMailId());

        // 获取邮件详细信息回写字符串
        String readMailResponse = MailRequestAnalysis
                .getMailDetailResponse(mail);
        SocketUtil.responseClient(mailDomain.getCtx(), readMailResponse);
    }

    /**
     * 删除邮件
     */
    private void deleteMail()
    {
        // 删除 邮件
        boolean result = mailInfoService.deleteMail(mailDomain
                .getMailId());

        // 获取发送邮件响应字符串
//        String sendMailResponse = JSONUtil.getBooleanResponse(result);
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        if (result)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
            if (mailDomain.getRelation_id() != 0)
            {
                resultObj.put("relation_id", mailDomain.getRelation_id());
            }
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        SocketUtil.responseClient(mailDomain.getCtx(), resultObj.toString());
    }
    
    /**
     * 删除某个类型的全部邮件
     */
    private void deleteAllmail()
    {
        //获取邮件类型
        int mail_type = mailDomain.getMailType();
        //删除邮件
        boolean result = false;
        if (mail_type == 0 || mail_type == -1)
        {
            result = mailInfoService.deleteAllMail(null);
        }
        else
        {
            result = mailInfoService.deleteAllMail(mail_type);
        }
        
        //响应客户端
        SocketUtil.responseClient(mailDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 标记某个类型的全部邮件
     */
    private void markAllMail()
    {
        //获取邮件类型
        int mail_type = mailDomain.getMailType();
        //删除邮件
        boolean result = false;
        if (mail_type == 0 || mail_type == -1)
        {
            result = mailInfoService.markAllMail(null);
        }
        else
        {
            result = mailInfoService.markAllMail(mail_type);
        }
        
        //响应客户端
        SocketUtil.responseClient(mailDomain.getCtx(),
                JSONUtil.getBooleanResponse(result));
    }
    
    /**
     * 接收附件
     */
    @SuppressWarnings("unchecked")
	private void mailRecevieAttach()
    {
        // 获取邮件实体
        Mail mail = new Mail();
        mail.setAttachments(mailDomain.getAnnexList());
        
        //接收附件并响应客户端
        Map<String, Object> result = null;
        int logRes = Constants.LOG_RES_FAIL;
        String title = "领取附件\r\n";
		try {
			result = mailInfoService.receiveAttach(mail, mailDomain.getRoleId());
			
			//响应客户端
			int flag = (Integer) result.get("result");
			if (flag == 0)
			{
				//附件中存在已经被提取的情况
				SocketUtil.responseClient(mailDomain.getCtx(), MailRequestAnalysis.getAttachmentIsPicked());
			}
			else
			{
				//根据包裹id查询道具详细信息
				List<RoleProps> list = new ArrayList<RoleProps>();
				for (long id : (List<Long>) result.get("newIds"))
				{
					if (id != -10 && id != -1)
					{
			            RoleProps roleProps = rolePropsInfoService.getRolePropsById(id);
			            title += "附件入包裹后RolePropsID:"+id+";";
				        list.add(roleProps);
					}
				}
		    	logRes = Constants.LOG_RES_SUCESS;
				//成功提取所有附件
		        SocketUtil.responseClient(mailDomain.getCtx(), MailRequestAnalysis.getRolePropsDetailInfo(list));
			}
	        
		} catch (Exception e) {
			 //返回异常信息
			 SocketUtil.responseClient(mailDomain.getCtx(), JSONUtil.getErrorResponse());
			 e.printStackTrace();
			 title = OperateLogUtil.getExceptionStackInfo(e);
		}
		toolsService.addNewLogInfo(new Tools(mailDomain.getRoleId(), "邮件模块", title, logRes));
    }
}
