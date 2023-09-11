package com.astro.smiteapi.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionUtils {

    private static final Logger LOGGER = Logger.getLogger(SessionUtils.class.getName());

    public static String makeSignature(String request, String time, String devID, String authKey) {
        try {
            String sig = devID + request + authKey + time;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((sig).getBytes());

            byte[] signature = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte x : signature) {
                sb.append(String.format("%02X", x).toLowerCase());
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String makeRequestUri(String... components) {
        return String.join("/", components);
    }

    public static boolean verifySession(String pastDate, String pastTime) {
        String[] currentTimeStamp = SessionUtils.makeSignatureTimeStamp().split(" ");
        if (compareDate(currentTimeStamp[0], pastDate)) {
            return compareTime(currentTimeStamp[1], pastTime, 15);
        }
        return false;
    }

    // Custom timestamps used for API requests

    public static String makeAPITimeStamp() {
        return makeTimeStamp("yyyyMMddHHmmss");
    }

    public static String makeAPIDate() {
        return makeTimeStamp("yyyyMMdd");
    }

    public static String makeAPIDate(LocalDate date) {
        return makeTimeStamp(date, "yyyyMMdd");
    }

    public static String makeSignatureTimeStamp() {
        return makeTimeStamp("MM/dd/yyyy HH:mm:ss:a");
    }

    public static String makeRecordTimeStamp() { return makeTimeStamp("dd/MM/yy HH:mm:ss:a"); }

    public static String makeCustomTimeStamp(Instant instant, String format) {
        return makeTimeStamp(instant, format);
    }

    private static String makeTimeStamp(String format) {
        Instant instant = Instant.now();
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("UTC"));
        return formatterUTC.format(instant);
    }

    private static String makeTimeStamp(Instant instant, String format) {
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("UTC"));
        return formatterUTC.format(instant);
    }

    private static String makeTimeStamp(LocalDate date, String format) {
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("UTC"));
        return date.format(formatterUTC);
    }

    // mm/dd/yyyy

    public static Boolean compareDate(String currentDate, String pastDate) {
        String[] currentDateArr = currentDate.split("/");
        int currentMonth = getDateAttribute(currentDateArr[0]);
        int currentDay = getDateAttribute(currentDateArr[1]);
        int currentYear = getDateAttribute(currentDateArr[2]);

        String[] pastDateArr = pastDate.split("/");
        int pastMonth = getDateAttribute(pastDateArr[0]);
        int pastDay = getDateAttribute(pastDateArr[1]);
        int pastYear = getDateAttribute(pastDateArr[2]);

        return pastMonth == currentMonth && pastYear == currentYear && pastDay == currentDay;
    }

    private static int getDateAttribute(String val) {
        return Integer.parseInt(val.replaceFirst("^0+(?!$)", ""));
    }

    public static LocalDate subtractDays(String recordedYear, String recordedMonth, String recordedDay, int difference) {
        LocalDate date = LocalDate.of(Integer.parseInt(recordedYear), Integer.parseInt(recordedMonth), Integer.parseInt(recordedDay));
        return date.minusDays(difference);
    }

    public static Boolean compareTime(String currentTime, String pastTime, int timeBetween) {
        String[] currentTimeArr = sliceTimeArr(currentTime);
        String[] pastTimeArr = sliceTimeArr(pastTime);

        // Check if the last session request sent was less than 'timeBetween' minutes ago

        int compareHours = customTimeTrim(pastTimeArr[0]);
        int compareMinutes = customTimeTrim(pastTimeArr[1]);
        int compareSeconds = customTimeTrim(pastTimeArr[2]);
        String compareAMorPM = customAMOrPM(pastTimeArr[3]);

        if (compareHours >= 12) {
            compareAMorPM = customAMOrPM("PM");
            compareHours %= 12;
        }

        if (compareMinutes < (60 - timeBetween)) {
            compareMinutes += timeBetween;
        } else {
            compareMinutes = (compareMinutes + timeBetween) % 60;
            compareHours++;
        }

        int currentHours = customTimeTrim(currentTimeArr[0]);
        int currentMinutes = customTimeTrim(currentTimeArr[1]);
        int currentSeconds = customTimeTrim(currentTimeArr[2]);
        String currentAMorPM = customAMOrPM(currentTimeArr[3]);

        if (currentHours >= 12) {
            currentAMorPM = customAMOrPM("PM");
            currentHours %= 12;
        }

        // Compares hours, must be equal after changes and AM or PM
        if (compareHours == currentHours && compareAMorPM.equals(currentAMorPM)) {

            // Evaluate the amount of minutes left before expiring
            if (compareMinutes > currentMinutes) {

                return true;
            } else if (compareMinutes == currentMinutes) {

                // Evaluate the amount of seconds left before expiring, must have at least 5 seconds to spare
                compareSeconds = compareSeconds > 55 ? -1 : compareSeconds + 5;
                return compareSeconds > currentSeconds;
            }
        }

        return compareHours > currentHours && currentAMorPM.equals(compareAMorPM);
    }

    public static Boolean compareTime(String currentTime, String pastTime) {
        return SessionUtils.compareTime(currentTime, pastTime, 15);
    }

    private static String[] sliceTimeArr(String time) {
        return time.replaceAll(" ", ":").split(":");
    }

    private static String customAMOrPM(String time) {
        return time.replaceAll("\\.", "").toUpperCase();
    }

    private static Integer customTimeTrim(String timeElement) {
        return Integer.parseInt(timeElement.replaceFirst("^0+(?!$)", ""));
    }

    // Will parse JSON data, regardless of whether it is a JSON array or object
    public static <T> T parseJSONData(Class<T> responseType, String data) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return mapper.readValue(data, responseType);
        } catch (JsonParseException e) {
            LOGGER.log(Level.WARNING, "Encountered a parsing error");
        } catch (JsonProcessingException e1) {
            LOGGER.log(Level.WARNING, "Encountered a processing error");
        }
        if (responseType.isArray()) {
            Class<?> componentType = responseType.getComponentType();
            return (T) Array.newInstance(componentType, 0);
        }
        return responseType.getDeclaredConstructor().newInstance();
    }

}
