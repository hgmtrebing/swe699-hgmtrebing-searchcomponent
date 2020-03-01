package us.hgmtrebing.swe699.database.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MysqlConnector {
    private static final Logger log = LoggerFactory.getLogger(MysqlConnector.class);

    private String username = "swe699_user01";
    private String password = "password";
    private String connectionUrl = "jdbc:mysql://localhost:3306/swe699_01";
    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    public static void main (String[] args) {
        MysqlConnector connector = new MysqlConnector();
        connector.connect();
        connector.initializeDatabaseSchema(true);
        connector.initializeDummyData();
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
                String drop01 = "DROP TABLE IF EXISTS tbl_restaurant_cuisine_matching";
                PreparedStatement dropTableStatement = this.connection.prepareStatement(drop01);
                dropTableStatement.executeUpdate();

                String drop02 = "DROP TABLE IF EXISTS tbl_restaurants";
                dropTableStatement = this.connection.prepareStatement(drop02);
                dropTableStatement.executeUpdate();

                String drop03 = "DROP TABLE IF EXISTS tbl_cuisines";
                dropTableStatement = this.connection.prepareStatement(drop03);
                dropTableStatement.executeUpdate();


                log.info("All old tables dropped. Ready to reinitialize schema on {}.", this.connectionUrl);
            }

            String createRestaurantTableString = "CREATE TABLE IF NOT EXISTS tbl_restaurants " +
                    "(col_restaurant_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "col_restaurant_public_id INTEGER NOT NULL, " +
                    "col_restaurant_name VARCHAR(255) NOT NULL, " +
                    "col_restaurant_street_address VARCHAR(255) NOT NULL, " +
                    "col_restaurant_city VARCHAR(255) NOT NULL, " +
                    "col_restaurant_state VARCHAR(255) NOT NULL, " +
                    "col_restaurant_zip INTEGER NOT NULL, " +
                    "col_restaurant_pricing INTEGER NOT NULL, " +
                    "col_restaurant_search_appearances INTEGER NOT NULL, " +
                    "col_restaurant_search_clicks INTEGER NOT NULL, " +
                    "PRIMARY KEY ( col_restaurant_id ))";
            PreparedStatement createTableStatement = this.connection.prepareStatement(createRestaurantTableString);
            createTableStatement.executeUpdate();

            String createCuisineTableString = "CREATE TABLE IF NOT EXISTS tbl_cuisines " +
                    "(col_cuisine_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "col_cuisine_name VARCHAR(255) NOT NULL UNIQUE, " +
                    "PRIMARY KEY ( col_cuisine_id )) ";
            createTableStatement = this.connection.prepareStatement(createCuisineTableString);
            createTableStatement.executeUpdate();

            String createRestaurantCuisineTable = "CREATE TABLE IF NOT EXISTS tbl_restaurant_cuisine_matching" +
                    "(col_association_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "col_restaurant_id INTEGER NOT NULL, " +
                    "col_cuisine_id INTEGER NOT NULL, " +
                    "PRIMARY KEY ( col_association_id ), " +
                    "INDEX (col_restaurant_id), " +
                    "INDEX (col_cuisine_id), " +
                    "FOREIGN KEY (col_restaurant_id) REFERENCES tbl_restaurants(col_restaurant_id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (col_restaurant_id) REFERENCES tbl_cuisines(col_cuisine_id) ON DELETE CASCADE ON UPDATE CASCADE)";
            createTableStatement = this.connection.prepareStatement(createRestaurantCuisineTable);
            createTableStatement.executeUpdate();

            log.info("Database schema successfully initialized on {}.", this.connectionUrl);
        } catch (Exception e) {
            log.error("Error occurred while trying to initialize Database Schema: {}", e.toString());
        }
    }

    public void insertCuisine(Cuisine cuisine) {
        try {
            String insertion =  "INSERT INTO tbl_cuisines (col_cuisine_name) VALUES (?) ";
            PreparedStatement statement = this.connection.prepareStatement(insertion);
            statement.setString(1, cuisine.getName());
            statement.executeUpdate();
        } catch (Exception e) {
            log.error("Error occurred while trying to insert a Cuisine into the database: {}", e.toString());
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

    public void initializeDummyData() {
        this.insertCuisine(new Cuisine("Fast Food"));
        this.insertCuisine(new Cuisine("Barbecue"));
        this.insertCuisine(new Cuisine("Cajun"));
        this.insertCuisine(new Cuisine("Soul Food"));
        this.insertCuisine(new Cuisine("Fried Food"));
        this.insertCuisine(new Cuisine("Classic American"));
    }

}
