package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.List;
import java.util.ArrayList;

public class SearchEngine {

    /*
    public SearchResults getSearchResults(SearchRequest request) {
        SearchResults tempResults = getDummyData(request);
        SearchResults newResults = new SearchResults();
        newResults.setRequest(request);
        for (IndividualRestaurantSearchResult result : tempResults) {
            if (result.getName().equalsIgnoreCase(request.getTextSearchInput())) {
                newResults.addSearchResult(result);
            }
        }

        if (newResults.getNumberOfSearchResults() > 0) {
            newResults.setResultsType(ResultsType.SUCCESSFUL_SEARCH_RESULTS);
            return newResults;
        } else {
            newResults = getBrowseResults(request);
            newResults.setResultsType(ResultsType.FAILED_SEARCH_RESULTS);
            return newResults;
        }
    }

    public SearchResults getBrowseResults(SearchRequest request) {

        return results;
    }

    public static void main(String[] args) {
    }

     */
}
