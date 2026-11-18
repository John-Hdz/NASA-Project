package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.baseDatos.ApodDao;
import com.example.nasaproject.baseDatos.datosCompartidos.RepoteApod;
import com.example.nasaproject.modelsMysql.Apod;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReporApodController implements Initializable {
    public TableView<Apod> tblApod;
    ApodDao apodDao = new ApodDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        tblApod.setItems(FXCollections.observableList(apodDao.findAll()));
    }

    private void openPdfFile(String dest) {
        try {
            File file = new File(dest);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
                System.out.println("ARCHIVO ABIERTO");
            }
        } catch (IOException e) {

        }
    }
    public static final  String dest = "Reportes/ReporteDescargas.pdf";
    @FXML
    protected void ReportePdf() throws IOException
    {
        File file = new File(dest);
        file.getParentFile().mkdirs();
        new RepoteApod().createPdf(dest);
        openPdfFile(dest);
    }

    public void onReportButtonClick() throws IOException {
        ReportePdf();
    }
}