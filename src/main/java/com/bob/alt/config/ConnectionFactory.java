package com.bob.alt.config;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionFactory {

    private static String pathUrl = "./src/main/resources/application.properties";
    private final DataSource dataSource;

    private interface Singleton {
        ConnectionFactory INSTANCE = new ConnectionFactory(pathUrl);
    }

    private ConnectionFactory(String pathUrl) {

        Properties properties = new Properties();
        String logUsername = "";
        String logPassword = "";
        String logUrl = "";

        try (InputStream input = new FileInputStream(pathUrl)) {
            // load a properties file
            properties.load(input);

            // get the property value
            logUsername = properties.getProperty("spring.datasource.username");
            logPassword = properties.getProperty("spring.datasource.password");
            logUrl = properties.getProperty("spring.datasource.url");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        properties.setProperty("user", logUsername);
        properties.setProperty("password", logPassword);

        GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<>();
        DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(logUrl, properties);
        new PoolableConnectionFactory(
                connectionFactory, pool, null, "SELECT 1", 3, false, false, Connection.TRANSACTION_READ_COMMITTED
        );

        this.dataSource = new PoolingDataSource(pool);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}