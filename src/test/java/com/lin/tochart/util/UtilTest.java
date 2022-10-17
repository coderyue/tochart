package com.lin.tochart.util;



import com.lin.tochart.util.chat.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UtilTest implements PieUtil, LineBarUtil, SankeyUtil, HeatMapUtil, TreeMapUtil, RelationMapUtil {

    @Test
    public void test() {
        // param data

        LocalDate localDate = LocalDate.now().minusDays(10);
        LocalDate now = LocalDate.now();

        List<Map<String, Object>> dataMapList = new ArrayList<>();
        IntStream.range(1, 6).forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("dataTime", localDate.plusDays(item).toString());
            map.put("flow", item * item + "");
            map.put("cnt", item + item + "");
            dataMapList.add(map);
        });

        // 饼图
        List<Map<String, Object>> mapList = toPie(dataMapList, "dataTime", "cnt");
        System.out.println("pie" + mapList);

        // 柱状图或折线图
        Map<String, Object> map = toLineOrBar(dataMapList, "dataTime", "cnt");
        System.out.println("line or bar" + map);

        Map<String, Object> map1 = toLineOrBar(dataMapList, localDate.toString(), now.toString(),
                DATE_FORMAT, ChronoUnit.DAYS, "dataTime", "cnt");
        System.out.println("deal time line or bar" + map1);

        Map<String, Object> map5 = toMultiLineOrBar(dataMapList, "dataTime", "flow", "cnt");
        System.out.println("multi line or bar" + map5);

        Map<String, Object> map2 = toMultiLineOrBar(dataMapList, localDate.toString(), now.toString(), DATE_FORMAT, ChronoUnit.DAYS, "dataTime", "flow", "cnt");
        System.out.println("multi line or bar using time" + map2);

        // 桑基图
        Map<String, Object> map3 = toSankey(dataMapList.stream().peek(item -> {
            item.put(SOURCE, item.get("dataTime"));
            item.put(TARGET, item.get("flow"));
            item.put(VALUE, item.get("cnt"));
        }).collect(Collectors.toList()));
        System.out.println("sankey" + map3);

        // 桑基图， 手动分隔source和target
        Map<String, Object> map6 = toSankey(dataMapList, "dataTime,flow,cnt");
        System.out.println("sankey >>:" + map6);

        // 热力图
        Map<String, Object> map4 = toHeatMap(dataMapList, "dataTime", "flow", "cnt");
        System.out.println("heatMap" + map4);

        // 矩形树图

//        toTreeMap()


    }

}
