package com.example.mat88.labbrazorobtico;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.stream.Stream;

public class ServiceBluetooth extends Service {

    private IBinder Binder;

    public BluetoothAdapter mBluetoothAdapter = null;
    public BluetoothSocket btSocket = null;


    private OutputStream outStream = null;
    private InputStream inStream = null;

    public String address = "98:D3:36:81:08:64";
//public String address="20:62:74:88:57:96";
    private static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ServiceBluetooth() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        setBinder(new BluetoothBinder(this));
        return getBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        CheckBt();
    }

    public IBinder getBinder() {
        return Binder;
    }

    public void setBinder(IBinder binder) {
        Binder = binder;
    }


    //------------FUNCIONES----------------------
    public void Connect() {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        mBluetoothAdapter.cancelDiscovery();

        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

            //btSocket=device.createInsecureRfcommSocketToServiceRecord(MY_UUID);

            btSocket.connect();

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                btSocket.close();
            }catch(Exception exe){
                exe.printStackTrace();
            }
        }

    }


    public void CheckBt(){
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter.enable()){
            Toast.makeText(this,"Bluetooth Activado", Toast.LENGTH_LONG).show();
        }else if(!mBluetoothAdapter.enable()){
            Toast.makeText(this,"Bluetooth Desactivado",Toast.LENGTH_SHORT).show();
        }else if(mBluetoothAdapter==null){
            Toast.makeText(this,"Bluetooth No existe O Está Ocupado",Toast.LENGTH_SHORT).show();
        }
    }

    public void writeData(char[] data){
        try{

                outStream = btSocket.getOutputStream();


        }catch (Exception ex){

            ex.printStackTrace();
        }

        char[] message = data;

        byte[] msgBuffer=new byte[2];

        msgBuffer[0]=(byte)message[0];
        msgBuffer[1]=(byte)message[1];

        try{
            if(outStream!=null){
                outStream.write(msgBuffer,0,msgBuffer.length) ;
            }

        }catch(Exception ex){
ex.printStackTrace();
        }
    }

}

