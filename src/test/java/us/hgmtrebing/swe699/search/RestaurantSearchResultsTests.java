package us.hgmtrebing.swe699.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.Iterator;

public class RestaurantSearchResultsTests {

    @Test
    public void addRestaurantTest01() {
        Restaurant r01 = new Restaurant();
        r01.setId(01);
        r01.setPublicId("02");
        r01.setName("Test 03");
        r01.setStreetAddress("04 Drive");
        r01.setCity("Test 05 City");
        r01.setState("Virginia");
        r01.setZipCode(66666);
        r01.setClickCount(7);
        r01.setSearchCount(8);

        Restaurant r02 = new Restaurant();
        r02.setId(02);
        r02.setPublicId("03");
        r02.setName("Test 04");
        r02.setStreetAddress("05 Drive");
        r02.setCity("Test 06 City");
        r02.setState("Alabama");
        r02.setZipCode(77777);
        r02.setClickCount(8);
        r02.setSearchCount(9);

        RestaurantSearchResults rsr = new RestaurantSearchResults();
        rsr.addSearchResult(r01);
        rsr.addSearchResult(r02);

        Iterator<Restaurant> iterator = rsr.iterator();
        Restaurant r03 = iterator.next();
        Restaurant r04 = iterator.next();

        Assertions.assertEquals(r01, r03);
        Assertions.assertEquals(r02, r04);
    }
}
