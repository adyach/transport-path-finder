package org.crazycoder.transport.writer;

import org.crazycoder.transport.network.StationsFactory;
import org.crazycoder.transport.network.StationsNetwork;
import org.crazycoder.transport.reader.NetworkInputReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class NearbyResultPrinterTest {

    @Test
    public void shouldPrintListOfStationsWithTheirTravelTimeWhenThereIsRoute() {
        final StationsNetwork.NearbyStations nearbyStations =
                new StationsNetwork.NearbyStations(
                        Arrays.asList(StationsFactory.createAlwaysThereTransportStation("A")),
                        Arrays.asList(1.0)
                );

        NetworkInputReader.NearbyData nearbyData = new NetworkInputReader.NearbyData(
                StationsFactory.createAlwaysThereTransportStation("A"), 1.0);
        final ResultPrinter printer = new NearbyResultPrinter(nearbyStations, nearbyData);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        printer.print(new PrintStream(out));

        Assert.assertEquals("Stations near A: A=1.0\n", out.toString());
    }

    @Test
    public void shouldPrintErrorWhenThereIsNoRoute() {
        final StationsNetwork.NearbyStations nearbyStations =
                new StationsNetwork.NearbyStations(Arrays.asList(), Arrays.asList());
        NetworkInputReader.NearbyData nearbyData = new NetworkInputReader.NearbyData(
                StationsFactory.createAlwaysThereTransportStation("A"), 1.0);
        final ResultPrinter printer = new NearbyResultPrinter(nearbyStations, nearbyData);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        printer.print(new PrintStream(out));

        Assert.assertEquals("No stations near A in time 1.0\n", out.toString());
    }

}