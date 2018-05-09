/** 
 *
 * @Title: MailInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-29 上午10:44:23 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IGamePropsDao;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.domain.Attachment;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.util.Constants;

/**
 * @Description:邮件业务层
 */
@Service
public class MailInfoService {

	@Autowired
	private IMailDao mailDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IRolePropsDao rolePropDao;
	@Autowired
	private IGamePropsDao gamePropsDao;

	

	public MailInfoService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 查询最新邮件
	 * 
	 * @param mail_to
	 *            收件人ID
	 * @param last_mail_id
	 *            最近一次邮件ID
	 * @return
	 */
	public List<Mail> showMailsByCondition(long mail_to, long last_mail_id) {
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put("mail_to", mail_to);
		map.put("last_mail_id", last_mail_id);
		List<Mail> mails = null;
		try {
			mails = mailDao.showMailsByCondition(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mails;
	}

	/**
	 * 查看邮件详细信息
	 * 
	 * @param mail_id
	 * @return
	 */
	public Mail showMailDetailsById(long mail_id) {
		Mail mail = null;
		try {
			mail = mailDao.showMailDetailsById(mail_id);
			if (mail != null && mail.getHas_attachments() == 1) {// 该邮件具有附件
				
				//获取未被接收的附件
				List<Attachment> attachments = mailDao.getAttachmentsByMailId(mail_id);
				mail.setAttachments(attachments);
			}
			SqlToolsService.sqlQueue.put(new DaoVo("mailMap.updateIsRead", mail_id, 2));
		//	mailDao.updateIsRead(mail_id);// 修改邮件阅读状态
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mail;
	}

	/**
	 * 新邮件入库
	 * 
	 * @param mail
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, Long> addNewMailByService(Mail mail) throws Exception {
		HashMap<String, Long> resultMap = new HashMap<String, Long>();
		if (mail.getMail_to_name() != null
				&& !mail.getMail_to_name().equals("")) {
			Long mail_to_id;
			try {
				mail_to_id = (Long) roleDao.selectRoleIdByName(mail
						.getMail_to_name());
				if (mail_to_id == null) {
					resultMap.put(Constants.RESULT_KEY,
							Constants.MAIL_TONAME_NOFOUND);
					return resultMap;
				}
				mail.setMail_to(mail_to_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (mail.getHas_attachments() == 1 && mail.getAttachments().size() > 0) {// 该邮件具有附件
				// 邮件主体入库
				long mail_id = mailDao.addNewMail(mail);
				List<Attachment> attahcments = mail.getAttachments();
				// 邮件附件入库
				for (Attachment attachment : attahcments) {
					
					
					long rolePropsId = attachment.getRole_prop_id();
					String extarInfo = attachment.getExtra_info() == null ? "" : attachment.getExtra_info();
					// 将角色道具删除
					if ( rolePropsId > 0) {//玩家间相互邮件
						
						long role_props_id = attachment.getRole_prop_id();
						int attType = gamePropsDao.getPropsInfo(attachment.getProps_id()).getProps_type();
						int count = 0;
						
						//查看附件类型 
						if(attType == Constants.PROP_EQUIP){
							//装备类道具 获取装备属性值
							extarInfo = rolePropDao.getEquipProperty(role_props_id).getInc_property();
							//解除装备属性值 与 邮件发送人关系
							rolePropDao.deleteEquipValue(role_props_id);
							count = 0 ;
						}else{
							count = rolePropDao.getPropsCount(role_props_id) - attachment.getCounts();
						}
						
						if (count <= 0) {
							rolePropDao.deleteProps(role_props_id);
						} else {
							RoleProps prop = new RoleProps();
							prop.setRole_props_id(role_props_id);
							prop.setProps_count(count);
							rolePropDao.updatePropsCount(prop);
						}
					}
					attachment.setExtra_info(extarInfo);
					attachment.setMail_id(mail_id);
					mailDao.addNewAttachment(attachment);
				}
				resultMap.put(Constants.RESULT_KEY, 1l);
		} else {// 无附件邮件 has_attachments = 0
				mailDao.addNewMail(mail);
				resultMap.put(Constants.RESULT_KEY, 1l);
		}

		return resultMap;
	}

	/**
	 * 删除邮件
	 * 
	 * @param mail_id
	 * @return
	 */
	public boolean deleteMail(long mail_id) {
		boolean flag = false;
		try {
			//mailDao.deleteMail(mail_id);// 只删除邮件主体信息 数据库触发器自动删除附件
			
			SqlToolsService.sqlQueue.put(new DaoVo("mailMap.deleteMail", mail_id, 3));
			
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 是否已向对方发送过好友申请
	 * 
	 * @param mail
	 *            5: 双方互相申请好友 6:已在对方申请列表中
	 * @return
	 */
	public HashMap<String, Long> checkIsSendFriendApply(Mail mail) {
		HashMap<String, Long> result = new HashMap<String, Long>();
		Object mailO = null;
		try {
			Mail mailTemp = new Mail();
			mailTemp.setMail_from(mail.getMail_to());
			mailTemp.setMail_to(mail.getMail_from());
			mailO = mailDao.checkIsSendFriendApply(mailTemp);
			if (mailO != null) {
				result.put(Constants.RESULT_KEY, Constants.APPLY_FRIEND_EACH);// 双方互相申请好友
				result.put(Constants.ID_KEY,
						(Long) mailDao.checkIsSendFriendApply(mailTemp));
				return result;
			}
			mailO = mailDao.checkIsSendFriendApply(mail);
			if (mailO != null) {
				result.put(Constants.RESULT_KEY, Constants.APPLY_FRIEND_ALREADY);// 6:已在对方申请列表中
				result.put(Constants.ID_KEY,
						(Long) mailDao.checkIsSendFriendApply(mail));
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询好友申请邮件列表
	 * 
	 * @param mail_to
	 * @return
	 */
	public List<Mail> getFriendMails(long mail_to) {
		List<Mail> mails = null;
		try {
			mails = mailDao.getFriendMails(mail_to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mails;
	}

	/**
	 * 标记所有邮件为已读状态
	 * 
	 * @param mail_type
	 * @return
	 */
	public boolean markAllMail(Integer mail_type) {
		boolean flag = false;
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("mail_type", mail_type);
			mailDao.updateAllMail(map);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除某个类型的邮件
	 * 
	 * @param mail_type
	 * @return
	 */
	public boolean deleteAllMail(Integer mail_type) {
		boolean flag = false;
		try {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("mail_type", mail_type);
			mailDao.deleteAllMail(map);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 接收邮件附件
	 * 
	 * @param mail
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, Object> receiveAttach(Mail mail, long role_id) throws Exception {
		int resFlag = -1;
		List<Long> propsIds = new ArrayList<Long>();
		
			// 邮件主体入库
			List<Attachment> attahcments = mail.getAttachments();
			// 邮件附件入库
			for (Attachment attachment : attahcments) {
				//判定当前附件是否已被提取 1--已被提取 0--未被提取
				if (mailDao.checkAttachmentIsPick(attachment.getAttachment_id()) == 1)
				{
					resFlag = 0;
				}
				else
				{
					RoleProps roleProps = new RoleProps(role_id, attachment.getProps_id(), attachment.getCounts());
					
					// 更新当前邮件附件为已领取状态
					mailDao.updateMailRecevie(attachment.getAttachment_id());
					
					//获取邮件附件额外属性
					String extraInfo = mailDao.getAttachExtraInfo(attachment.getAttachment_id());
					roleProps.setExtra_info(extraInfo);
						
					// 将道具放入用户包裹中
					long result = rolePropDao.callAddRoleProps(roleProps);// 附件进入玩家包裹
					if (result == -1)
					{
						throw new Exception();
					}
					
					result = resFlag == 0 ? 0 : result;
					propsIds.add(result);
				}
			}

			//返回结果
		HashMap<String, Object> map = new HashMap<String, Object>();
		//状态
		map.put("result", resFlag);
		//成功id序列
		map.put("newIds", propsIds);
		return map;
	}
}
