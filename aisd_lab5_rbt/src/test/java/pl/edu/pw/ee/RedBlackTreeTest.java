package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {

    private RedBlackTree<Character, Integer> tree = new RedBlackTree<>();

    @Before
    public void init() {
        tree = new RedBlackTree<>();
    }

    @Test
    public void addNewElemTest() {
        int numOfElemBefore = tree.getNElems();
        tree.put('a', 1);
        int numOfElemAfter = tree.getNElems();

        Assert.assertEquals(0, numOfElemBefore);
        Assert.assertEquals(1, numOfElemAfter);
    }

    @Test
    public void addDuplicateTest() {
        int numOfElemBefore = tree.getNElems();
        tree.put('a', 1);
        tree.put('a', 2);
        int numOfElemAfter = tree.getNElems();

        Assert.assertEquals(0, numOfElemBefore);
        Assert.assertEquals(1, numOfElemAfter);
        Assert.assertEquals(2, (int) tree.get('a'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullValueTest() {
        tree.put('a', null);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullKeyTest() {
        tree.put(null, 5);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullKeyAndValueTest() {
        tree.put(null, null);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullKeyTest() {
        tree.get(null);

        assert false;
    }

    @Test
    public void getElemNotInTreeTest() {
        Assert.assertNull(tree.get('b'));
    }

    @Test
    public void getValueTest() {
        for (char c = 'a'; c <= 'f'; c++) {
            tree.put(c, 5);
        }

        Assert.assertEquals(5, (int) tree.get('a'));
        Assert.assertEquals(5, (int) tree.get('c'));
    }

    @Test
    public void deleteMaxWithOneElemTreeTest() {
        tree.put('z', 1);

        tree.deleteMax();

        Assert.assertNull(tree.get('z'));
    }

    @Test
    public void deleteMaxTest() {
        for (char c = 'a'; c <= 'f'; c++) {
            tree.put(c, 5);
        }

        tree.deleteMax();

        Assert.assertNull(tree.get('f'));
    }

    @Test
    public void deleteMaxInEmptyTreeTest() {
        tree.deleteMax();
    }

    @Test
    public void preOrderTest() {
        for (char c = 'a'; c <= 'e'; c++) {
            tree.put(c, 1);
        }

        Assert.assertEquals("d:1 b:1 a:1 c:1 e:1", tree.getPreOrder());
    }

    @Test
    public void inOrderTest() {
        for (char c = 'a'; c <= 'e'; c++) {
            tree.put(c, 1);
        }

        Assert.assertEquals("a:1 b:1 c:1 d:1 e:1", tree.getInOrder());
    }

    @Test
    public void postOrderTest() {
        for (char c = 'a'; c <= 'e'; c++) {
            tree.put(c, 1);
        }

        Assert.assertEquals("a:1 c:1 b:1 e:1 d:1", tree.getPostOrder());
    }

    @Test
    public void recursionPutTest() throws IOException {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("WordList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (int i = 1; i <= 10; i++) {
                    wordList.add(line + i);
                }
            }
        }
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        FileWriter resultsSorted = new FileWriter("Sorted.txt");
        Collections.sort(wordList);
        int n = 1;
        for (String word : wordList) {
            tree.put(word, word);
            resultsSorted.append(n + " " + tree.getRecursionCounter() + "\n");
            tree.resetRecursionCounter();
            n++;
        }
        resultsSorted.close();

        tree = new RedBlackTree<>();
        FileWriter resultsReversed = new FileWriter("Reversed.txt");
        Collections.sort(wordList);
        Collections.reverse(wordList);
        n = 1;
        for (String word : wordList) {
            tree.put(word, word);
            resultsReversed.append(n + " " + tree.getRecursionCounter() + "\n");
            tree.resetRecursionCounter();
            n++;
        }
        resultsReversed.close();

        tree = new RedBlackTree<>();
        FileWriter resultsShuffled = new FileWriter("Shuffled.txt");
        Collections.shuffle(wordList);
        n = 1;
        for (String word : wordList) {
            tree.put(word, word);
            resultsShuffled.append(n + " " + tree.getRecursionCounter() + "\n");
            tree.resetRecursionCounter();
            n++;
        }
        resultsShuffled.close();
    }
}
