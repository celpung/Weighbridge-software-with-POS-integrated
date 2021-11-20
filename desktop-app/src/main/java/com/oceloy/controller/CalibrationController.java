package com.oceloy.controller;

import com.oceloy.Env;
import com.oceloy.helper.*;
import com.oceloy.model.CalibrationTableModelFinish;
import com.oceloy.model.CalibrationTableModelProcess;

import org.controlsfx.control.textfield.TextFields;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalibrationController implements Initializable {

    Connection con = DatabaseManager.Connector();
    Statement stmt;
    ResultSet rs;
    String mode;

    String customer;
    String supplier;
    String sync;
    String user_level;

    String tbl_query;
    String tickets;

    String selected_tab = "process";

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    Alert info = new Alert(Alert.AlertType.INFORMATION);

    Locale locale = new Locale("id", "ID");
    NumberFormat num = NumberFormat.getIntegerInstance(locale);

    DecimalFormat df = new DecimalFormat("#.##");

    ObservableList<CalibrationTableModelProcess> calibration_data_process = FXCollections.observableArrayList();
    ObservableList<CalibrationTableModelFinish> calibration_data_finish = FXCollections.observableArrayList();

    @FXML
    private Label lbl_operator;

    @FXML
    private Label lbl_waiting;

    @FXML
    private Label lbl_company_name;

    @FXML
    private Label lbl_bridge_id;

    @FXML
    private Label lbl_weight;

    @FXML
    private Label lbl_kg;

    @FXML
    private Label lbl_date_in;

    @FXML
    private Label lbl_date_out;

    @FXML
    private ComboBox<String> cb_vehicle;

    @FXML
    private ComboBox<String> cb_status;

    @FXML
    private ComboBox<String> cb_products;

    @FXML
    private ComboBox<String> cb_customer_supplier;

    @FXML
    private TextField tf_order_number;

    @FXML
    private TextField tf_driver;

    @FXML
    private TextField tf_info;

    @FXML
    private TextField tf_gross;

    @FXML
    private TextField tf_tare;

    @FXML
    private TextField tf_result;

    @FXML
    private TextField tf_sort;

    @FXML
    private TextField tf_percent;

    @FXML
    private TextField tf_netto;

    @FXML
    private TextField tf_price;

    @FXML
    private TextField tf_total_price;

    @FXML
    private Button btn_save;

    @FXML
    private Label lbl_ticket;

    @FXML
    private TextField tf_search;

    @FXML
    private TableView<CalibrationTableModelProcess> tbl_data_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_ticket_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_date_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_vehicle_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_status_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_products_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, String> col_ton_process;

    @FXML
    private TableColumn<CalibrationTableModelProcess, HBox> col_action_process;

    @FXML
    private TableView<CalibrationTableModelFinish> tbl_data_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_ticket_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_date_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_vehicle_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_status_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_products_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, String> col_ton_finish;

    @FXML
    private TableColumn<CalibrationTableModelFinish, HBox> col_action_finish;

    @FXML
    private AnchorPane pnl_approval;

    @FXML
    private Label lbl_approval_waiting;

    @FXML
    private TextField tf_outweight;

    @FXML
    private TextField tf_transfer;

    @FXML
    private TextField tf_cash;

    @FXML
    private Label lbl_gap;

    public void loginData(String operator, String priv) {
        lbl_operator.setText(operator);
        user_level = priv;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        //lbl_weight.setText("26.410");
        clear();
        set_icon();
        set_cb_vehicle();
        set_cb_status();
        set_cb_products();
        lbl_company_name.setText(Env.company_name);
        lbl_bridge_id.setText(Env.bridge_id);
        set_calibration_table_process();
        set_calibration_table_finish();
        calibrationplay();
        listen_data_play();
    }

    // -------- method start ---------//
    private void set_icon() {
        Image operator_icon = new Image(String.valueOf(getClass().getResource("/com/oceloy/images/user.png")));
        ImageView operator_icon_imageview = new ImageView(operator_icon);
        operator_icon_imageview.setFitWidth(16);
        operator_icon_imageview.setFitHeight(16);
        lbl_operator.setGraphic(operator_icon_imageview);

        Image save_icon = new Image(String.valueOf(getClass().getResource("/com/oceloy/images/save.png")));
        ImageView save_icon_imageview = new ImageView(save_icon);
        save_icon_imageview.setFitWidth(16);
        save_icon_imageview.setFitHeight(16);
        btn_save.setGraphic(save_icon_imageview);

        Image approval_waiting_icon = new Image(
                String.valueOf(getClass().getResource("/com/oceloy/images/waiting.gif")));
        ImageView approval_waiting_icon_imageview = new ImageView(approval_waiting_icon);
        approval_waiting_icon_imageview.setFitWidth(28);
        approval_waiting_icon_imageview.setFitHeight(28);
        lbl_approval_waiting.setGraphic(approval_waiting_icon_imageview);

        Image waiting_icon = new Image(String.valueOf(getClass().getResource("/com/oceloy/images/waiting.gif")));
        ImageView waiting_icon_imageview = new ImageView(waiting_icon);
        waiting_icon_imageview.setFitWidth(16);
        waiting_icon_imageview.setFitHeight(16);
        lbl_waiting.setGraphic(waiting_icon_imageview);
    }

    private void clear() {
        mode = "calibration";

        lbl_waiting.setVisible(false);
        lbl_ticket.setText("-");
        lbl_date_in.setText("-");
        lbl_date_out.setText("-");
        cb_vehicle.getSelectionModel().selectFirst();
        cb_vehicle.valueProperty().set(null);
        cb_status.getSelectionModel().selectFirst();
        cb_products.getSelectionModel().selectFirst();
        cb_customer_supplier.getSelectionModel().selectFirst();
        tf_order_number.clear();
        tf_driver.clear();
        tf_info.clear();

        tf_gross.setText("0");
        tf_tare.setText("0");
        tf_result.setText("0");
        tf_sort.setText("0");
        tf_percent.setText("0");
        tf_netto.setText("0");

        tf_price.setText("0");
        tf_total_price.setText("0");

        tf_cash.setText("0");
        tf_transfer.setText("0");
        tf_outweight.setText("0");
        form_disable();
        sync = "";
    }

    private void form_disable() {
        cb_vehicle.setDisable(true);
        cb_status.setDisable(true);
        cb_products.setDisable(true);
        cb_customer_supplier.setDisable(true);
        tf_order_number.setEditable(false);
        tf_driver.setEditable(false);
        tf_info.setEditable(false);
        tf_gross.setEditable(false);
        tf_tare.setEditable(false);
        tf_result.setEditable(false);
        tf_sort.setEditable(false);
        tf_percent.setEditable(false);
        tf_netto.setEditable(false);
        tf_price.setEditable(false);
        tf_total_price.setEditable(false);
        tf_cash.setEditable(false);
        tf_transfer.setEditable(false);
        tf_outweight.setEditable(false);

        // tf_vehicle.setStyle("-fx-background-color: #EEE;");
        tf_order_number.setStyle("-fx-background-color: #EEE;");
        tf_driver.setStyle("-fx-background-color: #EEE;");
        tf_info.setStyle("-fx-background-color: #EEE");
        tf_gross.setStyle("-fx-background-color: #EEE;");
        tf_tare.setStyle("-fx-background-color: #EEE;");
        tf_result.setStyle("-fx-background-color: #EEE;");
        tf_sort.setStyle("-fx-background-color: #EEE;");
        tf_percent.setStyle("-fx-background-color: #EEE;");
        tf_netto.setStyle("-fx-background-color: #EEE;");
        tf_price.setStyle("-fx-background-color: #EEE;");
        tf_total_price.setStyle("-fx-background-color: #EEE;");
        tf_cash.setStyle("-fx-background-color: #EEE;");
        tf_transfer.setStyle("-fx-background-color: #EEE;");
        tf_outweight.setStyle("-fx-background-color: #EEE;");
    }

    private void form_enable() {
        cb_vehicle.setDisable(false);
        cb_status.setDisable(false);
        cb_products.setDisable(false);
        tf_order_number.setEditable(true);
        tf_driver.setEditable(true);
        tf_info.setEditable(true);
        tf_sort.setEditable(true);
        tf_price.setEditable(true);
        tf_cash.setEditable(true);
        tf_transfer.setEditable(true);
        tf_outweight.setEditable(true);

        // tf_vehicle.setStyle("-fx-background-color: #FFF;");
        tf_order_number.setStyle("-fx-background-color: #FFF;");
        tf_driver.setStyle("-fx-background-color: #FFF;");
        tf_info.setStyle("-fx-background-color: #FFF");

        tf_gross.setStyle("-fx-background-color: #EEE;");
        tf_tare.setStyle("-fx-background-color: #EEE;");
        tf_result.setStyle("-fx-background-color: #EEE;");
        tf_sort.setStyle("-fx-background-color: #FFF;");
        tf_percent.setStyle("-fx-background-color: #EEE;");
        tf_netto.setStyle("-fx-background-color: #EEE;");
        tf_price.setStyle("-fx-background-color: #FFF;");
        tf_total_price.setStyle("-fx-background-color: #EEE;");
        tf_cash.setStyle("-fx-background-color: #FFF;");
        tf_transfer.setStyle("-fx-background-color: #FFF;");
        tf_outweight.setStyle("-fx-background-color: #FFF;");
    }

    private void set_cb_vehicle() {
        cb_vehicle.getItems().clear();
        ObservableList<String> list_vehicle = FXCollections.observableArrayList("-");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT (vehicle) FROM " + Env.table_name + "");
            while (rs.next()) {
                list_vehicle.add(rs.getString("vehicle"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cb_vehicle.setItems(list_vehicle);
        // new AutoCompleteBox(cb_vehicle);
        cb_vehicle.setEditable(true);
        TextFields.bindAutoCompletion(cb_vehicle.getEditor(), cb_vehicle.getItems());
    }

    private void set_cb_status() {
        cb_status.getItems().clear();
        ObservableList<String> list_status = FXCollections.observableArrayList("-", "KELUAR", "MASUK");
        cb_status.setItems(list_status);
        cb_status.getSelectionModel().selectFirst();
    }

    private void set_cb_products() {
        cb_products.getItems().clear();
        ObservableList<String> list_product = FXCollections.observableArrayList("-");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                list_product.add(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cb_products.setItems(list_product);
        // new AutoCompleteBox(cb_products);
        cb_products.setEditable(true);
        TextFields.bindAutoCompletion(cb_products.getEditor(), cb_products.getItems());
    }

    private void set_cb_customer_supplier() {
        cb_customer_supplier.getItems().clear();
        if (cb_status.getSelectionModel().getSelectedItem().equals("MASUK")) {
            cb_customer_supplier.setDisable(false);

            ObservableList<String> list_supplier = FXCollections.observableArrayList("-");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM suppliers");
                while (rs.next()) {
                    list_supplier.add(rs.getString("supplier_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cb_customer_supplier.setItems(list_supplier);
        } else if (cb_status.getSelectionModel().getSelectedItem().equals("KELUAR")) {
            cb_customer_supplier.setDisable(false);

            ObservableList<String> list_customer = FXCollections.observableArrayList("-");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM customers");
                while (rs.next()) {
                    list_customer.add(rs.getString("customer_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cb_customer_supplier.setItems(list_customer);
        } else {
            cb_customer_supplier.setDisable(true);
        }
        // new AutoCompleteBox(cb_customer_supplier);
        cb_customer_supplier.setEditable(true);
        TextFields.bindAutoCompletion(cb_customer_supplier.getEditor(), cb_customer_supplier.getItems());
    }

    private void tbl_process() {
        tbl_data_process.getItems().clear();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(tbl_query);

            while (rs.next()) {
                String ticket = rs.getString("ticket");
                String date_in = rs.getString("date_in");
                String vehicle = rs.getString("vehicle");
                String status = rs.getString("product_status");
                String products_name = rs.getString("product_name");
                String result = rs.getString("result");

                Button btn_edit = new Button("EDIT");
                btn_edit.setStyle("-fx-background-color:green; -fx-text-fill: white;");
                btn_edit.setOnAction(act -> {
                    tickets = ticket;
                    if (user_level.equals("admin")) {
                        fill_field();
                        form_enable();
                        mode = "edit";
                        tf_gross.setEditable(true);
                        tf_tare.setEditable(true);
                        tf_gross.setStyle("-fx-background-color: #FFF;");
                        tf_tare.setStyle("-fx-background-color: #FFF;");
                    } else {
                        ApprovalManager.approval_operator = lbl_operator.getText();
                        ApprovalManager.approval_type = "EDIT DATA TIMBANGAN";
                        ApprovalManager.ticket = ticket;
                        ApprovalManager.request_approval();
                        /*
                         * if (ApprovalManager.request_approval_status.equals("requested")) {
                         * pnl_approval.setVisible(true); get_edit_approval_play(); }
                         */
                    }
                });

                Button btn_delete = new Button("DELETE");
                btn_delete.setStyle("-fx-background-color:red; -fx-text-fill: white;");
                btn_delete.setOnAction(act -> {
                    if (user_level.equals("admin")) {
                        confirm.setTitle("Hapus Data");
                        confirm.setHeaderText("HAPUS DATA !");
                        confirm.setContentText("Yakin ingin menghapus data ?");

                        Optional<ButtonType> results = confirm.showAndWait();
                        if (results.orElse(null) == ButtonType.OK) {
                            lbl_waiting.setVisible(true);
                            InternetConnection.connect();
                            String connection_status = InternetConnection.connection_status;
                            if (connection_status.equals("connected")) {
                                ApiManager.api_endpoint = "delete";
                                ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"bridge_id\":\""
                                        + Env.bridge_id + "\", \"api_key\":\"" + Env.api_key + "\"}";
                                ApiManager.api_send();

                                if (ApiManager.status.equals("ok")) {
                                    try {
                                        stmt = con.createStatement();
                                        stmt.execute("DELETE FROM " + Env.table_name + " WHERE ticket=" + ticket + "");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        stmt = con.createStatement();
                                        stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                try {
                                    stmt = con.createStatement();
                                    stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            lbl_waiting.setVisible(false);
                        }
                    } else {
                        ApprovalManager.approval_operator = lbl_operator.getText();
                        ApprovalManager.approval_type = "HAPUS DATA TIMBANGAN";
                        ApprovalManager.ticket = ticket;
                        ApprovalManager.request_approval();
                        /*
                         * if (ApprovalManager.request_approval_status.equals("requested")) {
                         * pnl_approval.setVisible(true); tickets = ticket; get_delete_approval_play();
                         * }
                         */
                    }
                    set_calibration_table_process();
                });

                HBox action = new HBox(btn_edit, btn_delete);
                action.setAlignment(Pos.CENTER);
                action.setSpacing(5);

                calibration_data_process.add(new CalibrationTableModelProcess(ticket, date_in, vehicle, status,
                        products_name, num.format(Integer.parseInt(result)), action));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_ticket_process.setCellValueFactory(new PropertyValueFactory<>("ticket"));
        col_date_process.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_vehicle_process.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        col_status_process.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_products_process.setCellValueFactory(new PropertyValueFactory<>("product"));
        col_ton_process.setCellValueFactory(new PropertyValueFactory<>("tonase"));
        col_action_process.setCellValueFactory(new PropertyValueFactory<>("action"));

        tbl_data_process.setItems(calibration_data_process);
    }

    private void tbl_finish() {
        tbl_data_finish.getItems().clear();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(tbl_query);

            while (rs.next()) {
                String ticket = rs.getString("ticket");
                String date_in = rs.getString("date_in");
                String vehicle = rs.getString("vehicle");
                String status = rs.getString("product_status");
                String products_name = rs.getString("product_name");
                String result = rs.getString("result");

                Button btn_print = new Button("PRINT");
                btn_print.setStyle("-fx-background-color:blue; -fx-text-fill: white;");
                btn_print.setOnAction(act -> {
                    Struck.ticket = ticket;
                    Struck.company_name = lbl_company_name.getText();
                    Struck.preview_struck();
                });

                Button btn_edit = new Button("EDIT");
                btn_edit.setStyle("-fx-background-color:green; -fx-text-fill: white;");
                btn_edit.setOnAction(act -> {
                    tickets = ticket;
                    if (user_level.equals("admin")) {
                        fill_field();
                        form_enable();
                        mode = "edit";
                        tf_gross.setEditable(true);
                        tf_tare.setEditable(true);
                        tf_gross.setStyle("-fx-background-color: #FFF;");
                        tf_tare.setStyle("-fx-background-color: #FFF;");
                    } else {
                        ApprovalManager.approval_operator = lbl_operator.getText();
                        ApprovalManager.approval_type = "EDIT DATA TIMBANGAN";
                        ApprovalManager.ticket = ticket;
                        ApprovalManager.request_approval();
                        /*
                         * if (ApprovalManager.request_approval_status.equals("requested")) {
                         * pnl_approval.setVisible(true); get_edit_approval_play(); }
                         */
                    }
                });

                Button btn_delete = new Button("DELETE");
                btn_delete.setStyle("-fx-background-color:red; -fx-text-fill: white;");

                btn_delete.setOnAction(act -> {
                    if (user_level.equals("admin")) {
                        confirm.setTitle("Hapus Data");
                        confirm.setHeaderText("HAPUS DATA !");
                        confirm.setContentText("Yakin ingin menghapus data ?");

                        Optional<ButtonType> results = confirm.showAndWait();
                        if (results.orElse(null) == ButtonType.OK) {
                            lbl_waiting.setVisible(true);
                            InternetConnection.connect();
                            String connection_status = InternetConnection.connection_status;
                            if (connection_status.equals("connected")) {
                                ApiManager.api_endpoint = "delete";
                                ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"bridge_id\":\""
                                        + Env.bridge_id + "\", \"api_key\":\"" + Env.api_key + "\"}";
                                ApiManager.api_send();

                                if (ApiManager.status.equals("ok")) {
                                    try {
                                        stmt = con.createStatement();
                                        stmt.execute("DELETE FROM " + Env.table_name + " WHERE ticket=" + ticket + "");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        stmt = con.createStatement();
                                        stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                try {
                                    stmt = con.createStatement();
                                    stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            lbl_waiting.setVisible(false);
                        }
                    } else {
                        ApprovalManager.approval_operator = lbl_operator.getText();
                        ApprovalManager.approval_type = "HAPUS DATA TIMBANGAN";
                        ApprovalManager.ticket = ticket;
                        ApprovalManager.request_approval();
                        /*
                         * if (ApprovalManager.request_approval_status.equals("requested")) {
                         * pnl_approval.setVisible(true); tickets = ticket; get_delete_approval_play();
                         * }
                         */
                    }
                    set_calibration_table_finish();
                });

                HBox action = new HBox(btn_print, btn_edit, btn_delete);
                action.setAlignment(Pos.CENTER);
                action.setSpacing(5);

                calibration_data_finish.add(new CalibrationTableModelFinish(ticket, date_in, vehicle, status,
                        products_name, num.format(Integer.parseInt(result)), action));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_ticket_finish.setCellValueFactory(new PropertyValueFactory<>("ticket"));
        col_date_finish.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_vehicle_finish.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        col_status_finish.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_products_finish.setCellValueFactory(new PropertyValueFactory<>("product"));
        col_ton_finish.setCellValueFactory(new PropertyValueFactory<>("tonase"));
        col_action_finish.setCellValueFactory(new PropertyValueFactory<>("action"));

        tbl_data_finish.setItems(calibration_data_finish);
    }

    private void fill_field() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " WHERE ticket=" + tickets + "");
            if (rs.next()) {
                lbl_ticket.setText(rs.getString("ticket"));
                lbl_date_in.setText(rs.getString("date_in"));
                lbl_date_out.setText(rs.getString("date_out"));
                // tf_vehicle.setText(rs.getString("vehicle"));
                cb_vehicle.getSelectionModel().select(rs.getString("vehicle"));
                cb_products.getSelectionModel().select(rs.getString("product_name"));
                tf_order_number.setText(rs.getString("order_id"));
                tf_driver.setText(rs.getString("driver"));
                tf_info.setText(rs.getString("info"));
                tf_gross.setText(num.format(Integer.parseInt(rs.getString("gross"))));
                tf_tare.setText(num.format(Integer.parseInt(rs.getString("tare"))));
                tf_result.setText(num.format(Integer.parseInt(rs.getString("result"))));
                tf_sort.setText(num.format(Integer.parseInt(rs.getString("sort"))));
                tf_percent.setText(df.format(rs.getDouble("percent")));
                tf_netto.setText(num.format(Integer.parseInt(rs.getString("nett"))));
                tf_price.setText(num.format(Integer.parseInt(rs.getString("price"))));
                tf_total_price.setText(num.format(Integer.parseInt(rs.getString("total_price"))));
                tf_cash.setText(num.format(Integer.parseInt(rs.getString("cash"))));
                tf_transfer.setText(num.format(Integer.parseInt(rs.getString("transfer"))));
                tf_outweight.setText(num.format(Integer.parseInt(rs.getString("out_weight"))));
                sync = rs.getString("sync");
                String cssp;
                String cs = rs.getString("customer");
                String sp = rs.getString("supplier");
                if (cs.equals("-")) {
                    cssp = sp;
                } else if (sp.equals("-")) {
                    cssp = cs;
                } else {
                    cssp = "-";
                }
                cb_customer_supplier.getSelectionModel().select(cssp);
                cb_status.getSelectionModel().select(rs.getString("product_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void set_calibration_table_process() {
        tbl_query = "SELECT * FROM " + Env.table_name + " WHERE tare=0 AND sync != 'delete' ORDER BY ticket ASC";
        tbl_process();
    }

    private void set_calibration_table_finish() {
        tbl_query = "SELECT * FROM " + Env.table_name + " WHERE tare!=0 AND sync != 'delete' ORDER BY ticket DESC";
        tbl_finish();
    }

    Timeline get_edit_approval = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            InternetConnection.connect();
            String connection_status = InternetConnection.connection_status;
            if (connection_status.equals("connected")) {
                ApprovalManager.get_approval_query = "{\"approval_id\":\"" + ApprovalManager.approval_id
                        + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"api_key\":\"" + Env.api_key + "\"}";

                ApiManager.api_endpoint = "get-approval";
                ApiManager.api_query = ApprovalManager.get_approval_query;
                ApiManager.api_send();

                if (ApiManager.status.equals("approved")) {
                    get_edit_approval_stop();
                    pnl_approval.setVisible(false);
                    fill_field();
                    form_enable();
                    mode = "edit";
                    tf_gross.setEditable(true);
                    tf_tare.setEditable(true);
                    tf_gross.setStyle("-fx-background-color: #FFF;");
                    tf_tare.setStyle("-fx-background-color: #FFF;");
                } else if (ApiManager.status.equals("rejected")) {
                    get_edit_approval_stop();
                    pnl_approval.setVisible(false);
                    info.setTitle("Approval");
                    info.setHeaderText("TIDAK DISETUJUI !");
                    info.setContentText("Permintaan tidak disetujui !");
                    info.show();
                }
            } else {
                get_edit_approval_stop();
                pnl_approval.setVisible(false);
                info.setTitle("Approval");
                info.setHeaderText("TIDAK ADA KONEKSI !");
                info.setContentText("Membutuhkan sambungan internet untuk approval !");
                info.show();
            }
        }
    }));

    private void get_edit_approval_play() {
        get_edit_approval.setCycleCount(Timeline.INDEFINITE);
        get_edit_approval.play();
    }

    private void get_edit_approval_stop() {
        get_edit_approval.stop();
    }

    Timeline get_delete_approval = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            InternetConnection.connect();
            String connection_status = InternetConnection.connection_status;
            if (connection_status.equals("connected")) {
                ApprovalManager.get_approval_query = "{\"approval_id\":\"" + ApprovalManager.approval_id
                        + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"api_key\":\"" + Env.api_key + "\"}";

                ApiManager.api_endpoint = "get-approval";
                ApiManager.api_query = ApprovalManager.get_approval_query;
                ApiManager.api_send();

                if (ApiManager.status.equals("approved")) {
                    get_delete_approval_stop();
                    pnl_approval.setVisible(false);

                    lbl_waiting.setVisible(true);
                    InternetConnection.connect();
                    connection_status = InternetConnection.connection_status;
                    if (connection_status.equals("connected")) {
                        ApiManager.api_endpoint = "delete";
                        ApiManager.api_query = "{\"ticket\":\"" + tickets + "\", \"bridge_id\":\"" + Env.bridge_id
                                + "\", \"api_key\":\"" + Env.api_key + "\"}";
                        ApiManager.api_send();

                        if (ApiManager.status.equals("ok")) {
                            try {
                                stmt = con.createStatement();
                                stmt.execute("DELETE FROM " + Env.table_name + " WHERE ticket=" + tickets + "");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            stmt = con.createStatement();
                            stmt.executeUpdate("UPDATE " + Env.table_name + " set sync='delete'");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    tbl_finish();
                    lbl_waiting.setVisible(false);
                    tickets = "-";

                } else if (ApiManager.status.equals("rejected")) {
                    get_delete_approval_stop();
                    pnl_approval.setVisible(false);
                    info.setTitle("Approval");
                    info.setHeaderText("TIDAK DISETUJUI !");
                    info.setContentText("Permintaan tidak disetujui !");
                    info.show();
                }
            } else {
                get_delete_approval_stop();
                pnl_approval.setVisible(false);
                info.setTitle("Approval");
                info.setHeaderText("TIDAK ADA KONEKSI !");
                info.setContentText("Membutuhkan sambungan internet untuk approval !");
                info.show();
            }
        }
    }));

    private void get_delete_approval_play() {
        get_delete_approval.setCycleCount(Timeline.INDEFINITE);
        get_delete_approval.play();
    }

    private void get_delete_approval_stop() {
        get_delete_approval.stop();
    }

    Timeline calibration = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            SerialPort serialPort = new SerialPort(Portcomm.com);
            {
                try {
                    serialPort.openPort();

                    serialPort.setParams(Portcomm.baud, Portcomm.databits, Portcomm.stopbits, Portcomm.parity);
                    byte[] buffer = serialPort.readBytes(Portcomm.readbytes);

                    serialPort.closePort();

                    String s = new String(buffer).replaceAll("[^-0-9]", "");
                    String v = s.replaceFirst("^0+(?!$)", "");

                    if (v.equals("")) {
                        return;
                    }

                    int y = Integer.parseInt(v);
                    if (y % 10 != 0) {
                        return;
                    }

                    int x = Integer.parseInt(v);
                    String z = num.format(x);

                    if (v.contains("-")) {
                        lbl_weight.setText("-Minus");
                    } else {
                        lbl_weight.setText(z);
                        lbl_kg.setText("Kg");
                    }
                } catch (SerialPortException e) {
                    lbl_weight.setText("No Data");
                    lbl_kg.setText("");
                    calibrationstop();
                    e.printStackTrace();
                }
            }
        }
    }));

    private void calibrationplay() {
        Thread calibrationThread = new Thread(() -> {
            Portcomm.setportcom();
            calibration.setCycleCount(Timeline.INDEFINITE);
            calibration.play();
        });
        calibrationThread.start();
    }

    private void calibrationstop() {
        calibration.stop();
    }

    Timeline listen_data = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            Thread ListenerDataThread = new Thread(() -> {
                ListenerData.api_send();
            });
            ListenerDataThread.start();
            if (ListenerData.refresh_data.equals("refresh")) {
                set_calibration_table_finish();
                set_calibration_table_process();
            }
        }
    }));

    private void listen_data_play() {
        listen_data.setCycleCount(Timeline.INDEFINITE);
        listen_data.play();
    }

    // --------- method end ----------//

    @FXML
    void btn_cancel_approval_action() {
        // get_approval_stop();
        get_edit_approval_stop();
        pnl_approval.setVisible(false);
    }

    @FXML
    void btn_clear_action() {
        clear();
    }

    @FXML
    void btn_count_action() {
        if (lbl_ticket.getText().equals("-")) {
            info.setTitle("Alert !");
            info.setHeaderText("Tiket tidak dapat di proses !");
            info.setContentText("Silahkan buka transaksi terlebih dahulu !");
            info.showAndWait();
            return;
        }

        if (mode.equals("calibration")) {
            DateSet.date_set();
            String date = DateSet.date;

            int weight = Integer.parseInt(lbl_weight.getText().replaceAll("[^0-9]+", ""));

            if (tf_gross.getText().equals("0")) {
                tf_gross.setText(num.format(weight));
                lbl_date_in.setText(date);
            } else if (tf_tare.getText().equals("0")) {
                tf_tare.setText(num.format(weight));
                lbl_date_out.setText(date);
            }

            int g = Integer.parseInt(tf_gross.getText().replaceAll("[^0-9]+", ""));
            int t = Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""));

            if (t > g) {
                tf_gross.setText(num.format(t));
                tf_tare.setText(num.format(g));
            }

            int gross = Integer.parseInt(tf_gross.getText().replaceAll("[^0-9]+", ""));
            int tare = Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""));
            int result = gross - tare;
            tf_result.setText(num.format(result));

            if (tf_tare.getText().equals("0")) {
                return;
            }

            int sort = Integer.parseInt(tf_sort.getText().replaceAll("[^0-9]+", ""));
            tf_sort.setText(num.format(sort));

            double percent = (100 * (double) sort) / (double) result;
            tf_percent.setText(df.format(percent));

            int nett = result - sort;
            String netto = num.format(nett);
            tf_netto.setText(netto);

            if (tf_tare.getText().equals("0")) {
                return;
            }

            int price = 0;

            if (tf_price.getText().equals("0")) {
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM products WHERE product_name='"
                            + cb_products.getSelectionModel().getSelectedItem() + "'");
                    while (rs.next()) {
                        if (cb_status.getSelectionModel().getSelectedItem().equals("MASUK")) {
                            price = rs.getInt("buy_price");
                            tf_price.setText(num.format(price));
                        } else if (cb_status.getSelectionModel().getSelectedItem().equals("KELUAR")) {
                            price = rs.getInt("sell_price");
                            tf_price.setText(num.format(price));
                        } else {
                            price = 0;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                price = Integer.parseInt(tf_price.getText().replaceAll("[^0-9]+", ""));
                tf_price.setText(num.format(price));
            }

            int totalprice = nett * price;
            tf_total_price.setText(num.format(totalprice));
            tf_tare.setText(num.format(Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""))));
            tf_outweight.setText(num.format(Integer.parseInt(tf_outweight.getText().replaceAll("[^0-9]+", ""))));

            String status = cb_status.getSelectionModel().getSelectedItem();
            int ow = Integer.parseInt(tf_outweight.getText().replaceAll("[^0-9]+", ""));

            if (status == "MASUK" && ow != 0) {
                int ttp = ow * price;
                tf_total_price.setText(num.format(ttp));
            }
        } else if (mode.equals("edit")) {
            int g = Integer.parseInt(tf_gross.getText().replaceAll("[^0-9]+", ""));
            int t = Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""));

            if (t > g) {
                tf_gross.setText(num.format(t));
                tf_tare.setText(num.format(g));
            }

            int gross = Integer.parseInt(tf_gross.getText().replaceAll("[^0-9]+", ""));
            int tare = Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""));
            int result = gross - tare;
            tf_result.setText(num.format(result));

            if (tf_tare.getText().equals("0")) {
                return;
            }

            int sort = Integer.parseInt(tf_sort.getText().replaceAll("[^0-9]+", ""));
            tf_sort.setText(num.format(sort));

            double percent = (100 * (double) sort) / (double) result;
            tf_percent.setText(df.format(percent));

            int nett = result - sort;
            String netto = num.format(nett);
            tf_netto.setText(netto);

            int price = 0;

            if (tf_price.getText().equals("0")) {
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM products WHERE product_name='"
                            + cb_products.getSelectionModel().getSelectedItem() + "'");
                    while (rs.next()) {
                        if (cb_status.getSelectionModel().getSelectedItem().equals("MASUK")) {
                            price = rs.getInt("buy_price");
                            tf_price.setText(num.format(price));
                        } else if (cb_status.getSelectionModel().getSelectedItem().equals("KELUAR")) {
                            price = rs.getInt("sell_price");
                            tf_price.setText(num.format(price));
                        } else {
                            price = 0;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                price = Integer.parseInt(tf_price.getText().replaceAll("[^0-9]+", ""));
                tf_price.setText(num.format(price));
            }

            int totalprice = nett * price;
            tf_total_price.setText(num.format(totalprice));
            tf_tare.setText(num.format(Integer.parseInt(tf_tare.getText().replaceAll("[^0-9]+", ""))));
            tf_outweight.setText(num.format(Integer.parseInt(tf_outweight.getText().replaceAll("[^0-9]+", ""))));

            String status = cb_status.getSelectionModel().getSelectedItem();
            int ow = Integer.parseInt(tf_outweight.getText().replaceAll("[^0-9]+", ""));

            if (status.equals("MASUK") && ow != 0) {
                int ttp = ow * price;
                tf_total_price.setText(num.format(ttp));
            }

            cb_customer_supplier.setDisable(false);
        }
    }

    @FXML
    void btn_open_action() {
        clear();
        form_enable();

        if (lbl_weight.getText().equals("No Data")) {
            info.setTitle("ERROR COMMUNICATION !");
            info.setHeaderText("Info");
            info.setContentText("Cannot connect to indicators !");
            info.showAndWait();
            return;
        }

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + Env.table_name + " ORDER BY ticket DESC LIMIT 1");

            if (rs.next()) {
                String ticket = rs.getString("ticket");
                int ticket_int = Integer.parseInt(ticket) + 1;
                String ticket_num = Integer.toString(ticket_int);
                lbl_ticket.setText(ticket_num);
            } else {
                lbl_ticket.setText("1");
            }
            cb_vehicle.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_save_action() {
        if (lbl_ticket.getText().equals("-")) {
            info.setTitle("Alert !");
            info.setHeaderText("Tiket tidak dapat di proses !");
            info.setContentText("Silahkan buka transaksi terlebih dahulu !");
            info.showAndWait();
            return;
        }

        if (cb_status.getSelectionModel().getSelectedItem().equals("-")) {
            info.setTitle("Alert !");
            info.setHeaderText("STATUS BARANG KOSONG !");
            info.setContentText("Silahkan pilih status terlebih dahulu !");
            info.showAndWait();
            cb_status.requestFocus();
            return;
        }

        int tth = Integer.parseInt(tf_total_price.getText().replaceAll("\\D", ""));
        int csh = Integer.parseInt(tf_cash.getText().replaceAll("\\D", ""));
        int trf = Integer.parseInt(tf_transfer.getText().replaceAll("\\D", ""));

        int cshtrf = csh + trf;
        if (cshtrf != tth) {
            info.setTitle("Nilai cash dan transfer tidak sesua");
            info.setHeaderText("Alert !");
            info.setContentText("Silahkan periksa kembali nominal cash dan transfer !");
            info.showAndWait();
            return;
        }

        if (cb_status.getSelectionModel().getSelectedItem().equals("MASUK")) {
            supplier = cb_customer_supplier.getSelectionModel().getSelectedItem();
            customer = "-";
        } else if (cb_status.getSelectionModel().getSelectedItem().equals("KELUAR")) {
            customer = cb_customer_supplier.getSelectionModel().getSelectedItem();
            supplier = "-";
        } else {
            customer = "-";
            supplier = "-";
        }

        String ticket = lbl_ticket.getText();
        String date_in = lbl_date_in.getText();
        String date_out = lbl_date_out.getText();
        String vehicle = cb_vehicle.getSelectionModel().getSelectedItem();
        String status = cb_status.getSelectionModel().getSelectedItem();
        String products_name = cb_products.getSelectionModel().getSelectedItem();
        String order_id = tf_order_number.getText();
        if (order_id.equals("")) {
            order_id = "-";
        }
        String oi = order_id;
        String driver = tf_driver.getText();
        if (driver.equals("")) {
            driver = "-";
        }
        String dr = driver;
        String infos = tf_info.getText();
        if (infos.equals("")) {
            infos = "-";
        }
        String inf = infos;
        String gross = tf_gross.getText().replaceAll("\\D", "");
        String tare = tf_tare.getText().replaceAll("\\D", "");
        String result = tf_result.getText().replaceAll("\\D", "");
        String sort = tf_sort.getText().replaceAll("\\D", "");
        String percent = tf_percent.getText();
        String nett = tf_netto.getText().replaceAll("\\D", "");
        String price = tf_price.getText().replaceAll("\\D", "");
        String total_price = tf_total_price.getText().replaceAll("\\D", "");
        String cash = tf_cash.getText().replaceAll("\\D", "");
        String transfer = tf_transfer.getText().replaceAll("\\D", "");
        String out_weight = tf_outweight.getText().replaceAll("\\D", "");
        String gap = lbl_gap.getText().replaceAll("\\D", "");

        if (mode.equals("calibration")) {
            if (tf_tare.getText().equals("0")) {
                InternetConnection.connect();
                String connection_status = InternetConnection.connection_status;
                if (connection_status.equals("connected")) {
                    lbl_waiting.setVisible(true);
                    Thread insertThread = new Thread(() -> {
                        ApiManager.api_endpoint = "insert";
                        ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                                + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                                + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                                + "\", \"customer\":\"" + customer + "\", \"supplier\":\"" + supplier
                                + "\", \"order_id\":\"" + oi + "\", \"driver\":\"" + dr + "\", \"info\":\"" + inf
                                + "\", \"gross\":\"" + gross + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result
                                + "\", \"sort\":\"" + sort + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett
                                + "\", \"price\":\"" + price + "\", \"total_price\":\"" + total_price
                                + "\", \"cash\":\"" + cash + "\", \"transfer\":\"" + transfer + "\", \"out_weight\":\""
                                + out_weight + "\", \"gap\":\"" + gap + "\", \"operator\":\"" + lbl_operator.getText()
                                + "\", \"bridge_id\":\"" + lbl_bridge_id.getText() + "\", \"api_key\":\"" + Env.api_key
                                + "\"}";
                        ApiManager.api_send();

                        if (ApiManager.status.equals("ok")) {
                            sync = "ok";
                        } else {
                            sync = "insert";
                        }
                        lbl_waiting.setVisible(false);
                    });

                    insertThread.start();
                } else {
                    sync = "insert";
                }

                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO " + Env.table_name
                            + " (ticket, date_in, date_out, vehicle, product_status, product_name, customer, supplier, order_id, driver, info, gross, tare, result, sort, percent, nett, price, total_price, cash, transfer, out_weight, gap, operator, sync) VALUES ('"
                            + ticket + "', '" + date_in + "', '" + date_out + "', '" + vehicle + "', '" + status
                            + "', '" + products_name + "', '" + customer + "', '" + supplier + "', '" + order_id
                            + "', '" + driver + "', '" + infos + "', '" + gross + "', '" + tare + "', '" + result
                            + "', '" + sort + "', '" + percent + "', '" + nett + "', '" + price + "', '" + total_price
                            + "', '" + cash + "', '" + transfer + "', '" + out_weight + "', '" + gap + "', '"
                            + lbl_operator.getText() + "', '" + sync + "')");
                    info.setTitle("Input Data !");
                    info.setHeaderText("BERHASIL !");
                    info.setContentText("Data berhasil di simpan !");
                    info.showAndWait();
                } catch (SQLException e) {
                    info.setTitle("Input Data !");
                    info.setHeaderText("Gagal !");
                    info.setContentText("Data Gagal di simpan !");
                    info.showAndWait();
                    e.printStackTrace();
                }
            } else {
                InternetConnection.connect();
                String connection_status = InternetConnection.connection_status;
                if (connection_status.equals("connected")) {
                    lbl_waiting.setVisible(true);
                    Thread updateThread = new Thread(() -> {
                        ApiManager.api_endpoint = "update";
                        ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                                + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                                + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                                + "\", \"customer\":\"" + customer + "\", \"supplier\":\"" + supplier
                                + "\", \"order_id\":\"" + oi + "\", \"driver\":\"" + dr + "\", \"info\":\"" + inf
                                + "\", \"gross\":\"" + gross + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result
                                + "\", \"sort\":\"" + sort + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett
                                + "\", \"price\":\"" + price + "\", \"total_price\":\"" + total_price
                                + "\", \"cash\":\"" + cash + "\", \"transfer\":\"" + transfer + "\", \"out_weight\":\""
                                + out_weight + "\", \"gap\":\"" + gap + "\", \"operator\":\"" + lbl_operator.getText()
                                + "\", \"bridge_id\":\"" + lbl_bridge_id.getText() + "\", \"api_key\":\"" + Env.api_key
                                + "\"}";
                        ApiManager.api_send();
                        if (ApiManager.status.equals("ok")) {
                            sync = "ok";
                        } else {
                            if (sync.equals("ok")) {
                                sync = "update";
                            } else if (sync.equals("insert")) {
                                sync = "insert";
                            }
                        }
                        lbl_waiting.setVisible(false);
                    });
                    updateThread.start();

                } else {
                    if (sync.equals("ok")) {
                        sync = "update";
                    } else if (sync.equals("insert")) {
                        sync = "insert";
                    }
                }

                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE " + Env.table_name + " SET date_in='" + date_in + "', date_out='"
                            + date_out + "', vehicle='" + vehicle + "', product_status='" + status + "', product_name='"
                            + products_name + "', customer='" + customer + "', supplier='" + supplier + "', order_id='"
                            + order_id + "', driver='" + driver + "', info='" + infos + "', gross='" + gross
                            + "', tare='" + tare + "', result='" + result + "', sort='" + sort + "', percent='"
                            + percent + "', nett='" + nett + "', price='" + price + "', total_price='" + total_price
                            + "', cash='" + cash + "', transfer='" + transfer + "', gap='" + gap + "', out_weight='"
                            + out_weight + "', sync='" + sync + "' WHERE ticket='" + ticket + "'");

                    confirm.setTitle("Print Struk");
                    confirm.setHeaderText("Record !");
                    confirm.setContentText("Print struk timbangan ?");

                    Optional<ButtonType> results = confirm.showAndWait();
                    if (results.orElse(null) == ButtonType.OK) {
                        Struck.ticket = lbl_ticket.getText();
                        Struck.company_name = lbl_company_name.getText();
                        Struck.print_struck();
                    }
                } catch (SQLException e) {
                    info.setTitle("Input Data !");
                    info.setHeaderText("Gagal !");
                    info.setContentText("Data Gagal di simpan !");
                    info.showAndWait();
                    e.printStackTrace();
                }
            }
        } else if (mode.equals("edit")) {
            InternetConnection.connect();
            String connection_status = InternetConnection.connection_status;
            if (connection_status.equals("connected")) {
                lbl_waiting.setVisible(true);
                Thread editThread = new Thread(() -> {
                    ApiManager.api_endpoint = "update";
                    ApiManager.api_query = "{\"ticket\":\"" + ticket + "\", \"date_in\":\"" + date_in
                            + "\", \"date_out\":\"" + date_out + "\", \"vehicle\":\"" + vehicle
                            + "\", \"product_status\":\"" + status + "\", \"product_name\":\"" + products_name
                            + "\", \"customer\":\"" + customer + "\", \"supplier\":\"" + supplier
                            + "\", \"order_id\":\"" + oi + "\", \"driver\":\"" + dr + "\", \"info\":\"" + inf
                            + "\", \"gross\":\"" + gross + "\", \"tare\":\"" + tare + "\", \"result\":\"" + result
                            + "\", \"sort\":\"" + sort + "\", \"percent\":\"" + percent + "\", \"nett\":\"" + nett
                            + "\", \"price\":\"" + price + "\", \"total_price\":\"" + total_price + "\", \"cash\":\""
                            + cash + "\", \"transfer\":\"" + transfer + "\", \"gap\":\"" + gap + "\", \"out_weight\":\""
                            + out_weight + "\", \"operator\":\"" + lbl_operator.getText() + "\", \"bridge_id\":\""
                            + lbl_bridge_id.getText() + "\", \"api_key\":\"" + Env.api_key + "\"}";
                    ApiManager.api_send();
                    if (ApiManager.status.equals("ok")) {
                        sync = "ok";
                    } else {
                        sync = "edit";
                    }
                    lbl_waiting.setVisible(false);
                });
                editThread.start();
            } else {
                sync = "edit";
            }

            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE " + Env.table_name + " SET date_in='" + date_in + "', date_out='" + date_out
                        + "', vehicle='" + vehicle + "', product_status='" + status + "', product_name='"
                        + products_name + "', customer='" + customer + "', supplier='" + supplier + "', order_id='"
                        + order_id + "', driver='" + driver + "', info='" + infos + "', gross='" + gross + "', tare='"
                        + tare + "', result='" + result + "', sort='" + sort + "', percent='" + percent + "', nett='"
                        + nett + "', price='" + price + "', total_price='" + total_price + "', sync='" + sync
                        + "', cash='" + cash + "', transfer='" + transfer + "', gap='" + gap + "', out_weight='"
                        + out_weight + "' WHERE ticket='" + ticket + "'");
                info.setTitle("Edit Data !");
                info.setHeaderText("BERHASIL !");
                info.setContentText("Data berhasil di edit !");
                info.showAndWait();
            } catch (SQLException e) {
                info.setTitle("Edit Data !");
                info.setHeaderText("Gagal !");
                info.setContentText("Data Gagal di edit !");
                info.showAndWait();
                e.printStackTrace();
            }
        }

        Thread csspThread = new Thread(() -> {
            String sc = "";
            if (status.equals("KELUAR")) {
                try {
                    String cs = cb_customer_supplier.getSelectionModel().getSelectedItem();
                    InternetConnection.connect();
                    String connection_status = InternetConnection.connection_status;
                    if (connection_status.equals("connected")) {
                        ApiManager.api_endpoint = "add-calibration-customers";
                        ApiManager.api_query = "{\"api_key\":\"" + Env.api_key + "\", \"customer_name\":\"" + cs
                                + "\"}";
                        ApiManager.api_send();
                        if (ApiManager.status.equals("ok")) {
                            sc = "ok";
                        } else {
                            sc = "insert";
                        }
                    }
                    stmt = con.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM customers WHERE customer_name='" + cs + "'");
                    if (!rs.next()) {
                        stmt = con.createStatement();
                        stmt.executeUpdate(
                                "INSERT INTO customers (customer_name, sync) VALUES ('" + cs + "', '" + sc + "')");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (status.equals("MASUK")) {
                try {
                    String sp = cb_customer_supplier.getSelectionModel().getSelectedItem();
                    InternetConnection.connect();
                    String connection_status = InternetConnection.connection_status;
                    if (connection_status.equals("connected")) {
                        ApiManager.api_endpoint = "add-calibration-suppliers";
                        ApiManager.api_query = "{\"api_key\":\"" + Env.api_key + "\", \"supplier_name\":\"" + sp
                                + "\"}";
                        ApiManager.api_send();
                        if (ApiManager.status.equals("ok")) {
                            sc = "ok";
                        } else {
                            sc = "insert";
                        }
                    }

                    stmt = con.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM suppliers WHERE supplier_name='" + sp + "'");
                    if (!rs.next()) {
                        stmt = con.createStatement();
                        stmt.executeUpdate(
                                "INSERT INTO suppliers (supplier_name, sync) VALUES ('" + sp + "', '" + sc + "')");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        csspThread.start();

        set_calibration_table_process();
        set_calibration_table_finish();
        clear();
    }

    @FXML
    void cb_status_action() {
        set_cb_customer_supplier();
    }

    @FXML
    void menu_logout_action() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oceloy/Login.fxml"));
            Parent port = loader.load();
            Scene scene = new Scene(port);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/oceloy/style/default.css")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/oceloy/images/icon.png"))));
            stage.setTitle(Env.app_name + "- Login");
            stage.setResizable(false);
            stage.show();
            Stage stgs = (Stage) lbl_operator.getScene().getWindow();
            stgs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_port_action() {
        calibrationstop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oceloy/Port.fxml"));
            Parent port = loader.load();
            Scene scene = new Scene(port);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/oceloy/style/default.css")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/oceloy/images/icon.png"))));
            stage.setTitle(Env.app_name + "- Pengaturan Port");
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(we -> calibrationplay());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_report_action() {
        // skip this
    }

    @FXML
    void tab_finish_selection_changed() {
        selected_tab = "finish";
    }

    @FXML
    void tab_process_selection_changed() {
        selected_tab = "process";
    }

    @FXML
    void tbl_data_finish_clicked(MouseEvent event) {
        clear();
        if (event.getClickCount() > 0) {
            if (tbl_data_finish.getSelectionModel().getSelectedItem() != null) {
                CalibrationTableModelFinish selected = tbl_data_finish.getSelectionModel().getSelectedItem();

                tickets = selected.getTicket();
                fill_field();
                cb_customer_supplier.setDisable(true);
            }
        }
    }

    @FXML
    void tbl_data_finish_keyreleased() {
        if (tbl_data_finish.getSelectionModel().getSelectedItem() != null) {
            CalibrationTableModelFinish selected = tbl_data_finish.getSelectionModel().getSelectedItem();

            tickets = selected.getTicket();
            fill_field();
            cb_customer_supplier.setDisable(true);
        }
    }

    @FXML
    void tbl_data_process_clicked(MouseEvent event) {
        clear();
        if (event.getClickCount() > 0) {
            if (tbl_data_process.getSelectionModel().getSelectedItem() != null) {
                CalibrationTableModelProcess selected = tbl_data_process.getSelectionModel().getSelectedItem();

                tickets = selected.getTicket();
                fill_field();
                form_enable();
            }
        }
    }

    @FXML
    void tbl_data_process_keyreleased() {
        if (tbl_data_process.getSelectionModel().getSelectedItem() != null) {
            CalibrationTableModelProcess selected = tbl_data_process.getSelectionModel().getSelectedItem();

            tickets = selected.getTicket();
            fill_field();
        }
    }

    @FXML
    void tf_driver_keypressed() {
        tf_driver.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    @FXML
    void tf_info_keypressed() {
        tf_info.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    @FXML
    void tf_order_number_keypressed() {
        tf_order_number.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    @FXML
    void tf_search_clear_action() {
        tf_search.clear();
        set_calibration_table_process();
        set_calibration_table_finish();
    }

    @FXML
    void tf_search_keypressed() {
        tf_search.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    @FXML
    void tf_search_keyreleased() {
        String search = tf_search.getText();
        if (selected_tab.equals("process")) {
            if (search.equals("")) {
                set_calibration_table_process();
                return;
            }
            tbl_query = "SELECT * FROM " + Env.table_name + " WHERE vehicle LIKE '" + search + "%' OR ticket LIKE '"
                    + search + "%' AND tare = 0";
            tbl_process();
        } else if (selected_tab.equals("finish")) {
            if (search.equals("")) {
                set_calibration_table_finish();
                return;
            }
            tbl_query = "SELECT * FROM " + Env.table_name + " WHERE vehicle LIKE '" + search + "%' OR ticket LIKE '"
                    + search + "%' AND tare != 0";
            tbl_finish();
        }
    }

    /*
     * @FXML void tf_vehicle_keypressed() { tf_vehicle.setTextFormatter(new
     * TextFormatter<>((change) -> { change.setText(change.getText().toUpperCase());
     * return change; })); }
     */
    @FXML
    void btn_sync_action() {
        lbl_waiting.setVisible(true);
        Thread syncDataThread = new Thread(() -> {
            SyncProduct.api_send();
            set_cb_products();
            SyncCustomer.api_send();
            SyncSupplier.api_send();
            set_cb_customer_supplier();
            SyncRecord.api_send();
            set_calibration_table_finish();
            set_calibration_table_process();
            set_cb_vehicle();
            lbl_waiting.setVisible(false);
        });

        syncDataThread.start();
    }

    @FXML
    void tf_outweight_keyreleased() {
        if (tf_outweight.getText().equals("0")) {
            lbl_gap.setText("0");
        } else {
            int nettos = Integer.parseInt(tf_netto.getText().replaceAll("[^0-9]+", ""));
            int ow = Integer.parseInt(tf_outweight.getText().replaceAll("[^0-9]+", ""));

            if (cb_status.getSelectionModel().getSelectedItem().equals("MASUK")) {
                int gaps = nettos - ow;
                lbl_gap.setText(num.format(gaps));
            } else if (cb_status.getSelectionModel().getSelectedItem().equals("KELUAR")) {
                int gaps = ow - nettos;
                lbl_gap.setText(num.format(gaps));
            } else {
                lbl_gap.setText("0");
            }
        }
    }
}
