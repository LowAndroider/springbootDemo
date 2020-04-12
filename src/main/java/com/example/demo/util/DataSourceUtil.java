package com.example.demo.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author DJH
 */
public class DataSourceUtil {

    public static String getDataBaseName(DataSource dataSource) {
        try {
            return getDataBaseName(dataSource.getConnection());
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getDataBaseName(Connection connection) {
        DatabaseMetaData metaData;
        try {
            metaData = connection.getMetaData();
            String url = metaData.getURL();
            int start = url.lastIndexOf("/");
            int end = url.indexOf("?");
            return url.substring(start + 1,  end);
        } catch (SQLException e) {
            return null;
        }
    }
}
