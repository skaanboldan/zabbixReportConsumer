package com.consumer.zabbix.report.zabbixReportConsumer.DbController;

import com.consumer.zabbix.report.zabbixReportConsumer.RabbitMQController.RabbitMQBody;

import java.io.IOException;
import java.sql.*;

public class DatabaseController {


    
    public long insertExcelInfo(RabbitMQBody rabbitMQBody) {
        //TODO: 1. schema must add, 2. table add, 3. change insertSQL string also pstmt values must be change.
        String insertSQL = "INSERT INTO excelInfo(excelByteArray,excelName,insertDate) "
                + "VALUES(?,?,?)";

        long id = 0;
        DBConnectionManager connectionManager=new DBConnectionManager();
        try (
                Connection conn = connectionManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, rabbitMQBody.getMessage());
            pstmt.setString(2, rabbitMQBody.getMessage());
            pstmt.setTimestamp(3, rabbitMQBody.getDate());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
}
