package android.soding.com.sodingapp.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mohamed Abd-Allah on 9/30/2017.
 */

public class Utils {
    /**
     * Gets String representation of date
     * @param date Date to convert
     * @return String Value of date as represented in Constants.DATE_FORMAT
     */
    public static final String get_String_from_Date(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constants.DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Converts String as represented in Constants.DATE_FORMAT to Date object
     * @param date String to convert
     * @return Date representation of date string
     * @throws ParseException
     */
    public static final Date get_Date_from_String(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        return sdf.parse(date);
    }
}
