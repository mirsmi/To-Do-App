package Model;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Helper class to help format the date
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    //date format used for parsing and formatting
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Converts a string into a date object
     * @param text the date string to be parsed
     * @return a Date object representing the parsed date
     * @throws ParseException if the text cannot be parsed into a valid date
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parse(text);
    }

    /**
     * Converts a date object into a formatted string
     * @param value the value to be formatted, expected to be a Calendar object
     * @return a formatted date string, or an empty string if the value is null
     */
    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar calendar = (Calendar) value;
            return dateFormatter.format(calendar.getTime());
        }
        return "";
    }
}
