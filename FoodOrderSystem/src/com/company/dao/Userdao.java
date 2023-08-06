package com.company.dao;

import com.company.Exceptions.UserAlreadyPresent;
import com.company.Exceptions.UserNotPresent;
import com.company.model.User;

import java.util.HashMap;

public class Userdao {

    private static Userdao userDaoInstance = null;
    private HashMap<String, User> userMap;
    private User currentLoginUser;

    private Userdao() {
        this.userMap = new HashMap<>();
        currentLoginUser = null;
    }

    public static Userdao getInstance() {
        if (userDaoInstance == null)
            userDaoInstance = new Userdao();

        return userDaoInstance;
    }

    public void addUser(User user) {
        if (userMap.containsKey(user.getPhoneNumber())) {
            throw new UserAlreadyPresent("User already exists with same name " + user.getName());
        }
        userMap.put(user.getPhoneNumber(), user);
    }

    public User getUser(String userPhoneNumber) {
        if (!userMap.containsKey(userPhoneNumber)) {
            throw new UserNotPresent("User not found");
        }
        return userMap.get(userPhoneNumber);
    }

    public User getCurrentLoginUser() {
        return currentLoginUser;
    }

    public void setCurrentLoginUser(User currentLoginUser) {
        this.currentLoginUser = currentLoginUser;
    }
}