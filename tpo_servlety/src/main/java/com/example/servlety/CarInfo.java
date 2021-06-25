package com.example.servlety;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CarInfo", value = "/CarInfo")
public class CarInfo extends HttpServlet {
    List<Car> cars;
    public void init() {
        cars = new Database().getData();
//        cars = new FileHandler("cars.txt").read();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        String url = "/DisplayInfo.jsp";
//        url = "/Display";
        List<Car> carsList = new ArrayList<>();
        String carType = request.getParameter("name");

        for (Car c : cars) {
            if (c.getType().toLowerCase().equals(carType.toLowerCase())) {
                carsList.add(c);
            }
        }

        request.setAttribute("carType", carType);
        request.setAttribute("cars", carsList);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
