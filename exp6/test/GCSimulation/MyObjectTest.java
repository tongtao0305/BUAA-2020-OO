package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyObjectTest {

    private MyObject testObject1;
    private MyObject testObject2;

    @Before
    public void setUp() throws Exception {
        System.out.println("---- Begin Test ----");
        testObject1 = new MyObject();
        testObject2 = new MyObject();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("---- End Test ----");
    }

    @Test
    public void setAge() {
    }

    @Test
    public void getAge() {
    }

    @Test
    public void getId() {
    }

    @Test
    public void setReferenced() {
    }

    @Test
    public void getReferenced() {
    }

    @Test
    public void compareTo() {
    }
}