package org.jim.tree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;

@RunWith(SpringRunner.class)
public class BinaryTreeTest {

    @Test
    public void testBfs() {
        System.out.println();

        BinaryTree<Integer> bt = new BinaryTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        bt.insert(8);
        bt.insert(4);
        bt.insert(2);
        bt.insert(6);
        bt.insert(12);
        bt.insert(10);
        bt.insert(14);

        bt.bfs();

        bt.invert();
        bt.bfs();

        bt.print();

        System.out.println();
        System.out.println();
    }

    @Test
    public void testTree() {
        BinaryTree<Integer> bt = new BinaryTree<>(6, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        bt.insert(7);
        bt.insert(4);
        bt.insert(8);
        bt.insert(3);
        bt.insert(9);

//        bt.prev();
//        System.out.println();
//        bt.prev2();

//        bt.mid();
//        System.out.println();
//        bt.mid2();

//        bt.post();
//        System.out.println();
//        bt.post2();

        bt.level();
    }

    @Test
    public void testPrintTree() {
        BinaryTree<Integer> bt = new BinaryTree<>(4, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        bt.insert(2);
        bt.insert(1);
        bt.insert(3);
        bt.insert(5);

        bt.print();
    }


    @Test
    public void testPrintTree2() {
        BinaryTree<Integer> bt = new BinaryTree<>(6, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        bt.insert(7);
        bt.insert(4);
        bt.insert(8);
        bt.insert(3);
        bt.insert(9);

        bt.print();
    }

}
