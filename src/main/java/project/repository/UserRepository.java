package project.repository;

import project.entity.User;
import project.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(@Param("email") String email);

    UserWrapper getUserById(@Param("id") Integer id);

    List<UserWrapper> getAllUsers();
    List<String> getAllEmailAdmins();

    User findByEmail(String email);

}
