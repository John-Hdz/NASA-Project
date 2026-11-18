package com.example.nasaproject.Controladores.funtions;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.baseDatos.historialDao;
import com.example.nasaproject.modelsMysql.historial;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class userSearchesController implements Initializable {
    @FXML
    private TableView tblHistorial;

    List<historial> historialList= new ArrayList<>();

     historialDao historialdao = new historialDao();

    historial BusquedaSeleccionada;

    UsuarioActual usuarioActual = UsuarioActual.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarHistorial();
        SelctTabla();
    }

    private void mostrarHistorial() {
        historialList=historialdao.findAll();
        tblHistorial.setItems(FXCollections.observableArrayList(historialList));

    }
    private void SelctTabla()
    {
        tblHistorial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
            {
                BusquedaSeleccionada = (historial)newSelection;
            }
        });
    }

    public void onDeleteSearchButtonClick()
    {
        if (BusquedaSeleccionada == null) {
            return;
        }
        historialdao.delete(BusquedaSeleccionada.getIdHistorial());
        BusquedaSeleccionada = null;
        mostrarHistorial();
    }
}
