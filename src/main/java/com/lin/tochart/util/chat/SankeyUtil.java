package com.lin.tochart.util.chat;

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
public interface SankeyUtil {


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

}