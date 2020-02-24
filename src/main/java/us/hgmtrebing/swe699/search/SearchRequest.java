package us.hgmtrebing.swe699.search;

import lombok.Data;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.List;

@Data
public class SearchRequest {

    private SearchType searchType;
    private List<Cuisine> cusines;
    private List<Pricing> prices;
    private String textSearchInput;
    private String streetAddress;
    private String city;
    private State state;
    private int zipCode;

    public SearchRequest () {
        this.searchType = SearchType.UNKNOWN;
        this.textSearchInput = "";
        this.streetAddress = "";
        this.city = "";
        this.state = State.NONE;
        this.zipCode = 00000;
    }

    public void setState(String state) {
        this.state = State.parseFromString(state);
    }

    public void processCuisineQueryString(String cuisines) {

    }

    public void processPricingQueryString(String pricing) {

    }
}
