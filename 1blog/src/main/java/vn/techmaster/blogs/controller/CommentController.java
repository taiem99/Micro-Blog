package vn.techmaster.blogs.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.exception.PostException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.request.CommentRequest;
import vn.techmaster.blogs.service.PostService;
import vn.techmaster.blogs.service.UserService;

@Controller
public class CommentController {
    

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping("/comment")
    public String handlePostComment(
        @ModelAttribute CommentRequest commentRequest,
        Principal principal){

            UserInfo user = userService.getLoggedUser(principal);
            if (user != null){
                try{
                    postService.addComment(commentRequest, user.getId());
                } catch (PostException e){
                    e.printStackTrace();
                }
                return "redirect:/posts/" + commentRequest.getPost_id();
            } else {
                return Route.HOME;
            }
    }
}
