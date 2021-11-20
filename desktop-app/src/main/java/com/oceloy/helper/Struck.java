package com.oceloy.helper;

import com.oceloy.Env;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Struck {
    static Connection con = DatabaseManager.Connector();
    static Statement stmt;
    static ResultSet rs;
    static String sql;
    static String tbl_db = Env.table_name;

    static Locale locale = new Locale("id", "ID");
    static NumberFormat num = NumberFormat.getIntegerInstance(locale);

    public static String ticket;
    public static String company_name;

    public static void preview_struck() {
        try {
            sql = "SELECT * FROM " + Env.table_name + " WHERE ticket='" + ticket + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String sort = num.format(Integer.parseInt(rs.getString("sort")));
                String customer_supplier = "";
                String cssp = "";
                String customer = rs.getString("customer");
                String supplier = rs.getString("supplier");

                String gross = num.format(Integer.parseInt(rs.getString("gross")));
                String tare = num.format(Integer.parseInt(rs.getString("tare")));
                String res = num.format(Integer.parseInt(rs.getString("result")));
                String nett = num.format(Integer.parseInt(rs.getString("nett")));
                String price = num.format(Integer.parseInt(rs.getString("price")));
                String total_price = num.format(Integer.parseInt(rs.getString("total_price")));

                if (customer.equals("-")) {
                    customer_supplier = supplier;
                    cssp = "SUPPLIER";
                } else {
                    customer_supplier = customer;
                    cssp = "KOSTUMER";
                }

                JasperDesign jasdi;

                if (sort.equals("0")) {
                    jasdi = JRXmlLoader.load("ows_no_sort.jrxml");
                } else {
                    jasdi = JRXmlLoader.load("ows_receipt_full.jrxml");
                }

                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);

                jasdi.setQuery(newQuery);
                HashMap<String, Object> para = new HashMap<>();

                para.put("company_name", company_name);
                para.put("gross", gross);
                para.put("tare", tare);
                para.put("result", res);
                para.put("sort", sort);
                para.put("nett", nett);
                para.put("price", price);
                para.put("total_price", total_price);
                para.put("customer_supplier", customer_supplier);
                para.put("cssp", cssp);

                JasperReport js = JasperCompileManager.compileReport(jasdi);
                JasperPrint jp = JasperFillManager.fillReport(js, para, con);
                JasperViewer.viewReport(jp, false);
            }
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void print_struck() {
        try {
            sql = "SELECT * FROM " + Env.table_name + " WHERE ticket='" + ticket + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String sort = num.format(Integer.parseInt(rs.getString("sort")));
                String customer_supplier = "";
                String cssp = "";
                String customer = rs.getString("customer");
                String supplier = rs.getString("supplier");

                String gross = num.format(Integer.parseInt(rs.getString("gross")));
                String tare = num.format(Integer.parseInt(rs.getString("tare")));
                String res = num.format(Integer.parseInt(rs.getString("result")));
                String nett = num.format(Integer.parseInt(rs.getString("nett")));
                String price = num.format(Integer.parseInt(rs.getString("price")));
                String total_price = num.format(Integer.parseInt(rs.getString("total_price")));

                if (customer.equals("-")) {
                    customer_supplier = supplier;
                    cssp = "SUPPLIER";
                } else {
                    customer_supplier = customer;
                    cssp = "KOSTUMER";
                }

                JasperDesign jasdi;

                if (sort.equals("0")) {
                    jasdi = JRXmlLoader.load("ows_no_sort.jrxml");
                } else {
                    jasdi = JRXmlLoader.load("ows_receipt_full.jrxml");
                }

                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);

                jasdi.setQuery(newQuery);
                HashMap<String, Object> para = new HashMap<>();

                para.put("company_name", company_name);
                para.put("gross", gross);
                para.put("tare", tare);
                para.put("result", res);
                para.put("sort", sort);
                para.put("nett", nett);
                para.put("price", price);
                para.put("total_price", total_price);
                para.put("customer_supplier", customer_supplier);
                para.put("cssp", cssp);
                JasperReport js = JasperCompileManager.compileReport(jasdi);
                JasperPrint jp = JasperFillManager.fillReport(js, para, con);
                JasperPrintManager.printReport(jp, false);
            }
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }
}
