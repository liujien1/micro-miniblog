/**
 *
 */
package website.liujie.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Description :日期工具类
 * @Copyright : Excenon. ALL Rights Reserved
 * @Company : jie
 * @Author : liujie
 * @Version : 1.0
 * @Date : 2015年11月27日 下午8:00:17
 */
public class DateUtil {

    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 获取某天的该周第一天（date==null,以本周为起点）
     *
     * @param n 后几周
     * @return
     */
    public static Date getFirstDayOfNWeek(Date date, int n) {
        Calendar calendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);// 取当前时间
        if (date != null) calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Monday is 2, so need to -1.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, 7 * n - dayOfWeek + 1);
        return calendar.getTime();
    }

    /**
     * 获取某天的该周最后一天（date==null,以本周为起点）
     *
     * @param date 起点时间
     * @param n    后几周
     * @return
     */
    public static Date getLastDayOfNWeek(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Monday is 2, so need to -1.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, 7 * (n + 1) - dayOfWeek);
        return calendar.getTime();
    }

    /**
     * 获取指定日期年份
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 获取指定日期月份
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.valueOf(month);
    }

    /**
     * 获取当前月最后一天日期
     *
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());

    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date strToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String DateToStr(Date date, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getDifferDayWithTwoDate(Date source, Date target) {
        if (null == source || null == target) {
            return -1;
        }
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(source);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(target);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    @SuppressWarnings("deprecation")
    public static boolean isTargetGtSourceDate(Date source, Date target) {
        if (null == source || null == target) {
            return false;
        }
        source.setHours(23);
        source.setMinutes(59);
        source.setSeconds(59);

        long sourceTimes = source.getTime();
        long targetTimes = target.getTime();

        return targetTimes > sourceTimes;
    }

    public static boolean isTargetGtSourceDateNo235959(Date source, Date target) {
        if (null == source || null == target) {
            return false;
        }

        long sourceTimes = source.getTime();
        long targetTimes = target.getTime();

        return targetTimes > sourceTimes;
    }

    public static long getDifferTimeMillisTwoDate(Date source, Date target) {
        if (null == source || null == target) {
            return 0;
        }
        long s = source.getTime();
        long d = target.getTime();
        return d - s;
    }

    public static int getDifferHoursWithTwoDate(Date source, Date target) {
        if (null == source || null == target) {
            return -1;
        }
        Calendar from = Calendar.getInstance();
        from.setTime(source);
        Calendar to = Calendar.getInstance();
        to.setTime(target);
        int hours = (int) Math.abs((to.getTime().getTime() - from.getTime().getTime())
                / (60 * 60 * 1000));

        return hours;
    }

    /**
     * 获取现在时间
     *
     * @return
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取日期 +hours 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
     * 2015-01-12 14:08:09, 8  ==> 2015-01-12 22:08:09
     * 2015-01-12 14:08:09, -8 ==> 2015-01-12 06:08:09
     *
     * @param hours
     */
    public static Date getDate(Date source, int hours) {
        if (source == null) {
            source = getNowDate();
        }
        if (hours == 0) {
            return source;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 获取日期 +month 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-02-12 14:08:09
     *
     * @param source
     * @param month
     * @return Date
     */
    public static Date getDateMoveByMonth(Date source, int month) {
        if (source == null) {
            source = getNowDate();
        }
        if (month == 0) {
            return source;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取日期 +day 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
     * 2015-01-12 14:08:09, 8  ==> 2015-01-20 14:08:09
     * 2015-01-12 14:08:09, -8 ==> 2015-01-04 14:08:09
     *
     * @param source
     * @param day
     * @return Date
     */
    public static Date getDateMoveByDay(Date source, int day) {
        if (source == null) {
            source = getNowDate();
        }
        if (day == 0) {
            return source;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 获取日期 +hours 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
     * 2015-01-12 14:08:09, 8  ==> 2015-01-12 22:08:09
     * 2015-01-12 14:08:09, -8 ==> 2015-01-12 06:08:09
     *
     * @param source
     * @param hours
     * @return Date
     */
    public static Date getDateMoveByHour(Date source, int hours) {
        return getDate(source, hours);
    }

    /**
     * 获取日期 +Minutes 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
     * 2015-01-12 14:08:09, 8  ==> 2015-01-12 14:16:09
     * 2015-01-12 14:08:09, -8 ==> 2015-01-12 14:00:09
     *
     * @param source
     * @param minutes
     * @return Date
     */
    public static Date getDateMoveByMinute(Date source, int minutes) {
        if (source == null) {
            source = getNowDate();
        }
        if (minutes == 0) {
            return source;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取日期 +Second 的日期
     * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
     * 2015-01-12 14:08:09, 8  ==> 2015-01-12 14:08:17
     * 2015-01-12 14:08:09, -8 ==> 2015-01-12 14:00:01
     *
     * @param source
     * @param second
     * @return Date
     */
    public static Date getDateMoveBySecond(Date source, int second) {
        if (source == null) {
            source = getNowDate();
        }
        if (second == 0) {
            return source;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 获取当前时间YYYY-MM-DD（2015-2-4 0:00:00）
     *
     * @return
     */
    public static Date getTodayDateOfYMD() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获取指定年月日期的间隔月份的年月
     *
     * @param date
     * @param monthNum
     * @return
     */
    public static String getMonthDate(String date, int monthNum) {
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化对象
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(DateUtil.strToDate(date, "yyyy-MM"));//设置当前日期
        calendar.add(Calendar.MONTH, +monthNum);//月份减一
        dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }

    /**
     * 根据指定格式的日期，返回该日期对应是几号
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) {
            w = 7;
        }
        return w;
    }

    public static void main(String[] args) {
        System.out.println(strToDate("2015-12-24", FORMAT_YYYY_MM_DD));
    }
}
