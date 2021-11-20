package com.oceloy;

import com.oceloy.helper.DatabaseManager;
import com.oceloy.helper.ListenerData;
import com.oceloy.helper.SyncCustomer;
import com.oceloy.helper.SyncData;
import com.oceloy.helper.SyncProduct;
import com.oceloy.helper.SyncSupplier;
import com.oceloy.helper.SyncUser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    String fxml = "Login";

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseManager.create_database();
        SyncUser.api_send();
        Thread startThread = new Thread(() -> {
            SyncData.syncing();
            SyncProduct.api_send();
            SyncCustomer.api_send();
            SyncSupplier.api_send();

        });
        startThread.start();
        load_view();
    }

    private void load_view() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oceloy/" + fxml + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/oceloy/style/default.css")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/oceloy/images/icon.png"))));
        stage.setTitle(Env.app_name + " - " + Env.company_name);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}