package com.hedging.user.service;

import java.util.List;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.RoleFunction;
import com.hedging.user.vo.RoleFunctionSaveVo;

/**
 * @author tianwenlong
 */
public interface IRoleFunctionService {

  /**
   * 新增
   */
  public RoleFunction add(RoleFunction roleFunction);

  /**
   * 新增(批量)
   */
  public void add(List<RoleFunction> roleFunctionList);

  /**
   * 修改
   */
  public RoleFunction update(RoleFunction roleFunction);

  /**
   * 修改(批量)
   */
  public void update(List<RoleFunction> roleFunctionList);

  /**
   * 删除
   */
  public void delete(Long id);

  /**
   * 按ID查找
   */
  public RoleFunction find(Long id);

  /**
   * 按条件查找
   */
  public Pager<RoleFunction> search(Pager<RoleFunction> pager, RoleFunction roleFunction);

  /**
   * 查询所有的角色-功能
   */
  public List<RoleFunction> list();

  /**
   * 新增
   */
  public void save(RoleFunctionSaveVo roleFunction);

  /**
   * 删除角色的所有功能
   */
  public void deleteRoleFunctions(String roleCode);

  /**
   * 根据id删除功能
   */
  public void deleteRoleFunctionsI(List<Long> id);

}
