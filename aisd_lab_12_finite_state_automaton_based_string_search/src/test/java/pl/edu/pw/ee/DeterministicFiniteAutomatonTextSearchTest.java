package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

public class DeterministicFiniteAutomatonTextSearchTest {

    @Test
    public void findFirstPatternInTheBeginningTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("dabcb");

        assertEquals(0, dfats.findFirst("dabcbabc"));
    }

    @Test
    public void findFirstPatternInTheMidleTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("abcb");

        assertEquals(1, dfats.findFirst("dabcbabc"));
    }

    @Test
    public void findFirstPatternInTheEndTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("abcx");

        assertEquals(5, dfats.findFirst("dabcbabcx"));
    }

    @Test
    public void findFirstWhenPatternIsNotInText() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("abcb");

        assertEquals(-1, dfats.findFirst("xxxxxxxxx"));
    }

    @Test
    public void findFirstPatternWhenOnlyPartOfThePatternIsInTextTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("abcde");

        assertEquals(-1, dfats.findFirst("abcdf"));
    }

    @Test
    public void findAllTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("aba");
        int[] expected = { 0, 2, 4, 6 };

        int[] result = dfats.findAll("ababababa");

        for (int i = 0; i < result.length; i++) {
            if (expected[i] != result[i]) {
                fail();
            }
        }
    }

    @Test
    public void findAllTest2() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("aaa");
        int[] expected = { 0, 1, 2, 3, 4, 5 };

        int[] result = dfats.findAll("aaaaaaaa");
        
        for (int i = 0; i < result.length; i++) {
            if (expected[i] != result[i]) {
                fail();
            }
        }
    }

    @Test
    public void findAllWhenPatternIsNotInTextTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("aba");
        int[] result = dfats.findAll("xxxxxxxxxxx");

        assertEquals(0, result.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPaternTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch(null);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTextTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("null");
        dfats.findFirst(null);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPatternTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("");

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTextTest() {
        DeterministicFiniteAutomatonTextSearch dfats = new DeterministicFiniteAutomatonTextSearch("null");
        dfats.findFirst("");

        fail();
    }
}
