package com.example.mat88.labbrazorobtico;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ServiceBluetooth bluetoothService = null;
    boolean bluetoothBound = false;


    Switch conectar;

    char progress1;
    char progress2;
    char progress3;
    Button button1, button2, button3, button4, button5, button6;
    SeekBar seekBar1, seekBar2, seekBar3;
    TextView Result1, Result2, Result3;
    ImageView button;

    boolean checked;

    MainActivity mainActivity = this;

    private char[] dataToSend = new char[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        conectar = (Switch) findViewById(R.id.toggleButton1);
        conectar.setOnCheckedChangeListener(SwitchChange);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'a';
                dataToSend[1] = 'a';
                bluetoothService.writeData(dataToSend);
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'b';
                dataToSend[1] = 'b';
                bluetoothService.writeData(dataToSend);
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'c';
                dataToSend[1] = 'c';
                bluetoothService.writeData(dataToSend);
            }
        });
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'd';
                dataToSend[1] = 'd';
                bluetoothService.writeData(dataToSend);
            }
        });
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'e';
                dataToSend[1] = 'e';
                bluetoothService.writeData(dataToSend);
            }
        });
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataToSend[0] = 'f';
                dataToSend[1] = 'f';
                bluetoothService.writeData(dataToSend);
            }
        });
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1 = (char) progress;
                Result1.setText("PULGAR: " + progress);
                dataToSend[0] = 'g';
                dataToSend[1] = progress1;
                bluetoothService.writeData(dataToSend);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Result1 = findViewById(R.id.textView1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress2 = (char) progress;
                Result2.setText("INDICE: " + progress);
                dataToSend[0] = 'h';
                dataToSend[1] = progress2;
                bluetoothService.writeData(dataToSend);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Result2 = findViewById(R.id.textView2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar3.setProgress(100);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress3 = (char) progress;
                Result3.setText("RESTANTES: " + progress);
                dataToSend[0] = 'i';
                dataToSend[1] = progress3;
                bluetoothService.writeData(dataToSend);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Result3 = findViewById(R.id.textView3);
        button=(ImageView)findViewById(R.id.imageView2);
    }

    CompoundButton.OnCheckedChangeListener SwitchChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checked = isChecked;
            Conexion con = new Conexion();
            Thread conHilo = new Thread(con);
            conHilo.start();


        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent blueService = new Intent(this, ServiceBluetooth.class);
        bindService(blueService, connectionBluetooth, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection connectionBluetooth = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothBinder binder = (BluetoothBinder) service;
            bluetoothService = binder.getService();
            bluetoothBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bluetoothBound = false;
        }

    };

    class Conexion implements Runnable {
        public void run() {
            if (checked) {
                bluetoothService.Connect();
                if (bluetoothService.btSocket.isConnected()) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Conexión Correcta", Toast.LENGTH_SHORT).show();
                            button.setImageResource(R.drawable.green_button);
                        }
                    });

                } else {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "No Se Pudo Conectar", Toast.LENGTH_SHORT).show();
                            conectar.setChecked(false);
                        }
                    });

                }
            } else {
                if (bluetoothService.btSocket.isConnected()) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setImageResource(R.drawable.red_button);
                            try {
                                bluetoothService.btSocket.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });


                }


            }
        }
    }


}
