package com.github.brankale.jcolorspace.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FloatArrayTest {

    @Test
    void shouldHaveTheSpecifiedSize() {
        FloatArray array = new FloatArray(3);
        assertEquals(3, array.size());
    }

    @Test
    void shouldSetTheValue() {
        FloatArray array = new FloatArray(3);
        int index = 2;
        float value = 45.0f;
        array.set(index, value);
        assertEquals(value, array.get(index));
    }

    @Test
    void shouldThrowExceptionWhenSettingValueOnOutOfBoundsIndex() {
        FloatArray array = new FloatArray(3);
        assertThrows(IndexOutOfBoundsException.class, () ->
                array.set(3, 1.0f)
        );
    }

    @Test
    void shouldThrowExceptionWhenRetrievingValueOnOutOfBoundsIndex() {
        FloatArray array = new FloatArray(3);
        assertThrows(IndexOutOfBoundsException.class, () ->
                array.get(3)
        );
    }

    @Test
    void defaultArrayValuesAreZeros() {
        int size = 3;
        FloatArray array = new FloatArray(size);
        for (int i = 0; i < size; ++i) {
            assertEquals(0, array.get(i));
        }
    }

}