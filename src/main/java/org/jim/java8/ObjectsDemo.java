package org.jim.java8;

import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class ObjectsDemo {

    public static void testEquals() {
        Integer ia = Integer.valueOf(9);
        System.out.println(Objects.equals(ia, 9));

        ia = null;
        System.out.println(Objects.equals(ia, 9));
    }

}
