package com.lin.tochart.common;

/**
 * 静态变量
 *
 * @author lin
 * @date 2022/7/24
 **/
public interface StaticValue {

    //
    //  日期格式化常量
    //
    String YEAR_FORMAT = "yyyy";

    String YEAR_MONTH_FORMAT = "yyyy-MM";

    String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";

    String YEAR_MONTH_DAY_HOUR_FORMAT = "yyyy-MM-dd HH";

    String DATE_FORMAT = "yyyy-MM-dd";

    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 一些情况下， null替换为未知
     */
    String unKnow = "未知";

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

    //
    //  饼图常量
    //

    String SOURCE = "source";

    String TARGET = "target";

    String LINKS = "links";

    String NODES = "nodes";

    // 关系图常量

    String ID = "id";

    String CATEGORIES = "categories";

    String CATEGORY = "category";

    String SYMBOL_SIZE = "symbolSize";


}
