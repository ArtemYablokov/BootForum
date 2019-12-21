package com.example.forum.controller;

import com.example.forum.domain.Role;
import com.example.forum.domain.User;
import com.example.forum.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')") // доступ к этому маппингу додступен только АДМИНУ
public class UserController {
    @Autowired
    UserSevice userSevice;

    @GetMapping
    public String getUser(Model model){
        model.addAttribute("users", userSevice.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        // если указать переменную Long user - в нее будет помещаться  USER.ID = АТТРИБУТ ЗАПРОСА
        // указывая юзер - получаем сразу пользователя благодаря Spring
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    //  для редактирования пользователя АДМИНОМ - роли / имя
    @PostMapping()
    public String userSave(
            @RequestParam String userName,
            @RequestParam Map<String, String> form, // в мапе передаются роли ( и не только ! ) - их разное количество
            @RequestParam("userId") User user) // юзера получаем по ID = передается скрыто
    {
        userSevice.saveUser(user, form, userName);
        return "redirect:/users";
    }



    @GetMapping("profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal User user) { // AuthenticationPrincipal -
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userSevice.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

}