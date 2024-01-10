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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


@Service
@Slf4j
public class ConsumeMessageService {
    private static Logger logger = LogManager.getLogger(ConsumeMessageService.class);

    public void consumeMessage(String messageBody)  {

       try {


        System.out.println("Consumed message:" +messageBody);
        ReportController reportController=new ReportController();
        ExcelGenerator2 excelGenerator2=new ExcelGenerator2();
        ArrayList<ZabbixReport> zabbixReport= reportController.listProblems();
        JSONArray jsArray = new JSONArray(zabbixReport);
        RabbitMQBody rabbitMQBody=parseRabbitMQBodyJson(messageBody);


        assert zabbixReport != null;
        //TelegramSender.sendToTelegram(String.valueOf(jsArray),excelGenerator2.createExcel());
            System.out.println(rabbitMQBody.getMail()+ " adresine mail Gönderilecek");
        GmailSender.sendEmail(rabbitMQBody.getMail(),"Zabbix Report",String.valueOf(jsArray),excelGenerator2.createExcel(zabbixReport),"Zabbix Report.xlsx");
            System.out.println("Mail gönderme aşaması tamamlandı");

        //RabbitMQBody rabbitMQBody= parseRabbitMQBodyJson(messageBody);
       //insertDb(rabbitMQBody);
        }catch (Exception e){
            System.out.println("hata ile karşılaştı.."+ e.getMessage());
            e.printStackTrace();
        }
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
