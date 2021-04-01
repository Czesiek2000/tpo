package Zadanie2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.*;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    String kraj, gwaluta, json;
    JPanel jPanel;
    JFXPanel jfxPanel;
    WebEngine webEngine;
    JLabel jLabel, jl, jLabel1;

    public Frame(String kraj, String json, String gwaluta, double rateNbp) {
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
        jfxPanel.setSize(new Dimension(1500, 1000));
        this.createLayout();
        jLabel = new JLabel();
        jLabel.setText(this.parseJson(json));
        jl = new JLabel();
        jl.setText("Currency " + new Service(this.kraj).getRateFor(this.gwaluta));
        jLabel1 = new JLabel();
        jLabel1.setText("Nbp rate: " + rateNbp);

        jPanel.add(jLabel1);
        jPanel.add(jLabel);
        jPanel.add(jl);

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
        JTextField kraj = new JTextField();
        JTextField miasto = new JTextField();
        JTextField waluta = new JTextField();
        JTextArea text = new JTextArea();
        text.setVisible(false);
        text.setEditable(false);

        Object[] message = {
                "Kraj:", kraj,
                "Miasto:", miasto,
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

        this.kraj = kraj.getText();
        this.gwaluta = waluta.getText();
//        this.jLabel.setText(this.parseJson());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.reload();
                createBrowser(jfxPanel, miasto.getText());
            }
        });
        Service s = new Service(this.kraj);
        this.jl.setText("Currency: " + s.getRateFor(waluta.getText()));
        this.jLabel.setText(this.parseJson(s.getWeather(miasto.getText())));
        this.jLabel1.setText("Nbp rate: " + s.getNBPRate());
        this.repaint();
    }

    public String parseJson(String json){

        Object obj = null;
        try {
            obj = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray object = (JSONArray) jsonObject.get("weather");
        JSONObject wind = (JSONObject) jsonObject.get("wind");
        Double speed = (Double) wind.get("speed");
        JSONObject temp = (JSONObject) jsonObject.get("main");
        String name = (String) jsonObject.get("name");
        Double temperature = (Double) temp.get("temp");
        int pressure = (int) temp.get("pressure");
        JSONObject weather = (JSONObject) object.get(0);
        String description = (String) weather.get("description");
        String s = (String) weather.get("main");
        String result = "Country: " + this.kraj + " city: " + " wind speed: " + speed + " temperature: " + temperature + " pressure: " + pressure + " description: " + description;

        return result;
    }
}
