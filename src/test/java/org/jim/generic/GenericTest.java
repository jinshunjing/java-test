package org.jim.generic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GenericTest {

    @Test
    public void test1() {
        Box<String> box1 = new Box<>("China");
        System.out.println(box1.getData());

        Box<Long> box2 = new Box<>(101L);
        System.out.println(box2.getData());
    }

    @Test
    public void test2() {
        BoxWeight weight = new BoxWeight();

        Box<Integer> box1 = new Box<>(102);
        Box<Long> box2 = new Box<>(101L);

        BoxWeight.calc1(box1, box2);
        BoxWeight.calc2(box1, box2);

        Integer data1 = BoxWeight.show1(box1);
        System.out.println(data1);

        Box<Integer> box3 = new Box<>(103);
        weight.print1(box1, box3);
    }

}
