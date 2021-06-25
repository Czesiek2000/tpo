package com.example.servlety;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Display", value = "/Display")
public class Display extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars = (List<Car>)request.getAttribute("cars");
        PrintWriter pw = response.getWriter();
        if (cars.size() != 0){
            pw.println("<html>" +
                    "<head>" +
                    "<title>Car details page</title>" +
                    "</head>" +
                    "<body>" +
                    "<table style='margin-bottom: 10px'>" +
                    "<tr>" +
                    "<td style='text-align: center'>Index</td>" +
                    "<td style='text-align: center'>Brand</td>" +
                    "<td style='text-align: center'>Production year</td>" +
                    "<td style='text-align: center'>Fuel</td>" +
                    "</tr>");

            for (int i = 0; i < cars.size(); i++){
                int index = i + 1;
                pw.println("<tr><td style='border: 1px solid black; text-align:center; padding:5px'>" + index + "</td>");
                pw.println("<td style='border: 1px solid black; text-align:center; padding:5px'>" + cars.get(i).getBrand() + "</td>");
                pw.println("<td style='border: 1px solid black; text-align:center; padding:5px'>" + cars.get(i).getCreateYear() + "</td>");
                pw.println("<td style='border: 1px solid black; text-align:center; padding:5px'>"  + cars.get(i).getFuel() + "</td></tr>");
            }

            pw.println("</table>" +
                    "<a href='index.jsp'>Go back</a>" +
                    "</body>" +
                    "</html>");
        }else {
            pw.println("There are no vehicles in the database or database doesn't exists");
        }
    }
}
