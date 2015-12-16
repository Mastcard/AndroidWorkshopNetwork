package com.androidworkshopnetwork;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Adrien on 16/12/15.
 */
public class NetworkDatagramSocket extends DatagramSocket {

    /** The Constant IP. */
    //private static final InetAddress IP;

    /** The Constant PORT. */
    private static final int PORT = 5000;

    public NetworkDatagramSocket() throws IOException {
        //super(PORT, IP);
    }

}
