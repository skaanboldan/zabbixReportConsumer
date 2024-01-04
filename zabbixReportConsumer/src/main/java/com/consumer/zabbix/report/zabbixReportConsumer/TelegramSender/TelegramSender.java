package com.consumer.zabbix.report.zabbixReportConsumer.TelegramSender;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class TelegramSender {
    public static void sendToTelegram(String message, ByteArrayOutputStream excel) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        //Add Telegram token (given Token is fake)
        String apiToken = "6873174503:AAFqF77vMduuwDGnmus_x6w3TFhxVeniv3I";

        //Add chatId (given chatId is fake)
        String chatId = "730442642";
        String text = message ;
        urlString = String.format(urlString, apiToken, chatId, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}