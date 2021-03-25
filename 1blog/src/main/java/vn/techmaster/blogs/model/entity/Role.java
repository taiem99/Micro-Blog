package vn.techmaster.blogs.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role implements Serializable{
  
    private static final long serialVersionUID = -7048484577663298957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public static final String ADMIN = "ADMIN";
    public static final String EDITOR = "EDITOR";
    public static final String AUTHOR = "AUTHOR";

    public Role(String name){
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
