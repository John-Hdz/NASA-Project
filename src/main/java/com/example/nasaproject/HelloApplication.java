package com.example.nasaproject;

import com.example.nasaproject.Controladores.funtions.LoginController;
import com.example.nasaproject.Controladores.funtions.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import jfxtras.styles.jmetro.JMetro;
//import jfxtras.styles.jmetro.Style;
import org.kordamp.bootstrapfx.BootstrapFX;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        showLogin();
    }
    public static void showLogin() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nasaLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        new JMetro(scene, Style.DARK);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        // Pasar el Stage al controlador
        LoginController loginController = fxmlLoader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMain() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainTabPane.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

//        new JMetro(scene, Style.DARK);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(ClassLoader.getSystemClassLoader().getResource("custumer.css").toExternalForm());

        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    public static void showRegister() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nasaRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400,550);
//        new JMetro(scene, Style.DARK);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        RegisterController registerController = fxmlLoader.getController();
        registerController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("SingUp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}