package com.oceloy.helper;

import java.util.Optional;

import com.oceloy.Env;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class ApprovalManager {
    static Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    static Alert info = new Alert(Alert.AlertType.INFORMATION);

    public static String request_approval_query;
    public static String get_approval_query;

    public static String request_approval_status;
    public static String get_approval_status;

    public static String approval_id;
    public static String approval_type;

    public static String ticket;
    public static String approval_operator;

    public static String page;
    public static String title;

    public static void request_approval() {

        String msg = "";

        TextInputDialog dialog = new TextInputDialog("");
        //dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Tulis pesan anda : ");
        //dialog.setContentText("Buat pesan :");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            msg = result.get();
        }

        confirm.setTitle("Approval");
        confirm.setHeaderText("Mengirm Permintaan!");
        confirm.setContentText("Apakah anda ingin meminta admin melakukan perubahan ?");

        Optional<ButtonType> results = confirm.showAndWait();
        if (results.orElse(null) == ButtonType.OK) {
            InternetConnection.connect();
            String connection_status = InternetConnection.connection_status;
            if (connection_status.equals("connected")) {
                DateSet.date_set();
                String date1 = DateSet.date.replaceAll("-", "");
                String date2 = date1.replaceAll(":", "");
                String date3 = date2.replaceAll(" ", "");
                approval_id = date3;

                request_approval_query = "{\"approval_id\":\"" + approval_id + "\", \"ticket\":\"" + ticket
                        + "\", \"type\":\"" + approval_type + "\", \"operator\":\"" + approval_operator
                        + "\", \"bridge_id\":\"" + Env.bridge_id + "\", \"notif\":\"" + msg + "\", \"api_key\":\"" + Env.api_key + "\"}";

                ApiManager.api_endpoint = "request-approval";
                ApiManager.api_query = request_approval_query;
                ApiManager.api_send();

                if (ApiManager.status.equals("ok")) {
                    // request_approval_status = "requested";
                    info.setTitle("Approval");
                    info.setHeaderText("Berhasil");
                    info.setContentText("Berhasil mengirim permintaan !");
                    info.showAndWait();
                } else {
                    request_approval_status = "failed";
                }
            } else {
                info.setTitle("Approval");
                info.setHeaderText("TIDAK ADA KONEKSI !");
                info.setContentText("Membutuhkan sambungan internet untuk approval !");
                info.showAndWait();
            }
        }
    }
}
