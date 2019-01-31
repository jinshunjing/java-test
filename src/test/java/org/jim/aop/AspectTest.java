package org.jim.aop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AspectTest {

    @Autowired
    private Performance performance;

    @Before
    public void setUp() throws Exception {
        Thread.sleep(2000L);
        System.out.println();
        System.out.println();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println();
        System.out.println();
        Thread.sleep(2000L);
    }

    @Test
    public void testAspect() throws Exception {
        String value = performance.perform("ABC");
        System.out.println(value);
    }

    @Test
    public void testAspect2() {
        try {
            String value = performance.perform(null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
