package com.teemo.schoolmap.common.component;

import javafx.beans.NamedArg;

import java.io.Serializable;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 18:48
 * @version 1.0
 * @name schoolmap-server
 * @description 一组键值对
 */
public class Pair<K, V> implements Serializable{

    /**
     * 键
     */
    private K key;

    /**
     * 值
     */
    private V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Pair(@NamedArg("key") K key, @NamedArg("value") V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public int hashCode() {
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            return (key != null ? key.equals(pair.key) : pair.key == null) && (value != null ? value.equals(pair.value) : pair.value == null);
        }
        return false;
    }
}
