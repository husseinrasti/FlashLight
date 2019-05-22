package ir.teachcode.app.flashlight.utils;

import android.content.Context;
import android.hardware.Camera;
import android.widget.ImageView;
import android.widget.Toast;

import ir.teachcode.app.flashlight.R;

public class FlashlightProvider {

    private static FlashlightProvider flashlightProvider;

    private static Camera camera;
    private static Camera.Parameters params;

    private boolean isFlashOn = false;

    private static boolean hasFlash;
    private static Context mContext;

    private ImageView imgFlashLed;

    public static FlashlightProvider getInstance( Context context ) {
        mContext = context;
        if ( flashlightProvider == null ) {
            flashlightProvider = new FlashlightProvider();
        }

        hasFlash = Utils.hasFlash( context );
        if ( !hasFlash ) {
            Toast.makeText( context , context.getString( R.string.str_toast_not_support_flashlight ) , Toast.LENGTH_SHORT ).show();
        }

        getCamera();

        return flashlightProvider;
    }

    public void checkFlashLight() {
        if ( !hasFlash ) {
            Toast.makeText( mContext , mContext.getString( R.string.str_toast_not_support_flashlight ) , Toast.LENGTH_SHORT ).show();
            return;
        }
        if ( isFlashOn ) {
            turnOff();
        } else {
            turnOn();
        }
    }

    public void setImgFlashLed( ImageView imageView ) {
        imgFlashLed = imageView;
    }

    public static void getCamera() {
        if ( camera == null ) {
            if ( SharedPref.getGrantPermission() ) {
                try {
                    camera = Camera.open();
                    params = camera.getParameters();
                } catch ( RuntimeException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onPause() {
        turnOff();
    }

    public void onStop() {
        if ( camera != null ) {
            camera.release();
            camera = null;
        }
    }

    private void turnOn() {
        if ( camera != null ) {
            params = camera.getParameters();
            params.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
            camera.setParameters( params );
            camera.startPreview();
            isFlashOn = true;
            toggleButtonImage();
        }
    }

    private void turnOff() {
        if ( camera != null ) {
            params = camera.getParameters();
            params.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
            camera.setParameters( params );
            camera.stopPreview();
            isFlashOn = false;
            toggleButtonImage();
        }
    }

    private void toggleButtonImage() {
        if ( !isFlashOn ) {
            imgFlashLed.setImageResource( R.drawable.btn_torch_default );
        } else {
            imgFlashLed.setImageResource( R.drawable.btn_torch_pressed );
        }
    }

}
