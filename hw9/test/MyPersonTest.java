import com.oocourse.spec1.main.Person;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.math.BigInteger;

/**
 * MyPerson Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>4�� 23, 2020</pre>
 */
public class MyPersonTest {

    private static Person person1;
    private static Person person2;

    @BeforeClass
    public static void beforeClass(){
        person1 = new MyPerson(1,"a",BigInteger.valueOf(100),10);
        person2 = new MyPerson(2,"b",BigInteger.valueOf(200),20);
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getId()
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(1, person1.getId());
        assertEquals(2, person2.getId());
    }

    /**
     * Method: getName()
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals("a", person1.getName());
        assertEquals("b", person2.getName());
    }

    /**
     * Method: getCharacter()
     */
    @Test
    public void testGetCharacter() throws Exception {
        assertEquals(BigInteger.valueOf(100), person1.getCharacter());
        assertEquals(BigInteger.valueOf(200), person2.getCharacter());
    }

    /**
     * Method: getAge()
     */
    @Test
    public void testGetAge() throws Exception {
        assertEquals(10, person1.getAge());
        assertEquals(20, person2.getAge());
    }

    /**
     * Method: equals(Object obj)
     */
    @Test
    public void testEquals() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isLinked(Person person)
     */
    @Test
    public void testIsLinked() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryValue(Person person)
     */
    @Test
    public void testQueryValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAcquaintanceSum()
     */
    @Test
    public void testGetAcquaintanceSum() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: compareTo(Person p2)
     */
    @Test
    public void testCompareTo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addAcquaintance(Person person, int value)
     */
    @Test
    public void testAddAcquaintance() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAcquaintance()
     */
    @Test
    public void testGetAcquaintance() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getFriends()
     */
    @Test
    public void testGetFriends() throws Exception {
//TODO: Test goes here... 
    }


} 
