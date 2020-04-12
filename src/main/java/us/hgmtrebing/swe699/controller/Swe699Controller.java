package us.hgmtrebing.swe699.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.Restaurant;
import us.hgmtrebing.swe699.search.RestaurantBrowseRequest;
import us.hgmtrebing.swe699.search.RestaurantSearchEngine;
import us.hgmtrebing.swe699.search.RestaurantSearchRequest;
import us.hgmtrebing.swe699.search.RestaurantSearchResults;

import java.util.Arrays;
import java.util.List;

@Controller
public class Swe699Controller {

    Logger log = LoggerFactory.getLogger(this.getClass());
    private MysqlConnection connection;


    public Swe699Controller() {
        super();
        this.connection = new MysqlConnection();
    }

    @RequestMapping("/test")
    public String test() {
        return "Goodbye peeps!";
    }

    @RequestMapping("/advanced_search")
    public String advancedSearch() {
        return "advanced_search";
    }

    @RequestMapping("/browse")
    public String browse(Model model) {
        // HibernateConnection connection = new HibernateConnection();
        // connection.connect();
        MysqlConnection connection = new MysqlConnection();
        connection.connect();

        List cuisines = connection.getAllCuisines();
        connection.closeConnection();
        // connection.disconnect();

        model.addAttribute("cuisines", cuisines);
        model.addAttribute("pricings", Pricing.values());
        return "browse";
    }

    @RequestMapping("/restaurant_browse")
    private String restaurantBrowse(@RequestParam("cuisine") String cuisineList,
                                    Model model) {
        RestaurantBrowseRequest browseRequest = new RestaurantBrowseRequest();

        MysqlConnection mysqlConnection = new MysqlConnection();
        mysqlConnection.connect();
        String[] cuisines = cuisineList.split(",");
        for (String cuisine : cuisines) {
            browseRequest.addCuisine(mysqlConnection.getCuisineByName(cuisine));
        }

        RestaurantSearchEngine engine = new RestaurantSearchEngine(this.connection);
        RestaurantSearchResults results = engine.getBrowseResults(browseRequest);
        model.addAttribute("results", results);

        return "search_results";
    }

    @RequestMapping("/restaurant_search")
    public String restaurantSearch( @RequestParam("text-search") String textSearch,
                                    @RequestParam(value = "street-address", defaultValue="") String streetAddress,
                                    @RequestParam(value = "city", defaultValue="") String city,
                                    @RequestParam(value = "state", defaultValue="") String state,
                                    @RequestParam(value = "zipcode", defaultValue="22030") String zipCode,
                                    Model model) {

        log.info("Search request received with the following parameters: \n{} \n{} \n{} \n{} \n{}", textSearch, streetAddress, city, state, zipCode);

        RestaurantSearchRequest request = new RestaurantSearchRequest();
        request.setTextSearchInput(textSearch);
        request.setStreetAddress(streetAddress.equals("") ? null : streetAddress);
        request.setCity(city.equals("") ? null : city);
        request.setState(state.equals("") ? null : state);
        request.setZipCode(Integer.parseInt(zipCode));

        RestaurantSearchEngine engine = new RestaurantSearchEngine(this.connection);
        RestaurantSearchResults results = engine.getSearchResults(request);
        results.incrementAllSearchResults();

        model.addAttribute("results", results);

        return "search_results";
    }

}
