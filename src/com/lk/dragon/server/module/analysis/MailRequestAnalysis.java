/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： MailAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:30:46
 */
package com.lk.dragon.server.module.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Attachment;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.server.domain.MailDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class MailRequestAnalysis
{
    /******************** 请求字段 ****************************/
    private static final String ROLE_ID_KEY = "role_id";
    private static final String MAIL_REQUEST_TYPE_KEY = "requestType";
    private static final String MAIL_SEND_NAME_KEY = "send_name";
    private static final String MAIL_ID_KEY = "mail_id";
    private static final String MAIL_TITLE_KEY = "mail_title";
    private static final String MAIL_CONTENT_KEY = "mail_content";
    private static final String MAIL_LAST_MAX_ID_KEY = "mail_last_max_id";

    // 邮件附件
    private static final String MAIL_ANNEX_KEY = "mail_annexs";
    private static final String MAIL_ANNEX_ID_KEY = "mail_annex_id";
    private static final String MAIL_ANNEX_NUM_KEY = "mail_annex_num";
    private static final String ROLE_PROP_ID_KEY = "role_prop_id";

    /******************** 响应 请求字段 *****************************/
    // 响应的关键字段
    private static final String RESULT_KEY = "result";
    private static final String REASON_KEY = "reason";
    // 邮件详细信息
    private static final String MAIL_FROM_KEY = "mail_from";
    private static final String MAIL_KEY = "mails";
    private static final String MAIL_SEND_TIME_KEY = "mail_time";
    private static final String MAIL_IS_READ_KEY = "is_read";
    private static final String MAIL_ATTACHMENT_LEFT_COUNT = "attachment_count";
    private static final String MAIL_HAS_ATTACHMENT_KEY = "mail_has_attachment";
    private static final String MAIL_TYPE_KEY = "mail_type";
    private static final String MAIL_LAST_TIME_KEY = "last_time";
    // 附件
    private static final String ATTACHMENT_KEY = "attachments";
    private static final String ATTACHMENT_NAME_KEY = "attachment_name";
    private static final String ATTACHMENT_ICON_KEY = "attachment_icon";
    private static final String ATTACHMENT_DESCR_KEY = "attachment_descr";
    private static final String ATTACHMENT_ID_KEY = "attachment_id";
    private static final String ATTACHMENT_COUNT_KEY = "count";
    private static final String PROPS_ID_KEY = "props_id";
    private static final String ATTACHMENT_IS_PICK_KEY = "is_pick";
    

	/** 关联表id **/
	private static final String RELATION_KEY = "role_prop_id";
	/** 道具请求类型 **/
	private static final String PROP_TYPE_KEY = "prop_type";
	private static final String PROP_KEY = "props";

    /**
     * 将json字符串转化为邮件相关的实体信息
     * 
     * @param json
     * @return
     */
    public static MailDomain getMailInfo(String json)
    {
        // 创建邮件实体
        MailDomain mailDomain = new MailDomain();

        // 获取请求数据
        JSONObject mailData = JSONUtil.getData(json);
        ;
        // 将信息分解到邮件实体中
        mailDomain.setType(mailData.getInt(MAIL_REQUEST_TYPE_KEY));
        // 设置邮件是否为系统邮件
        mailDomain.setIsSystemMail(Constants.MAIL_NOT_FROM_SYSTEM);

        mailDomain.setRoleId(mailData.has(ROLE_ID_KEY) ? mailData
                .getLong(ROLE_ID_KEY) : 0);
        mailDomain.setSendName(mailData.has(MAIL_SEND_NAME_KEY) ? mailData
                .getString(MAIL_SEND_NAME_KEY) : null);
        mailDomain.setMailId(mailData.has(MAIL_ID_KEY) ? mailData
                .getLong(MAIL_ID_KEY) : 0);
        mailDomain.setTitle(mailData.has(MAIL_TITLE_KEY) ? mailData
                .getString(MAIL_TITLE_KEY) : null);
        mailDomain.setContent(mailData.has(MAIL_CONTENT_KEY) ? mailData
                .getString(MAIL_CONTENT_KEY) : null);
        mailDomain
                .setLAST_MAX_MAIL_ID(mailData.has(MAIL_LAST_MAX_ID_KEY) ? mailData
                        .getLong(MAIL_LAST_MAX_ID_KEY) : 0);
        mailDomain.setMailType(mailData.has(MAIL_TYPE_KEY) ? 
                mailData.getInt(MAIL_TYPE_KEY) : Constants.MAIL_USER_TYPE);

        if (mailData.has(MAIL_ANNEX_KEY))
        {
            // 获取邮件附件
            JSONArray arr = mailData.getJSONArray(MAIL_ANNEX_KEY);
            List<Attachment> annexList = new ArrayList<Attachment>();
            for (int i = 0; i < arr.size(); i++)
            {
                JSONObject annexObj = arr.getJSONObject(i);

                // 创建附件对象
                Attachment attachment = new Attachment();
                attachment.setProps_id(annexObj.getInt(MAIL_ANNEX_ID_KEY));
                attachment.setCounts(annexObj.getInt(MAIL_ANNEX_NUM_KEY));
                attachment.setRole_prop_id(annexObj.has(ROLE_PROP_ID_KEY) ? 
                        annexObj.getLong(ROLE_PROP_ID_KEY) : -1l);
                attachment.setAttachment_id(annexObj.has(ATTACHMENT_ID_KEY) ? 
                        annexObj.getLong(ATTACHMENT_ID_KEY) : -1l);
                annexList.add(attachment);
            }

            mailDomain.setAnnexList(annexList);
        }

        return mailDomain;
    }

    /**
     * 获取详细信息
     * 
     * @param mail
     * @return
     */
    public static String getMailDetailResponse(Mail mail)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();

        if (mail != null)
        {
            // 获取邮件
            resultObj = getMailJSONObj(mail, true);

            // 存放结果
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        } else
        {
            // 存放结果
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }

        return resultObj.toString();
    }

    /**
     * 获取邮件列表连接
     * 
     * @param mailList
     * @return
     */
    public static String getMailListResponse(List<Mail> mailList)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();

        // 获取邮件数组
        JSONArray mailArr = new JSONArray();

        if (mailList != null)
        {
            for (Mail mail : mailList)
            {
                mailArr.add(getMailJSONObj(mail, false));
            }
        }

        resultObj.put(MAIL_KEY, mailArr);

        return resultObj.toString();
    }

    /**
     * 获取一条邮件的json对象
     * 
     * @param mail
     * @return
     */
    private static JSONObject getMailJSONObj(Mail mail, boolean flag)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();

        // 存放邮件信息
        // 将发送时间转化为年月日
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = null;
        try
        {
            createDate = date.parse(mail.getMail_createtime());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        resultObj.put(MAIL_ID_KEY, mail.getMail_id());
        String mailFrom = mail.getMail_from_name();
        resultObj.put("from_id", mail.getMail_from());
        resultObj.put(MAIL_FROM_KEY, mailFrom == null ? "" : mailFrom);
        resultObj.put(MAIL_TITLE_KEY, mail.getMail_title());
        resultObj.put(MAIL_SEND_TIME_KEY, date.format(createDate));
        resultObj.put(MAIL_TYPE_KEY, mail.getMail_type());
        resultObj.put(MAIL_IS_READ_KEY, mail.getMail_is_read());
        resultObj.put(MAIL_HAS_ATTACHMENT_KEY, mail.getHas_attachments());
        resultObj.put(MAIL_ATTACHMENT_LEFT_COUNT,
                mail.getAttachment_picked_count());
        resultObj.put(MAIL_LAST_TIME_KEY, 7 - daysBetween(createDate));

        // 包含详细信息
        if (flag)
        {
            resultObj.put(MAIL_CONTENT_KEY, mail.getMail_content());
            resultObj.put(MAIL_SEND_TIME_KEY, mail.getMail_createtime());

            // 附件信息
            if (mail.getHas_attachments() == Constants.MAIL_IS_HAS_ATTACH)
            {
                List<Attachment> attachList = mail.getAttachments();
                JSONArray attachArr = new JSONArray();
                if (attachList != null && attachList.size() > 0){
                	for (Attachment attach : attachList)
                	{
                		JSONObject attObj = new JSONObject();
                		attObj.put(ATTACHMENT_NAME_KEY, attach.getAttachment_name());
                		attObj.put(ATTACHMENT_ICON_KEY, attach.getAttachment_icon());
                		attObj.put(ATTACHMENT_DESCR_KEY,
                				attach.getAttachment_comment());
                		attObj.put(ATTACHMENT_ID_KEY, attach.getAttachment_id());
                		attObj.put(ATTACHMENT_COUNT_KEY, attach.getCounts());
                		attObj.put(PROPS_ID_KEY, attach.getProps_id());
                		attObj.put(ATTACHMENT_IS_PICK_KEY, attach.getIs_pick());
                		attachArr.add(attObj);
                	}
                }
                resultObj.put(ATTACHMENT_KEY, attachArr);
            }
        }

        return resultObj;
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate)
    {
        long between_days = 0;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            Date bdate = new Date();
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } 
        catch (Exception ex)
        {

        }

        return Integer.parseInt(String.valueOf(between_days));
    }
    
    /**
     * 某个附件已被提取的响应
     * @return
     */
    public static String getAttachmentIsPicked()
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
        //二级区分标志位，表示邮件附件存在已被提取的情况
        resultObj.put("detail", Constants.REQUEST_FAIL);
        
        return resultObj.toString();
    }
    
    /**
     * 获取详细响应信息
     * @return
     */
    public static String getRolePropsDetailInfo(List<RoleProps> propList)
    {
    	// 构造登录结果
		JSONObject resultObj = new JSONObject();
		resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		JSONArray arr = new JSONArray();
		if (propList != null) {
			for (int i = 0; i < propList.size(); i++) {
				JSONObject propObj = new JSONObject();
				RoleProps prop = propList.get(i);
				propObj.put(RELATION_KEY, prop.getRole_props_id());
				propObj.put(PROP_TYPE_KEY, prop.getProps_type());
				arr.add(propObj);
			}
		}
		
		resultObj.put(PROP_KEY, arr);

		return resultObj.toString();
    }
}
