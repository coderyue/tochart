package com.lin.tochart.util.chat;

import com.lin.tochart.common.StaticMethod;
import com.lin.tochart.common.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 桑基图工具类
 * @author lin
 * @date   2022/7/25
 **/
public interface SankeyUtil extends StaticMethod {


    /**
     * 桑基图工具类
     * 参数的map中需要source和target和value
     *
     * @param mapList
     * @return
     */
    default Map<String, Object> toSankey(List<Map<String, Object>> mapList) {
        List<Object> nameList = new ArrayList<>(mapList.size());
        mapList.forEach(item -> {
            Object s = item.get(StaticValue.SOURCE);
            Object t = item.get(StaticValue.TARGET);
            if (s != null && t != null) {
                nameList.add(s);
                nameList.add(t);
            }
        });
        List<Map<String, Object>> nameMapList = nameList.stream().distinct().sorted().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put(StaticValue.NAME, item);
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(StaticValue.LINKS, mapList);
        dataMap.put(StaticValue.NODES, nameMapList);
        return dataMap;
    }

    /**
     * 获取桑基图，手动分隔source和target
     * @param mapList 元数据
     * @param stv     桑基图层级， 最后一级为value对应名称
     * @return
     */
    default Map<String, Object> toSankey(List<Map<String, Object>> mapList, String stv) {
        String[] split = stv.split(",");
        String vStr = split[split.length - 1];
        List<Map<String, Object>> resultMapList = new ArrayList<>(mapList.size() << 1);
        for (int i = 0, j = i + 1; j < split.length - 1; i++, j++) {
            int s = i; int t = j;
            Map<Object, Map<Object, Double>> collect = mapList.stream()
                    .collect(Collectors.groupingBy(item -> item.get(split[s]) == null ? StaticValue.unKnow : item.get(split[s]),
                            Collectors.groupingBy(item -> item.get(split[t]) == null ? StaticValue.unKnow : item.get(split[t]),
                                    Collectors.summingDouble(item -> Double.parseDouble(item.get(vStr).toString())))));
            collect.forEach((k, v) -> v.forEach((kk, vv) -> {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put(StaticValue.SOURCE, k);
                tempMap.put(StaticValue.TARGET, kk);
                tempMap.put(StaticValue.VALUE, format2Place(vv));
                resultMapList.add(tempMap);
            }));
        }
        return toSankey(resultMapList);
    }

}
