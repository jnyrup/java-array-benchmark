import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.mutable.*;

import gnu.trove.list.array.*;

final public class Test {
	private static int			multiSize;
	private static int			singleSize;
	private static int			iterations;
	private static final double	nano2milli	= 10e6;

	/************************************** short ***/
	/********** single */
	private static final long shortSingleShort(final short[] arr) {
		short total = 0;
		for (short a : arr)
			total += a;
		return (long) total;
	}

	private static final long shortSingleInt(final short[] arr) {
		int total = 0;
		for (short a : arr)
			total += a;
		return (long) total;
	}

	/********** multi */
	private static final long shortMultiShort(final short[][] arr) {
		short total = 0;
		for (short[] a : arr)
			for (short b : a)
				total += b;
		return (long) total;
	}

	private static final long shortMultiInt(final short[][] arr) {
		int total = 0;
		for (short[] a : arr)
			for (short b : a)
				total += b;
		return (long) total;
	}

	/********** TROVE */

	private static final long troveShortQuick(final TShortArrayList arr) {
		int total = 0;
		for (int i = 0; i < singleSize; i++)
			total += arr.getQuick(i);
		return (long) total;
	}

	private static final long troveShort(final TShortArrayList arr) {
		int total = 0;
		for (int i = 0; i < singleSize; i++)
			total += arr.get(i);
		return (long) total;
	}

	/********************************************* int ***/
	private static final long intSingle(final int[] arr) {
		int total = 0;
		for (int a : arr)
			total += a;
		return (long) total;
	}

	private static final long intMulti(final int[][] arr) {
		int total = 0;
		for (int[] a : arr)
			for (int b : a)
				total += b;
		return (long) total;
	}

	/********** TROVE */

	private static final long troveIntQuick(final TIntArrayList arr) {
		int total = 0;
		for (int i = 0; i < singleSize; i++)
			total += arr.getQuick(i);
		return (long) total;
	}

	private static final long troveInt(final TIntArrayList arr) {
		int total = 0;
		for (int i = 0; i < singleSize; i++)
			total += arr.get(i);
		return (long) total;
	}

	/************************************** Integer ***/
	/********** Int[] *******/
	/**** single */
	private static final long integerSingleInteger(final Integer[] arr) {
		Integer total = 0;
		for (Integer a : arr)
			total += a;
		return (long) total;
	}

	private static final long integerSingleInt(final Integer[] arr) {
		int total = 0;
		for (Integer a : arr)
			total += a;
		return (long) total;
	}

	/**** multi */
	private static final long integerMultiInteger(final Integer[][] arr) {
		Integer total = 0;
		for (Integer[] a : arr)
			for (Integer b : a)
				total += b;
		return (long) total;
	}

	private static final long integerMultiInt(final Integer[][] arr) {
		int total = 0;
		for (Integer[] a : arr)
			for (Integer b : a)
				total += b;
		return (long) total;
	}

	/********** ArrayList<Integer *******/
	/**** single */
	private static final long arrayListSingleInteger(final ArrayList<Integer> arr) {
		Integer total = 0;
		for (int i = 0; i < singleSize; ++i)
			total += arr.get(i);
		return (long) total;
	}

	private static final long arrayListSingleInt(final ArrayList<Integer> arr) {
		int total = 0;
		for (int i = 0; i < singleSize; ++i)
			total += arr.get(i);
		return (long) total;
	}

	/********** foreach */
	private static final long arrayListSingleIntegerForeach(final ArrayList<Integer> arr) {
		Integer total = 0;
		for (Integer a : arr)
			total += a;
		return (long) total;
	}

	private static final long arrayListSingleintForeach(final ArrayList<Integer> arr) {
		int total = 0;
		for (Integer a : arr)
			total += a;
		return (long) total;
	}

	/**** multi */
	private static final long arrayListMultiInteger(final ArrayList<ArrayList<Integer>> arr) {
		Integer total = 0;
		for (int i = 0; i < multiSize; ++i)
			for (int j = 0; j < multiSize; ++j)
				total += arr.get(i).get(j);
		return (long) total;
	}

	private static final long arrayListMultiInt(final ArrayList<ArrayList<Integer>> arr) {
		int total = 0;
		for (int i = 0; i < multiSize; ++i)
			for (int j = 0; j < multiSize; ++j)
				total += arr.get(i).get(j);
		return (long) total;
	}

	private static final long arrayListMultiIntTemp(final ArrayList<ArrayList<Integer>> arr) {
		int total = 0;
		for (int i = 0; i < multiSize; ++i) {
			final ArrayList<Integer> b = arr.get(i);
			for (int j = 0; j < multiSize; ++j)
				total += b.get(j);
		}
		return (long) total;
	}

	/********** foreach */
	private static final long arrayListMultiIntegerForeach(final ArrayList<ArrayList<Integer>> arr) {
		Integer total = 0;
		for (ArrayList<Integer> a : arr)
			for (Integer b : a)
				total += b;
		return (long) total;
	}

