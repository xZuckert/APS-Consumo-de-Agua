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

    opens org.example.apsconsumodeagua to javafx.fxml;
    exports org.example.apsconsumodeagua;
    exports org.example.apsconsumodeagua.controller;
    exports org.example.apsconsumodeagua.model;
    exports org.example.apsconsumodeagua.model.usuario;
    exports org.example.apsconsumodeagua.utils;
    opens org.example.apsconsumodeagua.controller to javafx.fxml;
    exports org.example.apsconsumodeagua.service;
    exports org.example.apsconsumodeagua.manager;
    opens org.example.apsconsumodeagua.manager to javafx.fxml;
    exports org.example.apsconsumodeagua.utils.enums;
    exports org.example.apsconsumodeagua.utils.constantes;
    exports org.example.apsconsumodeagua.utils.uiutils;
}