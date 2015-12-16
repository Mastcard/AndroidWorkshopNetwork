package com.androidworkshopnetwork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context context = this;

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

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 4; j++) {
                SensorButton btnTag = new SensorButton(context, camera);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnTag.setText("Button " + (j + 1 + (i * 4)));
                btnTag.setId(j + 1 + (i * 4));
                btnTag.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View v) {
                                                  Log.i(TAG, "You clicked on the button");
                                                  Toast.makeText(context, R.string.stop_sended, Toast.LENGTH_LONG).show();
                                                  ((SensorButton) v).updateSensorState(StateEnum.OK);
                                              }
                                          }
                );

                row.addView(btnTag);
            }

            layout.addView(row);
        }

        this.addContentView(layout, layoutParams);
    }

}
