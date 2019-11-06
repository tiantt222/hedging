package com.hedging.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.google.common.collect.Maps;
import com.hedging.user.constant.UserStatusConstant;
import com.hedging.user.dao.IUserRoleDao;
import com.hedging.user.entity.User;
import com.hedging.user.entity.UserRole;

/**
 * @author tianwenlong
 *
 */
@Repository
public class UserRoleDao extends BaseDao implements IUserRoleDao {

	private String getConditionalQuery(UserRole userRole, Map<String, Object> params) {
		String queryString = "";

		if (StringUtils.isNotBlank(userRole.getRoleCode())) {
			params.put("roleCode", userRole.getRoleCode());
			queryString += " AND roleCode = :roleCode";
		}
		if (userRole.getUserId() != null && userRole.getUserId() > 0) {
			params.put("userId", userRole.getUserId());
			queryString += " AND userId = :userId";
		}

		queryString = queryString.replaceFirst("AND", "WHERE");
		return queryString;
	}

	@Override
	public Pager<UserRole> search(Pager<UserRole> pager, UserRole userRole) {
		Map<String, Object> params = Maps.newHashMap();
		String queryString = getConditionalQuery(userRole, params);
		String countQueryString = "SELECT count(entity) FROM UserRole entity " + queryString;

		String listQueryString = "SELECT entity FROM UserRole entity " + queryString + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<UserRole> resultList = this.list(UserRole.class, listQueryString, pager.getFirstNum(), pager.getPageSize(),
				params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);
		return pager;
	}

	@Override
	public void deleteByUserId(Long userId) {
		String deleteString = "DELETE FROM UserRole entity WHERE entity.userId = :userId";
		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", userId);
		this.execute(deleteString, params);
	}

	@Override
	public void deleteByIds(List<Long> ids) {
		String deleteString = "DELETE FROM UserRole entity WHERE entity.id IN (:ids)";
		Map<String, Object> params = Maps.newHashMap();
		params.put("ids", ids);
		this.execute(deleteString, params);
	}

	@Override
	public Pager<User> searchUserInfo(Pager<User> pager, String roleCode) {
		String mainSql = " FROM User u";

		String joinSql = " LEFT JOIN UserRole ur";

		String onSql2 = " ON u.id = ur.userId";

		String whereSql = " WHERE ur.roleCode = :roleCode AND u.status > :status";

		Map<String, Object> params = Maps.newHashMap();
		params.put("roleCode", roleCode);
		params.put("status", UserStatusConstant.DELETED);

		String countQueryString = "SELECT count(ui)" + mainSql + joinSql + onSql2 + whereSql;

		if (StringUtils.isBlank(pager.getOrderBy())) {
			pager.setOrderBy("u.createTime");
			pager.setSort("desc");
		}

		String listQueryString = "SELECT u" + mainSql + joinSql + onSql2 + whereSql + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<User> resultList = this.list(User.class, listQueryString, pager.getFirstNum(), pager.getPageSize(), params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);

		return pager;
	}
}
