import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.math.BigInteger;

/**
 * MyNetwork Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>4ÔÂ 23, 2020</pre>
 */
public class MyNetworkTest {

    Network network;

    @Before
    public void before() throws Exception {
        network = new MyNetwork();
    }

    @After
    public void after() throws Exception {
        network = null;
    }

    /**
     * Method: contains(int id)
     */
    @Test
    public void testContains() throws Exception {
        Person person = new MyPerson(1, "tt", BigInteger.valueOf(20000305), 20);
    }

    /**
     * Method: getPerson(int id)
     */
    @Test
    public void testGetPerson() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addPerson(Person person)
     */
    @Test
    public void testAddPerson() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addRelation(int id1, int id2, int value)
     */
    @Test
    public void testAddRelation() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryValue(int id1, int id2)
     */
    @Test
    public void testQueryValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryConflict(int id1, int id2)
     */
    @Test
    public void testQueryConflict() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryAcquaintanceSum(int id)
     */
    @Test
    public void testQueryAcquaintanceSum() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: compareAge(int id1, int id2)
     */
    @Test
    public void testCompareAge() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: compareName(int id1, int id2)
     */
    @Test
    public void testCompareName() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryPeopleSum()
     */
    @Test
    public void testQueryPeopleSum() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: queryNameRank(int id)
     */
    @Test
    public void testQueryNameRank() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isCircle(int id1, int id2)
     */
    @Test
    public void testIsCircle() throws Exception {
//TODO: Test goes here... 
    }


} 
