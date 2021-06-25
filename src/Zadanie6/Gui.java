package Zadanie6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    JTextArea field;
    JTextField enterField;
    JTextField nick;
    JButton button;
    String title;
    boolean showBtn;
    String message = "";
    Gui(String title, boolean showBtn) {
        this.title = title;
        this.showBtn = showBtn;
        setTitle(title);
        setLayout(new GridLayout(5,1));
        field = new JTextArea();
        enterField = new JTextField();
        nick = new JTextField();
//        enterField.setText("Enter message");
//        field.setText("messages");
        field.setEditable(false);
        add(field);
        if (this.showBtn){
            this.button = new JButton("Send");
            add(nick);
            add(enterField);
            update();
            add(this.button);
        }
        setPreferredSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public JTextArea getTextField(){
        return this.field;
    }

    public static void main(String[] args) {
        Gui gui = new Gui("test", true);
        System.out.println(gui.nick.getText());
    }

    public void setMessage(String message) {
        this.field.setText(message);
    }

    public String getMessage() {
        return this.field.getText();
    }

    public void update() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button){
                    message = enterField.getText();
                    field.setText(message + "\n" + field.getText());
                    enterField.setText("");
//                    nick.setVisible(false);
                }
            }
        });
    }

    public void setMessage(){
        message = enterField.getText();
        field.setText(message + "\n" + field.getText());
    }
    public String getNick(){
        return this.nick.getText();
    }
}
