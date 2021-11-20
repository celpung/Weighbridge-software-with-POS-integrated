module com.oceloy {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hsqldb;
    requires java.sql;
    requires org.json;
    requires jssc;
    requires jasperreports;
    requires org.controlsfx.controls;

    opens com.oceloy to javafx.fxml;
    opens com.oceloy.controller to javafx.fxml;
    opens com.oceloy.model to javafx.fxml;

    exports com.oceloy;
    exports com.oceloy.model;

}
