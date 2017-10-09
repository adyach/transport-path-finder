package org.crazycoder.transport;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class TransportPathFinderAppTest {

    @Test
    public void testTransportPathFinderApp() {
        final String input = "5\n" +
                "A~>B=50\n" +
                "A~>D=20\n" +
                "D~>A=77\n" +
                "A~>C=12\n" +
                "C~>B=10\n" +
                "path:A~>B\n" +
                "near A in 130";
        final ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        TransportPathFinderApp.main(null);

        Assert.assertEquals("A~>C~>B=22.0\nStations near A: B=50.0 D=20.0 C=12.0 A=97.0 B=22.0 D=117.0 C=109.0 B=119.0\n", out.toString());
    }
}