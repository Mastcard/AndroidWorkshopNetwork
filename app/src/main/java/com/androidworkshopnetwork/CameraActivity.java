package com.androidworkshopnetwork;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.UnknownHostException;

public class CameraActivity extends AppCompatActivity {

    /** The TAG. */
    private final String TAG = this.getClass().getSimpleName();

    /** The cameraIp. */
    private String cameraIp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        cameraIp = intent.getStringExtra("ip");

        this.initButtons();
    }

    private void initButtons() {
        DirectionButton upButton = (DirectionButton) findViewById(R.id.up_button);
        upButton.setIp(cameraIp);
        upButton.setDirection(DirectionEnum.UP);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectionButton directionButton = (DirectionButton) v;
                try {
                    NetworkCommunicator.sendCameraDirection(directionButton.getDirection(), directionButton.getIp());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        DirectionButton downButton = (DirectionButton) findViewById(R.id.down_button);
        downButton.setIp(cameraIp);
        downButton.setDirection(DirectionEnum.DOWN);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectionButton directionButton = (DirectionButton) v;
                try {
                    NetworkCommunicator.sendCameraDirection(directionButton.getDirection(), directionButton.getIp());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        DirectionButton leftButton = (DirectionButton) findViewById(R.id.left_button);
        leftButton.setIp(cameraIp);
        leftButton.setDirection(DirectionEnum.LEFT);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectionButton directionButton = (DirectionButton) v;
                try {
                    NetworkCommunicator.sendCameraDirection(directionButton.getDirection(), directionButton.getIp());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        DirectionButton rightButton = (DirectionButton) findViewById(R.id.right_button);
        rightButton.setIp(cameraIp);
        rightButton.setDirection(DirectionEnum.RIGHT);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectionButton directionButton = (DirectionButton) v;
                try {
                    NetworkCommunicator.sendCameraDirection(directionButton.getDirection(), directionButton.getIp());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
