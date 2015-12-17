package com.androidworkshopnetwork;

import java.io.Serializable;

/**
 * Created by Adrien on 16/12/15.
 */
public class Sensor implements Serializable {

    /** The name. */
    private String name;

    /** The ip. */
    private String ip;

    /** The sensor type. */
    private SensorTypeEnum sensorType;

    /** The state. */
    private StateEnum state;

    /**
     * Instantiates a new Sensor.
     */
    public Sensor() {
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the ip.
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the sensor type.
     *
     * @return the sensor type
     */
    public SensorTypeEnum getSensorType() {
        return sensorType;
    }

    /**
     * Sets the sensor type.
     *
     * @param sensorType
     */
    public void setSensorType(SensorTypeEnum sensorType) {
        this.sensorType = sensorType;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public StateEnum getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     */
    public void setState(StateEnum state) {
        this.state = state;
    }

}
