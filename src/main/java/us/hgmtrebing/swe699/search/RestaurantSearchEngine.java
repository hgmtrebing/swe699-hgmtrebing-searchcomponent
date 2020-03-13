package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.database.HibernateConnection;
import us.hgmtrebing.swe699.model.Restaurant;

public class RestaurantSearchEngine {

    private HibernateConnection connection;

    public RestaurantSearchEngine(HibernateConnection connection) {
        this.connection = connection;

    }

    public RestaurantSearchResults getSearchResults(RestaurantSearchRequest request) {
        RestaurantSearchResults tempResults = this.connection.search(request);
        RestaurantSearchResults newResults = new RestaurantSearchResults();
        newResults.setRequest(request);
        for (Restaurant result : tempResults) {
            if (result.getName().equalsIgnoreCase(request.getTextSearchInput())) {
                newResults.addSearchResult(result);
            }
        }

        if (newResults.getNumberOfSearchResults() > 0) {
            newResults.setRestaurantSearchResultsType(us.hgmtrebing.swe699.search.RestaurantSearchResultsType.SUCCESSFUL_SEARCH_RESULTS);
            return newResults;
        } else {
            newResults = getDefaultResults(request);
            newResults.setRestaurantSearchResultsType(us.hgmtrebing.swe699.search.RestaurantSearchResultsType.FAILED_SEARCH_RESULTS);
            return newResults;
        }
    }

    public RestaurantSearchResults getDefaultResults(RestaurantSearchRequest request) {
        RestaurantSearchResults results = this.connection.search(request);

        return results;
    }

    public static void main(String[] args) {
    }

}
