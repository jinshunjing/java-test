package org.jim.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HttpTest {

    @Test
    public void testDemo() {
        HttpDemo demo = new HttpDemo();
        demo.demoBtc();
    }

}
