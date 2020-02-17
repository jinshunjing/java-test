package org.jim.sort;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class QuickSortTest {

    @Test
    public void testFind() {
        int[] values = {1};
        QuickSort.find(values, 1);
        QuickSort.find(values, 2);
        QuickSort.find(values, 0);

        values = new int[]{1, 2, 3};
        QuickSort.find(values, 1);
        QuickSort.find(values, 2);
        QuickSort.find(values, 3);
        QuickSort.find(values, 4);
        QuickSort.find(values, 0);

        values = new int[]{4, 5, 1, 2, 3};
        QuickSort.find(values, 1);
        QuickSort.find(values, 2);
        QuickSort.find(values, 3);
        QuickSort.find(values, 4);
        QuickSort.find(values, 5);
        QuickSort.find(values, 6);
        QuickSort.find(values, 0);
    }

    @Test
    public void testSort() {
        int[] values = {1, 2, 3};
        values = new int[]{2, 1};
        values = new int[]{3, 2, 1};
        values = new int[]{3, 4, 2, 1};

        values = new int[]{1};
        values = new int[]{1, 2};
        values = new int[]{2, 1};
        values = new int[]{1, 1, 2, 2};
        values = new int[]{2, 2, 1};
        values = new int[]{1, 1, 2, 2, 3, 3, 4, 6, 5};
        values = new int[]{4, 5, 6, 1, 2, 3};
        QuickSort.quickSort(values, 0, values.length - 1);
        for (int v : values) {
            System.out.print(v + ", ");
        }
        System.out.println();
    }


}
