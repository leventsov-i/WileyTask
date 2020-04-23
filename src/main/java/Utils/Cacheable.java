package Utils;

public interface Cacheable<K, V> {

    V get(K key);

    void put(K key, V value);

    int size();
}
