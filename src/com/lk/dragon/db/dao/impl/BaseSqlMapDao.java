/**
 *
 *
 * 文件名称： DaoConfig.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-13 下午3:37:21
 */
package com.lk.dragon.db.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author hex
 * @version 1.0.0
 * @revision
 */
public  class BaseSqlMapDao extends SqlMapClientDaoSupport {

 @Autowired
 private SqlMapClient sqlMapClient;
  
 @PostConstruct
 public void initSqlMapClient(){
  super.setSqlMapClient(sqlMapClient);
} 

 

}

