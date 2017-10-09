package org.crazycoder.transport.reader;

import org.crazycoder.transport.network.Connection;
import org.crazycoder.transport.network.Station;
import org.crazycoder.transport.network.StationsFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation uses java.util.Scanner to read from input stream and
 * parsing it according to provided requirements
 */
public final class SimpleNetworkInputReader implements NetworkInputReader {

    private static final String FORMAT_CONNECTIONS = "The network format should be : A~>B:11";
    private static final String FORMAT_ROUTE = "The route function format should be : path:A~>B";
    private static final String FORMAT_NEARBY = "The nearby function format should be : near A in 11";

    private final Scanner scanner;

    public SimpleNetworkInputReader(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    /**
     * Process input data
     *
     * @return InputData
     * @throws IllegalArgumentException if input format was incorrect
     */
    @Override
    public InputData readInputData() throws IllegalArgumentException {
        final List<Connection> network = readNetwork();
        final RouteData routeData = readRouteData();
        final NearbyData nearbyData = readNearbyData();
        return new InputData(network, routeData, nearbyData);
    }

    // format: A~>B=11
    private List<Connection> readNetwork() {
        final int connectionsNumber = Integer.valueOf(scanner.nextLine());
        return IntStream.range(0, connectionsNumber)
                .mapToObj(i -> scanner.nextLine())
                .map(connection -> {
                    final String[] namesAndTravelTime = connection.split("=");
                    if (namesAndTravelTime.length != 2) {
                        throw new IllegalArgumentException(FORMAT_CONNECTIONS);
                    }
                    final String[] names = namesAndTravelTime[0].split("~>");
                    if (names.length != 2) {
                        throw new IllegalArgumentException(FORMAT_CONNECTIONS);
                    }
                    final Station src = StationsFactory.createAlwaysThereTransportStation(names[0]);
                    final Station dest = StationsFactory.createAlwaysThereTransportStation(names[1]);
                    try {
                        final Double travelTime = Double.valueOf(namesAndTravelTime[1].trim());
                        return new Connection(src, dest, travelTime);
                    } catch (final NumberFormatException nfe) {
                        throw new IllegalArgumentException(FORMAT_CONNECTIONS);
                    }
                })
                .collect(Collectors.toList());
    }

    // format: path:A~>B
    private RouteData readRouteData() {
        final String routeStr = scanner.nextLine();
        final String[] route = routeStr.split("path:");
        if (route.length != 2 && route[0].isEmpty()) {
            throw new IllegalArgumentException(FORMAT_ROUTE);
        }
        final String[] names = route[1].split("~>");
        if (names.length != 2) {
            throw new IllegalArgumentException(FORMAT_ROUTE);
        }
        return new RouteData(StationsFactory.createAlwaysThereTransportStation(names[0]),
                StationsFactory.createAlwaysThereTransportStation(names[1]));
    }

    // format: near A in 11
    private NearbyData readNearbyData() {
        final String neatbyStr = scanner.nextLine();
        final String[] nearby = neatbyStr.split("near ");
        if (nearby.length != 2 && nearby[0].isEmpty()) {
            throw new IllegalArgumentException(FORMAT_NEARBY);
        }
        final String[] values = nearby[1].split(" in ");
        if (values.length != 2) {
            throw new IllegalArgumentException(FORMAT_NEARBY);
        }

        try {
            final Double travelTime = Double.valueOf(values[1].trim());
            return new NearbyData(StationsFactory.createAlwaysThereTransportStation(values[0]), travelTime);
        } catch (final NumberFormatException nfe) {
            throw new IllegalArgumentException(FORMAT_NEARBY);
        }
    }

}
