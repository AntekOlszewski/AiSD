package pl.edu.pw.ee;

import java.util.Collections;
import pl.edu.pw.ee.services.Sorting;

public class HeapSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Nums array cannot be null");
        }
        Heap<Double> heap = new Heap<>();
        for (double d : nums) {
            heap.put(d);
        }
        int index = nums.length - 1;
        while (heap.n > 0) {
            Collections.swap(heap.heap, 0, index);
            nums[index] = heap.pop();
            heap.hepifyDown(0);
            index--;
        }
    }

}
