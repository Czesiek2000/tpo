package Zadanie2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.*;

public class Service {

    String kraj;
    String city;
    String isoCode;
    String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String apiKey =  "&appid=fa897a8454a533e23262f18d5a9ab628";
    String currency;
    String curr2;
    JSONObject nbpRate;

    public Service(String kraj) {
        this.kraj = (kraj);
        getIso();
    }

    public String getWeather(String city) {
        this.city = city;
        String link = weatherUrl + city + ","+ isoCode + apiKey;

        return apiHandler(link);
    }

    public Double getRateFor(String currency) {
        this.currency = currency;
        String API = "https://api.exchangerate.host/latest?symbols=" + currency + "&base=" + this.curr2;
        String result = apiHandler(API);
        Double curr = 0.0;

        try {
            JSONObject data_obj = new JSONObject(result);
            JSONObject obj = (JSONObject) data_obj.get("rates");

            curr = Double.parseDouble(obj.get(currency).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curr;
    }

    public Double getNBPRate() {
        String nbpa = apiHandler("https://www.nbp.pl/kursy/xml/a061z210330.xml");
        String nbpb = apiHandler("https://www.nbp.pl/kursy/xml/b012z210324.xml");
        Double rate = 0.0;

        if (!nbpa.isEmpty() && !nbpb.isEmpty()) {
            JSONObject objA = XML.toJSONObject(nbpa);
            JSONObject objB = XML.toJSONObject(nbpb);
            JSONArray array = new JSONArray();
            array.put(objA);
            array.put(objB);

            JSONObject nbpCurrency = findCurrency(array);

            if (nbpCurrency != null) {
                rate = Double.parseDouble((String) nbpCurrency.get("kurs_sredni").toString().replace(",", "."));
                nbpRate = nbpCurrency;
            }else {
                rate = null;
            }
        }
        return rate;
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

    public void getIso() {
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }

        this.isoCode = countries.get(kraj);

        try {
            curr2 = Currency.getInstance(new Locale("", this.isoCode)).getCurrencyCode();

        } catch (Exception e) {
            System.out.println("Podany błedny kraj!");
            return;
        }

    }

    public JSONObject findCurrency(JSONArray arr) {
        if (this.currency.equals("PLN")){
            return new JSONObject("{\"kurs_sredni\":\"1\",\"kod_waluty\":\"PLN\",\"nazwa_waluty\":\"polski zloty\",\"przelicznik\":1}");
        }

        JSONObject tmp;

        for (int i = 0; i < arr.length(); i++) {
            for (int j = 0; j < arr.getJSONObject(i).optJSONObject("tabela_kursow").getJSONArray("pozycja").length(); j++) {
                tmp = arr.getJSONObject(i).optJSONObject("tabela_kursow").getJSONArray("pozycja").getJSONObject(j);
                if (tmp.optString("kod_waluty").equals(this.currency)) {
                    return tmp;
                }
            }
        }

        return null;
    }

    public String getKraj(){
        return this.kraj;
    }

    public String getCurrency(){
        return this.currency;
    }

    public String getCity(){
        return this.city;
    }

}
