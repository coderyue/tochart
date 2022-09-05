package com.lin.tochart.annotaiton;

import com.lin.tochart.common.ChartType;
import com.lin.tochart.common.StaticValue;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@ToChart(type = ChartType.PIE)
public @interface ToPie {

    String name() default StaticValue.NAME;

    String value() default StaticValue.VALUE;
}
