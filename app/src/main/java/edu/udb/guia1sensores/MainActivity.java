package edu.udb.guia1sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor==null);
        Toast.makeText(this, "Mueve el dispositivo a un lado ", Toast.LENGTH_SHORT).show();

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x= sensorEvent.values[0];
                System.out.println("valor giro"+x);
                if(x<-5 && whip == 0){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(x>5 && whip ==1 ){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }

                if (whip==2){
                    sound();
                    whip=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }
    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.latigo);
        mediaPlayer.start();

    }
    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}