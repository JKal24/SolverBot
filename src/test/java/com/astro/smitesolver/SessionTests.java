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
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 1, 10, 45, 05);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 10, 59, 55);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession2() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 1, 10, 48, 05);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 11, 01, 55);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession3() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 1, 11, 01, 55);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertTrue(SessionUtils.compareDate(date1, date2));
        Assertions.assertTrue(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession4() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 2, 1, 13, 01, 55);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession5() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 1, 1, 13, 01, 55);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 10, 48, 05);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "PM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession6() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 3, 17, 01, 55);
        LocalDateTime localDateTime2 = LocalDateTime.of(2021, 2, 1, 13, 48, 05);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "AM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }

    @Test
    public void testCustomSession7() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 1, 14, 01, 55);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, 3, 2, 13, 48, 05);

        String date1 = localDateTime1.getMonthValue() + "/" + localDateTime1.getDayOfMonth() + "/" + localDateTime1.getYear();
        String date2 = localDateTime2.getMonthValue() + "/" + localDateTime2.getDayOfMonth() + "/" + localDateTime2.getYear();

        String time1 = localDateTime1.getHour() + ":" + localDateTime1.getMinute() + ":" + localDateTime1.getSecond() + " " + "AM";
        String time2 = localDateTime2.getHour() + ":" + localDateTime2.getMinute() + ":" + localDateTime2.getSecond() + " " + "PM";

        Assertions.assertFalse(SessionUtils.compareDate(date1, date2));
        Assertions.assertFalse(SessionUtils.compareTime(time1, time2));
    }
}
