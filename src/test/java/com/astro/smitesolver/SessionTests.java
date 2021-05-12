package com.astro.smitesolver;

import com.astro.smitesolver.api.SessionService;
import com.astro.smitesolver.api.SessionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class SessionTests {

    @Autowired
    private SessionService sessionService;

    @Test
    public void testCustomSession1() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 1, 1, 10, 45, 05);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 1, 1, 10, 59, 55);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession2() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 1, 1, 10, 48, 05);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 1, 1, 11, 01, 55);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession3() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 1, 1, 11, 01, 55);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession4() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 2, 1, 13, 01, 55);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession5() {
        LocalDateTime currentDateTime = LocalDateTime.of(2020, 1, 1, 13, 01, 55);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "PM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession6() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 1, 3, 17, 01, 55);
        LocalDateTime prevDateTime = LocalDateTime.of(2021, 2, 1, 13, 48, 05);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession7() {
        LocalDateTime currentDateTime = LocalDateTime.of(2021, 1, 1, 12, 01, 55);
        LocalDateTime prevDateTime = LocalDateTime.of(2019, 3, 2, 11, 48, 05);

        String date1 = currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + "/" + currentDateTime.getYear();
        String date2 = prevDateTime.getMonthValue() + "/" + prevDateTime.getDayOfMonth() + "/" + prevDateTime.getYear();

        String time1 = currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond() + " " + "AM";
        String time2 = prevDateTime.getHour() + ":" + prevDateTime.getMinute() + ":" + prevDateTime.getSecond() + " " + "PM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }
}
