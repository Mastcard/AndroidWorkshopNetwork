package com.androidworkshopnetwork;

import android.util.Log;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static junit.framework.Assert.*;

/**
 * Created by Adrien on 16/12/15.
 *
 * This class tests the UDP client used to connect the pad to the application server (not yet developed).
 * WARNING : DON'T FORGET TO LAUNCH A SERVER...
 */
public class UDPClientTests {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The UDP client. */
    private static UDPClient client;

    @BeforeClass
    public static void setUp() {
        client = new UDPClient();
    }

    @Test
    public void testSimpleDataSending() throws IOException, InterruptedException {
        assertNotNull(client);

        String clientIp = "127.0.0.1";
        int clientPort = 8532;
        client.setIp(InetAddress.getByName(clientIp));
        client.setPort(clientPort);
        client.setMessageToSend("ST 10.40.57.212");
        Thread clientThread = new Thread(client);
        clientThread.start();

        clientThread.join();
        assertNotNull(client.getResponse());
        Log.d(TAG, client.getResponse());
    }

}
