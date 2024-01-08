package com.consumer.zabbix.report.zabbixReportConsumer.TelegramSender;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TelegramSender {
    public static void sendToTelegram(String message, String excel) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        String apiToken = "6873174503:AAFqF77vMduuwDGnmus_x6w3TFhxVeniv3I";
        String chatId = "730442642";
        String text = message ;
        File targetFile = new File(excel);
        urlString = String.format(urlString, apiToken, chatId, text);
        System.out.println(urlString);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}