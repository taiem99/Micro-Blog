package vn.techmaster.blogs.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.blogs.model.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPOJO {
    private Long id;
    private String title;
    private String content;
    private Long user_id;
    private String userFullName;
    private LocalDateTime createAt;
    private LocalDateTime lastUpdate;
    private Set<Role> roles;
}
