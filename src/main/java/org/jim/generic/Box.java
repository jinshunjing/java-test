package org.jim.generic;

/**
 * 范型类
 *
 * @author Dev
 * @param <T>
 */
public class Box<T> {

    private T data;

    public Box(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
