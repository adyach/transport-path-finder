package org.crazycoder.transport.network;

import java.util.List;

/**
 * Represents the network data storage (stations and connections between them) and
 * set methods to process this data
 */
public interface StationsNetwork {

    /**
     * Finds the shortest possible path from source to destination
     *
     * @param src  station to start
     * @param dest station to end
     * @return Route
     */
    Route findRoute(Station src, Station dest);

    /**
     * Finds nearby stations from the provided station reachable in a given time interval
     *
     * @param src
     * @param travelTime
     * @return NearbyStations
     */
    NearbyStations findNearbyStations(Station src, double travelTime);

    final class Route {
        private final List<Station> stations;
        private final Double travelTime;

        public Route(final List<Station> stations, final Double travelTime) {
            this.stations = stations;
            this.travelTime = travelTime;
        }

        public List<Station> getStations() {
            return stations;
        }

        public Double getTravelTime() {
            return travelTime;
        }

        public boolean isEmpty() {
            return stations.isEmpty();
        }
    }

    final class NearbyStations {
        private final List<Station> stations;
        private final List<Double> travelTimes;

        public NearbyStations(final List<Station> stations, final List<Double> travelTimes) {
            this.stations = stations;
            this.travelTimes = travelTimes;
        }

        public List<Station> getStations() {
            return stations;
        }

        public List<Double> getTravelTimes() {
            return travelTimes;
        }

        public boolean isEmpty() {
            return stations.isEmpty();
        }
    }
}
