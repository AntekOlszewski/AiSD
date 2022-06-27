package pl.edu.pw.ee;

public class HashDoubleHashing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    public HashDoubleHashing() {
        super();
    }

    public HashDoubleHashing(int size) {
        super(size == 3 ? 6 : size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        int hash = (firstHash(key) + i * secondHash(key)) % m;
        hash = hash < 0 ? -hash : hash;
        return hash;
    }

    private int firstHash(int key) {
        int m = getSize();
        return key % m;
    }

    private int secondHash(int key) {
        int m = getSize();
        return 1 + 2 * (key % (m - 3));
    }
}