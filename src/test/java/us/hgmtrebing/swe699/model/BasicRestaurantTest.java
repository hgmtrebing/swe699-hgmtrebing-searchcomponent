package us.hgmtrebing.swe699.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicRestaurantTest {

    @Test
    public void restaurantIdTest () {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setId(2);
        Assertions.assertEquals(2, restaurant.getId());
    }

    @Test
    public void restaurantPublicIdTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setPublicId("test01");
        restaurant.setPublicId("test02");
        Assertions.assertEquals("test02", restaurant.getPublicId());
    }

    @Test
    public void restaurantNameTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("name01");
        restaurant.setName("name02");
        Assertions.assertEquals("name02", restaurant.getName());
    }

    @Test
    public void restaurantStreetAddressTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setStreetAddress("11111 Address Drive");
        restaurant.setStreetAddress("22222 Address Drive");
        Assertions.assertEquals("22222 Address Drive", restaurant.getStreetAddress());
    }

    @Test
    public void restaurantCityTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCity("Fairfax");
        restaurant.setCity("Ashburn");
        Assertions.assertEquals("Ashburn", restaurant.getCity());
    }

    @Test
    public void restaurantStateTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setState("Virginia");
        restaurant.setState("Minnesota");
        Assertions.assertEquals("Minnesota", restaurant.getState());
    }

    @Test
    public void restaurantZipCodeTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setZipCode(22032);
        restaurant.setZipCode(10001);
        Assertions.assertEquals(10001, restaurant.getZipCode());
    }

    @Test
    public void restaurantSearchCountTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setSearchCount(5);
        restaurant.setSearchCount(17);
        Assertions.assertEquals(17, restaurant.getSearchCount());
    }

    @Test
    public void restaurantClickCountTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setClickCount(14);
        restaurant.setClickCount(451);
        Assertions.assertEquals(451, restaurant.getClickCount());
    }

}
