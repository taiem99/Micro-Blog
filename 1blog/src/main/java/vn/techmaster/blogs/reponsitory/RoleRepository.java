package vn.techmaster.blogs.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.blogs.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Role findByName(String string);
    
}
