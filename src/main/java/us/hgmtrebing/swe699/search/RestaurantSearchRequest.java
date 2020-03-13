package us.hgmtrebing.swe699.search;

import lombok.Data;
import us.hgmtrebing.swe699.model.State;

@Data
public class RestaurantSearchRequest {

    private RestaurantSearchType restaurantSearchType;
    private String textSearchInput;
    private String streetAddress;
    private String city;
    private State state;
    private int zipCode;

    public RestaurantSearchRequest() {
        this.restaurantSearchType = RestaurantSearchType.UNKNOWN;
        this.textSearchInput = null;
        this.streetAddress = null;
        this.city = null;
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
