package us.hgmtrebing.swe699.database.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MysqlUtil {
    private static final Logger log = LoggerFactory.getLogger(MysqlUtil.class);

    private String username = "swe699_user01";
    private String password = "password";
    private String connectionUrl = "jdbc:mysql://localhost:3306/swe699_02";
    private String driver = "com.mysql.cj.jdbc.Driver";

    public static final String associationTableName = "tbl_restaurant_cuisine_association";
    public static final String associationIdName = "col_association_id";
    public static final String associationRestaurantIdName = "col_restaurant_id";
    public static final String associationCuisineIdName = "col_cuisine_id";


    private Connection connection;

    public static void main (String[] args) {
        MysqlUtil connector = new MysqlUtil();
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
                String drop01 = "DROP TABLE IF EXISTS " + MysqlUtil.associationTableName;
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
                    Restaurant.restaurantPublicIdName + " INTEGER NOT NULL, " +
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

            String createRestaurantCuisineTable = "CREATE TABLE IF NOT EXISTS " + MysqlUtil.associationTableName + "(" +
                    MysqlUtil.associationIdName + " INTEGER NOT NULL AUTO_INCREMENT, " +
                    MysqlUtil.associationRestaurantIdName + " INTEGER NOT NULL, " +
                    MysqlUtil.associationCuisineIdName + " INTEGER NOT NULL, " +
                    "PRIMARY KEY ( " + MysqlUtil.associationIdName +" ), " +
                    "INDEX ("+MysqlUtil.associationRestaurantIdName+"), " +
                    "INDEX ("+MysqlUtil.associationCuisineIdName+"), " +
                    "FOREIGN KEY ("+MysqlUtil.associationRestaurantIdName+") REFERENCES "+Restaurant.restaurantTableName+"("+Restaurant.restaurantIdName+") ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY ("+MysqlUtil.associationRestaurantIdName+") REFERENCES "+Cuisine.cuisineTableName+"("+Cuisine.cuisineColIdName+") ON DELETE CASCADE ON UPDATE CASCADE)";
            createTableStatement = this.connection.prepareStatement(createRestaurantCuisineTable);
            createTableStatement.executeUpdate();

            log.info("Database schema successfully initialized on {}.", this.connectionUrl);
        } catch (Exception e) {
            log.error("Error occurred while trying to initialize Database Schema: {}", e.toString());
        }
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
