package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class KruskalAlgorithmTest {
    @Test
    public void correct_small_dataTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        String expected = "B_1_C|C_1_D|A_3_B|D_7_E";
        assertEquals(expected, ka.findMST("files\\correct_small_data.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPathTest(){
        KruskalAlgorithm ka = new KruskalAlgorithm();
        ka.findMST(null);

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void empty_dataTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\empty_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_node_name_dataTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\incorrect_node_name.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_weight_dataTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\incorrect_weight_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrect_weight_data2Test() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\incorrect_weight_data2.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_connectedTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\not_connected.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectPathTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("flies\\incorrect_weight_data.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missing_node_nameTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\missing_node_name.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missing_weightTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\missing_weight.txt"));

        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void graph_with_loopTest() {
        KruskalAlgorithm ka = new KruskalAlgorithm();
        System.out.println(ka.findMST("files\\graph_with_loop.txt"));

        fail();
    }
}
