module lma.librarymanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires fontawesomefx;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires mysql.connector.j;
    requires javafx.media;
    requires com.google.gson;
    requires java.net.http;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.commons;
    requires annotations;
    requires com.zaxxer.hikari;
    requires jdk.sctp;
    requires java.sql;

    opens lma.librarymanagementapplication to javafx.fxml;
    exports lma.librarymanagementapplication;
}