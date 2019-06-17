package com.example.ex4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;

public class JoyStickActivity extends AppCompatActivity {
    private JoyStickLogic js;
    private final String AileronPath = "set controls/flight/aileron";
    private final String ElevatorPath = "set controls/flight/elevator";
    private static final String STICK_UP = "UP";
    private static final String STICK_RIGHT = "RIGHT";
    private static final String STICK_DOWN = "DOWN";
    private static final String STICK_LEFT = "LEFT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_stick);

        RelativeLayout layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoyStickLogic(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(110);
        js.setMinimumDistance(50);
        js.initJoyStickPosition();

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                float xPosition = js.getX();
                float yPosition = js.getY();
                String direction = js.get4Direction();
                sendData(xPosition, yPosition, direction);
                return true;
            }
        });
    }

    public void sendData(float x, float y, String direction) {

        if (direction.equals(STICK_UP) || direction.equals(STICK_DOWN)) {

            String massage = ElevatorPath + " " + String.valueOf(y);
            TcpClient.Instance().sendMesssage(massage);
        } else {
            String massage = AileronPath + " " + String.valueOf(x);
            TcpClient.Instance().sendMesssage(massage);
        }
    }


}
