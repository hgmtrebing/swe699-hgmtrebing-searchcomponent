package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.List;
import java.util.ArrayList;

public class SearchEngine {

    public SearchResults getSearchResults(SearchRequest request) {
        return getDummyData(request);
    }

    public SearchResults getBrowseResults (BrowseRequest request) {
        SearchResults results = new SearchResults();

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
        arigato.setName("Bombay Cafe");
        arigato.setPrice(Pricing.ONE_STAR);
        arigato.addCuisine(Cuisine.CASUAL);
        arigato.addCuisine(Cuisine.SIT_DOWN);
        arigato.addCuisine(Cuisine.INDIAN);
        arigato.addCuisine(Cuisine.ASIAN);
        arigato.setStreetAddress("11213 Lee Hwy E");
        arigato.setCity("Fairfax");
        arigato.setState(State.VIRGINIA);

        IndividualRestaurantSearchResult bww = new IndividualRestaurantSearchResult();
        arigato.setName("Buffalo Wild Wings");
        arigato.setPrice(Pricing.TWO_STAR);
        arigato.addCuisine(Cuisine.CASUAL);
        arigato.addCuisine(Cuisine.SIT_DOWN);
        arigato.addCuisine(Cuisine.GENERAL_AMERICAN);
        arigato.addCuisine(Cuisine.HAPPY_HOUR_FOOD);
        arigato.addCuisine(Cuisine.BAR_FOOD);
        arigato.setStreetAddress("11204 James Swart Cir");
        arigato.setCity("Fairfax");
        arigato.setState(State.VIRGINIA);

        results.addSearchResult(chuys);
        results.addSearchResult(arigato);
        results.addSearchResult(bombay);
        results.addSearchResult(bww);

        return results;
    }
}
