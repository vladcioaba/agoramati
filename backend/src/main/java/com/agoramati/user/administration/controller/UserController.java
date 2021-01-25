package com.agoramati.user.administration.controller;

import com.agoramati.user.administration.vo.UserAuthorizeResponseVo;
import com.agoramati.user.administration.vo.UserTokenResponseVo;
import com.agoramati.user.administration.services.UserService;
import com.agoramati.user.administration.vo.UserRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/watchlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void getWatchlist(@RequestBody UserRequestVo userRequestVo) {
        userService.getWatchList(userRequestVo);
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void registerNewUser(@RequestBody UserRequestVo userRequestVo) {
        userService.registerNewUser(userRequestVo);
    }

    @CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserTokenResponseVo login(@RequestBody UserRequestVo userRequestVo) {
        return userService.validateUserCredentialsAndGenerateToken(userRequestVo);
    }

    @CrossOrigin
    @RequestMapping(value = "/authorize", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserAuthorizeResponseVo authorize(@RequestBody UserRequestVo userRequestVo) throws ParseException {
        return userService.authorizeV2(userRequestVo);
    }
}