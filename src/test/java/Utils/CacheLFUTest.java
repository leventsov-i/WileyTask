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
}