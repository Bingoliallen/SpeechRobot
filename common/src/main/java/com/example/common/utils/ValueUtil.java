package com.example.common.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by xxdr on 2018/4/20.
 */

public class ValueUtil {

  private ValueUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  public static String null2Value(String value) {
    return null2Value(value, "");
  }

  public static String null2Value(String value, String target) {
    return value == null ? target : value;
  }

  public static int null2Value(Integer value) {
    return null2Value(value, 0);
  }

  public static int null2Value(Integer value, int target) {
    return value == null ? target : value;
  }

  /**
   * List
   */
  public static boolean isEmpty(List data) {
    if (data == null || data.isEmpty()) {
      return true;
    }
    return false;
  }

  public static String array2String(List list, String split) {
    StringBuilder result = new StringBuilder();
    if (list != null) {
      int size = list.size();
      for (int i = 0; i < size; i++) {
        if (i == 0) {
          result.append(list.get(i).toString());
        } else {
          result.append(String.format("%s%s", split, list.get(i).toString()));
        }
      }
    }
    return result.toString();
  }

  public static <T> void array2remove(List<T> list, T data) {
    Iterator<T> iterator = list.iterator();
    T item;
    while (iterator.hasNext()) {
      item = iterator.next();
      if (data.equals(item)) {
        iterator.remove();
      }
    }
  }

  /**
   * Map
   */
  public static <K, V> void map2traversal(Map<K, V> map, MapTraversalCallBack<K, V> callBack) {
    Iterator iterator = map.entrySet().iterator();
    Map.Entry<K, V> entry;
    int index = 0;
    while (iterator.hasNext()) {
      entry = (Entry) iterator.next();
      if (callBack != null) {
        callBack.call(entry.getKey(), entry.getValue(), index++);
      }
    }
  }

  public interface MapTraversalCallBack<K, V> {

    void call(K key, V value, int index);
  }
}
