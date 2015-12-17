package com.androidworkshopnetwork;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
        Log.i(TAG, "Instantiates the SensorManager");
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

        Log.i(TAG, "Save the sensor list");
        IOManager.saveSensorList();
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

            Log.i(TAG, "Save the sensor list");
            IOManager.saveSensorList();
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
     * Gets the sensor map.
     *
     * @return
     */
    public HashMap<String, Sensor> getSensorMap() {
        return sensorMap;
    }

    /**
     * Gets the sensor map as a list.
     *
     * @return
     */
    public List<Sensor> getSensorAsList() {
        List<Sensor> result = new ArrayList<Sensor>();

        for (Iterator<Sensor>it = sensorMap.values().iterator(); it.hasNext(); ) {
            Sensor sensor = (Sensor) it.next();
            result.add(sensor);
        }

        return result;
    }

    /**
     * Sets the sensor map.
     */
    public void setSensorMap(HashMap<String, Sensor> newMap) {
        Log.i(TAG, "Loading the new Sensor Map from serialized file...");

        Log.i(TAG, "\t\tClean the actual map.");
        sensorMap.clear();

        Log.i(TAG, "\t\tPut all data from new map.");
        sensorMap.putAll(newMap);

        Log.i(TAG, "\t\tUpdate sensor count.");
        sensorCount = sensorMap.size();

        Log.i(TAG, "Done.");
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
