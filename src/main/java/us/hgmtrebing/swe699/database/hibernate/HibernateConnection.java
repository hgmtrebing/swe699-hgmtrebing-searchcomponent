package us.hgmtrebing.swe699.database.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.hgmtrebing.swe699.search.ResultsType;
import us.hgmtrebing.swe699.search.SearchRequest;
import us.hgmtrebing.swe699.search.SearchResults;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public SearchResults search(SearchRequest request) {
        try {
            this.session.beginTransaction();
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

            SearchResults results = new SearchResults();
            for (Restaurant restaurant : restaurants) {
                results.addSearchResult(restaurant);
            }
            results.setRequest(request);
            return results;

        } catch (Exception e) {
            log.warn("Error encoutered during query", e);
        }
        SearchResults results = new SearchResults();
        results.setRequest(request);
        results.setResultsType(ResultsType.FAILED_SEARCH_RESULTS);
        return results;
    }
}
