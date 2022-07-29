package com.wxw.util;

import io.swagger.models.auth.In;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.regex.Pattern.*;

public class Tset {
    @Autowired
    ApplicationContext context;

    @Test
    public void aa(){
        System.out.println(context);
    }
//
//    public static void main(String[] args) {
//        byte[] readBuffer = new byte[1024*1024];
//        try {
//            String flightInfo=new String(readBuffer,"UTF-8");
//            int length=flightInfo.length();
//            System.out.println(length);
//            flightInfo=flightInfo.trim();//通过trim去掉空字符
//            int length2=flightInfo.length();
//            System.out.println(length2);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    static void pong() {
        System.out.println("2222222222222");
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /*********************************************java 8********************************************/

    public static void main(String[] args) {
        System.out.println("4-2.0-桶".split("-")[0]);





    }

    /**
     * forEach 遍历
     */
    public static void streamForeach(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        list.stream().filter(a->a>3).forEach(System.out::println);

        List<Map<String,Integer>> list1 = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>(16);
        map.put("a",1);
        Map<String,Integer> map1 = new HashMap<>(16);
        map1.put("a",2);
        Map<String,Integer> map2 = new HashMap<>(16);
        map2.put("a",3);
        list1.add(map);
        list1.add(map1);
        list1.add(map2);
        list1.stream().filter(x->Integer.valueOf(x.get("a").toString())>1).forEach(System.out::println);
        list1.stream().filter(x->Integer.valueOf(x.get("a").toString())>1).map(Map::keySet).collect(Collectors.toList());
    }

    /**
     * find/match
     */
    public static void streamFindAndMatch(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        //匹配第一个
        Optional<Integer> optional =  list.stream().filter(x->x>3).findFirst();
        System.out.println("匹配第一个值："+optional.get());
        //匹配任意（适用于并行流）
        Optional<Integer> optional1 = list.parallelStream().filter(x->x>3).findAny();
        System.out.println("匹配任意一个值：" +optional1.get());
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 3);
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    /**
     * max/count
     */
    public static void streamMaxAndCount(){
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());

        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7);
        Optional<Integer> min0 = list1.stream().min(Integer::compareTo);
        System.out.println("最小的数："+min0.get());
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);
        System.out.println("最大的数："+max1.get());

        Optional<Integer> max2 =  list1.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自定义排序的最大值：" + max2.get());

        long count =  list1.stream().filter(x->x>2).count();
        System.out.println("list中大于2的元素个数："+count);
    }


    /**
     * flatMap
     */
    private static void streamFlatMap(){
        /**
         * toLowerCase 小写
         * toUpperCase 大写
         */
        String[] strArr = { "abcd", "bcdd", "Defde", "fTr" };
        List<String> strList = Arrays.stream(strArr).map(String::toLowerCase).collect(Collectors.toList());
        System.out.println("每个元素大写：" + strList);

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println("每个元素+3：" + intListNew);


        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s->{
            String [] strings = s.split(",");
            Stream<String> stream = Arrays.stream(strings);
            return stream;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }




}
