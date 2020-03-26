package us.hgmtrebing.swe699.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Restaurant;
import us.hgmtrebing.swe699.search.RestaurantSearchRequest;
import us.hgmtrebing.swe699.search.RestaurantSearchResults;
import us.hgmtrebing.swe699.search.RestaurantSearchResultsType;

import javax.persistence.Query;
import java.util.List;

public class HibernateConnection {
    private static final Logger log = LoggerFactory.getLogger(HibernateConnection.class);
    private SessionFactory factory = null;
    private Session session = null;

    public HibernateConnection() { }

    public boolean connect() {
        boolean successful = false;
        try {
            this.factory = new Configuration().configure().buildSessionFactory();
            this.session = factory.openSession();
            successful = true;
        } catch (Exception e) {
            successful = false;
            log.warn("Error occurred while trying to connect to Hibernate Session", e);
        }
        return successful;
    }

    public boolean disconnect() {
        boolean successful = false;
        try {
           this.session.close();
           this.factory.close();
           successful = true;
           log.info("Successfully disconnected Hibernate Session");
        } catch (Exception e) {
            log.warn("Error encountered when trying to disconnect Hibernate session.");
        }
        return successful;
    }

    public boolean saveOrUpdate(Object... objects) {
        boolean successful = false;
        try {

            this.session.beginTransaction();
            for (Object o  : objects) {
                this.session.saveOrUpdate(o);
            }
            this.session.getTransaction().commit();

            successful = true;
            log.info("{} objects saved to the database.", objects.length);
        } catch (Exception e) {
            successful = false;
            this.session.getTransaction().rollback();
            log.warn("Error encountered when attempting to save objects to the database.", e);
        }
        return successful;
    }

    public boolean flush() {
        boolean successful = false;
        try {
            this.session.beginTransaction();
            this.session.flush();
            this.session.getTransaction().commit();
            successful = true;
            log.info("Hibernate Connection successfully flushed");
        } catch (Exception e) {
            this.session.getTransaction().rollback();
            successful = false;
            log.warn("Error encountered when trying to flush the Hibernate Connection", e);
        }
        return successful;
    }

    public List<Cuisine> getAllCuisines() {
        this.session.beginTransaction();
        Query query = this.session.createQuery("from Cuisine as cuisine");
        List cuisines = query.getResultList();
        this.session.getTransaction().commit();
        return cuisines;
    }

    public RestaurantSearchResults search(RestaurantSearchRequest request) {
        try {
            this.session.beginTransaction();
            /*
            CriteriaBuilder cb = this.session.getCriteriaBuilder();
            CriteriaQuery<Restaurant> cq = cb.createQuery(Restaurant.class);
            Root<Restaurant> root = cq.from(Restaurant.class);

            List<Predicate> predicates = new ArrayList<>();

            if (request.getTextSearchInput() != null) {
                predicates.add(cb.like(root.get("name"), request.getTextSearchInput()));
            }

            if (predicates.size() > 0) {
                Predicate[] predicates1 = new Predicate[predicates.size()];
                for (int i = 0; i < predicates.size(); i++) {
                   predicates1[i] = predicates.get(i);
                }
                cq.select(root).where(predicates1);
            }
            List<Restaurant> restaurants = session.createQuery(cq).getResultList();
             */

            String queryString = "from Restaurant as restaurant";
            boolean whereInserted = false;

            if (request.getTextSearchInput() != null) {
                if (whereInserted) {
                    queryString += " and name like :restaurantName";
                } else {
                    queryString += " where name like :restaurantName";
                    whereInserted = true;
                }
            }

            if (request.getStreetAddress() != null) {
                if (whereInserted) {
                    queryString += " and streetAddress = :restaurantStreet";
                } else {
                    queryString += " where streetAddress = :restaurantStreet";
                    whereInserted = true;
                }
            }

            if (request.getCity() != null) {
                if (whereInserted) {
                    queryString += " and city = :restaurantCity";
                } else {
                    queryString += " where city = :restaurantCity";
                    whereInserted = true;
                }
            }

            Query query = session.createQuery(queryString);

            if (request.getTextSearchInput() != null) {
                query.setParameter("restaurantName", "%"+request.getTextSearchInput()+"%");
            }

            if (request.getStreetAddress() != null) {
                query.setParameter("restaurantStreet", request.getStreetAddress());
            }

            if (request.getCity() != null) {
                query.setParameter("restaurantCity", request.getCity());
            }

            List restaurants = query.getResultList();

            session.getTransaction().commit();
            RestaurantSearchResults results = new RestaurantSearchResults();
            for (Object restaurant : restaurants) {
                results.addSearchResult((Restaurant) restaurant);
            }
            results.setRequest(request);
            return results;

        } catch (Exception e) {
            log.warn("Error encountered during query", e);
        }
        RestaurantSearchResults results = new RestaurantSearchResults();
        results.setRequest(request);
        results.setRestaurantSearchResultsType(RestaurantSearchResultsType.FAILED_SEARCH_RESULTS);
        return results;
    }
}
