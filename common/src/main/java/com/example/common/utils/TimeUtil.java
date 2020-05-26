package com.example.common.utils;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.example.common.consts.CommonTimeConst;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期处理工具类
 */
public class TimeUtil {

  public static final String TIME_HM = String
      .format("%s:%s", CommonTimeConst.HOUR, CommonTimeConst.MINUTE);
  public static final String TIME_HMS = String
      .format("%s:%s:%s", CommonTimeConst.HOUR, CommonTimeConst.MINUTE, CommonTimeConst.SECOND);

  public static final String DATE_MD = String
      .format("%s-%s", CommonTimeConst.MONTH, CommonTimeConst.DAY);
  public static final String DATE_YMD = String
      .format("%s-%s-%s", CommonTimeConst.YEAR, CommonTimeConst.MONTH, CommonTimeConst.DAY);

  public static final String DATETIME_MDHM = String
      .format("%s-%s %s:%s", CommonTimeConst.MONTH, CommonTimeConst.DAY, CommonTimeConst.HOUR,
          CommonTimeConst.MINUTE);

  private static final ArrayMap<String, SimpleDateFormat> SDF_MAP = new ArrayMap<>();

  private TimeUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  public static SimpleDateFormat getSimpleDateFormat(String pattern) {
    SimpleDateFormat format = SDF_MAP.get(pattern);
    if (format == null) {
      format = new SimpleDateFormat(pattern, Locale.CHINESE);
      format.setTimeZone(getTimeZone());
      SDF_MAP.put(pattern, format);
    }
    return format;
  }

  /**
   * 当前时间
   */
  public static String getCurrTime(String pattern) {
    return getSimpleDateFormat(pattern).format(new Date());
  }

  /**
   * long to str
   */
  public static String long2Str(long time, String pattern) {
    return getSimpleDateFormat(pattern).format(new Date(time));
  }

  /**
   * str to long
   */
  public static long str2Long(String dateStr, String pattern) {
    if (TextUtils.isEmpty(dateStr)) {
      return 0;
    }
    return str2Date(dateStr, pattern).getTime();
  }

  /**
   * date to str
   */
  public static String date2Str(Date date, String pattern) {
    return getSimpleDateFormat(pattern).format(date);
  }

  /**
   * str to date
   */
  public static Date str2Date(String str, String pattern) {
    try {
      return getSimpleDateFormat(pattern).parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取年份
   */
  public static String getYear(String dateStr) {
    return date2Str(str2Date(dateStr, CommonTimeConst.YEAR), CommonTimeConst.YEAR);
  }

  /**
   * 获取月份
   */
  public static String getMonth(String dateStr) {
    return date2Str(str2Date(dateStr, CommonTimeConst.MONTH), CommonTimeConst.MONTH);
  }

  /**
   * 获取第几周
   */
  public static int getWeek(String dateStr, String pattern) {
    Date date = str2Date(dateStr, pattern);
    Calendar c = getCalendar();
    c.setTime(date);
    return c.get(Calendar.WEEK_OF_MONTH);
  }

  /**
   * 获取第几天
   */
  public static String getDay(String dateStr) {
    return date2Str(str2Date(dateStr, CommonTimeConst.DAY), CommonTimeConst.DAY);
  }

  /**
   * 当前月份有多少天
   */
  public static int getDaysInCrtMonth(int year, int month) {
    int mArray[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    // 判断闰年的情况 ，2月份有29天；
    if ((year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0))) {
      mArray[1] = 29;
    }
    return mArray[month - 1];
  }

  /**
   * 返回指定这个月的第几天
   */
  public static int getDayOfMonth(int year, int month, int weekOfMonth, int dayOfWeek) {
    // 在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。
    Calendar cal = getCalendar();
    // 不保留以前的设置
    cal.clear();
    // 将日期设置为本月的第一天。
    cal.set(year, month - 1, 1);
    cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekOfMonth);
    cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    // WEEK_OF_MONTH表示当天在本月的第几个周。不管1号是星期几，都表示在本月的第一个周
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * 取得给定日期加上一定天数后的日期对象.
   */
  public static Date dateAddDay(Date date, int amount) {
    Calendar cal = getCalendar();
    cal.setTime(date);
    cal.add(GregorianCalendar.DATE, amount);
    return cal.getTime();
  }

  /**
   * 返回当前时间加实数小时后的日期时间。
   */
  public static Date dateAddHour(float hour) {
    int addMinute = (int) (hour * 60);
    Calendar cal = getCalendar();
    cal.add(GregorianCalendar.MINUTE, addMinute);
    return cal.getTime();
  }

  /**
   * 计算月份差
   */
  public static int monthDiff(Date startDate, Date endDate) {
    Calendar startCalendar = getCalendar();
    startCalendar.setTime(startDate);
    Calendar endCalendar = getCalendar();
    endCalendar.setTime(endDate);

    int startYear = startCalendar.get(Calendar.YEAR);
    int startMonth = startCalendar.get(Calendar.MONTH);
    int endYear = endCalendar.get(Calendar.YEAR);
    int endMonth = endCalendar.get(Calendar.MONTH);

    int month = (endYear - startYear) * 12 + (endMonth - startMonth);
    return month;
  }

  /**
   * 计算天数差
   */
  public static long dayDiff(Date startDate, Date endDate) {
    long difference = endDate.getTime() - startDate.getTime();
    return difference / CommonTimeConst.D;
  }

  /**
   * 计算小时差
   */
  public static long hourDiff(Date startDate, Date endDate) {
    long difference = endDate.getTime() - startDate.getTime();
    return difference / CommonTimeConst.H;
  }

  /**
   * 获取相对时间
   */
  public static String getRelativeTimeString(long timestamp) {
    Calendar oldCalendar = getCalendar();
    oldCalendar.setTimeInMillis(timestamp);
    Calendar crtCalendar = getCalendar();
    long current = crtCalendar.getTimeInMillis();
    long diff = current - timestamp;
    if (diff < CommonTimeConst.M) {
      return "刚刚";
    } else if (diff < CommonTimeConst.H) {
      return String.format("%d 分钟前", (diff / CommonTimeConst.S)); // 显示:1分钟前 - 60 分钟前
    }
    if (oldCalendar.get(Calendar.YEAR) == crtCalendar.get(Calendar.YEAR)) {
      if (oldCalendar.get(Calendar.DAY_OF_YEAR) == crtCalendar.get(Calendar.DAY_OF_YEAR)) { // 当天
        return String
            .format("今天 %s", getSimpleDateFormat(TIME_HM).format(oldCalendar.getTime())); // 显示： 时分
      } else if (oldCalendar.get(Calendar.DAY_OF_YEAR) == (crtCalendar.get(Calendar.DAY_OF_YEAR)
          - 1)) {
        return String.format("昨天 %s",
            getSimpleDateFormat(TIME_HM).format(oldCalendar.getTime())); // 显示：昨天 时分
      } else {
        return getSimpleDateFormat(DATETIME_MDHM).format(oldCalendar.getTime()); // 显示：月日 时分
      }
    } else {
      return getSimpleDateFormat(DATE_YMD).format(oldCalendar.getTime()); // 显示：年月日
    }
  }

  private static TimeZone getTimeZone() {
    return TimeZone.getTimeZone("GMT+8");
  }

  private static Calendar getCalendar() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTimeZone(getTimeZone());
    return calendar;
  }
}