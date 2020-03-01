package us.hgmtrebing.swe699.database.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Restaurant.restaurantTableName)
public class Restaurant {

    public static final String restaurantTableName = "tbl_restaurants";
    public static final String restaurantIdName = "col_restaurant_id";
    public static final String restaurantPublicIdName = "col_restaurant_public_id";
    public static final String restaurantNameName = "col_restaurant_name";
    public static final String restaurantStreetAddressName = "col_restaurant_street_address";
    public static final String restaurantCityName = "col_restaurant_city";
    public static final String restaurantStateName = "col_restaurant_state";
    public static final String restaurantZipcodeName = "col_restaurant_zipcode";
    public static final String restaurantPricingName = "col_restaurant_pricing";
    public static final String restaurantSearchCountName = "col_restaurant_search_count";
    public static final String restaurantClickCountName = "col_restaurant_click_count";

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=Restaurant.restaurantIdName, unique = true, nullable = false)
    private int id;

    @Setter
    @Getter
    @Column(name=Restaurant.restaurantPublicIdName, unique = false, nullable = false)
    private int publicId;

    @Setter
    @Getter
    @Column(name=Restaurant.restaurantNameName, unique = false, nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantStreetAddressName, unique = false, nullable = false)
    private String streetAddress;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantCityName, unique = false, nullable = false)
    private String city;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantStateName, unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantZipcodeName, unique = false, nullable = false)
    private int zipCode;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantPricingName, unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Pricing pricing;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantSearchCountName, unique = false, nullable = false)
    private int searchCount;

    @Getter
    @Setter
    @Column(name=Restaurant.restaurantClickCountName, unique = false, nullable = false)
    private int clickCount;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = MysqlUtil.associationTableName, joinColumns = {
            @JoinColumn(name = MysqlUtil.associationRestaurantIdName , nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = MysqlUtil.associationCuisineIdName,
                    nullable = false, updatable = false) })
    private Set<Cuisine> cuisines = new HashSet<>();

    public Restaurant() {
        this.searchCount = 0;
        this.clickCount = 0;
    }

    public void incrementSearchCount() {
        this.searchCount++;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }

    public void addCuisine(Cuisine cuisine) {
        if (cuisine != null) {
            this.cuisines.add(cuisine);
        }
    }
}
