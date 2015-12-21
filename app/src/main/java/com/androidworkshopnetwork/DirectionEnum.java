package com.androidworkshopnetwork;

/**
 * Created by Adrien on 18/12/15.
 */
public enum DirectionEnum {

    UP ("UP"),
    DOWN ("DW"),
    LEFT ("LF"),
    RIGHT ("RG");

    /** The direction. */
    private String direction = "";

    /**
     * Instantiates a new DirectionEnum.
     *
     * @param direction
     */
    DirectionEnum(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }

}
