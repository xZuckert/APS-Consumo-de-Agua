module org.example.apsconsumodeagua {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens org.example.apsconsumodeagua to javafx.fxml;
    exports org.example.apsconsumodeagua;
    exports org.example.apsconsumodeagua.controllers;
    opens org.example.apsconsumodeagua.controllers to javafx.fxml;
}