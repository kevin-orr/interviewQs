Implement the “Sieve of Erathostenes” algorithm to determine all prime numbers ≤
n. Add all numbers from 2 to n to a set. Then repeatedly find the smallest element s
in the set, and remove s2, s · (s + 1), s · (s + 2), and so on. You are done when s2 > n.
Do this with both a HashSet<Integer> and a BitSet.
