package springBootSecurity.com.example.springBootSecurity.component;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateHelper {

  /**
   * Get current datetime
   *
   * @param pattern - String format (YYYY-MM-dd HH:mm:ss)
   * @return String
   */
  public static String currentDate(String pattern) {
    Calendar cal = Calendar.getInstance();
    Date dateNow = cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    return dateFormat.format(dateNow);
  }
}
