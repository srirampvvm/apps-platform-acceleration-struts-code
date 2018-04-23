package org.superbiz.struts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userService;

    @GetMapping("/addUser")
    public String addUserForm() {
        return "addUserForm";
    }

    @PostMapping("/addUser")
    @Transactional
    public String postUserForm(@RequestParam long id, @RequestParam String firstName, @RequestParam String lastName, Model model) {
        try {
            userService.save(new User(id, firstName, lastName));
            return "addedUser";
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",e.getMessage());
            return "addUserForm";
        }
    }

    @GetMapping("/findUser")
    public String findUserForm() {
        return "findUserForm";
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model) {
        User user = userService.findOne(id);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "findUserForm";
        }

        model.addAttribute("user", user);
        return "displayUser";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "displayUsers";
    }
}
