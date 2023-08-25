package com.mkl.util;

import java.util.*;

public class ListToMap {

    public static void main(String[] args) {
        /*
         *List<Map<String, Object>> 转Map<Object, Object>
         */
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("1001", "list1_map1_value1");
        map1.put("1002", "list1_map1_value2");
        map1.put("1003", "list1_map1_value3");
        list1.add(map1);
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("1001", "list2_map3_value1");
        map2.put("1002", "list2_map3_value2");
        map2.put("1003", "list2_map3_value3");
        list2.add(map2);

        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("1001", "list3_map3_value1");
        map3.put("1002", "list3_map3_value2");
        map3.put("1003", "list3_map3_value3");
        list3.add(map3);
        list1.addAll(list2);
        list1.addAll(list3);
        /*
         * 1003:list1_map1_value3 1003:list2_map3_value3 1003:list3_map3_value3
         *
         * 1002:list1_map1_value2 1002:list2_map3_value2 1002:list3_map3_value2
         *
         * 1001:list1_map1_value1 1001:list2_map3_value1 1001:list3_map3_value1
         *
         * 转换为：
         * {1003=[list1_map1_value3, list2_map3_value3, list3_map3_value3],
         *  1001=[list1_map1_value1, list2_map3_value1, list3_map3_value1],
         *  1002=[list1_map1_value2, list2_map3_value2, list3_map3_value2]
         *  }
         */
        // 遍历所有集合
        Map<Object, Object> map4 = new HashMap<Object, Object>();
        for (Map<String, Object> map : list1) {
            Set set = map.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                List<Object> list4 = new ArrayList<Object>();
                Object key = it.next();
                Object value = map.get(key);
                if (map4.containsKey(key)) {
                    list4 = (List) map4.get(key);
                }
                list4.add(value);
                map4.put(key, list4);
            }
        }
        System.out.println(map4);

    }
}
