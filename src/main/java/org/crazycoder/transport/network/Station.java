package org.crazycoder.transport.network;

import java.util.Comparator;


public class Station {

    private final String name;
    private final Timetable timetable;
    private double stationTravelTime;
    private Station previousStation;
    private boolean discovered;

    public Station(final String name, final Timetable timetable) {
        this.name = name;
        this.timetable = timetable;
    }

    public String getName() {
        return name;
    }

    public double getWaitingTime() {
        return timetable.getWaitingTime();
    }

    public double getStationTravelTime() {
        return stationTravelTime;
    }

    public void setStationTravelTime(double stationTravelTime) {
        this.stationTravelTime = stationTravelTime;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered() {
        discovered = true;
    }

    public Station copy() {
        return StationsFactory.createAlwaysThereTransportStation(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this.name.equals(((Station) o).name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    static class StationComparator implements Comparator<Station> {
        @Override
        public int compare(Station s1, Station s2) {
            return Double.compare(s1.getStationTravelTime(), s2.getStationTravelTime());
        }
    }

}
