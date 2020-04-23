package Utils;

import java.util.*;

public class CacheLFU<K, V> implements Cacheable<K, V> {
    private final List<Node<K, V>> list;
    private final Map<K, Node<K, V>> map;
    private final int maxSize;

    public CacheLFU(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException("Illegal maximum cache size: " + maxSize);

        this.list = new ArrayList<>();
        this.map = new HashMap<>();
        this.maxSize = maxSize;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) return null;
        node.counterUse++;
        possibleSwap(node.index);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        if (list.size() == maxSize) {
            Node<K, V> nodeD = list.remove(list.size() - 1);
            map.remove(nodeD.key);
        }

        Node<K, V> node;
        if (map.containsKey(key)) {
            node = map.get(key);
            node.value = value;
        } else {
            node = new Node<>(key, value);
            list.add(node);
            node.index = list.size() - 1;
        }
        map.put(key, node);
    }

    private void possibleSwap(int index) {
        if (index == 0) return;

        Node<K, V> current = list.get(index);
        Node<K, V> last = list.get(index - 1);

        while (current.counterUse > last.counterUse) {
            list.set(index - 1, current);
            list.set(index, last);
            current.index = index - 1;
            last.index = index;

            index--;

            if (index == 0) return;

            current = list.get(index);
            last = list.get(index - 1);
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    private static class Node<K, V>{
        K key;
        V value;
        int counterUse;
        int index;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            counterUse = 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node<?, ?>) o;
            return Objects.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

}


