package com.oceloy.helper;

import com.oceloy.Env;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SyncUser {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;

    public static void api_send() {
        InternetConnection.connect();
        String connection_status = InternetConnection.connection_status;
        if (connection_status.equals("connected")) {
            try {
                stmt = con.createStatement();
                stmt.execute("DELETE FROM users");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(Env.api_url + "get-calibration-user");
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.setRequestMethod("POST");
                httpcon.setRequestProperty("Content-Type", "application/json; utf-8");
                httpcon.setRequestProperty("Accept", "Application/json");
                httpcon.setDoOutput(true);

                String jsonInput = "{\"api_key\":\"" + Env.api_key + "\"}";
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

                if (status.equals("ok")) {
                    Object users_val = jsonKey.get("users");
                    String users_string = users_val.toString();
                    JSONArray jsonarray = new JSONArray(users_string);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("name");
                        String username = jsonobject.getString("username");
                        String password = jsonobject.getString("pd");
                        String level = jsonobject.getString("level");

                        stmt = con.createStatement();
                        stmt.executeUpdate("INSERT INTO users (name, username, password, level) VALUES ('" + name
                                + "', '" + username + "', '" + password + "', '" + level + "')");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
