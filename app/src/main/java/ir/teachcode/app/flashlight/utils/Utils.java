package ir.teachcode.app.flashlight.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class Utils {

    public static String PACKAGE_NAME;

    public static boolean isSdk23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasFlash( Context context ) {
        return context.getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FLASH );
    }

    public static Boolean isPackageExisted( Context context , String targetPackage ) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo( targetPackage , PackageManager.GET_META_DATA );
        } catch ( PackageManager.NameNotFoundException e ) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkConnected( Context context ) {
        ConnectivityManager connectivityManager = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
