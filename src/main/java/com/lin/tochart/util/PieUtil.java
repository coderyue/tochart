package com.lin.tochart.util;

import com.lin.tochart.common.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 饼图工具类
 * @author lin
 * @date   2022/7/24
 **/
public interface PieUtil {

    /**
     * 获取饼图
     * @param mapList
     * @param name
     * @param value
     * @return
     */
    default List<Map<String, Object>> toPie(List<Map<String, Object>> mapList, String name, String value) {
        List<Map<String, Object>> resultMapList = new ArrayList<>(mapList.size());
        mapList.forEach(item -> {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put(StaticValue.NAME, item.get(name));
            tempMap.put(StaticValue.VALUE, item.get(value));
            resultMapList.add(tempMap);
        });
        return resultMapList;
    }


}
