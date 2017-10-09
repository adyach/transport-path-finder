package org.crazycoder.transport.network;

public class StationsFactory {

    /**
     * Creates station, which has transport to go ready all the time
     */
    public static Station createAlwaysThereTransportStation(final String name) {
        return new Station(name, new AlwaysThereTransportTimetable());
    }

}
