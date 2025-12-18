package com.college;

public class UserResponse extends BasicResponse{
    private User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public UserResponse(boolean success, Integer errorCode, User user) {
        super(success, errorCode);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
