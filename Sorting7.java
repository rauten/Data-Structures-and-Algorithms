import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riley Auten
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail shaker sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * When writing your sort, don't recheck already sorted items. The amount of
     * items you are comparing should decrease by 1 for each pass of the array
     * (in either direction). See the PDF for more info.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailShakerSort(T[] arr,
                                              Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Your list and your comparator cannot be null");
        }
        int remaining = arr.length;
        int beginning = 0;
        int i;
        int j;
        while (remaining > 0) {
            i = beginning + 1;
            while (i < remaining) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap((i - 1), i, arr);
                }
                i++;
            }
            remaining--;
            if (remaining > 0) {
                j = remaining - 2;
                while (j >= beginning) {
                    if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                        swap(j, (j + 1), arr);
                    }
                    j--;
                }
                beginning++;
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */

    public static <T> void insertionSort(T[] arr,
                                         Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Your list and your comparator cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if ((comparator.compare(arr[j], arr[j - 1])) < 0) {
                    swap(j, (j - 1), arr);
                } else {
                    j = 0;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Your list and your comparator cannot be null");
        }
        T greatest;
        int holder = 0;
        int remaining = arr.length;
        for (int i = 0; i < arr.length - 1; i++) {
            greatest = arr[0];
            for (int j = 1; j < remaining; j++) {
                if (comparator.compare(arr[j], greatest) > 0) {
                    holder = j;
                    greatest = arr[j];
                }
            }
            swap(holder, (remaining - 1), arr);
            remaining--;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr,
                                     Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                    "Your list, comparator, and random cannot be null");
        }
        quickSortHelp(0, (arr.length - 1), arr, comparator, rand);
    }

    /**
     *
     * @param low lower bound for the list split by the pivot
     * @param high upper bound for the list split by the pivot
     * @param arr list of Ts
     * @param comparator comparator to compare values of T
     * @param rand random number
     * @param <T> specifies a class type T in the method
     */
    private static <T> void quickSortHelp(int low, int high, T[] arr,
                                          Comparator<T> comparator,
                                          Random rand) {
        if ((high - low) < 0) {
            return;
        }
        int random = rand.nextInt(high - low) + low;
        int i = low;
        int j = high;
        int pivotFollower = random;
        while (i <= j) {
            while (comparator.compare(arr[i], arr[random]) < 0) {
                i++;
            }
            while (comparator.compare(arr[j], arr[random]) > 0) {
                j--;
            }
            if (i <= j) {
                swap(i, j, arr);
                i++;
                j--;
            }

        }
        if ((j - low) > 0) {
            quickSortHelp(low, j, arr, comparator, rand);
        }
        if ((high - i) > 0) {
            quickSortHelp(i, high, arr, comparator, rand);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Your list and your comparator cannot be null");
        }
        splitAndMergeArray(arr, comparator);
    }

    /**
     *
     * @param arr list of T values
     * @param a lower list
     * @param b upper list
     * @param comparator compares values of T
     * @param <T> specifies a class type T in the method
     */
    private static <T> void mergeNums(T[] arr, T[] a,
                                      T[] b, Comparator<T> comparator) {
        int aLow = 0;
        int bLow = 0;
        int placer = 0;
        while (aLow < a.length && bLow < b.length) {
            if (comparator.compare(a[aLow], b[bLow]) > 0) {
                arr[placer] = b[bLow];
                bLow++;
            } else {
                arr[placer] = a[aLow];
                aLow++;
            }
            placer++;
        }
        if (aLow < a.length) {
            while (aLow < a.length) {
                arr[placer] = a[aLow];
                aLow++;
                placer++;
            }
        } else {
            while (bLow < b.length) {
                arr[placer] = b[bLow];
                bLow++;
                placer++;
            }
        }
    }

    /**
     *
     * @param arr a list of type T values
     * @param comparator compares values of T
     * @param <T> specifies a class type T in the method
     */
    private static <T> void splitAndMergeArray(T[] arr,
                                              Comparator<T> comparator) {
        if (arr.length > 1) {
            T[] b = (T[]) new Object[(arr.length) / 2];
            T[] a;
            if (arr.length % 2 == 0) {
                a = (T[]) new Object[(arr.length) / 2];
            } else {
                a = (T[]) new Object[(arr.length) / 2 + 1];
            }
            for (int i = 0; i < a.length; i++) {
                a[i] = arr[i];
            }
            for (int i = 0; i < b.length; i++) {
                b[i] = arr[a.length + i];
            }
            splitAndMergeArray(a, comparator);
            splitAndMergeArray(b, comparator);
            mergeNums(arr, a, b, comparator);
        }
    }

    /**
     * Implement radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * DO NOT USE {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use an ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] radixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Your list cannot be null");
        }
        int bucketPlacement;
        int decPlace = 1;
        int greatest = Math.abs(arr[0]);
        List<Integer>[] bucket = new ArrayList[19];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > greatest) {
                greatest = arr[i];
            }
        }
        while ((greatest / decPlace) > 0) {
            for (Integer i : arr) {
                if (i >= 0) {
                    bucketPlacement = ((i / decPlace) % 10) + 9;
                    bucket[bucketPlacement].add(i);
                } else {
                    bucketPlacement = 9 + (((i / decPlace) % 10) - 10);
                    bucket[bucketPlacement].add(i);
                }
            }
            int placer = 0;
            for (int bucketIndex = 0; bucketIndex < 19; bucketIndex++) {
                for (Integer i : bucket[bucketIndex]) {
                    arr[placer] = i;
                    placer++;
                }
                bucket[bucketIndex].clear();
            }
            decPlace *= 10;
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sort instead of {@code Math.pow()}. DO NOT MODIFY THIS METHOD.
     *
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power.
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Invalid exponent.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }

    /**
     *
     * @param j first swap position
     * @param i second swap position
     * @param arr list of type T values
     * @param <T> specifies a class type T in the method
     */
    private static <T> void swap(int j, int i, T[] arr) {
        T holder = arr[j];
        arr[j] = arr[i];
        arr[i] = holder;
    }
}
