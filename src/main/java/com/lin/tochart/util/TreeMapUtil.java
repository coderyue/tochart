package com.lin.tochart.util;

import com.lin.tochart.common.StaticUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 矩形树图工具类
 * @author lin
 * @date   2022/7/24
 **/
public interface TreeMapUtil {

    /**
     * 获取矩形树图
     *
     * @param mapList
     * @param level1
     * @param level2
     * @param level3
     * @param level4
     * @param v
     * @return
     */
    default List<Map<String, Object>> getTreeMap(List<Map<String, Object>> mapList, String level1, String level2, String level3, String level4, String v) {
        return getResultList(mapList.stream().collect(Collectors.groupingBy(item -> (String) item.get(level1),
                Collectors.groupingBy(item -> (String) item.get(level2),
                        Collectors.groupingBy(item -> (String) item.get(level3),
                                Collectors.groupingBy(item -> (String) item.get(level4)))))), level4, v);

    }

    default List<Map<String, Object>> getResultList(Map<String, Map<String, Map<String, Map<String, List<Map<String, Object>>>>>> collect, String level4, String v) {
        Map<String, Object> tempMap = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        collect.forEach(tempMap::put);
        getResult(resultList, tempMap, level4, v);
        return resultList;
    }


    default void getResult(List<Map<String, Object>> resultList, Map<String, Object> collect, String level4, String v) {
        collect.forEach((dbName, dbValue) -> {
            if (dbValue instanceof Map && ((Map<String, Object>) dbValue).size() > 0) {
                List<Map<String, Object>> childList = new ArrayList<>();
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put(StaticUtil.NAME, dbName);
                tempMap.put(StaticUtil.CHILDREN, childList);
                resultList.add(tempMap);
                getResult(childList, (Map<String, Object>) dbValue, level4, v);
            } else if (dbValue instanceof List && ((List<?>) dbValue).size() > 0) {
                Map<String, Object> map = new HashMap<>();
                ((List<?>) dbValue).forEach(item -> {
                    map.put(StaticUtil.NAME, ((Map<String, Object>) item).get(level4));
                    map.put(StaticUtil.VALUE, ((Map<String, Object>) item).get(v));
                    resultList.add(map);
                });
            }
        });
    }

}
