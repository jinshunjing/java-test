package org.jim.java8;

import java.util.Optional;

/**
 * 处理可能为null的返回值
 *
 * @author Jim
 */
public class OptionalDemo {

    public static void testOrElse() throws Exception {
        Integer a = null;

        // null返回默认值
        Integer b = 2;
        System.out.println(Optional.ofNullable(a).orElse(b));

        // null调用方法
        Integer c = Optional.ofNullable(a).orElseGet(() -> {
            System.out.println("OrElseGet Supplier");
            return 3;
        });
        System.out.println(c);

        // null抛出异常
        c = Optional.ofNullable(a).orElseThrow(() -> {
            System.out.println("OrElseThrow Supplier");
            return new Exception("OrElseThrow Exception");
        });
        System.out.println(c);
    }

}
