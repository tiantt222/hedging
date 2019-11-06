package com.hedging.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dz.platform.system.dao.impl.BaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.dao.IRoleDao;
import com.hedging.user.entity.Role;

/**
 * @author tianwenlong
 *
 */
@Repository
public class RoleDao extends BaseDao implements IRoleDao {

  @Override
  public Role find(String code) {
    String queryString = "SELECT entity FROM Role entity WHERE entity.code = :code";

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("code", code);

    return this.find(Role.class, queryString, params);
  }

  private String getConditionalQuery(Role role, Map<String, Object> params) {
    String queryString = "";

    if (StringUtils.isNotBlank(role.getName())) {
      params.put("name", "%" + role.getName() + "%");
      queryString += " AND name LIKE :name";
    }
    if (StringUtils.isNotBlank(role.getCode())) {
      params.put("code", role.getCode());
      queryString += " AND code = :code";
    }

    queryString = queryString.replaceFirst("AND", "WHERE");
    return queryString;
  }

  @Override
  public Pager<Role> search(Pager<Role> pager, Role role) {
    Map<String, Object> params = new HashMap<String, Object>();
    String queryString = getConditionalQuery(role, params);

    if (pager.getOrderString().equals("") || pager.getOrderString() == null) {
      pager.setOrderBy("createTime");
      pager.setSort("desc");
    }
    String countQueryString = "SELECT count(entity) FROM Role entity" + queryString;
    String listQueryString = "SELECT entity FROM Role entity" + queryString + pager.getOrderString();

    long size = this.count(countQueryString, params);
    List<Role> resultList = this.list(Role.class, listQueryString, pager.getFirstNum(), pager.getPageSize(), params);
    pager.setTotalNum(size);
    pager.setDatas(resultList);
    return pager;
  }

  @Override
  public Role findByName(String name) {
    String queryString = "SELECT entity FROM Role entity WHERE entity.name = :name";

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", name);

    return this.find(Role.class, queryString, params);
  }

}
