package com.androidworkshopnetwork;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Adrien on 17/12/15.
 */
public class IOManager {

    /** The TAG. */
    private static final String TAG = "IOMAnager";

    /** The DIR. */
    private static final String DIR = Environment.getExternalStorageDirectory().getAbsolutePath();

    /** The sensor manager instance. */
    private static SensorManager sensorManager = SensorManager.getInstance();

    /**
     * Serialize sensor list.
     */
    public static void saveSensorList() {
        try
        {
            /*
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/atelier");
            dir.mkdirs();

            File outStream = new File(dir, "sensors.ser");
            */

            File dir = new File(DIR);
            dir.mkdirs();

            File file = new File(DIR, "sensors.ser");
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sensorManager.getSensorMap());
            out.close();
            fileOut.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deserialize sensor list.
     */
    public static HashMap<String, Sensor> loadSensorList() {
        HashMap<String, Sensor> result = null;

        try
        {
            /*
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/atelier");
            dir.mkdirs();

            File inStream = new File(dir, "sensors.ser");
            */

            File file = new File(DIR, "sensors.ser");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            result = (HashMap<String, Sensor>) in.readObject();
            in.close();
            fileIn.close();

        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (result != null) {
            sensorManager.setSensorMap(result);
        }

        return sensorManager.getSensorMap();
    }

}
