package us.hgmtrebing.swe699.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.List;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private Swe699Controller swe699Controller;

    @Autowired
    private Swe699RestController swe699RestController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void acceptanceTest01() {
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        List<Restaurant> restaurants = connection.getAllRestaurants();
        List<Cuisine> cuisines = connection.getAllCuisines();
        Assertions.assertTrue(restaurants.size() > 0);
        Assertions.assertTrue(cuisines.size() > 0);
        connection.closeConnection();
        Assertions.assertNotNull(swe699Controller);
        Assertions.assertNotNull(swe699RestController);
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" + port +"/search_statistics", String.class));
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" + port +"/", String.class));
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" + port +"/advanced_search", String.class));
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" + port +"/browse", String.class));
        Assertions.assertNotNull(testRestTemplate.getForObject("http://localhost:" + port +"/restaurant_browse", String.class));
    }

}
