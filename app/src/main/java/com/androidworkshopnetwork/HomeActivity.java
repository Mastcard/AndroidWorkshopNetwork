package com.androidworkshopnetwork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class HomeActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context context = this;

    private SensorManager sensorManager = SensorManager.getInstance();
    private SensorButtonManager sensorButtonManager = SensorButtonManager.getInstance();

    //private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        NetworkCommunicator.startServer(context);
        NetworkCommunicator.setContext(context);

        File serializable_file = new File(Constants.SERIALIZE_FILE);
        if (serializable_file.exists()) {
            IOManager.loadSensorList();
        }

        if (sensorManager.getSensorCount() == 0) {
            // Fichier vid
            Sensor camera = new Sensor();
            camera.setName("Camera1");
            camera.setIp("1.1.1.1");
            camera.setSensorType(SensorTypeEnum.Camera);
            camera.setState(StateEnum.Alarm);
            sensorManager.addSensor(camera);
        }

        this.init();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        sensorButtonManager.updateSensorButtonMap(context);
        HashMap<String, SensorButton> sensorButtonMap = sensorButtonManager.getSensorButtonMap();

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        for (Iterator<SensorButton> it = sensorButtonMap.values().iterator(); it.hasNext(); ) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            SensorButton sensorButton = (SensorButton) it.next();
            row.addView(sensorButton);

            layout.addView(row);
        }
        this.addContentView(layout, layoutParams);

    }

}
