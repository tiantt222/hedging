package com.hedging.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dz.platform.system.util.Pager;
import com.hedging.system.exception.BusinessException;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.dao.IRoleDao;
import com.hedging.user.entity.Role;
import com.hedging.user.entity.RoleFunction;
import com.hedging.user.service.IRoleFunctionService;
import com.hedging.user.service.IRoleService;

/**
 * @author tianwenlong
 *
 */
@Service
public class RoleService implements IRoleService {

  @Resource
  private IRoleDao roleDao;

  @Resource
  private IRoleFunctionService roleFunctionService;

  @Override
  @Transactional
  public Role add(Role role) {
    if (roleDao.findByName(role.getName()) != null) {
      throw new BusinessException("role.error.nameExist");
    }
    if (this.find(role.getCode()) != null) {
      throw new BusinessException("role.error.codeExist");
    }
    Long userId = UserContextHolder.getCurrentUser().getId();

    Role entity = new Role();

    entity.setName(role.getName());
    entity.setCode(role.getCode());
    entity.setDescription(role.getDescription());

    Date date = new Date();
    entity.setCreateUserId(userId);
    entity.setCreateTime(date);
    entity.setUpdateUserId(userId);
    entity.setUpdateTime(date);

    this.roleDao.add(entity);
    return entity;
  }

  @Override
  @Transactional
  public Role update(Role role) {
    Long userId = UserContextHolder.getCurrentUser().getId();

    Role entity = this.find(role.getId());
    if (!role.getName().equals(entity.getName())) {
      if (roleDao.findByName(role.getName()) != null) {
        throw new BusinessException("role.error.nameExist");
      }
    }
    // 修改Code的时候需要更新关联表
    if (!role.getCode().equals(entity.getCode())) {
      if (this.find(role.getCode()) != null) {
        throw new BusinessException("role.error.codeExist");
      }
      RoleFunction roleFunction = new RoleFunction();
      roleFunction.setRoleCode(entity.getCode());
      Pager<RoleFunction> pager = new Pager<>();
      pager = roleFunctionService.search(pager, roleFunction);

      for (RoleFunction data : pager.getDatas()) {
        data.setFunctionCode(role.getCode());
        roleFunctionService.update(data);
      }

      entity.setCode(role.getCode());
    }
    entity.setName(role.getName());
    entity.setDescription(role.getDescription());
    Date date = new Date();
    entity.setUpdateUserId(userId);
    entity.setUpdateTime(date);

    this.roleDao.update(entity);
    return entity;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    this.roleDao.delete(Role.class, id);
  }

  @Override
  public Role find(Long id) {
    return this.roleDao.find(Role.class, id);
  }

  @Override
  public Role find(String code) {
    return this.roleDao.find(code);
  }

  @Override
  public Pager<Role> search(Pager<Role> pager, Role role) {
    return this.roleDao.search(pager, role);
  }

  @Override
  public List<Role> list() {
    return this.roleDao.list(Role.class);
  }

}
