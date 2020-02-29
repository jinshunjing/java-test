package org.jim.java8;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class Java8Test {
    @Before
    public void before() {
        System.out.println();
        System.out.println();
    }

    @After
    public void after() {
        System.out.println();
        System.out.println();
    }

    /**
     * 接口的默认方法
     */
    @Test
    public void testDefaultMethod() {
        // Lambda 实现内部匿名类: (参数列表) -> {代码块}
        IDefaultMethod dm = (x) -> x * x;

        System.out.println(dm.version());
        System.out.println(dm.calc(8.0d));
    }

    @Test
    public void testLambda() {
        LambdaUtil.testMethod();
    }

    @Test
    public void testDate() throws Exception {
        //DateDemo.testZone();
        DateDemo.testClock();
        //DateDemo.testDateTime();
    }

    @Test
    public void testObjects() {
        ObjectsDemo.testEquals();
    }

    @Test
    public void testOptional() {
        try {
            OptionalDemo.testOrElse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
