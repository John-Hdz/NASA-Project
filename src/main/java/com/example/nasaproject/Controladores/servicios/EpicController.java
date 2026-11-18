package com.example.nasaproject.Controladores.servicios;

import com.example.nasaproject.Modelos.EPIC.Epic;
import com.example.nasaproject.baseDatos.APIDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class EpicController implements Initializable {

    public ImageView mainImageView;
    public ProgressIndicator indicadorCarga;
    public Label txtDescription;
    public Label txtRightDescription;
    public Label txtLeftDescription;
    public Button Reload;

    Epic[] epics;
    int posicion;
    String api, apiKey;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        APIDAO apidao = new APIDAO();
        api = apidao.resolveApiUrl(2, "NASA_EPIC_URL", "https://api.nasa.gov/EPIC/api/natural?");
        apiKey = apidao.resolveApiKey();
        m_conectarEpic();
        if (epics != null && epics.length > 0) {
            onRotarDerechaButtonClick();
        }
    }
    private void m_conectarEpic()
    {
        String line;
        StringBuilder jsonResponse = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(api + "api_key=" + apiKey);

        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = rd.readLine()) != null) {
                jsonResponse.append(line);
            }
            rd.close();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            epics = gson.fromJson(jsonResponse.toString(), Epic[].class);

            System.out.println("Se ha obtenido respuesta EPIC");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void m_mostImagen(String p_nombre)
    {
        System.out.println("Cargando Imagen...");
        new Thread(()->
        {
            Platform.runLater(()->
            {
                Image image;
                   try
                  {
                    image = new Image(getClass().getResourceAsStream("/EpicImages/"+p_nombre+".png"));
                  }catch (Exception e){
                     image = new Image(getClass().getResourceAsStream("/EpicImages/dont_found.png"));
                      System.out.println("la Imagen No existe o no se encuentra en el directorio de descargas");
                     }
                   mainImageView.setImage(image);
            });
        }).start();

    }
    private void m_descargarImagenes()
    {
        if (epics == null || epics.length == 0) {
            return;
        }
        System.out.println("Se estan descargando imagenes de EPIC...");
        int v_contador;
        for(v_contador=0; v_contador<epics.length; v_contador++)
        {
            int finalV_contador = v_contador;
                try {
                    String finalP_fecha = epics[finalV_contador].getDate().split(" ")[0].replace("-","/");
                    String p_nombre = epics[finalV_contador].getImage();
                    URL url = new URL("https://epic.gsfc.nasa.gov/archive/natural/"+ finalP_fecha +"/png/"+p_nombre+".png");
                    BufferedImage image = ImageIO.read(url); // Lee la imagen desde la URL

                    // Guarda la imagen en el disco
                    File outputImage = new File("src/main/resources/EpicImages/"+p_nombre+".png");
                    ImageIO.write(image, "png", outputImage);

                    System.out.println("Imagen descargada correctamente.");
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(outputImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al descargar imagen.");
                }
                System.out.println("Se descargaron "+ (finalV_contador+1) +" imagenes");
        }
    }

    public void onRotarIzquierdaButtonClick()
    {
        if (epics == null || epics.length == 0) {
            return;
        }
        if(posicion==0)
            posicion=epics.length-1;
        else
            posicion--;
        String nombre = epics[posicion].getImage();
        txtDescription.setText(epics[posicion].toString());
        txtRightDescription.setText(epics[posicion].SecondContent());
        txtLeftDescription.setText(epics[posicion].FisrtContent());
        m_mostImagen(nombre);
    }

    public void onRotarDerechaButtonClick()
    {
        if (epics == null || epics.length == 0) {
            return;
        }
        if(posicion==epics.length-1)
            posicion=0;
        else
            posicion++;
        String nombre = epics[posicion].getImage();
        txtDescription.setText(epics[posicion].toString());
        txtRightDescription.setText(epics[posicion].SecondContent());
        txtLeftDescription.setText(epics[posicion].FisrtContent());
        m_mostImagen(nombre);

    }

    public void onReloadButtonClick()
    {
        Reload.setDisable(true);
        m_conectarEpic();
        m_descargarImagenes();
        Reload.setDisable(false);
    }
}
