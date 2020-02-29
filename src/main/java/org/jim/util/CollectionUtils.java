package org.jim.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合的工具类
 *
 * @author Jim
 */
public class CollectionUtils {

    public static <E> void addAll(Collection<E> c, Collection<? extends E> other) {
        if (other == null || other.isEmpty()) {
            return;
        }
        c.addAll(other);
    }

    public static <E> void addNotNull(Collection<E> c, E e) {
        if (e == null) {
            return;
        }
        c.add(e);
    }

}
