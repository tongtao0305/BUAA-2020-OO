package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MyHeapTest {

    private MyHeap myHeapTest;

    @Before
    public void setUp() throws Exception {
        System.out.println("---- Begin Test ----");
        myHeapTest = new MyHeap(8);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("---- End Test ----");
    }

    @Test
    public void heapSizeFor() {
    }

    @Test
    public void clear() {
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        assertEquals(2,myHeapTest.getSize());
        myHeapTest.clear();
        assertEquals(0,myHeapTest.getSize());
    }

    @Test
    public void add() {
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        System.out.println(Arrays.toString(myHeapTest.getElementData()));
    }

    @Test
    public void removeFirst() {
        myHeapTest.add(new MyObject());
        myHeapTest.add(new MyObject());
        assertEquals(2,myHeapTest.getSize());
        myHeapTest.removeFirst();
        assertEquals(1,myHeapTest.getSize());
        myHeapTest.removeFirst();
        assertEquals(0,myHeapTest.getSize());
    }
}