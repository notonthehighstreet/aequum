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

import java.util.Comparator;
import java.util.Optional;

class OptionalComparator<T> implements Comparator<Optional<T>> {

    private final Comparator<T> comparator;

    OptionalComparator(final Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(final Optional<T> o1, final Optional<T> o2) {
        if (!o1.isPresent()) {
            return (!o2.isPresent()) ? 0 : -1;
        } else if (!o2.isPresent()) {
            return 1;
        } else {
            return comparator.compare(o1.get(), o2.get());
        }
    }
}
