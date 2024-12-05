package org.itt.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnector {
    private static volatile DataBaseConnector instance = null;
    private static Connection connection;

    private DataBaseConnector() {
    }

    public static DataBaseConnector getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            synchronized (DataBaseConnector.class) {
                if (instance == null) {
                    try {
                        Properties props = new Properties();
                        try (InputStream input = DataBaseConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
                            props.load(input);
                        }
                        String url = props.getProperty("db.url");
                        String username = props.getProperty("db.username");
                        String password = props.getProperty("db.password");

                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(url, username, password);
                        instance = new DataBaseConnector();
                    } catch (Exception e) {
                        throw new SQLException("Unable to connect to the database", e);
                    }
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
