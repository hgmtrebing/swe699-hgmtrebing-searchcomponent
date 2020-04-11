package us.hgmtrebing.swe699.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.*;

public class RestaurantManagerRequestor {

    private static Logger log = LoggerFactory.getLogger(RestaurantManagerRequestor.class);

    private static String restaurantListUrl = "http://3.88.210.26:8080/RestaurantManager-0.0.1/restaurant/list";

    private static String cuisineListUrl = "http://3.88.210.26:8080/RestaurantManager-0.0.1/restaurant/categories";

    public RestaurantManagerRequestor() {

        int retrieverTimerInterval = 60_000;
        TimerTask retrieverTask = new TimerTask() {
            @Override
            public void run() {
                log.info("Retrieving data from Restaurant Manager");
                MysqlConnection mysqlConnection = new MysqlConnection();
                mysqlConnection.connect();
                mysqlConnection.initializeDatabaseSchema(false);
                mysqlConnection.closeConnection();

                retrieveAndInsertCuisines();
                retrieveAndInsertRestaurants();
            }
        };
        Timer retrieverTimer = new Timer();
        retrieverTimer.schedule(retrieverTask, 0, retrieverTimerInterval);
    }

    public static void retrieveAndInsertCuisines() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(cuisineListUrl, String.class);
        JsonParser parser = JsonParserFactory.getJsonParser();
        List<Object> responseBody = parser.parseList((response.getBody()));

        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        for (Object o : responseBody) {
            Cuisine cuisine = connection.getCuisineByName(o.toString());
            if (cuisine == null || cuisine.getName() == null || !(cuisine.getName().equalsIgnoreCase(o.toString()))) {
                connection.addNewCuisine(o.toString());
            }
        }
        log.info("Successfully retrieved Cuisines from Restaurant Manager API");
        connection.closeConnection();
    }

    public static void retrieveAndInsertRestaurants() {

        MysqlConnection connection = new MysqlConnection();
        connection.connect();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(restaurantListUrl, String.class);
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, Object> responseBody = parser.parseMap((response.getBody()));

        List<Restaurant> restaurants = new ArrayList<>();

        for (Map.Entry<String, Object> restaurantEntry: responseBody.entrySet()) {

            Restaurant restaurant = new Restaurant();
            restaurant.setPublicId(restaurantEntry.getKey());
            Map<String, Object> restaurantObject = (Map<String, Object>)restaurantEntry.getValue();
            restaurant.setName((String) restaurantObject.get("Name"));

            Map<String, Object> locationObject = (Map<String, Object>) restaurantObject.get("Location");
            restaurant.setStreetAddress((String)locationObject.get("Street"));
            restaurant.setCity((String)locationObject.get("City"));
            restaurant.setZipCode(Integer.parseInt((String)locationObject.get("Zipcode")));
            restaurant.setState((String)locationObject.get("State"));

            List<Object> categories = (List<Object>)restaurantObject.get("Categories");
            for (Object o : categories) {
               Cuisine c = connection.getCuisineByName(o.toString());
               if (c != null || c.getName() != null) {
                   restaurant.addCuisine(c);
               }
            }
            restaurants.add(restaurant);
        }

        for (Restaurant restaurant : restaurants) {
            if (!connection.doesRestaurantExist(restaurant.getPublicId())) {
                log.info("Adding a new restaurant: {}", restaurant);
                connection.addNewRestaurant(restaurant);
            }
            log.info("Restaurant already exists; skipping : {}", restaurant);
        }

        log.info("Successfully retrieved Restaurants from Restaurant Manager API");
        connection.closeConnection();
    }
}
