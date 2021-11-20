package com.oceloy.helper;

import com.oceloy.Env;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SyncData {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;

    public static void syncing() {

        InternetConnection.connect();
        String connection_status = InternetConnection.connection_status;
        if (connection_status.equals("connected")) {
            // insert
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " WHERE sync='insert'");
                while (rs.next()) {
                    String ticket = rs.getString("ticket");
                    String date_in = rs.getString("date_in");
                    String date_out = rs.getString("date_out");
                    String vehicle = rs.getString("vehicle");
                    String products_name = rs.getString("product_name");
                    String order_id = rs.getString("order_id");
                    String driver = rs.getString("driver");
                    String infos = rs.getString("info");
                    String gross = rs.getString("gross");
                    String tare = rs.getString("tare");
                    String result = rs.getString("result");
                    String sort = rs.getString("sort");
                    double percent = rs.getDouble("percent");
                    String nett = rs.getString("nett");
                    String price = rs.getString("price");
                    String total_price = rs.getString("total_price");
                    String cs = rs.getString("customer");
                    String sp = rs.getString("supplier");
                    String status = rs.getString("product_status");
                    String op = rs.getString("operator");
                    String cash = rs.getString("cash");
                    String transfer = rs.getString("transfer");
                    String out_weight = rs.getString("out_weight");
                    String gap = rs.getString("gap");

                    ApiManager.api_endpoint = "insert";
                    ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                            + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                            + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                            + "\", \"customer\":\"" + cs + "\", \"supplier\":\"" + sp + "\", \"order_id\":\"" + order_id
                            + "\", \"driver\":\"" + driver + "\", \"info\":\"" + infos + "\", \"gross\":\"" + gross
                            + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result + "\", \"sort\":\"" + sort
                            + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett + "\", \"price\":\"" + price
                            + "\", \"cash\":\"" + cash + "\", \"transfer\":\"" + transfer + "\", \"out_weight\":\""
                            + out_weight + "\", \"gap\":\"" + gap + "\", \"total_price\":\"" + total_price
                            + "\", \"operator\":\"" + op + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"api_key\":\""
                            + Env.api_key + "\"}";
                    ApiManager.api_send();

                    Statement insert_statement;
                    insert_statement = con.createStatement();
                    insert_statement.executeUpdate(
                            "UPDATE " + Env.table_name + " SET sync = 'ok' WHERE ticket='" + ticket + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // update
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " WHERE sync='update'");
                while (rs.next()) {
                    String ticket = rs.getString("ticket");
                    String date_in = rs.getString("date_in");
                    String date_out = rs.getString("date_out");
                    String vehicle = rs.getString("vehicle");
                    String products_name = rs.getString("product_name");
                    String order_id = rs.getString("order_id");
                    String driver = rs.getString("driver");
                    String infos = rs.getString("info");
                    String gross = rs.getString("gross");
                    String tare = rs.getString("tare");
                    String result = rs.getString("result");
                    String sort = rs.getString("sort");
                    double percent = rs.getDouble("percent");
                    String nett = rs.getString("nett");
                    String price = rs.getString("price");
                    String total_price = rs.getString("total_price");
                    String cs = rs.getString("customer");
                    String sp = rs.getString("supplier");
                    String status = rs.getString("product_status");
                    String op = rs.getString("operator");
                    String cash = rs.getString("cash");
                    String transfer = rs.getString("transfer");
                    String out_weight = rs.getString("out_weight");
                    String gap = rs.getString("gap");

                    ApiManager.api_endpoint = "update";
                    ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                            + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                            + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                            + "\", \"customer\":\"" + cs + "\", \"supplier\":\"" + sp + "\", \"order_id\":\"" + order_id
                            + "\", \"driver\":\"" + driver + "\", \"info\":\"" + infos + "\", \"gross\":\"" + gross
                            + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result + "\", \"sort\":\"" + sort
                            + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett + "\", \"price\":\"" + price
                            + "\", \"cash\":\"" + cash + "\", \"transfer\":\"" + transfer + "\", \"out_weight\":\""
                            + out_weight + "\", \"gap\":\"" + gap + "\", \"total_price\":\"" + total_price
                            + "\", \"operator\":\"" + op + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"api_key\":\""
                            + Env.api_key + "\"}";
                    ApiManager.api_send();

                    Statement update_statement;
                    update_statement = con.createStatement();
                    update_statement.executeUpdate(
                            "UPDATE " + Env.table_name + " SET sync = 'ok' WHERE ticket='" + ticket + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // edit
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " WHERE sync='edit'");
                while (rs.next()) {
                    String ticket = rs.getString("ticket");
                    String date_in = rs.getString("date_in");
                    String date_out = rs.getString("date_out");
                    String vehicle = rs.getString("vehicle");
                    String products_name = rs.getString("product_name");
                    String order_id = rs.getString("order_id");
                    String driver = rs.getString("driver");
                    String infos = rs.getString("info");
                    String gross = rs.getString("gross");
                    String tare = rs.getString("tare");
                    String result = rs.getString("result");
                    String sort = rs.getString("sort");
                    double percent = rs.getDouble("percent");
                    String nett = rs.getString("nett");
                    String price = rs.getString("price");
                    String total_price = rs.getString("total_price");
                    String cs = rs.getString("customer");
                    String sp = rs.getString("supplier");
                    String status = rs.getString("product_status");
                    String op = rs.getString("operator");
                    String cash = rs.getString("cash");
                    String transfer = rs.getString("transfer");
                    String out_weight = rs.getString("out_weight");
                    String gap = rs.getString("gap");

                    ApiManager.api_endpoint = "update";
                    ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                            + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                            + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                            + "\", \"customer\":\"" + cs + "\", \"supplier\":\"" + sp + "\", \"order_id\":\"" + order_id
                            + "\", \"driver\":\"" + driver + "\", \"info\":\"" + infos + "\", \"gross\":\"" + gross
                            + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result + "\", \"sort\":\"" + sort
                            + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett + "\", \"price\":\"" + price
                            + "\", \"cash\":\"" + cash + "\", \"transfer\":\"" + transfer + "\", \"out_weight\":\""
                            + out_weight + "\", \"gap\":\"" + gap + "\", \"total_price\":\"" + total_price
                            + "\", \"operator\":\"" + op + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"api_key\":\""
                            + Env.api_key + "\"}";
                    ApiManager.api_send();

                    Statement edit_statement;
                    edit_statement = con.createStatement();
                    edit_statement.executeUpdate(
                            "UPDATE " + Env.table_name + " SET sync = 'ok' WHERE ticket='" + ticket + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // delete
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " WHERE sync='delete'");
                while (rs.next()) {
                    String ticket = rs.getString("ticket");

                    ApiManager.api_endpoint = "delete";
                    ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"bridge_id\":\"" + Env.bridge_id
                            + "\", \"api_key\":\"" + Env.api_key + "\"}";
                    ApiManager.api_send();

                    Statement delete_statement;
                    delete_statement = con.createStatement();
                    delete_statement.execute("DELETE FROM " + Env.table_name + " WHERE ticket='" + ticket + "'");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // insert customer
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM customers WHERE sync='insert'");
                while (rs.next()) {
                    String cs = rs.getString("customer_name");
                    ApiManager.api_endpoint = "add-calibration-customers";
                    ApiManager.api_query = "{\"api_key\":\"" + Env.api_key + "\", \"customer_name\": \"" + cs + "\"}";
                    ApiManager.api_send();

                    Statement st;
                    st = con.createStatement();
                    st.executeUpdate("UPDATE customers SET sync='ok' WHERE customer_name='" + cs + "'");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // insert supplier
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM suppliers WHERE sync='insert'");
                while (rs.next()) {
                    String sp = rs.getString("supplier_name");
                    ApiManager.api_endpoint = "add-calibration-suppliers";
                    ApiManager.api_query = "{\"api_key\":\"" + Env.api_key + "\", \"supplier_name\": \"" + sp + "\"}";
                    ApiManager.api_send();

                    Statement st;
                    st = con.createStatement();
                    st.executeUpdate("UPDATE suppliers SET sync='ok' WHERE supplier_name='" + sp + "'");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
