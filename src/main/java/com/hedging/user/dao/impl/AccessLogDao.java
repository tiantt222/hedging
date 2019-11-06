package com.hedging.user.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.google.common.collect.Maps;
import com.hedging.user.dao.IAccessLogDao;
import com.hedging.user.entity.AccessLog;
import com.hedging.user.vo.AccessLogSearchVo;

/**
 * @author tianwenlong
 */
@Repository
public class AccessLogDao extends BaseDao implements IAccessLogDao {

	private String getConditionalQuery(AccessLogSearchVo accessLogSearchVo, Map<String, Object> params) {
		String queryString = "";

		if (StringUtils.isNotBlank(accessLogSearchVo.getUserName())) {
			params.put("userName", "%" + accessLogSearchVo.getUserName() + "%");
			queryString += " AND userName LIKE :userName";
		}
		if (accessLogSearchVo.getIdNumber() != null) {
			params.put("idNumber", accessLogSearchVo.getIdNumber());
			queryString += " AND idNumber = :idNumber";
		}
		if (StringUtils.isNotBlank(accessLogSearchVo.getRemoteIP())) {
			params.put("remoteIP", accessLogSearchVo.getRemoteIP());
			queryString += " AND remoteIP = :remoteIP";
		}
		if (StringUtils.isNotBlank(accessLogSearchVo.getActionName())) {
			params.put("actionName", "%" + accessLogSearchVo.getActionName() + "%");
			queryString += " AND actionName LIKE :actionName";
		}
		if (StringUtils.isNotBlank(accessLogSearchVo.getRequestPath())) {
			params.put("requestPath", "%" + accessLogSearchVo.getRequestPath() + "%");
			queryString += " AND requestPath LIKE :requestPath";
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (accessLogSearchVo.getStartTime() != null) {
				Date startTime = sdf.parse(accessLogSearchVo.getStartTime());
				params.put("startTime", startTime);
				queryString += " AND accessTime >= :startTime";
			}

			if (accessLogSearchVo.getEndTime() != null) {
				Date endTime = sdf.parse(accessLogSearchVo.getEndTime());
				params.put("endTime", endTime);
				queryString += " AND accessTime <= :endTime";
			}
		} catch (ParseException e) {

		}

		queryString = queryString.replaceFirst("AND", "WHERE");
		return queryString;
	}

	@Override
	public Pager<AccessLog> search(Pager<AccessLog> pager, AccessLogSearchVo accessLogSearchVo) {
		Map<String, Object> params = Maps.newHashMap();
		String queryString = getConditionalQuery(accessLogSearchVo, params);

		if (StringUtils.isBlank(pager.getOrderBy())) {
			pager.setOrderBy("accessTime");
			pager.setSort("desc");
		}

		String countQueryString = "SELECT count(entity) FROM AccessLog entity" + queryString;
		String listQueryString = "SELECT entity FROM AccessLog entity" + queryString + pager.getOrderString();

		long size = this.count(countQueryString, params);
		List<AccessLog> resultList = this.list(AccessLog.class, listQueryString, pager.getFirstNum(), pager.getPageSize(),
				params);
		pager.setTotalNum(size);
		pager.setDatas(resultList);

		return pager;
	}

	@Override
	public AccessLog add(AccessLog accessLog) {
		super.add(accessLog);
		return accessLog;
	}

	@Override
	public AccessLog find(Class<AccessLog> findClass, Long id) {
		return super.find(findClass, id);
	}

}
