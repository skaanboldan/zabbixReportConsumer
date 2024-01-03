package com.consumer.zabbix.report.zabbixReportConsumer.DbController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {
    public Connection connect() throws SQLException, IOException {
        return DriverManager.getConnection("${test.db.url}"+"${test.db.schema}", "${test.db.user}", "${test.db.password}");
    }

    }

