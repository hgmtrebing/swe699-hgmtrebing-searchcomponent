package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.database.MysqlConnection;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestaurantSearchEngine {

    private MysqlConnection connection;

    public RestaurantSearchEngine(MysqlConnection connection) {
        this.connection = connection;
    }

    public RestaurantSearchResults getSearchResults(RestaurantSearchRequest request) {
        this.connection.connect();
        RestaurantSearchResults tempResults = this.connection.search(request);
        this.connection.closeConnection();
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

        Set<Pricing> pricings = request.getAllPricings();
        Set<Cuisine> cuisines = request.getAllCuisines();
        RestaurantSearchResults results = new RestaurantSearchResults();

        if (cuisines.size() > 0) {
            for (Cuisine cuisine : request.getAllCuisines()) {
                this.connection.connect();
                Set<Restaurant> restaurants = this.connection.getRestaurantsByCuisineId(cuisine.getId());
                this.connection.closeConnection();
                for (Restaurant restaurant : restaurants) {
                    if (pricings.contains(restaurant.getPricing()) || pricings.size() < 1) {
                        results.addSearchResult(restaurant);
                    }
                }
            }
        } else if (pricings.size() > 0) {
            for (Pricing pricing : pricings) {
                this.connection.connect();
                Set<Restaurant> restaurants= this.connection.getAllRestaurantsByPricing(pricing);
                this.connection.closeConnection();
                for (Restaurant restaurant : restaurants) {
                    results.addSearchResult(restaurant);
                }
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
