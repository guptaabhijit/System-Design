package com.company.strategy;


import com.company.Util.FoodKartUtil;
import com.company.model.Restaurant;
import com.company.model.User;

import java.util.*;

public class PriceStrategy implements RestaurantDisplayStrategy {
    @Override
    public List<Restaurant> findRestaurants(List<Restaurant> listOfRestaurants, User currentUser) {
        List<Restaurant> list = FoodKartUtil.getMatchingRestaurant(listOfRestaurants, currentUser);
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant restaurant1, Restaurant restaurant2) {
                return Double.compare(restaurant2.getFoodItem().getPrice(), restaurant1.getFoodItem().getPrice());
            }
        });

        return list;
    }
}