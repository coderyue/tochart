package com.lin.tochart.common;

import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StaticUtilTest implements StaticUtil {

    @Test
    public void generatorDateTest() {
        List<String> xData = new ArrayList<>();
        String startTime = "2022-01-01";
        String endTime = "2022-01-02";

        generatorDate(xData, startTime, endTime, getFormatter(YEAR_MONTH_DAY_HOUR_FORMAT), ChronoUnit.HOURS);

        System.out.println(xData);
    }


}
