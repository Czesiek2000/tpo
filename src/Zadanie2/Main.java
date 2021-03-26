/**
 *
 *  @author Czech Michał S20613
 *
 */

package Zadanie2;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();
        System.out.println(rate1);
        // ...
        // część uruchamiająca GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame(s.getKraj(), weatherJson, s.getCurrency());
            }
        });


    }

}