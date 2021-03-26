package Zadanie2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {
    String kraj;
    String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String apiKey =  "&appid=fa897a8454a533e23262f18d5a9ab628";
    String currency;

    public Service(String kraj) {
        this.kraj = kraj;
    }

    public String getWeather(String city) {
        String link = weatherUrl + city + apiKey;

        return apiHandler(link);
    }

    public Double getRateFor(String currency) {
        this.currency = currency;
        String API = "https://api.exchangeratesapi.io/latest?symbols=" + currency;
        String result = apiHandler(API);
        Double curr = 0.0;

        try {
            JSONParser parser = new JSONParser();
            JSONObject data_obj = (JSONObject) parser.parse(apiHandler(API));
            JSONObject obj = (JSONObject) data_obj.get("rates");

            curr = (Double)obj.get(currency);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return curr;
    }

    public Double getNBPRate() {
        String nbpa = apiHandler("https://www.nbp.pl/kursy/kursya.html");
        String nbpb = apiHandler("https://www.nbp.pl/kursy/xml/b011z210317.xml");
        System.out.println(nbpa);
        return 1.0;
    }

    public String apiHandler(String link){
        String result = "";
        try {
            URL url = new URL(link);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                result = inline;

                scanner.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getKraj(){
        return this.kraj;
    }

    public String getCurrency(){
        return this.currency;
    }

}
