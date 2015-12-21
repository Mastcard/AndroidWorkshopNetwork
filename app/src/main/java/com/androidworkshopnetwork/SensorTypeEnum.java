package com.androidworkshopnetwork;

import java.io.Serializable;

/**
 * Created by Adrien on 16/12/15.
 */
public enum SensorTypeEnum implements Serializable {

    Photoresistance ("PhotoResistance"),
    Pushbutton ("PushButton"),
    Camera ("Camera");

    /** The name. */
    private String name = "";

    /**
     * Instantiates a new SensorTypeEnum.
     *
     * @param name
     */
    SensorTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
