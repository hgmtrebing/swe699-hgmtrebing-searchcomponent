package us.hgmtrebing.swe699.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.model.Pricing;
import us.hgmtrebing.swe699.model.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MysqlConnection {
    private static final Logger log = LoggerFactory.getLogger(MysqlConnection.class);

    private String username = "swe699_user02";
    private String password = "password01";
    private String connectionUrl = "jdbc:mysql://ec2-52-91-109-149.compute-1.amazonaws.com:3306/swe699_01";
    private String driver = "com.mysql.cj.jdbc.Driver";

    public static final String associationTableName = "tbl_restaurant_cuisine_association";
    public static final String associationIdName = "col_association_id";
    public static final String associationRestaurantIdName = "col_restaurant_id";
    public static final String associationCuisineIdName = "col_cuisine_id";


    private Connection connection;

    public static void main (String[] args) {
        MysqlConnection connector = new MysqlConnection();
        connector.connect();
        connector.initializeDatabaseSchema(true);
        connector.closeConnection();
    }

    public void connect() {
        try {
            Class.forName(this.driver);
            this.connection = DriverManager.getConnection(this.connectionUrl, this.username, this.password);
            log.info("Connection successfully established with database at {}", this.connectionUrl);
        } catch (Exception e) {
            log.error("Error occurred while trying to connect to the database at {}: {}", this.connectionUrl, e.toString());
        }
    }

    public void initializeDatabaseSchema(boolean reinitialize) {
        try {
            if (reinitialize) {
                String drop01 = "DROP TABLE IF EXISTS " + MysqlConnection.associationTableName;
                PreparedStatement dropTableStatement = this.connection.prepareStatement(drop01);
                dropTableStatement.executeUpdate();

                String drop02 = "DROP TABLE IF EXISTS " + Cuisine.cuisineTableName;
                dropTableStatement = this.connection.prepareStatement(drop02);
                dropTableStatement.executeUpdate();

                String drop03 = "DROP TABLE IF EXISTS " + Restaurant.restaurantTableName;
                 dropTableStatement = this.connection.prepareStatement(drop03);
                dropTableStatement.executeUpdate();

                log.info("All old tables dropped. Ready to reinitialize schema on {}.", this.connectionUrl);
            }

            String createRestaurantTableString = "CREATE TABLE IF NOT EXISTS " + Restaurant.restaurantTableName + "(" +
                    Restaurant.restaurantIdName + " INTEGER NOT NULL AUTO_INCREMENT, " +
                    Restaurant.restaurantPublicIdName + " VARCHAR(255) UNIQUE NOT NULL, " +
                    Restaurant.restaurantNameName + " VARCHAR(255) NOT NULL, " +
                    Restaurant.restaurantStreetAddressName + " VARCHAR(255) NOT NULL, " +
                    Restaurant.restaurantCityName + " VARCHAR(255) NOT NULL, " +
                    Restaurant.restaurantStateName + " VARCHAR(255) NOT NULL, " +
                    Restaurant.restaurantZipcodeName + " INTEGER NOT NULL, " +
                    Restaurant.restaurantPricingName + " VARCHAR(255) NOT NULL, " +
                    Restaurant.restaurantSearchCountName + " INTEGER NOT NULL, " +
                    Restaurant.restaurantClickCountName + " INTEGER NOT NULL, " +
                    "PRIMARY KEY ( " + Restaurant.restaurantIdName + " ))";
            PreparedStatement createTableStatement = this.connection.prepareStatement(createRestaurantTableString);
            createTableStatement.executeUpdate();

            String createCuisineTableString = "CREATE TABLE IF NOT EXISTS tbl_cuisines " +
                    "(col_cuisine_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "col_cuisine_name VARCHAR(255) NOT NULL UNIQUE, " +
                    "PRIMARY KEY ( col_cuisine_id )) ";
            createTableStatement = this.connection.prepareStatement(createCuisineTableString);
            createTableStatement.executeUpdate();

            String createRestaurantCuisineTable = "CREATE TABLE IF NOT EXISTS " + MysqlConnection.associationTableName + "(" +
                    MysqlConnection.associationIdName + " INTEGER NOT NULL AUTO_INCREMENT, " +
                    MysqlConnection.associationRestaurantIdName + " INTEGER NOT NULL, " +
                    MysqlConnection.associationCuisineIdName + " INTEGER NOT NULL, " +
                    "PRIMARY KEY ( " + MysqlConnection.associationIdName +" ), " +
                    "INDEX ("+ MysqlConnection.associationRestaurantIdName+"), " +
                    "INDEX ("+ MysqlConnection.associationCuisineIdName+"), " +
                    "FOREIGN KEY ("+ MysqlConnection.associationRestaurantIdName+") REFERENCES "+Restaurant.restaurantTableName+"("+Restaurant.restaurantIdName+") ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY ("+ MysqlConnection.associationRestaurantIdName+") REFERENCES "+Cuisine.cuisineTableName+"("+Cuisine.cuisineColIdName+") ON DELETE CASCADE ON UPDATE CASCADE," +
                    "UNIQUE KEY cuisine_key ("+MysqlConnection.associationRestaurantIdName + ", " + MysqlConnection.associationCuisineIdName + "))"
                    ;
            createTableStatement = this.connection.prepareStatement(createRestaurantCuisineTable);
            createTableStatement.executeUpdate();

            log.info("Database schema successfully initialized on {}.", this.connectionUrl);
        } catch (Exception e) {
            log.error("Error occurred while trying to initialize Database Schema: {}", e.toString());
        }
    }

    public List<Cuisine> getAllCuisines() {
        List<Cuisine> cuisines = new ArrayList<>();
        String query = "SELECT * FROM " + Cuisine.cuisineTableName;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Cuisine cuisine = new Cuisine();
                cuisine.setId(resultSet.getInt(Cuisine.cuisineColIdName));
                cuisine.setName(resultSet.getString(Cuisine.cuisineColNameName));
                cuisines.add(cuisine);
            }
        } catch (Exception e) {
            log.warn("Error encountered when trying to retrieve Cuisines!", e);
        } finally {
            return cuisines;
        }
    }

    public Cuisine getCuisineByName(String name) {
       Cuisine cuisine = new Cuisine();
       String query = "SELECT * FROM " + Cuisine.cuisineTableName + " WHERE " + Cuisine.cuisineColNameName + " = '" + name + "'";
       try {
           Statement statement = this.connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           while(resultSet.next()) {
               cuisine.setId(resultSet.getInt(Cuisine.cuisineColIdName));
               cuisine.setName(resultSet.getString(Cuisine.cuisineColNameName));
           }

       } catch (Exception e) {
           log.warn("Error encountered when trying to retrieve Cuisine!", e);
       } finally {
           return cuisine;
       }
    }
    public Cuisine getCuisineById(int id) {
        Cuisine cuisine = new Cuisine();
        String query = "SELECT * FROM " + Cuisine.cuisineTableName + " WHERE " + Cuisine.cuisineColIdName + " = '" + id + "'";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                cuisine.setId(resultSet.getInt(Cuisine.cuisineColIdName));
                cuisine.setName(resultSet.getString(Cuisine.cuisineColNameName));
            }

        } catch (Exception e) {
            log.warn("Error encountered when trying to retrieve Cuisine!", e);
        } finally {
            return cuisine;
        }
    }


    public void addNewCuisine(String name) {
        String query = "INSERT INTO " + Cuisine.cuisineTableName + " ( " + Cuisine.cuisineColNameName + " ) VALUES ( ? )";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (Exception e) {
            log.warn("Error encountered when trying to persist new Cuisine!", e);
        }
    }

    public Restaurant getRestaurantByPublicId(String publicId) {
        String query = "SELECT * FROM " + Restaurant.restaurantTableName + " WHERE " + Restaurant.restaurantPublicIdName + "=?";
        Restaurant restaurant = null;
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, publicId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                restaurant = new Restaurant();
                restaurant.setId(set.getInt(Restaurant.restaurantIdName));
                restaurant.setPublicId(set.getString(Restaurant.restaurantPublicIdName));
                restaurant.setName(set.getString(Restaurant.restaurantNameName));
                restaurant.setStreetAddress(set.getString(Restaurant.restaurantStreetAddressName));
                restaurant.setCity(set.getString(Restaurant.restaurantCityName));
                restaurant.setState(set.getString(Restaurant.restaurantStateName));
                restaurant.setZipCode(set.getInt(Restaurant.restaurantZipcodeName));
                restaurant.setClickCount(set.getInt(Restaurant.restaurantClickCountName));
                restaurant.setSearchCount(set.getInt(Restaurant.restaurantSearchCountName));
            }
        } catch (Exception e) {
           log.warn("Error encountered when trying to get a Restaurant by Public Id", e);
        }
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        String query = "SELECT * FROM " + Restaurant.restaurantTableName;
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(set.getInt(Restaurant.restaurantIdName));
                restaurant.setPublicId(set.getString(Restaurant.restaurantPublicIdName));
                restaurant.setName(set.getString(Restaurant.restaurantNameName));
                restaurant.setStreetAddress(set.getString(Restaurant.restaurantStreetAddressName));
                restaurant.setCity(set.getString(Restaurant.restaurantCityName));
                restaurant.setState(set.getString(Restaurant.restaurantStateName));
                restaurant.setZipCode(set.getInt(Restaurant.restaurantZipcodeName));
                restaurant.setClickCount(set.getInt(Restaurant.restaurantClickCountName));
                restaurant.setSearchCount(set.getInt(Restaurant.restaurantSearchCountName));

                Set<Cuisine> cuisines = this.getCuisinesByRestaurantId(restaurant.getId());
                restaurant.setCuisines(cuisines);

                restaurants.add(restaurant);

            }
        } catch (Exception e) {
            log.warn("Error encountered when trying to get a Restaurant by Public Id", e);
        }
        return restaurants;
    }


    public void addNewRestaurant(Restaurant restaurant) {
        String insertString = "INSERT IGNORE INTO " + Restaurant.restaurantTableName + " (" +
                Restaurant.restaurantPublicIdName + ", " +
                Restaurant.restaurantNameName + ", " +
                Restaurant.restaurantStreetAddressName + ", " +
                Restaurant.restaurantCityName + ", " +
                Restaurant.restaurantStateName + ", " +
                Restaurant.restaurantZipcodeName + ", " +
                Restaurant.restaurantClickCountName + ", " +
                Restaurant.restaurantSearchCountName + ", " +
                Restaurant.restaurantPricingName +

                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateString = "UPDATE " + Restaurant.restaurantTableName +
                " SET " + Restaurant.restaurantNameName + " =?," +
                Restaurant.restaurantStreetAddressName + " =?," +
                Restaurant.restaurantCityName + " =?," +
                Restaurant.restaurantStateName + " =?," +
                Restaurant.restaurantZipcodeName + " =?," +
                Restaurant.restaurantClickCountName + " =?," +
                Restaurant.restaurantSearchCountName + " =?," +
                Restaurant.restaurantPricingName + " =?" +
                " WHERE " + Restaurant.restaurantPublicIdName + " =?"
                ;

        try {
            PreparedStatement statement;

            // Attempt to insert the Restaurant - errors (like if the Restaurant already exists) are ignored
            statement = this.connection.prepareStatement(insertString);
            statement.setString(1, restaurant.getPublicId());
            statement.setString(2, restaurant.getName());
            statement.setString(3, restaurant.getStreetAddress());
            statement.setString(4, restaurant.getCity());
            statement.setString(5, restaurant.getState());
            statement.setInt(6, restaurant.getZipCode());
            statement.setInt(7, restaurant.getClickCount());
            statement.setInt(8, restaurant.getSearchCount());
            statement.setString(9, Pricing.ONE_STAR.toString());
            statement.executeUpdate();


            // Now update the Restaurant - if the Restaurant already existed, then it is updated with new data
            statement = this.connection.prepareStatement(updateString);
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getStreetAddress());
            statement.setString(3, restaurant.getCity());
            statement.setString(4, restaurant.getState());
            statement.setInt(5, restaurant.getZipCode());
            statement.setInt(6, restaurant.getClickCount());
            statement.setInt(7, restaurant.getSearchCount());
            statement.setString(8, Pricing.ONE_STAR.toString());
            statement.setString(9, restaurant.getPublicId());
            statement.executeUpdate();


        } catch (Exception e) {
            log.warn("Error encountered when trying insert a Restaurant", e);
        }

        int restaurantId = this.getRestaurantByPublicId(restaurant.getPublicId()).getId();
        for (Cuisine cuisine: restaurant.getCuisines()) {
            String insertCuisineString = "INSERT IGNORE INTO " + associationTableName + " ( " +
                    associationCuisineIdName + ", " + associationRestaurantIdName + ") VALUES ( ?, ? )";

            try {
                PreparedStatement statement = this.connection.prepareStatement(insertCuisineString);
                statement.setInt(1, cuisine.getId());
                statement.setInt(2, restaurantId);
                statement.executeUpdate();
            } catch ( Exception e) {
                log.warn("Error encountered when trying insert a Restaurant", e);
            }
        }
    }

    public boolean doesRestaurantExist(String publicId) {
        Restaurant restaurant = this.getRestaurantByPublicId(publicId);
        return restaurant != null && restaurant.getPublicId() != null;
    }

    public Set<Cuisine> getCuisinesByRestaurantId(int restaurantId) {
        String query = "SELECT * FROM " + associationTableName + " WHERE " + associationRestaurantIdName + " = ?";
        Set<Cuisine> cuisines = new HashSet<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, restaurantId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Cuisine c = this.getCuisineById(resultSet.getInt(associationCuisineIdName));
                cuisines.add(c);
            }

        } catch (Exception e) {
            log.warn("Error encountered when trying to query a restaurant's cuisines!", e);
        }
        return cuisines;
    }

    public void closeConnection() {
        try {
            this.connection.close();
            log.info("Connection successfully closed to {}.", this.connectionUrl);
        } catch (Exception e) {
            log.error("Error occurred while trying to close database connection: {} ", e.toString());
        }
    }
}
