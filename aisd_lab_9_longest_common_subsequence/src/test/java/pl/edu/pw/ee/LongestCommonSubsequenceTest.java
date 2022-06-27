package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

public class LongestCommonSubsequenceTest {
    @Test
    public void komotyTest() {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence("lokomotywa", "kompoty");
        assertEquals("komoty", lcs.findLCS());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInputTest() {
        LongestCommonSubsequence lsc = new LongestCommonSubsequence(null, null);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringTest() {
        LongestCommonSubsequence lsc = new LongestCommonSubsequence("null", "");

        fail();
    }

    @Test
    public void twoLSCTest() {
        LongestCommonSubsequence lsc = new LongestCommonSubsequence("pierwszy_drugi123", "drugi123 pierwszy");
        System.out.println(lsc.findLCS());
        lsc.display();
    }

    @Test
    public void specialCharactersTest() {
        LongestCommonSubsequence lsc = new LongestCommonSubsequence("\t\b\r\f\nabc", "abc\n\t");
        lsc.display();
    }

    @Test
    public void displayTest() {
        LongestCommonSubsequence lsc = new LongestCommonSubsequence("często_z_odkrywaniem",
                "rzeczy_nie_trzeba\n_się_spieszyć");
        lsc.display();
    }
}
