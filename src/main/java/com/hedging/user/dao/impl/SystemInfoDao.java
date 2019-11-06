package com.hedging.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.google.common.collect.Maps;
import com.hedging.user.dao.ISystemInfoDao;
import com.hedging.user.entity.SystemInfo;

/**
 * 
 * @author Zhao JinMing
 *
 */
@Repository
public class SystemInfoDao extends BaseDao implements ISystemInfoDao {

	@Override
	public String fetchCurrentVersion() {
		String sqlQueryMax = "SELECT MAX(systemVersion) FROM SystemInfo";
		return (String) this.findBySql(sqlQueryMax);
	}

	@Override
	public SystemInfo findBySystemVersion(String systemVersion) {
		String sqlQuery = "SELECT entity FROM SystemInfo entity WHERE systemVersion = :systemVersion";
		Map<String, Object> params = Maps.newHashMap();
		params.put("systemVersion", systemVersion);
		return this.find(SystemInfo.class, sqlQuery, params);
	}

	@Override
	public List<SystemInfo> getAllSort() {
		String sqlQuery = "SELECT entity FROM SystemInfo entity ORDER BY systemVersion DESC";
		return this.list(SystemInfo.class, sqlQuery);
	}

}
