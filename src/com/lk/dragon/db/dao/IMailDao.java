 /** 
 *
 * @Title: IMailDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-28 下午10:23:31 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.Attachment;
import com.lk.dragon.db.domain.Mail;

/**  
 * @Description:邮件表接口信息
 */
public interface IMailDao {
	
	
	
	/**
	 * 批量新增邮件
	 * @param mails
	 * @return
	 */
	public Object addNewMailList(ArrayList<Mail> mails);
	
	/**
	 * 按条件展示邮件列表
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public List<Mail> showMailsByCondition(Map<String,Long> conditonMap)throws Exception;
	
	/**
	 * 显示邮件相信信息
	 * @param mail_id
	 * @return
	 * @throws Exception
	 */
	public Mail showMailDetailsById(long mail_id) throws Exception;
	
	
	public Long getMailSeq()throws Exception;
	/**
	 * 新增一条邮件信息
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public Long addNewMail(Mail mail)throws Exception;
	
	
	/**
	 * 新增邮件(含邮件ID)
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public Object addNewMailIncludeId(Mail mail)throws Exception;
	
	/**
	 * 删除一条邮件
	 * @param mail_id
	 * @return
	 * @throws Exception
	 */
	public int deleteMail(long mail_id)throws Exception;
	
	/**
	 * 新增一条附件信息
	 * @param attachment
	 * @return
	 * @throws Exception
	 */
	public Long addNewAttachment(Attachment attachment)throws Exception;
	
	
	/**
	 * 获取该邮件所有附件
	 * @param mail_id
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> getAttachmentsByMailId(long mail_id)throws Exception;
	
	
	/**
	 * 删除该邮件所有附件
	 * @param mail_id
	 * @return
	 * @throws Exception
	 */
	public int deleteAttachment(long mail_id)throws Exception;
	
	/**
	 * 更改邮件阅读状态
	 * @param mail_id
	 * @return
	 * @throws Exception
	 */
	public int updateIsRead(long mail_id)throws Exception;
	
	/**
	 * 查看是否已经发送过好友申请
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public Object checkIsSendFriendApply(Mail mail)throws Exception;
	
	/**
	 * 查询好友申请邮件列表
	 * @param mail_to
	 * @return
	 * @throws Exception
	 */
	public List<Mail> getFriendMails(long mail_to)throws Exception;
	
	/**
	 * 删除某个类型的邮件
	 * @param mail_type
	 * @return
	 * @throws Exception
	 */
	public int deleteAllMail(Map<String,Integer> conditionMap)throws Exception;
	
	/**
	 * 标记某个类型的邮件为已读
	 * @param mail_type
	 * @return
	 * @throws Exception
	 */
	public int updateAllMail(Map<String,Integer> conditionMap)throws Exception;
	
	/**
	 * 更新邮件是否已被领取
	 * @param attachment_id
	 * @return
	 * @throws Exception
	 */
	public int updateMailRecevie(long attachment_id)throws Exception;
	
	/**
	 * 检测附件是否已被提取
	 * @param attachment_id
	 * @return
	 * @throws Exception
	 */
	public int checkAttachmentIsPick(long attachment_id)throws Exception;
	
	/**
	 * 查询附件额外属性信息
	 * @param attachment_id
	 * @return
	 * @throws Exception
	 */
	public String getAttachExtraInfo(long attachment_id)throws Exception;
}
