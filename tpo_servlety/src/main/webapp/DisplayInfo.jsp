<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.servlety.Car" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList cars = (ArrayList)request.getAttribute("cars"); %>
<html>
<head>
    <title>Car information</title>
</head>
<body>
<p>Type of car is: ${carType}</p>
 <%if (cars.size() != 0) {%>
    <table style="border: 1px solid black">
        <tr>
            <th style="border: 1px solid black; padding: 10px">Id</th>
            <th style="border: 1px solid black; padding: 10px">Brand</th>
            <th style="border: 1px solid black; padding: 10px">Production year</th>
            <th style="border: 1px solid black; padding: 10px">Fuel consumption</th>
        </tr>
        <% for(int i = 0; i < cars.size(); i++) { %>
         <tr>
             <td style="border: 1px solid black; padding: 10px"><%= i + 1 %></td>
             <% Car car = (Car) cars.get(i); %>
             <td style="border: 1px solid black; padding: 10px"><%=car.getBrand()%></td>
             <td style="border: 1px solid black; padding: 10px"><%=car.getCreateYear()%></td>
             <td style="border: 1px solid black; padding: 10px"><%=car.getFuel()%></td>
         </tr>
         <% } %>
    </table>
 <% } %>
 <% if (cars.size() == 0) { %>
 No cars match to that category
 <% } %>
    <br />
 <a href="index.jsp">Back</a>
</body>
</html>
