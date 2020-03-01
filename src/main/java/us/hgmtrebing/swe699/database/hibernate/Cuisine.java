package us.hgmtrebing.swe699.database.hibernate;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void main (String[] args) {
        SessionFactory factory = null;
        MysqlUtil initializer = null;
        try {
            initializer = new MysqlUtil();
            initializer.connect();
            initializer.initializeDatabaseSchema(true);

            factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();

            Cuisine c01 = new Cuisine();
            c01.setName("Classic American");
            session.save(c01);

            Cuisine c02 = new Cuisine();
            c02.setName("Seafood");
            session.save(c02);

            Cuisine c03 = new Cuisine();
            c03.setName("Bar");
            session.save(c03);

            Restaurant r01 = new Restaurant();
            r01.setName("Artie's");
            r01.setPublicId(01);
            r01.setStreetAddress("3260 Old Lee Hwy");
            r01.setCity("Fairfax");
            r01.setState(State.VIRGINIA);
            r01.setZipCode(22030);
            r01.setPricing(Pricing.TWO_STAR);
            r01.incrementSearchCount();
            r01.incrementSearchCount();
            r01.incrementClickCount();
            r01.addCuisine(c01);
            r01.addCuisine(c02);
            session.save(r01);

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        } finally {
           if (factory != null) {
               factory.close();
           }

           if (initializer != null) {
               initializer.closeConnection();
           }
        }
    }
}
