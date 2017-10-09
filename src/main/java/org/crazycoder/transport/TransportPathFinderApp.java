package org.crazycoder.transport;

import org.crazycoder.transport.network.SimpleStationsNetwork;
import org.crazycoder.transport.network.StationsNetwork;
import org.crazycoder.transport.reader.NetworkInputReader;
import org.crazycoder.transport.reader.SimpleNetworkInputReader;
import org.crazycoder.transport.writer.NearbyResultPrinter;
import org.crazycoder.transport.writer.RouteResultPrinter;

import java.util.Arrays;

public class TransportPathFinderApp {

    public static void main(String[] args) {
        try {
            final NetworkInputReader networkInputReader = new SimpleNetworkInputReader(System.in);
            final NetworkInputReader.InputData inputData = networkInputReader.readInputData();
            final NetworkInputReader.RouteData routeData = inputData.getRouteData();
            final NetworkInputReader.NearbyData nearbyData = inputData.getNearbyData();

            final StationsNetwork stationsNetwork = new SimpleStationsNetwork(inputData.getNetwork());
            final StationsNetwork.Route route = stationsNetwork.findRoute(routeData.getSrc(), routeData.getDest());
            final StationsNetwork.NearbyStations nearbyStations = stationsNetwork.findNearbyStations(nearbyData.getSrc(), nearbyData.getTravelTime());

            Arrays.asList(
                    new RouteResultPrinter(route, routeData),
                    new NearbyResultPrinter(nearbyStations, nearbyData)
            ).stream().forEach(printer -> printer.print(System.out));
        } catch (final IllegalArgumentException iae) {
            System.err.println();
            System.err.println(iae.getMessage());
            System.exit(-1);
        }
    }
}
