package project.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "User.findUserByEmail", query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUsers", query = "select new " +
        "project.wrapper.UserWrapper(u.id, u.firstName, u.lastName,u.contactNumber,u.email, u.status, u.address, u.role) " +
        "from User u where u.role='user'")
@NamedQuery(name = "User.getAllEmailAdmins", query = "select u.email from User u where u.role='admin'")
@NamedQuery(name = "User.getUserById", query = "select new " +
        "project.wrapper.UserWrapper(u.id, u.firstName, u.lastName,u.contactNumber,u.email, u.status, u.address, u.role) " +
        "from User u where u.id=:id")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable {
    private static final PasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "First name must not be empty")
    @Pattern(regexp = "^[^0-9]*$", message = "First name must not contain digits")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Pattern(regexp = "^[^0-9]*$", message = "Last name must not contain digits")
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "contactNumber")
    private String contactNumber;

    @NotBlank(message = "Email must be not blank")
    @NotNull(message = "Email must not be null")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password must be not blank")
    @NotNull(message = "Password must not be null")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Pattern(regexp = "^(user|admin)$", message = "Role must be either 'user' or 'admin'")
    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String firstName, String lastName, String contactNumber, String email, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public User(String firstName, String lastName, String contactNumber, String email, String password, String address, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @PostPersist
    public void postPersist() {
        this.password = B_CRYPT_PASSWORD_ENCODER.encode(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
