package Utils;

import java.util.LinkedHashMap;

public class CacheLRU<K, V> implements Cacheable<K, V> {
    private final LinkedHashMap<K,V> map;
    private final int maxSize;

    public CacheLRU(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException("Illegal maximum cache size: " + maxSize);

        this.map = new LinkedHashMap<>(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    public V get(K key) {
        V value = map.get(key);
        if (value == null) return null;
        map.remove(key);
        map.put(key, value);
        return value;
    }

    @Override
    public void put(K key, V value) {
        if (map.size() == maxSize) {
            map.remove(getLastKey());
        }
        map.put(key, value);
    }

    @Override
    public int size() {
        return map.size();
    }

    private K getLastKey() {
        for (K k : map.keySet()) {
            return  k;
        }
        return null;
    }
}
