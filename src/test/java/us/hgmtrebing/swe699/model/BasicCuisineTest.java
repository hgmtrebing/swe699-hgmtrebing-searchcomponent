package us.hgmtrebing.swe699.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class BasicCuisineTest {

    @Test
    public void cuisineNameTest() {
        Cuisine cuisine = new Cuisine();
        cuisine.setName("test01");
        cuisine.setName("test02");
        Assertions.assertEquals("test02", cuisine.getName());
    }

    @Test
    public void cuisineIdTest() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(1);
        cuisine.setId(2);
        Assertions.assertEquals(2, cuisine.getId());
    }
}
