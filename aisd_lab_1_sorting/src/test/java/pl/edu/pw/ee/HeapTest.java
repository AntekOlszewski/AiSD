package pl.edu.pw.ee;

import org.junit.Test;

public class HeapTest {
    @Test
    public void heapTest() {
        Heap<Double> heap = new Heap<>();
        double[] nums = { 1.1, 5.4, 2.5, 2.5, 2.6, 9.3, 0.1, 0.6, 9.55 };
        for (double d : nums) {
            heap.put(d);
        }
        for (int i = 0; i < heap.heap.size(); i++) {
            System.out.print(heap.heap.get(i) + " ");
            if (i == 0 || i == 2 || i == 6) {
                System.out.println(" ");
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void popEmptyHeapTest() {
        Heap<Double> heap = new Heap<>();
        heap.pop();
    }
}
