package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainTabPaneController implements Initializable {
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab TabAPOD,TabLibrary, TabEpic;
    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        new Thread(()-> {

                Platform.runLater(() -> {
                    try {
                        TabAPOD.setContent(AgregarRecursoFxml("APOD.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        }).start();
        new Thread(()->{

            Platform.runLater(() -> {
                try {
                    TabLibrary.setContent(AgregarRecursoFxml("Library.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
        new Thread(()->{
            Platform.runLater(() -> {
                try {
                    TabEpic.setContent(AgregarRecursoFxml("Epic.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();

    }


    private Node AgregarRecursoFxml(String fxmlFile) throws IOException {

            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));;
            return loader.load();
    }

}
