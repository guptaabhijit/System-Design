package com.company.strategy;

import com.company.Util.FoodKartUtil;
import com.company.model.Restaurant;
import com.company.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RatingStrategy implements RestaurantDisplayStrategy {
    @Override
    public List<Restaurant> findRestaurants(List<Restaurant> listOfRestaurants, User currentUser) {
        List<Restaurant> list = FoodKartUtil.getMatchingRestaurant(listOfRestaurants, currentUser);
        Collections.sort(list, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return Double.compare(o2.getRestaurantRating(), o1.getRestaurantRating());
            }
        });
        return list;
    }
}
