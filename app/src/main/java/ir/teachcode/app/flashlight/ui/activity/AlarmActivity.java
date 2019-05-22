package ir.teachcode.app.flashlight.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.App;
import ir.teachcode.app.flashlight.R;


public class AlarmActivity extends AppCompatActivity {


    private Unbinder unbinder;

    @BindView(R.id.imgWarningAlarm)
    ImageView imgWarningAlarm;
    @BindView(R.id.imgBackAlarm)
    ImageView imgBackAlarm;
    @BindView(R.id.imgPlayAlarm)
    ImageView imgPlay;
    @BindView(R.id.imgStopAlarm)
    ImageView imgStop;

    private boolean isActive = false;
    private boolean isSwap = true;

    private Runnable runnable;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alarm );
        unbinder = ButterKnife.bind( this );

        WindowManager.LayoutParams layoutParam = getWindow().getAttributes();
        layoutParam.screenBrightness = 1;
        getWindow().setAttributes( layoutParam );

        imgPlay.setOnClickListener( v -> {
            mediaPlayer = MediaPlayer.create( this , R.raw.warning );
            mediaPlayer.setLooping( true );
            imgPlay.setVisibility( View.INVISIBLE );
            imgStop.setVisibility( View.VISIBLE );
            try {
                mediaPlayer.start();
            } catch ( IllegalArgumentException e ) {
                e.printStackTrace();
            } catch ( SecurityException e ) {
                e.printStackTrace();
            } catch ( IllegalStateException e ) {
                e.printStackTrace();
            }
        } );
        imgStop.setOnClickListener( v -> {
            try {
                imgPlay.setVisibility( View.VISIBLE );
                imgStop.setVisibility( View.INVISIBLE );
                mediaPlayer.release();
                mediaPlayer = null;
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } );
        runnable = () -> {
            try {
                alarmLight();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        };

        startAlarmLights();

        imgBackAlarm.setOnClickListener( v -> finish() );
    }

    private void alarmLight() {
        if ( isActive ) {
            if ( isSwap ) {
                imgWarningAlarm.setImageResource( R.drawable.warning_lights_off );
                isSwap = false;
                App.HANDLER.postDelayed( runnable , 500 );
            } else {
                imgWarningAlarm.setImageResource( R.drawable.warning_lights_on );
                isSwap = true;
                App.HANDLER.postDelayed( runnable , 500 );
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        runnable = null;
        try {
            mediaPlayer.release();
            mediaPlayer.stop();
        } catch ( Exception e ) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void startAlarmLights() {
        isActive = true;
        App.HANDLER.post( runnable );
    }

}