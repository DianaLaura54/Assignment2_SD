package project.controller;

import project.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserController {
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @GetMapping(path = "/getAdminEmails")
    public ResponseEntity<List<String>> getAllAdminEmails();

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<UserWrapper> getById(@PathVariable Integer id);

    @PutMapping(path = "/update")
    public ResponseEntity<String> update(@Valid @RequestBody(required = true) Map<String, String> requestMap);

    @PutMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Integer id);
}

