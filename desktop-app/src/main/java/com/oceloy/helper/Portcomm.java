package com.oceloy.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Portcomm {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;

    public static String com;
    public static int baud;
    public static int databits;
    public static int stopbits;
    public static int parity;
    public static int readbytes;

    public static void setportcom() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication WHERE id=0");

            if (rs.next()) {
                com = rs.getString("port");
                baud = rs.getInt("baud");
                databits = rs.getInt("databits");
                stopbits = rs.getInt("stopbits");
                parity = rs.getInt("parity");
                readbytes = rs.getInt("readbytes");
            } else {
                stmt = con.createStatement();
                rs = stmt.executeQuery(
                        "INSERT INTO communication (port, baud, databits, stopbits, parity, readbytes) VALUES ('COM1', '2400', '8', '1', '0', '10')");

                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM communication WHERE id=0");

                while (rs.next()) {
                    com = rs.getString("port");
                    baud = rs.getInt("baud");
                    databits = rs.getInt("databits");
                    stopbits = rs.getInt("stopbits");
                    parity = rs.getInt("parity");
                    readbytes = rs.getInt("readbytes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
