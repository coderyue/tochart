package com.lin.tochart.util;

import com.lin.tochart.common.StaticUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 关系图
 *
 * @author qishuo
 * @date 2022-07-27 17:07:10
 */
public interface RelationMapUtil {

    /**
     * 关系图数据
     * @param mapList 数据源
     * @param sourceNodeKey 关联源的key
     * @param sourceCategoryKey 关联源分组的key
     * @param targetNodeKey 关联对象的key
     * @param targetCategoryKey 关联的对象分组的key
     * @return
     */
    default Map<String, Object> getRelationMap(List<Map<String, Object>> mapList, String sourceNodeKey, String sourceCategoryKey, String targetNodeKey, String targetCategoryKey) {
        Map<String, Integer> categoryMap = new LinkedHashMap<>();
        Map<String, Map<String, Object>> nodeMap = new LinkedHashMap<>();
        List<Map<String, Object>> links = new ArrayList<>();
        mapList.forEach(map -> {
            Map<String, Object> sourceNode = createNode(map, categoryMap, nodeMap, sourceCategoryKey, sourceNodeKey);
            Map<String, Object> targetNode = createNode(map, categoryMap, nodeMap, targetCategoryKey, targetNodeKey);
            links.add(new HashMap<String, Object>(){{
                put(StaticUtil.SOURCE, sourceNode.get(StaticUtil.ID));
                put(StaticUtil.TARGET, targetNode.get(StaticUtil.ID));
            }});
        });
        return new HashMap<String, Object>(){{
            put(StaticUtil.NODES, nodeMap.values());
            put(StaticUtil.LINKS, links);
            put(StaticUtil.CATEGORIES, categoryMap.values());
        }};
    }

    default Map<String, Object> createNode(Map<String, Object> map, Map<String, Integer> categoryMap, Map<String, Map<String, Object>> nodeMap, String categoryKey, String nodeKey) {
        String nodeName = map.get(nodeKey).toString();
        Integer categoryIndex = categoryMap.computeIfAbsent(map.get(categoryKey).toString(), k -> categoryMap.size() + 1);
        Map<String, Object> node = nodeMap.computeIfAbsent(nodeName, k -> new HashMap<String, Object>(){{
            put(StaticUtil.ID, String.valueOf(nodeMap.size()));
            put(StaticUtil.NAME, nodeName);
            put(StaticUtil.CATEGORY, categoryIndex);
            put(StaticUtil.VALUE, 0);
        }});
        node.put(StaticUtil.VALUE, ((int) node.get(StaticUtil.VALUE)) + 1);
        node.put(StaticUtil.SYMBOL_SIZE, node.get(StaticUtil.VALUE));
        return node;
    }
}