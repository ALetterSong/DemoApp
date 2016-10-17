package cc.haoduoyu.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by xiepan on 2016/10/14.
 */

public class TwoSum {


    public static void main(String[] a) {
        int[] numbers = new int[]{1, 2, 3, 7, 5, 6, 4};
        int[] result = twoSum(numbers, 10);
        System.out.print(Arrays.toString(result));
    }


    private static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            hashMap.put(target - numbers[i], i);
        }

        for (int i = 0; i < numbers.length; i++) {
            Integer v = hashMap.get(numbers[i]);

            if (v != null && v != i) {
                return new int[]{i, v};
            }
        }
        throw new RuntimeException();
    }
}
