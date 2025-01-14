package com.lckclub.controller.admin;

import com.lckclub.model.AdminUser;
import com.lckclub.service.IAdminUserService;
import com.lckclub.service.IPermissionService;
import com.lckclub.service.IRoleService;
import com.lckclub.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/role")
public class RoleAdminController extends BaseAdminController {

  @Autowired
  private IRoleService roleService;
  @Autowired
  private IPermissionService permissionService;
  @Autowired
  private IAdminUserService adminUserService;

  @RequiresPermissions("role:list")
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("roles", roleService.selectAll());
    return "admin/role/list";
  }

  @RequiresPermissions("role:add")
  @GetMapping("/add")
  public String add(Model model) {
    // 查询所有的权限
    model.addAttribute("permissions", permissionService.selectAll());
    return "admin/role/add";
  }

  @RequiresPermissions("role:add")
  @PostMapping("/add")
  public String save(String name, Integer[] permissionIds) {
    roleService.insert(name, permissionIds);
    return redirect("/admin/role/list");
  }

  @RequiresPermissions("role:edit")
  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    model.addAttribute("role", roleService.selectById(id));
    // 查询当前角色关联的权限
    model.addAttribute("currentPermissions", permissionService.selectByRoleId(id));
    // 查询所有的权限
    model.addAttribute("permissions", permissionService.selectAll());
    return "admin/role/edit";
  }

  @RequiresPermissions("role:edit")
  @PostMapping("/edit")
  public String update(Integer id, String name, Integer[] permissionIds) {
    roleService.update(id, name, permissionIds);
    return redirect("/admin/role/list");
  }

  @RequiresPermissions("role:delete")
  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    // 先查询还有没有用户关联这个角色,其实这个在数据库里已经做过关联了，如果有用户关联了这个角色，也删不掉，数据库会报错
    // 不过这里为了用户体验，加一个验证
    List<AdminUser> adminUsers = adminUserService.selectByRoleId(id);
    if (adminUsers.size() > 0) {
      List<String> usernames = adminUsers.stream().map(AdminUser::getUsername).collect(Collectors.toList());
      String s = StringUtils.collectionToCommaDelimitedString(usernames);
      return error("还有这么多用户关联这个角色：" + s + " 你把它删除，那些用户怎么办？");
    } else {
      roleService.delete(id);
      return success();
    }
  }
}
