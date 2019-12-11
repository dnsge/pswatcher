package org.dnsge.pswatcher.history;

import java.util.Objects;

/**
 * Class that encapsulates a change in state of an Object
 *
 * @param <T> Type of object being recorded
 */
public class Modification<T> {

    private T from;
    private T to;

    public Modification(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public T from() {
        return from;
    }

    public T to() {
        return to;
    }

    @Override
    public String toString() {
        return "Modification{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modification<?> that = (Modification<?>) o;
        return from().equals(that.from()) &&
                to().equals(that.to());
    }

    @Override
    public int hashCode() {
        return Objects.hash(from(), to());
    }

}
