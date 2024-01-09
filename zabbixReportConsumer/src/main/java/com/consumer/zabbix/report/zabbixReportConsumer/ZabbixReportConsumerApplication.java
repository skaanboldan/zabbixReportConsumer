package com.consumer.zabbix.report.zabbixReportConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;


@SpringBootApplication
public class ZabbixReportConsumerApplication {

	public static void main(String[] args) {
		try {
			String  address = InetAddress.getByName("www.google.com").getHostAddress();
			InetAddress inet = InetAddress.getByName(address);
			System.out.println("Sending Ping Request to " + address);
			if(inet.isReachable(50000)){
				System.out.println("Host is reachable");
			}
			else{
				System.out.println("Host is not reachable");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SpringApplication.run(ZabbixReportConsumerApplication.class, args);

	}

}
