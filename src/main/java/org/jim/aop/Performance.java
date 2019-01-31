package org.jim.aop;

import org.springframework.stereotype.Component;

/**
 * @Component 必须是Spring托管的对象才可以AOP
 *
 * @author Dev
 */
@Component
public class Performance {

    public String perform(String arg) throws Exception {
        Thread.sleep(500L);
        if (null == arg) {
            throw new Exception("NULL");
        }
        System.out.println("IN Performance#perform: " + arg);
        Thread.sleep(500L);
        return "West Lake";
    }

}
