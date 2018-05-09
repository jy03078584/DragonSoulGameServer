 /** 
 *
 * @Title: MailDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-28 下午10:24:28 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.domain.Attachment;
import com.lk.dragon.db.domain.Mail;

/**  
 * @Description:
 */
@Repository("mailDao")
public class MailDaoImpl extends BaseSqlMapDao implements IMailDao {


	private final static String MAP_NAME = "mailMap";
	
	public String getMapName(){
		return MAP_NAME;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mail> showMailsByCondition(Map<String,Long> conditonMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("mailMap.showMailsByCondition", conditonMap);
	}

	@Override
	public Mail showMailDetailsById(long mail_id) throws Exception {
		return (Mail) getSqlMapClientTemplate().queryForObject("mailMap.showMailDetailsById", mail_id);
	}

	@Override
	public Long addNewMail(Mail mail) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("mailMap.addNewMail", mail);
	}

	@Override
	public int deleteMail(long mail_id) throws Exception {
		return getSqlMapClientTemplate().delete("mailMap.deleteMail", mail_id);
	}

	@Override
	public Long addNewAttachment(Attachment attachment) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("mailMap.addNewAttachment", attachment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> getAttachmentsByMailId(long mail_id)throws Exception {
		return getSqlMapClientTemplate().queryForList("mailMap.getAttachmentsByMailId", mail_id);
	}

	@Override
	public int deleteAttachment(long mail_id) throws Exception {
		return getSqlMapClientTemplate().delete("mailMap.deleteAttachment", mail_id);
	}

	@Override
	public int updateIsRead(long mail_id) throws Exception {
		return getSqlMapClientTemplate().update("mailMap.updateIsRead", mail_id);
	}

	@Override
	public Object checkIsSendFriendApply(Mail mail) throws Exception {
		return getSqlMapClientTemplate().queryForObject("mailMap.checkIsSendFriendApply", mail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mail> getFriendMails(long mail_to) throws Exception {
		return getSqlMapClientTemplate().queryForList("mailMap.getFriendMails", mail_to);
	}

    @Override
    public int deleteAllMail(Map<String,Integer> conditionMap) throws Exception
    {
        return getSqlMapClientTemplate().delete("mailMap.deleteAllMail", conditionMap);
    }

    @Override
    public int updateAllMail(Map<String,Integer> conditionMap) throws Exception
    {
        return getSqlMapClientTemplate().update("mailMap.updateAllIsRead", conditionMap);
    }

    @Override
    public int updateMailRecevie(long attachment_id) throws Exception
    {
        return getSqlMapClientTemplate().update("mailMap.updateAttachHasReceive", attachment_id);
    }

	@Override
	public int checkAttachmentIsPick(long attachment_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("mailMap.checkAttachmentIsPick", attachment_id);
	}

	@Override
	public String getAttachExtraInfo(long attachment_id) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("mailMap.getAttachExtraInfo", attachment_id);
	}

	@Override
	public Object addNewMailList(ArrayList<Mail> mails) {
		if(mails == null || mails.size() <= 0 )
			return null;
		return getSqlMapClientTemplate().insert("mailMap.addNewMailList", mails);
	}

	@Override
	public Object addNewMailIncludeId(Mail mail) throws Exception {
		if(mail.getMail_id() == null)
			return null;
		getSqlMapClientTemplate().insert("mailMap.addNewMailIncludeId", mail);
		return null;
	}

	@Override
	public Long getMailSeq() throws Exception {
		return (Long) getSqlMapClientTemplate().queryForObject("mailMap.getMailSeq");
	}

	
}
