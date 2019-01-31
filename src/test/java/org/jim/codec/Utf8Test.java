package org.jim.codec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
public class Utf8Test {

    @Test
    public void testASCII() throws Exception {
        String str = "abc";
        byte[] bytes = str.getBytes("utf-8");
        print(bytes);

        str = "中文";
        bytes = str.getBytes("utf-8");
        print(bytes);
    }

    private void print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : bytes) {
            sb.append(Integer.toHexString(b));
            //sb.append(b).append(" ");
        }
        System.out.println(bytes.length);
        System.out.println(sb.toString());
    }
}
