package com.hedging.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.google.common.collect.Maps;
import com.hedging.user.dao.IUserDao;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 *
 */
@Repository
public class UserDao extends BaseDao implements IUserDao {

	@Override
	public User findByName(String name) {
		String queryString = "SELECT entity FROM User entity WHERE entity.userName = :userName AND entity.status = 1";
		Map<String, Object> params = Maps.newHashMap();
		params.put("userName", name);

		return this.find(User.class, queryString, params);
	}

	@Override
	public User findByNameAndType(String name, String type) {
		String queryString = "";
		Map<String, Object> params = Maps.newHashMap();

		queryString = "SELECT entity FROM User entity WHERE "
				.concat("entity.userName = :userName AND entity.status = 1 AND entity.type= :type");
		params.put("userName", name);
		params.put("type", type);

		return this.find(User.class, queryString, params);
	}

	@Override
	public List<Function> listFunctionsByUserId(Long userId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", userId);

		String sql = "SELECT f FROM Function f " + "INNER JOIN RoleFunction rf ON rf.functionCode = f.code "
				+ "INNER JOIN UserRole ur ON rf.roleCode = ur.roleCode "
				+ "INNER JOIN User u on u.id = ur.userId WHERE u.id = :userId";

		return this.list(Function.class, sql, params);
	}

	@Override
	public Pager<User> search(Pager<User> pager, User user) {
		Map<String, Object> params = Maps.newHashMap();
		String queryString = getConditionalQuery(user, params);

		if (pager.getOrderString().equals("") || pager.getOrderString() == null) {
			pager.setOrderBy("createTime");
			pager.setSort("desc");
		}
		String countQueryString = "SELECT count(entity) FROM User entity " + queryString;

		String listQueryString = "SELECT entity FROM User entity " + queryString + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<User> resultList = this.list(User.class, listQueryString, pager.getFirstNum(), pager.getPageSize(), params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);
		return pager;
	}

	private String getConditionalQuery(User userSearchVo, Map<String, Object> params) {
		String queryString = "";

		if (userSearchVo.getStatus() != null) {
			params.put("status", userSearchVo.getStatus());
			queryString += " AND status IN (:status) ";
		} else {
			queryString += " AND status IN (1, 2) ";
		}
		if (StringUtils.isNotBlank(userSearchVo.getUserName())) {
			params.put("username", "%" + userSearchVo.getUserName() + "%");
			queryString += " AND username LIKE :username";
		}

		queryString = queryString.replaceFirst("AND", "WHERE");
		return queryString;
	}

}
