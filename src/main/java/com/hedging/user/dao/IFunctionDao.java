package com.hedging.user.dao;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 */
public interface IFunctionDao extends IBaseDao {

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
   * 
   * @param pager
   * @param functionCode
   * @return
   */
  public Pager<User> searchUser(Pager<User> pager, String functionCode);

}
