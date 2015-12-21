package com.androidworkshopnetwork;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Adrien on 17/12/15.
 */
public class UDPServer extends Thread {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The UDPServer. */
    private static UDPServer instance = new UDPServer();

    /** The Constant IP. */
    private final String IP = "192.168.1.16";

    /** The Constant PORT. */
    private final int PORT = 5555;

    /** The Constant BUFFER_SIZE. */
    private final int BUFFER_SIZE = 32;

    /** The buffer. */
    private byte buffer[] = new byte[BUFFER_SIZE];

    /** The received message. */
    private String receivedMessage;

    /** The context. */
    private Context context;

    /**
     * Instantiates a new UDPServer.
     */
    private UDPServer() {
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static UDPServer getInstance() {
        return instance;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(PORT);
            Log.i(TAG, "Socket opened :");

            while (true) {
                Log.d(TAG, "Reading..........");
                DatagramPacket data = new DatagramPacket(buffer, buffer.length);
                socket.receive(data);
                this.receivedMessage = new String(data.getData(), "UTF-8").substring(0, data.getLength());
                if (receivedMessage.trim().length() > 0) {

                    Log.i(TAG, "Just received : \"" + receivedMessage + "\"");
                    String response = NetworkCommunicator.buildResponseForReceivedMessage(receivedMessage);
                    data.setData(response.getBytes("UTF-8"));
                    socket.send(data);

                    NetworkCommunicator.executeReceveivedProtocolAction(receivedMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the received message.
     *
     * @return the received message
     */
    public String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * Sets the context.
     *
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

}
