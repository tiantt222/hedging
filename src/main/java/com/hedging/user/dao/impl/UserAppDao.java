package com.hedging.user.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.google.common.collect.Maps;
import com.hedging.user.dao.IUserAppDao;
import com.hedging.user.entity.UserApp;

/**
 * @author tianwenlong
 *
 */
@Repository
public class UserAppDao extends BaseDao implements IUserAppDao {

	@Override
	public UserApp findByAppId(String appId) {
		String queryString = "SELECT entity FROM UserApp entity WHERE appId = :appId ";

		Map<String, Object> params = Maps.newHashMap();
		params.put("appId", appId);

		return this.find(UserApp.class, queryString, params);
	}

}
