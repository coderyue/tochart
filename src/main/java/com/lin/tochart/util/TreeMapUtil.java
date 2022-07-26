package com.lin.tochart.util;

import com.lin.tochart.common.StaticUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 矩形树图工具类
 * @author lin
 * @date   2022/7/24
 **/
public interface TreeMapUtil {

    /**
     * 获取矩形树图
     * @param collect   按层级分组后的collect
     *                  <br>
     *                  mapList.stream().collect(Collectors.groupingBy(item -> (String) item.get(level1),
     *                      Collectors.groupingBy(item -> (String) item.get(level2),
     *                         Collectors.groupingBy(item -> (String) item.get(level3),
     *                                 Collectors.groupingBy(item -> (String) item.get(level4))))))
     * @param endChile 最后节点的child名称
     * @param v        取值的value
     * @return
     */
    default List<Map<String, Object>> getTreeMap(Map<String, ?> collect, String endChile, String v) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        getResult(resultList, collect, endChile, v);
        return resultList;
    }

    default void getResult(List<Map<String, Object>> resultList, Map<String, ?> collect, String level4, String v) {
        collect.forEach((dbName, dbValue) -> {
            if (dbValue instanceof Map && ((Map<String, ?>) dbValue).size() > 0) {
                List<Map<String, Object>> childList = new ArrayList<>();
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put(StaticUtil.NAME, dbName);
                tempMap.put(StaticUtil.CHILDREN, childList);
                resultList.add(tempMap);
                getResult(childList, (Map<String, ?>) dbValue, level4, v);
            } else if (dbValue instanceof List && ((List<?>) dbValue).size() > 0) {
                Map<String, Object> map = new HashMap<>();
                ((List<?>) dbValue).forEach(item -> {
                    map.put(StaticUtil.NAME, ((Map<String, ?>) item).get(level4));
                    map.put(StaticUtil.VALUE, ((Map<String, ?>) item).get(v));
                    resultList.add(map);
                });
            }
        });
    }

}
