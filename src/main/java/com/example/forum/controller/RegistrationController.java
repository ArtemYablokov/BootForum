package com.example.forum.controller;

import com.example.forum.domain.Role;
import com.example.forum.domain.User;
import com.example.forum.repos.UserRepo;
import com.example.forum.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
//    @Autowired
//    private UserRepo userRepo;

    @Autowired
    private UserSevice userSevice;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

//    @PostMapping("/registration")
//    public String addUser(User user, Map<String, Object> model) {
//        User userFromDb = userRepo.findByUsername(user.getUsername());
//
//        if (userFromDb != null) {
//            model.put("message", "User exists!");
//            return "registration";
//        }
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        userRepo.save(user);
//
//        return "redirect:/login";
//    }


    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) { // как сюда попадает ЮЗЕР из формы..
        if ( !userSevice.addUser(user)) { // проверка что такого нет, отправка АКТИВАЦИОННОГО кода
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    // ПО ЭТОМУ УРЛУ ПЕРЕХОДИТ ПОЛЬЗОВАТЕЛЬ ПРИ АКТИВАЦИИ ИЗ МЭЙЛА
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {

        boolean isActivated = userSevice.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
