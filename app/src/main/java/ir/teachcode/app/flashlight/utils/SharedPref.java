package ir.teachcode.app.flashlight.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static final String NAME_SESSION_PREF = "FlashLightPref";
    private static final String SHOW_DIALOG_KEY = "SHOW_DIALOG";
    private static final String GRANT_PERMISSION_KEY = "GRANT_PERMISSION_KEY";

    private static Context mContext;

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private static SharedPref sharedPref;

    public static SharedPref getInstance( Context context ) {
        mContext = context;
        if ( sharedPref == null ) {
            sharedPref = new SharedPref();
            initPref();
        }

        return sharedPref;
    }

    private static void initPref() {
        pref = mContext.getSharedPreferences( NAME_SESSION_PREF , Context.MODE_PRIVATE );
    }

    public static void setShowDialog( boolean key ) {
        editor = pref.edit();
        editor.putBoolean( SHOW_DIALOG_KEY , key );
        editor.apply();
    }


    public static Boolean getShowDialog() {
        return pref.getBoolean( SHOW_DIALOG_KEY , false );
    }

    public static void setGrantPermission() {
        editor = pref.edit();
        editor.putBoolean( GRANT_PERMISSION_KEY , true );
        editor.apply();
    }


    public static Boolean getGrantPermission() {
        return pref.getBoolean( GRANT_PERMISSION_KEY , false );
    }

}
