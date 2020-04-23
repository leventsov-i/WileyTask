package Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheLRUTest {

    @Test
    void simplifyTestAddObjects() {
        CacheLRU<Integer, Integer> cacheLRU = new CacheLRU(3);

        cacheLRU.put(1, 100);
        cacheLRU.put(2, 200);
        cacheLRU.put(3, 300);

        Assertions.assertEquals(100, (int) cacheLRU.get(1));
        Assertions.assertEquals(200, (int) cacheLRU.get(2));
        Assertions.assertEquals(300, (int) cacheLRU.get(3));
        Assertions.assertEquals(null, cacheLRU.get(4));
    }

    @Test
    public void testForEvictObjectsBySizeCache() {
        CacheLRU<Integer, Integer> cacheLRU = new CacheLRU(3);

        cacheLRU.put(1, 100);
        cacheLRU.put(2, 200);
        cacheLRU.put(3, 300);

        cacheLRU.put(4, 400);

        Assertions.assertEquals(400, (int) cacheLRU.get(4));
        //must evict key 1
        assertNull(cacheLRU.get(1));

        cacheLRU.get(2);

        cacheLRU.put(5, 500);
        Assertions.assertEquals(500, (int) cacheLRU.get(5));
        //must evict key 1
        assertNull(cacheLRU.get(3));
    }
}