package com.androidworkshopnetwork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context context = this;

    private RelativeLayout.LayoutParams layoutParams =
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);




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

        Log.d(TAG, "before init");
        this.init();
        Log.d(TAG, "after init");
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
        Sensor camera = new Sensor();
        camera.setName("Camera1");
        camera.setIp("1.1.1.1");
        camera.setSensorType(SensorTypeEnum.Camera);
        camera.setState(StateEnum.Alarm);

        SensorButton button = new SensorButton(context, camera);
        button.setText(button.getSensorName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "You clicked on the button");
                Toast.makeText(context, R.string.stop_sended, Toast.LENGTH_LONG).show();
                ((SensorButton) v).updateSensorState(StateEnum.OK);

            }
        });

        this.addContentView(button, layoutParams);
    }

}
