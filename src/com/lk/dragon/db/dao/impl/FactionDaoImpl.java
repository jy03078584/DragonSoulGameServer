 /** 
 *
 * @Title: FactionDaoImpl.java 
 * @Package com.lk.dragon.db.dao.impl 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-24 下午3:41:04 
 * @version V1.0   
 */
package com.lk.dragon.db.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.dao.client.DaoManager;
import com.lk.dragon.db.dao.IFactionDao;
import com.lk.dragon.db.domain.Faction;
import com.lk.dragon.db.domain.RoleFaction;

/**  
 * @Description:
 */
@Repository("factionDao")
public class FactionDaoImpl extends BaseSqlMapDao implements IFactionDao {

	@Override
	public long createFaction(Faction faction) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("factionMap.createFaction", faction);
	}

	@Override
	public long addRoleFaction(Map<String, Object> map) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("factionMap.addRoleFaction", map);
	}

	@Override
	public List<RoleFaction> getFactionRoles(String condition)throws Exception {
		return getSqlMapClientTemplate().queryForList("factionMap.getFactionRoles", condition);
	}

	@Override
	public List<Faction> getFactionsByCondition(String condition)throws Exception {
		return getSqlMapClientTemplate().queryForList("factionMap.getFactionsByCondition", condition);
	}

	@Override
	public Object applyUnionFaction(Map<String, Long> map) throws Exception {
		return getSqlMapClientTemplate().insert("factionMap.applyUnionFaction", map);
	}

	@Override
	public List<Faction> selectApplyFactionInfo(Map<String, Long> map)throws Exception {
		return getSqlMapClientTemplate().queryForList("factionMap.selectApplyFactionInfo", map);
	}

	@Override
	public int deleteApplyFactionInfo(String condition) throws Exception {
		return getSqlMapClientTemplate().delete("factionMap.deleteApplyFactionInfo", condition);
	}

	@Override
	public Faction selectFactionRight(long role_id) throws Exception {
		return (Faction) getSqlMapClientTemplate().queryForObject("factionMap.selectFactionRight", role_id);
	}

	@Override
	public int deleteRoleFaction(long role_id) throws Exception {
		return getSqlMapClientTemplate().delete("factionMap.deleteRoleFaction", role_id);
	}

	@Override
	public int updateFactionInfo(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("factionMap.updateFactionInfo", map);
	}

	@Override
	public int updateRoleFactionInfo(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("factionMap.updateRoleFactionInfo", map);
	}


	@Override
    public long addGuildPosition(Map<String, Object> map) throws Exception
    {
	    return (Long) getSqlMapClientTemplate().insert("factionMap.upRoleFactionPosition", map);
    }

    @Override
    public int editGuildPosition(Map<String, Object> map) throws Exception
    {
        return getSqlMapClientTemplate().update("factionMap.updateFactionPosition", map);
    }

    @Override
    public int deleGuildPosition(long position_id) throws Exception
    {
        return getSqlMapClientTemplate().delete("factionMap.deleteFactionPosition", position_id);
    }

    @Override
	public int selectRoleFactionRight(long role_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("factionMap.selectRoleFactionRight", role_id);
	}

	@Override
	public int deleteFactionPosition(long role_id) throws Exception {
		return getSqlMapClientTemplate().delete("factionMap.deleteFactionPosition", role_id);
	}

	@Override
	public int updateFactionPosition(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().update("factionMap.updateFactionPosition", map);
	}

	@Override
	public long addFactionLog(Map<String, Object> map) throws Exception {
		return (Long) getSqlMapClientTemplate().insert("factionMap.addFactionLog", map);
	}

	@Override
	public List<Faction> selectFactionLog(long faction_id) throws Exception {
		return getSqlMapClientTemplate().queryForList("factionMap.selectFactionLog", faction_id);
	}

	@Override
	public int deleteFaction(long faction_id) throws Exception {
		return getSqlMapClientTemplate().delete("factionMap.deleteFaction", faction_id);
	}


	@Override
	public int checkIsSameFaction(String condition) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("factionMap.checkIsSameFaction", condition);
	}


    @Override
    public Faction getPositionUseInfo(Map<String, Object> map) throws Exception
    {
        return (Faction)getSqlMapClientTemplate().queryForList("factionMap.selectFactionPositionUse", map).get(0);
    }

    @Override
    public List<Faction> getPositionList(long faction_id) throws Exception
    {
        return getSqlMapClientTemplate().queryForList("factionMap.selectPositionList", faction_id);
    }

	@Override
	public int checkAlreadyFaction(long role_id) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("factionMap.checkAlreadyFaction", role_id);
	}

	@Override
	public long getFactionsApplyKeyId() {
		return (Long) getSqlMapClientTemplate().queryForObject("factionMap.getFactionsApplyKeyId");
	}

}
