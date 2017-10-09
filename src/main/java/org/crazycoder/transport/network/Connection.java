package org.crazycoder.transport.network;

import java.util.Objects;

/**
 * Connection (edge) between two stations
 */
public final class Connection {

    private final Station src;
    private final Station dest;
    private final double travelTime;

    public Connection(final Station src,
                      final Station dest,
                      final double travelTime) {
        this.src = src;
        this.dest = dest;
        this.travelTime = travelTime;
    }

    public Station getSrc() {
        return src;
    }

    public Station getDest() {
        return dest;
    }

    public double getTravelTime() {
        return travelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Double.compare(that.travelTime, travelTime) == 0 &&
                Objects.equals(src, that.src) &&
                Objects.equals(dest, that.dest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, travelTime);
    }
}
