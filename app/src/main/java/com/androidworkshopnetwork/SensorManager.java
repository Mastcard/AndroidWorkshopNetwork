package com.androidworkshopnetwork;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Adrien on 16/12/15.
 */
public class SensorManager {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The instance. */
    private static SensorManager instance = new SensorManager();

    /** The sensor map. */
    private HashMap<String, Sensor>sensorMap = new HashMap<String, Sensor>();

    /** The sensor count. */
    private int sensorCount;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static SensorManager getInstance() {
        return instance;
    }

    /**
     * Instantiates a new SensorManager.
     */
    private SensorManager() {
        Log.d(TAG, "Instantiates the SensorManager");
        sensorCount = 0;
    }

    /*
     *
     */

    /**
     * Adds a sensor.
     *
     * @param sensor
     */
    public void addSensor(Sensor sensor) {
        Log.i(TAG, "Adding the sensor " + sensor.getName() + " with IP=" + sensor.getIp() + "...");

        sensorMap.put(sensor.getName(), sensor);
        sensorCount = sensorMap.size();

        Log.i(TAG, "Added. Now " + sensorCount + " sensor(s).");
    }

    /**
     * Removes a sensor.
     *
     * @param name
     */
    public void removeSensor(String name) {
        Log.i(TAG, "Looking for the sensor " + name + "...");

        Sensor sensorToRemove = sensorMap.get(name);

        if (sensorToRemove != null) {
            Log.i(TAG, "Sensor " + name + " found.");
            Log.i(TAG, "Removing the sensor " + sensorToRemove.getName() + " with IP=" + sensorToRemove.getIp() + "...");

            sensorMap.remove(sensorToRemove.getName());
            sensorCount = sensorMap.size();

            Log.i(TAG, "Removed. Now " + sensorCount + " sensor(s).");

        } else {
            Log.e(TAG, "Sensor " + name + " not found !");
        }
    }

    /**
     * Gets the sensor with sensor name.
     *
     * @param sensorName
     * @return the sensor
     */
    public Sensor getSensorByName(String sensorName) {
        return sensorMap.get(sensorName);
    }

    /**
     * Gets the sensor by ip.
     *
     * @param ip
     * @return the sensor
     */
    public Sensor getSensorByIp(String ip) {

        for (Iterator<Sensor>it = sensorMap.values().iterator(); it.hasNext(); ) {
            Sensor sensor = (Sensor) it.next();
            if (sensor.getIp().equals(ip)) {
                return sensor;
            }
        }

        return null;
    }

    /**
     * Gets the sensor count.
     *
     * @return the sensor count
     */
    public int getSensorCount() {
        return sensorCount;
    }

}
