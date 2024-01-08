package com.consumer.zabbix.report.zabbixReportConsumer.RabbitMQController;

import com.consumer.zabbix.report.zabbixReportConsumer.ExcelController.ExcelGenerator;
import com.consumer.zabbix.report.zabbixReportConsumer.ExcelController.ExcelGenerator2;
import com.consumer.zabbix.report.zabbixReportConsumer.MailController.GmailSender;
import com.consumer.zabbix.report.zabbixReportConsumer.TelegramSender.TelegramSender;
import com.consumer.zabbix.report.zabbixReportConsumer.ZabbixController.ReportController;
import com.consumer.zabbix.report.zabbixReportConsumer.ZabbixController.ZabbixReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;


@Service
@Slf4j
public class ConsumeMessageService {

    public void consumeMessage(String messageBody) throws IOException {
        System.out.println("Consumed message:" +messageBody);
        ReportController reportController=new ReportController();
        ExcelGenerator2 excelGenerator2=new ExcelGenerator2();
        ArrayList<ZabbixReport> zabbixReport= reportController.listProblems();
        JSONArray jsArray = new JSONArray(zabbixReport);


        assert zabbixReport != null;
        //TelegramSender.sendToTelegram(String.valueOf(jsArray),excelGenerator2.createExcel());
String excelPath=excelGenerator2.createExcel(zabbixReport);
System.out.println(excelPath);
        GmailSender.sendEmail("kaan.boldan@gizdanismanlik.com.tr","Zabbix Report",String.valueOf(jsArray),excelPath);

        RabbitMQBody rabbitMQBody= parseRabbitMQBodyJson(messageBody);
        insertDb(rabbitMQBody);
    }




    private void insertDb(RabbitMQBody rabbitMQBody) {

    }

    private RabbitMQBody parseRabbitMQBodyJson(String messageBody) {
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
            RabbitMQBody rabbitMQBody=objectMapper.readValue(messageBody, RabbitMQBody.class);
            return rabbitMQBody;
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }


}
