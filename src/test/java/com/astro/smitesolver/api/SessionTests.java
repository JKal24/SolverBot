package com.astro.smitesolver.api;

import com.astro.smitesolver.smite.repository.SessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SessionTests {

    @Autowired
    private SessionService sessionService;

    @MockBean
    SessionRepository sessionRepository;

    @Test
    public void testSignature1() {
        String request = "createsession";
        String time = "20120927183145";
        String devID = "1004";
        String authKey = "23DF3C7E9BD14D84BF892AD206B6755C";

        // From API docs

        String result = "0abd990b4ca9f86817e087ad68\n" +
                "4515db";

        assertEquals(result, SessionUtils.makeSignature(request, time, devID, authKey));

    }

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
