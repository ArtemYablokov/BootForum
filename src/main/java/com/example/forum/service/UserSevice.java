package com.example.forum.service;

import com.example.forum.domain.Role;
import com.example.forum.domain.User;
import com.example.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserSevice implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    // добавление юзера
    public boolean addUser(User user) {
        //проверка что такого еще нет
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString()); // КОД АКТИВАЦИИ - ДЛЯ Е МЭИЛА

        userRepo.save(user);

        sendMessage(user);     // ОТППРАВКА АКТИВАЦИОННОГО КОДА

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


    //  для редактирования пользователя АДМИНОМ - роли / имя

    public void saveUser(User user, Map<String, String> form, String userName) {
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
    }
    // для редактирования пользователя самим пользователем

    public void updateProfile(User user, String password, String email) {
        // прверка мэйла
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        // установка НОВОГО активационного кода - если обновили МЭИЛ
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);     // ОТППРАВКА АКТИВАЦИОННОГО КОДА
        }
    }

    // ОТППРАВКА АКТИВАЦИОННОГО КОДА
    private void sendMessage(User user) {
        if ( !StringUtils.isEmpty(user.getEmail()) ) { // ДЛЯ ПРОВЕРКИ ЧТО ПОЛЕ НЕ ПУСТОЕ !!!
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Forum. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
}
