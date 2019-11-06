package com.hedging.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dz.platform.system.util.Pager;
import com.hedging.user.dao.IUserRoleDao;
import com.hedging.user.entity.User;
import com.hedging.user.entity.UserRole;
import com.hedging.user.service.IUserRoleService;
import com.hedging.user.service.IUserService;
import com.hedging.user.vo.UserRoleSaveVo;

/**
 * @author tianwenlong
 *
 */
@Service
public class UserRoleService implements IUserRoleService {

  @Resource
  private IUserRoleDao userRoleDao;

  @Resource
  private IUserService userService;

  @Override
  @Transactional
  public UserRole add(UserRole userRole) {
    UserRole entity = new UserRole();
    entity.setUserId(userRole.getUserId());
    entity.setRoleCode(userRole.getRoleCode());

    this.userRoleDao.add(entity);

    return entity;
  }

  @Override
  @Transactional
  public void add(List<UserRole> userRoleList) {
    for (UserRole userRole : userRoleList) {
      this.add(userRole);
    }
  }

  @Override
  @Transactional
  public UserRole update(UserRole userRole) {
    UserRole entity = this.find(userRole.getId());
    entity.setUserId(userRole.getUserId());
    entity.setRoleCode(userRole.getRoleCode());

    this.userRoleDao.add(entity);

    return entity;
  }

  @Override
  @Transactional
  public void update(List<UserRole> userRoleList) {
    for (UserRole userRole : userRoleList) {
      this.update(userRole);
    }
  }

  @Override
  public UserRole find(Long id) {
    return this.userRoleDao.find(UserRole.class, id);
  }

  @Override
  public Pager<UserRole> search(Pager<UserRole> pager, UserRole userRole) {
    return this.userRoleDao.search(pager, userRole);
  }

  @Override
  public List<UserRole> list() {
    return this.userRoleDao.list(UserRole.class);
  }

  @Override
  @Transactional
  public void save(UserRoleSaveVo userRoleSave) {
    this.userRoleDao.deleteByUserId(userRoleSave.getUserId());
    for (String roleCode : userRoleSave.getRoleCode()) {
      UserRole userRole = new UserRole();
      userRole.setUserId(userRoleSave.getUserId());
      userRole.setRoleCode(roleCode);
      this.add(userRole);
    }
  }

  @Override
  public Pager<User> searchUser(Pager<User> pager, String roleCode) {
    return this.userRoleDao.searchUserInfo(pager, roleCode);
  }

  @Override
  @Transactional
  public void deleteUserRoles(Long userId) {
    userRoleDao.deleteByUserId(userId);
  }

  @Override
  @Transactional
  public void deleteUserRolesI(List<Long> ids) {
    userRoleDao.deleteByIds(ids);
  }

}
