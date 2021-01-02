package com.project.user.administration.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.user.administration.model.User;
import com.project.user.administration.model.UserLogin;
import com.project.user.administration.repository.UserRepository;
import com.project.user.administration.repository.UserLoginRepository;
import com.project.user.administration.vo.UserAuthorizeResponseVo;
import com.project.user.administration.vo.UserRequestVo;
import com.project.user.administration.vo.UserTokenResponseVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    public UserRequestVo findByUserId(Long userId) {

        User user = userRepository.findByUserId(userId);

        return UserRequestVo.builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    public List<UserRequestVo> getAllUsers() {
        List<UserRequestVo> usersVo = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u : users) {
            usersVo.add(UserRequestVo.builder()
                    .username(u.getUserName())
                    .email(u.getEmail())
                    .build());
        }
        return usersVo;
    }

    public void registerNewUser(UserRequestVo userRequestVo) {
        User user = User.builder()
                .userName(userRequestVo.getUsername())
                .password(userRequestVo.getPassword())
                .email(userRequestVo.getEmail())
                .build();

        userRepository.save(user);
    }

    public String getCurrentTimeStamp() {
        Date newDate = DateUtils.addHours(new Date(), 3);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newDate);
    }

    public static String createJsonWebToken(String username){
        String jwt = JWT.create()
                .withSubject(username)
                .withIssuer("auth0")
                .withExpiresAt(DateUtils.addHours(new Date(), 3))
                .sign(Algorithm.HMAC256("secret"));
        return jwt;
    }


    public UserTokenResponseVo validateUserCredentialsAndGenerateToken(UserRequestVo userRequestVo) {

        User user = userRepository.findUser(userRequestVo.getUsername(), userRequestVo.getPassword());

        if( user != null) {

            //String token=  RandomStringUtils.random(25, true, true);
            String token = createJsonWebToken(userRequestVo.getUsername());

            UserLogin userLogin = UserLogin.builder()
                    .user(user)
                    .token(token)
                    .tokenExpireTime(getCurrentTimeStamp())
                    .build();
            userLoginRepository.save(userLogin);

            UserTokenResponseVo userTokenResponseVo = new UserTokenResponseVo();
            userTokenResponseVo.setToken(token);
            userTokenResponseVo.setUsername(userRequestVo.getUsername());

            return userTokenResponseVo;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public UserAuthorizeResponseVo authorizeV1(UserRequestVo userRequestVo) throws ParseException {
        UserLogin userLogin = userLoginRepository.findByUserAndToken(userRequestVo.getUsername(), userRequestVo.getToken());

        // check user-login in database
        if(userLogin != null){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(userLogin.getTokenExpireTime());

            // check if token expired
            if(new Date().compareTo(date) <1){
                return new UserAuthorizeResponseVo(userRequestVo.getUsername(), true);
            } else {
                return new UserAuthorizeResponseVo(userRequestVo.getUsername(), false);
            }
        }
        return new UserAuthorizeResponseVo(userRequestVo.getUsername(), false);
    }

    public UserAuthorizeResponseVo authorizeV2(UserRequestVo userRequestVo) throws ParseException {
        String userName = extractUserNameFromToken(userRequestVo.getToken());
        UserLogin userLogin = userLoginRepository.findByUserAndToken(userName, userRequestVo.getToken());

        // check user-login in database
        if(userLogin != null){
            return new UserAuthorizeResponseVo(userRequestVo.getUsername(),  verifyToken(userRequestVo.getUsername(),     userRequestVo.getToken()));
        }
        return new UserAuthorizeResponseVo(userRequestVo.getUsername(), false);
    }


    public static String extractUserNameFromToken( String token) throws JWTVerificationException{

        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier =  JWT.require(algorithm)
                .withIssuer("auth0")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return  jwt.getSubject();

    }

    public static boolean verifyToken(String user, String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier =  JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();

            Date dateTheTokenWillExpire = jwt.getExpiresAt();
            if(new Date().compareTo(dateTheTokenWillExpire) <1){
                return true;
            } else {
                return false;
            }

        } catch(JWTVerificationException exception){
            return false;
        }

    }
}