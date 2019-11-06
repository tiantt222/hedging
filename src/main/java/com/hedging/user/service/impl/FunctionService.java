package com.hedging.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dz.platform.system.util.Pager;
import com.hedging.system.exception.BusinessException;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.dao.IFunctionDao;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.RoleFunction;
import com.hedging.user.entity.User;
import com.hedging.user.service.IFunctionService;
import com.hedging.user.service.IRoleFunctionService;

/**
 * @author tianwenlong
 *
 */
@Service
public class FunctionService implements IFunctionService {

  @Resource
  private IFunctionDao functionDao;

  @Resource
  private IRoleFunctionService roleFunctionService;

  @Override
  @Transactional
  public Function add(Function function) {
    if (this.find(function.getCode()) != null) {
      throw new BusinessException("function.error.codeExist");
    }

    User admin = UserContextHolder.getCurrentUser();
    Function entity = new Function();

    entity.setName(function.getName());
    entity.setDescription(function.getDescription());
    entity.setCode(function.getCode());

    Date date = new Date();
    entity.setCreateUserId(admin.getCreateUserId());
    entity.setCreateTime(date);
    entity.setUpdateUserId(admin.getCreateUserId());
    entity.setUpdateTime(date);

    this.functionDao.add(entity);
    return entity;
  }

  @Override
  public void add(List<Function> functionList) {
    for (Function function : functionList) {
      this.add(function);
    }
  }

  @Override
  @Transactional
  public Function update(Function function) {
    Long userId = UserContextHolder.getCurrentUser().getId();

    Function entity = this.find(function.getId());

    // 修改Code的时候需要更新关联表
    if (!function.getCode().equals(entity.getCode())) {
      if (this.find(function.getCode()) != null) {
        throw new BusinessException("function.error.codeExist");
      }
      RoleFunction roleFunction = new RoleFunction();
      roleFunction.setFunctionCode(entity.getCode());
      Pager<RoleFunction> pager = new Pager<>();
      pager = roleFunctionService.search(pager, roleFunction);

      for (RoleFunction data : pager.getDatas()) {
        data.setFunctionCode(function.getCode());
        roleFunctionService.update(data);
      }

      entity.setCode(function.getCode());
    }
    entity.setName(function.getName());
    entity.setDescription(function.getDescription());
    Date date = new Date();
    entity.setUpdateUserId(userId);
    entity.setUpdateTime(date);

    this.functionDao.update(entity);
    return entity;
  }

  @Override
  @Transactional
  public void update(List<Function> functionList) {
    for (Function function : functionList) {
      this.update(function);
    }
  }

  @Override
  @Transactional
  public void delete(Long id) {
    // RoleFunction关联
    Function entity = this.find(id);
    RoleFunction roleFunction = new RoleFunction();
    roleFunction.setFunctionCode(entity.getCode());
    Pager<RoleFunction> pager = new Pager<>();
    pager = roleFunctionService.search(pager, roleFunction);

    for (RoleFunction data : pager.getDatas()) {
      roleFunctionService.delete(data.getId());
    }

    this.functionDao.delete(Function.class, id);

  }

  @Override
  public Function find(Long id) {
    return this.functionDao.find(Function.class, id);
  }

  @Override
  public Function find(String code) {
    return this.functionDao.find(code);
  }

  @Override
  public Pager<Function> search(Pager<Function> pager, Function function) {
    return this.functionDao.search(pager, function);
  }

  @Override
  public Pager<User> searchUser(Pager<User> pager, String functionCode) {
    return this.functionDao.searchUser(pager, functionCode);
  }

  @Override
  public List<Function> list() {
    return this.functionDao.list(Function.class);
  }

}
