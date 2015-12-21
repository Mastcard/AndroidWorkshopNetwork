package com.androidworkshopnetwork;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Adrien on 16/12/15.
 */
public class UDPClient implements Runnable {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 32;

    /** The buffer. */
    private byte buffer[] = new byte[BUFFER_SIZE];

    /** The Constant TIME_OUT. */
    private int timeout = 15000;

    /** The ip. */
    private InetAddress ip;

    /** The port. */
    private int port;

    /** The message to send. */
    private String messageToSend;

    /** The response. */
    private String response;

    /**
     * Instantiates a new UDPClient.
     */
    public UDPClient() {
    }

    /**
     * Instantiates a new UDPClient.
     *
     * @param ip
     * @param port
     * @param messageToSend
     */
    public UDPClient(String ip, int port, String messageToSend) throws UnknownHostException {
        this.ip = InetAddress.getByName(ip);
        this.port = port;
        this.messageToSend = messageToSend;
    }

    @Override
    public void run() {
        try {
            byte buffer[] = this.messageToSend.getBytes("UTF-8");
            int length = buffer.length;

            // Datagram socket.
            Log.d(TAG, "Creating the DatagramSocket...");
            DatagramSocket socket = new DatagramSocket();
            Log.d(TAG, "Done.");

            // Datagram packet.
            Log.d(TAG, "Creating the datagram packet with :");
            Log.d(TAG, "\t\tip = " + ip);
            Log.d(TAG, "\t\tport = " + port);
            Log.d(TAG, "\t\tbuffer = \"" + Arrays.toString(buffer) + "\"");
            Log.d(TAG, "\t\tsize = " + length);
            DatagramPacket dataToSend = new DatagramPacket(buffer, length, ip, port);
            Log.d(TAG, "Done.");

            // Send.
            Log.i(TAG, "Sending...");
            socket.send(dataToSend);
            Log.d(TAG, "Done.");

            // Read response
            socket.setSoTimeout(timeout);
            DatagramPacket dataToReceive = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
            Log.d(TAG, "Reading response...");

            try {
                socket.receive(dataToReceive);
                response = new String(dataToReceive.getData()).substring(0, dataToReceive.getLength());
                NetworkCommunicator.executeReceveivedProtocolAction(response);
            } catch(SocketException e) {
                e.printStackTrace();
                response = "";
            } finally {
                Log.d(TAG, "Received : \"" + response + "\"");
                Log.d(TAG, "Closing the DatagramSocket");
                socket.close();
                Log.d(TAG, "Done.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************************/

    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * Sets the ip.
     *
     * @param ip
     */
    public void setIp(String ip) throws UnknownHostException {
        this.ip = InetAddress.getByName(ip);
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the timeout.
     *
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout.
     *
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Gets the message to send.
     *
     * @return the message to send
     */
    public String getMessageToSend() {
        return messageToSend;
    }

    /**
     * Sets the message to send.
     *
     * @param messageToSend
     */
    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public String getResponse() {
        return response;
    }

}
