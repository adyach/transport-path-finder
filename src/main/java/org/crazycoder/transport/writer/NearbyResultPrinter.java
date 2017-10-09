package org.crazycoder.transport.writer;

import org.crazycoder.transport.network.Station;
import org.crazycoder.transport.network.StationsNetwork;
import org.crazycoder.transport.reader.NetworkInputReader;

import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class NearbyResultPrinter implements ResultPrinter {

    private final StationsNetwork.NearbyStations nearbyStations;
    private final NetworkInputReader.NearbyData nearbyData;

    public NearbyResultPrinter(final StationsNetwork.NearbyStations nearbyStations,
                               final NetworkInputReader.NearbyData nearbyData) {
        this.nearbyStations = nearbyStations;
        this.nearbyData = nearbyData;
    }

    @Override
    public void print(PrintStream out) {
        if (nearbyStations.isEmpty()) {
            out.printf("No stations near %s in time %s", nearbyData.getSrc(), nearbyData.getTravelTime());
            out.println();
        } else {
            final String result = IntStream.range(0, nearbyStations.getStations().size())
                    .mapToObj(i -> {
                        final Station station = nearbyStations.getStations().get(i);
                        final Double travelTime = nearbyStations.getTravelTimes().get(i);
                        return String.format("%s=%s", station, travelTime);
                    })
                    .collect(Collectors.joining(" "));
            out.format("Stations near %s: %s", nearbyData.getSrc(), result).println();
        }
    }
}
