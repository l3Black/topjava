package ru.javawebinar.topjava.service;

import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1.getId(), SecurityUtil.authUserId());
        assertThat(meal).isEqualToComparingFieldByField(MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getAlien(){
        service.get(MEAL1.getId(), UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() {
        List<Meal> expected = new ArrayList<>(mealsForUser0);
        expected.remove(MEAL1);
        service.delete(MEAL1.getId(), SecurityUtil.authUserId());
        assertThat(service.getAll(SecurityUtil.authUserId())).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien(){
        service.delete(MEAL1.getId(), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound(){
        service.delete(9999, SecurityUtil.authUserId());
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = service.getBetweenDates(LocalDate.of(2019, 10, 23), LocalDate.of(2019, 10, 25), SecurityUtil.authUserId());
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(mealsBetweenDatesForUser0);
    }

    @Test
    public void getAll() {
        List<Meal> expected = mealsForUser0;
        assertThat(service.getAll(SecurityUtil.authUserId())).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("updated description");
        updated.setCalories(200);
        service.update(updated, SecurityUtil.authUserId());
        assertThat(service.get(updated.getId(), SecurityUtil.authUserId())).isEqualToComparingFieldByField(updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien(){
        Meal updated = new Meal(MEAL1);
        updated.setDescription("updated");
        updated.setCalories(200);
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, 10, 25, 10, 57), "newMeal", 700);
        Meal created = service.create(newMeal, SecurityUtil.authUserId());
        newMeal.setId(created.getId());
        assertThat(created).isEqualToComparingFieldByField(newMeal);
    }
}