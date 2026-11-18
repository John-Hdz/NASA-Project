package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.HelloApplication;
import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterController {

    public TextField txtfirstName;
    public TextField txtlastName;
    public TextField txtEmail;
    public PasswordField txtPassword;

    private Stage primaryStage;

    UsuarioDao userDao = new UsuarioDao();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onRegisterButtonClick()
    {
        Usuario v_usuario = new Usuario();
        String v_fecha;
        if (txtfirstName.getText().equals("") || txtEmail.getText().equals("") || txtPassword.getText().equals(""))
            showMessage("COMPLETE THE FIELDS [*]","please complete the required fields [*]", Alert.AlertType.ERROR);
        else {
            v_usuario.setNombreUsuario(txtfirstName.getText());
            v_usuario.setUsername(txtfirstName.getText());
            v_usuario.setPassword(txtPassword.getText());
            v_usuario.setEmail(txtEmail.getText());
            v_usuario.setApellidos(txtlastName.getText());
            v_fecha = obtenerFechaActual();
            if (userDao.registerUser(v_usuario, v_fecha, "Casual"))
                showMessage("Registration Completed","The registration was successful, you can now log in", Alert.AlertType.INFORMATION);
        }

    }
    private String obtenerFechaActual()
    {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en el formato deseado (año-mes-día)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formateador);

        return fechaFormateada;
    }
    private void showMessage(String tittle, String msg, Alert.AlertType alertType)
    {
        Alert alert=new Alert(alertType);
        alert.setTitle(tittle);
        alert.setContentText(msg);
        alert.show();
    }

    public void onLoginButtonClick() throws Exception {
        primaryStage.close();
        HelloApplication.showLogin();// Cierra la ventana de login
    }
}
