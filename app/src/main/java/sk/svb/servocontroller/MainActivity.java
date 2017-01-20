package sk.svb.servocontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sk.svb.servocontroller.UsbDevice.ServoController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText channelET;
    EditText valueET1, valueET2, valueET3, valueET4, valueET5, valueET6;
    Button sendBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServoController.findDeviceAndStartService(getApplicationContext());
//        ServoController.sendMessage(getApplicationContext(), ServoController.LOCATION_CENTER, ServoController.SPEED_NORMAL);

        initView();
    }

    private void initView() {
        valueET1 = (EditText) findViewById(R.id.value_1);
        valueET2 = (EditText) findViewById(R.id.value_2);
        valueET3 = (EditText) findViewById(R.id.value_3);
        valueET4 = (EditText) findViewById(R.id.value_4);
        valueET5 = (EditText) findViewById(R.id.value_5);
        valueET6 = (EditText) findViewById(R.id.value_6);

        sendBTN = (Button) findViewById(R.id.send_btn);
        sendBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.send_btn:
                //TODO validation
                ServoController.sendMessage(getApplicationContext(), 1, Integer.parseInt(valueET1.getText().toString()), ServoController.SPEED_NORMAL);
                ServoController.sendMessage(getApplicationContext(), 2, Integer.parseInt(valueET2.getText().toString()), ServoController.SPEED_NORMAL);
                ServoController.sendMessage(getApplicationContext(), 3, Integer.parseInt(valueET3.getText().toString()), ServoController.SPEED_NORMAL);
                ServoController.sendMessage(getApplicationContext(), 4, Integer.parseInt(valueET4.getText().toString()), ServoController.SPEED_NORMAL);
                ServoController.sendMessage(getApplicationContext(), 5, Integer.parseInt(valueET5.getText().toString()), ServoController.SPEED_NORMAL);
                ServoController.sendMessage(getApplicationContext(), 6, Integer.parseInt(valueET6.getText().toString()), ServoController.SPEED_NORMAL);
                break;
        }
    }
}
