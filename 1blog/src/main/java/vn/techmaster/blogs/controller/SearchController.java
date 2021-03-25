package vn.techmaster.blogs.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Post;
import vn.techmaster.blogs.request.SearchRequest;
import vn.techmaster.blogs.service.PostService;
import vn.techmaster.blogs.service.UserService;

@Controller
public class SearchController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public String getSearchForm(Model model, Principal principal){
        UserInfo user = userService.getLoggedUser(principal);
        if(user != null){
            model.addAttribute("user", user);
        }
        model.addAttribute("searchRequest", new SearchRequest());
        return Route.SEARCH;
    }

    @PostMapping("/search")
    public String handleSearch(SearchRequest searchRequest, Model model, Principal principal){
        UserInfo user = userService.getLoggedUser(principal);
        if(user != null){
            model.addAttribute("user", user);
        }
        List<Post> posts = postService.searchPost(searchRequest.getTerm(), 5, 0);
        model.addAttribute("posts", posts);
        return Route.SEARCH_RESULT;
    }

    @GetMapping("/search/index")
    public String reindexFullText(Model model, Principal principal){
        UserInfo user = userService.getLoggedUser(principal);
        if(user != null){
            model.addAttribute("user", user);
        }
        postService.reindexFullText();
        return Route.REDIRECT_HOME;
    }
}
