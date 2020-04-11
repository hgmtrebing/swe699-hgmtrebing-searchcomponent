package us.hgmtrebing.swe699.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.hgmtrebing.swe699.model.Cuisine;

import java.util.Iterator;

public class RestaurantBrowseRequestTests {

    @Test
    public void restaurantBrowseRequestTest01() {
        Cuisine c1 = new Cuisine();
        c1.setId(01);
        c1.setName("Fast Food");

        Cuisine c2 = new Cuisine();
        c2.setId(02);
        c2.setName("Chinese");

        RestaurantBrowseRequest r = new RestaurantBrowseRequest();
        r.addCuisine(c1);
        r.addCuisine(c2);

        Iterable<Cuisine> iterable = r.getAllCuisines();
        Iterator<Cuisine> iterator = iterable.iterator();

        Cuisine c3 = (Cuisine) iterator.next();
        Assertions.assertEquals(c2, c3);

        Cuisine c4 = (Cuisine) iterator.next();
        Assertions.assertEquals(c1, c4);
    }
}
