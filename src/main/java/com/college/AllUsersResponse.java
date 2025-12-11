package com.college;

import java.util.List;

public class AllUsersResponse extends BasicResponse{
    private List<User> userList;

    public AllUsersResponse(boolean success, Integer errorCode, List<User> userList) {
        super(success, errorCode);
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
