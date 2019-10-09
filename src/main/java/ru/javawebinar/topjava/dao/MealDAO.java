package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDAO implements DAO<Meal> {
    private final static Map<Integer, Meal> mealDAO = new ConcurrentHashMap<>();

    static {
        for (Meal meal : MealsUtil.getMeals()){
            mealDAO.put(meal.getId(), meal);
        }
    }

    public MealDAO(){}

    @Override
    public void editOrAdd(Meal meal) {
        mealDAO.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealDAO.remove(id);
    }

    @Override
    public List<Meal> getEntities() {
        return new ArrayList<>(mealDAO.values());
    }

    @Override
    public Meal getEntityById(int id) {
        return mealDAO.get(id);
    }


}
