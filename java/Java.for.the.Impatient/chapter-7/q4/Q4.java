package impatient.chapter7;

import java.util.*;

/**
 * In an array list of strings, make each string uppercase.
 * Do this with
 * (a) an iterator,
 * (b) a loop over the index values, and
 * (c) the replaceAll method
 *
 */
public class Q4 {
    public Q4() {}

    public List<String> bang() {
        // create a list with a couple of items
        List<String> list = new ArrayList<>(Arrays.asList("ho", "ho!"));
        // next get an iterator for the list
        Iterator<String> it = list.iterator();
        // next remove an item from original list - iterator doesn't now we're about to remove item
        list.remove(0);
        // now just iterate over the what the iterator 'thinks' is the original 2 itemed list
        while (it.hasNext()) {
            // we should go back on one of these calls
            it.next();
        }
        // we're not even gonna get this far...
        return list;
    }

}
