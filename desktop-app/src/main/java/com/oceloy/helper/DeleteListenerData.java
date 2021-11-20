package com.oceloy.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.oceloy.Env;

import org.json.JSONObject;

public class DeleteListenerData {

    public static String ticket;

    public static void api_send() {
        InternetConnection.connect();
        String connection_status = InternetConnection.connection_status;
        if (connection_status.equals("connected")) {

            try {
                URL url = new URL(Env.api_url + "delete-listener-data");
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.setRequestMethod("POST");
                httpcon.setRequestProperty("Content-Type", "application/json; utf-8");
                httpcon.setRequestProperty("Accept", "Application/json");
                httpcon.setDoOutput(true);

                String jsonInput = "{\"api_key\":\"" + Env.api_key + "\", \"ticket\": \"" + ticket + "\"}";
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
                String status = status_val.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
