package com.lin.tochart.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 静态变量及方法
 *
 * @author lin
 * @date 2022/7/24
 **/
public interface StaticUtil {

    //
    //  日期格式化常量
    //
    String YEAR_FORMAT = "yyyy";

    String YEAR_MONTH_FORMAT = "yyyy-MM";

    String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";

    String YEAR_MONTH_DAY_HOUR_FORMAT = "yyyy-MM-dd HH";

    String DATE_FORMAT = "yyyy-MM-dd";

    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //
    //  饼图,柱状图,常量
    //

    String X_DATA = "xData";

    String Y_DATA = "yData";

    String DATA = "data";

    //
    //  饼图常量
    //
    String NAME = "name";

    String VALUE = "value";

    //
    //  饼图常量
    //

    String CHILDREN = "children";

    /**
     * 返回指定格式的DateTimeFormatter
     * @param pattern
     * @return
     */
    default DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 生成横轴坐标，可供日期补全用, 不包括最后日期
     * @param xData
     * @param startTime 开始时间 这里需要到天， 或者到秒， 只接受这两种格式
     * @param endTime 结束时间
     * @param toFormatter 要格式化成为的格式
     */
    default void generatorDate(List<String> xData, String startTime,
                               String endTime, DateTimeFormatter toFormatter, ChronoUnit addUnit) {
        generatorDate(xData, startTime, endTime, toFormatter, 1, addUnit);
    }

    default void generatorDate(List<String> xData, String startTime,
                               String endTime, DateTimeFormatter toFormatter,
                               long addSize, ChronoUnit addUnit) {
        LocalDateTime start;
        LocalDateTime end;
        if (startTime.length() == 10) {
            start = LocalDate.parse(startTime, getFormatter(DATE_FORMAT)).atStartOfDay();
            end = LocalDate.parse(endTime, getFormatter(DATE_FORMAT)).atStartOfDay();
        } else {
            start = LocalDateTime.parse(startTime, getFormatter(DATE_TIME_FORMAT));
            end = LocalDateTime.parse(endTime, getFormatter(DATE_TIME_FORMAT));
        }
        while (start.isBefore(end)) {
            xData.add(start.format(toFormatter));
            start = start.plus(addSize, addUnit);
        }
    }

}
