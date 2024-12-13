package com.example.demo.controllerUI;

import com.example.demo.model.User;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.User.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {
    private final UserService userService;

    public GlobalModelAttributes(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addUserIdToModel(Model model) {
        // Get logged-in user's username
        String username = SecurityUtil.getSessionUser();
        if (username != null) { // Check if a user is logged in
            User loggedInUser = userService.findByUserName(username);
            model.addAttribute("userId", loggedInUser.getId());
        }
    }
}
