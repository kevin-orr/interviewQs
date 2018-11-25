package impatient.chapter7;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Kevin Orr
 * Implement the “Sieve of Erathostenes” algorithm to determine all prime numbers ≤ n.
 * Add all numbers from 2 to n to a set. Then repeatedly find the smallest element s
 * in the set, and remove s2, s · (s + 1), s · (s + 2), and so on. You are done when s2 > n.
 * Do this with both a HashSet<Integer> and a BitSet.
 */
public class Q1 {

    public Q1() {}

    public Set<Integer> primesFrom2To(int n) {
        Set<Integer> candidates = new HashSet<>(n);
        // fill up the collection with ints from 2...n
        for (int i = 2; i <= n; i++) candidates.add(i);

        int smallest = Collections.min(candidates);

        for (int i = 2; i <= n; i++) {
            // remove multiples of items left in set
            for (int factor = 0; (smallest * factor) < n; factor++){
                int dropThis = smallest*(smallest+factor);
                candidates.remove(dropThis);
            }
            // next find the smallest item not already used as smallest value
            for(smallest = smallest+1; smallest <= n; smallest++) {
                if (candidates.contains(smallest)) {
                    break;
                }
            }
        }
        return candidates;
    }
}

