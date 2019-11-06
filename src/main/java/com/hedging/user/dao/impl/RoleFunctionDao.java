package com.hedging.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.google.common.collect.Maps;
import com.hedging.user.dao.IRoleFunctionDao;
import com.hedging.user.entity.RoleFunction;

/**
 * @author tianwenlong
 *
 */
@Repository
public class RoleFunctionDao extends BaseDao implements IRoleFunctionDao {

	private String getConditionalQuery(RoleFunction roleFunction, Map<String, Object> params) {
		String queryString = "";

		if (StringUtils.isNotBlank(roleFunction.getFunctionCode())) {
			params.put("functionCode", roleFunction.getFunctionCode());
			queryString += " AND functionCode = :functionCode";
		}
		if (StringUtils.isNotBlank(roleFunction.getRoleCode())) {
			params.put("roleCode", roleFunction.getRoleCode());
			queryString += " AND roleCode = :roleCode";
		}

		queryString = queryString.replaceFirst("AND", "WHERE");
		return queryString;
	}

	@Override
	public Pager<RoleFunction> search(Pager<RoleFunction> pager, RoleFunction roleFunction) {
		Map<String, Object> params = Maps.newHashMap();
		String queryString = getConditionalQuery(roleFunction, params);

		String countQueryString = "SELECT count(entity) FROM RoleFunction entity" + queryString;
		String listQueryString = "SELECT entity FROM RoleFunction entity" + queryString + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<RoleFunction> resultList = this.list(RoleFunction.class, listQueryString, pager.getFirstNum(),
				pager.getPageSize(), params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);
		return pager;
	}

	@Override
	public void deleteByRoleCode(String roleCode) {
		String deleteString = "DELETE FROM RoleFunction entity WHERE entity.roleCode = :roleCode";
		Map<String, Object> params = Maps.newHashMap();
		params.put("roleCode", roleCode);
		this.execute(deleteString, params);
	}

	@Override
	public void deleteByIds(List<Long> ids) {
		String deleteString = "DELETE FROM RoleFunction entity WHERE entity.id IN (:ids)";
		Map<String, Object> params = Maps.newHashMap();
		params.put("ids", ids);
		this.execute(deleteString, params);
	}

}
