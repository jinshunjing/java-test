package org.jim.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MyPriorityHeapTest {

    @Test
    public void testPrint() {
        MyPriorityHeap mph = new MyPriorityHeap(16);

        mph.insert(10);
        mph.insert(8);
        mph.insert(12);
        mph.insert(7);
        mph.insert(9);
        mph.insert(13);

        mph.print();
    }


    @Test
    public void testFind() {
        int[] values = {1, 3, 2};
        System.out.println(MyPriorityHeap.find(values, 1));
        System.out.println(MyPriorityHeap.find(values, 2));
        System.out.println(MyPriorityHeap.find(values, 3));
    }
}
