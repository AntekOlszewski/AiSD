package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingTest {
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> hash = new HashLinearProbing<>(initialSize);

        // then
        assert false;
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashLinearProbing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void addDuplicateTest() {
        HashTable<String> emptyHash = new HashLinearProbing<>();
        String newEleme = "nothing special";

        emptyHash.put(newEleme);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        assertEquals(1, nOfElemsAfterPut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInputTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.put(null);
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.get(null);
        assert false;
    }

    @Test
    public void putAndGetTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        int input = 1;
        hash.put(input);
        int output = hash.get(input);
        assertEquals(input, output);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNullTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.delete(null);
        assert false;
    }

    @Test
    public void deleteTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.put(1);
        hash.delete(1);
        assertEquals(null, hash.get(1));
    }

    @Test
    public void searchAfterDeletionTest() {
        HashTable<Integer> hash = new HashLinearProbing<>(10);
        hash.put(1);
        hash.put(11);
        hash.delete(1);
        int output = hash.get(11);
        assertEquals(11, output);
    }

    @Test
    public void searchAfterResizeTest() {
        HashTable<Integer> hash = new HashLinearProbing<>(2);
        hash.put(1);
        hash.put(11);
        hash.put(111);
        int output = hash.get(11);
        assertEquals(11, output);
    }

    @Test
    public void deleteElemNotInHashTableTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.put(12);
        hash.delete(1);
        assertEquals(null, hash.get(1));
    }

    @Test
    public void getElemNOtInHashTableTest() {
        HashTable<Integer> hash = new HashLinearProbing<>();
        hash.put(12);
        assertEquals(null, hash.get(1));
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
        for (int i = 0; i < 10; i++) {
            List<Long> insertTimes = new ArrayList<>();
            List<Long> searchTimes = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                HashTable<String> hashTable = new HashLinearProbing<>(512 * (int) Math.pow(2, i));
                long iTimeStart = System.currentTimeMillis();
                for (String string : wordList) {
                    hashTable.put(string);
                }
                long iTimeEnd = System.currentTimeMillis() - iTimeStart;
                insertTimes.add(iTimeEnd);
                long sTimeStart = System.currentTimeMillis();
                for (String string : wordList) {
                    hashTable.get(string);
                }
                long sTimeEnd = System.currentTimeMillis() - sTimeStart;
                searchTimes.add(sTimeEnd);
            }
            System.out.println(getAverageTime(insertTimes) + " " + getAverageTime(searchTimes));
        }
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
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
