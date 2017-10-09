package org.crazycoder.transport.network;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class SimpleStationsNetworkTest {

    private final Station a = StationsFactory.createAlwaysThereTransportStation("a");
    private final Station b = StationsFactory.createAlwaysThereTransportStation("b");
    private final Station c = StationsFactory.createAlwaysThereTransportStation("c");

    @Test
    public void shouldFindRouteWhenThereIsRoute() {
        final Connection ab = new Connection(a, b, 10);
        final Connection bc = new Connection(b, c, 5);

        final StationsNetwork network = new SimpleStationsNetwork(Arrays.asList(ab, bc));
        final StationsNetwork.Route route = network.findRoute(a, c);
        Assert.assertEquals(Arrays.asList(a, b, c), route.getStations());
        Assert.assertEquals(15, route.getTravelTime(), 0.0);
    }

    @Test
    public void shouldReturnEmptyRouteWhenThereIsNoRoute() {
        final Connection ab = new Connection(a, b, 10);

        final StationsNetwork network = new SimpleStationsNetwork(Arrays.asList(ab));
        final StationsNetwork.Route route = network.findRoute(a, c);
        Assert.assertEquals(Collections.emptyList(), route.getStations());
        Assert.assertEquals(0.0, route.getTravelTime(), 0.0);
    }

    @Test
    public void shouldFindNearbyWhenThereIsNearbyStations() {
        final Connection ab = new Connection(a, b, 5);
        final Connection bc = new Connection(b, c, 10);

        final StationsNetwork network = new SimpleStationsNetwork(Arrays.asList(ab, bc));
        final StationsNetwork.NearbyStations nearbyStations = network.findNearbyStations(a, 6);
        Assert.assertEquals(Arrays.asList(b), nearbyStations.getStations());
        Assert.assertEquals(Arrays.asList(5.0), nearbyStations.getTravelTimes());
    }

    @Test
    public void shouldNotFindNearbyWhenThereIsNoNearbyStations() {
        final Connection ab = new Connection(a, b, 10);
        final Connection bc = new Connection(b, c, 5);

        final StationsNetwork network = new SimpleStationsNetwork(Arrays.asList(ab, bc));
        final StationsNetwork.NearbyStations nearbyStations = network.findNearbyStations(a, 6);
        Assert.assertEquals(Arrays.asList(), nearbyStations.getStations());
        Assert.assertEquals(Arrays.asList(), nearbyStations.getTravelTimes());
    }


}