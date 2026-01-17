package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static long diffDays(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }
}
