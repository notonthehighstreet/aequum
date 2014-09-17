package com.noths.aequum;

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

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *     Utility class that allows {@linkplain Object#equals(Object) equals}, {@linkplain Object#hashCode() hashCode} and {@linkplain Comparable#compareTo(Object)} methods to be
 *     written simply and quickly.
 * </p>
 * <p>
 *     Example
 * </p>
 * <pre>
 *  private static final ComparableEqualsHashCode<DeliveryGroup> EQUALS_HASH_CODE = Aequum.builder(DeliveryGroup.class)
 *      .withComparableField(o -> o.partner)
 *      .withComparableField(o -> o.name)
 *      .build();
 * </pre>
 * <pre>
 *
 * &#64;Override
 * public int compareTo(Example o) {
 *   return EQUALS_HASH_CODE.compareTo(this, o);
 * }
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
public class ComparableEqualsHashCode<T> extends EqualsHashCode<T> {

    private final Map<Function<T, ?>, Comparator> comparators;

    ComparableEqualsHashCode(final Collection<Function<T, ?>> fields, final Map<Function<T, ?>, Comparator> comparators, final Class<T> expectedType) {
        super(fields, expectedType);
        this.comparators = comparators;
    }

    /**
     * Compare two objects to each other for ordering.
     * @param thisObject <code>this</code> object.
     * @param thatObject Object to compare it to.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     */
    public int isComparableTo(final T thisObject, final T thatObject) {
        return fields().map(f -> compare(comparators.get(f), f.apply(thisObject), f.apply(thatObject))).filter(i -> i != 0).findFirst().orElse(0);
    }

    @SuppressWarnings("unchecked")
    private int compare(final Comparator comparator, final Object thisField, final Object thatField) {
        return comparator.compare(thisField, thatField);
    }

}
