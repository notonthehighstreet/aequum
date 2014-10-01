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

import static org.junit.Assert.*;

public class EqualsHashCodeTest {

    private EqualsHashCode<Dummy> subject;

    @Before
    public void setUp() throws Exception {
        subject = Aequum.builder(Dummy.class).withField(o -> o.one).withField(o -> o.two).withField(o -> o.three).build();
    }

    @Test
    public void isEqualShouldReturnTrueForSameObject() throws Exception {
        final Dummy o = dummy("one", "two", "three", "four");
        assertTrue(subject.isEqual(o, o));
    }

    @Test
    public void isEqualShouldReturnFalseForDifferentObjectTypes() throws Exception {
        assertFalse(subject.isEqual(dummy("one", "two", "three", "four"), this));
    }

    @Test
    public void isEqualShouldReturnFalseForDifferentFieldValues() throws Exception {
        assertFalse(subject.isEqual(dummy("one", "two", "three", "four"), dummy("one", "two", "three")));
    }

    @Test
    public void isEqualShouldReturnTueForSameFieldValues() throws Exception {
        assertTrue(subject.isEqual(dummy("one", "two", null, "four", "five"), dummy("one", "two", null, "four", "five")));
    }

    @Test
    public void toHashCodeShouldBeSameForSameFieldValues() throws Exception {
        assertEquals(subject.toHashCode(dummy("one", "two", "three")), subject.toHashCode(dummy("one", "two", "three")));
    }

    @Test
    public void toHashCodeShouldBeSameForSameObjects() throws Exception {
        final Dummy dummy = dummy("one", null, "three");
        assertEquals(subject.toHashCode(dummy), subject.toHashCode(dummy));
    }

    private Dummy dummy(final String one, final String two, final String... three) {
        final Dummy dummy = new Dummy();
        dummy.one = one;
        dummy.two = two;
        dummy.three = three;
        return dummy;
    }

    private static class Dummy {
        private String one;
        private String two;
        private String[] three;
    }
}
