package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class SelectionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Nums array cannot be null");
        }
        int n = nums.length;
        if (n == 0 || n == 1)
            return;
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (nums[j] < nums[minIndex])
                    minIndex = j;
            }
            swap(nums, i, minIndex);
        }
    }

    private void swap(double[] nums, int index1, int index2) {
        if (index1 != index2) {
            double temp = nums[index1];
            nums[index1] = nums[index2];
            nums[index2] = temp;
        }
    }
}
