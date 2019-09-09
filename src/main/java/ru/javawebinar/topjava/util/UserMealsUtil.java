package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceeded_Stream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> localMealList = new ArrayList<>(mealList);
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> datesWithCalories = new HashMap<>();

        ListIterator<UserMeal> iterator = localMealList.listIterator();
        while (iterator.hasNext()) {
            UserMeal userMeal = iterator.next();
            LocalDate date = userMeal.getDateTime().toLocalDate();
            int caloriesLimit = datesWithCalories.getOrDefault(date, caloriesPerDay);
            datesWithCalories.put(date, caloriesLimit - userMeal.getCalories());
            if (!TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                iterator.remove();
        }

        for (UserMeal userMeal : localMealList) {
            boolean exceed = datesWithCalories.get(userMeal.getDateTime().toLocalDate()) < 0;
            result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
        }

        return result;
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded_Stream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> datesWithCalories = mealList.stream().
                collect(Collectors.groupingBy(u -> u.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        Predicate<UserMeal> isBetween = u -> TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime);
        Function<UserMeal, UserMealWithExceed> morfUMWE = u -> new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), datesWithCalories.get(u.getDateTime().toLocalDate()) > caloriesPerDay);

        return mealList.stream().
                filter(isBetween).
                map(morfUMWE).
                collect(Collectors.toList());
    }
}
