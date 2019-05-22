package ir.teachcode.app.flashlight;

import android.app.Application;
import android.os.Handler;

import co.ronash.pushe.Pushe;
import ir.adad.core.Adad;
import ir.teachcode.app.flashlight.utils.SharedPref;
import ir.teachcode.app.flashlight.utils.Utils;

/**
 * Created by Hossein on 5/5/2017.
 */

public class App extends Application {

    public static final Handler HANDLER = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPref.getInstance( getApplicationContext() );

        Utils.PACKAGE_NAME = getApplicationContext().getPackageName();

        if ( Utils.isNetworkConnected( getApplicationContext() ) ) {
            Adad.initialize( "5a807b08baca4b3ab9cd3463ae21c603" );
            Pushe.initialize( this , true );
        }
    }
}