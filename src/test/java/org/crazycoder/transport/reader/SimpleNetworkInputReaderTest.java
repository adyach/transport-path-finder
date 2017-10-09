package org.crazycoder.transport.reader;

import org.crazycoder.transport.network.Connection;
import org.crazycoder.transport.network.StationsFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class SimpleNetworkInputReaderTest {

    @Test
    public void shouldParseCorrectInput() {
        final String input = "1\n" +
                "A~>B=11\n" +
                "path:A~>B\n" +
                "near A in 11";
        final ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));

        final NetworkInputReader reader = new SimpleNetworkInputReader(in);
        final NetworkInputReader.InputData inputData = reader.readInputData();
        Assert.assertEquals(
                Arrays.asList(new Connection(
                        StationsFactory.createAlwaysThereTransportStation("A"),
                        StationsFactory.createAlwaysThereTransportStation("B"), 11.0)),
                inputData.getNetwork());

        final NetworkInputReader.RouteData routeData = inputData.getRouteData();
        Assert.assertEquals(StationsFactory.createAlwaysThereTransportStation("A"), routeData.getSrc());
        Assert.assertEquals(StationsFactory.createAlwaysThereTransportStation("B"), routeData.getDest());

        final NetworkInputReader.NearbyData nearbyData = inputData.getNearbyData();
        Assert.assertEquals(StationsFactory.createAlwaysThereTransportStation("A"), nearbyData.getSrc());
        Assert.assertEquals(11.0, nearbyData.getTravelTime(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenNetworkFormatIsWrong() {
        final String input = "1\nA>B?00\n";
        final ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));

        final NetworkInputReader reader = new SimpleNetworkInputReader(in);
        reader.readInputData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRouteDataFormatIsWrong() {
        final String input = "1\n" +
                "A->B240\n" +
                "path A Y B\n";
        final ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));

        final NetworkInputReader reader = new SimpleNetworkInputReader(in);
        reader.readInputData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenNearbyDataFormatIsWrong() {
        final String input = "1\n" +
                "A~>B240\n" +
                "path A > B\n" +
                "near AP into 130";
        final ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));

        final NetworkInputReader reader = new SimpleNetworkInputReader(in);
        reader.readInputData();
    }
}