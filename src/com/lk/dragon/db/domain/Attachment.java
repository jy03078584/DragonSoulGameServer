 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Attachment.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-29 下午3:55:22 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:邮件附件表映射
 */
public class Attachment {

	private Long attachment_id;//邮件-附件id
	private Long mail_id;//邮件id
	private Integer props_id;//道具id
	private Integer counts;//物品个数
	private Integer is_pick;//是否已提取 0:否  1:是

	private String attachment_name;//附件名称
	private String attachment_icon;//附件图标
	private String attachment_comment;//附件效果介绍
	private Long role_prop_id;    //角色道具id
	private String extra_info;//额外信息
	public Attachment(){}

	
	
	
	
	
	public String getExtra_info() {
		return extra_info;
	}






	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}






	public Integer getIs_pick() {
		return is_pick;
	}




	public void setIs_pick(Integer is_pick) {
		this.is_pick = is_pick;
	}




	public String getAttachment_name() {
		return attachment_name;
	}



	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}



	public String getAttachment_icon() {
		return attachment_icon;
	}



	public void setAttachment_icon(String attachment_icon) {
		this.attachment_icon = attachment_icon;
	}



	public String getAttachment_comment() {
		return attachment_comment;
	}



	public void setAttachment_comment(String attachment_comment) {
		this.attachment_comment = attachment_comment;
	}



	public Long getAttachment_id() {
		return attachment_id;
	}

	public void setAttachment_id(Long attachment_id) {
		this.attachment_id = attachment_id;
	}

	public Long getMail_id() {
		return mail_id;
	}

	public void setMail_id(Long mail_id) {
		this.mail_id = mail_id;
	}

	public Integer getProps_id() {
		return props_id;
	}

	public void setProps_id(Integer props_id) {
		this.props_id = props_id;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}




    public Long getRole_prop_id()
    {
        return role_prop_id;
    }




    public void setRole_prop_id(Long role_prop_id)
    {
        this.role_prop_id = role_prop_id;
    }
	
	
	
	
}
