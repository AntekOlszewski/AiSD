package pl.edu.pw.ee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RbtMapTest {

    private RbtMap<String, Integer> rbtMap;

    @Before
    public void setUp() {
        rbtMap = new RbtMap<>();
    }

    @Test
    public void addElemTest() {
        int numOfElemsBeforeAdding = rbtMap.getNElems();
        rbtMap.setValue("elem", 1);
        int numOfElemsAfterAdding = rbtMap.getNElems();

        Assert.assertEquals(0, numOfElemsBeforeAdding);
        Assert.assertEquals(1, numOfElemsAfterAdding);
    }

    @Test
    public void addDuplicateTest() {
        String key = "elem";

        int numOfElemsBeforeAdding = rbtMap.getNElems();
        rbtMap.setValue(key, 1);
        rbtMap.setValue(key, 2);
        int numOfElemsAfterAdding = rbtMap.getNElems();

        int expected = rbtMap.getValue(key);
        Assert.assertEquals(0, numOfElemsBeforeAdding);
        Assert.assertEquals(1, numOfElemsAfterAdding);
        Assert.assertEquals(2, expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullKeyTest() {
        rbtMap.setValue(null, 0);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullValueTest() {
        rbtMap.setValue("something", null);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void addElemWithNullKeyAndValueTest() {
        rbtMap.setValue(null, null);

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullTest() {
        rbtMap.getValue(null);

        assert false;
    }

    @Test
    public void getElemNotInMap() {
        Assert.assertNull(rbtMap.getValue("elem2"));
    }

    @Test
    public void getElemTest() {
        rbtMap.setValue("elem1", 1);
        rbtMap.setValue("elem2", 2);
        rbtMap.setValue("elem3", 3);

        int expected = rbtMap.getValue("elem1");
        Assert.assertEquals(1, expected);
    }

    @Test
    public void deleteMaxInMapWith1ElemTest() {
        String key = "elem";

        rbtMap.setValue(key, 1);

        rbtMap.deleteMax();

        Assert.assertNull(rbtMap.getValue(key));
    }

    @Test
    public void deleteMaxTest() {
        for (int i = 0; i < 10; i++) {
            rbtMap.setValue("elem" + i, i);
        }

        rbtMap.deleteMax();

        Assert.assertNull(rbtMap.getValue("elem9"));
    }

}
