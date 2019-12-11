package org.dnsge.pswatcher;

import org.dnsge.powerschoolapi.client.PowerschoolClient;
import org.dnsge.powerschoolapi.detail.Course;
import org.dnsge.powerschoolapi.user.User;
import org.dnsge.pswatcher.history.Change;
import org.dnsge.pswatcher.history.ChangeType;
import org.dnsge.pswatcher.history.ListHistory;
import org.dnsge.pswatcher.history.Modification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class that checks PowerSchool on regular interval
 */
public class TimedChecker {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> handle;

    /**
     * Schedule grade monitoring for every so often
     *
     * @param user    PowerSchool User object
     * @param client  PowerSchool client to use for requests
     * @param actor   Actor to consume modifications
     * @param seconds Seconds between checking
     */
    public static void beginChecking(User user, PowerschoolClient client, Actor<List<Modification<Course>>> actor,
                                     int seconds) {
        if (handle != null) throw new IllegalStateException("Handle already bound. Call stop before starting again.");
        List<Course> courses = copyList(user.getCourses());
        ListHistory<Course> history = new ListHistory<>(courses);

        final Runnable refreshUser = () -> {
            try {
                client.refreshUser(user);
                List<Modification<Course>> modifications = findModifications(history, user.getCourses());
                history.updateHistory(user.getCourses());
                actor.actOn(modifications); // act upon changes
            } catch (Exception e) {
                MiniLogger.err(e);
            }
        };

        handle = scheduler.scheduleAtFixedRate(refreshUser, 0, seconds, TimeUnit.SECONDS);
    }

    protected static void stop() {
        if (handle == null) return;
        handle.cancel(true);
        handle = null;
    }

    /**
     * Find modifications from last check of PowerSchool
     *
     * @param history History object
     * @param now     New course list
     * @return List of modifications
     */
    private static List<Modification<Course>> findModifications(ListHistory<Course> history, List<Course> now) {
        List<Change<Course>> changes = history.changes(now);
        List<String> changedIdentifiers = changes.stream()
                .map(change -> change.getValue().courseIdentifier())
                .collect(Collectors.toList());

        List<Modification<Course>> modifications = new ArrayList<>();

        for (int i = 0; i < changedIdentifiers.size(); i++) {
            int indexOf = listIndexOfExceptFor(changedIdentifiers, changedIdentifiers.get(i), i);
            if (indexOf != -1) {
                Change<Course> one = changes.get(i);
                Change<Course> two = changes.get(indexOf);

                if (one.getType() == ChangeType.ADD) {
                    Modification<Course> modification = new Modification<>(two.getValue(), one.getValue());

                    if (!modifications.contains(modification))
                        modifications.add(modification);
                } else if (one.getType() == ChangeType.REMOVE) {
                    Modification<Course> modification = new Modification<>(one.getValue(), two.getValue());

                    if (!modifications.contains(modification))
                        modifications.add(modification);
                }
            }
        }

        return modifications;
    }

    private static <E> int listIndexOfExceptFor(List<E> list, E item, int exclude) {
        for (int i = 0; i < list.size(); i++) {
            if (i != exclude && list.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    private static <E> List<E> copyList(List<E> list) {
        return new ArrayList<>(list);
    }

}
