package pl.edu.pw.ee.services;

public interface HashTable<T extends Comparable<T>> {

    void add(T value);

    T get(T value);

    void remove(T value);

}