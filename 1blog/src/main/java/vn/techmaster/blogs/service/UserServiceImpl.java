package vn.techmaster.blogs.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.techmaster.blogs.exception.UserException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Role;
import vn.techmaster.blogs.model.entity.User;
import vn.techmaster.blogs.model.mapper.UserMapper;
import vn.techmaster.blogs.reponsitory.RoleRepository;
import vn.techmaster.blogs.reponsitory.UserRepository;
import vn.techmaster.blogs.request.RegisterRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var oUser = userRepo.findByEmail(username);
        if(!oUser.isPresent()){
            throw new UsernameNotFoundException("Not found User");
        } 
        return oUser.get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).get();
    }

    @Override
    public User createUser(RegisterRequest request) throws UserException{
        Optional<User> oUser = userRepo.findByEmail(request.getEmail());
        if(!oUser.isPresent()){
            User user = new User(request.getFullname(), request.getEmail(), encoder.encode(request.getPassword()));
            return userRepo.save(user);
        } else {
            throw new UserException(request.getEmail() + "already exist");
        }

    }
    
    @Override
    @Transactional
    public void generateUsersRoles() {
        Role roleAdmin = new Role("ADMIN");
      
        Role roleEditor = new Role("EDITOR");
        Role roleAuthor = new Role("AUTHOR");

        roleRepo.save(roleAdmin);
        roleRepo.save(roleAuthor);
        roleRepo.save(roleEditor);
        roleRepo.flush();

        User admin = createUser(new RegisterRequest("admin", "admin@gmail.com", "123"));
        admin.getRoles().add(roleAdmin);
        userRepo.save(admin);

        User anh = createUser(new RegisterRequest("mar", "hoaianhnotice@gmail.com", "123"));
        anh.getRoles().add(roleAuthor);
        userRepo.save(anh);
        
        User nam = createUser(new RegisterRequest("rose", "rose123@gmail.com", "123"));
        nam.getRoles().add(roleAuthor);
        userRepo.save(nam);

        User hoai = createUser(new RegisterRequest("yasuo", "lylia@gmail.com", "123"));
        hoai.getRoles().add(roleEditor);
        userRepo.save(hoai);

        userRepo.flush();
    }

    @Override
    public UserInfo getLoggedUser(Principal principal) {
        if  (principal != null){
            Optional<User> user = userRepo.findByEmail(principal.getName());
            if(user.isPresent()){
                return UserMapper.INSTANCE.userToUserInfo(user.get());
            } else {
                return null;
            }
            
        }
        return null;
    }

    @Override
    public List<UserInfo> findAllUser() {   
        List<User> users =  userRepo.findAll();
        List<UserInfo> result = new ArrayList<>();
        for(var user : users){
            result.add(UserMapper.INSTANCE.userToUserInfo(user));
        }
        return result; 
    }

    @Override
    public UserInfo findById(Long id) {

        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return UserMapper.INSTANCE.userToUserInfo(user.get());
        } 
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public void createNewUser(RegisterRequest request) throws UserException {
        Role roleAuthor = roleRepo.findByName("AUTHOR");
        var oUser = userRepo.findByEmail(request.getEmail());
        if(!oUser.isPresent()){
            User user = new User(request.getFullname(), request.getEmail(), encoder.encode(request.getPassword()));
            user.addRole(roleAuthor);
            userRepo.saveAndFlush(user);
        } else {
            throw new UserException(request.getEmail() + "already exist");
        }
        
    }
}
