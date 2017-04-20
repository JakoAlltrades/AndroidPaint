package com.example.matthewbalderas.bluetoothexample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(bluetoothAdapter.isEnabled()) {
            Log.e("bluetooth enabled:", "true");
        }
        else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        }



    }

    public void turnBluetoothOff(View view) {

        bluetoothAdapter.disable();
    }

    public void discoverDevices(View view) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        startActivity(intent);

    }

    public void pairedDevices(View view) {
        Set<BluetoothDevice> set = bluetoothAdapter.getBondedDevices();
        ListView listView = (ListView)findViewById(R.id.list);

        ArrayList arrayList = new ArrayList();
        for(BluetoothDevice bluetoothDevice : set) {
            arrayList.add(bluetoothDevice.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
