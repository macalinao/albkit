package pw.ian.albkit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Collections;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Countdown extends BukkitRunnable {

    private final Map<Integer, String> messages;

    private final List<Integer> times;

    private final long start;

    private final int length;

    private int current = 0;

    /**
     * C'tor
     *
     * @param messages A map that takes in entries of seconds => message.
     */
    public Countdown(Map<Integer, String> messages) {
        this.messages = messages;

        times = new ArrayList<>(messages.keySet());
        Collections.sort(times);
        length = times.get(times.size() - 1);
        start = System.currentTimeMillis();
        current = times.size() - 1;
    }

    @Override
    public void run() {
        if (!checkCondition()) {
            cancel();
            return;
        }

        // Expect that the times are in length order
        int secsRemaining = length - (((int) (System.currentTimeMillis() - start)) / 1000);

        if (current < 0) {
            if (secsRemaining <= 0) {
                onEnd();
                cancel();
            }
        } else {
            int nextSecs = times.get(current);
            if (secsRemaining <= nextSecs) {
                onCheckpoint(nextSecs, messages.get(nextSecs));
                current--;
            }
        }
    }

    /**
     * A condition method that will be checked on each iteration of the timer.
     * If it returns false, the task will be cancelled.
     *
     * @return False if cancel
     */
    public boolean checkCondition() {
        return true;
    }

    /**
     * Called whenever a checkpoint is reached.
     *
     * @param seconds
     * @param message
     */
    public void onCheckpoint(int seconds, String message) {
    }

    /**
     * Called when the task ends. This isn't called if the timer breaks due to
     * the condition check.
     */
    public void onEnd() {
    }
}
