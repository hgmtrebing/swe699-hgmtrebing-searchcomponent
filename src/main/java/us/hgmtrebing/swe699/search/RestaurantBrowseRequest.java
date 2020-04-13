package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantBrowseRequest {

    private Set<Cuisine> cuisines;
    private Set<Pricing> pricings;

    public RestaurantBrowseRequest() {
        this.cuisines = new HashSet<>();
        this.pricings = new HashSet<>();
    }

    public void addCuisine(Cuisine cuisine) {
        if (cuisine != null) {
            this.cuisines.add(cuisine);
        }
    }

    public void addPricing(Pricing pricing) {
        if (pricing != null) {
            this.pricings.add(pricing);
        }
    }

    public Set<Cuisine> getAllCuisines() {
        return this.cuisines;
    }

    public Set<Pricing> getAllPricings() {
        return this.pricings;
    }

}
