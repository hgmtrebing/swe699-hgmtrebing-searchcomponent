package us.hgmtrebing.swe699;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.hgmtrebing.swe699.database.HibernateConnection;

import java.util.Map;

public class Testing {

    public static void main (String[] args) {
        getAllCuisines();
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
}
