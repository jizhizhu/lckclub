package com.lckclub.config.realm;

import com.lckclub.model.AdminUser;
import com.lckclub.model.Permission;
import com.lckclub.model.Role;
import com.lckclub.service.IAdminUserService;
import com.lckclub.service.IPermissionService;
import com.lckclub.service.IRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {

  private Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

  @Autowired
  private IAdminUserService adminUserService;
  @Autowired
  private IRoleService roleService;
  @Autowired
  private IPermissionService permissionService;

  // 用户权限配置
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    //访问@RequirePermission注解的url时触发
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    AdminUser adminUser = adminUserService.selectByUsername(principals.toString());
    //获得用户的角色，及权限进行绑定
    Role role = roleService.selectById(adminUser.getRoleId());
    // 其实这里也可以不要权限那个类了，直接用角色这个类来做鉴权，
    // 不过角色包含很多的权限，已经算是大家约定的了，所以下面还是查询权限然后放在AuthorizationInfo里
    simpleAuthorizationInfo.addRole(role.getName());
    // 查询权限
    List<Permission> permissions = permissionService.selectByRoleId(adminUser.getRoleId());
    // 将权限具体值取出来组装成一个权限String的集合
    List<String> permissionValues = permissions.stream().map(Permission::getValue).collect(Collectors.toList());
    // 将权限的String集合添加进AuthorizationInfo里，后面请求鉴权有用
    simpleAuthorizationInfo.addStringPermissions(permissionValues);
    return simpleAuthorizationInfo;
  }

  // 组装用户信息
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String) token.getPrincipal();
    log.info("用户：{} 正在登录...", username);
    AdminUser adminUser = adminUserService.selectByUsername(username);
    // 如果用户不存在，则抛出未知用户的异常
    if (adminUser == null) throw new UnknownAccountException();
    return new SimpleAuthenticationInfo(username, adminUser.getPassword(), getName());
  }

}
