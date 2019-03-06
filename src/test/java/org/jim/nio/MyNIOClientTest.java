package org.jim.nio;

import org.jim.nio.server.MyNIOClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MyNIOClientTest {

    private MyNIOClient client;

    @Before
    public void setUp() throws Exception {
        client = new MyNIOClient();
        client.connect();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(5_000L);
        client.close();
    }

    @Test
    public void testSend() throws Exception {
        client.send("Qiyi123456789");
    }
}
