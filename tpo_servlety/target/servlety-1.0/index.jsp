<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cars servlet</title>
</head>
<body>
<h1><%= "Cars list" %></h1>
<form action="CarInfo" method="post">
    <label>Car type</label>
    <input type="text" name="name">
    <br/>
    <input type="submit" value="Submit">
</form>
<br/>
</body>
</html>