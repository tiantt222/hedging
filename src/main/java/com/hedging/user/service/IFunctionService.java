package com.hedging.user.service;

import java.util.List;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 */
public interface IFunctionService {

  /**
   * 新增
   */
  public Function add(Function function);

  /**
   * 新增(批量)
   */
  public void add(List<Function> functionList);

  /**
   * 修改
   */
  public Function update(Function function);

  /**
   * 修改(批量)
   */
  public void update(List<Function> functionList);

  /**
   * 删除
   */
  public void delete(Long id);

  /**
   * 按ID查找
   */
  public Function find(Long id);

  /**
   * 按代码查找
   */
  public Function find(String code);

  /**
   * 按条件查找
   */
  public Pager<Function> search(Pager<Function> pager, Function function);

  /**
   * 分页查询用户信息-角色
   */
  public Pager<User> searchUser(Pager<User> pager, String functionCode);

  /**
   * 查询所有的功能
   */
  public List<Function> list();

}
