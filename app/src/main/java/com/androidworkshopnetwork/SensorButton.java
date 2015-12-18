package com.androidworkshopnetwork;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.UnknownHostException;

/**
 * Created by Adrien on 16/12/15.
 */
public class SensorButton extends Button {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The sensor. */
    private Sensor sensor;

    /**
     * Instantiates a new SensorButton.
     *
     * @param context
     */
    public SensorButton(final Context context, final Sensor sensor) {
        super(context);
        this.sensor = sensor;
        this.setText(sensor.getName() + " (" + sensor.getIp() + ")" + "\n" + sensor.getSensorType());

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.setEnabled(false);
                    //new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new AutoReloadButton((SensorButton)v));
                    Toast.makeText(context, "STOP Alarm signal sending...", Toast.LENGTH_SHORT).show();
                    NetworkCommunicator.stopAlarm(sensor.getIp());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        this.update();
    }

    /**
     * Updates sensor state.
     *
     * @param state
     */
    public void updateSensorState(StateEnum state) {
        this.sensor.setState(state);
        //this.update();
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new AutoReloadButton(this));

        Log.i(TAG, "Save the sensor list");
        IOManager.saveSensorList();
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
     * Gets the sensor state.
     *
     * @return
     */
    public StateEnum getSensorState() {
        return sensor.getState();
    }

    /**
     * Update the button
     */
    private void update() {
        switch (sensor.getState()) {
            case OK:
                this.setBackgroundColor(Color.GREEN);
                this.setTextColor(Color.WHITE);
                this.setEnabled(false);
                break;

            case Alarm:
                this.setBackgroundColor(Color.RED);
                this.setTextColor(Color.WHITE);
                this.setEnabled(true);
                break;

            case Disconnected:
                this.setBackgroundColor(Color.BLACK);
                this.setTextColor(Color.WHITE);
                this.setEnabled(true);
                break;

            case SerialError:
                this.setBackgroundColor(Color.GRAY);
                this.setTextColor(Color.BLACK);
                this.setEnabled(true);
                break;

            case Unknown:
                this.setBackgroundColor(Color.WHITE);
                this.setTextColor(Color.BLACK);
                this.setEnabled(false);
                break;
        }
    }

    class AutoReloadButton implements Runnable {

        private SensorButton sensorButton;

        public AutoReloadButton(SensorButton sensorButton) {
            this.sensorButton = sensorButton;
        }

        @Override
        public void run() {
            sensorButton.update();
        }
    }

}
