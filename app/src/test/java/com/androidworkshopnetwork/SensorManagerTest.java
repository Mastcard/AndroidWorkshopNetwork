package com.androidworkshopnetwork;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests :
 *      - sensor creation
 *      - sensor management by SensorManager
 *
 * WARNING : The tests follow an execution order, then pay attention of all of them when you modify one.
 */
public class SensorManagerTest {

    /** The sensor manager instance. */
    private static SensorManager sensorManager = SensorManager.getInstance();

    /** The sensors. */
    private static Sensor camera1;
    private static Sensor photo1;
    private static Sensor button1;

    /**
     * Set up.
     */
    @BeforeClass
    public static void setUp() {
        camera1 = new Sensor();
        camera1.setName("Camera1");
        camera1.setIp("1.1.1.1");
        camera1.setSensorType(SensorTypeEnum.Camera);
        camera1.setState(StateEnum.OK);

        photo1 = new Sensor();
        photo1.setName("Photo1");
        photo1.setIp("1.1.1.2");
        photo1.setSensorType(SensorTypeEnum.Photoresistance);
        photo1.setState(StateEnum.OK);

        button1 = new Sensor();
        button1.setName("Button1");
        button1.setIp("1.1.1.3");
        button1.setSensorType(SensorTypeEnum.Pushbutton);
        button1.setState(StateEnum.OK);

        sensorManager.addSensor(camera1);
        sensorManager.addSensor(photo1);
        sensorManager.addSensor(button1);
    }

    @Test
    public void testSensorManagerMapSize() throws Exception {
        assertEquals(3, sensorManager.getSensorCount());
    }

    @Test
    public void testRetrieveSensorByName() throws Exception {
        // Negative test
        Sensor photo = sensorManager.getSensorByName("Photo");
        assertNull(photo);

        photo = sensorManager.getSensorByName("Photo1");
        assertNotNull(photo);
        assertEquals("Photo1", photo.getName());
        assertEquals("1.1.1.2", photo.getIp());
        assertEquals(SensorTypeEnum.Photoresistance, photo.getSensorType());
        assertEquals(StateEnum.OK, photo.getState());

    }

    @Test
    public void testRetrieveByIp() throws Exception {
        // Negative test
        Sensor camera = sensorManager.getSensorByIp("255.255.255.255");
        assertNull(camera);

        camera = sensorManager.getSensorByIp("1.1.1.1");
        assertNotNull(camera);
        assertEquals("Camera1", camera.getName());
        assertEquals("1.1.1.1", camera.getIp());
        assertEquals(SensorTypeEnum.Camera, camera.getSensorType());
        assertEquals(StateEnum.OK, camera.getState());
    }

    @Test
    public void testSensorModificationPropagation() throws Exception {
        Sensor button = sensorManager.getSensorByName("Button1");
        assertNotNull(button);
        assertEquals(StateEnum.OK, button.getState());

        button.setState(StateEnum.Alarm);

        Sensor sameButton = sensorManager.getSensorByName("Button1");
        assertEquals(StateEnum.Alarm, sameButton.getState());

        button.setState(StateEnum.Unknown);
        assertEquals(StateEnum.Unknown, sameButton.getState());

        sameButton.setState(StateEnum.SerialError);
        assertEquals(StateEnum.SerialError, button.getState());
    }

    @Test
    public void testAddSensor() throws Exception {
        Sensor camera2 = new Sensor();
        camera2.setName("Camera2");
        camera2.setIp("2.2.2.1");
        camera2.setSensorType(SensorTypeEnum.Camera);
        camera2.setState(StateEnum.Disconnected);

        sensorManager.addSensor(camera2);
        assertEquals(4, sensorManager.getSensorCount());

        // By name
        Sensor sameCamera = sensorManager.getSensorByName("Camera2");
        assertNotNull(sameCamera);
        assertEquals(camera2.getName(), sameCamera.getName());
        assertEquals(camera2.getIp(), sameCamera.getIp());
        assertEquals(camera2.getSensorType(), sameCamera.getSensorType());
        assertEquals(camera2.getState(), sameCamera.getState());

        // By IP
        sameCamera = sensorManager.getSensorByIp("2.2.2.1");
        assertNotNull(sameCamera);
        assertEquals(camera2.getName(), sameCamera.getName());
        assertEquals(camera2.getIp(), sameCamera.getIp());
        assertEquals(camera2.getSensorType(), sameCamera.getSensorType());
        assertEquals(camera2.getState(), sameCamera.getState());
    }

    @Test
    public void testRemoveSensor() throws Exception {
        sensorManager.removeSensor("Camera2");
        assertEquals(3, sensorManager.getSensorCount());

        // By name.
        Sensor nullSensor = sensorManager.getSensorByName("Camera2");
        assertNull(nullSensor);

        // By Ip.
        nullSensor = sensorManager.getSensorByIp("2.2.2.1");
        assertNull(nullSensor);
    }

}
