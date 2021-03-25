package vn.techmaster.blogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.blogs.service.PostService;
import vn.techmaster.blogs.service.UserService;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        
        userService.generateUsersRoles();
        postService.generateSampleData();
    }
    
}
