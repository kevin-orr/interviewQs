package impatient.chapter7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Kevin Orr
 * Some tests for Q4
 * Maybe should have used Spock or Kotlin for the tests...
 */
public class Q4Test {

    Q4 q4;

    @Before
    public void setUp() throws Exception {
        q4 = new Q4();
    }

    @After
    public void tearDown() throws Exception {
        q4 = null;
    }

    @Test(expected = ConcurrentModificationException.class)
    public void expectConcurrentModificationException() {

        //given
        //when
        List<String> upper = q4.bang();
        //then
        fail("We shouldn't have got this far - expecting an exception to be throw before now...");
    }

    @Test
    public void expectNoConcurrentModificationException() {

        //given
        //when
        List<String> upper = q4.noException();
        //then
        assertTrue(true);
    }
}