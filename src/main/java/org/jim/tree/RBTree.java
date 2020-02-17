package org.jim.tree;

import java.util.Stack;

/**
 * 红黑树
 */
public class RBTree {

    private static class Node {
        int value;
        boolean color;
        Node parent, left, right;

        Node(int v, boolean c, Node p) {
            value = v;
            color = c;
            parent = p;
        }
    }

    public void prev() {
        Stack<Node> stack = new Stack<>();

        Node n = root;
        while (n != null || !stack.isEmpty()) {
            while (n != null) {
                System.out.print(n.value);

                stack.push(n);
                n = n.left;
            }

            n = stack.pop();
            n = n.right;
        }
    }

    public void mid() {
        Stack<Node> stack = new Stack<>();

        Node n = root;
        while (n != null || !stack.isEmpty()) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }

            n = stack.pop();
            System.out.print(n.value);
            n = n.right;
        }
    }

    public void post() {
        Stack<Node> stack = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        Node n = root;
        while (n != null || !stack.isEmpty()) {
            while (n != null) {
                stack2.push(n.value);

                stack.push(n);
                n = n.right;
            }

            n = stack.pop();
            n = n.left;
        }

        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop());
        }
    }

    private Node root;

    public void insert(int v) {
        // 插入新叶子
        Node node = add(v);
        if (node.color) {
            return;
        }
        if (root == node.parent) {
            return;
        }

        fixAfterInsertion(node);
    }

    private Node add(int v) {
        if (root == null) {
            Node node = new Node(v, true, null);
            root = node;
            return node;
        }

        Node p = null, c = root;
        while (c != null) {
            p = c;
            if (v < p.value) {
                c = p.left;
            } else {
                c = p.right;
            }
        }

        return new Node(v, false, p);
    }

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private void fixAfterInsertion(Node x) {
        // 新节点染成红色
        x.color = RED;

        // 新节点不是root，并且父节点为红色
        while (x != null && x != root && x.parent.color == RED) {
            // 父亲是祖父的左儿子
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                // 叔叔
                Node y = rightOf(parentOf(parentOf(x)));
                // 叔叔和父亲都是红色
                if (colorOf(y) == RED) {
                    // 叔叔，父亲变黑色，祖父变红色
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    // 继续处理祖父
                    x = parentOf(parentOf(x));
                }
                // 叔叔是黑色，父亲是红色
                else {
                    // 新节点与父亲不在一条线上
                    // 新节点跟父亲交换，变成一条线
                    if (x == rightOf(parentOf(x))) {
                        // 父亲变成了新节点
                        x = parentOf(x);
                        // 父亲左转，新节点跟父亲交换
                        rotateLeft(x);
                    }
                    // 新节点与父亲在一条线上
                    // 父亲变黑色，祖父变红色
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    // 祖父右转，父亲与祖父交换
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Node y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }

        // 确保root节点是黑色
        root.color = BLACK;
    }

    /**
     * 向左旋转：父亲--右儿子 变成 父亲--左儿子
     *
     * @param p
     */
    private void rotateLeft(Node p) {
        if (p != null) {
            // 交换当前节点与右儿子
            Node r = p.right;

            // r 的左儿子成为 p 的右儿子
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }

            // r 取代 p
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            }
            else if (p.parent.left == p) {
                p.parent.left = r;
            }
            else {
                p.parent.right = r;
            }

            // p 成为 r 的左儿子
            r.left = p;
            p.parent = r;
        }
    }

    /**
     * 向右旋转：父亲--左儿子 变成 父亲--右儿子
     *
     * @param p
     */
    private void rotateRight(Node p) {
        if (p != null) {
            // 交换当前节点与左儿子
            Node l = p.left;

            // l 的右儿子成为 p 的左儿子
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }

            // l 换代 p
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            }
            else if (p.parent.right == p) {
                p.parent.right = l;
            }
            else {
                p.parent.left = l;
            }

            // p 成为 l 的右儿子
            l.right = p;
            p.parent = l;
        }
    }

    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }
    private void setColor(Node node, boolean color) {
        if (node == null) {
            node.color = color;
        }
    }
    private Node parentOf(Node node) {
        return node == null ? null : node.parent;
    }
    private Node leftOf(Node node) {
        return node == null ? null : node.left;
    }
    private Node rightOf(Node node) {
        return node == null ? null : node.right;
    }
}
