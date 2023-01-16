package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateMethods {


    public static String getDate(String formatInput){
        //SimpleDateFormat dateFormat = new SimpleDateFormat(formatInput);
        Date date = new Date();

        SimpleDateFormat bstFormat = new SimpleDateFormat(formatInput);

        TimeZone estTime = TimeZone.getTimeZone("America/New_York");

        bstFormat.setTimeZone(estTime);

        String printDate = bstFormat.format(date);
        //System.out.println(printDate);
        return printDate;
    }

    public static String getAFutureDate(String formatInput, int daysToIncrease){
        Date date = new Date();

        SimpleDateFormat bstFormat = new SimpleDateFormat(formatInput);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysToIncrease);
        Date printDate = cal.getTime();

        String futureDate = bstFormat.format(printDate);
        return futureDate;
    }

    public static int compareTwoDates(String dateFromPayload, String dateFromResponse){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(dateFromPayload);
            Date date2 = sdf.parse(dateFromResponse);
            int result = (sdf.format(date1)).compareTo(sdf.format(date2));
            return result;
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }
    }
}
