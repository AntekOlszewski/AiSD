package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final Deleted deletedElem;
    private final double correctLoadFactor;

    private class Deleted implements Comparable<T> {

        @Override
        public int compareTo(T o) {
            return 0;
        }

    }

    HashOpenAdressing() {
        this(2039); // initial size as random prime number
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.deletedElem = new Deleted();
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && hashElems[hashId] != newElem) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        if (hashElems[hashId] != newElem) {
            nElems++;
        }

        hashElems[hashId] = newElem;
    }

    @Override
    public T get(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException("Cannot serch for null!");
        }
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        while (hashElems[hashId] != nil || hashElems[hashId] instanceof HashOpenAdressing.Deleted) {
            if (hashElems[hashId].equals(elem)) {
                return elem;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
        return null;
    }

    @Override
    public void delete(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException("Cannot delete null!");
        }
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        while (hashElems[hashId] != nil || hashElems[hashId] instanceof HashOpenAdressing.Deleted) {
            if (hashElems[hashId].equals(elem)) {
                hashElems[hashId] = (T) deletedElem;
                return;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) (nElems + 1) / size;
    }

    private void doubleResize() {
        T[] oldHashElems = this.hashElems.clone();
        this.size *= 2;
        T[] newHashElems = (T[]) new Comparable[this.size];
        this.nElems = 0;
        this.hashElems = newHashElems;
        for (T t : oldHashElems) {
            if (t != null && !(t instanceof HashOpenAdressing.Deleted)) {
                this.put(t);
            }
        }
    }

}