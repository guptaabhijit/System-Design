package com.company.dao;


import com.company.Exceptions.*;
import com.company.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurantdao {
    private static Restaurantdao restaurantDaoInstance;
    private HashMap<String, Restaurant> restaurantMap;

    private Restaurantdao() {
        this.restaurantMap = new HashMap<>();
    }

    public static Restaurantdao getInstance() {
        if (restaurantDaoInstance == null)
            restaurantDaoInstance = new Restaurantdao();

        return restaurantDaoInstance;
    }

    public void addRestaurant(Restaurant restaurant) {
        if (this.restaurantMap.containsKey(restaurant.getRestaurantName())) {
            throw new RestaurantAlreadyPresent("restaurant Already present");
        }
        this.restaurantMap.put(restaurant.getRestaurantName(), restaurant);
    }

    public Restaurant getRestaurant(String restaurantName) {
        if (!restaurantMap.containsKey(restaurantName)) {
            throw new RestaurantNotPresent("restaurant Not Present");
        }
        return restaurantMap.get(restaurantName);
    }

    public List<Restaurant> getListOfRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        for (Map.Entry<String, Restaurant> entry : restaurantMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
}
