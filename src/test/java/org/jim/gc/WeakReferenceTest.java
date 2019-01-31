package org.jim.gc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

@RunWith(SpringRunner.class)
public class WeakReferenceTest {

    @Test
    public void testWeakReference() throws Exception {
        WeakReference<String> name = new WeakReference<>(new String("Will"));
        System.out.println(name.get());

        System.gc();
        // GC 之后回收

        Thread.sleep(2000);
        System.out.println(name.get());
    }

    @Test
    public void testSoftReference() throws Exception {
        SoftReference<String> name = new SoftReference<>(new String("Will"));
        System.out.println(name.get());

        System.gc();
        // 只有内存不够了才回收

        Thread.sleep(2000);
        System.out.println(name.get());
    }

}
