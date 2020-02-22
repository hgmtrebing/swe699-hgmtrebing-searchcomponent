package us.hgmtrebing.swe699.search;

import lombok.Data;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;

import java.util.List;

@Data
public class BrowseRequest {
    private int zipCode;
    private List<Cuisine> cusines;
    private List<Pricing> prices;

    public BrowseRequest() { }

    public void processCuisineQueryString(String cuisines) {

    }

    public void processPricingQueryString(String pricing) {

    }
}
