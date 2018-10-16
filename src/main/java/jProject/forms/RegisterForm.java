package jProject.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class RegisterForm {
    @NotBlank(message = "This field is required!")
    @Column(unique=true)
    @Email(message = "Not well formed e-mail")
    @Pattern(regexp = "^[A-Za-z0-9@.]*$",message ="Only A-Z a-z 0-9 . and @ are allowed")
    private String email;

    @NotBlank(message = "This field is required!")
    @Size(min = 3,message = "The password must be at least 3 symbols")
    @Pattern(regexp = "^[A-Za-z0-9]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String password;

    @NotBlank(message = "This field is required!")
    @Size(min = 3,message = "The password must be at least 3 symbols")
    @Pattern(regexp = "^[A-Za-z0-9]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String confirmPassword;

    @NotBlank(message = "This field is required!")
    @Size(min = 3,max = 100,message = "The name must be between 3 and 100 symbols")
    @Pattern(regexp = "^[A-Za-z0-9]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String firstName;

    @NotBlank(message = "This field is required!")
    @Size(min = 3,max = 100,message = "The name must be between 3 and 100 symbols")
    @Pattern(regexp = "^[A-Za-z0-9]*$",message ="Only A-Z a-z and 0-9 are allowed")
    private String lastName;


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

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword;  }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName;  }

    public void setLastName(String lastName) { this.lastName = lastName;  }
}