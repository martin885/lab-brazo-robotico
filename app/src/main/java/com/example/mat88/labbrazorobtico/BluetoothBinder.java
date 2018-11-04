package com.example.mat88.labbrazorobtico;


import android.os.Binder;

public class BluetoothBinder extends Binder {

    public BluetoothBinder(ServiceBluetooth service){
        this.setService(service);
    }
    private ServiceBluetooth Service;


    public ServiceBluetooth getService() {
        return Service;
    }


    private void setService(ServiceBluetooth service) {
        Service = service;
    }
}
