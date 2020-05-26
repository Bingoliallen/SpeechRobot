package com.vma.speechrobot.bean;

public class KeyValueBean<K, V> {

  public K key;
  public V value;

  public KeyValueBean(K key, V value) {
    this.key = key;
    this.value = value;
  }
}
