package com.company.strategy;

import com.company.model.Restaurant;
import com.company.model.User;

import java.util.List;

public interface RestaurantDisplayStrategy {
    public List<Restaurant> findRestaurants(List<Restaurant> listOfRestaurants, User currentUser);
}