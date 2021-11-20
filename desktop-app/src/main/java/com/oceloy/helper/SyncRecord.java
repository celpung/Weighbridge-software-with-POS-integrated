package com.oceloy.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.oceloy.Env;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyncRecord {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;

    public static void api_send() {
        InternetConnection.connect();
        String connection_status = InternetConnection.connection_status;
        if (connection_status.equals("connected")) {
            try {
                stmt = con.createStatement();
                stmt.execute("DELETE FROM " + Env.table_name + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(Env.api_url + "get-all");
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
                    Object listener_val = jsonKey.get("record");
                    String listener_string = listener_val.toString();
                    JSONArray jsonarray = new JSONArray(listener_string);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int ticket = jsonobject.getInt("ticket");
                        String date_in = jsonobject.getString("date_in");
                        String date_out = jsonobject.getString("date_out");
                        String vehicle = jsonobject.getString("vehicle");
                        String product_status = jsonobject.getString("product_status");
                        String product_name = jsonobject.getString("product_name");
                        String customer = jsonobject.getString("customer");
                        String supplier = jsonobject.getString("supplier");
                        String order_id = jsonobject.getString("order_id");
                        String driver = jsonobject.getString("driver");
                        String info = jsonobject.getString("info");
                        int gross = jsonobject.getInt("gross");
                        int tare = jsonobject.getInt("tare");
                        int result = jsonobject.getInt("result");
                        int sort = jsonobject.getInt("sort");
                        Double percent = jsonobject.getDouble("percent");
                        int nett = jsonobject.getInt("nett");
                        int price = jsonobject.getInt("price");
                        int total_price = jsonobject.getInt("total_price");
                        int cash = jsonobject.getInt("cash");
                        int transfer = jsonobject.getInt("transfer");
                        int out_weight = jsonobject.getInt("out_weight");
                        int gap = jsonobject.getInt("gap");
                        String operator = jsonobject.getString("operator");
                        try {
                            stmt = con.createStatement();
                            stmt.executeUpdate("INSERT INTO " + Env.table_name
                                    + " (ticket, date_in, date_out, vehicle, product_status, product_name, customer, supplier, order_id, driver, info, gross, tare, result, sort, percent, nett, price, total_price,cash, transfer, out_weight, gap, operator, sync) VALUES ('"
                                    + ticket + "', '" + date_in + "', '" + date_out + "', '" + vehicle + "', '"
                                    + product_status + "', '" + product_name + "', '" + customer + "', '" + supplier
                                    + "', '" + order_id + "', '" + driver + "', '" + info + "', '" + gross + "', '"
                                    + tare + "', '" + result + "', '" + sort + "', '" + percent + "', '" + nett + "', '"
                                    + price + "', '" + total_price + "', '" + cash + "', '" + transfer + "', '"
                                    + out_weight + "', '" + gap + "', '" + operator + "', '" + "ok" + "')");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
