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

        Node(E value) {
            this.value = value;
        }
    }


    private Comparator<E> comparator;

    private Node<E> root;

    public BinaryTree(E value, Comparator<E> comparator) {
        root = new Node(value);
        this.comparator = comparator;
    }

    public BinaryTree(Comparator<E> comparator) {
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
            root = new Node(value);
        } else {
            if (comparator.compare(value, p.value) < 0) {
                p.left = new Node(value);
            } else {
                p.right = new Node(value);
            }
        }
    }

    /**
     * BFS
     */
    public void bfs() {
        Queue<Node<E>> queue = new LinkedList<>();

        int c = 0;
        root.level = c;
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> p = queue.poll();

            if (p.level != c) {
                c++;
                System.out.println();
            }
            System.out.print(p.value + ", ");

            if (p.left != null) {
                p.left.level = p.level + 1;
                queue.offer(p.left);
            }
            if (p.right != null) {
                p.right.level = p.level + 1;
                queue.offer(p.right);
            }
        }
        System.out.println();
    }

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
     * 打印
     */
    public void print() {
        // 计数层高
        root.level = 1;
        int level = calcHeight(root);

        // 打印树
        format2Decimal(level);
    }

    /**
     * 计算高度
     */
    private int calcHeight(Node<E> n) {
        int ch = n.level;
        int lh = ch, rh = ch;

        ch++;
        if (n.left != null) {
            n.left.level = ch;
            lh = calcHeight(n.left);
        }
        if (n.right != null) {
            n.right.level = ch;
            rh = calcHeight(n.right);
        }

        return Math.max(lh, rh);
    }

    /**
     * 分层打印
     */
    private void format2Decimal(int level) {
        Queue<Node<E>> queue = new LinkedList<>();

        int d = 0;

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> n = queue.poll();
            if (n == null) {
                continue;
            }

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

            // 打印间隔
            for (int k = 0; k < num; k++) {
                System.out.print("--");
            }

            // 打印数值
            if (n.value == null) {
                System.out.print("**");
            } else {
                String str = n.value.toString();
                if (str.length() == 1) {
                    System.out.print("0");
                }
                System.out.print(str);
            }

            // 不是叶子节点，补上不存在的儿子
            if (n.left != null || n.right != null) {
                Node<E> pad = new Node<>(null);
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
            }
            // 叶子节点不需要补
            else {
                if (n.left != null) {
                    queue.offer(n.left);
                }
                if (n.right != null) {
                    queue.offer(n.right);
                }
            }
        }
        System.out.println();
    }

    /**
     * 翻转树
     */
    public void invert() {
        invert(root);
    }
    public void invert(Node<E> p) {
        if (p == null) {
            return;
        }

        invert(p.left);
        invert(p.right);

        // swap
        Node<E> t = p.left;
        p.left = p.right;
        p.right = t;
    }

}
