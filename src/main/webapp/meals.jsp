<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 13Black
  Date: 05.10.2019
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Meals</title>
    </head>

    <body>
        <h2><a href="index.html">Home</a> </h2>
        <hr>
        <h3 align="center">The table with all Meals</h3>
            <table border="1" cellpadding="5" cellspacing="0" align="center">
                <thead>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th>Action</th>
                </thead>
                <tbody>
                    <jstl:forEach items="${meals}" var="meal">
                        <tr style="color: ${meal.excess ? 'crimson' : 'green'}">
                            <td>${meal.dateTime.format(formatter)}</td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a>
                                <a href="meals?action=edit&mealId=${meal.id}">Edit</a>
                            </td>
                        </tr>
                    </jstl:forEach>
                    <tr><td><a href="meals?action=create">Create meal</a></td></tr>
                </tbody>
            </table>
    </body>
</html>
