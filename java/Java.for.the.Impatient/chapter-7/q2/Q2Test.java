package impatient.chapter7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Kevin Orr
 * Some tests for Q1
 * Maybe should have used Spock or Kotlin for the tests...
 */
public class Q2Test {

    Q2 q2;

    @Before
    public void setUp() throws Exception {
        q2 = new Q2();
    }

    @After
    public void tearDown() throws Exception {
        q2 = null;
    }

    @Test
    public void withAnIterator() {

        //given
        List<String> list = Arrays.asList("hello", "there", "world");
        //when
        List<String> upper = q2.iterator(list);
        //then
        assertTrue(upper.equals(Arrays.asList("HELLO", "THERE", "WORLD")));
    }

    @Test
    public void withLoop() {

        //given
        List<String> list = Arrays.asList("hello", "there", "world");
        //when
        List<String> upper = q2.loop(list);
        //then
        assertTrue(upper.equals(Arrays.asList("HELLO", "THERE", "WORLD")));
    }

    @Test
    public void withReplaceAll() {

        //given
        List<String> list = Arrays.asList("hello", "there", "world");
        //when
        List<String> upper = q2.replaceAll(list);
        //then
        assertTrue(upper.equals(Arrays.asList("HELLO", "THERE", "WORLD")));
    }

}