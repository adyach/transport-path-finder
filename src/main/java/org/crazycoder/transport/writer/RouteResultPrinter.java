package org.crazycoder.transport.writer;

import org.crazycoder.transport.network.Station;
import org.crazycoder.transport.network.StationsNetwork;
import org.crazycoder.transport.reader.NetworkInputReader;

import java.io.PrintStream;
import java.util.stream.Collectors;

public final class RouteResultPrinter implements ResultPrinter {

    private final StationsNetwork.Route route;
    private final NetworkInputReader.RouteData routeData;

    public RouteResultPrinter(final StationsNetwork.Route route,
                              final NetworkInputReader.RouteData routeData) {
        this.route = route;
        this.routeData = routeData;
    }

    @Override
    public void print(final PrintStream out) {
        if (route.isEmpty()) {
            out.printf("No path %s ~> %s", routeData.getSrc(), routeData.getDest());
            out.println();
        } else {
            final String result = route.getStations().stream()
                    .map(Station::getName)
                    .collect(Collectors.joining("~>"));
            out.printf("%s=%s", result, route.getTravelTime());
            out.println();
        }
    }
}
