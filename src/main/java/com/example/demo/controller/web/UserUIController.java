package com.example.demo.controller.web;



import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userui")
public class UserUIController {
    @Autowired
    private IUserService userService;

    //Show User List
    @GetMapping({""})
    public String getUserList(Model model) {
        Iterable<UserDTO> users =  userService.getAllUser();
        model.addAttribute("users", users);
        return "user/index";
    }

    @GetMapping("/createuser")
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/CreateUser";
    }

    @PostMapping("/createuser")
    public String saveUser(@ModelAttribute("user") User model){
        userService.save(model);
        return "redirect:/userui";
    }

//    @GetMapping("/editUser/{id}")
//    public String editUserForm(@PathVariable Long id, User model){
//        UserDTO userDTO = userService.findById(id);
//        return "";
//    }




}
