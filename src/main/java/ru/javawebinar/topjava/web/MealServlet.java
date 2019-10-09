package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static DAO<Meal> mealDAO = new MealDAO();
    private static String EDIT_OR_CREATE = "/mealEdit.jsp";
    private static String MEAL_TABLE = "/meals.jsp";
    private LocalTime startTime = LocalTime.of(0, 0);
    private LocalTime endTime = LocalTime.of(23, 59);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward = "";
        String action = request.getParameter("action");
        request.setAttribute("formatter", formatter);

        if (action != null && action.equalsIgnoreCase("edit")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal editMeal = mealDAO.getEntityById(mealId);
            forward = EDIT_OR_CREATE;
            request.setAttribute("editMeal", editMeal);
        } else if (action != null && action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDAO.delete(mealId);
            forward = MEAL_TABLE;

        } else if (action != null && action.equalsIgnoreCase("listMeal")){
            forward = MEAL_TABLE;

        } else {
            forward = EDIT_OR_CREATE;
        }

        List<MealTo> mealsTo = MealsUtil.getFiltered(mealDAO.getEntities(), startTime, endTime, 2000);
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("mealId");
        LocalDateTime date = LocalDateTime.parse(req.getParameter("date"), formatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal newMeal;
        if (id == null || id.isEmpty()){
            newMeal = new Meal(date, description, calories);
        } else
            newMeal = new Meal(date, description, calories, Integer.parseInt(id));
        mealDAO.editOrAdd(newMeal);

        List<MealTo> mealsTo = MealsUtil.getFiltered(mealDAO.getEntities(), startTime, endTime, 2000);
        req.setAttribute("formatter", formatter);
        req.setAttribute("meals", mealsTo);
        req.getRequestDispatcher(MEAL_TABLE).forward(req, resp);
    }
}
