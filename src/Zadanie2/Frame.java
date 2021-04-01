package Zadanie2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Frame extends JFrame {
    String kraj, gwaluta, json, miasto;
    JPanel jPanel, jPanel2, jPanel3, jPanel4, mainPanel;
    JFXPanel jfxPanel;
    WebEngine webEngine;
    JLabel jl, jLabel1;
    JTextArea jLabel;

    public Frame(String kraj, String json, String gwaluta, double rateNbp, String miasto) {
        this.kraj = kraj;
        this.json = json;
        this.gwaluta = gwaluta;
        this.miasto = miasto;

        setTitle("TPO2 GUI");
        setSize(new Dimension(800, 600));
        this.setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        this.setLayout(new BorderLayout());
        jfxPanel = new JFXPanel();
//        jfxPanel.setLocation(new Point(750,500));
        jfxPanel.setPreferredSize(new Dimension(800, 600));
        jLabel = new JTextArea();
        jLabel.setBackground(Color.WHITE);
        jLabel.setForeground(Color.BLACK);
        jLabel.setOpaque(false);
        jLabel.setEditable(false);
        jLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        jLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        jLabel.setText(this.parseJson(json));
        jLabel.setBounds(0, 30, 90, 100);
        jl = new JLabel();
        jl.setText("Currency: \n" + new Service(this.kraj).getRateFor(this.gwaluta));
        jLabel1 = new JLabel();
        jLabel1.setText("Nbp rate: \n" + rateNbp);

        this.createLayout();

        this.add(mainPanel, "North");
        this.add(jfxPanel, "Center");
        this.pack();

        Platform.runLater(() -> this.createBrowser(jfxPanel, this.kraj));
    }

    public void createBrowser(JFXPanel jfxPanel, String city) {

        Group group = new Group();
        Scene scene = new Scene(group);
        group.autosize();
        jfxPanel.setScene(scene);
        WebView webView = new WebView();
        webView.setPrefSize(jfxPanel.getWidth()/ 1.0, jfxPanel.getHeight() / 1.0);
        group.getChildren().add(webView);
        webEngine = webView.getEngine();
        webEngine.load("https://en.wikipedia.org/wiki/" + city);

    }

    public void createLayout(){
        jPanel = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        mainPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(150, 200));
        jPanel.setBorder(BorderFactory.createTitledBorder("Nbp rate"));
        jPanel2.setBorder(BorderFactory.createTitledBorder("Weather"));
        jPanel2.setPreferredSize(new Dimension(250, 200));
        jPanel3.setBorder(BorderFactory.createTitledBorder("Currency"));
        jPanel3.setPreferredSize(new Dimension(200, 200));
        jPanel4.setPreferredSize(new Dimension(200, 200));
        JButton button = new JButton("Zmien miasto");
        button.setPreferredSize(new Dimension(200, 100));
        button.addActionListener(actionEvent -> this.showdialog() );
        jPanel.add(jLabel1);
        jPanel2.add(jLabel);
        jPanel3.add(jl);
        jPanel4.add(button);
        mainPanel.add(jPanel);mainPanel.add(jPanel2);mainPanel.add(jPanel3);mainPanel.add(jPanel4);
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
        this.miasto = miasto.getText();
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
        String result = "Country: " + this.kraj + "\nCity: " + this.miasto +"\nWind speed: " + speed + "\nTemperature: " + temperature + "\nPressure: " + pressure + "\nDescription: " + description;

        return result;
    }
}