	private static final long arrayListMultiIntForeach(final ArrayList<ArrayList<Integer>> arr) {
		int total = 0;
		for (ArrayList<Integer> a : arr)
			for (Integer b : a)
				total += b;
		return (long) total;
	}

	/************************************** Mutable Integer ***/

	/********** ArrayList<MutableInt> *******/
	/**** single */
	private static final long ArrayListMutableSingleMutableInt(final ArrayList<MutableInt> arr) {
		MutableInt total = new MutableInt(0);
		for (int i = 0; i < singleSize; ++i)
			total.add(arr.get(i));
		return total.longValue();
	}

	private static final long ArrayListMutableSingleInt(final ArrayList<MutableInt> arr) {
		int total = 0;
		for (int i = 0; i < singleSize; ++i)
			total += arr.get(i).intValue();
		return (long) total;
	}

	/********** foreach */
	private static final long ArrayListMutableSingleMutableIntForeach(final ArrayList<MutableInt> arr) {
		MutableInt total = new MutableInt(0);
		for (MutableInt a : arr)
			total.add(a.intValue());
		return total.longValue();
	}

	private static final long ArrayListMutableSingleIntForeach(final ArrayList<MutableInt> arr) {
		int total = 0;
		for (MutableInt a : arr)
			total += a.intValue();
		return (long) total;
	}

	/**** multi */
	private static final long ArrayListMutableMultiMutableInt(final ArrayList<ArrayList<MutableInt>> arr) {
		MutableInt total = new MutableInt(0);
		for (int i = 0; i < multiSize; ++i)
			for (int j = 0; j < multiSize; ++j)
				total.add(arr.get(i).get(j));
		return total.longValue();
	}

	private static final long ArrayListMutableMultiInt(final ArrayList<ArrayList<MutableInt>> arr) {
		int total = 0;
		for (int i = 0; i < multiSize; ++i)
			for (int j = 0; j < multiSize; ++j)
				total += arr.get(i).get(j).intValue();
		return (long) total;
	}

	private static final long ArrayListMutableMultiIntTemp(final ArrayList<ArrayList<MutableInt>> arr) {
		int total = 0;
		for (int i = 0; i < multiSize; ++i) {
			final ArrayList<MutableInt> b = arr.get(i);
			for (int j = 0; j < multiSize; ++j)
				total += b.get(j).intValue();
		}
		return (long) total;
	}

	/********** foreach */
	private static final long ArrayListMutableMultiMutableIntForeach(final ArrayList<ArrayList<MutableInt>> arr) {
		MutableInt total = new MutableInt(0);
		for (ArrayList<MutableInt> a : arr)
			for (MutableInt b : a)
				total.add(b);
		return total.longValue();
	}

	private static final long ArrayListMutableMultiIntForeach(final ArrayList<ArrayList<MutableInt>> arr) {
		int total = 0;
		for (ArrayList<MutableInt> a : arr)
			for (MutableInt b : a)
				total += b.intValue();
		return (long) total;
	}

