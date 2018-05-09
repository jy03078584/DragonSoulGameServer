 /** 
 *
 * @Title: Mail.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-28 下午10:20:07 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:邮件表映射
 */
public class Mail {
	private Long mail_id;//邮件id
	private String mail_title;//邮件标题
	private Long mail_from;//发件人id
	private Long mail_to;//收件人id
	private String mail_content;//邮件主体内容
	private Integer mail_type;//邮件类型
	private Integer mail_is_read;//是否已阅读  0:否  1:是
	private String mail_createtime;//邮件创建时间
	private Integer has_attachments;//是否附带附件  0:否  1：是
	
	private String mail_to_name;//收件人角色名
	
	private String mail_from_name;//发件人角色名
	
	
	private List<Attachment> attachments;//附件列表
	private int attachment_picked_count;  //附件未提取个数
	
	public Mail(){}
	
	
	
	
	
	
	
	
	public String getMail_from_name() {
		return mail_from_name;
	}






	public String getMail_to_name() {
		return mail_to_name;
	}








	public void setMail_to_name(String mail_to_name) {
		this.mail_to_name = mail_to_name;
	}








	public Mail(String mail_title, Long mail_from, Long mail_to,String mail_content, Integer mail_type, Integer has_attachments) {
		this.mail_title = mail_title;
		this.mail_from = mail_from;
		this.mail_to = mail_to;
		this.mail_content = mail_content;
		this.mail_type = mail_type;
		this.has_attachments = has_attachments;
	}





	public void setMail_from_name(String mail_from_name) {
		this.mail_from_name = mail_from_name;
	}







	public int getAttachment_picked_count()
    {
        return attachment_picked_count;
    }







    public void setAttachment_picked_count(int attachment_picked_count)
    {
        this.attachment_picked_count = attachment_picked_count;
    }







    public List<Attachment> getAttachments() {
		return attachments;
	}







	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}







	public Integer getHas_attachments() {
		return has_attachments;
	}





	public void setHas_attachments(Integer has_attachments) {
		this.has_attachments = has_attachments;
	}







	public Long getMail_id() {
		return mail_id;
	}
	public void setMail_id(Long mail_id) {
		this.mail_id = mail_id;
	}
	public String getMail_title() {
		return mail_title;
	}
	public void setMail_title(String mail_title) {
		this.mail_title = mail_title;
	}
	public Long getMail_from() {
		return mail_from;
	}
	public void setMail_from(Long mail_from) {
		this.mail_from = mail_from;
	}
	public Long getMail_to() {
		return mail_to;
	}
	public void setMail_to(Long mail_to) {
		this.mail_to = mail_to;
	}
	public String getMail_content() {
		return mail_content;
	}
	public void setMail_content(String mail_content) {
		this.mail_content = mail_content;
	}
	public Integer getMail_type() {
		return mail_type;
	}
	public void setMail_type(Integer mail_type) {
		this.mail_type = mail_type;
	}
	public Integer getMail_is_read() {
		return mail_is_read;
	}
	public void setMail_is_read(Integer mail_is_read) {
		this.mail_is_read = mail_is_read;
	}
	public String getMail_createtime() {
		return mail_createtime;
	}
	public void setMail_createtime(String mail_createtime) {
		this.mail_createtime = mail_createtime;
	}
	
	
	
}
