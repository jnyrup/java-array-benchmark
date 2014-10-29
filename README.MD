# Benchmark of arrays in Java

This repository contains source code for benchmarking the runtime overhead between different implementations of 2D arrays in Java.

The benchmark mainly covers the following questions:

1. If you know the size of your array, what is the overhead of using an ArrayList vs an array?
2. Is there any measurable difference using a 1D array of size n^2 vs a 2D array of size n x n?
3. If you know how large numbers you store, are there any performance gains of choosing a smaller data type?

If you find any mistakes, improvements or whatever you are more than welcome to open an issue or a pull request. 
I am particarly interessed in a nice way to encapsulate the testing in a function that takes a function and an array/ArrayList as parameter.

#Results
Please skim through the source code before reading my observations. At least to get familiar with my obscure function naming.

The testing was done with `n = 2000` and `100` iterations. I plan to do the benchmarks with different array sizes to see how that changes the picture.

When looking at the test results it is easy to divide the implementations into five different groups based on their speed.
For each group I will discuss why its speed differs from the others.
```
name of benchmark                             avg time [ms]    scaled score
---------------------------------------------------------------------------
name of benchmark                             avg time [ms]    scaled score
---------------------------------------------------------------------------
shortSingleInt                                0.143937             1.000000    (1)
troveShort                                    0.144306             0.997443
troveShortQuick                               0.144464             0.996349
shortMultiInt                                 0.146000             0.985868

intSingle                                     0.175314             0.821019    (2)
troveIntQuick                                 0.180594             0.797019
intMulti                                      0.181340             0.793740
troveInt                                      0.181487             0.793094

shortSingleShort                              0.268475             0.536127    (3)
shortMultiShort                               0.269197             0.534689

ArrayListMutableSingleInt                     0.775027             0.185718    (4)
ArrayListMutableSingleMutableInt              0.778915             0.184791
ArrayListMutableSingleIntForeach              0.779429             0.184669
ArrayListMutableSingleMutableIntForeach       0.779671             0.184612
ArrayListMutableMultiIntTemp                  0.795110             0.181027
ArrayListMutableMultiInt                      0.795508             0.180937
ArrayListMutableMultiMutableInt               0.795630             0.180909
ArrayListMutableMultiMutableIntForeach        0.800553             0.179796
ArrayListMutableMultiIntForeach               0.800647             0.179775
integerSingleInt                              0.824274             0.174622
integerMultiInt                               0.897896             0.160304
arrayListShortSingleInt                       0.939230             0.153249
arrayListShortSingleintForeach                0.945768             0.152190
arrayListIntegerSingleInt                     0.990180             0.145364
arrayListIntegerMultiInt                      1.009556             0.142574
arrayListIntegerMultiIntTemp                  1.010221             0.142480
arrayListShortMultiIntTemp                    1.020367             0.141063
arrayListIntegerSingleintForeach              1.021236             0.140943
arrayListShortMultiInt                        1.022112             0.140823
arrayListIntegerMultiIntForeach               1.025668             0.140334
arrayListShortMultiIntForeach                 1.029982             0.139747

integerSingleInteger                          2.299529             0.062594    (5)
integerMultiInteger                           2.308570             0.062349
arrayListShortSingleShort                     2.324123             0.061932
arrayListShortSingleShortForeach              2.376490             0.060567
arrayListIntegerSingleInteger                 2.440701             0.058973
arrayListIntegerSingleIntegerForeach          2.468810             0.058302
arrayListIntegerMultiIntegerForeach           2.695929             0.053390
arrayListIntegerMultiInteger                  2.763739             0.052080
arrayListShortMultiShort                      2.893809             0.049739
arrayListShortMultiShortForeach               3.319872             0.043356
```

##From 5 to 4
When compiling and disassembling this piece of Java code representing accumulation of Integers in an Integer
```java
Integer a = 1, b = 2;
a+=b;
```
the bytecode you get is
```
0: iconst_1
1: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
4: astore_1
5: iconst_2
6: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
9: astore_2
10: aload_1
11: invokevirtual #3                  // Method java/lang/Integer.intValue:()I
14: aload_2
15: invokevirtual #3                  // Method java/lang/Integer.intValue:()I
18: iadd
19: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
22: astore_1
```
If you again translates this back into Java, you will get
```java
Integer a = 1, b = 2;
a = Integer.valueOf(a.intValue() + b.intValue());
```
Apparently Integer is an immutable class, so instead of changing the int it is holding, a new object is allocated with the new int (I haven't looked further into why this is the case, but that is not the focus of these benchmarks).
This explains the slowdown of a factor 2.2--4.3 quite well, as each addition causes the creation of a new object leaving the old object to be garbage collected.

For Short its even worse, its additional overhead compared to Integer is described in [Section 3 to 2](#3to2).

##From 4 to 2
The first difference from 4 and 5 to 1,2 and 3 is that primitive data types are used instead of their corresponding wrapper classes. 
This gives us two advantages:

* The size of an int is 4 bytes, where an Integer takes up 32 bytes of heap space, so simply an array of ints has less data to be crunched. Check out http://btoddb-java-sizing.blogspot.dk/ for a more indepth analysis.
* The method `intValue()` is no longer called to extract the int, saving an operation.

<a name="3to2"></a>
##From 3 to 2
When short is only half the size of an int, why does it perform worse? 
It is even accumulated in a short to avoid any conversion, well think again.
Again the bytecode reveals the facts.
```java
short a = 1, b = 2;
a+=b;
```
gives us
```
0: iconst_1
1: istore_1
2: iconst_2
3: istore_2
4: iload_1
5: iload_2
6: iadd
7: i2s
8: istore_1
```
The answer is found on line 6 and 7 accompanied with [The Java Virtual Machine Instruction Set](http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.iadd).
There is no native operation for performing addition on two shorts. 
Instead addition is done by `iadd` which int result is then converted to a short with `i2s`. 
The reasons for not having an `sadd` operation is described in [The Structure of the Java Virtual Machine](http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.11.1)

##From 2 to 1
This one should now be obvious. 
To avoid the `i2s` operation the data type of the accumulator variable is changed  to an int. 
We now get all the benefits of native addition, no method calling and less data to fetch from memory.


##Memory
As you might have noticed the use of ArrayLists or multidimensions haven't been mentioned so far as obvious sinners, because for my tests their usage didn't influenced the performance. 
Of course there is an extra memory consumption of 2D arrays as each entry in the first dimension is a pointer to a second dimension. 

As far as I have been able to measure using some [code by Twitter](https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/objectsize/ObjectSizeCalculator.java) a 2D arrayList of 2000x2000 Integers consumes `46.9 kb` more memory than a 2D array of 2000 Integers, which consumes `39 kb` more memory than a 1D array of Integers.

The [Trove library](http://trove.starlight-systems.com/) offers high performance collections, e.g. ArrayLists that works on primitive data types instead of objects.

##Conclusion

Explore your language and if doubt which way to do it (disregardnig readability), benchmark to know which way is faster and disassemble to get insight *why* one way is faster. Trying to be "smart" can end up hurting your performance
