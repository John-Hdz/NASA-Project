module com.example.nasaproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.jfxtras.styles.jmetro;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.annotation;
    requires java.net.http;
    requires java.desktop;
    requires jdk.jfr;
    requires java.sql;
    requires jbcrypt;
    requires kernel;
    requires layout;
    requires io;

    opens com.example.nasaproject to javafx.fxml;
    exports com.example.nasaproject;
    exports com.example.nasaproject.Modelos.Apod;
    opens com.example.nasaproject.Modelos.Apod to javafx.fxml;
    exports com.example.nasaproject.Modelos.LibraryImage;
    opens com.example.nasaproject.Modelos.LibraryImage to javafx.fxml;
    exports com.example.nasaproject.Modelos.EPIC;
    opens com.example.nasaproject.Modelos.EPIC to com.google.gson;
    exports com.example.nasaproject.modelsMysql;
    opens com.example.nasaproject.modelsMysql to javafx.fxml;
    exports com.example.nasaproject.Controladores.servicios;
    opens com.example.nasaproject.Controladores.servicios to javafx.fxml;
    exports com.example.nasaproject.Controladores.funtions;
    opens com.example.nasaproject.Controladores.funtions to javafx.fxml;

}