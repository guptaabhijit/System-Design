package com.company.service;


public interface UserService {

    //Register a User
    void registerUser(String name, String gender, String phoneNumber, String pincode);

    boolean loginUser(String userId);
}
