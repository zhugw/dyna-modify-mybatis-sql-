package com.example.dynamodifymybatissql;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhuguowei on 5/14/17.
 */
public class ReflectionTest {
    @Test
    public void testGetFinalFieldValue() throws NoSuchFieldException, IllegalAccessException {
        Field field = Foo.class.getDeclaredField("bar");
        field.setAccessible(true);
        Foo f = new Foo();
        Object o = field.get(f);
        assertEquals(1, o);

    }

    class Foo{
        private final int bar = 1;
    }
}
