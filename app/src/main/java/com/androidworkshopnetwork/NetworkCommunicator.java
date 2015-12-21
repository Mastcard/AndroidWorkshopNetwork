package com.androidworkshopnetwork;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Adrien on 15/12/15.
 */
public class NetworkCommunicator {

    /** The TAG. */
    private static final String TAG = "NetworkCommunicator";

    /** The UDPServer instance. */
    private static UDPServer udpServer = UDPServer.getInstance();

    /** The Constant SERVER_IP. */
    private static final String SERVER_IP = "192.168.1.38";

    /** The Constant SERVER_PORT. */
    private static final int SERVER_PORT = 8532;

    /** The context. */
    private static Context context = null;

    /** The ip address validator. */
    private static IpAddressValidator ipValidator = new IpAddressValidator();

    /**
     * Build response for message.
     *
     * @param receivedMessage
     * @return
     */
    public static synchronized String buildResponseForReceivedMessage(String receivedMessage) {
        String response = null;
        String keyword = receivedMessage.substring(0, 2);
        String[] splittedMessage = receivedMessage.split("_");

        switch (keyword) {
            case "AD":
                response = splittedMessage[0] + "_OK_" + splittedMessage[1];
                break;
            case "AL":
            case "ES":
            case "EN":
                response = "OK_" + receivedMessage;
                break;
            default:
                response = "Unknown keyword \"" + keyword + "\" !";
                Log.e(TAG, response);
        }
        return response;
    }

    /**
     * Executes action after reception of a UDP message
     *
     * @param receivedMessage
     */
    public static synchronized void executeReceveivedProtocolAction(String receivedMessage) {
        SensorManager sensorManager = SensorManager.getInstance();
        SensorButtonManager sensorButtonManager = SensorButtonManager.getInstance();

        String sensorIp = null;
        SensorButton sensorButton = null;
        HashMap<String, SensorButton> sensorButtonMap = null;

        String splittedMessage[] = receivedMessage.split("_");

        // Check if there is at least 2 elements
        if (splittedMessage.length > 1) {

            String keyword = splittedMessage[0];
            Log.i(TAG, "Executing " + keyword + "...");

            switch (keyword) {

                case "AD":
                    if (splittedMessage.length > 2) {
                        sensorIp = splittedMessage[1];
                        if (ipValidator.validate(sensorIp)) {
                            Sensor sensor = new Sensor();
                            sensor.setIp(splittedMessage[1]);

                            switch (splittedMessage[2]) {
                                case "-2":
                                    sensor.setSensorType(SensorTypeEnum.Photoresistance);
                                    break;
                                case "-4":
                                    sensor.setSensorType(SensorTypeEnum.Pushbutton);
                                    break;
                                default:
                                    Log.i(TAG, "Unknown sensor type \"" + splittedMessage[2] + "\" !");
                                    return;
                            }

                            sensor.setName(sensor.getSensorType().toString() + sensorManager.getSensorCount());
                            sensor.setState(StateEnum.OK);
                            sensorManager.addSensor(sensor);
                            sensorButtonManager.updateSensorButtonMap(context);
                            context.startActivity(new Intent(context, HomeActivity.class));
                        } else {
                            Log.i(TAG, "IP address \"" + sensorIp + "\" is not valid.");
                        }
                    } else {
                        Log.i(TAG, "AD protocol not in correct format \"AL\" \"IP\" \"TYPE\"");
                    }
                    break;

                case "AL":
                    sensorIp = splittedMessage[1];
                    sensorButton = sensorButtonManager.getSensorButtonBySensorIp(sensorIp);
                    if (sensorButton != null) {
                        sensorButton.updateSensorState(StateEnum.Alarm);
                    } else {
                        Log.i(TAG, "Unknown sensor with IP : " + sensorIp);
                    }
                    break;

                case "ES":
                    sensorIp = splittedMessage[1];
                    sensorButton = sensorButtonManager.getSensorButtonBySensorIp(sensorIp);
                    if (sensorButton != null) {
                        sensorButton.updateSensorState(StateEnum.SerialError);
                    }  else {
                        Log.i(TAG, "Unknown sensor with IP : " + sensorIp);
                    }
                    break;

                case "EN":
                    sensorIp = splittedMessage[1];
                    sensorButton = sensorButtonManager.getSensorButtonBySensorIp(sensorIp);
                    if (sensorButton != null) {
                        sensorButton.updateSensorState(StateEnum.Disconnected);
                    } else {
                        // Pc-com is dead => all sensors are in an unknown status
                        sensorButtonMap = sensorButtonManager.getSensorButtonMap();
                        for (Iterator<SensorButton>it = sensorButtonMap.values().iterator(); it.hasNext(); ) {
                            sensorButton = (SensorButton) it.next();
                            sensorButton.updateSensorState(StateEnum.Unknown);
                        }
                    }
                    break;

                case "OK":
                    if (splittedMessage.length > 2) {
                        sensorIp = splittedMessage[2];
                        switch (splittedMessage[1]) {

                            case "ST":
                                if (splittedMessage.length > 3) {
                                    Log.i(TAG, "STOP alarm signal successfully send :)");
                                    sensorIp = splittedMessage[3];
                                    sensorButton = sensorButtonManager.getSensorButtonBySensorIp(sensorIp);
                                    if (sensorButton != null) {
                                        sensorButton.updateSensorState(StateEnum.OK);
                                    }
                                } else {
                                    Log.i(TAG, "The ST message \"" + receivedMessage + "\" is too short !");
                                }
                                break;

                            case "CA":
                                Log.i(TAG, "CAMERA request accepted :)");
                                Intent cameraIntent = new Intent(context, CameraActivity.class);
                                cameraIntent.putExtra("ip", sensorIp);
                                context.startActivity(cameraIntent);
                                break;

                            case "FA":
                                Log.i(TAG, "END CAMERA signal successfully send :)");
                                break;

                            case "UP":
                            case "DW":
                            case "RG":
                            case "LF":
                                Log.i(TAG, splittedMessage[1] + " signal successfully send :)");
                                break;
                            default:
                                Log.i(TAG, "Unknown message \"" + receivedMessage + "\"");
                        }
                    } else {
                        Log.i(TAG, "The OK message \"" + receivedMessage + "\" is too short !");
                    }
            }

        } else {
            Log.i(TAG, "The message \"" + receivedMessage + "\" is not in correct format !");
        }
    }

