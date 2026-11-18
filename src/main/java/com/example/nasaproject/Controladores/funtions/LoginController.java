package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.HelloApplication;
import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    public Button singIn;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;

    private Stage primaryStage;

    UsuarioDao usuarioDao = new UsuarioDao();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onSingInButtonClick()
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Aquí puedes agregar la lógica de autenticación
        if (usuarioDao.authenticate(username, password))
        {
            m_GetDataFromUser();
            try
            {
                primaryStage.close();
                HelloApplication.showMain();// Cierra la ventana de login
            } catch (Exception e) {e.printStackTrace();}
        }
        else
        {
            showMessage("failed authentication","The username or password is incorrect, please try again", Alert.AlertType.ERROR);
            System.out.println("Login fallido");
        }
    }
    private void showMessage(String tittle, String msg, Alert.AlertType alertType)
    {
        Alert alert=new Alert(alertType);
        alert.setTitle(tittle);
        alert.setContentText(msg);
        alert.show();
    }
    private void m_GetDataFromUser()
    {
        Usuario user =usuarioDao.getUsuarioByUsername(usernameField.getText());
        UsuarioActual.getInstance().setUsuario(user);
        registrarAcceso(user.getEmail());
    }
    private void registrarAcceso(String p_email)
    {
        usuarioDao.guardarAcceso(p_email);
    }


    public void onSingUpButtonClick() throws Exception {
        primaryStage.close();
        HelloApplication.showRegister();// Cierra la ventana de login
    }

    public void onGuestModeButtonClick()
    {
        Usuario user = new Usuario();
        user.setNombreUsuario("Guest");
        user.setCveUsuario("INV");
        UsuarioActual.getInstance().setUsuario(user);

        System.out.println("Estas en modo invitado");

        try
        {
            primaryStage.close();
            HelloApplication.showMain();// Cierra la ventana de login
        } catch (Exception e) {e.printStackTrace();}
    }
}