module franke.c195project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens franke.c195project to javafx.fxml;
    exports franke.c195project;
    opens franke.c195project.model to javafx.fxml;
    exports franke.c195project.model;
    opens franke.c195project.controller to javafx.fxml;
    exports franke.c195project.controller;
}