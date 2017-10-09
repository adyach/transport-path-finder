package org.crazycoder.transport.network;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public final class SimpleStationsNetwork implements StationsNetwork {

    private final List<Connection> network;

    public SimpleStationsNetwork(final List<Connection> network) {
        this.network = network;
    }

    /**
     * Algorithm is based on priority queue, which decides, based on station travel time,
     * what the next station is to take for processing. The queue is filled up with
     * adjacent stations by selecting connections(edges) and taking the destination station.
     * Once station was visited, it will be omitted the next time if and only if the travel
     * time to go to that station is lower than current traveling time. It process the
     * stations until it finds the destination station
     *
     * @param src  station to start
     * @param dest station to end
     * @return Route
     */
    public Route findRoute(final Station src, final Station dest) {
        double currentStationTravelTime = 0;
        double totalTravelTime = 0.0;
        final Queue<Station> stationsInProgress = new PriorityQueue<>(10, new Station.StationComparator());
        stationsInProgress.add(src);
        while (stationsInProgress.isEmpty() == false) {
            final Station currentStation = stationsInProgress.poll();
            if (currentStation.equals(dest)) {
                return new Route(createRoute(currentStation), totalTravelTime);
            }
            double minTravelTime = 0.0;
            for (Connection connection : getConnectionsForStation(currentStation)) {
                final Station nextStation = connection.getDest();
                final double stationTravelTime = connection.getTravelTime() + nextStation.getWaitingTime();
                if (Double.compare(stationTravelTime, currentStationTravelTime) == -1 ||
                        Double.compare(minTravelTime, 0.0) == 0) {
                    minTravelTime = stationTravelTime;
                }
                if (!nextStation.isDiscovered() || Double.compare(stationTravelTime, currentStationTravelTime) == -1) {
                    nextStation.setDiscovered();
                    nextStation.setStationTravelTime(stationTravelTime);
                    nextStation.setPreviousStation(currentStation);
                    stationsInProgress.add(nextStation);
                    currentStationTravelTime = stationTravelTime;
                }
            }
            totalTravelTime += minTravelTime;
        }
        return new Route(Collections.emptyList(), 0.0);
    }

    /**
     * Traverses back to the source node using previous stations of the destination station
     *
     * @param station
     * @return list of stations, which represent the path from source to destination
     */
    private List<Station> createRoute(Station station) {
        List<Station> stations = new LinkedList<>();
        stations.add(station);
        while (station.getPreviousStation() != null) {
            stations.add(0, station.getPreviousStation());
            station = station.getPreviousStation();
        }
        return stations;
    }

    /**
     * The coping of the stations is unavoidable here, because every visited node in
     * the findRoute or findNearbyStations marked as discovered. Other solutions would be
     * to go through the whole network and reset the discovered flag
     *
     * @param station
     * @return list of connections for the provided station
     */
    private List<Connection> getConnectionsForStation(Station station) {
        final List<Connection> stations = new LinkedList<>();
        for (Connection connection : network) {
            if (connection.getSrc().equals(station)) {
                final Connection connectionCopy = new Connection(connection.getSrc().copy(),
                        connection.getDest().copy(), connection.getTravelTime());
                stations.add(connectionCopy);
            }
        }
        return stations;
    }

    /**
     * Algorithm goes through the stations level by level, tracking current time to reach
     * the station for every station on that level
     *
     * @param src        station near which other stations have to be discovered
     * @param travelTime maximum travel time from the source station to other reachable station
     * @return NearbyStations
     */
    public NearbyStations findNearbyStations(Station src, double travelTime) {
        // resulting reachable stations
        final List<Station> stations = new LinkedList<>();
        // resulting time to travel to station i from stations list
        final List<Double> travelTimes = new LinkedList<>();
        // since algorithm traverse nodes by levels on which they are located then we track time
        // to reach station on that level by separate queue.
        // later this time is taken as base for the next level
        final Queue<Double> currentStationsTravelTimes = new LinkedList<>();
        // tracking the nodes to process next
        final Queue<Station> stationsInProgress = new LinkedList<>();

        stationsInProgress.add(src);
        currentStationsTravelTimes.add(0.0); // on the 0 level (source station) the time to reach it is 0
        while (!stationsInProgress.isEmpty()) {
            final Station currentStation = stationsInProgress.poll();
            final Double currentTravelTime = currentStationsTravelTimes.poll();
            for (Connection connection : getConnectionsForStation(currentStation)) {
                final double nextStationTravelTime = connection.getTravelTime() + currentTravelTime;
                final Station dest = connection.getDest();
                if (Double.compare(nextStationTravelTime, travelTime) <= 0 && !dest.isDiscovered()) {
                    stationsInProgress.add(dest);
                    currentStationsTravelTimes.add(nextStationTravelTime);
                    travelTimes.add(nextStationTravelTime);
                    stations.add(dest);
                }
                dest.setDiscovered();
            }
        }

        return new NearbyStations(stations, travelTimes);
    }
}
