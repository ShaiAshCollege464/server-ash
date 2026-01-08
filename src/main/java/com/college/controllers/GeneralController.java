package com.college.controllers;


import com.college.*;
import com.college.utils.DbUtils;
import com.college.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.college.utils.Errors.*;


@RestController
public class GeneralController {
    private Map<String, Integer> wrongLoginAttempts = new HashMap<>();

    @Autowired
    private DbUtils dbUtils;

    @PostConstruct
    public void init () {
    }

    private Map<Integer, Integer> someData = new HashMap<>();

    @RequestMapping ("/get-from-map")
    public Integer getFromMap (int key) {
        long start = System.nanoTime();
        dbUtils.someQuery();
        System.out.println("Took: " + (start - System.nanoTime()));
        return null;
    }


    @RequestMapping("/all")
    public BasicResponse getAllUsers () {
        return new AllUsersResponse(
                true,
                null,
                dbUtils.getAllUsers());
    }

    @RequestMapping("create-user")
    public BasicResponse addUser (String username, String password) {
        if (username != null && !username.isEmpty()) {
            if (password != null && !password.isEmpty()) {
                User user = new User(username, GeneralUtils.hash("", password));
                 dbUtils.createUserOnDb(user);
                return new BasicResponse(true, null);
            } else {
                return new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }

    @RequestMapping("/login")
    public BasicResponse login (String username, String password, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Integer attempts = wrongLoginAttempts.get(ip);
        if (attempts == null) {
            attempts = 0;
        }
        if (attempts < 3) {
            if (username != null && !username.isEmpty()) {
                if (password != null && !password.isEmpty()) {
                    password = GeneralUtils.hash("", password);
                    User user = dbUtils.getUserByUsernameAndPassword(username, password);
                    if (user != null) {
                        return new LoginResponse(true, null, user);
                    } else {
                        attempts++;
                        wrongLoginAttempts.put(ip, attempts);
                        boolean usernameExists = dbUtils.checkIfUsernameExists(username);
                        if (usernameExists) {
                            return new BasicResponse(false, ERROR_WRONG_CREDS);
                        } else {
                            return new BasicResponse(false, ERROR_NO_ACCOUNT);
                        }
                    }
                } else {
                    return new BasicResponse(false, ERROR_MISSING_PASSWORD);
                }
            } else {
                return new BasicResponse(false, ERROR_MISSING_USERNAME);
            }
        } else {
            attempts++;
            wrongLoginAttempts.put(ip, attempts);
            return new BasicResponse(false, ERROR_WRONG_CREDS);
        }
    }

    @RequestMapping("is-username-available")
    public BasicResponse isUsernameAvailable (String username) {
        if (username != null && !username.isEmpty()) {
            boolean available = dbUtils.isUsernameAvailable(username);
            return new BooleanResponse(true, null, available);
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }

    @RequestMapping("sign-in")
    public BasicResponse signIn (String username, String phone) {
        if (username != null && !username.isEmpty()) {
            if (phone != null && !phone.isEmpty()) {
                User user = dbUtils.getUser(username, phone);
                return new UserResponse(true, null, user);
            } else {
                return new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }



}
