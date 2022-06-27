package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Collections;

import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T> {

    ArrayList<T> heap;
    int n;

    public Heap() {
        heap = new ArrayList<>();
        n = 0;
    }

    @Override
    public void put(T item) {
        heap.add(item);
        hepifyUp(n);
        n++;
    }

    @Override
    public T pop() {
        if (n == 0)
            throw new IndexOutOfBoundsException("Heap is empty");
        T tmp = heap.get(n - 1);
        heap.remove(n - 1);
        n--;
        return tmp;
    }

    private void hepifyUp(int index) {
        int parentIndex;
        while (parentIndex(index) >= 0) {
            parentIndex = parentIndex(index);
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                Collections.swap(heap, index, parentIndex);
                index = parentIndex;
            } else
                break;
        }
    }

    public void hepifyDown(int index) {
        while (index < n) {
            int leftChildIndex = leftChildIndex(index);
            int rightChildIndex = rightChildIndex(index);
            T parentValue = heap.get(index);
            if (rightChildIndex > 0) {
                if (heap.get(leftChildIndex).compareTo(heap.get(rightChildIndex)) >= 0
                        && parentValue.compareTo(heap.get(leftChildIndex)) < 0) {
                    Collections.swap(heap, index, leftChildIndex);
                    index = leftChildIndex;
                } else if (parentValue.compareTo(heap.get(rightChildIndex)) < 0) {
                    Collections.swap(heap, index, rightChildIndex);
                    index = rightChildIndex;
                } else
                    break;
            } else if (leftChildIndex > 0 && parentValue.compareTo(heap.get(leftChildIndex)) < 0) {
                Collections.swap(heap, index, leftChildIndex);
                index = leftChildIndex;
            } else
                break;
        }
    }

    private int parentIndex(int childIndex) {
        return childIndex > 0 ? (childIndex - 1) / 2 : -1;
    }

    private int leftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1 < n ? 2 * parentIndex + 1 : -1;
    }

    private int rightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2 < n ? 2 * parentIndex + 2 : -1;
    }
}
