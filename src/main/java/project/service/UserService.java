package project.service;

import project.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUsers();
    ResponseEntity<List<String>> getAllEmailAdmins();

    ResponseEntity<UserWrapper> getById(Integer id);

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> changePassword(Map<String, String> requestMap);
    ResponseEntity<String> deleteUser(Integer id);
}
