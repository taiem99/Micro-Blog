package vn.techmaster.blogs.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private Long id;

    @NotNull(message = "fullname is required")
    @NotEmpty(message = "fullname is required")
    private String fullname;

    @NotEmpty(message = "email is required")
    @NotNull(message = "email is required")
    @Email(message = "please provide a valid email")
    private String email;

    @NotEmpty(message = "password is required")
    @NotNull(message = "password is required")
    @Size(min = 3, max = 20, message = "password must be between 3 and 20 characters")
    private String password;

    public RegisterRequest(String fullname, String email, String password){
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
