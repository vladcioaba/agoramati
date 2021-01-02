package com.agoramati.user.administration.controller;

import com.agoramati.user.administration.vo.UserAuthorizeResponseVo;
import com.agoramati.user.administration.vo.UserTokenResponseVo;
import com.agoramati.user.administration.services.UserService;
import com.agoramati.user.administration.vo.UserRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    @CrossOrigin
    public UserRequestVo getUserById(@PathVariable Long userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping("/user")
    @CrossOrigin
    public UserRequestVo getUserByName(@RequestParam(name = "name") String name) {
        List<UserRequestVo> users = userService.getAllUsers();
        UserRequestVo userBestMatch = null;
        int len = name.length();
        for (UserRequestVo u : users) {
            if (u.getUsername().contains(name)) {
                if (u.getUsername().indexOf(name) == 0) {
                    int d = u.getUsername().length() - name.length();
                    if (d < len) {
                        len = d;
                        userBestMatch = u;
                    }
                }
            }
        }
        return userBestMatch;
    }

    @GetMapping("/user/all")
    @CrossOrigin
    public List<UserRequestVo> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user/register")
    @CrossOrigin
    public void registerNewUser(@RequestBody UserRequestVo userRequestVo) {
        userService.registerNewUser(userRequestVo);
    }

    @PostMapping("/user/authenticate")
    @CrossOrigin
    public UserTokenResponseVo login(@RequestBody UserRequestVo userRequestVo) {
        return userService.validateUserCredentialsAndGenerateToken(userRequestVo);
    }

    @PostMapping("/user/authorize")
    @CrossOrigin
    public UserAuthorizeResponseVo authorize(@RequestBody UserRequestVo userRequestVo) throws ParseException {
        return userService.authorizeV2(userRequestVo);
    }
}