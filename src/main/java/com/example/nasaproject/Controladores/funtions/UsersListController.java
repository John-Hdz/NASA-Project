package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.HelloApplication;
import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersListController implements Initializable {
    public TableView usersTable;

    private Usuario UsuarioSEleccionado;
    private UsuarioDao usuarioDao = new UsuarioDao();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        m_loadTable();
        SelctTabla();

    }
    private void m_loadTable()
    {
        usersTable.getItems().clear();
        usersTable.setItems(FXCollections.observableList(usuarioDao.findAll()));
    }

    public void UpdateButtonClick()
    {
        if(UsuarioSEleccionado==null)
            showMessage("Error","Please Select a User to Update", Alert.AlertType.WARNING);
        else
            try {
                accountController accountController = new accountController();
                accountController.setUsuario(UsuarioSEleccionado);
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
                loader.setController(accountController);
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal mientras esta esté activa
                stage.setTitle("Actualizar Usuario: " + UsuarioSEleccionado.getEmail());
                Scene scene = new Scene(root,1066,650);
                new JMetro(scene, Style.DARK);
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setScene(scene);
                stage.showAndWait(); // Espera hasta que la ventana emergente sea cerrada
            } catch (Exception e) {e.printStackTrace();}
       m_loadTable();
    }

    public void DeleteButtonClick()
    {
        if(showConfirmation("Confirm","are you sure to delete this user?")) {

//            if (usersTable.getSelectionModel().getSelectedIndex() > 0)
            if(UsuarioSEleccionado!=null)
            {
//                Usuario usuario =(Usuario) usersTable.getSelectionModel().getSelectedItem();
                System.out.println( UsuarioSEleccionado.getNombreUsuario());
                usuarioDao.borrarRegistroTablasUsuarios(UsuarioSEleccionado);
                if (usuarioDao.delete(UsuarioSEleccionado.getIdUsuario())) {
                    m_loadTable();
                    showMessage("Success", "User deleted", Alert.AlertType.INFORMATION);
                } else
                    showMessage("Error", "Error deleting user", Alert.AlertType.ERROR);


            } else
                showMessage("Error", "Please select a user", Alert.AlertType.WARNING);
        }
    }

    private void SelctTabla()
    {
        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
            {
                UsuarioSEleccionado = (Usuario) newSelection;
                System.out.println(UsuarioSEleccionado.getIdUsuario());
            }
        });
    }
    private boolean showConfirmation(String title, String message)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> responce= alert.showAndWait();
        return responce.isPresent() && responce.get() == ButtonType.OK;

    }
    private void showMessage(String tittle, String msg, Alert.AlertType alertType)
    {
        Alert alert=new Alert(alertType);
        alert.setTitle(tittle);
        alert.setContentText(msg);
        alert.show();
    }
}
