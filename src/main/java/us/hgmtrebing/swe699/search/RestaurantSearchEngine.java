package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.database.HibernateConnection;
import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestaurantSearchEngine {

    private HibernateConnection connection;

    public RestaurantSearchEngine(HibernateConnection connection) {
        this.connection = connection;

    }

    public RestaurantSearchResults getSearchResults(RestaurantSearchRequest request) {
        RestaurantSearchResults tempResults = this.connection.search(request);
        /*
        RestaurantSearchResults newResults = new RestaurantSearchResults();
        newResults.setRequest(request);
        for (Restaurant result : tempResults) {
            if (result.getName().equalsIgnoreCase(request.getTextSearchInput())) {
                newResults.addSearchResult(result);
            }
        }
         */

        if (tempResults.getNumberOfSearchResults() > 0) {
            tempResults.setRestaurantSearchResultsType(us.hgmtrebing.swe699.search.RestaurantSearchResultsType.SUCCESSFUL_SEARCH_RESULTS);
            return tempResults;
        } else {
            tempResults = getDefaultResults(request);
            tempResults.setRestaurantSearchResultsType(us.hgmtrebing.swe699.search.RestaurantSearchResultsType.FAILED_SEARCH_RESULTS);
            return tempResults;
        }
    }

    public RestaurantSearchResults getBrowseResults(RestaurantBrowseRequest request) {
        MysqlConnection connection = new MysqlConnection();
        connection.connect();

        RestaurantSearchResults results = new RestaurantSearchResults();
        for (Cuisine cuisine : request.getAllCuisines()) {
            Set<Restaurant> restaurants= connection.getRestaurantsByCuisineId(cuisine.getId());
            for (Restaurant restaurant : restaurants) {
                results.addSearchResult(restaurant);
            }
        }

        if (results.getNumberOfSearchResults() < 1) {
            results = getDefaultResults(null);
            results.setRestaurantSearchResultsType(RestaurantSearchResultsType.FAILED_SEARCH_RESULTS);
        } else {
            results.setRestaurantSearchResultsType(RestaurantSearchResultsType.SUCCESSFUL_SEARCH_RESULTS);
        }
        RestaurantSearchRequest searchRequest = new RestaurantSearchRequest();
        searchRequest.setRestaurantSearchType(RestaurantSearchType.USER_BROWSE);
        searchRequest.setTextSearchInput("Just Browsing");
        results.setRequest(searchRequest);

        return results;
    }

    public RestaurantSearchResults getDefaultResults(RestaurantSearchRequest request) {
        MysqlConnection connection = new MysqlConnection();
        connection.connect();
        List<Restaurant> restaurants = connection.getAllRestaurants();
        RestaurantSearchResults results = new RestaurantSearchResults();
        for (Restaurant restaurant : restaurants) {
            results.addSearchResult(restaurant);
        }
        results.setRequest(request);

        return results;
    }

    public static void main(String[] args) {
    }

}
