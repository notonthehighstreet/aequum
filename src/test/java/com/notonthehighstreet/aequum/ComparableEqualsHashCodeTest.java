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

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ComparableEqualsHashCodeTest {

    private ComparableEqualsHashCode<Dummy> subject;

    @Before
    public void setUp() throws Exception {
        subject = Aequum.builder(Dummy.class)
                .withComparableField(Dummy::getOne)
                .withOptionalComparableField(Dummy::getTwo)
                .withComparableField(Dummy::getThree, (o1, o2) -> o1[0].compareTo(o2[0]))
                .build();
    }

    @Test
    public void compareToShouldReturn0ForEqualObjects() throws Exception {
        final Dummy one = dummy("one", null, "three", "four");
        final Dummy two = dummy("one", null, "three", "four");

        assertEquals(0, subject.compare(one, two));
    }

    @Test
    public void compareToShouldSortNullAfterEverythingElseForFirstObject() throws Exception {
        final Dummy one = dummy("one", null, "three", "four");
        final Dummy two = dummy("one", "two", "three", "four");

        assertThat(subject.compare(one, two), lessThan(0));
    }

    @Test
    public void compareToShouldSortNullAfterEverythingElseForSecondObject() throws Exception {
        final Dummy one = dummy("one", "two", "three", "four");
        final Dummy two = dummy("one", null, "three", "four");

        assertThat(subject.compare(one, two), greaterThan(0));
    }

    @Test
    public void compareToShouldUsePassedInComparator() throws Exception {
        final Dummy one = dummy("one", "two", "three", "four");
        final Dummy two = dummy("one", "two", "three");

        assertEquals(0, subject.compare(one, two));
    }

    @Test
    public void compareToShouldCompareFieldsInSameOrderAsEquals() throws Exception {
        final String first = "two";
        final String second = "three";

        final Dummy one = dummy("one", first, "three", "four");
        final Dummy two = dummy("one", second, "three", "four");

        assertEquals(first.compareTo(second), subject.compare(one, two));
    }

    private Dummy dummy(final String one, final String two, final String... three) {
        final Dummy dummy = new Dummy();
        dummy.one = one;
        dummy.two = Optional.ofNullable(two);
        dummy.three = three;
        return dummy;
    }

    private static class Dummy {
        private String one;
        private Optional<String> two;
        private String[] three;

        public String getOne() {
            return one;
        }

        public Optional<String> getTwo() {
            return two;
        }

        public String[] getThree() {
            return three;
        }
    }
}
