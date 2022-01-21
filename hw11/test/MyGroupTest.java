import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.main.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyGroupTest {

    private MyGroup myGroup;

    @BeforeEach
    void setUp() {
        System.out.println("----- Test Begin -----");
        myGroup = new MyGroup(1);
    }

    @AfterEach
    void tearDown() {
        System.out.println("----- Test End -----");
    }

    @Test
    void addPerson() {
    }

    @Test
    void delPerson() {
    }

    @Test
    void getRelationSum() throws EqualGroupIdException, PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException, EqualRelationException {
        MyNetwork network = new MyNetwork();
        MyGroup group1 = new MyGroup(1);
        MyGroup group2 = new MyGroup(2);
        MyPerson person1 = new MyPerson(1, "a", BigInteger.ONE, 1);
        MyPerson person2 = new MyPerson(2, "b", BigInteger.ONE, 1);
        MyPerson person3 = new MyPerson(3, "c", BigInteger.ONE, 1);
        MyPerson person4 = new MyPerson(4, "d", BigInteger.ONE, 1);
        MyPerson person5 = new MyPerson(5, "e", BigInteger.ONE, 1);
        network.addGroup(group1);
        network.addGroup(group2);
        network.addPerson(person1);
        network.addPerson(person2);
        network.addPerson(person3);
        network.addPerson(person4);
        network.addPerson(person5);

        // 初始化判断
        assertEquals(getRelationSum(group1.getPeople()), group1.getRelationSum());
        assertEquals(getRelationSum(group2.getPeople()), group2.getRelationSum());

        network.addtoGroup(1, 1);
        network.addtoGroup(2, 1);
        network.addRelation(1, 2, 300);
        network.addRelation(1, 3, 400);

        assertEquals(getRelationSum(group1.getPeople()), group1.getRelationSum());
        assertEquals(getRelationSum(group2.getPeople()), group2.getRelationSum());

        network.addtoGroup(3, 1);

        assertEquals(getRelationSum(group1.getPeople()), group1.getRelationSum());
        assertEquals(getRelationSum(group2.getPeople()), group2.getRelationSum());

        network.addtoGroup(4, 1);
        network.addtoGroup(5, 1);
        network.addRelation(1,4,500);
        network.addRelation(1,5,600);
        network.addRelation(3,4,700);
        network.addRelation(2,5,700);

        assertEquals(getRelationSum(group1.getPeople()), group1.getRelationSum());
        assertEquals(getRelationSum(group2.getPeople()), group2.getRelationSum());
    }

    @Test
    void getValueSum() throws EqualGroupIdException, EqualPersonIdException, PersonIdNotFoundException, EqualRelationException, GroupIdNotFoundException {
        MyNetwork network = new MyNetwork();
        MyGroup group1 = new MyGroup(1);
        MyGroup group2 = new MyGroup(2);
        MyPerson person1 = new MyPerson(1, "a", BigInteger.ONE, 1);
        MyPerson person2 = new MyPerson(2, "b", BigInteger.ONE, 1);
        MyPerson person3 = new MyPerson(3, "c", BigInteger.ONE, 1);
        MyPerson person4 = new MyPerson(4, "d", BigInteger.ONE, 1);
        MyPerson person5 = new MyPerson(5, "e", BigInteger.ONE, 1);
        network.addGroup(group1);
        network.addGroup(group2);
        network.addPerson(person1);
        network.addPerson(person2);
        network.addPerson(person3);
        network.addPerson(person4);
        network.addPerson(person5);

        network.addtoGroup(1, 2);
        network.addtoGroup(2, 2);
        network.addtoGroup(3, 2);
        network.addtoGroup(4, 2);
        network.addtoGroup(5, 2);

        // 初始化判断
        assertEquals(getValueSum(group1.getPeople()), group1.getValueSum());
        assertEquals(getValueSum(group2.getPeople()), group2.getValueSum());

        network.addtoGroup(1, 1);
        network.addtoGroup(2, 1);
        network.addRelation(1, 2, 300);
        network.addRelation(1, 3, 400);

        assertEquals(getValueSum(group1.getPeople()), group1.getValueSum());
        assertEquals(getValueSum(group2.getPeople()), group2.getValueSum());

        network.addtoGroup(3, 1);

        assertEquals(getValueSum(group1.getPeople()), group1.getValueSum());
        assertEquals(getValueSum(group2.getPeople()), group2.getValueSum());

        network.addtoGroup(4, 1);
        network.addtoGroup(5, 1);
        network.addRelation(1,4,500);
        network.addRelation(1,5,600);
        network.addRelation(3,4,700);
        network.addRelation(2,5,700);

        assertEquals(getValueSum(group1.getPeople()), group1.getValueSum());
        assertEquals(getValueSum(group2.getPeople()), group2.getValueSum());
    }

    private int getRelationSum(ArrayList<Person> people) {
        int count = 0;

        for (Person person1 : people) {
            for (Person person2 : people) {
                if (person1.isLinked(person2)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int getValueSum(ArrayList<Person> people) {
        int count = 0;

        for (Person person1 : people) {
            for (Person person2 : people) {
                if (person1.isLinked(person2)) {
                    count += person1.queryValue(person2);
                }
            }
        }

        return count;
    }
}