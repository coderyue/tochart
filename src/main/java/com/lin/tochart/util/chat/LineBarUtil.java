package com.lin.tochart.util.chat;

import com.lin.tochart.common.StaticMethod;
import com.lin.tochart.common.StaticValue;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 折线图或柱状图工具类
 * @author lin
 * @date   2022/7/24
 **/
public interface LineBarUtil extends StaticMethod {

    /**
     * 返回折线图/柱状图结构
     * @param mapList
     * @param x
     * @param y
     * @return
     */
    default Map<String, Object> toLineOrBar(List<Map<String, Object>> mapList, String x, String y) {
        List<Object> xData = new ArrayList<>(mapList.size());
        List<Object> yData = new ArrayList<>(mapList.size());
        mapList.forEach(item -> {
            xData.add(item.get(x));
            yData.add(item.get(y));
        });
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(StaticValue.X_DATA, xData);
        resultMap.put(StaticValue.Y_DATA, yData);
        return resultMap;
    }

    /**
     * 返回折线图/柱状图结构
     * x轴补充时间，y轴不进行补充
     *
     * @param mapList
     * @param startTime
     * @param endTime
     * @param toFormatter
     * @param addUnit
     * @param x
     * @param y
     * @return
     */
    default Map<String, Object> toLineOrBar(List<Map<String, Object>> mapList, String startTime, String endTime,
                                            String toFormatter, ChronoUnit addUnit, String x, String y) {
        List<String> xData = new ArrayList<>(mapList.size());
        generatorDate(xData, startTime, endTime, DateTimeFormatter.ofPattern(toFormatter), addUnit);
        List<Object> yData = new ArrayList<>(mapList.size());

        Map<String, Object> xyMap = mapList.stream().collect(Collectors.toMap(item -> (String) item.get(x), item -> item.get(y)));

        xData.forEach(item -> yData.add(xyMap.get(item)));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(StaticValue.X_DATA, xData);
        resultMap.put(StaticValue.Y_DATA, yData);
        return resultMap;
    }

    /**
     * 多折线图/柱状图， 横纵坐标都不为时间
     * @param mapList
     * @param x
     * @param y
     * @param value
     * @return
     */
    default Map<String, Object> toMultiLineOrBar(List<Map<String, Object>> mapList, String x, String y, String value) {
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<String> xData = mapList.stream().map(item -> item.get(x).toString()).distinct().collect(Collectors.toList());
        return getMultiLineOrBar(mapList, x, y, value, dataMapList, xData);
    }

    default Map<String, Object> getMultiLineOrBar(List<Map<String, Object>> mapList, String x, String y, String value,
                                                  List<Map<String, Object>> dataMapList, List<String> xData) {
        Map<Object, Map<Object, Double>> collect = mapList.stream().collect(Collectors.groupingBy(item -> item.get(y),
                Collectors.groupingBy(item -> item.get(x),
                        Collectors.summingDouble(item -> Double.parseDouble(item.get(value).toString())))));
        List<String> nameList = mapList.stream().map(item -> ((String) item.get(y))).distinct().collect(Collectors.toList());
        nameList.forEach(name -> {
            Map<String, Object> tempMap = new HashMap<>();
            dataMapList.add(tempMap);
            tempMap.putIfAbsent(StaticValue.NAME, name);
            List<Object> data = new ArrayList<>();
            tempMap.putIfAbsent(StaticValue.DATA, data);
            xData.forEach(xx -> {
                // 这里没有的数据不进行补0，就是null
                Double v = collect.get(name).get(xx);
                data.add(v);
            });
        });

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(X_DATA, xData);
        resultMap.put(Y_DATA, dataMapList);
        resultMap.put(NAME, nameList);
        return resultMap;
    }

    /**
     * 多折线/多柱状图
     * 横轴为时间， 可以对时间进行补齐
     *
     * @param mapList
     * @param startTime
     * @param endTime
     * @param toFormatter
     * @param addUnit
     * @param x
     * @param y
     * @param value   值
     * @return
     */
    default Map<String, Object> toMultiLineOrBar(List<Map<String, Object>> mapList, String startTime, String endTime,
                                                 String toFormatter, ChronoUnit addUnit,
                                                 String x, String y, String value) {
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<String> xData = new ArrayList<>(mapList.size() << 1);
        generatorDate(xData, startTime, endTime, DateTimeFormatter.ofPattern(toFormatter), addUnit);
        return getMultiLineOrBar(mapList, x, y, value, dataMapList, xData);
    }

}
