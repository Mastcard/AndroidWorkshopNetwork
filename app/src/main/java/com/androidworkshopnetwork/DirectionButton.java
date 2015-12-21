package com.androidworkshopnetwork;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Adrien on 18/12/15.
 */
public class DirectionButton extends Button {

    /** The direction. */
    private DirectionEnum direction;

    /** The ip. */
    private String ip;

    /**
     * Instantiates a new direction button.
     *
     * @param context
     */
    public DirectionButton(Context context, AttributeSet set) {
        super(context, set);
    }

    /**
     * Gets the direction.
     *
     * @return direction
     */
    public DirectionEnum getDirection() {
        return direction;
    }

    /**
     * Sets the direction.
     *
     * @param direction
     */
    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
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

}
