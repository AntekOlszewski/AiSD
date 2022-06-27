package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class HashListChainingTest {

    @Test
    public void addAndGetValueTest() {
        HashListChaining<Integer> hashList = new HashListChaining<>(10);
        hashList.add(1);
        hashList.printHashTable();
        assertEquals((Integer) 1, hashList.get(1));
    }

    @Test
    public void removeValueTest() {
        HashListChaining<String> hashList = new HashListChaining<>(10);
        hashList.add("value");
        hashList.printHashTable();
        System.out.println(" ");
        hashList.remove("value");
        hashList.printHashTable();
        assertEquals(null, hashList.get("value"));
    }

    @Test
    public void addDuplicate() {
        HashListChaining<Character> hashList = new HashListChaining<>(10);
        hashList.add('x');
        hashList.printHashTable();
        System.out.println(" ");
        hashList.add('x');
        hashList.printHashTable();
        hashList.remove('x');
        assertEquals(null, hashList.get('x'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNull() {
        HashListChaining<Character> hashList = new HashListChaining<>(10);
        hashList.add(null);
    }

    @Test
    public void timeTest() throws IOException {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("WordList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
            }
        }
        for (int i = 1; i <= 7; i++) {
            HashListChaining<String> hashList = new HashListChaining<>(4096 * (int) Math.pow(2, i - 1));
            for (String string : wordList) {
                hashList.add(string);
            }
            List<Long> times = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                long timeStart = System.currentTimeMillis();
                for (String string : wordList) {
                    hashList.get(string);
                }
                long timeEnd = System.currentTimeMillis() - timeStart;
                times.add(timeEnd);
            }
            System.out.println(getAverageTime(times));
        }
    }

    private long getAverageTime(List<Long> times) {
        Collections.sort(times);
        long sum = 0;
        for (int i = 10; i < 20; i++) {
            sum += times.get(i);
        }
        return sum / 10;
    }
}