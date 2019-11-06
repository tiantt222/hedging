package com.hedging.user.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.dz.platform.system.util.Pager;
import com.hedging.system.exception.BusinessException;
import com.hedging.user.constant.LoginConstant;
import com.hedging.user.constant.UserStatusConstant;
import com.hedging.user.context.UserContextHolder;
import com.hedging.user.dao.IUserDao;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;
import com.hedging.user.service.IUserService;
import com.hedging.user.vo.ResetPasswordVo;
import com.hedging.user.vo.UserLoginVo;

/**
 * @author tianwenlong
 *
 */
@Service
public class UserService implements IUserService {

  @Resource
  private IUserDao userDao;

  /**
   * 登录<br>
   * 用户输入用户名和密码登录
   */
  @Override
  @Transactional(readOnly = true)
  public User login(UserLoginVo userLoginVo) {
    User user = userDao.findByNameAndType(userLoginVo.getUsername(), userLoginVo.getType());
    if (user != null && DigestUtils.md5DigestAsHex(userLoginVo.getPassword().getBytes()).equals(user.getPassword())) {
      return user;
    } else {
      throw new BusinessException("user.error.login");
    }
  }

  /**
   * 登录后处理
   * 
   * @return
   */
  @Override
  @CachePut(value = "userFunctions", key = "#user.id")
  public Set<String> processLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
      User user) {
    // 重新生成sessionid
    session.invalidate();
    session = request.getSession();

    // 保持当前登录用户会话
    session.setAttribute(LoginConstant.SESSION_USER, user);
    UserContextHolder.setCurrentUser(user);

    List<Function> userFunctions = userDao.listFunctionsByUserId(user.getId());
    Set<String> functions = new HashSet<>();
    for (Function f : userFunctions) {
      functions.add(f.getCode());
    }

    // // TODO START 测试用,正式版需要删除
    String[] uFunctions = {"user", "user-accessLog", "user-user", "user-role", "user-func", "system-accessLog", "app",
        "templete"};

    functions.addAll(new HashSet<>(Arrays.asList(uFunctions)));

    return functions;
  }

  /**
   * 获取用户权限功能集
   */
  @Override
  @Cacheable(value = "userFunctions", key = "#user.id")
  public Set<String> listFunctions(User user) {
    return new HashSet<>();
  }

  @Override
  @Transactional
  public User add(User user) {
    if (userDao.findByName(user.getUserName()) != null) {
      throw new BusinessException("user.error.exist");
    }
    User admin = UserContextHolder.getCurrentUser();
    User entity = new User();
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setEmail(user.getEmail());
    entity.setCreateUserId(admin.getId());
    entity.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
    entity.setStatus(UserStatusConstant.ENABLED);
    entity.setTel(user.getTel());
    entity.setType(user.getType());
    entity.setUpdateUserId(admin.getId());
    entity.setUserName(user.getUserName());

    userDao.add(entity);

    return entity;
  }

  @Override
  @Transactional
  public User update(User user) {
    User admin = UserContextHolder.getCurrentUser();

    user.setUpdateTime(new Date());
    user.setEmail(user.getEmail());
    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
    user.setStatus(UserStatusConstant.ENABLED);
    user.setTel(user.getTel());
    user.setUpdateUserId(admin.getId());

    userDao.update(user);

    return user;
  }

  @Override
  @Transactional
  public User updateMyInfo(User user) {
    User entity = UserContextHolder.getCurrentUser();

    entity.setUpdateTime(new Date());
    entity.setEmail(user.getEmail());
    entity.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
    entity.setStatus(UserStatusConstant.ENABLED);
    entity.setTel(user.getTel());
    entity.setUpdateUserId(entity.getId());

    userDao.update(entity);
    return entity;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    User user = this.find(id);
    user.setStatus(UserStatusConstant.DELETED);
    this.userDao.update(user);
  }

  @Override
  public User find(Long userId) {
    return this.userDao.find(User.class, userId);
  }

  @Override
  public User findByUsername(String username) {
    return this.userDao.findByName(username);
  }

  @Override
  @Transactional
  public void modifyPassword(ResetPasswordVo resetPasswordVo) {
    if (!resetPasswordVo.getPassword().equals(resetPasswordVo.getPasswordConfirm())) {
      return;
    }
    User entity = this.find(resetPasswordVo.getUserId());

    entity.setPassword(DigestUtils.md5DigestAsHex(resetPasswordVo.getPassword().getBytes()));

    this.userDao.update(entity);
  }

  @Override
  @Transactional
  public void changeStatus(Long userId, String status) {
    User user = this.find(userId);
    user.setStatus(status);
    this.userDao.update(user);
  }

  @Override
  public Pager<User> search(Pager<User> pager, User user) {
    return this.userDao.search(pager, user);
  }

}
