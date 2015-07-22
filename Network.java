package com.example.yezan.myapplication;
import  android.os.AsyncTask;
import  android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import  java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;

/**
 * Created by yezan on 21/07/15.
 */


public class Network extends AsyncTask<String, Void, String> {


    private  String  mHost;
    private  int     mPort;
    private  Socket  mSocket;

    public   Network(String mHost, int mPort)
    {
        this.mHost = mHost;
        this.mPort = mPort;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if ( params.length == 2 ) {
                if (params[0].equals("upload") && !params[1].isEmpty()) {
                    onUpload(params[1]);
                }
            }
            else {
                mSocket = new Socket(mHost, mPort);
            }
            Thread.sleep(5000);
        } catch (InterruptedException |  java.io.IOException e) {
            Log.e("LongOperation", "Interrupted", e);
            Log.e("Network Background", "Interupted or can't connect");
            return "Interrupted";
        }
        return "Executed";
    }

    public void onUpload(String fileName)
    {
        if ( mSocket != null ) {
            if (!mSocket.isConnected()) {
                Log.e("Newtwork", "Not connected !");
                return;
            }
            try {
                File f = new File(fileName);
                FileInputStream fin = new FileInputStream(f);
                ByteArrayOutputStream ba = new ByteArrayOutputStream();
                byte[]     receiverBuffer = new byte[400];

                int offset = 0;

                try {

                    mSocket.getOutputStream().write((f.length() + "/" + f.getName()).getBytes());
                    int a;
                    while ((a = fin.read()) != -1) {
                        ba.write(a);
                        offset++;
                    }
                    mSocket.getOutputStream().write(ba.toByteArray());


                    Log.e("Server Replied", receiverBuffer.toString());
                    System.out.println(offset);
                } catch (java.io.IOException e) {
                    e.getMessage();
                }
            } catch (java.io.FileNotFoundException e) {
                e.getMessage();
            }
        }
    }




}
