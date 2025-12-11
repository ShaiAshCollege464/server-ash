package com.college.controllers;


import com.college.AllUsersResponse;
import com.college.BasicResponse;
import com.college.User;
import com.college.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.college.utils.Errors.ERROR_MISSING_FIRST_NAME;
import static com.college.utils.Errors.ERROR_MISSING_LAST_NAME;


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




}
