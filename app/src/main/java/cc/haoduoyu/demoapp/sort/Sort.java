package cc.haoduoyu.demoapp.sort;

import java.util.Comparator;

/**
 * http://algs4.cs.princeton.edu/20sorting/
 * Created by XP on 2016/2/1.
 */
public class Sort {
    /**
     * 选择排序，
     * <p/>
     * 首先，找到数组中最小的那个元素，其次，将它和数组第一个元素交换位置，
     * 然后在剩下的元素中找到最小的元素，将它和数组的第二个元素交换位置。
     * 如此往复，直到整个数组排序
     *
     * @param a
     */
    public static void selection(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) min = j;
            }
            exch(a, i, min);
        }
    }

    /**
     * 插入排序
     * <p/>
     * 在要排序的一组数中，假设前面(n-1) [n>=2] 个数已经是排
     * 好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数
     * 也是排好顺序的。如此反复循环，直到全部排好顺序。
     *
     * @param a
     */
    public static void insertion(Comparable[] a) {//1，2,3
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    /**
     * 冒泡排序
     * <p/>
     * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，
     * 让较大的数往下沉，较小的往上冒。
     * 即每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
     *
     * @param a
     */
    public static void bubble(Comparable[] a) {

        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[i])) {
                    exch(a, j, i);
                }
            }
        }
    }

    /**
     * 快速排序
     * <p/>
     * 选择一个基准元素,通常选择第一个元素或者最后一个元素,
     * 通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,
     * 此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     *
     * @param a
     */

    public static void quick(Comparable[] a) {
        quick(a, 0, a.length - 1);
    }


    private static void quick(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        quick(a, lo, j - 1);
        quick(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }


    /***************************************************************************
     * Helper sorting functions.
     ***************************************************************************/
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
