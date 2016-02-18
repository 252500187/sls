package com.sls.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static final String yyyy = "yyyy";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    //文件夹命名时用到的
    public static final String yyyyMMDDHHSS = "yyyyMMDDHHSS";
    public static final String yyyyMMddhhmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd_hh_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String defaultDate = "2014-05-01 00:00:00";

    private DateUtil() {
    }

    /**
     * SCORM 用于计算两个时间和
     * 0000：00：00.00
     * 时：分：秒.毫秒
     */
    public static String getTotalTime(String sessionTime, String totalTime) {
        if (("").equals(totalTime.trim())) {
            return sessionTime;
        }
        int[] sessionTimes = splitScormTime(sessionTime);
        int[] totalTimes = splitScormTime(totalTime);
        return calScormTimeSum(sessionTimes, totalTimes);
    }

    public static int[] splitScormTime(String time) {
        int[] times = new int[4];
        if (time.equals("")) {
            times[0] = 0;
            times[1] = 0;
            times[2] = 0;
            times[3] = 0;
            return times;
        }
        String[] secTimes = time.split(":");
        String[] tsTime = secTimes[2].split("\\.");
        //时
        times[0] = Integer.parseInt(secTimes[0]);
        //分
        times[1] = Integer.parseInt(secTimes[1]);
        //秒
        times[2] = Integer.parseInt(tsTime[0]);
        //毫秒
        if (tsTime.length == 1) {
            times[3] = 0;
        } else {
            times[3] = Integer.parseInt(tsTime[1]);
        }
        return times;
    }

    public static String calScormTimeSum(int[] oneTime, int[] timeTwo) {
        int sumTime[] = new int[4];
        sumTime[0] = oneTime[0] + timeTwo[0];
        sumTime[1] = oneTime[1] + timeTwo[1];
        sumTime[2] = oneTime[2] + timeTwo[2];
        sumTime[3] = oneTime[3] + timeTwo[3];
        if (sumTime[3] / 1000 > 0) {
            sumTime[2]++;
            sumTime[3] = sumTime[3] % 1000;
        }
        if (sumTime[2] / 60 > 0) {
            sumTime[1]++;
            sumTime[2] = sumTime[2] % 60;
        }
        if (sumTime[1] / 60 > 0) {
            sumTime[0]++;
            sumTime[1] = sumTime[1] % 60;
        }
        String totalTime;
        if (sumTime[0] < 10) {
            totalTime = "0" + sumTime[0] + ":";
        } else {
            totalTime = sumTime[0] + ":";
        }
        if (sumTime[1] < 10) {
            totalTime += "0" + sumTime[1] + ":";
        } else {
            totalTime += sumTime[1] + ":";
        }
        if (sumTime[2] < 10) {
            totalTime += "0" + sumTime[2] + ":";
        } else {
            totalTime += sumTime[2] + ":";
        }
        if (sumTime[3] < 10) {
            totalTime += "0" + sumTime[3];
        } else {
            totalTime += sumTime[3];
        }
        return totalTime;
    }

    /**
     * 获取当前系统时间
     * Param：时间格式
     */
    public static String getSystemDate(String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return sdf.format(new Date()).toString();
    }

    /**
     * 获取当前系统时间时间戳
     * Param：时间格式
     */
    public static Timestamp getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(new Date());
        return Timestamp.valueOf(nowTime);
    }

    /**
     * ------------自定义时间格式化-----------------------------
     * DateUtil.convertStringToDate(str,"yyyyMMdd")；
     */

    public static Date convertStringToDate(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String convertDateToString(java.sql.Timestamp date, String format) {
        if (date == null) {
            return "";
        }
        Date jdate = new Date(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(jdate);
    }

    //---------------比较两个时间-----------------------
    public static boolean compareTime(String date1, String date2) {
        boolean flag = false;
        if (covertTimeToLong(date1) <= covertTimeToLong(date2)) {
            flag = false;
        } else if (covertTimeToLong(date1) > covertTimeToLong(date2)) {
            flag = true;
        }
        return flag;
    }

    private static long covertTimeToLong(String time) {
        long timeLong = 0l;
        if (time != null && !"".equals(time)) {
            int yy = Integer.parseInt(time.substring(0, 4));
            int mm = Integer.parseInt(time.substring(5, 7));
            int dd = Integer.parseInt(time.substring(8, 10));
            int hh = Integer.parseInt(time.substring(11, 13));
            int mi = Integer.parseInt(time.substring(14, 16));
            int ss = Integer.parseInt(time.substring(17, 19));
            GregorianCalendar gc = new GregorianCalendar(yy, mm, dd, hh, mi, ss);
            Date d = gc.getTime();
            timeLong = d.getTime();
        } else {
            timeLong = Long.MAX_VALUE;
        }
        return timeLong;
    }

    /**
     * <p>判断传入的日期是否为传入日期月份的最后一天</p>
     *
     * @param dateStr 日期字符串
     * @return boolean true--是最后一天 false--不是最后一天
     * @author Fanqi
     */
    public static boolean checkDate(String dateStr) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyy_MM_dd);
        try {
            //转换参数为日期类型的
            if ("".equals(dateStr) || dateStr == null) {
                cal.setTime(sdf.parse(defaultDate));
            } else {
                cal.setTime(sdf.parse(dateStr));
            }
            //获取月份的最后一天
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            //获取参数是哪一天
            int dateStrMonth = cal.get(cal.DAY_OF_MONTH);
            return lastDay == dateStrMonth ? true : false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * <p>获取某月的最后一天</p>
     *
     * @param date 参数日期
     * @return
     * @author Fanqi
     */
    public static String getLastDay(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyy_MM);
        String lastDay = "";
        try {
            //转换参数为日期类型的
            if ("".equals(date) || date == null) {
                cal.setTime(sdf.parse(defaultDate));
            } else {
                cal.setTime(sdf.parse(date));
            }
            lastDay = String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lastDay;
        }
    }

    /**
     * <p>根据实际需要对传入的日期参数月部分进行增减</p>
     *
     * @param date 日期参数
     * @param temp "1"--传入的参数月份+1,"-1"--传入的参数月份-1
     * @return 处理后的日期   返回月份
     * @author Fanqi
     */
    public static String getMonth(String date, int temp) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat(DateUtil.yyyy_MM);
        try {
            //转换参数为日期类型的
            if ("".equals(date) || date == null) {
                cal.setTime(dateformat.parse(defaultDate));
            } else {
                cal.setTime(dateformat.parse(date));
            }
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1 + temp);
            String month = String.valueOf(cal.get(Calendar.MONTH));
            return month.trim().equals("0") ? "12" : month.length() == 1 ? "0" + month : month;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <p>根据实际需要对传入的日期参数月部分进行增减</p>
     *
     * @param date 日期参数
     * @param temp "1"--传入的参数月份+1,"-1"--传入的参数月份-1
     * @return 处理后的日期
     * @author Fanqi
     */
    public static String getDateDealWithMonth(String date, int temp) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat(DateUtil.yyyy_MM);
        try {
            //转换参数为日期类型的
            if ("".equals(date) || date == null) {
                cal.setTime(dateformat.parse(defaultDate));
            } else {
                cal.setTime(dateformat.parse(date));
            }
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + temp);
            return dateformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <p>根据实际需要对传入的日期参数年部分进行增减</p>
     *
     * @param date 日期参数
     * @param temp "1"--传入的参数月份+1,"-1"--传入的参数月份-1
     * @return 处理后的日期
     * @author Fanqi
     */
    public static String getYearByNeed(Date date, int temp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + temp);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    /**
     * <p>根据实际需要对传入的日期参数天数进行增减</p>
     *
     * @param date 日期参数
     * @param num  "1"--传入的参数天数+1,"-1"--传入的参数天数-1
     * @return 处理后的日期
     * @author Fanqi
     */
    public static String getDateDealWithDay(String date, String num) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyy_MM_dd);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(num));
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * <p>获取两个时间的差</p>
     *
     * @param /String startTime 开始时间 yyyy-MM-dd HH:mm:ss
     * @param /String endTime	结束时间 yyyy-MM-dd HH:mm:ss
     * @return 相差的小时数
     * @author Fanqi
     */
    public static double getTimeDifference(String startTime, String endTime) {
        // 格式化时间
        SimpleDateFormat d = new SimpleDateFormat(DateUtil.yyyy_MM_dd_HH_mm);
        double result = 0.0;
        try {
            result = ((double) d.parse(endTime).getTime() - (double) d.parse(startTime)
                    .getTime()) / (double) 3600000;
            result = (double) (Math.round(result * 100)) / 100;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
        return result;
    }

    /**
     * <p>获取两个时间的差</p>
     *
     * @param /String startTime 开始时间 yyyy-MM-dd HH:mm:ss
     * @param /String endTime	结束时间 yyyy-MM-dd HH:mm:ss
     * @return 相差的天数
     * @author Fanqi
     */
    public static double getTimeDifferenceDay(String startTime, String endTime) {
        // 格式化时间
        SimpleDateFormat d = new SimpleDateFormat(DateUtil.yyyy_MM_dd);
        double result = 0.0;
        try {
            result = ((double) d.parse(endTime).getTime() - (double) d.parse(startTime)
                    .getTime()) / (double) 3600000;
            result = (double) (Math.round(result * 100)) / 100 / 24;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
        return result;
    }

    public static String computeDateInterval(String startTime, String endTime, String timeFormat) {
        if (null == startTime || "".equals(startTime) || startTime.length() == 0) {
            return "";
        }
        if (null == endTime || "".equals(endTime) || endTime.length() == 0) {
            return "";
        }
        // 格式化时间
        SimpleDateFormat d = new SimpleDateFormat(timeFormat);
        long time = 0;
        try {
            time = d.parse(endTime).getTime() - d.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        //将毫秒转化为秒
        time = time / 1000;
        if (0 == time) {
            return "0分";
        }
        long second = time % 60;
        time = time / 60;
        long minute = time % 60;
        time = time / 60;
        long hour = time % 24;
        time = time / 24;
        long day = time;
        String result = " ";
        if (0 != day) {
            result += day + "天";
        }
        if (0 != hour) {
            result += hour + "小时";
        }
        if (0 != minute) {
            result += minute + "分";
        }
        if (0 != second) {
            result += second + "秒";
        }
        return result;
    }

    //    获取上月的第一天和最后一天
    public static Map<String, String> findLastMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String dayFirstPrevM = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(dayFirstPrevM).append(" 00:00:00");
        dayFirstPrevM = str.toString();

        calendar.add(cal.MONTH, 1);
        calendar.set(cal.DATE, 1);
        calendar.add(cal.DATE, -1);
        String dayEndPrevM = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(dayEndPrevM).append(" 23:59:59");
        dayEndPrevM = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("prevMonthFD", dayFirstPrevM);
        map.put("prevMonthPD", dayEndPrevM);
        return map;
    }

    public static String getLastDay() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //此时打印它获取的是系统当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date theDate = calendar.getTime();
        return df.format(theDate);
    }
}
