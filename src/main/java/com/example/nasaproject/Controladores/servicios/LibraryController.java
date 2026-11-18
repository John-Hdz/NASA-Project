package com.example.nasaproject.Controladores.servicios;

import com.example.nasaproject.Modelos.LibraryImage.Item;
import com.example.nasaproject.Modelos.LibraryImage.LibraryJgason;
import com.example.nasaproject.Modelos.LibraryImage.Link;
import com.example.nasaproject.baseDatos.APIDAO;
import com.example.nasaproject.baseDatos.UsuarioDao;
import com.example.nasaproject.baseDatos.datosCompartidos.UsuarioActual;
import com.example.nasaproject.modelsMysql.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {

    @FXML
    public TextField txtfieldBusqueda;
    @FXML
    public Button Buscar;
    @FXML
    public FlowPane FPCentroImagenes;
    @FXML
    public CheckBox CBimagenes,CBAudios,CBVideos;

    LibraryJgason libraryJgason;

    UsuarioDao usuarioDao=new UsuarioDao();
    Usuario usuario = UsuarioActual.getInstance().getUsuario();
    String api;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        api = new APIDAO().resolveApiUrl(1, "NASA_LIBRARY_URL", "https://images-api.nasa.gov/search?");
        onSearchButtonClick();
    }
    //Metodo para realizar la consulta de la api
    private void m_conectLibrary() {
        String busqueda,line, mediaTyipe;
        BufferedReader rd;
        HttpResponse response;
        busqueda=txtfieldBusqueda.getText();
        mediaTyipe= m_confMediaType();

        StringBuilder jsonResponse = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        if (!busqueda.isEmpty() && usuario != null && usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            usuarioDao.guardarBusqueda(usuario.getEmail(), busqueda, obtenerFechaActual());
        }

        busqueda=busqueda.replaceAll(" ","%20");
        System.out.println(busqueda);
        HttpGet request = new HttpGet(api+"q="+busqueda+"&media_type="+mediaTyipe+"&page=1&page_size=100");
        try {
            //Hcer Peticion de la informacion
            response = client.execute(request);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {throw new RuntimeException(e);}
        //leer y almacenar la respuesta e un stringBuilder
        try {
            while ((line = rd.readLine()) != null)
                jsonResponse.append(line);
            rd.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        //Traducir y convertir a un Objeto para la obtencion de los datos por individual
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        libraryJgason = gson.fromJson(jsonResponse.toString(), LibraryJgason.class);
        System.out.println("Se ah obtenido respuesta de Library");
    }
    //Permite descargar la imagen con el url obtenido del objeto jgason
    private void m_DescImagen(String p_imgUrl, String p_Nombre, String typeData)
    {
        p_Nombre = p_Nombre.replaceAll(" ", "_");
        p_Nombre = p_Nombre.replaceAll(":", "");

        try {
            URL url = new URL(p_imgUrl);
            BufferedImage image = ImageIO.read(url); // Lee la imagen desde la URL

            // Guarda la imagen en el disco
            File outputImage = new File("src/main/resources/DownloadImgLibrary/"+p_Nombre+"."+typeData);
            ImageIO.write(image, typeData, outputImage);

            System.out.println("Recurso descargado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al descargar el recurso.");
        }
    }
    private void m_downloadVideo(String videoUrl) {
//        String videoUrl = "https://images-assets.nasa.gov/video/Apollo%2011%20Overview/Apollo%2011%20Overview~orig.mp4";
        String saveFilePath = "src/main/resources/DownloadImgLibrary/video.mp4";

        try {
            // Abrir conexión con la URL
            URL url = new URL(videoUrl);
            URLConnection connection = url.openConnection();

            // Configurar tiempo de lectura y conexión
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            // Crear stream de entrada
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

            // Crear stream de salida para guardar el archivo descargado
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            // Leer los datos del stream de entrada y escribirlos en el archivo de salida
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Cerrar los streams
            outputStream.close();
            inputStream.close();

            System.out.println("Video descargado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Asigna las imagenes conseguidas de la consulta a los ImageView declarados en la interfaz
    private void m_prepararImagenes(LibraryJgason libraryJgason)
    {
        FPCentroImagenes.getChildren().clear();
        if (libraryJgason != null &&
                libraryJgason.getCollection() != null &&
                libraryJgason.getCollection().getItems() != null)
        {
            List<Item> items = libraryJgason.getCollection().getItems();
            for (Item item : items)
            {
                List<Link> links = item.getLinks();
                if (links != null && !links.isEmpty())
                {
                    String imageUrl = links.getFirst().getHref();
                    new Thread(() ->
                    {
                        m_AcomodoPorMediaType(imageUrl, item);
                    }).start();
                }else
                    new Thread(() ->
                    {
                        m_AcomodoPorMediaType("", item);
                    }).start();
            }
        }else System.out.println("no se obtuvo respuesta de la api");
        System.out.println("La carga de imagenes ha terminado");

    }
    private void m_prepararDetallesImagen(ImageView p_image, String p_Descripcion, String urlResorce)
    {
        p_image.setFitHeight(300);
        p_image.setPreserveRatio(true);

        Label label = new Label(p_Descripcion);
        Stage stage = new Stage();
        Button button = new Button();
        button.setText("Open");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                m_openWebpage(urlResorce);
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().add(button);
        button = new Button();
        button.setText("Download");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = urlResorce.substring(urlResorce.length() - 3);
                if(url.equals("jpg"))
                    m_DescImagen(urlResorce, "prueba", url);
                else if(url.equals("mp4"))
                    m_downloadVideo(urlResorce);
            }
        });
        hBox.getChildren().add(button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(p_image, label,hBox);
        vBox.setStyle("-fx-background-color: #1a1a1c");

        Scene scene = new Scene(vBox, 490, 430);

        new JMetro(scene, Style.DARK);
        stage.setTitle("Image Details");
        stage.setScene(scene);
        stage.show();


    }
    public void onSearchButtonClick()
    {
        new Thread(() -> {
            m_conectLibrary();
            Platform.runLater(() -> m_prepararImagenes(libraryJgason));
        }).start();
    }
    private String m_confMediaType()
    {
        StringBuilder mediaType = new StringBuilder();
        if(CBimagenes.isSelected())
            mediaType.append("image,");
        if(CBVideos.isSelected())
            mediaType.append("video,");
        if(CBAudios.isSelected())
            mediaType.append("audio,");

        if (mediaType.isEmpty()) {
            return "image";
        }
        return mediaType.substring(0, mediaType.length() - 1);
    }
    private void m_AcomodoPorMediaType( String p_imageUrl, Item item)
    {
        try {
            String Description = item.getData().getFirst().getDescription();
            String mediaType = item.getData().getFirst().getMediaType();

            if (mediaType.equals("image")) {
                Image image = new Image(p_imageUrl);
                Platform.runLater(() -> {
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(175);
                    imageView.setPreserveRatio(true);
                    imageView.setOnMouseClicked(event ->
                            m_prepararDetallesImagen(new ImageView(image), Description, p_imageUrl));
                    FPCentroImagenes.getChildren().add(imageView);
                });
            } else if (mediaType.equals("audio"))
            {
                System.out.println("se obtuvo una respuesta de audio");
                String urlResorce = m_GetUrlResorces(item.getHref(), ".mp3");
                Platform.runLater(() -> {
                    try
                    {
                        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/DownloadImgLibrary/Audio.jpg")));
                        imageView.setFitHeight(175);
                        imageView.setPreserveRatio(true);
                        imageView.setOnMouseClicked(event ->
                                m_prepararDetallesImagen(new ImageView(new Image(getClass().getResourceAsStream("/DownloadImgLibrary/Audio.jpg"))),
                                        Description, urlResorce));
                        FPCentroImagenes.getChildren().add(imageView);
                    }catch (Exception e){
                        System.out.println("Error al obtener la imagen");
                    }
                });
            } else if (mediaType.equals("video")) {
                String urlResorce = m_GetUrlResorces(item.getHref(), ".mp4");
                Platform.runLater(() -> {
                    ImageView imageView = new ImageView(new Image(p_imageUrl));
                    imageView.setFitHeight(175);
                    imageView.setPreserveRatio(true);
                    imageView.setOnMouseClicked(event ->
                            m_prepararDetallesImagen(new ImageView(new Image(p_imageUrl)), Description, urlResorce));
                    FPCentroImagenes.getChildren().add(imageView);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void m_openWebpage(String url)
    {
        try {
            URI uri = new URI(url);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(uri);
                }
            } else {
                System.err.println("Desktop is not supported. Cannot open the web page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String m_GetUrlResorces(String p_jason, String mediaType)
    {
        String line, urlResorce = "";
        StringBuilder jsonResponse = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(p_jason.replaceAll(" ","%20"));


        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            // Leer la respuesta JSON y almacenarla en una cadena
            while ((line = rd.readLine()) != null) {
                jsonResponse.append(line);
            }
            rd.close(); // Cerrar el BufferedReader después de leer la respuesta

            String[] linksResorces = jsonResponse.toString().replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .trim().split(",");

            for (String s : linksResorces)
                // Verificar si el string termina con el sufijo especificado
                if (s.endsWith(mediaType))
                    urlResorce=s;
            return urlResorce.trim().replaceAll(" ","%20");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    private String obtenerFechaActual()
    {
        // Obtener la fecha y hora actuales
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Formatear la fecha y hora en el formato deseado (año-mes-día hora:minuto:segundo)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formateador);

        return fechaHoraFormateada;
    }
}