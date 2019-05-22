package ir.teachcode.app.flashlight.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

import ir.teachcode.app.flashlight.R;

/**
 * Created by Hossein on 3/22/2018.
 */

public abstract class RuntimePermissionsActivity extends AppCompatActivity {


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode , String[] permissions , int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode , permissions , grantResults );

    }

    public void requestAppPermissions( final String[] requestedPermissions ,
                                       final int stringId , final int requestCode ) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for ( String permission : requestedPermissions ) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission( this , permission );
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale
                    || ActivityCompat.shouldShowRequestPermissionRationale( this , permission );
        }
        if ( permissionCheck != PackageManager.PERMISSION_GRANTED ) {
            if ( shouldShowRequestPermissionRationale ) {
                Snackbar.make( findViewById( android.R.id.content ) , stringId ,
                        Snackbar.LENGTH_INDEFINITE ).setAction( R.string.str_btn_snackbar_enable ,
                        v -> ActivityCompat.requestPermissions( this , requestedPermissions , requestCode ) ).show();
            } else {
                ActivityCompat.requestPermissions( this , requestedPermissions , requestCode );
            }
        } else {
            onPermissionsGranted( requestCode );
        }

    }

    public abstract void onPermissionsGranted( int requestCode );
}