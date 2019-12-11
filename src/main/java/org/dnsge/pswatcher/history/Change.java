package org.dnsge.pswatcher.history;

import java.util.Objects;

/**
 * Representation of a change of object states
 *
 * @param <T> Type of object
 */
public class Change<T> {

    private final ChangeType type;
    private final T value;

    /**
     * Create new Change object
     *
     * @param type  Type of change in the object
     * @param value Final state of object
     */
    public Change(ChangeType type, T value) {
        this.type = type;
        this.value = value;
    }

    public ChangeType getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Change<?> change = (Change<?>) o;
        return getType() == change.getType() &&
                getValue().equals(change.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue());
    }

    @Override
    public String toString() {
        return "Change{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
