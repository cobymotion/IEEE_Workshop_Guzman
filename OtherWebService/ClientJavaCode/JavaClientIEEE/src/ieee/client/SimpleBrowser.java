package ieee.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SimpleBrowser extends JFrame {

    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;

    private final JPanel panel = new JPanel();
    private final JLabel lblStatus = new JLabel();

    private final JProgressBar progressBar = new JProgressBar();

    JList<DtoLugares> list = null;

    public SimpleBrowser() {
        super();
        initComponents();
    }

    private void initComponents() {
        createScene();
        //separaciòn de los paneles
        BorderLayout bl = new BorderLayout(20, 20);
        panel.setLayout(bl);

        //obtenemos los valores del web service
        DtoLugares[] lugares = getDataWS();

        // Panel del lado izquierda 
        list = new JList(lugares);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSize(100, 600);
        list.setFixedCellWidth(150);
        //en caso que los elementos cubran mayor espacio de lo que tenia
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(100, 600));
        ///Revisamos cuando damos click en los elementos
        ListSelectionModel selec = list.getSelectionModel();
        selec.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String url = "https://www.google.com.co/maps?q=" + list.getSelectedValue().getLatitud()
                        + "+" + list.getSelectedValue().getLongitud();
                loadURL(url);// cargamos la url 
            }
        });

        //barra del estado de datos de la pagina 
        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);

        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(lblStatus, BorderLayout.CENTER);
        statusBar.add(progressBar, BorderLayout.EAST);

        ///Panel para agregar 
        JPanel agregado = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Datos:");
        JTextField nombre = new JTextField(20);
        JTextField latitud = new JTextField(20);
        JTextField longitud = new JTextField(20);
        JButton button = new JButton("Guardar");
        agregado.add(label);
        agregado.add(nombre);
        agregado.add(latitud);
        agregado.add(longitud);
        agregado.add(button);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enviarDatos(nombre.getText(), latitud.getText(), longitud.getText());
            }

        });

        panel.add(agregado, BorderLayout.NORTH);
        panel.add(list, BorderLayout.WEST);
        panel.add(jfxPanel, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setPreferredSize(new Dimension(1224, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void createScene() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView view = new WebView();
                engine = view.getEngine();

                engine.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {

                        SimpleBrowser.this.setTitle(newValue);

                    }
                });

                engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
                    @Override
                    public void handle(final WebEvent<String> event) {

                        lblStatus.setText(event.getData());

                    }
                });

                engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {

                        progressBar.setValue(newValue.intValue());

                    }
                });
                jfxPanel.setScene(new Scene(view));
            }
        });
    }

    public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String tmp = toURL(url);

                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }

                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    public static void main(String[] args) {

        SimpleBrowser browser = new SimpleBrowser();
        browser.setVisible(true);
        browser.loadURL("http://google.com.mx/maps");

    }

    private DtoLugares[] getDataWS() {
        String cadena = getJSOn();
        java.lang.reflect.Type tipoLista = new TypeToken<List<DtoLugares>>() {
        }.getType();
        Gson gson = new Gson();
        List<DtoLugares> lugares = gson.fromJson(cadena, tipoLista);
        DtoLugares[] array = new DtoLugares[lugares.size()];
        for (int i = 0; i < lugares.size(); i++) {
            array[i] = lugares.get(i);
        }
        return array;
    }

    private String getJSOn() {
        try {

            URL url = new URL("http://localhost:8080/testlugares/webresources/ws/lugares");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            String salida = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();
            
            
            
            
           
            return salida;

        } catch (MalformedURLException e) {

            e.printStackTrace();
            throw new RuntimeException();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void enviarDatos(String text, String text0, String text1) {

        try {

            URL url = new URL("http://localhost:8080/testlugares/webresources/ws/lugares");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"id\":0,\"nombre\":\""+ text +"\",\"latitud\":\""+ text0 +"\",\"longitud\":\""+ text1 +"\"}";
            

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
            DefaultListModel<DtoLugares> modelo = new DefaultListModel<>();
            DtoLugares[] lugares = getDataWS();
            for (DtoLugares lugare : lugares) {
                modelo.addElement(lugare);
            }
            list.setModel(modelo);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
