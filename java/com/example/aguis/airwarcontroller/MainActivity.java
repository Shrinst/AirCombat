package com.example.aguis.airwarcontroller;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private Client mClient;
    private TextView tvText;
    private TextView textViewMessage;
    private Button btnShoot;
    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private boolean shootFlag = false;

    private int X_AXIS;
    private int Y_AXIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "TESLA.ttf");

        tvText = (TextView) findViewById(R.id.tvTextView);
        tvText.setTextColor(Color.CYAN);
        tvText.setTypeface(myTypeface);

        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        textViewMessage.setTextColor(Color.CYAN);
        textViewMessage.setTypeface(myTypeface);

        btnShoot = (Button) findViewById(R.id.btnShoot);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        new connectTask().execute("");

        btnShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shootFlag = true;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        X_AXIS = (int) event.values[0];
        Y_AXIS = (int) event.values[1];

        tvText.setText(" ");
        tvText.append("\n" + "El valor de X: " +  X_AXIS + "\n" + "El valor de Y: " + Y_AXIS );

        String msg = "";
        if (shootFlag) {
            msg +=  String.valueOf(X_AXIS) + ";" + String.valueOf(Y_AXIS) + ";" + "shooting";
        } else {
            msg +=  String.valueOf(X_AXIS) + ";" + String.valueOf(Y_AXIS) + ";" + "null";
        }

        shootFlag = false;

        mClient.sendMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }



    public class connectTask extends AsyncTask<String,String,Client> {

        @Override
        protected Client doInBackground(String... message) {

            //we create a Client object and
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the textview we add the messaged received from server
            String[] split = values[0].split(";");
            textViewMessage.setText("");
            textViewMessage.append("Puntaje: " + split[0] + "\n" + "\n" + "Nivel: " + split[1] + "\n" + "\n" +
                    "Tiempo: " + split[2]);
            //textViewMessage.append(values[0] + "\n");
        }
    }
}
