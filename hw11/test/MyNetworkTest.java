import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.main.Person;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyNetworkTest {

    private MyNetwork network;

    @BeforeEach
    void setUp() {
        System.out.println("----- Test Begin -----");
        network = new MyNetwork();
    }

    @AfterEach
    void tearDown() {
        System.out.println("----- Test End -----");
    }

    @Test
    void queryMinPath() throws EqualPersonIdException, PersonIdNotFoundException, EqualRelationException {
        for (int i = 1; i <= 10000; i++) {
            network.addPerson(new MyPerson(i, "name", BigInteger.ONE, 1));
        }

        network.addRelation(1, 2, 100);
        assertEquals(100, network.queryMinPath(1, 2));

        network.addRelation(1, 1000, 250);
        network.addRelation(2, 1000, 100);
        assertEquals(200, network.queryMinPath(1, 1000));

        network.addRelation(1, 3, 40);
        network.addRelation(2, 3, 50);
        assertEquals(90, network.queryMinPath(1, 2));

//        network.addRelation(1, 2, 101);
//        network.addRelation(1, 3, 201);
//        assertEquals(101, network.queryMinPath(1, 2));
//        assertEquals(201, network.queryMinPath(1, 3));
//
//        for (int i = 2; i < 1000; i = i + 2) {
//            network.addRelation(i, i + 2, 1);
//        }
//        for (int i = 3; i < 999; i = i + 2) {
//            network.addRelation(i, i + 2, 1);
//        }
//        network.addRelation(999, 1000, 1);
//        assertEquals(600, network.queryMinPath(1, 1000));


    }

    @Test
    void queryStrongLinked() throws EqualPersonIdException, PersonIdNotFoundException, EqualRelationException {
//        for (int i = 1; i <= 100; i++) {
//            network.addPerson(new MyPerson(i, "name", BigInteger.ONE, 1));
//        }
//
//        network.addRelation(1, 2, 1);
//        assertEquals(false, network.queryStrongLinked(1, 2));
//
//        network.addRelation(2, 3, 1);
//        network.addRelation(3, 4, 1);
//        network.addRelation(4, 5, 1);
//        network.addRelation(5, 6, 1);
//        network.addRelation(6, 7, 1);
//        network.addRelation(7, 8, 1);
//        network.addRelation(8, 9, 1);
//        assertEquals(false, network.queryStrongLinked(1, 2));
//
//        network.addRelation(9, 1, 1);
//        assertEquals(true, network.queryStrongLinked(1, 2));

        ArrayList<Integer> nums1 = new ArrayList<>();
        ArrayList<Integer> nums2 = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            network.addPerson(new MyPerson(i, "name", BigInteger.ONE, 1));
        }

        network.addRelation(1, 2, 1);
        //assertEquals(false, network.queryStrongLinked(1, 2));

        for (int i = 1; i < 500; i = i + 2) {
            nums1.add(i);
            network.addRelation(i, i + 2, 1);
        }
        for (int i = 502; i < 1000; i = i + 2) {
            nums1.add(i);
            network.addRelation(i, i + 2, 1);
        }

        network.addRelation(501, 502, 1);
        network.addRelation(2, 1000, 1);
        assertEquals(true, network.queryStrongLinked(1, 1000));

        nums1.add(2);
        nums1.add(501);
        nums1.add(1000);

        for (int i = 1; i <= 1000; i++) {
            System.out.println(i);
            assertEquals(nums1.contains(i), network.queryStrongLinked(1, i));
        }

        for (int i = 1; i <= 1000; i++) {
            if (!nums1.contains(i)) {
                nums2.add(i);

                if (nums2.size() > 2) {
                    network.addRelation(nums2.get(nums2.size() - 2), i, 1);
                } else {
                    network.addRelation(1000, i, 1);
                }
            }
        }

        for (Integer i : nums2) {
            assertEquals(false, network.queryStrongLinked(1, i));
        }


        for (int i = 1005; i <= 10000; i = i + 2) {
            network.addRelation(i - 4, i, 0);
        }

        for (int i = 1005; i <= 10000; i = i + 2) {
            assertEquals(false, network.queryStrongLinked(1, i));
        }

        network.addRelation(1001, 1, 1);
        network.addRelation(9997, 1, 1);
        for (int i = 1005; i <= 10000; i = i + 2) {
            if (i % 4 == 1) {
                assertEquals(true, network.queryStrongLinked(1, i));
            } else {
                assertEquals(false, network.queryStrongLinked(1, i));
            }
        }

        network.addRelation(1002, 1, 1);
        network.addRelation(9998, 1, 1);
        for (int i = 1005; i <= 10000; i = i + 2) {
            if (i % 4 == 1 || i % 4 == 2) {
                assertEquals(true, network.queryStrongLinked(1, i));
            } else {
                assertEquals(false, network.queryStrongLinked(1, i));
            }
        }

        network.addRelation(1003, 1, 1);
        network.addRelation(9999, 1, 1);
        for (int i = 1005; i <= 10000; i = i + 2) {
            if (i % 4 == 1 || i % 4 == 2 || i % 4 == 3) {
                assertEquals(true, network.queryStrongLinked(1, i));
            } else {
                assertEquals(false, network.queryStrongLinked(1, i));
            }
        }

        network.addRelation(1004, 1, 1);
        network.addRelation(10000, 1, 1);
        for (int i = 1005; i <= 10000; i = i + 2) {
            assertEquals(true, network.queryStrongLinked(1, i));
        }
    }

    @Test
    void queryBlockSum() throws EqualPersonIdException, PersonIdNotFoundException, EqualRelationException {

        for (int i = 1; i <= 1000; i++) {
            network.addPerson(new MyPerson(i, "name", BigInteger.ONE, 1));
        }

        assertEquals(1000, network.queryBlockSum());

        for (int i = 2; i <= 500; i += 2) {
            network.addRelation(1, i, 1);
        }
        assertEquals(750, network.queryBlockSum());

        for (int i = 503; i <= 999; i += 2) {
            network.addRelation(501, i, 2);
        }

        assertEquals(501, network.queryBlockSum());

        network.addRelation(1, 501, 3);

        assertEquals(500, network.queryBlockSum());
    }
}