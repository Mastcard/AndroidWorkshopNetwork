package com.androidworkshopnetwork;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Adrien on 17/12/15.
 */
public class SensorButtonManager {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The instance. */
    private static SensorButtonManager ourInstance = new SensorButtonManager();

    /** The sensor button map. */
    private HashMap<String, SensorButton>sensorButtonMap = new HashMap<String, SensorButton>();

    /** The sensor manager. */
    private SensorManager sensorManager = SensorManager.getInstance();

    /** The sensor button count. */
    private int sensorButtonCount;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static SensorButtonManager getInstance() {
        return ourInstance;
    }

    /**
     * Instantiates a new SensorButtonManager.
     */
    private SensorButtonManager() {
        Log.i(TAG, "Instantiates the SensorButtonManager");
        sensorButtonCount = 0;
    }

    /**
     * Updates the sensor button map.
     *
     * @param context
     */
    public void updateSensorButtonMap(Context context) {
        HashMap<String, Sensor> sensorMap = sensorManager.getSensorMap();

        for (Iterator<Sensor> it = sensorMap.values().iterator(); it.hasNext(); ) {
            Sensor sensor = (Sensor) it.next();
            if (sensorButtonMap.get(sensor.getName()) == null) {
                SensorButton sensorButton = new SensorButton(context, sensor);
                sensorButtonMap.put(sensorButton.getSensorName(), sensorButton);
                sensorButtonCount++;
            }
        }

    }

    /**
     * Gets sensor button by sensor name.
     *
     * @param name
     * @return the sensor button
     */
    public SensorButton getSensorButtonBySensorName(String name) {
        return sensorButtonMap.get(name);
    }

    /**
     * Gets sensor button by sensor ip.
     *
     * @param ip
     * @return the sensor button
     */
    public SensorButton getSensorButtonBySensorIp(String ip) {

        for (Iterator<SensorButton>it = sensorButtonMap.values().iterator(); it.hasNext(); ) {
            SensorButton sensorButton = (SensorButton) it.next();
            if (sensorButton.getSensorIp().equals(ip)) {
                return sensorButton;
            }
        }

        return null;
    }

    /**
     * Gets the sensor button map.
     *
     * @return the sensor button map
     */
    public HashMap<String, SensorButton> getSensorButtonMap() {
        return sensorButtonMap;
    }

    /**
     * Gets the sensor button count.
     *
     * @return the sensor button count
     */
    public int getSensorButtonCount() {
        return sensorButtonCount;
    }

}
