package com.company.service;

import com.company.dao.Userdao;
import com.company.enums.Gender;
import com.company.model.User;

public class UserServiceImpl implements UserService {

    Userdao userDao;

    public UserServiceImpl() {
        this.userDao = Userdao.getInstance();
    }

    @Override
    public void registerUser(String name, String gender, String phoneNumber, String pinCode) {
        User user = new User(name, gender.toLowerCase().equals("male") ? Gender.MALE : Gender.FEMALE, phoneNumber,
                pinCode);
        userDao.addUser(user);
    }

    @Override
    public boolean loginUser(String userId) {
        User user = userDao.getUser(userId);
        if (user != null) {
            userDao.setCurrentLoginUser(user);
            return true;
        }
        return false;
    }
}
