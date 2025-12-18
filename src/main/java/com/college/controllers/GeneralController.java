package com.college.controllers;


import com.college.*;
import com.college.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.college.utils.Errors.*;


@RestController
public class GeneralController {
    private List<User> allUsers = new ArrayList<>();

    @Autowired
    private DbUtils dbUtils;

    @PostConstruct
    public void init () {
    }

    @RequestMapping("/all")
    public BasicResponse getAllUsers () {
        return new AllUsersResponse(
                true,
                null,
                dbUtils.getAllUsers());
    }

    @RequestMapping("create-user")
    public BasicResponse addUser (String first, String last, String phone) {
        if (first != null && !first.isEmpty()) {
            if (last != null && !last.isEmpty()) {
                User user = new User(first, last, phone, "");
                 dbUtils.createUserOnDb(user);
                return new BasicResponse(true, null);
            } else {
                return new BasicResponse(false, ERROR_MISSING_LAST_NAME);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_FIRST_NAME);
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
