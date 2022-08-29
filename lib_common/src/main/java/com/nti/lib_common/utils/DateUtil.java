package com.nti.lib_common.utils;

/**
 * @author: weiqiyuan
 * @date: 2022/8/19
 * @describe
 */
public class DateUtil {

    /**
     * 判断参数的格式是否为“yyyyMMdd”格式的合法日期字符串
     *
     */
    public static boolean isValidDate(String str) {
        try {
            if (str != null && !str.equals("")) {
                if (str.length() == 6) {
                    // 闰年标志
                    boolean isLeapYear = false;
                    String year = "20"+str.substring(0, 2);
                    String month = str.substring(2, 4);
                    String day = str.substring(4, 6);
                    int vYear = Integer.parseInt(year);
                    // 判断年份是否合法
                    if (vYear < 1900 || vYear > 2200) {
                        return false;
                    }
                    // 判断是否为闰年
                    if (vYear % 4 == 0 && vYear % 100 != 0 || vYear % 400 == 0) {
                        isLeapYear = true;
                    }
                    // 判断月份
                    // 1.判断月份
                    if (month.startsWith("0")) {
                        String units4Month = month.substring(1, 2);
                        int vUnits4Month = Integer.parseInt(units4Month);
                        if (vUnits4Month == 0) {
                            return false;
                        }
                        if (vUnits4Month == 2) {
                            // 获取2月的天数
                            int vDays4February = Integer.parseInt(day);
                            if (isLeapYear) {
                                if (vDays4February > 29)
                                    return false;
                            } else {
                                if (vDays4February > 28)
                                    return false;
                            }

                        }
                    } else {
                        // 2.判断非0打头的月份是否合法
                        int vMonth = Integer.parseInt(month);
                        if (vMonth != 10 && vMonth != 11 && vMonth != 12) {
                            return false;
                        }
                    }
                    // 判断日期
                    if (day.startsWith("0")) {
                        String units4Day = day.substring(1, 2);
                        int vUnits4Day = Integer.parseInt(units4Day);
                        if (vUnits4Day == 0) {
                            return false;
                        }
                    } else {
                        // 2.判断非0打头的日期是否合法
                        int vDay = Integer.parseInt(day);
                        if (vDay < 10 || vDay > 31) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

}
