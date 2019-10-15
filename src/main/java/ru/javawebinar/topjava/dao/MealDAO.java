package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAO implements DAO<Meal> {
    private final static Map<Integer, Meal> mealDAO = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::editOrSave);
    }

    public MealDAO(){}

    @Override
    public void editOrSave(Meal meal) {
        if (meal.getId() == null)
            meal.setId(counter.incrementAndGet());
        mealDAO.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealDAO.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealDAO.values();
    }

    @Override
    public Meal get(int id) {
        return mealDAO.get(id);
    }


}
