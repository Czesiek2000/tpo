package Zadanie2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    String kraj;
    String gwaluta;
    JPanel jPanel;
    JFXPanel jfxPanel;
    WebEngine webEngine;
    String json;
    JLabel jLabel;
    JLabel jl;

    public Frame(String kraj, String json, String gwaluta) {
        this.kraj = kraj;
        this.json = json;
        this.gwaluta = gwaluta;

        setTitle("TPO2 GUI");
        setSize(new Dimension(1500, 1200));
        this.setMinimumSize(new Dimension(1500, 1000));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        this.setLayout(new BorderLayout());
        jfxPanel = new JFXPanel();
        jfxPanel.setLocation(new Point(750,500));
        this.createLayout();
        this.parseJson();
        jLabel = new JLabel();
        jLabel.setText(this.parseJson());
        jl = new JLabel();
        jl.setText("Currency " + new Service(this.kraj).getRateFor(this.gwaluta));
        jPanel.add(jl);
        jPanel.add(jLabel);
        this.add(jPanel, "North");
        this.add(jfxPanel, "Center");
        this.pack();
        Platform.runLater(() -> this.createBrowser(jfxPanel, this.kraj));
    }

    public void createBrowser(JFXPanel jfxPanel, String city) {

        Group group = new Group();
        Scene scene = new Scene(group);
        jfxPanel.setScene(scene);
        WebView webView = new WebView();
        group.getChildren().add(webView);
        webEngine = webView.getEngine();
        webEngine.load("https://en.wikipedia.org/wiki/" + city);

    }

    public void createLayout(){
        jPanel = new JPanel();
        JButton button = new JButton("Zmien miasto");
        button.addActionListener(actionEvent -> this.showdialog() );
        jPanel.add(button);
    }

    public void showdialog() {
        JTextField miasto = new JTextField();
        JTextField waluta = new JTextField();
        JTextArea text = new JTextArea();
        text.setVisible(false);

        Object[] message = {
                "Kraj:", miasto,
                "Waluta:", waluta,
                text
        };

        boolean valid = false;
        while(!valid){
            int option = JOptionPane.showConfirmDialog(null, message, "Options", JOptionPane.OK_CANCEL_OPTION);
            if (miasto.getText().length() != 0 && waluta.getText().length() != 0){
                valid = true;
            }

            text.setVisible(true);
            text.setText("Input not valid");

        }

        this.kraj = miasto.getText();
        this.gwaluta = waluta.getText();
        this.jLabel.setText(this.parseJson());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.reload();
                createBrowser(jfxPanel, kraj);
            }
        });
        this.repaint();
        this.jLabel.setText(this.parseJson());
        this.jl.setText(String.valueOf(new Service(this.kraj).getRateFor(this.gwaluta)));
        System.out.println(new Service(this.kraj).getRateFor(this.gwaluta));
        System.out.println(this.kraj + " " + this.gwaluta);
    }

    public String parseJson(){

        JSONParser parser = new JSONParser();
        Object obj = null; //the location of the file
        try {
            obj = parser.parse(new Service(this.kraj).getWeather(this.kraj));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        System.out.println(jsonObject.get("name"));
        JSONArray object = (JSONArray) jsonObject.get("weather");
        JSONObject wind = (JSONObject) jsonObject.get("wind");
        Double speed = (Double) wind.get("speed");
        JSONObject temp = (JSONObject) jsonObject.get("main");
        String name = (String) jsonObject.get("name");
        Double temperature = (Double) temp.get("temp");
        Long pressure = (Long) temp.get("pressure");
        JSONObject weather = (JSONObject) object.get(0);
        String description = (String) weather.get("description");
        String s = (String) weather.get("main");
        String result = "Country: " + this.kraj + " wind speed: " + speed + " temperature: " + temperature + " pressure: " + pressure + " description: " + description;

        return result;
    }
}
