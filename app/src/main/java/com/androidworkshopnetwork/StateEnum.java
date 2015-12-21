package com.androidworkshopnetwork;

import java.io.Serializable;

/**
 * Created by Adrien on 16/12/15.
 */
public enum StateEnum implements Serializable {

    OK ("OK"),
    Alarm ("Alarm"),
    Disconnected ("Disconnected"),
    SerialError ("SerialError"),
    Unknown ("?");

    /** The state. */
    private String state = "";

    /**
     * Instantiates a new StateEnum
     *
     * @param state
     */
    StateEnum(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }

}
