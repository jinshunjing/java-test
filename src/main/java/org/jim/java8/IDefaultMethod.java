package org.jim.java8;

/**
 * Java 8 引入了接口的默认方法
 *
 * @author JSJ
 */
public interface IDefaultMethod {

    double calc(double v);

    default String version() {
        return "1.0.0";
    }
}
