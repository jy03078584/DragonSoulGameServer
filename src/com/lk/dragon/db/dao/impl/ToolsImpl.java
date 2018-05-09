/**
 *
 *
 * 文件名称： DealDataDao.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-11-12 下午2:25:16
 */
package com.lk.dragon.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.dao.IToolsDao;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.db.domain.WorldMap;
import com.lk.dragon.util.DateTimeUtil;

@Repository("toolsDao")
public class ToolsImpl extends BaseSqlMapDao implements IToolsDao {




	@Override
	public void doSqlBatch(final List<DaoVo> daoVos) {

		getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)throws SQLException {
				executor.startBatch();

				for (DaoVo daoVo : daoVos) {
					try {
						switch (daoVo.getIbatisSqlType()) {// 1:insert 2:update / 3:delete // 4:queryObject:调用DB的存储过程
						case 1:
							executor.insert(daoVo.getIbatisSqlId(),
									daoVo.getParamObj());
							break;
						case 2:
							executor.update(daoVo.getIbatisSqlId(),daoVo.getParamObj());
							
							break;
						case 3:
							executor.delete(daoVo.getIbatisSqlId(),
									daoVo.getParamObj());
							break;
						case 4:
							executor.queryForObject(daoVo.getIbatisSqlId(),
									daoVo.getParamObj());
						default:
							break;
						}
					} catch (Exception e) {
						logger.error(DateTimeUtil.getNowTimeByFormat()
								+ "  WarService batch ERROR>>>>>>"
								+ daoVo.getIbatisSqlId() + " || "
								+ daoVo.getParamObj() + "====>"
								+ e.getMessage());
					}

				}

				executor.executeBatch();
				return null;
			}
		});
	}

}