	public static void main(String[] args) {
		// Test number of parameters
		if (args.length != 2) {
			System.err.println("Usage: Test <array size> <#iterations>");
			System.exit(1);
		}

		// Test if parameters are numeric and positive
		try {
			multiSize = Integer.valueOf(args[0]);
			singleSize = multiSize * multiSize;
			iterations = Integer.valueOf(args[1]);
			if (!(multiSize >= 1 && iterations >= 1)) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.err.println("One or more parameters could not be interpreted as positive numeric values");
			System.exit(1);
		}

		ArrayList<Container> container = new ArrayList<Container>();
		Random r = new Random();
		long total = 0;
		long time = 0;
		/**********************************************/
		int[] arr = new int[singleSize];
		for (int i = 0; i < singleSize; i++)
			arr[i] = r.nextInt();

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = intSingle(arr);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "intSingle"));

		arr = null;
		System.gc();
		/**********************************************/
		Integer[] arrb = new Integer[singleSize];
		for (int i = 0; i < singleSize; i++)
			arrb[i] = r.nextInt();

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = integerSingleInteger(arrb);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "integerSingleInteger"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = integerSingleInt(arrb);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "integerSingleInt"));

		arrb = null;
		System.gc();
		/**********************************************/
		int[][] arrMulti = new int[multiSize][multiSize];
		for (int i = 0; i < multiSize; i++)
			for (int j = 0; j < multiSize; j++)
				arrMulti[i][j] = r.nextInt();

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = intMulti(arrMulti);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "intMulti"));

		arrMulti = null;
		System.gc();
		/**********************************************/
		Integer[][] arrMultiInt = new Integer[multiSize][multiSize];
		for (int i = 0; i < multiSize; i++)
			for (int j = 0; j < multiSize; j++)
				arrMultiInt[i][j] = r.nextInt();

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = integerMultiInteger(arrMultiInt);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "integerMultiInteger"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = integerMultiInt(arrMultiInt);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "integerMultiInt"));

		arrMultiInt = null;
		System.gc();
		/**********************************************/
		ArrayList<ArrayList<Integer>> arrMulti2 = new ArrayList<ArrayList<Integer>>(multiSize);
		for (int i = 0; i < multiSize; i++) {
			arrMulti2.add(i, new ArrayList<Integer>(multiSize));
			for (int j = 0; j < multiSize; j++)
				arrMulti2.get(i).add(j, r.nextInt());
		}

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListMultiInteger(arrMulti2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListMultiInteger"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListMultiInt(arrMulti2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListMultiInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListMultiIntTemp(arrMulti2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListMultiIntTemp"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListMultiIntegerForeach(arrMulti2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListMultiIntegerForeach"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListMultiIntForeach(arrMulti2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListMultiIntForeach"));

		arrMulti2 = null;
		System.gc();
		/**********************************************/
		ArrayList<ArrayList<MutableInt>> arrMultiMutable2 = new ArrayList<ArrayList<MutableInt>>(multiSize);
		for (int i = 0; i < multiSize; i++) {
			arrMultiMutable2.add(i, new ArrayList<MutableInt>(multiSize));
			for (int j = 0; j < multiSize; j++)
				arrMultiMutable2.get(i).add(j, new MutableInt(r.nextInt()));
		}

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableMultiMutableInt(arrMultiMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableMultiMutableInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableMultiInt(arrMultiMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableMultiInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableMultiIntTemp(arrMultiMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableMultiIntTemp"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableMultiMutableIntForeach(arrMultiMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableMultiMutableIntForeach"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableMultiIntForeach(arrMultiMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableMultiIntForeach"));

		arrMultiMutable2 = null;
		System.gc();
		/**********************************************/
		ArrayList<MutableInt> arrSingleMutable2 = new ArrayList<MutableInt>(singleSize);
		for (int i = 0; i < singleSize; i++) {
			arrSingleMutable2.add(i, new MutableInt(r.nextInt()));
		}

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableSingleMutableInt(arrSingleMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableSingleMutableInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableSingleInt(arrSingleMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableSingleInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableSingleMutableIntForeach(arrSingleMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableSingleMutableIntForeach"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = ArrayListMutableSingleIntForeach(arrSingleMutable2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "ArrayListMutableSingleIntForeach"));

		arrSingleMutable2 = null;
		System.gc();
		/**********************************************/
		ArrayList<Integer> arrSingle2 = new ArrayList<Integer>(singleSize);
		for (int i = 0; i < singleSize; i++)
			arrSingle2.add(i, r.nextInt());

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListSingleInteger(arrSingle2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListSingleInteger"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListSingleInt(arrSingle2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListSingleInt"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListSingleIntegerForeach(arrSingle2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListSingleIntegerForeach"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = arrayListSingleintForeach(arrSingle2);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "arrayListSingleintForeach"));

		arrSingle2 = null;
		System.gc();
		/**********************************************/
		short[] arrs = new short[singleSize];
		for (int i = 0; i < singleSize; i++)
			arrs[i] = (short) r.nextInt(Short.MAX_VALUE + 1);

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = shortSingleShort(arrs);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "shortSingleShort"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = shortSingleInt(arrs);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "shortSingleInt"));

		arrs = null;
		System.gc();
		/**********************************************/
		short[][] arrsmult = new short[multiSize][multiSize];
		for (int i = 0; i < multiSize; i++)
			for (int j = 0; j < multiSize; j++)
				arrsmult[i][j] = (short) r.nextInt(Short.MAX_VALUE + 1);

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = shortMultiShort(arrsmult);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "shortMultiShort"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = shortMultiInt(arrsmult);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "shortMultiInt"));

		arrsmult = null;
		System.gc();
		/**********************************************/
		TShortArrayList shortTrove = new TShortArrayList(singleSize);
		for (int i = 0; i < singleSize; i++)
			shortTrove.insert(i, (short) r.nextInt(Short.MAX_VALUE + 1));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = troveShortQuick(shortTrove);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "troveShortQuick"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = troveShort(shortTrove);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "troveShort"));

		shortTrove = null;
		System.gc();
		/**********************************************/
		TIntArrayList intTrove = new TIntArrayList(singleSize);
		for (int i = 0; i < singleSize; i++)
			intTrove.insert(i, (short) r.nextInt(Short.MAX_VALUE + 1));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = troveIntQuick(intTrove);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "troveIntQuick"));

		time = System.nanoTime();
		for (int i = 0; i < iterations; i++)
			total = troveInt(intTrove);
		time = System.nanoTime() - time;
		container.add(new Container(time / iterations, "troveInt"));

		intTrove = null;
		System.gc();

		// Sort and print results
		Collections.sort(container);
		double fast = (double) container.get(0).getTime();
		System.out.printf("%-45s %s %15s\n", "name of benchmark", "avg time [ms]", "scaled score");
		System.out.println("---------------------------------------------------------------------------");
		for (Container c : container) {
			System.out.printf("%-45s %f %20f\n", c.getName(), c.getTime() / nano2milli, fast / c.getTime());
		}
	}
}
