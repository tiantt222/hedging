package com.hedging.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dz.platform.system.util.Pager;
import com.hedging.user.dao.IRoleFunctionDao;
import com.hedging.user.entity.RoleFunction;
import com.hedging.user.service.IRoleFunctionService;
import com.hedging.user.vo.RoleFunctionSaveVo;

/**
 * @author tianwenlong
 *
 */
@Service
public class RoleFunctionService implements IRoleFunctionService {

  @Resource
  private IRoleFunctionDao roleFunctionDao;

  @Override
  @Transactional
  public RoleFunction add(RoleFunction roleFunction) {
    RoleFunction entity = new RoleFunction();

    entity.setFunctionCode(roleFunction.getFunctionCode());
    entity.setRoleCode(roleFunction.getRoleCode());

    this.roleFunctionDao.add(entity);
    return entity;
  }

  @Override
  @Transactional
  public void add(List<RoleFunction> roleFunctionList) {
    for (RoleFunction roleFunction : roleFunctionList) {
      this.add(roleFunction);
    }
  }

  @Override
  @Transactional
  public RoleFunction update(RoleFunction roleFunction) {
    RoleFunction entity = this.find(roleFunction.getId());

    entity.setFunctionCode(roleFunction.getFunctionCode());
    entity.setRoleCode(roleFunction.getRoleCode());

    this.roleFunctionDao.update(entity);
    return entity;
  }

  @Override
  @Transactional
  public void update(List<RoleFunction> roleFunctionList) {
    for (RoleFunction roleFunction : roleFunctionList) {
      this.update(roleFunction);
    }
  }

  @Override
  @Transactional
  public void delete(Long id) {
    this.roleFunctionDao.delete(RoleFunction.class, id);

  }

  @Override
  public RoleFunction find(Long id) {
    return this.roleFunctionDao.find(RoleFunction.class, id);
  }

  @Override
  public Pager<RoleFunction> search(Pager<RoleFunction> pager, RoleFunction roleFunction) {
    return this.roleFunctionDao.search(pager, roleFunction);
  }

  @Override
  public List<RoleFunction> list() {
    return this.roleFunctionDao.list(RoleFunction.class);
  }

  @Override
  @Transactional
  public void save(RoleFunctionSaveVo roleFunctionVo) {
    this.roleFunctionDao.deleteByRoleCode(roleFunctionVo.getRoleCode());

    for (int i = 0; i < roleFunctionVo.getFunctionCode().size(); i++) {
      RoleFunction roleFunction = new RoleFunction();
      roleFunction.setRoleCode(roleFunctionVo.getRoleCode());
      roleFunction.setFunctionCode(roleFunctionVo.getFunctionCode().get(i));
      this.add(roleFunction);
    }
  }

  @Override
  @Transactional
  public void deleteRoleFunctions(String roleCode) {
    roleFunctionDao.deleteByRoleCode(roleCode);
  }

  @Override
  @Transactional
  public void deleteRoleFunctionsI(List<Long> ids) {
    roleFunctionDao.deleteByIds(ids);
  }

}
