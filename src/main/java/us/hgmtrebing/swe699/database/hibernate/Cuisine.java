package us.hgmtrebing.swe699.database.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Cuisine.cuisineTableName)
public class Cuisine implements Serializable {

    public static final String cuisineTableName = "tbl_cuisines";
    public static final String cuisineColIdName = "col_cuisine_id";
    public static final String cuisineColNameName = "col_cuisine_name";

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=Cuisine.cuisineColIdName, unique = true, nullable = false)
    private int id;

    @Getter
    @Setter
    @Column(name =Cuisine.cuisineColNameName, unique = true, nullable = false, length = 255)
    private String name;

    @Getter
    @Setter
    @ManyToMany (fetch = FetchType.LAZY, mappedBy = "cuisines")
    private Set<Restaurant> restaurants = new HashSet<>();

    public Cuisine() {}

    @Override
    public String toString() {
        return this.name;
    }
}
