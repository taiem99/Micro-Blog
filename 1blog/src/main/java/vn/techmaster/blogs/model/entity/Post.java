package vn.techmaster.blogs.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.blogs.exception.PostException;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Indexed
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField
    private String title;
    
    @Column(length = 5000)
    @FullTextField
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime lastUpdate;
    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.lastUpdate = LocalDateTime.now();
    }

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setPost(this);
    }
    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setPost(null);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @ManyToMany
    @JoinTable(
        name = "post_tag",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    public void addTag(Tag tag){
        tags.add(tag);
        tag.getPosts().add(this);
    }
    public void removeTag(Tag tag){
        tags.remove(tag);
        tag.getPosts().remove(this);
    }

    private final static int PHOTO_LIMIT = 3;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Photo> photos = new ArrayList<>();

    public void addPhoto(Photo photo) throws PostException{
        if(photos.size() == PHOTO_LIMIT){
            throw new PostException("Amount photos per post must be smaller than  " + PHOTO_LIMIT);
        }
        photos.add(photo);
        photo.setPost(this);
    }

    public void removePhoto(Photo photo){
        photos.remove(photo);
        photo.setPost(null);
    }


}
