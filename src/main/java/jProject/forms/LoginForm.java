package jProject.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class LoginForm {
    @NotBlank(message = "This field is required!")
    @Column(unique=true)
    @Email(message = "Not well formed e-mail")
    @Pattern(regexp = "^[A-Za-z0-9@.]*$",message ="Only A-Z a-z 0-9 . and @ are allowed")
    private String email;

    @NotBlank(message = "This field is required!")
    @Size(min = 3,message = "The password must be at least 3 symbols")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginForm() {
    }
}