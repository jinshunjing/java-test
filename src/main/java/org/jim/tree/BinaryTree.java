package org.jim.tree;

import java.util.*;

/**
 * 二叉树
 *
 * @param <E>
 */
public class BinaryTree<E> {

    /**
     * 节点
     *
     * @param <E>
     */
    private static class Node<E> {
        E value;
        int num;
        int level;

        Node<E> left;
        Node<E> right;

        Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }


    private Comparator<E> comparator;

    private Node<E> root;

    public BinaryTree(E value, Comparator<E> comparator) {
        root = new Node(value, null, null);
        this.comparator = comparator;
    }

    /**
     * 插入一个节点
     *
     * @param value
     */
    public void insert(E value) {
        Node<E> p = null;
        Node<E> c = root;
        while (c != null) {
            p = c;
            if (comparator.compare(value, p.value) < 0) {
                c = p.left;
            } else {
                c = p.right;
            }
        }

        if (p == null) {
            root = new Node(value, null, null);
        } else {
            if (comparator.compare(value, p.value) < 0) {
                p.left = new Node(value, null, null);
            } else {
                p.right = new Node(value, null, null);
            }
        }
    }

    /**
     * 先序遍历
     */
    public void prev() {
        prev(root);
    }

    private void prev(Node<E> n) {
        if (n == null) {
            return;
        }

        System.out.println(n.value);

        prev(n.left);
        prev(n.right);
    }

    /**
     * 用栈来改写成递推
     */
    public void prev2() {
        Stack<Node<E>> stack = new Stack<>();

        // 第一个节点
        Node<E> n = root;

        // 结束条件：栈为空
        while (n != null || !stack.isEmpty()) {

            while(n != null) {
                // 处理
                System.out.println(n.value);

                // 左边入栈
                stack.push(n);
                n = n.left;
            }

            // 出栈转向右边
            n = stack.pop();
            n = n.right;
        }
    }


    /**
     * 中序遍历
     */
    public void mid() {
        mid(root);
    }
    private void mid(Node<E> n) {
        if (n == null) {
            return;
        }

        mid(n.left);
        System.out.println(n.value);
        mid(n.right);
    }

    public void mid2() {
        Stack<Node<E>> stack = new Stack<>();

        // 第一个节点
        Node<E> n = root;

        // 结束条件
        while (n != null || !stack.isEmpty()) {

            while(n != null) {
                // 左边入栈
                stack.push(n);
                n = n.left;
            }

            // 出栈
            n = stack.pop();

            // 中间处理
            System.out.println(n.value);

            // 右边转向
            n = n.right;
        }
    }

    /**
     * 后序遍历
     */
    public void post() {
        post(root);
    }
    private void post(Node<E> n) {
        if (n == null) {
            return;
        }

        post(n.left);
        post(n.right);

        System.out.println(n.value);
    }

    /**
     * 用两个栈处理后序遍历
     * 其实就是前序遍历反一下
     */
    public void post2() {
        Stack<Node<E>> stack = new Stack<>();

        Stack<E> stack2 = new Stack<>();

        // 第一个节点
        Node<E> n = root;

        // 结束条件
        while (n != null || !stack.isEmpty()) {

            while(n != null) {
                // 处理
                stack2.push(n.value);

                // 右边入栈
                stack.push(n);
                n = n.right;
            }

            // 出栈转向左边
            n = stack.pop();
            n = n.left;
        }

        // 反一下
        while(!stack2.isEmpty()) {
            System.out.println(stack2.pop());
        }
    }

    /**
     * BFS
     */
    public void level() {
        Queue<Node<E>> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> n = queue.poll();
            if (n != null) {
                System.out.println(n.value);

                queue.offer(n.left);
                queue.offer(n.right);
            }
        }
    }

    /**
     * 打印
     */
    public void print() {
        //
        root.level = 1;
        int level = calc2(root);

        //
        format2(level);
    }

    /**
     * 计算高度
     */
    private int calc2(Node<E> n) {
        int ch = n.level;
        int lh = ch, rh = ch;

        ch++;
        if (n.left != null) {
            n.left.level = ch;
            lh = calc2(n.left);
        }
        if (n.right != null) {
            n.right.level = ch;
            rh = calc2(n.right);
        }

        return Math.max(lh, rh);
    }

    /**
     * 分层打印
     */
    private void format2(int level) {
        Queue<Node<E>> queue = new LinkedList<>();

        int d = 0;

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> n = queue.poll();
            if (n != null) {
                // 计算间隔
                int num = (1 << (level - n.level + 1)) - 1;

                // 第一个减半
                if (d < n.level) {
                    num = num >>> 1;

                    // 换行
                    if (d > 0) {
                        System.out.println();
                    }
                    d = n.level;
                }

                for (int k = 0; k < num; k++) {
                    System.out.print("-");
                }

                if (n.value == null) {
                    System.out.print("*");
                } else {
                    System.out.print(n.value);
                }

                if (n.level < level) {
                    // 补上不存在的节点
                    Node<E> pad = new Node<>(null, null, null);
                    pad.level = n.level + 1;

                    if (n.left == null) {
                        queue.offer(pad);
                    } else {
                        queue.offer(n.left);
                    }
                    if (n.right == null) {
                        queue.offer(pad);
                    } else {
                        queue.offer(n.right);
                    }
                } else {
                    if (n.left != null) {
                        queue.offer(n.left);
                    }
                    if (n.right != null) {
                        queue.offer(n.right);
                    }
                }
            }
        }
        System.out.println();
    }
}
