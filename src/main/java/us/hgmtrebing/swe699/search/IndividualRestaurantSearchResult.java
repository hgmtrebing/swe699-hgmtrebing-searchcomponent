package us.hgmtrebing.swe699.search;

import lombok.Data;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.State;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndividualRestaurantSearchResult {

    private String name;
    private String streetAddress;
    private String city;
    private State state;
    private int zipCode;
    private List<Cuisine> cuisines = new ArrayList<>();
    private Pricing price;

    public IndividualRestaurantSearchResult() { }

    public void addCuisine (Cuisine cuisine) {
        this.cuisines.add(cuisine);
    }
}
