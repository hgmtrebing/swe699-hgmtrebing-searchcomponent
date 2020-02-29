package us.hgmtrebing.swe699.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MysqlConnector {
    private static final Logger log = LoggerFactory.getLogger(MysqlConnector.class);

    private String username = "swe699_user01";
    private String password = "password";
    private String connectionUrl = "jdbc:mysql://localhost:3306/swe699_01";
    private String driver = "com.mysql.cj.jdbc.Driver";

    public final String restaurantTableName = "tbl_restaurants";
    public final String restaurantIdColumn = "col_restaurant_id";
    public final String restaurantIdType = "INTEGER";
    public final String restaurantNameColumn = "col_restaurant_name";
    public final String restaurantNameType = "VARCHAR(255)";

    private Connection connection;

    public static void main (String[] args) {
        MysqlConnector connector = new MysqlConnector();
        connector.connect();
        connector.initializeDatabaseSchema();
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

    public void initializeDatabaseSchema() {
        try {
            String createTableString = "CREATE TABLE restaurants " +
                    "(col_restaurant_id INTEGER NOT NULL, " +
                    "col_restaurant_public_id INTEGER NOT NULL, " +
                    "col_restaurant_name VARCHAR(255) NOT NULL, " +
                    "col_restaurant_street_address VARCHAR(255) NOT NULL, " +
                    "col_restaurant_city VARCHAR(255) NOT NULL, " +
                    "col_restaurant_state VARCHAR(255) NOT NULL, " +
                    "col_restaurant_zip INTEGER NOT NULL, " +
                    "PRIMARY KEY ( col_restaurant_id ))";
            PreparedStatement createTableStatement = this.connection.prepareStatement(createTableString);

            createTableStatement.executeUpdate();

        } catch (Exception e) {
            log.error("Error occurred while trying to initialize Database Schema: {}", e.toString());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception e) {
            log.error("Error occurred while trying to close database connection: {} ", e.toString());
        }
    }

}
