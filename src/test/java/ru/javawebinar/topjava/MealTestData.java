package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MealTestData {
    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2019, 10, 23, 20, 28), "dinner", 700);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2019, 10, 23, 15, 28), "lunch", 800);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2019, 10, 23, 11, 28), "breakfast", 400);
    public static final Meal MEAL4 = new Meal(100005, LocalDateTime.of(2019, 10, 22, 20, 28), "dinner", 700);
    public static final Meal MEAL5 = new Meal(100006, LocalDateTime.of(2019, 10, 22, 15, 28), "lunch", 800);
    public static final Meal MEAL6 = new Meal(100007, LocalDateTime.of(2019, 10, 22, 11, 28), "breakfast", 600);

    public static final List<Meal> mealsForUser0 = new ArrayList<>(Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));

    public static final List<Meal> mealsBetweenDatesForUser0 = new ArrayList<>(Arrays.asList(MEAL1, MEAL2, MEAL3));
}
