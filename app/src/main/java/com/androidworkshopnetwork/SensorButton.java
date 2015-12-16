package com.androidworkshopnetwork;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by Adrien on 16/12/15.
 */
public class SensorButton extends Button {

    /** The sensor. */
    private Sensor sensor;

    /**
     * Instantiates a new SensorButton.
     *
     * @param context
     */
    public SensorButton(Context context, Sensor sensor) {
        super(context);
        this.sensor = sensor;

        this.update();
    }

    /**
     * Updates sensor state.
     *
     * @param state
     */
    public void updateSensorState(StateEnum state) {
        this.sensor.setState(state);
        this.update();
    }

    /**
     * Gets the sensor name
     *
     * @return the sensor name
     */
    public String getSensorName() {
        return sensor.getName();
    }

    /**
     * Gets the sensor Ip.
     *
     * @return
     */
    public String getSensorIp() {
        return sensor.getIp();
    }

    /**
     * Update the button
     */
    private void update() {
        switch (sensor.getState()) {
            case OK:
                this.setBackgroundColor(Color.GREEN);
                this.setTextColor(Color.WHITE);
                break;

            case Alarm:
                this.setBackgroundColor(Color.RED);
                this.setTextColor(Color.WHITE);
                break;

            case Disconnected:
                this.setBackgroundColor(Color.BLACK);
                this.setTextColor(Color.WHITE);
                break;

            case Unknown:
                this.setBackgroundColor(Color.GRAY);
                this.setTextColor(Color.BLACK);
                break;
        }
    }

}
