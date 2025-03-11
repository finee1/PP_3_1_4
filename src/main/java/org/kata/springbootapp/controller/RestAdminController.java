package org.kata.springbootapp.controller;

import org.kata.springbootapp.dto.UserDTO;
import org.kata.springbootapp.model.User;
import org.kata.springbootapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAdminController {

    private final UserService userService;

    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.listUsers();
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userService.add(user);
        User createdUser = userService.loadUserByUsername(user.getUsername());
        UserDTO createdUserDTO = convertToDTO(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userService.add(user);
        User createdUser = userService.loadUserByUsername(user.getUsername());
        UserDTO createdUserDTO = convertToDTO(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "Deleted user with id: " + id;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setDepartment(userDTO.getDepartment());
        user.setSalary(userDTO.getSalary());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setDepartment(user.getDepartment());
        userDTO.setSalary(user.getSalary());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

}
