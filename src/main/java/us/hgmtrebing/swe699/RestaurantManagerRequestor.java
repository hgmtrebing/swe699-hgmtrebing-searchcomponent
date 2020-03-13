package us.hgmtrebing.swe699;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.hgmtrebing.swe699.database.hibernate.HibernateConnection;
import us.hgmtrebing.swe699.database.hibernate.MysqlConnection;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RestaurantManagerRequestor {


    private String restaurantListUrl;
    private int restaurantTimerInterval;
    private TimerTask restaurantTask;
    private Timer restaurantTimer;

    private String cuisineListUrl;
    private int cuisineTimerInterval;
    private TimerTask cuisineTask;
    private Timer cuisineTimer;

    private MysqlConnection mysqlConnection;
    private HibernateConnection hibernateConnection;

    public RestaurantManagerRequestor(MysqlConnection mysqlConnection, HibernateConnection hibernateConnection) {

        this.mysqlConnection = mysqlConnection;
        this.hibernateConnection = hibernateConnection;

        this.mysqlConnection.connect();
        this.mysqlConnection.initializeDatabaseSchema(false);
        this.mysqlConnection.closeConnection();


        this.cuisineTimerInterval = 60_000 * 15;
        this.cuisineTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        this.cuisineTimer = new Timer();
        this.cuisineTimer.schedule(this.cuisineTask, 0, this.cuisineTimerInterval);

        this.restaurantTimerInterval = 60_000 * 15;
        this.restaurantTask = new TimerTask() {
            @Override
            public void run() {
                hibernateConnection.connect();
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(restaurantListUrl, String.class);
                JsonParser parser = JsonParserFactory.getJsonParser();
                Map<String, Object> responseBody = parser.parseMap(response.getBody());

                hibernateConnection.flush();
                hibernateConnection.disconnect();
            }
        };
        this.restaurantTimer = new Timer();
        this.restaurantTimer.schedule(this.restaurantTask, 0, this.restaurantTimerInterval);

    }


}
