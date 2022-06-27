package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class HuffmanTest {

    @Test
    public void niemanieTest() throws IOException {
        Huffman huffman = new Huffman();

        huffman.huffman("testFiles/niemanie/", true);
        huffman.huffman("testFiles/niemanie/", false);
        if (!decompressedCorrectly("testFiles/niemanie/")) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDirTest() throws FileNotFoundException, IOException {
        Huffman huffman = new Huffman();

        assertEquals(-1, huffman.huffman(null, true));
        assertEquals(-1, huffman.huffman(null, false));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongDirNameTest() throws FileNotFoundException, IOException {
        Huffman huffman = new Huffman();

        assertEquals(0, huffman.huffman("test_wrong/", true));
        assertEquals(0, huffman.huffman("test_files", false));

        fail();
    }

    @Test
    public void oneCharInFileTest() throws FileNotFoundException, IOException {
        Huffman huffman = new Huffman();
        String dir = "testFiles/oneChar/";

        huffman.huffman(dir, true);
        huffman.huffman(dir, false);
        if (!decompressedCorrectly(dir)) {
            fail();
        }
    }

    @Test
    public void emptyFileTest() throws FileNotFoundException, IOException {
        Huffman huffman = new Huffman();
        String dir = "testFiles/emptyFile/";

        huffman.huffman(dir, true);
        huffman.huffman(dir, false);
        if (!decompressedCorrectly(dir)) {
            fail();
        }
    }

    private boolean decompressedCorrectly(String dir) throws IOException {
        Path file1 = Path.of(dir + "input.txt");
        Path file2 = Path.of(dir + "output.txt");

        try {
            String s1 = Files.readString(file1);
            String s2 = Files.readString(file2);
            return s1.equals(s2);

        } catch (IOException e) {
            return false;
        }
    }

}
