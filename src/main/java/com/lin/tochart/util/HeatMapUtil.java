package com.lin.tochart.util;

import com.lin.tochart.common.StaticUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 热力图工具类
 * @author lin
 * @date   2022/7/24
 **/
public interface HeatMapUtil {

    /**
     * 获取热力图
     * @param mapList
     * @param x
     * @param y
     * @param value
     * @return
     */
    default Map<String, Object> toHeatMap(List<Map<String, Object>> mapList, String x, String y, String value) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = mapList.stream().map(item -> (String) item.get(x)).distinct().sorted().collect(Collectors.toList());
        List<String> yList = mapList.stream().map(item -> (String) item.get(y)).distinct().collect(Collectors.toList());
        Map<Object, Map<Object, Double>> collect = mapList.stream().collect(Collectors.groupingBy(item -> item.get(y),
                Collectors.groupingBy(item -> item.get(x), Collectors.summingDouble(item -> Double.parseDouble(item.get(value).toString())))));

        List<List<Object>> vList = new ArrayList<>(xList.size() * yList.size());
        for (int i = 0; i < yList.size(); i++) {
            for (int j = 0; j < xList.size(); j++) {
                List<Object> cellData = new ArrayList<>();
                cellData.add(i);
                cellData.add(j);
                Double cellV = collect.get(yList.get(i)).get(xList.get(j));
//                cellData.add(cellV == null ? 0d : cellV);
                cellData.add(cellV);
                vList.add(cellData);
            }
        }

        resultMap.put(StaticUtil.X_DATA, xList);
        resultMap.put(StaticUtil.Y_DATA, yList);
        resultMap.put(StaticUtil.DATA, vList);

        return resultMap;
    }

}
