package com.example.servlety;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;
    public Database(){
        String url = "jdbc:derby:db/cars;create=true";
        try {
            connection = DriverManager.getConnection(url);
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null,null, "CARS", null);
            Statement stm = connection.createStatement();
            if (!tables.next()){
                String query = "CREATE TABLE CARS(ID INT, BRAND VARCHAR(50), CREATION_YEAR INT, FUEL INT, TYPE VARCHAR(50))";
                stm.execute(query);
                String[] insert = {
                        "INSERT INTO CARS VALUES(1, 'Nysa',1958,161,'dostawczy')",
                        "INSERT INTO CARS VALUES(2, 'Zuk',1980,151,'dostawczy')",
                        "INSERT INTO CARS VALUES(3, 'Syrena',1974,101,'osobowy')",
                        "INSERT INTO CARS VALUES(4, 'Fiat 126p',1978,51,'osobowy')",
                        "INSERT INTO CARS VALUES(5, 'Skoda',2000,150,'osobowy')",
                        "INSERT INTO CARS VALUES(6, 'Volkswagen',2001,145,'osobowy')",
                        "INSERT INTO CARS VALUES(7, 'Trabant',1902,40,'osobowy')",
                        "INSERT INTO CARS VALUES(8, 'Mercedes',1999,250,'F1')",
                        "INSERT INTO CARS VALUES(9, 'McLaren',1998,251,'F1')",
                };

                for (String s : insert) {
                    stm.execute(s);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Car> getData(){

        List<Car> cars = new ArrayList<>();

        try {
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery("SELECT * FROM CARS");
            while (result.next()) {
                cars.add(new Car(result.getString("BRAND"), result.getInt("CREATION_YEAR"), result.getInt("FUEL"), result.getString("TYPE")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }
}
