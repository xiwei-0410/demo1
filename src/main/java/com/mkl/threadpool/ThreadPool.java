package com.mkl.threadpool;

import java.util.*;

/**
 * 线程相关内容
 * @author mkl
 */
public class ThreadPool {

    public static boolean compareArray(int[] arr1, int[] arr2) {
        Set<Integer> s1 = new HashSet<Integer>();
        for (int i : arr1) { s1.add(i); }
        Set<Integer> s2 = new HashSet<Integer>();
        for (int i : arr2) { s2.add(i); }
        return s1.containsAll(s2) && s2.containsAll(s1); }


    public static boolean compareArray1(int[] arr1, int[] arr2) {
        for (int i : arr1) {
            int count = 0;
            for (int j : arr2) {
                if (i == j) { count++; } } if (count == 0) { return true; } } return false; }


    public static ArrayList<String> compareArray(String[] arr1, String[] arr2) {
        ArrayList<String> list = new ArrayList<String>();
        for(String s : arr1){
            boolean isExist = false;
            for(String t : arr2){
                if(s.equalsIgnoreCase(t)){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                list.add(s);
            }
        }
        return list;
    }

    public static ArrayList<String> compareArray1(String[] arr1,String[] arr2){
        ArrayList<String> list = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        for(String s : arr2){
            set.add(s);
        }
        for(String s : arr1){
            if(!set.contains(s)){
                list.add(s);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String[] aa = "日检,周检".split(",");
        String[] bb = "周检".split(",");
        ArrayList cc = compareArray1(aa,bb);
        System.out.println(cc);
        System.out.println(String.join(",", cc));
    }




}
