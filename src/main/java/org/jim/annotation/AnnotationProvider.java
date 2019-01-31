package org.jim.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationProvider {

    public static void parserAnnotation(Object object) {
        Class<?> class1 = object.getClass();
        Field[] fields = class1.getDeclaredFields();
        for(Field field : fields) {

            FruitName fruitName = field.getAnnotation(FruitName.class);
            try {
                if (fruitName != null) {
                    String value = (String) field.get(object);
                    field.set(object, fruitName.value() + value);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parserAnnotation2(Object object) {
        // 反射机制
        Class<?> class1 = object.getClass();
        Field[] fields = class1.getDeclaredFields();
        for(Field field : fields) {

            // 获取注解
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof FruitName) {

                    // 注解的处理逻辑
                    try {
                        String value = (String) field.get(object);
                        field.set(object, ((FruitName)annotation).value() + value);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

}
