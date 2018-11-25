package impatient.chapter7;

import java.util.List;
import java.util.ListIterator;

/**
 * In an array list of strings, make each string uppercase.
 * Do this with
 * (a) an iterator,
 * (b) a loop over the index values, and
 * (c) the replaceAll method
 *
 */
public class Q2 {
    public Q2() {}

    public List<String> iterator(List<String> list) {
        ListIterator<String> it = list.listIterator();

        while (it.hasNext()) {
            it.set(it.next().toUpperCase());
        }

        return list;
    }

    public List<String> loop(List<String> list) {
        for (int index = 0; index < list.size(); index++)
            list.set(index, list.get(index).toUpperCase());
        return list;
    }

    public List<String> replaceAll(List<String> list) {
        list.replaceAll(String::toUpperCase);
        return list;
    }
}
