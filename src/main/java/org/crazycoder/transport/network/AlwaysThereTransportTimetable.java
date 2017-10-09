package org.crazycoder.transport.network;

/**
 * Represents timetable, which has transport to go ready all the time
 */
public class AlwaysThereTransportTimetable implements Timetable {

    /**
     * Returns 0.0, meaning transport is ready to go all the time
     */
    @Override
    public double getWaitingTime() {
        return 0.0;
    }

}
