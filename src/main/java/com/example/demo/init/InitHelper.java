package com.example.demo.init;

import com.example.demo.util.SpringContextUtil;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Djh
 */
public class InitHelper {

    private static final String VERSION_TABLE_CREATE_INIT = "" +
            "CREATE TABLE IF NOT EXISTS data_version (" +
            "version VARCHAR(32)" +
            ");";

    private static final String VERSION_INIT = "INSERT INTO data_version SELECT '0' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM data_version);";

    private static final String SELECT_VERSION = "SELECT version FROM data_version;";

    /**
     * 获取数据库数据的版本，默认为0
     * @return 数据库的版本
     */
    public static String getDataVersion(String databaseName) throws SQLException {
        DataSource dataSource = (DataSource) SpringContextUtil.getBean(databaseName);
        Statement statement = dataSource.getConnection().createStatement();
        String version = "none";
        statement.execute(VERSION_TABLE_CREATE_INIT);
        statement.execute(VERSION_INIT);
        ResultSet resultSet = statement.executeQuery(SELECT_VERSION);

        if (resultSet.next()) {
            version = resultSet.getString(1);
        }

        return version;
    }

    /**
     * 获取文件sql所有下级目录
     * @param currentVersion    当前版本
     * @return sql目录, 并返回最新的版本号
     * @throws FileNotFoundException 没有找到文件的异常
     */
    public static Map<String, Object> getInitDir(String currentVersion) throws FileNotFoundException {
        Map<String, Object> result = new HashMap<>();
        String lastVersion = currentVersion;
        result.put("version", lastVersion);
        File root = ResourceUtils.getFile("sql");
        File[] files = root.listFiles();
        List<File> dirList = new ArrayList<>();
        result.put("dir", dirList);
        if (files == null) {
            return result;
        }

        for (File file : files) {
            if (!file.isDirectory()) {
                continue;
            }

            String fileName = file.getName();
            if (!fileName.startsWith("v")) {
                continue;
            }

            String versionCode = fileName.substring(1);
            if (versionCode.compareTo(currentVersion) > 0) {
                dirList.add(file);
            }

            if (versionCode.compareTo(lastVersion) > 0) {
                lastVersion = versionCode;
            }
        }

        result.put("version", lastVersion);
        return result;
    }
}
