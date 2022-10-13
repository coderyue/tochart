package com.lin.tochart.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 静态方法
 * @author lin
 * @date   2022/10/8
 **/
public interface StaticMethod extends StaticValue {

    /**
     * 返回对应的chronoUnit
     * @param x_add_unit
     * @return
     */
    default ChronoUnit getAddUnit(String x_add_unit) {
        x_add_unit = x_add_unit.trim();
        if ("day".equals(x_add_unit)) {
            return ChronoUnit.DAYS;
        }
        if ("hour".equals(x_add_unit)) {
            return ChronoUnit.HOURS;
        }
        if ("month".equals(x_add_unit)) {
            return ChronoUnit.MONTHS;
        }
        if ("year".equals(x_add_unit)) {
            return ChronoUnit.YEARS;
        }
        return ChronoUnit.DAYS;
    }

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
     * @param addUnit 步长单位
     */
    default void generatorDate(List<String> xData, String startTime, String endTime,
                               DateTimeFormatter toFormatter, ChronoUnit addUnit) {
        generatorDate(xData, startTime, endTime, toFormatter, 1, addUnit);
    }

    /**
     * 生成横轴坐标日期集合
     * @param xData
     * @param startTime
     * @param endTime
     * @param toFormatter
     * @param addSize
     * @param addUnit
     */
    default void generatorDate(List<String> xData, String startTime,
                               String endTime, DateTimeFormatter toFormatter,
                               long addSize, ChronoUnit addUnit) {
        LocalDateTime start;
        LocalDateTime end;
        if (startTime.length() == 10) {
            start = LocalDate.parse(startTime, getFormatter(StaticValue.DATE_FORMAT)).atStartOfDay();
            end = LocalDate.parse(endTime, getFormatter(StaticValue.DATE_FORMAT)).atStartOfDay();
        } else {
            start = LocalDateTime.parse(startTime, getFormatter(StaticValue.DATE_TIME_FORMAT));
            end = LocalDateTime.parse(endTime, getFormatter(StaticValue.DATE_TIME_FORMAT));
        }
        while (start.isBefore(end)) {
            xData.add(start.format(toFormatter));
            start = start.plus(addSize, addUnit);
        }
    }
}
