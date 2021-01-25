package com.agoramati.user.administration.controller;

import com.agoramati.user.administration.vo.*;
import com.agoramati.user.administration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/watchlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<WatchlistResponseVo> getWatchlist(@RequestBody WatchlistRequestVo watchlistRequestVo) {
        return userService.getWatchList(watchlistRequestVo);
    }

    @CrossOrigin
    @RequestMapping(value = "/addsymbol", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void addSymbolToWatchlist(@RequestBody WatchlistAddRequestVo watchlistAddVo) {
        userService.addSymbolToWatchlist(watchlistAddVo);
    }

    @CrossOrigin
    @RequestMapping(value = "/removesymbol", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void removeSymbolFromWatchlist(@RequestBody WatchlistRemoveVo watchlistRemoveVo) {
        userService.removeSymbolFromWatchlist(watchlistRemoveVo);
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