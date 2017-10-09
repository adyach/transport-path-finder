package org.crazycoder.transport.writer;

import org.crazycoder.transport.network.StationsFactory;
import org.crazycoder.transport.network.StationsNetwork;
import org.crazycoder.transport.reader.NetworkInputReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class RouteResultPrinterTest {

    @Test
    public void shouldPrintListOfStationsWithTheirTravelTimeWhenThereIsRoute() {
        final StationsNetwork.Route route = new StationsNetwork.Route(
                Arrays.asList(
                        StationsFactory.createAlwaysThereTransportStation("A"),
                        StationsFactory.createAlwaysThereTransportStation("B")), 15.0);
        NetworkInputReader.RouteData routeData = new NetworkInputReader.RouteData(
                StationsFactory.createAlwaysThereTransportStation("A"),
                StationsFactory.createAlwaysThereTransportStation("B"));
        final ResultPrinter printer = new RouteResultPrinter(route, routeData);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        printer.print(new PrintStream(out));

        Assert.assertEquals("A~>B=15.0\n", out.toString());
    }

    @Test
    public void shouldPrintErrorWhenThereIsNoRoute() {
        final StationsNetwork.Route route = new StationsNetwork.Route(Arrays.asList(), 0.0);
        NetworkInputReader.RouteData routeData = new NetworkInputReader.RouteData(
                StationsFactory.createAlwaysThereTransportStation("A"),
                StationsFactory.createAlwaysThereTransportStation("B"));
        final ResultPrinter printer = new RouteResultPrinter(route, routeData);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        printer.print(new PrintStream(out));

        Assert.assertEquals("No path A ~> B\n", out.toString());
    }
}