package com.lin.tochart.util;

import com.lin.tochart.common.StaticUtil;

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
public interface LineBarUtil {

    /**
     * 返回折线图结构
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
        resultMap.put(StaticUtil.X_DATA, xData);
        resultMap.put(StaticUtil.Y_DATA, yData);
        return resultMap;
    }

    /**
     * 多折线
     *
     * @param mapList
     * @param x
     * @param y
     * @param value   值
     * @return
     */
    default Map<String, Object> toMultiLineOrBar(List<Map<String, Object>> mapList, String startTime, String endTime, ChronoUnit addUnit,
                                            String x, String y, String value) {
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        List<String> xData = new ArrayList<>(mapList.size() << 1);
        Map<Object, Map<Object, Double>> collect = mapList.stream().collect(Collectors.groupingBy(item -> item.get(y),
                Collectors.groupingBy(item -> item.get(x),
                        Collectors.summingDouble(item -> Double.parseDouble(item.get(value).toString())))));
        List<String> nameList = mapList.stream().map(item -> ((String) item.get(y))).distinct().collect(Collectors.toList());

        nameList.forEach(name -> {
            Map<String, Object> tempMap = new HashMap<>();
            dataMapList.add(tempMap);
            tempMap.putIfAbsent(StaticUtil.NAME, name);
            List<Object> data = new ArrayList<>();
            tempMap.putIfAbsent(StaticUtil.DATA, data);
            xData.forEach(xx -> {
                Double v = collect.get(y).get(xx);
                data.add(v == null ? 0d : v);
            });
        });

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("series", dataMapList);
        resultMap.put("legend", new HashMap<String, Object>() {{this.put("data", nameList);}});
        resultMap.put("xAxis", new HashMap<String, Object>() {{this.put("data", xData);}});
        return resultMap;
    }

}
