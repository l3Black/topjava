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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static DAO<Meal> mealDAO = new MealDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        request.setAttribute("formatter", formatter);
        log.info("doGetMeal");
        log.info(action);

        if (action == null || action.isEmpty() || action.equalsIgnoreCase("listMeal")){
            List<MealTo> mealsTo = MealsUtil.getAllTo((List<Meal>) mealDAO.getAll(), 2000);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("create")){
            String mealId = request.getParameter("mealId");
            Meal editMeal = mealId == null || mealId.isEmpty() ?
                    new Meal(LocalDateTime.now(), "description", 0) :
                    mealDAO.get(Integer.parseInt(mealId));
            request.setAttribute("editMeal", editMeal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDAO.delete(mealId);
            response.sendRedirect("/meals.jsp");
        }
*/      request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPostMealServlet");
        for(String[] s : req.getParameterMap().values()){
            log.info(Arrays.toString(s));
        }
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("mealId");
        LocalDateTime date = LocalDateTime.parse(req.getParameter("date"), formatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal newMeal = new Meal(date, description, calories);
        newMeal.setId(id == null || id.isEmpty() ? null : Integer.parseInt(id));
        mealDAO.editOrSave(newMeal);
        resp.sendRedirect("meals");
    }
}
