package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.baseDatos.ComentarioDao;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.comentariosApod;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class comentariosController implements Initializable {

    public Button btnDelete;
    @FXML
    private TextArea comentariosTextArea;
    @FXML
    private TextField Comentario;
    @FXML
    private TableView tblComentarios;

    UsuarioActual usuarioActual= UsuarioActual.getInstance();

    ComentarioDao comentarioDao = new ComentarioDao();

    List<comentariosApod> comentariosList= new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (usuarioActual.getUsuario() != null && "ADM".equals(usuarioActual.getUsuario().getCveUsuario())) {
            btnDelete.setVisible(true);
        }
        mostrarTextArea();
        mostrarTodaInformacion();
        onReloadButtonClick();
    }
//Aqui nos muestra el area en donde el usuario puede digitar los comentarios y se guardan
    @FXML
    private void save() {
        try {
            String comentarioTexto = Comentario.getText().trim();
            String email = usuarioActual.getUsuario().getEmail(); // Reemplaza esto con el email del usuario actual

            if (comentarioTexto.isEmpty()) {
                showAlert("Error", "Por favor ingresa un comentario.", Alert.AlertType.ERROR);
                return;
            }

            if (!comentarioDao.emailExists(email)) {
                showAlert("Error", "El correo electrónico no está registrado en la base de datos.", Alert.AlertType.ERROR);
                return;
            }

            comentariosApod comentario = new comentariosApod();
            comentario.setEmail(email);
            comentario.setComentario(comentarioTexto);

            if (comentarioDao.save(comentario)) {
                showAlert("Éxito", "Comentario guardado exitosamente.", Alert.AlertType.INFORMATION);
                mostrarTextArea(); // Actualiza la tabla con el nuevo comentario
                Comentario.clear(); // Limpia el campo de texto del comentario
                onReloadButtonClick();
            } else {
                showAlert("Error", "Error al guardar el comentario.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarTextArea() {
        try {
            List<comentariosApod> comentarios = comentarioDao.findAll();

            StringBuilder comentariosText = new StringBuilder();
            for (comentariosApod comentario : comentarios) {
                comentariosText.append("Correo: ").append(comentario.getEmail()).append("\n");
                comentariosText.append("Comentario: ").append(comentario.getComentario()).append("\n\n");
            }
            comentariosTextArea.setText(comentariosText.toString());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al mostrar los comentarios: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

// De aqui comienza a ser la otra tabla en donde puede que el administrador tenga el CRUD
    private void mostrarTodaInformacion(){
        comentariosList=comentarioDao.findAllInformation();
        tblComentarios.setItems(FXCollections.observableArrayList(comentariosList));
    }

    @FXML
    protected void onReloadButtonClick() {
        tblComentarios.getItems().clear();
        tblComentarios.setItems(FXCollections.observableArrayList(comentarioDao.findAll()));

        System.out.println("Reload...");
    }

    @FXML
    private void onDeleteButtonClick() {
        if (showConfirmation("Confirm", "Are you sure you want to delete this comment?")) {
            if (tblComentarios.getSelectionModel().getSelectedItem() != null) {
                comentariosApod com = (comentariosApod) tblComentarios.getSelectionModel().getSelectedItem();
                if (comentarioDao.delete(com.getIdComentario())) {
                    onReloadButtonClick();
                    showAlert("Success", "Comment deleted successfully", Alert.AlertType.INFORMATION);

                } else {
                    showAlert("Error", "Error deleting comment", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Error", "Please select a comment", Alert.AlertType.ERROR);
            }
        }
        System.out.println("DELETE...");
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean showConfirmation(String title, String message){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setContentText(message);
        Optional<ButtonType> responde = confirmation.showAndWait();
        return responde.isPresent() && responde.get() == ButtonType.OK;

    }
}


