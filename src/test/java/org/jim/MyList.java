package org.jim;

public class MyList {

    static class Element {
        int value;

        Element next;

        Element(int value, Element next) {
            this.value = value;
            this.next = next;
        }
    }

    private Element head;
    //private Element tail;


    public void flip() {
        Element p = head; // 2

        Element prev = head; // 1, 3
        Element post = null; // 2

        while (prev != null) {
            // 当前对的后一个, prev = 5, post = null
            post = prev.next;
            if (post == null) {
                break;
            }

            // head
            if (head == prev) {
                head = post;
            }

            // p.next = prev;
            p.next = post;

            // swap:  prev, post, post.next
            prev.next = post.next;
            post.next = prev;

            // post, prev, prev.next
            p = prev;
            prev = prev.next;
            post = null;
        }
    }

    public void build() {
        Element e5 = new Element(5, null);
        Element e4 = new Element(4, e5);
        Element e3 = new Element(3, e4);
        Element e2 = new Element(2, e3);
        Element e1 = new Element(1, e2);

        head = e1;

        Element e = head;
        while (e != null) {
            System.out.print(e.value + " ");
            e = e.next;
        }
        System.out.println();

        flip();

        e = head;
        while (e != null) {
            System.out.print(e.value + " ");
            e = e.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new MyList().build();
    }
}
