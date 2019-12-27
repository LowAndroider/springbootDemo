package com.example.demo.init;

import com.example.demo.util.SpringContextUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Djh
 */
@Component
@DependsOn("springContextUtil")
public class SystemInit {

    private final static String VERSION_TABLE_DATABASE = "demo";

    private Logger logger = LoggerFactory.getLogger(SystemInit.class);

    private String lastVersion;

    /** 保存数据库连接信息 */
    private Map<String, Statement> statementMap = new HashMap<>();
    private Map<String, Connection> connectionMap = new HashMap<>();

    @PostConstruct
    private void init()  {
        String dataVersion;
        String databaseName = getDataBaseName(VERSION_TABLE_DATABASE);
        try {
            dataVersion = InitHelper.getDataVersion(databaseName);
            lastVersion = dataVersion;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("无法获取数据库版本号！");
            return;
        }
        List<File> initFiles = new ArrayList<>();
        try {
            Map<String, Object> result = InitHelper.getInitDir(dataVersion);
            initFiles = (List<File>) result.get("dir");
            lastVersion = (String) result.get("version");
        } catch (FileNotFoundException e) {
            logger.error("无法找到初始化的sql目录！");
        }

        for (File file : initFiles) {
            if (!file.isDirectory()) {
                continue;
            }

            File[] files = file.listFiles();
            if (files == null) {
                return;
            }

            try {
                dealVersionFile(files);
                updateVersion(lastVersion);
            } catch (SQLException e) {
                logger.error("数据库初始化异常！");
                rollBack();
                throw new RuntimeException("数据库初始化异常");
            } catch (IOException e) {
                logger.error("读取文件错误！");
                rollBack();
                throw new RuntimeException("读取文件错误！");
            }

            commit();
        }
    }

    private void updateVersion(String version) throws SQLException {
        Statement statement = connectionMap.get(SystemInit.VERSION_TABLE_DATABASE).createStatement();
        statement.execute("UPDATE data_version SET version = '" + version + "'");
    }

    private void dealVersionFile(File[] files) throws IOException, SQLException {
        for (File file : files) {
            String[] s = file.getName().split("_");
            Statement statement = statementMap.get(s[0]);

            if (statement == null) {
                DataSource dataSource = (DataSource) SpringContextUtil.getBean(getDataBaseName(s[0]));
                Connection connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                connectionMap.put(s[0], connection);
                statement = connection.createStatement();
                statementMap.put(s[0], statement);
            }
            String script = readFile(file);
            System.out.println(script);
            statement.execute(script);
        }
    }

    /**
     * 回滚所有的连接
     */
    private void rollBack() {
        connectionMap.forEach((key, connection) -> {
            try {
                connection.rollback();
                logger.error(key + ": 初始化数据回滚！");
            } catch (SQLException e) {
                logger.error(key + ": 初始化数据回滚失败！");
            }
        });
    }

    private void commit() {
        connectionMap.forEach((key, connection) -> {
            try {
                connection.commit();
            } catch (SQLException e) {
                logger.error(key + ": 初始化事务提交失败！");
            }
        });
    }

    private String readFile(File file) throws IOException {
        StringBuilder script = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader lineReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = lineReader.readLine()) != null) {
            script.append(line + "\n");
        }

        return script.toString();
    }

    private String getDataBaseName(String dataBaseName) {
        return dataBaseName + "DataSource";
    }
}
