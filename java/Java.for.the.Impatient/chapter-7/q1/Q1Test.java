package impatient.chapter7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Kevin Orr
 * Some tests for Q1
 * Maybe should have used Spock or Kotlin for the tests...
 */
public class Q1Test {

    Q1 q1;

    @Before
    public void setUp() throws Exception {
        q1 = new Q1();
    }

    @After
    public void tearDown() throws Exception {
        q1 = null;
    }

    @Test
    public void primesBelow10() {

        //given
        int n = 10;
        //when
        Set<Integer> primes = q1.primesFrom2To(n);
        //then
        assertThat(primes, hasItems(2, 3, 5, 7));
        assertThat(primes, not(hasItems(4, 6, 8)));
        assertTrue(primes.size()==4);
    }

    @Test
    public void primesBelow3() {

        //given
        int n = 3;
        //when
        Set<Integer> primes = q1.primesFrom2To(n);
        //then
        assertThat(primes, hasItems(2,3));
        assertTrue(primes.size()==2);
    }

    @Test
    public void primesBelow30() {

        //given
        int n = 30;
        //when
        Set<Integer> primes = q1.primesFrom2To(n);
        //then
        assertThat(primes, hasItems(2, 3, 5, 7, 11, 13, 17, 19, 23, 29));
        assertThat(primes, not(hasItems(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30 )));
        assertTrue(primes.size()==10);
    }

    @Test
    public void expectOnly2WhenNIs2() {

        //given
        int n = 2;
        //when
        Set<Integer> primes = q1.primesFrom2To(n);
        //then
        assertThat(primes, hasItem(2));
        assertTrue(primes.size()==1);
    }
}