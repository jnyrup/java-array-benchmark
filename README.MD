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
shortSingleInt                                0.155216             1.000000    (1)
troveShort                                    0.157585             0.984971
troveShortQuick                               0.157680             0.984378
shortMultiInt                                 0.161012             0.964007

intSingle                                     0.200699             0.773379    (2)
troveIntQuick                                 0.201497             0.770317
troveInt                                      0.201720             0.769465
intMulti                                      0.202570             0.766235

shortSingleShort                              0.279101             0.556130    (3)
shortMultiShort                               0.280039             0.554267

integerMultiInt                               0.676243             0.229528    (4)
integerSingleInt                              0.678404             0.228796
arrayListSingleInt                            0.776887             0.199793
ArrayListMutableSingleInt                     0.803997             0.193056
arrayListSingleintForeach                     0.811861             0.191186
ArrayListMutableSingleMutableIntForeach       0.813853             0.190718
ArrayListMutableSingleIntForeach              0.820412             0.189193
ArrayListMutableMultiIntTemp                  0.831975             0.186564
ArrayListMutableMultiMutableInt               0.836187             0.185624
arrayListMultiIntTemp                         0.846434             0.183377
ArrayListMutableMultiIntForeach               0.848207             0.182994
ArrayListMutableMultiInt                      0.853029             0.181959
ArrayListMutableMultiMutableIntForeach        0.855127             0.181513
ArrayListMutableSingleMutableInt              0.857684             0.180972
arrayListMultiIntForeach                      0.880373             0.176308
arrayListMultiInt                             0.944912             0.164265

integerMultiInteger                           2.139346             0.072553    (5)
arrayListSingleIntegerForeach                 2.241368             0.069251
arrayListSingleInteger                        2.367811             0.065553
arrayListMultiIntegerForeach                  2.740213             0.056644
integerSingleInteger                          2.818088             0.055079
arrayListMultiInteger                         2.924940             0.053067

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
This explains the slowdown of a factor 2.2-2.5 quite well, as each addition causes the creation of a new object leaving the old object to be garbage collected.

##From 4 to 2
The first difference from 4 and 5 to 1,2 and 3 is that primitive data types are used instead of their corresponding wrapper classes. 
This gives us two advantages:

* The size of an int is 4 bytes, where an Integer takes up 32 bytes of heap space, so simply an array of ints has less data to be crunched. Check out http://btoddb-java-sizing.blogspot.dk/ for a more indepth analysis.
* The method `intValue()` is no longer called to extract the int, saving an operation.

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

##Conclusion
As you might have noticed the use of ArrayLists or multidimensions haven't been mentioned so far as obvious sinners, because for my tests their usage didn't influenced the performance. 
Of course there is a little extra memory consumption of 2D arrays as each entry in the first dimension is a pointer to a second dimension. 
So an `Integer[2000][2000]` takes up 62,5 kb extra memory in contrast to a `Integer[2000*2000]`.
Here the [Trove library](http://trove.starlight-systems.com/) offers high performance collections, e.g. ArrayLists that works on primitive data types instead of objects.

Explore your language and if doubt which way to do it (disregardnig readability), benchmark to know which way is faster and disassemble to get insight *why* one way is faster.
