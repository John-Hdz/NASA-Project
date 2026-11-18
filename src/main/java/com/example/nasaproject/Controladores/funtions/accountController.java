package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class accountController implements Initializable {
    public TextField cveUsuarioField;
    public TextField emailField;
    public TextField nombreUsuarioField;
    public TextField apellidosField;
    public TextField fechaNacField;
    public TextField nacionalidadField;
    public TextField usernameField;
    public TextField PasswordField;
    public Button updateButton;

    Usuario usuario;
    UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if (usuario == null) {
            usuario = UsuarioActual.getInstance().getUsuario();
        }
        if (usuario != null) {
            completarCampos(usuario);
        }
        Usuario currentUser = UsuarioActual.getInstance().getUsuario();
        if (currentUser != null && "ADM".equals(currentUser.getCveUsuario()))
        {
            cveUsuarioField.setEditable(true);
            cveUsuarioField.setFocusTraversable(false);
            cveUsuarioField.setDisable(false);
        }
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                onUpdateButtonClick();
            }
        });

    }
    public void setUsuario(Usuario p_usuario)
    {
        usuario = p_usuario;
    }
    private void completarCampos(Usuario usuario)
    {
        cveUsuarioField.setText(usuario.getCveUsuario());
        emailField.setText(usuario.getEmail());
        nombreUsuarioField.setText(usuario.getNombreUsuario());
        apellidosField.setText(usuario.getApellidos());
        fechaNacField.setText(usuario.getFechaNac());
        nacionalidadField.setText(usuario.getNacionalidad());
        usernameField.setText(usuario.getUsername());
        // Never expose the stored BCrypt hash in the UI; leave blank unless changing password
        PasswordField.clear();
    }
    public void onUpdateButtonClick()
    {
        if (usuario == null) {
            return;
        }
        Usuario v_usuario = new Usuario();
        v_usuario.setIdUsuario(usuario.getIdUsuario());
        v_usuario.setCveUsuario(cveUsuarioField.getText());
        v_usuario.setEmail(emailField.getText());
        v_usuario.setNombreUsuario(nombreUsuarioField.getText());
        v_usuario.setApellidos(apellidosField.getText());
        v_usuario.setFechaNac(fechaNacField.getText());
        v_usuario.setNacionalidad(nacionalidadField.getText());
        v_usuario.setUsername(usernameField.getText());

        String newPlainPassword = PasswordField.getText();
        if (newPlainPassword != null && !newPlainPassword.isBlank()) {
            v_usuario.setPassword(newPlainPassword);
        } else {
            v_usuario.setPassword(usuario.getPassword());
        }

        if (usuarioDao.update(v_usuario) && usuarioDao.updateRegistro(v_usuario)) {
            showMessage("Success", "Updated user data", Alert.AlertType.INFORMATION);
        } else
            showMessage("Error", "Error updating user data", Alert.AlertType.ERROR);

        Usuario currentUser = UsuarioActual.getInstance().getUsuario();
        if (currentUser != null && currentUser.getIdUsuario() == v_usuario.getIdUsuario()) {
            UsuarioActual.getInstance().setUsuario(v_usuario);
        }
        this.usuario = v_usuario;
        completarCampos(v_usuario);

    }
    private void showMessage(String tittle, String msg, Alert.AlertType alertType)
    {
        Alert alert=new Alert(alertType);
        alert.setTitle(tittle);
        alert.setContentText(msg);
        alert.show();
    }


}
