package com.consumer.zabbix.report.zabbixReportConsumer.RabbitMQController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ConsumeMessageService {

    public void consumeMessage(String messageBody) {
        System.out.println("Consumed message:" +messageBody);
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
