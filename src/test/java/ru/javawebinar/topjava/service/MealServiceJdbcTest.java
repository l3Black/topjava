package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"postgres", "jdbc"})
public class MealServiceJdbcTest extends MealServiceTest {
}
