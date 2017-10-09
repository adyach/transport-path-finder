package org.crazycoder.transport.writer;

import java.io.PrintStream;

/**
 * Base class to print results of the find route and find nearby stations
 */
public interface ResultPrinter {

    /**
     * Prints data to provided output stream
     *
     * @param out output stream to write data to
     */
    void print(final PrintStream out);

}
