package org.jim.regex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
public class RegexTest {

    @Test
    public void testVersion() {
        RegexDemo.demoVersion();
    }

    @Test
    public void testVersion1() {
        long st = System.currentTimeMillis();
        int k = 1000_000;
        for (int i = 0; i < k; i++) {
            RegexDemo.demoVersion();
        }
        long ct = System.currentTimeMillis() - st;
        System.out.println(ct);
    }

    @Test
    public void testVersion2() {
        long st = System.currentTimeMillis();
        int k = 1000_000;

        String pattern = "^(.+)\\.(.+)\\.(.+)$";
        Pattern p = Pattern.compile(pattern);

        for (int i = 0; i < k; i++) {
            RegexDemo.demoVersion(p);
        }
        long ct = System.currentTimeMillis() - st;
        System.out.println(ct);
    }
}
