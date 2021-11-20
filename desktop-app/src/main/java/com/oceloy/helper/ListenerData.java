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

public class ListenerData {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;

    public static String refresh_data = "stay";

    public static void api_send() {
        InternetConnection.connect();
        String connection_status = InternetConnection.connection_status;
        if (connection_status.equals("connected")) {
            try {
                URL url = new URL(Env.api_url + "get-listener-data");
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

                if (status.equals("available")) {
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
                        int cash = jsonobject.getInt("cash");
                        int transfer = jsonobject.getInt("transfer");
                        int out_weight = jsonobject.getInt("out_weight");
                        int gap = jsonobject.getInt("gap");
                        int total_price = jsonobject.getInt("total_price");
                        String operator = jsonobject.getString("operator");
                        String sync = jsonobject.getString("sync");

                        if (sync.equals("insert")) {
                            try {
                                stmt = con.createStatement();
                                stmt.executeUpdate("INSERT INTO " + Env.table_name
                                        + " (ticket, date_in, date_out, vehicle, product_status, product_name, customer, supplier, order_id, driver, info, gross, tare, result, sort, percent, nett, price, total_price, cash, transfer, out_weight, gap operator, sync) VALUES ('"
                                        + ticket + "', '" + date_in + "', '" + date_out + "', '" + vehicle + "', '"
                                        + product_status + "', '" + product_name + "', '" + customer + "', '" + supplier
                                        + "', '" + order_id + "', '" + driver + "', '" + info + "', '" + gross + "', '"
                                        + tare + "', '" + result + "', '" + sort + "', '" + percent + "', '" + nett
                                        + "', '" + price + "', '" + total_price + "', '" + cash + "', '" + transfer
                                        + "', '" + out_weight + "', '" + gap + "', '" + operator + "', '" + "ok"
                                        + "')");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (sync.equals("update")) {
                            try {
                                stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE " + Env.table_name + " SET date_in='" + date_in
                                        + "', date_out='" + date_out + "', vehicle='" + vehicle + "', product_status='"
                                        + product_status + "', product_name='" + product_name + "', customer='"
                                        + customer + "', supplier='" + supplier + "', order_id='" + order_id
                                        + "', driver='" + driver + "', info='" + info + "', gross='" + gross
                                        + "', tare='" + tare + "', result='" + result + "', sort='" + sort
                                        + "', percent='" + percent + "', nett='" + nett + "', price='" + price
                                        + "', total_price='" + total_price + "', cash='" + cash + "', transfer='"
                                        + transfer + "', out_weight='" + out_weight + "', gap='" + gap + "', sync='"
                                        + "ok" + "' WHERE ticket='" + ticket + "'");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        if (sync.equals("delete")) {
                            try {
                                stmt = con.createStatement();
                                stmt.execute("DELETE FROM " + Env.table_name + " WHERE ticket=" + ticket + "");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        refresh_data = "refresh";

                        DeleteListenerData.ticket = Integer.toString(ticket);
                        DeleteListenerData.api_send();
                    }
                } else {
                    refresh_data = "stay";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
