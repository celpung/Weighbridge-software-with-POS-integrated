package com.oceloy.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.oceloy.helper.DatabaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

public class PortController implements Initializable {
    Connection con = DatabaseManager.Connector();
    Statement stmt;
    ResultSet rs;

    Alert info = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    public ComboBox<String> cb_com;

    @FXML
    public ComboBox<String> cb_baud;

    @FXML
    public ComboBox<String> cb_databits;

    @FXML
    public ComboBox<String> cb_stopbits;

    @FXML
    public ComboBox<String> cb_parity;

    @FXML
    public ComboBox<String> cb_readbytes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbox_com();
        cbox_baud();
        cbox_databits();
        cbox_stopbits();
        cbox_parity();
        cbox_readbytes();
    }

    private void cbox_com() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> com_ = FXCollections.observableArrayList("-", "COM1", "COM2", "COM3", "COM4",
                        "COM5", "COM6", "COM7", "COM8");
                cb_com.setItems(com_);
                cb_com.getSelectionModel().select(rs.getString("port"));
            } else {
                cb_com.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cbox_baud() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> baud_ = FXCollections.observableArrayList("-", "2400", "4800", "9600", "14400",
                        "19200", "38400", "57600", "115200", "128000", "256000");
                cb_baud.setItems(baud_);
                cb_baud.getSelectionModel().select(rs.getString("baud"));
            } else {
                cb_baud.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cbox_databits() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> databits_ = FXCollections.observableArrayList("-", "5", "6", "7", "8");
                cb_databits.setItems(databits_);
                cb_databits.getSelectionModel().select(rs.getString("databits"));
            } else {
                cb_databits.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cbox_stopbits() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> stopbits_ = FXCollections.observableArrayList("-", "1", "2", "3", "4", "5");
                cb_stopbits.setItems(stopbits_);
                cb_stopbits.getSelectionModel().select(rs.getString("stopbits"));
            } else {
                cb_stopbits.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cbox_parity() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> parity_ = FXCollections.observableArrayList("-", "0", "1");
                cb_parity.setItems(parity_);
                cb_parity.getSelectionModel().select(rs.getString("parity"));
            } else {
                cb_parity.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cbox_readbytes() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM communication LIMIT 1");

            if (rs.next()) {
                ObservableList<String> readbytes_ = FXCollections.observableArrayList("-", "1", "2", "3", "4", "5", "6",
                        "7", "8", "9", "10", "11", "12", "13", "14", "15");
                cb_readbytes.setItems(readbytes_);
                cb_readbytes.getSelectionModel().select(rs.getString("readbytes"));
            } else {
                cb_readbytes.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_set_port_action() {
        String port = cb_com.getSelectionModel().getSelectedItem();
        String baud = cb_baud.getSelectionModel().getSelectedItem();
        String databits = cb_databits.getSelectionModel().getSelectedItem();
        String stopbits = cb_stopbits.getSelectionModel().getSelectedItem();
        String parity = cb_parity.getSelectionModel().getSelectedItem();
        String readbytes = cb_readbytes.getSelectionModel().getSelectedItem();

        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE communication SET port='" + port + "', baud='" + baud + "', databits='"
                    + databits + "', stopbits='" + stopbits + "', parity='" + parity + "', readbytes='" + readbytes
                    + "' WHERE id=0");

            info.setTitle("Port Komunikasi");
            info.setHeaderText("Info");
            info.setContentText("Port komunikasi berhasil di update !");
            info.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
