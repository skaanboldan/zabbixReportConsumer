package com.consumer.zabbix.report.zabbixReportConsumer;

import com.consumer.zabbix.report.zabbixReportConsumer.TelegramSender.TelegramSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZabbixReportConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZabbixReportConsumerApplication.class, args);
	}

}
