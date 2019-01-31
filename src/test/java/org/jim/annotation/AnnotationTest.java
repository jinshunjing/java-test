package org.jim.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AnnotationTest {

    @Test
    public void testAnnotation() {
        Apple apple = new Apple();

        System.out.println(apple.fruit);
        AnnotationProvider.parserAnnotation2(apple);
        System.out.println(apple.fruit);
    }

}
