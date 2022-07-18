import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.WeakHashMap;

public class SynchronizeTest {

    @Test
    void testLock() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (var i = 0; i < 100; i++) {
                synchronized (Lock.locks.get("key")) {
                    Constant.count++;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (var i = 0; i < 100; i++) {
                synchronized (Lock.locks.get("key")) {
                    Constant.count++;
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(Constant.count);
    }
}

class Constant {
    static int count = 0;
}

class Lock {
    static Map<String, Class<?>> locks = new WeakHashMap<>();
    static {
        locks.put("key", SynchronizeTest.class);
    }
}