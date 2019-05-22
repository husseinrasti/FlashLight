package ir.teachcode.app.flashlight.utils;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.widget.ImageView;
import android.widget.Toast;

import ir.teachcode.app.flashlight.R;

public class FlashlightProvider {

    private static FlashlightProvider flashlightProvider;

    private static Camera camera;
    private static CameraManager camManager;
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

    public void close() {
        if ( isFlashOn ) {
            turnOff();
        }
    }

    private void turnOn() {
        if ( Utils.isSdk23() ) {
            try {
                camManager = ( CameraManager ) mContext.getSystemService( Context.CAMERA_SERVICE );
                String cameraId = null; // Usually front camera is at 0 position.
                if ( camManager != null ) {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode( cameraId , true );
                    isFlashOn = true;
                    toggleButtonImage();
                }
            } catch ( CameraAccessException e ) {
                e.printStackTrace();
            }
        } else if ( SharedPref.getGrantPermission() ) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
                params = camera.getParameters();
                params.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
                camera.setParameters( params );
                camera.startPreview();
                isFlashOn = true;
                toggleButtonImage();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    private void turnOff() {
        if ( Utils.isSdk23() ) {
            try {
                String cameraId;
                camManager = ( CameraManager ) mContext.getSystemService( Context.CAMERA_SERVICE );
                if ( camManager != null ) {
                    cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                    camManager.setTorchMode( cameraId , false );
                    isFlashOn = false;
                    toggleButtonImage();
                }
            } catch ( CameraAccessException e ) {
                e.printStackTrace();
            }
        } else if ( SharedPref.getGrantPermission() ) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
                params = camera.getParameters();
                params.setFlashMode( Camera.Parameters.FLASH_MODE_OFF );
                camera.setParameters( params );
                camera.stopPreview();
                camera.release();
                isFlashOn = false;
                toggleButtonImage();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
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
