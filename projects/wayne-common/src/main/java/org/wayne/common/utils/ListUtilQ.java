package org.wayne.common.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: lwq
 */
public class ListUtilQ {
    /**
     * 将入参的list超过reSize条的 拆分为多个list,最大的为reSize
     * 批量更新 批量插入等操作用到
     * @params List<T> , reSize
     * @return  List<List<T>>
     */
    public static <T> List<List<T>> diveList(List<T> source, int diveSize) {
        List<List<T>> result = new ArrayList();
        if (source == null) {
            return null;
        } else {
            int size = source.size();
            if (size <= diveSize) {
                result.add(source);
                return result;
            } else {
                for (int i = 0; i < source.size(); i += diveSize) {
                    result.add(source.subList(i, Math.min(i + diveSize, size)));
                }

                return result;
            }
        }
    }
    /**
     * 比较两个String集合的差异
     * true 返回交集 false差集
     *
     * 1.利用map快速定位key是否存在的特性
     * 2.使用并行stream
     */
    private static List<String> getDiffList(List<String> listA, List<String> listB,boolean flag) {
       return flag ?listA.parallelStream().filter(listB::contains).collect(Collectors.toList())
        :listA.parallelStream().filter(s -> !listB.contains(s)).collect(Collectors.toList());

        /*
        if (listB != null && !listB.isEmpty()) {
            Map<String, String> dataMap = new HashMap<String, String>();
            for (String id : listB) {
                dataMap.put(id, id);
            }
            List<String> newList = new ArrayList<String>();
            for (String id : listA) {
                if (!dataMap.containsKey(id)) {
                    newList.add(id);
                }
            }
            return newList;
        } else {
            return listA;
        }*/
    }

}
