package org.jim.dynproxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Proxy;

@RunWith(SpringRunner.class)
public class CarProxyTest {

    @Test
    public void testProxy() throws Exception {
        Car audi = (Car) Proxy.newProxyInstance(
                Car.class.getClassLoader(),
                // 动态实现接口
                new Class<?>[] {Car.class},
                // 调用InvocationHandler
                new CarInvocationHandler(new Audi()));
        audi.drive("name1", "audi");
    }

}
