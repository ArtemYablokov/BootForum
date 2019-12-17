package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    UserRepo userRepo;

    @GetMapping
    public String getUser(Model model){
        model.addAttribute("users", userRepo.findAll());
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

    @PostMapping()
    public String userSave(
            @RequestParam String userName,
            @RequestParam Map<String, String> form, // в мапе передаются роли ( и не только ! ) - их разное количество
            @RequestParam("userId") User user) // юзера получаем по ID = передается скрыто
    {
        user.setUsername(userName);

        // ПОЛУЧАЕМ все доступные роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name) // переводим их из ЕНАМА в СТРОКУ
                .collect(Collectors.toSet()); // из АРРАЯ в СЕТ

        // чистим все роли пользователя
        user.getRoles().clear();

        // устанавливаем пользователю роли
        for (String key : form.keySet()) { // если в форме есть роль - значит для нее установлен флажок
            // НО в FORM присутствуют еще
            //key = userName
            //key = USER
            //key = ADMIN
            //key = userId
            //key = _csrf  - то что нам не нужно
            if (roles.contains(key)) { // проверяем что в НАШЕМ СПИСКЕ РОЛЕЙ есть такой ключ
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
        return "redirect:/users";
    }
}