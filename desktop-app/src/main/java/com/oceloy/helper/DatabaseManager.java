package com.oceloy.helper;

import com.oceloy.Env;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    static Connection con = Connector();
    static Statement stmt;

    public static Connection Connector() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                return DriverManager.getConnection("jdbc:hsqldb:file:db\\ows-db", "sierra", "guitaristic");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (os.contains("Linux")) {
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                return DriverManager.getConnection("jdbc:hsqldb:file:db/ows-db", "sierra", "guitaristic");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void create_database() throws SQLException {
        File directory = new File("Report");
        if (!directory.exists()) {
            directory.mkdir();
        }

        stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + Env.table_name
                + " (ticket INTEGER, date_in VARCHAR(30), date_out VARCHAR(30), vehicle VARCHAR(30), product_status VARCHAR(30), product_name VARCHAR(100), customer VARCHAR(100), supplier VARCHAR(100), order_id VARCHAR(30), driver VARCHAR(50), info VARCHAR(100), gross INTEGER, tare INTEGER, result INTEGER, sort INTEGER, percent DOUBLE, nett INTEGER, price INTEGER, total_price INTEGER, cash INTEGER, transfer INTEGER, out_weight INTEGER, gap INTEGER, operator VARCHAR(50), sync VARCHAR(20))");

        stmt = con.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS products (id INTEGER IDENTITY, product_name VARCHAR (100), buy_price INTEGER, sell_price INTEGER)");

        stmt = con.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS customers (id INTEGER IDENTITY, customer_name VARCHAR (100), sync VARCHAR (20))");

        stmt = con.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS suppliers (id INTEGER IDENTITY, supplier_name VARCHAR (100), sync VARCHAR (20))");

        stmt = con.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (id INTEGER IDENTITY, name VARCHAR (100), username VARCHAR(30), password VARCHAR(30), level VARCHAR(30))");

        stmt = con.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS communication(id INTEGER IDENTITY, port VARCHAR(20), baud INT, databits int, stopbits INT, parity INT, readbytes INT, PRIMARY KEY (id));");
    }
}
