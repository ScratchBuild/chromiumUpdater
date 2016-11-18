package com.dosse.chromiumautoupdater;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

/**
 * This class contains utility methods
 */

public class Utils {
    /**
     * Check if the phone is rooted
     * @return true if su binary is present, false otherwise
     */
    public static boolean isRooted(){
        for (String s : System.getenv("PATH").split(System.getProperty("path.separator"))) {
            log("looking for su in "+s);
            if ( new File( s + (s.endsWith("/")?"":"/")+"su" ).exists() ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for storage write permissions
     * @param ctx context
     * @return true if permission is granted, false otherwise
     */
    public static boolean canWriteToSdcard(Context ctx){
        return ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks if an internet connection is available
     * @param ctx context
     * @return true if available, false otherwise
     */
    public static boolean isConnected(Context ctx){
        NetworkInfo info=((ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info!=null&&info.isConnected();
    }

    /**
     * Checks if the main internet connection is mobile
     * @param ctx context
     * @return true if available, false if not mobile or not connected
     */
    public static boolean isMobileConnection(Context ctx){
        NetworkInfo info=((ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info!=null&&info.getType()==ConnectivityManager.TYPE_MOBILE;
    }


    /**
     * Timestamp function in ms
     * @return utc time in ms
     */
    public static long getTimestamp(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public static final boolean USE_LOG= BuildConfig.DEBUG; //logging is only active in debug builds

    /**
     * Log
     * @param s String
     */
    public static void log(String s){
        if(USE_LOG) Log.d("Chromium Updater",s);
    }

}
