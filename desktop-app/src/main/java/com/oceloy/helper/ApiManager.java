package com.oceloy.helper;

import com.oceloy.Env;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiManager {
    public static String status;
    public static String messages;

    public static String api_endpoint;
    public static String api_query;

    public static void api_send() {
        try {
            URL url = new URL(Env.api_url + api_endpoint);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setRequestMethod("POST");
            httpcon.setRequestProperty("Content-Type", "application/json; utf-8");
            httpcon.setRequestProperty("Accept", "Application/json");
            httpcon.setDoOutput(true);

            String jsonInput = api_query;
            OutputStream os = httpcon.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpcon.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();

            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject jo = new JSONObject(response.toString());
            JSONObject jsonKey = jo.getJSONObject("data");
            Object status_val = jsonKey.get("status");
            status = status_val.toString();

            if (status.equals("ok")) {
                Object messages_val = jsonKey.get("messages");
                messages = messages_val.toString();
            }
        } catch (Exception e) {
            status = "refused";
            e.printStackTrace();
        }
    }
}
