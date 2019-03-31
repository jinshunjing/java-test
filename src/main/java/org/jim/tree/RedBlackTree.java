package org.jim.tree;

public class RedBlackTree {

    private static class Node {
        int color;

        int value;

        Node parent;
        Node left;
        Node right;

        Node(int color, int value, Node parent) {
            this.color = color;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;
    private Node nail;

    public RedBlackTree() {
        root = null;
        nail = new Node(0, -1, null);
    }

    public void insert(int value) {
        Node node = new Node(1, value, null);
        node.left = nail;
        node.right = nail;

        // TODO 找到插入的位置


    }

    public void rebalance(Node node) {
        // 父节点为黑色，结束
        Node p = node.parent;
        if (p.color == 0) {
            return;
        }

        // 祖父节点与叔父节点: g.left = p, g.right = u
        Node g = p.parent;
        int relation = 0; // 父节点是祖父节点的左儿子
        Node u = g.right;
        if (g.right.value == p.value) {
            relation = 1; // 父节点是祖父节点的右儿子
            u = g.left;
        }

        // 如果叔父节点也为红色，则把父节点与叔父节点改成黑色，把祖父节点改成红色
        if (u.color == 1) {
            p.color = 0;
            u.color = 0;
            g.color = 1;
            // TODO 继续处理祖父节点
            rebalance(g);
        }
        // 如果叔父节点为黑色
        else {
            int childRelation = 0;
            if (node.value == p.right.value) {
                childRelation = 1;
            }

            // 父节点是祖父的左儿子
            if (relation == 0) {
                while (childRelation == 1) {
                    // 如果继承关系不是一条直线，g.left -> p.right -> node, 旋转成直线 g.left -> node.left -> p
                    g.left = node;

                    node.left = p;

                    p.left = nail;
                    p.right = nail;

                    p = g.left;
                    node = p.left;
                    childRelation = 0;
                }

                // 现在已经是一条直线了，旋转成三角形 p.right -> g, p.left -> node
                g.left = nail;
                g.color = 1;

                p.right = g;
                p.color = 0;
            }
            else {
                // TODO
            }
        }
    }
}
