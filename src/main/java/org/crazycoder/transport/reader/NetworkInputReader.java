package org.crazycoder.transport.reader;

import org.crazycoder.transport.network.Connection;
import org.crazycoder.transport.network.Station;

import java.util.List;

/**
 * Base interface to read input data
 */
public interface NetworkInputReader {

    /**
     * Reads data from input, parse it and returns result
     *
     * @return InputData
     * @throws IllegalArgumentException
     */
    InputData readInputData() throws IllegalArgumentException;

    final class InputData {
        private final List<Connection> network;
        private final RouteData routeData;
        private final NearbyData nearbyData;

        public InputData(final List<Connection> network,
                         final RouteData routeData,
                         final NearbyData nearbyData) {
            this.network = network;
            this.routeData = routeData;
            this.nearbyData = nearbyData;
        }

        public List<Connection> getNetwork() {
            return network;
        }

        public RouteData getRouteData() {
            return routeData;
        }

        public NearbyData getNearbyData() {
            return nearbyData;
        }
    }

    final class RouteData {
        private final Station src;
        private final Station dest;

        public RouteData(final Station src, final Station dest) {
            this.src = src;
            this.dest = dest;
        }

        public Station getSrc() {
            return src;
        }

        public Station getDest() {
            return dest;
        }
    }

    final class NearbyData {
        private final Station src;
        private final double travelTime;

        public NearbyData(final Station src, final double travelTime) {
            this.src = src;
            this.travelTime = travelTime;
        }

        public Station getSrc() {
            return src;
        }

        public double getTravelTime() {
            return travelTime;
        }
    }
}
