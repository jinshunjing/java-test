package org.jim.tree;

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
        x.color = RED;

        // 当前节点不是root，并且父节点为红色
        while (x != null && x != root && x.parent.color == RED) {
            // 父亲是祖父的左儿子
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                // 叔叔节点
                Node y = rightOf(parentOf(parentOf(x)));
                // 叔叔节点也是红色
                if (colorOf(y) == RED) {
                    // 叔叔，父亲变黑色，祖父变红色
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);

                    // 继续处理祖父
                    x = parentOf(parentOf(x));
                }
                // 叔叔节点是黑色
                else {
                    // 不是一条线，转成一条线，跟父亲节点交换
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        // 父亲节点左转
                        rotateLeft(x);
                    }
                    // 是一条线
                    // 父亲变黑色，祖父变红色
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    // 祖父节点右转，父亲与祖父交换
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

        root.color = BLACK;
    }

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
            // 如果p是root
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
