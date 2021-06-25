package com.example.servlety;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private String fileName;
    public FileHandler(String fileName){
        this.fileName = fileName;
    }

    public List<Car> read(){
        List<Car> cars = new ArrayList<Car>();
        List<String> data = null;
        try {
            data = Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("data " + data.toString());
        for (String s : data) {
            String[] splitted = s.split(",");
            cars.add(new Car(splitted[0], Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), splitted[3]));
        }

        return cars;
    }
}
