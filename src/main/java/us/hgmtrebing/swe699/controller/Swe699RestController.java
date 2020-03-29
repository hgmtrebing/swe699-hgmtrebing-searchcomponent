package us.hgmtrebing.swe699.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Restaurant;
import java.util.List;

@RestController
public class Swe699RestController {
    @GetMapping("/search_statistics")
    public List<Restaurant> searchStatistics() {
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        return connection.getAllRestaurants();
    }
}
