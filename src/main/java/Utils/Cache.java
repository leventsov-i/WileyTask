package Utils;

public class Cache<K, V> {
    private final Cacheable cache;

    public Cache(int maxSize, CacheType type) {
        cache = createCache(maxSize, type);
    }

    public V get(K key) {
        return (V) cache.get(key);
    }

    public void put (K key, V value) {
        cache.put(key, value);
    }

    private static Cacheable createCache(int maxSize, CacheType type) {
        switch (type) {
            case LFU: return new CacheLFU(maxSize);
            case LRU: return new CacheLRU(maxSize);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

}
