package com.lckclub.service.impl;

import com.lckclub.mapper.RoleMapper;
import com.lckclub.model.Role;
import com.lckclub.model.RolePermission;
import com.lckclub.service.IRolePermissionService;
import com.lckclub.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class RoleService implements IRoleService {

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private IRolePermissionService rolePermissionService;

  @Override
  public Role selectById(Integer roleId) {
    return roleMapper.selectById(roleId);
  }

  @Override
  public List<Role> selectAll() {
    return roleMapper.selectList(null);
  }

  @Override
  public void insert(String name, Integer[] permissionIds) {
    Role role = new Role();
    role.setName(name);
    roleMapper.insert(role);
    insertRolePermissions(role, permissionIds);
  }

  @Override
  public void update(Integer id, String name, Integer[] permissionIds) {
    // 更新role
    Role role = this.selectById(id);
    role.setName(name);
    roleMapper.updateById(role);
    // 删除role permission 的关联关系
    rolePermissionService.deleteByRoleId(id);
    // 重新建立关联关系
    insertRolePermissions(role, permissionIds);
  }

  private void insertRolePermissions(Role role, Integer[] permissionIds) {
    for (Integer permissionId : permissionIds) {
      RolePermission rolePermission = new RolePermission();
      rolePermission.setRoleId(role.getId());
      rolePermission.setPermissionId(permissionId);
      rolePermissionService.insert(rolePermission);
    }
  }

  @Override
  public void delete(Integer id) {
    // 删除关联关系
    rolePermissionService.deleteByRoleId(id);
    // 删除自己
    roleMapper.deleteById(id);
  }
}
