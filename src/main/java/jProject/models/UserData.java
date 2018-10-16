package jProject.models;


        import javax.persistence.*;
        import javax.validation.constraints.NotNull;
        import javax.validation.constraints.Size;
        import java.sql.Date;
        import java.util.HashSet;
        import java.util.Set;

@Entity            // This tells Hibernate to make a table out of this class
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 3,max = 100)
    private String firstName;

    @NotNull
    @Size(min = 3,max = 100)
    private String lastName;

    @NotNull
    @Column(unique=true)
    @Size(min = 3,max = 100)
    private String email;

    private String authority;

    private boolean enabled;

    private Date registerdOn;

    @NotNull
    @Size(min = 3)
    private String password;


    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservationId = new HashSet<Reservation>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterdOn() { return registerdOn; }

    public void setRegisterdOn(Date registerdOn) { this.registerdOn = registerdOn; }
}