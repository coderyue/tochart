package com.lin.tochart.annotaiton;

import com.lin.tochart.common.ChartType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToChart {

    ChartType[] type() default {};
}
