package com.androidworkshopnetwork;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Adrien on 17/12/15.
 */
public class IOManagerTest {

    /** The Sensor Manager instance. */
    private static SensorManager sensorManager = SensorManager.getInstance();

    @BeforeClass
    public static void setUp() {
        Sensor camera1 = new Sensor();
        camera1.setName("camera1");
        camera1.setIp("1.1.1.1");
        camera1.setSensorType(SensorTypeEnum.Camera);
        camera1.setState(StateEnum.OK);
        sensorManager.addSensor(camera1);

        Sensor button1 = new Sensor();
        button1.setName("button1");
        button1.setIp("1.1.1.2");
        button1.setSensorType(SensorTypeEnum.Pushbutton);
        button1.setState(StateEnum.OK);
        sensorManager.addSensor(button1);
    }

    @Test
    public void testSerializeAndDeserialize() {
        // Check sensor manager map status
        assertNotNull(sensorManager.getSensorMap());
        assertEquals(2, sensorManager.getSensorCount());

        // Test
        IOManager.saveSensorList();
        HashMap<String, Sensor> deserializedMap = IOManager.loadSensorList();

        assertNotNull(deserializedMap);
        assertEquals(2, deserializedMap.size());

        assertNotNull(deserializedMap);
        assertEquals(2, deserializedMap.size());
        assertNotNull(sensorManager.getSensorMap());
        assertEquals(2, sensorManager.getSensorMap().size());
        assertEquals(2, sensorManager.getSensorCount());

        Sensor camera1 = sensorManager.getSensorByName("camera1");
        assertNotNull(camera1);
        assertEquals("camera1", camera1.getName());
        assertEquals("1.1.1.1", camera1.getIp());
        assertEquals(SensorTypeEnum.Camera, camera1.getSensorType());
        assertEquals(StateEnum.OK, camera1.getState());

        Sensor button1 = sensorManager.getSensorByName("button1");
        assertNotNull(button1);
        assertEquals("button1", button1.getName());
        assertEquals("1.1.1.2", button1.getIp());
        assertEquals(SensorTypeEnum.Pushbutton, button1.getSensorType());
        assertEquals(StateEnum.OK, button1.getState());
    }

    @Test
    public void testAddAndSerialize() {
        Sensor photo = new Sensor();
        photo.setName("photo1");
        photo.setIp("1.1.1.3");
        photo.setSensorType(SensorTypeEnum.Photoresistance);
        photo.setState(StateEnum.Unknown);

        sensorManager.addSensor(photo);
        IOManager.saveSensorList();

        HashMap<String, Sensor> map = IOManager.loadSensorList();
        assertEquals(3, map.size());
        assertEquals(3, sensorManager.getSensorCount());

        assertEquals("1.1.1.1", sensorManager.getSensorByName("camera1").getIp());
        assertEquals("1.1.1.2", sensorManager.getSensorByName("button1").getIp());
        assertEquals("1.1.1.3", sensorManager.getSensorByName("photo1").getIp());
    }

}
