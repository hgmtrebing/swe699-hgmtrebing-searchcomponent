package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.database.hibernate.HibernateConnection;
import us.hgmtrebing.swe699.database.hibernate.Restaurant;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.List;
import java.util.ArrayList;

public class SearchEngine {

    private HibernateConnection connection;

    public SearchEngine(HibernateConnection connection) {
        this.connection = connection;

    }

    public SearchResults getSearchResults(SearchRequest request) {
        SearchResults tempResults = this.connection.search(request);
        SearchResults newResults = new SearchResults();
        newResults.setRequest(request);
        for (Restaurant result : tempResults) {
            if (result.getName().equalsIgnoreCase(request.getTextSearchInput())) {
                newResults.addSearchResult(result);
            }
        }

        if (newResults.getNumberOfSearchResults() > 0) {
            newResults.setResultsType(ResultsType.SUCCESSFUL_SEARCH_RESULTS);
            return newResults;
        } else {
            newResults = getDefaultResults(request);
            newResults.setResultsType(ResultsType.FAILED_SEARCH_RESULTS);
            return newResults;
        }
    }

    public SearchResults getDefaultResults(SearchRequest request) {
        SearchResults results = this.connection.search(request);

        return results;
    }

    public static void main(String[] args) {
    }

}
