package pl.edu.pw.ee;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class PrimAlgorithmTest {
    @Test
    public void correct_small_dataTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        String expected = "A_3_B|B_1_C|C_1_D|D_7_E";
        assertEquals(expected, pa.findMST("files\\correct_small_data.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPathTest(){
        PrimAlgorithm pa = new PrimAlgorithm();
        pa.findMST(null);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void empty_dataTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\empty_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_node_name_dataTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\incorrect_node_name.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_weight_dataTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\incorrect_weight_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_weight_data2Test() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\incorrect_weight_data2.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_connectedTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\not_connected.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectPathTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("flies\\correct_small_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missing_node_nameTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\missing_node_name.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missing_weightTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\missing_weight.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void graph_with_loopTest() {
        PrimAlgorithm pa = new PrimAlgorithm();
        System.out.println(pa.findMST("files\\graph_with_loop.txt"));

        fail();
    }
}
