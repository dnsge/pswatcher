package org.dnsge.pswatcher.history;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that monitors the history of a List.
 * Objects in list must implement a correct hashCode method
 *
 * @param <E> Element type of list being monitored
 */
public class ListHistory<E> implements History<List<E>> {

    private List<E> list;
    private int listHash;

    public ListHistory(List<E> lst) {
        updateHistory(lst);
    }

    /**
     * Load new object state
     *
     * @param newObj New object
     */
    public void updateHistory(List<E> newObj) {
        this.list = copyList(newObj);
        this.listHash = newObj.hashCode();
    }

    /**
     * Check whether object differs from stored object state
     *
     * @param newObj Object to compare stored state to
     * @return Whether the passed state is different
     */
    public boolean differentFrom(List<E> newObj) {
        return listHash != newObj.hashCode();
    }

    /**
     * Get a list of changes in elements within the stored List state from a new List state
     *
     * @param newList New list state to compare to
     * @return List of Change Objects
     */
    public List<Change<E>> changes(List<E> newList) {
        if (!differentFrom(newList)) { // Not different, so skip check
            return new ArrayList<>();
        }

        List<Change<E>> changesList = new ArrayList<>();

        for (E newItem : newList) {
            if (!list.contains(newItem)) {
                changesList.add(new Change<>(ChangeType.ADD, newItem));
            }
        }

        for (E oldItem : list) {
            if (!newList.contains(oldItem)) {
                changesList.add(new Change<>(ChangeType.REMOVE, oldItem));
            }
        }

        return changesList;
    }

    public List<E> getList() {
        return list;
    }

    private static <E> List<E> copyList(List<E> list) {
        return new ArrayList<>(list);
    }

}
