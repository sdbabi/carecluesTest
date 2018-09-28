package com.careclues.app.constraints;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Common {

    public static boolean isDebug=true;
    // method to check internet connectivity
    public static boolean checkNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        try {
            if (context != null) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm != null) {
                    NetworkInfo[] netInfo = cm.getAllNetworkInfo();

                    if (netInfo != null) {
                        for (NetworkInfo ni : netInfo) {
                            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                                haveConnectedWifi = ni.isConnected();
                            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                                haveConnectedMobile = ni.isConnected();
                        }
                    }
                } else {
                    //could not fetch status assume true
                    haveConnectedWifi = true;
                    haveConnectedMobile = true;
                }
            } else {
                //could not fetch status assume true
                haveConnectedWifi = true;
                haveConnectedMobile = true;
            }
        } catch (Exception e) {
            haveConnectedWifi = false;
            haveConnectedMobile = false;
            Log.e("Network Error", "Error fecting network status");
        }

        return haveConnectedWifi || haveConnectedMobile;
    }
}
