package org.jim.generic;

/**
 * 范型方法
 *
 * @author Dev
 */
public class BoxWeight {

    /**
     * 普通方法，使用类型通配符
     *
     * @param box1
     * @param box2
     */
    public static void calc1(Box<?> box1, Box<?> box2) {
        System.out.println(box1.getData());
        System.out.println(box2.getData());
    }

    /**
     * 普通方法，使用类型通配符上限
     *
     * @param box1
     * @param box2
     */
    public static void calc2(Box<? extends Number> box1, Box<? extends Number> box2) {
        int sum1 = box1.getData().intValue() + box2.getData().intValue();
        System.out.println(sum1);
        long sum2 = box1.getData().longValue() + box2.getData().longValue();
        System.out.println(sum2);
    }

    /**
     * 范型方法
     *
     * @param box
     * @param <T>
     * @return
     */
    public static <T> T show1(Box<T> box) {
        return box.getData();
    }

    /**
     * 范型方法，可变参数
     *
     * @param args
     * @param <T>
     */
    public <T> void print1(Box<T>... args) {
        for (Box<T> arg : args) {
            System.out.println(arg.getData());
        }
    }

}
