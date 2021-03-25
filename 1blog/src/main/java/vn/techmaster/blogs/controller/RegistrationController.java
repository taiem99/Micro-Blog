package vn.techmaster.blogs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.exception.UserException;
import vn.techmaster.blogs.request.RegisterRequest;
import vn.techmaster.blogs.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String getRegisterForm(Model model){
        model.addAttribute("register", new RegisterRequest());
        return Route.REGISTRATION;
    }

    @PostMapping("/register")
    public String createNewUser(@ModelAttribute("register") @Valid RegisterRequest request,
        BindingResult result,
        Model model
    ){
        if(result.hasErrors()){
            model.addAttribute("register", new RegisterRequest());
            return Route.REGISTRATION;
        }
        try {
            userService.createNewUser(request);
        } catch (UserException e){
            model.addAttribute("register", new RegisterRequest());
            // result.rejectValue("email", "request.email", "an email already exist");
            return Route.REGISTRATION;
        }
        return Route.REGISTRATION_SUCCESS;
    }
}
