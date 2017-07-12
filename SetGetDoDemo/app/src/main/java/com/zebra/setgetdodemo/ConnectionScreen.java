/********************************************** 
 * CONFIDENTIAL AND PROPRIETARY 
 *
 * The source code and other information contained herein is the confidential and the exclusive property of
 * ZIH Corp. and is subject to the terms and conditions in your end user license agreement.
 * This source code, and any other information contained herein, shall not be copied, reproduced, published, 
 * displayed or distributed, in whole or in part, in any medium, by any means, for any purpose except as
 * expressly permitted under such license agreement.
 * 
 * Copyright ZIH Corp. 2010
 *
 * ALL RIGHTS RESERVED 
 ***********************************************/

package com.zebra.setgetdodemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * This class is for use with the ZebraLink Multiplatform SDK 
 * Demos.
 * 

 */


public abstract class ConnectionScreen extends AppCompatActivity {

    protected Button testButton;
    private RadioButton btRadioButton;
    private EditText macAddress;
    private EditText ipAddress;
    private EditText portNumber;

    public static final String bluetoothAddressKey = "ZEBRA_DEMO_BLUETOOTH_ADDRESS";
    public static final String tcpAddressKey = "ZEBRA_DEMO_TCP_ADDRESS";
    public static final String tcpPortKey = "ZEBRA_DEMO_TCP_PORT";
    public static final String PREFS_NAME = "OurSavedAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        ipAddress = (EditText) this.findViewById(R.id.ipAddressInput);
        String ip = settings.getString(tcpAddressKey, "");
        ipAddress.setText(ip);

        portNumber = (EditText) this.findViewById(R.id.portInput);
        String port = settings.getString(tcpPortKey, "");
        portNumber.setText(port);

        macAddress = (EditText) this.findViewById(R.id.macInput);
        String mac = settings.getString(bluetoothAddressKey, "");
        macAddress.setText(mac);

        btRadioButton = (RadioButton) this.findViewById(R.id.bluetoothRadio);

        testButton = (Button) this.findViewById(R.id.testButton);
        testButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                performTest();
            }
        });


        TextView t2 = (TextView) findViewById(R.id.launchpad_link);
        t2.setMovementMethod(LinkMovementMethod.getInstance());


        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.bluetoothRadio) {
                    toggleEditField(macAddress, true);
                    toggleEditField(portNumber, false);
                    toggleEditField(ipAddress, false);
                } else {
                    toggleEditField(portNumber, true);
                    toggleEditField(ipAddress, true);
                    toggleEditField(macAddress, false);
                }
            }
        });
    }

    private void toggleEditField(EditText editText, boolean set) {
        /*
         * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
         *       See http://code.google.com/p/android/issues/detail?id=2771
         */
        editText.setEnabled(set);
        editText.setFocusable(set);
        editText.setFocusableInTouchMode(set);
    }

    protected boolean isBluetoothSelected() {
        return btRadioButton.isChecked();
    }

    protected String getMacAddressFieldText() {
        return macAddress.getText().toString();
    }

    protected String getTcpAddress() {
        return ipAddress.getText().toString();
    }

    protected String getTcpPortNumber() {
        return portNumber.getText().toString();
    }

    public abstract void performTest();

}