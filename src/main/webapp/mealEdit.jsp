<%--
  Created by IntelliJ IDEA.
  User: 13Black
  Date: 09.10.2019
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MealEdit</title>
</head>
<body>
    <h3 align="center">The form edit meal</h3>
    <jsp:useBean id="editMeal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals" name="mealEdit">
        <input type="hidden" name="mealId" value="${editMeal.id}"/><br/>
        Date and Time : <input type="text" name="date" value="${editMeal.dateTime.format(formatter)}"/><br/>
        Description : <input type="text" name="description" value="${editMeal.description}" /><br/>
        Calories : <input type="number" name="calories" value="${editMeal.calories}"/><br/>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</body>
</html>
