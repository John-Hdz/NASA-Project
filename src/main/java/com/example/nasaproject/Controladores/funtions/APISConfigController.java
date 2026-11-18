package com.example.nasaproject.Controladores.funtions;

import com.example.nasaproject.baseDatos.APIDAO;
import com.example.nasaproject.modelsMysql.APIKey;
import com.example.nasaproject.modelsMysql.Api;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class APISConfigController implements Initializable
{
    public TextField txtapiKey;
    public TextField txtapiApod;
    public TextField txtapiEpic;
    public TextField textapiLibrary;
    APIDAO apidao = new APIDAO();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        cargarCampos();
    }

    private void cargarCampos() {
        APIKey currentKey = apidao.getApiKey();
        txtapiKey.setText(currentKey != null && currentKey.getApiKey() != null ? currentKey.getApiKey() : apidao.resolveApiKey());
        java.util.List<Api> apis = apidao.getApis();
        txtapiApod.setText(apis.size() > 0 ? apis.get(0).getApi() : apidao.resolveApiUrl(0, "NASA_APOD_URL", "https://api.nasa.gov/planetary/apod?"));
        txtapiEpic.setText(apis.size() > 1 ? apis.get(1).getApi() : apidao.resolveApiUrl(1, "NASA_LIBRARY_URL", "https://images-api.nasa.gov/search?"));
        textapiLibrary.setText(apis.size() > 2 ? apis.get(2).getApi() : apidao.resolveApiUrl(2, "NASA_EPIC_URL", "https://api.nasa.gov/EPIC/api/natural?"));
    }

    public void onUpdateButtonClick()
    {
        APIKey apiKey = new APIKey();
        apiKey.setApiKey(txtapiKey.getText());
        apiKey.setFechaCreacion(obtenerFechaActual());
        apiKey.setFechaVencimiento(obtenerFechaVencimiento());
        apiKey.setIdApi(1);
        if (apidao.updateKey(apiKey)) {
            showMessage("Success", "Updated API Key", Alert.AlertType.INFORMATION);
        } else
            showMessage("Error", "Error updating API", Alert.AlertType.ERROR);

        Api api = new Api();
        api.setApi(txtapiApod.getText());
        api.setIdApi(1);
        api.setTipoApi("APOD");
        m_updateByApi(api);

        api = new Api();
        api.setApi(textapiLibrary.getText());
        api.setIdApi(2);
        api.setTipoApi("LIBRY");
        m_updateByApi(api);

        api = new Api();
        api.setApi(txtapiEpic.getText());
        api.setIdApi(3);
        api.setTipoApi("EPIC");
        m_updateByApi(api);
    }
    private void m_updateByApi(Api p_api)
    {
        if (apidao.update(p_api)) {
            showMessage("Success", "Updated API", Alert.AlertType.INFORMATION);
        } else
            showMessage("Error", "Error updating API", Alert.AlertType.ERROR);
        cargarCampos();
    }

    private void showMessage(String tittle, String msg, Alert.AlertType alertType)
    {
        Alert alert=new Alert(alertType);
        alert.setTitle(tittle);
        alert.setContentText(msg);
        alert.show();
    }
    private String obtenerFechaActual() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en el formato deseado (año-mes-día)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formateador);

        return fechaFormateada;
    }
    private String obtenerFechaVencimiento() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Agregar un año a la fecha actual
        LocalDate fechaSiguienteAno = fechaActual.plusYears(1);

        // Formatear la fecha en el formato deseado (año-mes-día)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaSiguienteAno.format(formateador);

        return fechaFormateada;
    }
}
