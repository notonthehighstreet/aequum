package com.notonthehighstreet.aequum;

/*
 * #%L
 * Aequum Library
 * %%
 * Copyright (C) 2014 notonthehighstreet.com
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <p>
 *     Utility class that allows {@linkplain Object#equals(Object) equals} and {@linkplain Object#hashCode() hashCode} methods to be written simply and quickly.
 * </p>
 * <p>
 *     Example
 * </p>
 * <pre>
 *  private static final EqualsHashCode&lt;DeliveryGroup&gt; EQUALS_HASH_CODE = Aequum.builder(Pojo.class)
 *      .withField(o -&gt; o.fieldOne)
 *      .withField(Pojo::getFieldTwo)
 *      .build();
 * </pre>
 * <pre>
 *
 * &#64;Override
 * public boolean equals(Object o) {
 *   return EQUALS_HASH_CODE.isEqual(this, o);
 * }
 *
 * &#64;Override
 * public int hashCode() {
 *    return EQUALS_HASH_CODE.toHashCode(this);
 * }
 * </pre>
 * @param <T> Type that the equality and hash codes should be calculated on.
 */
public class EqualsHashCode<T> {

    private final Collection<Function<T, ?>> fields;
    private final Class<T> expectedType;

    EqualsHashCode(final Collection<Function<T, ?>> fields, final Class<T> expectedType) {
        this.fields = fields;
        this.expectedType = expectedType;
    }

    Stream<Function<T, ?>> fields() {
        return fields.stream();
    }

    /**
     * Check whether the given objects are equal.
     * @param thisObject <code>this</code> object.
     * @param thatObject Object to compare it to.
     * @return True if they are equal, false otherwise.
     */
    public boolean isEqual(final T thisObject, final Object thatObject) {
        if (thisObject == thatObject) {
            return true;
        }
        if (!expectedType.isInstance(thatObject)) {
            return false;
        }

        final T that = expectedType.cast(thatObject);

        return fields().allMatch(f -> Objects.deepEquals(f.apply(thisObject), f.apply(that)));
    }

    /**
     * Calculate the hash code for the given object.
     * @param thisObject <code>this</code> object.
     * @return The hash code value.
     */
    public int toHashCode(final T thisObject) {
        final Stream<Object> map = fields().map(f -> f.apply(thisObject));
        return Arrays.deepHashCode(map.toArray(Object[]::new));
    }
}
