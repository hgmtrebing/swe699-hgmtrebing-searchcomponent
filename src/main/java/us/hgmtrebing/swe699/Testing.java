package us.hgmtrebing.swe699;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.hgmtrebing.swe699.controller.RestaurantManagerRequestor;
import us.hgmtrebing.swe699.database.HibernateConnection;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.List;
import java.util.Map;

public class Testing {

    public static void main (String[] args) {
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        connection.initializeDatabaseSchema(false);
        connection.closeConnection();
        getRestaurantManagerCuisines();
        getRestaurantManagerRestaurants();
    }

    public static void restAndParseJSON() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity("http://3.86.25.222:8080/RestaurantManager-0.0.1/restaurant/list", String.class);
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, Object> responseBody = parser.parseMap(response.getBody());
        for (Map.Entry<String, Object> entry : responseBody.entrySet()) {
            if (entry.getValue() instanceof Map) {
                for (Map.Entry<Object, Object> subentry : ((Map<Object, Object>) entry.getValue()).entrySet())  {

                    System.out.println(subentry.getKey() + " = " + subentry.getValue());
                }
            } else {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
        System.out.println(response.toString());
    }

    public static void getAllCuisines() {
        HibernateConnection connection = new HibernateConnection();
        connection.connect();
        System.out.println(connection.getAllCuisines());
    }

    public static void getRestaurantManagerCuisines() {
        RestaurantManagerRequestor.retrieveAndInsertCuisines();
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        System.out.println(connection.getAllCuisines());
        connection.closeConnection();
    }

    public static void getRestaurantManagerRestaurants() {
        RestaurantManagerRequestor.retrieveAndInsertRestaurants();
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        List<Restaurant> restaurants = connection.getAllRestaurants();
        System.out.println(restaurants);
        System.out.println(connection.doesRestaurantExist("shit"));
        System.out.println(connection.doesRestaurantExist(restaurants.get(0).getPublicId()));
        System.out.println(connection.getCuisinesByRestaurantId(5));
        connection.closeConnection();
    }
}
