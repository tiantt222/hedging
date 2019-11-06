package com.hedging.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.google.common.collect.Maps;
import com.hedging.user.constant.UserStatusConstant;
import com.hedging.user.dao.IFunctionDao;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 *
 */
@Repository
public class FunctionDao extends BaseDao implements IFunctionDao {

	@Override
	public Function find(String code) {
		String queryString = "SELECT entity FROM Function entity WHERE code = :code";

		Map<String, Object> params = Maps.newHashMap();
		params.put("code", code);

		return this.find(Function.class, queryString, params);
	}

	private String getConditionalQuery(Function function, Map<String, Object> params) {
		String queryString = "";

		if (StringUtils.isNotBlank(function.getName())) {
			params.put("name", "%" + function.getName() + "%");
			queryString += " AND name LIKE :name";
		}
		if (StringUtils.isNotBlank(function.getCode())) {
			params.put("code", function.getCode());
			queryString += " AND code = :code";
		}

		queryString = queryString.replaceFirst("AND", "WHERE");
		return queryString;
	}

	@Override
	public Pager<Function> search(Pager<Function> pager, Function function) {
		Map<String, Object> params = Maps.newHashMap();
		String queryString = getConditionalQuery(function, params);

		if (pager.getOrderString().equals("") || pager.getOrderString() == null) {
			pager.setOrderBy("createTime");
			pager.setSort("desc");
		}
		String countQueryString = "SELECT count(entity) FROM Function entity" + queryString;
		String listQueryString = "SELECT entity FROM Function entity" + queryString + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<Function> resultList = this.list(Function.class, listQueryString, pager.getFirstNum(), pager.getPageSize(),
				params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);
		return pager;
	}

	@Override
	public Pager<User> searchUser(Pager<User> pager, String functionCode) {
		String mainSql = " FROM User u ";

		String joinSql2 = " LEFT JOIN UserRole ur";

		String onSql2 = " ON  u.id = ur.userId";

		String joinSql3 = " LEFT JOIN RoleFunction rf";

		String onSql3 = " ON  rf.roleCode = ur.roleCode";

		String joinSql4 = " LEFT JOIN Function f";

		String onSql4 = " ON ur.roleCode = f.roleCode";

		String whereSql = " WHERE f.functionCode = :functionCode AND u.status > :status";

		Map<String, Object> params = Maps.newHashMap();
		params.put("functionCode", functionCode);
		params.put("status", UserStatusConstant.DELETED);

		String countQueryString = "SELECT count(ui)" + mainSql + joinSql2 + onSql2 + joinSql3 + onSql3 + joinSql4 + onSql4
				+ whereSql;

		if (StringUtils.isBlank(pager.getOrderBy())) {
			pager.setOrderBy("u.createTime");
			pager.setSort("desc");
		}

		String listQueryString = "SELECT u " + mainSql + joinSql2 + onSql2 + joinSql3 + onSql3 + joinSql4 + onSql4
				+ whereSql + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<User> resultList = this.list(User.class, listQueryString, pager.getFirstNum(), pager.getPageSize(), params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);

		return pager;
	}

}
