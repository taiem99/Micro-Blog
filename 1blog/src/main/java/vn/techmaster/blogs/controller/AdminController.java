package vn.techmaster.blogs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Role;
import vn.techmaster.blogs.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAdminPage(Model model){
        List<UserInfo> users = userService.findAllUser();
        model.addAttribute("users", users);
        return Route.ADMIN;
    }
    
    @GetMapping("/admin/users/{id}")
    public String showUserDetails(@PathVariable("id") Long user_id, Model model){
        UserInfo user = userService.findById(user_id);
        model.addAttribute("user", user);
        List<Role> roles = userService.getRoles();
        model.addAttribute("roles", roles);
        return Route.USER_DETAILS;
    }

    @PostMapping("/admin/users/save")
    public String setRoleUser(Model model){
        return Route.ADMIN;
    }
}
