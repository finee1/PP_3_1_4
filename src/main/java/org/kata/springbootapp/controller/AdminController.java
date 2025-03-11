package org.kata.springbootapp.controller;

import org.kata.springbootapp.model.User;
import org.kata.springbootapp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        List<User> allUsers = userService.listUsers();
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        model.addAttribute("user", user);
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }

    @PostMapping("/saveUser")
    public String addNewUser(@ModelAttribute("newUser") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
