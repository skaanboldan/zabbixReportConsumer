package com.consumer.zabbix.report.zabbixReportConsumer.RabbitMQController;

import com.consumer.zabbix.report.zabbixReportConsumer.ExcelController.ExcelGenerator;
import com.consumer.zabbix.report.zabbixReportConsumer.TelegramSender.TelegramSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Slf4j
public class ConsumeMessageService {

    public void consumeMessage(String messageBody) throws IOException {
        System.out.println("Consumed message:" +messageBody);

        TelegramSender.sendToTelegram(messageBody,ExcelGenerator.createExcel());
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
