package us.hgmtrebing.swe699.search;

import us.hgmtrebing.swe699.model.Cuisine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantBrowseRequest {

    private Set<Cuisine> cuisines;

    public RestaurantBrowseRequest() {
        this.cuisines = new HashSet<>();
    }

    public void addCuisine(Cuisine cuisine) {
        this.cuisines.add(cuisine);
    }

    public Iterable<Cuisine> getAllCuisines() {
        return this.cuisines;
    }

}
