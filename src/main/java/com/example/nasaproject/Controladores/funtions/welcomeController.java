package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class welcomeController implements Initializable {

    public Label UserTitle;

    Usuario usuario = UsuarioActual.getInstance().getUsuario();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if (usuario == null) {
            UserTitle.setText("Guest");
            return;
        }
        String displayName = (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank())
                ? usuario.getUsername()
                : usuario.getNombreUsuario();
        UserTitle.setText(displayName != null ? displayName : "Guest");
    }
}
