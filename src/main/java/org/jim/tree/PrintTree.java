package org.jim.tree;

import java.util.LinkedList;
import java.util.Queue;

public class PrintTree {


    public void dfs(Node p) {
        System.out.println(p.value + ", " + p.level);

        Node l = p.left, r = p.right;

        if (l != null) {
            l.level = p.level + 1;
            dfs(l);
        }
        if (r != null) {
            r.level = p.level + 1;
            dfs(r);
        }
    }

    public int dfs(Node p, Node[] array) {
        Node l = p.left, r = p.right;
        int li = -1, ri = -1;
        if (l != null) {
            l.level = p.level + 1;
            l.index = p.index * 2 + 1;
            li = dfs(l, array);
        }
        if (r != null) {
            r.level = p.level + 1;
            r.index = p.index * 2 + 2;
            ri = dfs(r, array);
        }

        array[p.index] = p;
        int idx = Math.max(li, ri);
        return (idx == -1) ? p.index : idx;
    }

    public void bfs(Node[] array, int size) {
        int k = 0;
        for (int i = 0; i <= size; i++) {
            Node e = array[i];
            System.out.print((e == null) ? "* " : e.value + " ");
            if (i == k) {
                System.out.println();
                k = k * 2 + 2;
            }
        }
        System.out.println();
    }

    public void bfs2(Node[] array, int size) {
        // 每层间隔几个元素
        Node t = array[size];
        int[] d = new int[t.level + 2];
        d[t.level + 1] = 0;
        for (int i = t.level; i >=0; i--) {
            d[i] = d[i+1] * 2 + 1;
        }

        int k = 0;
        int level = 0;
        boolean first = true;
        for (int i = 0; i <= size; i++) {
            Node e = array[i];

            // 打印间隔
            int c = first ? d[level + 1] : d[level];
            StringBuilder sb = new StringBuilder();
            while (c-- > 0) {
                sb.append(" ");
            }
            // 打印数值
            sb.append(e == null ? "*" : e.value);
            System.out.print(sb.toString());
            // 打印换行
            if (i == k) {
                System.out.println();
                k = k * 2 + 2;
                level++;
                first = true;
            } else {
                first = false;
            }
        }
        System.out.println();
    }

    public void bfs(Node root) {
        Queue<Node> queue = new LinkedList<>();

        int d = 0;
        root.level = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node p = queue.poll();
            if (p == null) {
                continue;
            }

            if (d != p.level) {
                System.out.println();
                d = p.level;
            }

            System.out.print((p.value == null) ? "* " : p.value + " ");


        }
    }


    static class Node {
        Integer value;

        int level;
        int index;

        Node left;
        Node right;

        Node(Integer value) {
            this.value = value;
        }

        Node(Integer value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
