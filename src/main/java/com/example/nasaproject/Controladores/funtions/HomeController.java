package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.HelloApplication;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Label txtEmail;
    public Label txtUser;
    public Button btnUserControl;
    public Button btnAPIS_configuration;

    public Button btnAccountSettings;
    public Button btnSearchHistory;


    public Pane fondoPrincipal;
    public Button btnReviews;
    public Button btnUser_searches;
    public Button btnGuestMode;
    public Button btnDownloadReport;

    Usuario usuario = UsuarioActual.getInstance().getUsuario();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        boolean isGuest = usuario == null || "INV".equals(usuario.getCveUsuario());
        if (isGuest) {
            txtUser.setText("Guest");
            txtEmail.setText("");
            btnSearchHistory.setDisable(true);
            btnReviews.setDisable(true);
            btnAccountSettings.setDisable(true);
            btnGuestMode.setVisible(true);
            return;
        }

        txtUser.setText(usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isBlank()
                ? usuario.getNombreUsuario()
                : usuario.getUsername());
        txtEmail.setText(usuario.getEmail() != null ? usuario.getEmail() : "");

        if ("ADM".equals(usuario.getCveUsuario())) {
            btnUserControl.setVisible(true);
            btnAPIS_configuration.setVisible(true);
            btnUser_searches.setVisible(true);
            btnDownloadReport.setVisible(true);
        }
    }

    public void onLoadCountSettingsButtonClick() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        accountController accountController = new accountController();
        accountController.setUsuario(usuario);
        loader.setController(accountController);
        Parent welcomeContent = loader.load();
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);

    }

    public void onMostHistorialButtonClick() throws IOException
    {
        Parent welcomeContent = AgregarRecursoFxml("historial.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);
    }

    public void onMostComentariosButtonClick() throws IOException
    {
        Parent welcomeContent = AgregarRecursoFxml("comentarios.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);
    }

    public void onMostUserSearchesButtonClick() throws IOException
    {
        Parent welcomeContent = AgregarRecursoFxml("userSearches.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);
    }

    public void onMostUsuariosButtonClick() throws IOException
    {
        Parent welcomeContent = AgregarRecursoFxml("UsersList.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);
    }

    public void onLoadConfAPISButtonClick() throws IOException
    {
        Parent welcomeContent = AgregarRecursoFxml("APISConfig.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(welcomeContent);
    }
    public void onloasdGuestModeButtonClick() throws IOException
    {
        Parent registerContent = AgregarRecursoFxml("nasaRegister.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(registerContent);
    }
    public void onloadReporButtonClick() throws IOException {
        Parent registerContent = AgregarRecursoFxml("ReporApod.fxml");
        fondoPrincipal.getChildren().clear();
        fondoPrincipal.getChildren().add(registerContent);
    }
    private Parent AgregarRecursoFxml(String fxmlFile) throws IOException
    {

        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        return loader.load();
    }
}
