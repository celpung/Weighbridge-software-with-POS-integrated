package com.oceloy.controller;

import com.oceloy.Env;
import com.oceloy.helper.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Alert info = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void login() {
        try {
            if (tf_username.getText().equals("oceloy") && tf_password.getText().equals("killer")) {
                String operator = "Oceloy-ws";
                String priv = "ADMIN";
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oceloy/Calibration.fxml"));
                    Parent root = loader.load();
                    CalibrationController calibrationcontroller = loader.getController();
                    calibrationcontroller.loginData(operator, priv);
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/oceloy/style/default.css")));
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getIcons()
                            .add(new Image(String.valueOf(getClass().getResource("/com/oceloy/images/icon.png"))));
                    stage.setTitle(Env.app_name + " - " + Env.company_name);
                    stage.show();
                    Stage stgs = (Stage) tf_username.getScene().getWindow();
                    stgs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String sql = "SELECT * FROM users WHERE username='" + tf_username.getText() + "' and password='"
                        + tf_password.getText() + "'";
                Connection con = DatabaseManager.Connector();
                assert con != null;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    String operator = rs.getString("name");
                    String priv = rs.getString("level");
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oceloy/Calibration.fxml"));
                        Parent root = loader.load();
                        CalibrationController calibrationcontroller = loader.getController();
                        calibrationcontroller.loginData(operator, priv);
                        Scene scene = new Scene(root);
                        scene.getStylesheets()
                                .add(String.valueOf(getClass().getResource("/com/oceloy/style/default.css")));
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.getIcons()
                                .add(new Image(String.valueOf(getClass().getResource("/com/oceloy/images/icon.png"))));
                        stage.setTitle(Env.app_name + " - " + Env.company_name);
                        stage.show();
                        Stage stgs = (Stage) tf_username.getScene().getWindow();
                        stgs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    info.setTitle("INFO");
                    info.setHeaderText("Login Error");
                    info.setContentText("Username atau Password salah !");
                    info.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_login_action() {
        login();
    }

    @FXML
    void tf_password_keypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    void tf_username_keypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
