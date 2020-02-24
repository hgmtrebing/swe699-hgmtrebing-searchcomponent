package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.List;
import java.util.ArrayList;

public class SearchEngine {

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

    public SearchResults getBrowseResults (SearchRequest request) {
        SearchResults results = getDummyData(request);

        return results;
    }

    public SearchResults getDummyData(SearchRequest request) {
        SearchResults results = new SearchResults();
        results.setRequest(request);

        IndividualRestaurantSearchResult chuys = new IndividualRestaurantSearchResult();
        chuys.setName("Chuy's");
        chuys.setPrice(Pricing.TWO_STAR);
        chuys.addCuisine(Cuisine.CASUAL);
        chuys.addCuisine(Cuisine.SIT_DOWN);
        chuys.addCuisine(Cuisine.TEX_MEX);
        chuys.addCuisine(Cuisine.HAPPY_HOUR_FOOD);
        chuys.setStreetAddress("11219 Lee Hwy");
        chuys.setCity("Fairfax");
        chuys.setState(State.VIRGINIA);
        chuys.setZipCode(22030);

        IndividualRestaurantSearchResult arigato = new IndividualRestaurantSearchResult();
        arigato.setName("Arigato Sushi");
        arigato.setPrice(Pricing.TWO_STAR);
        arigato.addCuisine(Cuisine.CASUAL);
        arigato.addCuisine(Cuisine.SIT_DOWN);
        arigato.addCuisine(Cuisine.SUSHI);
        arigato.addCuisine(Cuisine.JAPANESE);
        arigato.addCuisine(Cuisine.ASIAN);
        arigato.setStreetAddress("11199-A Lee Hwy");
        arigato.setCity("Fairfax");
        arigato.setState(State.VIRGINIA);
        arigato.setZipCode(22030);

        IndividualRestaurantSearchResult bombay = new IndividualRestaurantSearchResult();
        bombay.setName("Bombay Cafe");
        bombay.setPrice(Pricing.ONE_STAR);
        bombay.addCuisine(Cuisine.CASUAL);
        bombay.addCuisine(Cuisine.SIT_DOWN);
        bombay.addCuisine(Cuisine.INDIAN);
        bombay.addCuisine(Cuisine.ASIAN);
        bombay.setStreetAddress("11213 Lee Hwy E");
        bombay.setCity("Fairfax");
        bombay.setState(State.VIRGINIA);
        bombay.setZipCode(22030);

        IndividualRestaurantSearchResult bww = new IndividualRestaurantSearchResult();
        bww.setName("Buffalo Wild Wings");
        bww.setPrice(Pricing.TWO_STAR);
        bww.addCuisine(Cuisine.CASUAL);
        bww.addCuisine(Cuisine.SIT_DOWN);
        bww.addCuisine(Cuisine.GENERAL_AMERICAN); arigato.addCuisine(Cuisine.HAPPY_HOUR_FOOD);
        bww.addCuisine(Cuisine.BAR_FOOD);
        bww.setStreetAddress("11204 James Swart Cir");
        bww.setCity("Fairfax");
        bww.setState(State.VIRGINIA);
        bww.setZipCode(22030);

        results.addSearchResult(chuys);
        results.addSearchResult(arigato);
        results.addSearchResult(bombay);
        results.addSearchResult(bww);

        return results;
    }

    public static void main(String[] args) {
        SearchResults results = new SearchEngine().getDummyData(new SearchRequest());
        System.out.println(results.getResultAt(0).getName());
        System.out.println(results.getResultAt(1).getName());
        System.out.println(results.getResultAt(2).getName());
        System.out.println(results.getResultAt(3).getName());
        for (IndividualRestaurantSearchResult result : new SearchEngine().getDummyData(new SearchRequest())) {
            System.out.println(result.getName());
        }
    }
}
