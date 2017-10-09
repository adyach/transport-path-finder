package org.crazycoder.transport.network;

/**
 * Represents the abstraction for schedule of transport.
 * Possible implementations could hold the map of transport name to waiting time.
 */
public interface Timetable {

    /**
     * Returns waiting time until transport arrives
     *
     * @return waiting time until transport arrives
     */
    double getWaitingTime();
}
