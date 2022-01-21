package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class JVMHeapTest {

    private JVMHeap<MyObject> myJVMHeapTest;

    @Before
    public void setUp() throws Exception {
        System.out.println("---- Begin Test ----");
        ArrayList<MyObject> myObjects = new ArrayList<>();
        myObjects.add(new MyObject());
        myObjects.add(new MyObject());
        myObjects.add(new MyObject());
        myObjects.add(new MyObject());
        myObjects.add(new MyObject());
        myObjects.add(new MyObject());
        myJVMHeapTest = new JVMHeap<>(myObjects);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("---- End Test ----");
    }

    @Test
    public void setUnreferenceId() {
        Integer[] i = {0 ,1, 2};
        myJVMHeapTest.setUnreferenceId(Arrays.asList(i));
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[0]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[1]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[2]).getReferenced());
        assertTrue(((MyObject) myJVMHeapTest.getElementData()[3]).getReferenced());
        assertTrue(((MyObject) myJVMHeapTest.getElementData()[4]).getReferenced());
        assertTrue(((MyObject) myJVMHeapTest.getElementData()[5]).getReferenced());

        Integer[] j = {4,5};
        myJVMHeapTest.setUnreferenceId(Arrays.asList(j));
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[0]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[1]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[2]).getReferenced());
        assertTrue(((MyObject) myJVMHeapTest.getElementData()[3]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[4]).getReferenced());
        assertFalse(((MyObject) myJVMHeapTest.getElementData()[5]).getReferenced());
    }

    @Test
    public void removeUnreference() {
        Integer[] i = {0 ,1, 2};
        myJVMHeapTest.setUnreferenceId(Arrays.asList(i));
        assertEquals(3,myJVMHeapTest.getSize());
        Integer[] j = {4,5};
        myJVMHeapTest.setUnreferenceId(Arrays.asList(j));
        assertEquals(1,myJVMHeapTest.getSize());
    }
}