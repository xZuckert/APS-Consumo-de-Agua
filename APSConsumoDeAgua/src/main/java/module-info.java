module org.example.apsconsumodeagua {
    requires javafx.graphics;
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
    requires java.sql;

    opens org.example.apsconsumodeagua to javafx.fxml;
    exports org.example.apsconsumodeagua;
    exports org.example.apsconsumodeagua.controllers;
    exports org.example.apsconsumodeagua.models.usuario;
    exports org.example.apsconsumodeagua.factory;
    exports org.example.apsconsumodeagua.utils;
    opens org.example.apsconsumodeagua.controllers to javafx.fxml;
    exports org.example.apsconsumodeagua.services;
    exports org.example.apsconsumodeagua.managers;
    opens org.example.apsconsumodeagua.managers to javafx.fxml;
    exports org.example.apsconsumodeagua.utils.enums;
    exports org.example.apsconsumodeagua.utils.constantes;
    exports org.example.apsconsumodeagua.models.grafico;
    exports org.example.apsconsumodeagua.core;
    exports org.example.apsconsumodeagua.models.base;
}