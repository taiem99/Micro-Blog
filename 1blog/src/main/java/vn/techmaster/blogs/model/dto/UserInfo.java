package vn.techmaster.blogs.model.dto;

import java.util.Set;

import lombok.Data;
import vn.techmaster.blogs.model.entity.Role;

@Data
public class UserInfo {
    private Long id;
    private String fullname;
    private String email;
    private Set<Role> roles;
}
