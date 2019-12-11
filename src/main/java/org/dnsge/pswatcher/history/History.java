package org.dnsge.pswatcher.history;

/**
 * Interface that specifies the monitoring of an object over time
 *
 * @param <T> Type of object being monitored
 */
public interface History<T> {

    void updateHistory(T newObj);
    boolean differentFrom(T newObj);

}
