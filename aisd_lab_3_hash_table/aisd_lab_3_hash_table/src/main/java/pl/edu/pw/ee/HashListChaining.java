package pl.edu.pw.ee;

import java.util.ArrayList;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem nil = null;
    private ArrayList<Elem> hashElems;
    private int n;
    private int nElem;

    private class Elem {
        private T value;
        private Elem next;

        Elem(T value, Elem nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        hashElems = new ArrayList<>();
        nElem = 0;
        n = size;
        initializeHash();
    }

    @Override
    public void add(T value) {
        if(value == null)
            throw new IllegalArgumentException("Value cannot be null");
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem oldElem = hashElems.get(hashId);
        while (oldElem != nil && !oldElem.value.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil) {
            oldElem.value = value;
        } else {
            hashElems.set(hashId, new Elem(value, hashElems.get(hashId)));
            nElem++;
        }
    }

    @Override
    public T get(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem elem = hashElems.get(hashId);

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != nil ? elem.value : null;
    }

    @Override
    public void remove(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        if (get(value) == null)
            return;

        Elem elem = hashElems.get(hashId);
        Elem prevElem;
        if (elem.value.equals(value)) {
            elem = elem.next;
            hashElems.set(hashId, elem);
            return;
        }
        prevElem = elem;
        elem = elem.next;
        while (elem != nil && !elem.value.equals(value)) {
            prevElem = prevElem.next;
            elem = elem.next;
        }
        prevElem.next = elem.next;
    }

    public double countLoadFactor() {
        return nElem / n;
    }

    private void initializeHash() {
        for (int i = 0; i < n; i++) {
            hashElems.add(nil);
        }
    }

    private int countHashId(int hashCode) {
        return Math.abs(hashCode) % n;
    }

    public void printHashTable() {
        for (int i = 0; i < n; i++) {
            Elem elem = hashElems.get(i);
            while (elem != nil) {
                System.out.print(elem.value + ", ");
                elem = elem.next;
            }
            System.out.println("null");
        }
    }
}