    /**
     * Starts the server.
     */
    public static void startServer(Context context) {
        if (!udpServer.isAlive()) {
            Log.i(TAG, "LAUNCH THE SERVER...");
            udpServer.start();
        }
    }

    /**
     * Stop alarm.
     *
     * @param sensorIp
     */
    public static void stopAlarm(String sensorIp) throws UnknownHostException {
        UDPClient udpClient = prepareUDPClient();
        SensorButton sensorButton = SensorButtonManager.getInstance().getSensorButtonBySensorIp(sensorIp);
        udpClient.setMessageToSend("ST_AL_" + sensorIp);

        StateEnum previewSensorState = sensorButton.getSensorState();
        sensorButton.updateSensorState(StateEnum.Unknown);

        Log.i(TAG, "STOP ALARM thread");
        Thread udpThread = new Thread(udpClient);
        Log.i(TAG, "\t\tLaunching the thread...");

        udpThread.start();

        try {
            udpThread.join();
            Log.i(TAG, "\t\tThread has been joined successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "STOP ALARM thread now dead");
        }

        if (udpClient.getResponse() == null) {
            sensorButton.updateSensorState(previewSensorState);
        }
    }

    /**
     * Start camera direction.
     */
    public static void startCameraDirection(String sensorIp) throws UnknownHostException {
        UDPClient udpClient = prepareUDPClient();
        udpClient.setMessageToSend("CA_" + sensorIp);

        Log.i(TAG, "Start camera drive signal thread");
        Thread udpThread = new Thread(udpClient);
        Log.i(TAG, "\t\tLaunching the thread...");

        udpThread.start();

        try {
            udpThread.join();
            Log.i(TAG, "\t\tThread has been joined successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "Start camera signal thread now dead");
        }
    }

    /**
     * Send camera direction.
     *
     * @param direction
     */
    public static void sendCameraDirection(DirectionEnum direction, String ip) throws UnknownHostException {
        UDPClient udpClient = prepareUDPClient();
        udpClient.setTimeout(1000);
        udpClient.setMessageToSend(direction.toString() + "_" + ip);

        Log.i(TAG, "SEND DIRECTION \"" + direction.toString() + "\" thread");
        Thread udpThread = new Thread(udpClient);
        Log.i(TAG, "\t\tLaunching the thread...");

        udpThread.start();

        try {
            udpThread.join();
            Log.i(TAG, "\t\tThread has been joined successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "SEND DIRECTION thread now dead");
        }
    }

    /**
     * Send end camrea direction.
     *
     * @param ip
     * @throws UnknownHostException
     */
    public static void sendEndCameraDirection(String ip) throws UnknownHostException {
        UDPClient udpClient = prepareUDPClient();
        udpClient.setTimeout(1000);
        udpClient.setMessageToSend("FA_" + ip);

        Log.i(TAG, "SEND END CAMERA thread");
        Thread udpThread = new Thread(udpClient);
        Log.i(TAG, "\t\tLaunching the thread...");

        udpThread.start();

        try {
            udpThread.join();
            Log.i(TAG, "\t\tThread has been joined successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "SEND END CAMERA thread now dead");
        }
    }

    /**
     * Sets the context.
     *
     * @param newContext
     */
    public static void setContext(Context newContext) {
        context = newContext;
    }

    /**
     * Prepares the UDPClient.
     *
     * @return the udpclient
     */
    private static UDPClient prepareUDPClient() throws UnknownHostException {
        UDPClient udpClient = new UDPClient();
        udpClient.setIp(SERVER_IP);
        udpClient.setPort(SERVER_PORT);
        return udpClient;
    }

}
