package us.hgmtrebing.swe699.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import us.hgmtrebing.swe699.database.HibernateConnection;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.search.RestaurantBrowseRequest;
import us.hgmtrebing.swe699.search.RestaurantSearchEngine;
import us.hgmtrebing.swe699.search.RestaurantSearchRequest;
import us.hgmtrebing.swe699.search.RestaurantSearchResults;

import java.util.Arrays;
import java.util.List;

@Controller
public class Swe699RestController {

    Logger log = LoggerFactory.getLogger(this.getClass());
    private HibernateConnection connection;


    public Swe699RestController() {
        super();
        this.connection = new HibernateConnection();
        this.connection.connect();
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
        HibernateConnection connection = new HibernateConnection();
        connection.connect();
        List cuisines = connection.getAllCuisines();
        connection.disconnect();

        model.addAttribute("cuisines", cuisines);
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

        model.addAttribute("results", results);

        return "search_results";
    }
}
