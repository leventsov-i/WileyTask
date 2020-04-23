package Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void test() {
        Cache<String, String> cache = new Cache(5, CacheType.LFU);

        cache.put("1", "100");
        Assertions.assertEquals("100", cache.get("1"));
        assertNull(cache.get("2"));

        cache.put("1", "101");
        Assertions.assertEquals("101", cache.get("1"));


        cache.put("2", "200");
        cache.put("3", "300");
    }

}