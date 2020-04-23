package Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheLFUTest {

    @Test
    public void simplifyTestAddObjects() {
        CacheLFU<Integer, Integer> cacheLFU = new CacheLFU(3);

        cacheLFU.put(1, 100);
        cacheLFU.put(2, 200);
        cacheLFU.put(3, 300);

        Assertions.assertEquals(100, (int) cacheLFU.get(1));
        Assertions.assertEquals(200, (int) cacheLFU.get(2));
        Assertions.assertEquals(300, (int) cacheLFU.get(3));
        Assertions.assertEquals(null, cacheLFU.get(4));
    }

    @Test
    public void testForEvictObjectsBySizeCache() {
        CacheLFU<Integer, Integer> cacheLFU = new CacheLFU(3);

        cacheLFU.put(1, 100);
        cacheLFU.put(2, 200);
        cacheLFU.put(3, 300);

        cacheLFU.get(1); cacheLFU.get(2);

        cacheLFU.put(4, 400);

        //must evict key 3
        Assertions.assertNull(cacheLFU.get(3));

        cacheLFU.get(4); cacheLFU.get(4); cacheLFU.get(4);
        cacheLFU.get(2);

        cacheLFU.put(5, 500);

        //must evict key 1
        Assertions.assertNull(cacheLFU.get(1));
        Assertions.assertEquals(200, (int) cacheLFU.get(2));
        Assertions.assertEquals(400, (int) cacheLFU.get(4));
    }

    @Test
    public void testForEvictObjectsBySizeCacheWithMoreElements() {
        CacheLFU<Integer, Integer> cacheLFU = new CacheLFU(6);

        cacheLFU.put(1, 100);
        cacheLFU.put(2, 200);
        cacheLFU.put(3, 300);
        cacheLFU.put(4, 400);
        cacheLFU.put(5, 500);
        cacheLFU.put(6, 600);

        Assertions.assertEquals(100, (int) cacheLFU.get(1));
        Assertions.assertEquals(200, (int) cacheLFU.get(2));
        Assertions.assertEquals(300, (int) cacheLFU.get(3));
        Assertions.assertEquals(400, (int) cacheLFU.get(4));
        Assertions.assertEquals(500, (int) cacheLFU.get(5));
        Assertions.assertEquals(600, (int) cacheLFU.get(6));

        cacheLFU.get(3); cacheLFU.get(3); cacheLFU.get(3);
        cacheLFU.get(4); cacheLFU.get(4); cacheLFU.get(4);
        cacheLFU.get(5); cacheLFU.get(5); cacheLFU.get(5);
        cacheLFU.get(6); cacheLFU.get(6); cacheLFU.get(6); cacheLFU.get(6);

        cacheLFU.put(7, 700); cacheLFU.get(7); cacheLFU.get(7);
        cacheLFU.put(8, 800);

        //Evict key 1 and 2
        Assertions.assertNull(cacheLFU.get(1));
        Assertions.assertNull(cacheLFU.get(2));

        cacheLFU.get(5);
    }
}