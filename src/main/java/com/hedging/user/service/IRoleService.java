package com.hedging.user.service;

import java.util.List;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.Role;

/**
 * @author tianwenlong
 */
public interface IRoleService {

  /**
   * 新增
   */
  public Role add(Role role);

  /**
   * 修改
   */
  public Role update(Role role);

  /**
   * 删除
   */
  public void delete(Long id);

  /**
   * 按ID查找
   */
  public Role find(Long id);

  /**
   * 按代码查找
   */
  public Role find(String code);

  /**
   * 按条件查找
   */
  public Pager<Role> search(Pager<Role> pager, Role role);

  /**
   * 查询所有的角色
   */
  public List<Role> list();

}
