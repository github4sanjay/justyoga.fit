package com.justyoga.util.dto.cache;

import java.util.Collection;
import java.util.Map;

public interface CacheApi<K, V> {
    void put(K k, V v);

    V get(K k);

    void clearAll();

    void clear(K k);

    void initialLoad();

    void putAll(Map<K, V> map);

    Collection<V> getAll();
}
