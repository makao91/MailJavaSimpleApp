module MailJavaFXProgram {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens MainPackage;
    opens MainPackage.view;
    opens MainPackage.controller;
    opens MainPackage.model;
}