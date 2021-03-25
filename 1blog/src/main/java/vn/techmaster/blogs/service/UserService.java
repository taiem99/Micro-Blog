package vn.techmaster.blogs.service;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import vn.techmaster.blogs.exception.UserException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Role;
import vn.techmaster.blogs.model.entity.User;
import vn.techmaster.blogs.request.RegisterRequest;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User createUser(RegisterRequest request) throws UserException;
    void createNewUser(RegisterRequest request) throws UserException;
    void generateUsersRoles();
    UserInfo getLoggedUser(Principal principal);
    List<UserInfo> findAllUser();
    UserInfo findById(Long id);

    List<Role> getRoles();
    
}
