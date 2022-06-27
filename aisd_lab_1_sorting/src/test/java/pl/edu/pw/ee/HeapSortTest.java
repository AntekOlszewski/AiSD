package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import java.util.Random;
import org.junit.Test;

public class HeapSortTest {
    @Test
    public void optimisticDataTest() {
        double[] nums = { 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5,
                12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5, 22,
                22.5, 23, 23.5, 24, 24.5, 25, 25.5, 26, 26.5, 27, 27.5, 28, 28.5, 29, 29.5, 30, 30.5, 31, 31.5, 32,
                32.5, 33, 33.5, 34, 34.5, 35, 35.5, 36, 36.5, 37, 37.5, 38, 38.5, 39, 39.5, 40, 40.5, 41, 41.5, 42,
                42.5, 43, 43.5, 44, 44.5, 45, 45.5, 46, 46.5, 47, 47.5, 48, 48.5, 49, 49.5, 50 };
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        assertEquals(0, isSorted(nums));
    }

    @Test
    public void timeOptimisticData() {
        double[] nums;
        String message = "";
        long startTime, endTime;
        HeapSort sorter = new HeapSort();
        for (int i = 0; i < 11; i++) {
            nums = new double[(int) (Math.pow(2, i) * 100)];
            for (int j = 0; j < Math.pow(2, i) * 100; j++) {
                nums[j] = 100.123;
            }
            startTime = System.nanoTime();
            sorter.sort(nums);
            endTime = System.nanoTime();
            message += (int) Math.pow(2, i) * 100 + " " + (endTime - startTime) + "\n";
        }
        System.out.println(message);
    }

    @Test
    public void pesimisticDataTest() {
        double[] nums = { 50, 49.5, 49, 48.5, 48, 47.5, 47, 46.5, 46, 45.5, 45, 44.5, 44, 43.5, 43, 42.5, 42, 41.5, 41,
                40.5, 40, 39.5, 39, 38.5, 38, 37.5, 37, 36.5, 36, 35.5, 35, 34.5, 34, 33.5, 33, 32.5, 32, 31.5, 31,
                30.5, 30, 29.5, 29, 28.5, 28, 27.5, 27, 26.5, 26, 25.5, 25, 24.5, 24, 23.5, 23, 22.5, 22, 21.5, 21,
                20.5, 20, 19.5, 19, 18.5, 18, 17.5, 17, 16.5, 16, 15.5, 15, 14.5, 14, 13.5, 13, 12.5, 12, 11.5, 11,
                10.5, 10, 9.5, 9, 8.5, 8, 7.5, 7, 6.5, 6, 5.5, 5, 4.5, 4, 3.5, 3, 2.5, 2, 1.5, 1 };
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        assertEquals(0, isSorted(nums));
    }

    @Test
    public void timePesimisticData() {
        double[] nums;
        String message = "";
        long startTime, endTime;
        HeapSort sorter = new HeapSort();
        for (int i = 0; i < 11; i++) {
            nums = new double[(int) (Math.pow(2, i) * 100)];
            for (int j = (int) Math.pow(2, i) * 100 - 1; j >= 0; j--) {
                nums[j] = j * 0.01;
            }
            startTime = System.nanoTime();
            sorter.sort(nums);
            endTime = System.nanoTime();
            message += (int) Math.pow(2, i) * 100 + " " + (endTime - startTime) + "\n";
        }
        System.out.println(message);
    }

    @Test
    public void unsortedDataTest() {
        double[] nums = { 1.1, 5.4, 2.5, 2.5, 2.6, 9.3, 0.1, 0.6, 9.55 };
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        for (double d : nums) {
            System.out.print(d + " ");
        }
        assertEquals(0, isSorted(nums));
    }

    @Test
    public void randomDataTest() {
        int n = 100;
        double[] nums = new double[n];
        Random rand = new Random(123);
        for (int i = 0; i < n; i++) {
            nums[i] = 100 * rand.nextDouble();
        }
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        assertEquals(0, isSorted(nums));
    }

    @Test
    public void timeRandomData() {
        double[] nums;
        String message = "";
        long startTime, endTime;
        Random rand;
        HeapSort sorter = new HeapSort();
        for (int i = 0; i < 11; i++) {
            nums = new double[(int) (Math.pow(2, i) * 100)];
            rand = new Random(123);
            for (int j = (int) Math.pow(2, i) * 100 - 1; j >= 0; j--) {
                nums[j] = 100 * rand.nextDouble();
            }
            startTime = System.nanoTime();
            sorter.sort(nums);
            endTime = System.nanoTime();
            message += (int) Math.pow(2, i) * 100 + " " + (endTime - startTime) + "\n";
        }
        System.out.println(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataIsNull() {
        double[] nums = null;
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
    }

    @Test
    public void dataHasZeroElements() {
        double[] nums = {};
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        assertEquals(0, isSorted(nums));
    }

    @Test
    public void dataHasOneElement() {
        double[] nums = { 1.0 };
        HeapSort sorter = new HeapSort();
        sorter.sort(nums);
        assertEquals(0, isSorted(nums));
    }

    private int isSorted(double[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1])
                return -1;
        }
        return 0;
    }
}
