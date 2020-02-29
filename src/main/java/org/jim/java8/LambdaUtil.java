package org.jim.java8;

import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda
 *
 * @author JSJ
 */
public class LambdaUtil {

    /**
     * 方法和构造方法
     */
    public static void testMethod() {
        // Lambda构造方法：匿名实现类 = 实现方法
        IDefaultMethodFactory factory = DefaultMethodImpl::new;
        IDefaultMethod dm = factory.create("2.0.0");
        System.out.println(dm.calc(8));
        System.out.println(dm.version());

        // Lambda方法：匿名实现类 = 实现方法
        DefaultMethodImpl dmi = new DefaultMethodImpl("3.0.0");
        IDefaultMethod idm = dmi::calc;
        System.out.println(idm.calc(8));
        System.out.println(idm.version());
    }

    /**
     * 断言：一个参数，返回boolean
     */
    public static void testPredicate() {
        Predicate pa = Objects::isNull;
    }

    /**
     * 函数：一个参数，一个返回值
     */
    public static void testFunction() {
        Function<String, Integer> fa = Integer::valueOf;

    }

    /**
     * 供应者：无参，一个返回值
     */
    public static void testSupplier() {
        //Supplier<String> sa =
    }

    /**
     * 消费者：一个参数，无返回值
     */
    public static void testConsumer() {
       // Consumer<String> ca =
    }

    /**
     * 比较器：两个参数，返回整型
     */
    public static void testComparator() {
        Comparator<Integer> ca = (a, b) -> a.compareTo(b);
        Comparator<Integer> cb = Comparator.comparing(Integer::intValue);
        Comparator<Integer> cc = Comparator.naturalOrder();
    }

    /**
     * 流式编程
     */
    public static void testStream() {
        // 串行 stream
        // 并行 parallelStream

        // 过滤 filter (Predicate)
        // 排序 sort (Comparator)
        // 映射 map (Function)
        // 匹配 match (Predicate)

        // 计数 count
        // 规约 reduce
    }

}
