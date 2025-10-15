package project.repository;

import project.entity.User;
import project.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(@Param("email") String email);

    @Query("SELECT new project.wrapper.UserWrapper(u.id, u.firstName, u.lastName, u.contactNumber, u.email, u.address, u.role) " +
            "FROM User u WHERE u.id = :id")
    UserWrapper getUserById(@Param("id") Integer id);

    @Query("SELECT new project.wrapper.UserWrapper(u.id, u.firstName, u.lastName, u.contactNumber, u.email,  u.address, u.role) " +
            "FROM User u WHERE u.role = 'user'")
    List<UserWrapper> getAllUsers();

    @Query("SELECT u.email FROM User u WHERE u.role = 'admin'")
    List<String> getAllEmailAdmins();

    User findByEmail(String email);
}