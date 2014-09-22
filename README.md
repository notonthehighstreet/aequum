##Aequum Library##

###Responsibilities###
The Aequum library is designed to help remove some of the boiler plate when
writing the `equals`, `hashCode` and `compareTo` methods on POJO classes through the
use of [Java 8 lambdas](http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).

Note that this library depends on Java 8.

###Component initialisation instructions###
To use this library, call the `builder` method on the `Aequum` class and then
add the relevant fields through either the `withField` or `withComparableField`.
Once the necessary fields have been added, then the `build` method should be
called.

###State characteristics (i.e. stateless)###
Once the `build` method has been called, the state of the built classes will not
change and so are thread safe.

###Developer Highlights (i.e. Classes of interest)###
Apart from the builder class, `Aequum`, the two classes of interest are
`EqualsHashCode` and `ComparableEqualsHashCode`.

####EqualsHashCode####
This class is responsible for calculating equality and the hashCode for an
object.

####ComparableEqualsHashCode####
This class extends `EqualsHashCode` and implements the comparison of an object.

###Example###
This is an example of using this library on a POJO:

````java
public Example implements Comparable<Example> {

  private static final ComparableEqualsHashCode<Example> EQUALS_HASH_CODE =
      Aequum.builder(Example.class)
          .withComparableField(Example::getFieldOne)
          .withComparableField(Example::getFieldTwo)
          .withComparableField(Example::getFieldThree)
          .build();

  private final String fieldOne;
  private final String fieldTwo;
  private final int fieldThree;

  public Example(final String fieldOne, final String fieldTwo, final int fieldThree) {...}

  public String getFieldOne() {...}

  public String getFieldTwo() {...}

  public int getFieldThree() {...}

  @Override
  public boolean equals(final Object o) {
    return EQUALS_HASH_CODE.isEqual(this, o);
  }

  @Overide
  public int hashCode() {
    return EQUALS_HASH_CODE.toHashCode(this);
  }

  @Override
  public int compareTo(final Example o) {
    return EQUALS_HASH_CODE.isComparableTo(this, o);
  }
}
````
