import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MyPersonTest {

    private MyPerson person;

    @BeforeEach
    void setUp() {
        System.out.println("----- Test Begin -----");
        person = new MyPerson(1,"a", BigInteger.ONE,1);
    }

    @AfterEach
    void tearDown() {
        System.out.println("----- Test End -----");
    }


    @Test
    void queryMinPath() {
    }

    @Test
    void queryStrongLinked() {
    }
}