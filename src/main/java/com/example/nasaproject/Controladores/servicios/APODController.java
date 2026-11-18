package com.example.nasaproject.Controladores.servicios;

import com.example.nasaproject.Modelos.Apod.ApodGason;
import com.example.nasaproject.baseDatos.APIDAO;
import com.example.nasaproject.baseDatos.ApodDao;
import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.modelsMysql.Apod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.imageio.ImageIO;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class APODController implements Initializable {

    @FXML
    public VBox VboxAPOD;
    @FXML
    public ScrollPane ScrollAPOD;
    @FXML
    public Label Title;
    @FXML
    public ImageView Imagen;
    @FXML
    public Text txtDescripcion;
    ApodGason apodGason;
    ApodDao apodDao = new ApodDao();
    String api, apiKey;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        APIDAO apidao = new APIDAO();
        api = apidao.resolveApiUrl(0, "NASA_APOD_URL", "https://api.nasa.gov/planetary/apod?");
        apiKey = apidao.resolveApiKey();
        m_ConeAPOD();
    }
    private void m_ConeAPOD()
    {
        StringBuilder jsonResponse = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(api + "api_key=" + apiKey);

        new Thread(()-> {
            try {
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    jsonResponse.append(line);
                }
                rd.close();

                // Deserializar la respuesta JSON en un objeto ApodGason
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                apodGason = gson.fromJson(jsonResponse.toString(), ApodGason.class);

                System.out.println("Se ha obtenido respuesta APOD");
                m_MostImagen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
    private void m_DescImagen()
    {
        String p_imgUrl, p_Nombre;
        Apod apod = new Apod(apodGason.getDate());
        apod.setDescripcion(apodGason.getExplanation());
        apod.setTipo(apodGason.getMediaType());
        apod.setUrl(apodGason.getUrl());
        java.util.Date fechaActual = new java.util.Date();
        apod.setFecha(new Date(fechaActual.getTime()));
        apod.setTitulo(apodGason.getTitle());

        p_imgUrl=apod.getUrl();
        p_Nombre=apod.getTitulo();
        System.out.println(p_imgUrl);

        p_Nombre = p_Nombre.replaceAll(" ", "_");
//        p_imgUrl = p_imgUrl.replaceAll(":", "");

        try {
            URL url = new URL(p_imgUrl);
            BufferedImage image = ImageIO.read(url); // Lee la imagen desde la URL

            // Guarda la imagen en el disco
            String path ="src/main/resources/DownloadImages/"+p_Nombre+".jpg";
            File outputImage = new File(path);
            ImageIO.write(image, "jpg", outputImage);

            System.out.println("Imagen descargada correctamente.");
//            usuarioDao.insertImage(path, apod);
            apodDao.insertImage(path,apod);

            openFile(outputImage.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al descargar imagen.");
        }

    }
    private void m_MostImagen()
    {
        if (apodGason == null) {
            return;
        }
        Platform.runLater(() -> {
            if (apodGason.getUrl() != null && !apodGason.getUrl().isBlank()) {
                Imagen.setImage(new Image(apodGason.getUrl()));
            }
            txtDescripcion.setText(apodGason.toString());
        });
    }

    public void onDownloadButtonClick()
    {
        if (apodGason != null) {
            m_DescImagen();
        }
    }

    private void openFile(String filename)
    {
        if (Desktop.isDesktopSupported())
        {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {/* no imagen */}
        }
    }
}