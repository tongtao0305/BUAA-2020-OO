package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyJVMTest {

    private MyJVM testJVM;

    @Before
    public void setUp() throws Exception {
        System.out.println("---- Begin Test ----");
        testJVM = new MyJVM();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("---- End Test ----");
    }

    @Test
    public void createObject() {
        testJVM.createObject(15);
    }

    @Test
    public void setUnreference() {
    }

    @Test
    public void minorGC() {
    }

    @Test
    public void majorGC() {
    }

    @Test
    public void getSnapShoot() {
    }

}