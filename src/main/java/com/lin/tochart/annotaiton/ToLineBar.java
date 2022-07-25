package com.lin.tochart.annotaiton;

import com.lin.tochart.common.ChartType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ToChart(type = {ChartType.LINE, ChartType.BAR})
public @interface ToLineBar {
}
