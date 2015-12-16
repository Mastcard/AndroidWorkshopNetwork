package com.androidworkshopnetwork;

/**
 * Created by Adrien on 16/12/15.
 */
public enum StateEnum {

    OK ("OK"),
    Alarm ("Alarm"),
    Disconnected ("Disconnected"),
    SerialError ("Serial error"),
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
