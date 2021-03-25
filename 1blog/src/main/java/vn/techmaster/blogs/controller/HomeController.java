package vn.techmaster.blogs.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Post;

import vn.techmaster.blogs.service.PostService;
import vn.techmaster.blogs.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @GetMapping(value = {"/", "/{page}"})
    public String getHome(@PathVariable(value = "page", required = false) Integer page, Model model, Principal principal){ 
       
        if(principal != null){
            UserInfo user = userService.getLoggedUser(principal);
            model.addAttribute("user", user);
        }   
        if(page == null){
            page = 0;
        } 
        Page<Post> pagePosts = postService.findAllPaging(page, 8);
        List<Post> posts = pagePosts.getContent();
        model.addAttribute("posts", posts);
        List<Paging> pagings = Paging.generatePage(page, pagePosts.getTotalPages());
        model.addAttribute("pagings", pagings);
        return Route.HOME;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        return Route.LOGIN_TEMPLATE;
      
    }

   
    @GetMapping("/login_success")
    public String loginSuccess(Principal principal, Model model){
   
        UserInfo user = userService.getLoggedUser(principal);
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.getAllPostsByUserId(user.getId()));
        return Route.REDIRECT_POSTS;
    }

    @GetMapping("/login_error")
    public String loginError(){
        return "login_error";
    }

    @GetMapping("/logout_success")
    public String logout_success(){
        return "logout";
    }

}
