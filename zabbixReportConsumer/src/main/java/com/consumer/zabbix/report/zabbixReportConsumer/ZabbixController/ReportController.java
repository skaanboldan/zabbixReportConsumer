package com.consumer.zabbix.report.zabbixReportConsumer.ZabbixController;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;


@RestController
@Service
public class ReportController {
    @Value("${zabbix.ip}")
    private String zabbixIp ;

    @Value("${zabbix.apikey}")
    private String apiKey ;

    public ReportController() {
    }


    @CrossOrigin
    @GetMapping
    @RequestMapping(path = "/zabbix/report/getProblems")
    public ArrayList<ZabbixReport> listProblems() throws UnsupportedEncodingException, JSONException {
        JSONObject jsonObj = null;



            // load a properties file

            // get the property value and print it out
        String ZABBIX_API_URL = "http://" + "192.168.1.101" + "/zabbix/api_jsonrpc.php";
         System.out.println(ZABBIX_API_URL);
            jsonObj = new JSONObject("{\n" +
                    "           \"jsonrpc\": \"2.0\",\n" +
                    "           \"method\": \"problem.get\",\n" +
                    "           \"params\": {\n" +
                    "               \"output\": \"extend\",\n" +
                    "               \"selectAcknowledges\": \"extend\",\n" +
                    "               \"selectTags\": \"extend\",\n" +
                    "               \"selectSuppressionData\": \"extend\",\n" +
                    "               \"recent\": \"true\",\n" +
                    "               \"sortfield\": [\"eventid\"],\n" +
                    "               \"sortorder\": \"DESC\"\n" +
                    "           },\n" +
                    "           \"auth\": \"" + "5d348e052eb29421b2ca423ba3aacc8b55cf4d45e2148bfe3366bdab39b3a853" + "\",\n" +
                    "           \"id\": 1\n" +
                    "       }");



        org.apache.commons.httpclient.HttpClient client = new HttpClient();

        PutMethod putMethod = new PutMethod(ZABBIX_API_URL);
        System.err.println(putMethod.getName());
        putMethod.setRequestHeader("Content-Type", "application/json-rpc"); // content-type is controlled in api_jsonrpc.php, so set it like this
        ArrayList<ZabbixReport> zabbixReports=new ArrayList<ZabbixReport>();
        putMethod.setRequestBody(fromString(jsonObj.toString())); // put the json object as input stream into request body
        String apiResponse = "";
        try {
            client.executeMethod(putMethod); // send to request to the zabbix api
            apiResponse = putMethod.getResponseBodyAsString(); // read the result of the response
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray arr = obj.getJSONArray("result"); // notice that `"posts": [...]`
            ArrayList < String > hostId = new ArrayList < > ();

            for (int i = 0; i < arr.length(); i++) {
                ZabbixReport zr =new ZabbixReport();
                zr.setReportName(arr.getJSONObject(i).getString("name"));
                zabbixReports.add(zr);
                hostId.add(arr.getJSONObject(i).getString("name"));

            }

            return zabbixReports;

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static InputStream fromString(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("UTF-8");
        return new ByteArrayInputStream(bytes);
    }
}