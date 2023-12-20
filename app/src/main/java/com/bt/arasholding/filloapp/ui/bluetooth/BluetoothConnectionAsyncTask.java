package com.bt.arasholding.filloapp.ui.bluetooth;

import android.os.AsyncTask;

import java.io.IOException;

public class BluetoothConnectionAsyncTask extends AsyncTask<Void,Void,String> {

    BluetoothFragmentMvpView view ;

    public BluetoothConnectionAsyncTask(BluetoothFragmentMvpView view){
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        view.showLoadingWithMessage("Bağlanıyor...");
    }

    @Override
    protected String doInBackground(Void... voids) {
        String sonuc = "";

        try {

            if (view.findBT() != null){
                view.openBT();
            }
            else
                sonuc = "Bluetooth cihaz bulunamadı";
        } catch (IOException e) {
            sonuc = e.getMessage();
        }

        return sonuc;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        view.hideLoadingWithMessage();

        if(view.getBluetoothSocket() != null ){

            if (view.getBluetoothSocket().isConnected()){
                view.beginListenForData();
                view.updateLabel("Cihaz bağlandı : " + view.getBluetoothDeviceName());
                view.updateButtonText("Bağlantıyı Kapat");
            }else{
                view.updateButtonText("Bağlan");
                view.updateLabel(s);
            }
        }
    }
}
